package com.first.team2052.stronghold;

public class Constants {

	// Driver
	public static double kDriveDeadZone = 0.015;
	public static double kDriveSpeedCurveTank = 1.5;
	public static double kDriveSpeedCurveTurn = 0.4;

	// Trajectory Controller
	public static double kDriveMaxVelocity = 140.0;
	public static double kDriveMaxAcceleration = 107.0;
	public static double kDriveStraightKp = 0.025;
	public static double kDriveStraightKi = 0.0;
	public static double kDriveStraightKd = 0.0;
	public static double kDriveStraightKv = 1.0 / 142.0;
	public static double kDriveStraightKa = 0.0015;
	public static double kDrivePathkTurn = 0.03;

	// Actions
	public static double kDriveStraightErrorAuto = 2.0;
	public static double kDriveAngleErrorAuto = 1.0;

	// DriveTurnController Sloppy, but works. Needs more tuning
	public static double kDriveTurnMaxVelocity = Math.PI;
	public static double kDriveTurnMaxAcceleration = Math.PI;
	public static double kDriveTurnKp = 3.0;
	public static double kDriveTurnKi = 2.2;
	public static double kDriveTurnKd = 0.15;
	public static double kDriveTurnKv = 1 / 7.28; // 1 / 7.28
	public static double kDriveTurnKa = 0.0;

	// Drive Train
	public static double kDriveWheelDiameter = 7.5;
	public static int kDriveEncoderPulsePerRotation = 256;
	public static double kDriveSprocketRatio = 26.0 / 15.0;
	public static double kDriveEncoderDistancePerPulse = (kDriveWheelDiameter * Math.PI / kDriveEncoderPulsePerRotation)
			/ kDriveSprocketRatio;

	// Control Loop
	public static final double kControlLoopPeriod = 1.0 / 100.0;
	public static final double kShooterLoopPeriod = 1.0 / 100.0;

	// Ports
	public static int kDriveLeft1Id = 1;
	public static int kDriveLeft2Id = 2;
	public static int kDriveRight1Id = 3;
	public static int kDriveRight2Id = 4;
	public static int kIntakeMotorId = 5;
	public static int kFriesMotorId = 6;
	
	// Intake
	public static double kIntakePickupSpeed = 0.5;
	public static double kLowgoalShootingSpeed = -1.0;
	
	//Cat A
	public static double kCatAManualMaxSpeed = 0.5;
	
	//Vision
	public static int kVisionOffsetPixelCenter = -25;
	
	
}