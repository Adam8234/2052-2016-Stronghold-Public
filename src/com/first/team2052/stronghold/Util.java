package com.first.team2052.stronghold;

public class Util {
	public static double checkForDeadzone(double value, double deadZone) {
		if (Math.abs(value) < deadZone)
			return 0;
		return value;
	}
	
	public static double getDifferenceInAngleRadians(double from, double to) {
        return boundAngleNegPiToPiRadians(to - from);
    }
	
	public static double boundAngleNegPiToPiRadians(double angle) {
        while (angle >= Math.PI) {
            angle -= 2.0 * Math.PI;
        }
        while (angle < -Math.PI) {
            angle += 2.0 * Math.PI;
        }
        return angle;
    }


	public static double curve(double base, double power) {
		if (base == 0.0)
			return base;
		if (power == 0.0)
			return base;
		if (base > 0.0)
			return power * (Math.pow(base, 2));
		if (base < 0.0)
			return -(power * (Math.pow(base, 2)));
		return 0.0;
	}

	public static double toRadians(double angle) {
		return angle * Math.PI / 180;
	}

	public static double limit(double value, double limit) {
		if (Math.abs(value) > limit) {
			if (value < 0) {
				return -limit;
			} else if (value > limit) {
				return limit;
			}
		}
		return value;
	}
	
	public static double boundAngle0to2PiRadians(double angle) {
        // Naive algorithm
        while (angle >= 2.0 * Math.PI) {
            angle -= 2.0 * Math.PI;
        }
        while (angle < 0.0) {
            angle += 2.0 * Math.PI;
        }
        return angle;
    }

}
