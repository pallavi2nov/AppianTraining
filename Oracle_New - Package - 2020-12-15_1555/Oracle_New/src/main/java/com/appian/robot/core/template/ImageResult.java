package com.appian.robot.core.template;

import com.novayre.jidoka.falcon.api.IFalconImage;


/**
 * Class Image Result
 */
public class ImageResult {
	
	
	/**
	 * Indicates if the image has been found
	 */
	private boolean found;
	
	/**
	 * Instance of Falcon Image
	 */
	private IFalconImage imageFound;
	

	public boolean isFound() {
		return found;
	}

	public void setFound(boolean found) {
		this.found = found;
	}

	public IFalconImage getImageFound() {
		return imageFound;
	}

	public void setImageFound(IFalconImage imageFound) {
		this.imageFound = imageFound;
	}

	
}
