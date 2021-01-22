package com.appian.robot.core.template;

import com.novayre.jidoka.client.api.*;
import com.novayre.jidoka.client.api.execution.IUsernamePassword;
import com.novayre.jidoka.falcon.api.IFalcon;
import com.novayre.jidoka.falcon.api.IFalconImage;
import org.apache.commons.lang.StringUtils;

import com.novayre.jidoka.browser.api.EBrowsers;
import com.novayre.jidoka.browser.api.IWebBrowserSupport;
import com.novayre.jidoka.client.api.annotations.Robot;
import com.novayre.jidoka.client.api.multios.IClient;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;




/**
 * Browser robot template. 
 */
@Robot
public class RobotBrowserTemplate implements IRobot {
	private static final String HOME_URL = "https://rpo-english--rnt7.frontlinesvc.com/cgi-bin/rpo_english.cfg/php/admin/launch.php";


	/** The JidokaServer instance. */
	private IJidokaServer<?> server;

	/** The IClient module. */
	private IClient client;

	/** WebBrowser module */
	private IWebBrowserSupport browser;

	/** Browser type parameter **/
	private String browserType = null;

	private List<IFalconImage> imgHelp;

	private List<IFalconImage> imgException;

	private List<IFalconImage> imgIncidents;

	private List<IFalconImage> imgForm;

	private List<IFalconImage> imgReference;

	private ImageResult imaageR  = null;

	private FalconManager falconManager;

	private IUsernamePassword oracleCredential;

	private IImageResource image;

	private IWaitFor waitFor;

	private IFalconImage[] popupWin ;

	/** The falcon. */
	private IFalcon falcon;


	/**
	 * Action "startUp".
	 * <p>
	 * This method is overrriden to initialize the Appian RPA modules instances.
	 */
	@Override
	public boolean startUp() throws Exception {

		server = (IJidokaServer< ? >) JidokaFactory.getServer();

		client = IClient.getInstance(this);

		browser = IWebBrowserSupport.getInstance(this, client);

		falcon = IFalcon.getInstance(this, client);

		falconManager = new FalconManager(this, falcon);

		waitFor = client.getWaitFor(this);

		//oracleCredential = server.getCredentials("ORACLE").get(0);



		return IRobot.super.startUp();

	}

	/*handling proxy concerns*/

	public void proxySetup() {

		DesiredCapabilities caps = DesiredCapabilities.internetExplorer();


	}

	/**
	 * Action "start".
	 */
	public void start() {
		server.setNumberOfItems(1);
		server.info("This is to show all of you we are deploying this code to Appian cloud env...");

//		imgHelp = new ArrayList<IFalconImage>();
//
//		imgException = new ArrayList<IFalconImage>();
//
//		imgIncidents = new ArrayList<IFalconImage>();
//
//		imgForm = new ArrayList<IFalconImage>();
//
//		imgReference = new ArrayList<IFalconImage>();
//
//		falconManager.initImagesToSearch(imgHelp, "oracle");
//
//		falconManager.initImagesToSearch(imgException, "exception");
//		falconManager.initImagesToSearch(imgIncidents, "incidents");
//		falconManager.initImagesToSearch(imgForm, "form");
//		falconManager.initImagesToSearch(imgReference, "reference");

	}


	public void openBrowser() throws Exception  {


		browser.setBrowserType(EBrowsers.INTERNET_EXPLORER);
//		browserType = server.getParameters().get("Browser");

		// Select browser type
		//Sometimes its create issues with default browser setup.
//		if (StringUtils.isBlank(browserType)) {
//			server.info("Browser parameter not present. Using the default browser IE");
//			browser.setBrowserType(EBrowsers.INTERNET_EXPLORER);
//			browserType = EBrowsers.INTERNET_EXPLORER.name();
//		} else {
//			EBrowsers selectedBrowser = EBrowsers.valueOf(browserType);
//			browserType = selectedBrowser.name();
//			browser.setBrowserType(selectedBrowser);
//			server.info("Browser selected: " + selectedBrowser.name());
//		}

		// Set timeout to 60 seconds
		browser.setTimeoutSeconds(60);

		// Init the browser module
		browser.initBrowser();

		server.setCurrentItem(1, HOME_URL);




		//This command is uses to make visible in the desktop the page (IExplore issue)
//		if (EBrowsers.INTERNET_EXPLORER.name().equals(browserType)) {
//			client.clickOnCenter();
//			client.pause(3000);
//		}

	}

