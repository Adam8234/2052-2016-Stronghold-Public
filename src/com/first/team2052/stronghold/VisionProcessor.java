package com.first.team2052.stronghold;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionProcessor {
	private String networkTablePath = "GRIP/myContoursReport";
	private int xResolution = 320, yResolution = 240;
	private double xAngle = 47.0, yAngle = 45.0;
	private double xBasePX, yBasePX;
	private final double DEFAULT_HEIGHT = 83.75;
	private NetworkTable gripTable;

	public VisionProcessor(String address) {
		xBasePX = (xResolution / 2.0) / Math.tan(Math.toRadians(xAngle / 2.0));
		yBasePX = (yResolution / 2.0) / Math.tan(Math.toRadians(yAngle / 2.0));

		networkTablePath = address;
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("localhost");
		gripTable = NetworkTable.getTable(networkTablePath);
	}

	public VisionProcessor() {
		xBasePX = (xResolution / 2.0) / Math.tan(Math.toRadians(xAngle / 2.0));
		yBasePX = (yResolution / 2.0) / Math.tan(Math.toRadians(yAngle / 2.0));

		/*
		 * NetworkTable.setClientMode(); NetworkTable.setIPAddress("localhost");
		 */
		gripTable = NetworkTable.getTable(networkTablePath);
	}

	public VisionProcessor(String networkTablesPath, double xTheta, double yTheta, int xRes, int yRes) {
		networkTablePath = networkTablesPath;

		xBasePX = (xRes / 2.0) / Math.tan(Math.toRadians(xTheta / 2.0));
		yBasePX = (yRes / 2.0) / Math.tan(Math.toRadians(yTheta / 2.0));

		xAngle = xTheta;
		yAngle = yTheta;

		xResolution = xRes;
		yResolution = yRes;

		networkTablePath = networkTablesPath;

		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("localhost");
		gripTable = NetworkTable.getTable(networkTablePath);
	}

	double getXAngleFromCenter(int pixels) {
		int pxFromCenter = pixels - (xResolution / 2);
		return Math.toDegrees(Math.atan((pxFromCenter) / xBasePX));
	}

	double getYAngleFromCenter(int pixels) {
		int pxFromCenter = pixels - (yResolution / 2);
		return Math.toDegrees(Math.atan((pxFromCenter) / yBasePX));
	}

	public double getXAngleFromCenter() { // returns angle in degrees, or it returns
									// the x angle of view if something went
									// wrong
		double[] centers = gripTable.getNumberArray("centerX", new double[0]);
		if (centers.length == 1) {
			int pxFromCenter = ((xResolution / 2) + Constants.kVisionOffsetPixelCenter) - (int) centers[0];
			return -Math.toDegrees(Math.atan((pxFromCenter) / xBasePX));
		} else if (centers.length > 1) {
			double[] widths = gripTable.getNumberArray("width", new double[0]);
			double max = 0;
			int indexMax = -1;
			for (int i = 0; i < widths.length; i++) {
				if (widths[i] > max) {
					indexMax = i;
					max = widths[i];
				}
			}
			int pxFromCenter = ((xResolution / 2) + Constants.kVisionOffsetPixelCenter) - (int) centers[indexMax];
			return -Math.toDegrees(Math.atan((pxFromCenter) / xBasePX));
		} else {
			return 0.0;
		}
	}

	double getYAngleFromCenter() { // same as xAngleFromCenter but uses bottom
									// of contours instead of center
		double[] centers = gripTable.getNumberArray("centerY", new double[0]);
		double[] areas = gripTable.getNumberArray("area", new double[0]);
		double[] heights = gripTable.getNumberArray("height", new double[0]);
		if (centers.length == 1) {
			int pxFromCenter = ((int) centers[0] + (int) heights[0] / 2) - (yResolution / 2);
			System.out.println("pxFromCenter: " + pxFromCenter);
			double angle = -Math.toDegrees(Math.atan((pxFromCenter) / xBasePX));

			System.out.println("Angle finding succeded: " + (angle + 22.5));
			return -Math.toDegrees(Math.atan((pxFromCenter) / xBasePX));
		} else if (centers.length > 1) {
			double max = 0.0;
			int indexMax = -1;
			for (int i = 0; i < areas.length; i++) {
				if (areas[i] > max) {
					indexMax = i;
					max = areas[i];
				}
			}
			int pxFromCenter = (((int) centers[indexMax] + (int) heights[indexMax] / 2) - (yResolution / 2)) * -1;
			System.out.println("pxFromCenter: " + pxFromCenter);
			double angle = -Math.toDegrees(Math.atan((pxFromCenter) / xBasePX));
			;
			System.out.println("Angle finding succeeded: " + (angle + 22.5));
			return -Math.toDegrees(Math.atan((pxFromCenter) / xBasePX));
		} else {
			System.out.println("angle finding failed");
			return yAngle;
		}
	}

	double getDistanceFromHeight(int px /* almost certainly in y-dimension */, double height /* inches */) { // height
																												// from
																												// ground
																												// method
		return (height / Math.tan(Math.toRadians(getYAngleFromCenter(px) + (yAngle / 2))));
	}

	double getDistanceFromHeight(int px /* y-dim */) {
		return (DEFAULT_HEIGHT / Math.tan(Math.toRadians(getYAngleFromCenter(px) + (yAngle / 2))));
	}

	double getDistanceFromHeight() {
		return (DEFAULT_HEIGHT / Math.tan(Math.toRadians(getYAngleFromCenter() + (yAngle / 2))));
	}

	int getXResolution() {
		return xResolution;
	}

	int returnyResolution() {
		return yResolution;
	}

	double getXAngleOfView() {
		return xAngle;
	}

	double getYAngleOfView() {
		return yAngle;
	}

	String getNetworkTablePath() {
		return networkTablePath;
	}

	NetworkTable getGripTable() {
		return gripTable;
	}
}
