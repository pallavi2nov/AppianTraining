package com.appian.robot.core.template;

import com.novayre.jidoka.client.api.IJidokaServer;
import com.novayre.jidoka.client.api.IRobot;
import com.novayre.jidoka.client.api.JidokaFactory;
import com.novayre.jidoka.falcon.api.FalconImageOptions;
import com.novayre.jidoka.falcon.api.IFalcon;
import com.novayre.jidoka.falcon.api.IFalconImage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to manage the Falcon Module
 *
 */
public class FalconManager {

	/**
	 * Hawk-Eye module
	 */
	private IFalcon falcon;

	/**
	 * Server
	 */
	private IJidokaServer< ? > server;

	/**
	 * Images folder name
	 */
	private String imagesFolderName;

	private final Map<String, IFalconImage> images = new HashMap<>();


	/**
	 * Class constructor
	 * Instantiates main objects 
	 * @param robot
	 * @throws IOException
	 */
	public FalconManager(IRobot robot, IFalcon falcon) throws IOException {

		server = JidokaFactory.getServer();

		this.falcon = falcon;

	}

	/**
	 * Initializes the images to search.
	 * <p>
	 * The images to search are those contained in the robot configuration page in
	 * the Appian RPA Console under the folder "images/images-to-search".
	 * @param imagesToSearch 
	 */
	public void initImagesToSearch(List<IFalconImage> imagesToSearch, String imagesFolder) {

		imagesFolderName = imagesFolder;

		// Get the proper path
		Path path = Paths.get(server.getCurrentDir(), imagesFolderName);

		// List files for that path
		File[] listFiles = path.toFile().listFiles();

		server.info(String.format("The following images will be searched %s", listFiles.length));

		// Loop images in the folder
		for (File f : listFiles) {

			if (f.isFile()) {
				try {

					// Create the IFalconImage
					IFalconImage fImg = falcon.getImage(f);

					FalconImageOptions options = fImg.getOptions();
					options.setFile(f);
					options.setDescription(f.getName());
					options.setColorTolerance(.05f);
					options.setNoiseTolerance(0.0f);

					fImg.setOptions(options);

					imagesToSearch.add(fImg);

					// Send each image to the log
					falcon.sendImage(fImg.getImage(), fImg.getDescription());

				} catch (IOException e) {
					server.warn(e.getMessage(), e);
				}
			}
		}
	}


	/**
	 * Tries to find an image into the larger one by increasing the color tolerance
	 * for the image in case it is not found.
	 * 
	 * @param imageList
	 *            list of images to search
	 * @return an instance of {@link ImageResult} to get the result of the image
	 *         search
	 */
	public ImageResult findImage(List<IFalconImage> imageList)  throws Exception{

		double colorTolerance = 0.0d;
		boolean found = false;
		IFalconImage imageFound = null;

		// Increase tolerance if the image is not found
		while (colorTolerance < 0.8d) {

			// Set the new tolerance for each image to search
			for (IFalconImage i : imageList) {

				i.getOptions().setColorTolerance((float)colorTolerance);

				found = i.search().found();
				imageFound = i;

				if (found) {
					break;
				}
			}

			server.info(String.format("Searching image with tolerance: %.2f", colorTolerance));

			if (!found) {
				// Increase tolerance if image is not found
				colorTolerance += .70d;
				continue;
			}

			server.info(String.format(
					"Image found with tolerance: %.2f in %d,%d",
					colorTolerance,
					(int)imageFound.getRectangle().getCenterX(),
					(int)imageFound.getRectangle().getCenterY()));

			break;
		}

		// The result of the image search
		ImageResult imageResult = new ImageResult();
		imageResult.setFound(found);
		imageResult.setImageFound(imageFound);

		return imageResult;
	}

	/**
	 * Returns a falcon image from specified path
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public IFalconImage getImage(Path path) throws IOException {

		IFalconImage image = images.get(path.toString());

		if (image == null) {

			image = falcon.getImage(new FalconImageOptions().description(path.getFileName().toString())
					.file(path.toFile()).colorTolerance(.05f));

			images.put(path.toString(), image);
		}

		image.setPointsWhereFound(new ArrayList<>());

		return image;
	}


	/**
	 * Sends an image to the log if it's found.
	 * 
	 * @param imageResult
	 *            {@link ImageResult} instance with the information about the image
	 *            search.
	 * @throws Exception
	 */
	public void sendImage(ImageResult imageResult) throws Exception {

		BufferedImage bi = imageResult.getImageFound().getImage();
		falcon.sendImage(bi, "Found");

	}



	public IFalcon getFalcon() {
		return falcon;
	}
}