	/**
	 * Navigate to Web Page
	 *
	 * @throws Exception
	 */
	public void navigateToWeb() throws Exception  {

//		server.setCurrentItem(1, HOME_URL);
//
//		// Navegate to HOME_URL address
		browser.navigate(HOME_URL);
//
//		// we save the screenshot, it can be viewed in robot execution trace page on the console
//		server.sendScreen("Screen after load page: " + HOME_URL);
//
//		server.setCurrentItemResultToOK("Success");
	}

	/**
	 *
	 * @throws Exception
	 */
	public void loginOracle() throws Exception{

		popupWin =  new IFalconImage[imgHelp.size()] ;

		popupWin = imgHelp.toArray(popupWin);

		image = waitFor.image(3, false, popupWin );

		if (image !=null && image.satisfied()) {

			client.keyboard().tab().pause();

			client.keyboard().type(oracleCredential.getUsername()).tab();
			client.pause();
			client.keyboard().type(oracleCredential.getPassword());
			client.pause();
			client.keyboard().tab(3).enter();

		}else {
			throw new Exception("Image not found");
		}
	}


	/**
	 *
	 * @throws Exception
	 */
	public void displyaException() throws Exception{

		popupWin =  new IFalconImage[imgException.size()] ;

		popupWin = imgException.toArray(popupWin);

		client.activateWindow("Navigation");

		image = waitFor.image(3, false, popupWin );


		if (image !=null && image.satisfied()) {

			image.mouseMoveToCenter();;
			image.mouseDoubleLeftClick();
			client.pause();
			client.keyboard().down();

			popupWin =  new IFalconImage[imgIncidents.size()];
			popupWin = imgIncidents.toArray(popupWin);

			image = waitFor.image(3, false, popupWin );

			if (image !=null && image.satisfied()) {


				image.mouseMoveToCenter();;
				image.mouseDoubleLeftClick();
				client.pause();

				if (client.waitFor(this).window(5, false, false, "Search") != null) {
					client.activateWindow("Search");
					client.getKeyboard().tab(20).pause();
					client.keyboard().enter();
					client.pause(3000);
					Point AMQWin = new Point(316, 308); // Click on #MU
					client.mouseLeftClick(AMQWin);
					client.keyboard().enter();

				}
			}
		}else {
			throw new Exception("Image not found");
		}

	}

	public void getValues() throws Exception{


		server.info("Make a click on #MU Field");

		popupWin =  new IFalconImage[imgForm.size()];
		popupWin = imgForm.toArray(popupWin);

		image = waitFor.image(5, false, popupWin );

		if (image !=null && image.satisfied()) {

			image.mouseMoveToCenter();
			image.click();
			server.info(client.getCursorInfo().getPosition().getX() + " " + client.getCursorInfo().getPosition().getY());

			client.getKeyboard().tab(12).pause();
			String dataNote = client.cleanCopyAndGet();
			server.info("Value:" + dataNote);

			popupWin =  new IFalconImage[imgReference.size()];

			popupWin = imgReference.toArray(popupWin);

			image = waitFor.image(5, false, popupWin );

			if (image !=null && image.satisfied()) {

				image.mouseMoveToCenter();
				image.click();
				server.info(client.getCursorInfo().getPosition().getX() + " " + client.getCursorInfo().getPosition().getY());

				client.getKeyboard().control("a");

				dataNote = client.cleanCopyAndGet();

				server.info("Value:" + dataNote);
			}
		}

	}

	/**
	 * @see com.novayre.jidoka.client.api.IRobot#cleanUp()
	 */
	@Override
	public String[] cleanUp() throws Exception {

		browserCleanUp();
		return null;
	}

	/**
	 * Close the browser.
	 */
	private void browserCleanUp() {

		// If the browser was initialized, close it
		if (browser != null) {
			try {
				browser.close();
				browser = null;

			} catch (Exception e) { // NOPMD
				// Ignore exception
			}
		}

		try {

			if(browserType != null) {

				switch (EBrowsers.valueOf(browserType)) {

					case CHROME:
						client.killAllProcesses("chromedriver.exe", 1000);
						break;

					case INTERNET_EXPLORER:
						client.killAllProcesses("IEDriverServer.exe", 1000);
						break;

					case FIREFOX:
						client.killAllProcesses("geckodriver.exe", 1000);
						break;

					default:
						break;

				}
			}

		} catch (Exception e) { // NOPMD
			// Ignore exception
		}

	}


	/**
	 * Last action of the robot.
	 */
	public void end()  {
		server.info("End process");
	}
	
}
