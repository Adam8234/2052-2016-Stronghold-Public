package com.first.team2052.stronghold.subsystems;

import com.first.team2052.lib.Loopable;
import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.VisionException;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class DriverCameras implements Loopable {
	private boolean wantRearCam = false;
	private USBCamera rearCam;
	private CameraServer camServer;
	private Image img;

	public DriverCameras() {
		img = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		camServer = CameraServer.getInstance();

		try {
			rearCam = new USBCamera("cam0");
			rearCam.openCamera();
			rearCam.startCapture();
		} catch (VisionException exception) {
			rearCam = null;
		}

		camServer.setQuality(50);
	}

	public void update() {
		if (rearCam == null)
			return;
		if (wantRearCam) {
			rearCam.startCapture();
			rearCam.getImage(img);
			camServer.setImage(img);
		} else {
			rearCam.stopCapture();
			camServer.setImage(img);
		}
	}

	/**
	 * 
	 * @param which
	 *            true sets to rear camera; false sets to front camera
	 */
	public void setCurrentCam(boolean which) {
		wantRearCam = which;
	}
}