package com.first.team2052.stronghold.subsystems.drive;

import com.first.team2052.lib.ADIS16448_IMU;
import com.first.team2052.lib.Loopable;
import com.first.team2052.lib.TalonSRXEncoder;
import com.first.team2052.lib.trajectory.Path;
import com.first.team2052.stronghold.Constants;
import com.first.team2052.stronghold.subsystems.drive.controllers.DriveController;
import com.first.team2052.stronghold.subsystems.drive.controllers.DrivePathController;
import com.first.team2052.stronghold.subsystems.drive.controllers.DriveSignal;
import com.first.team2052.stronghold.subsystems.drive.controllers.DriveStraightController;
import com.first.team2052.stronghold.subsystems.drive.controllers.DriveTurnController;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

public class DriveTrain implements Loopable {
	// Id's for the Talon's on the CAN bus
	private DriveController controller;
	private ADIS16448_IMU driveGyro;
	double lastTime;
	private TalonSRXEncoder leftSrxEncoder, rightSrxEncoder;

	private final CANTalon right, left;

	public DriveTrain() {
		right = new CANTalon(Constants.kDriveRight1Id);
		CANTalon right_ = new CANTalon(Constants.kDriveRight2Id);
		left = new CANTalon(Constants.kDriveLeft1Id);
		CANTalon left_ = new CANTalon(Constants.kDriveLeft2Id);

		// Set the other Talon's to follower mode
		left_.changeControlMode(TalonControlMode.Follower);
		left_.set(left.getDeviceID());
		right_.changeControlMode(TalonControlMode.Follower);
		right_.set(right.getDeviceID());

		// Setup encoders
		leftSrxEncoder = new TalonSRXEncoder(left, true);
		leftSrxEncoder.setDistancePerPulse(Constants.kDriveEncoderDistancePerPulse);

		rightSrxEncoder = new TalonSRXEncoder(right, false);
		rightSrxEncoder.setDistancePerPulse(Constants.kDriveEncoderDistancePerPulse);

		left.setInverted(true);

		resetEncoders();
		
		driveGyro = new ADIS16448_IMU();
	}

	public void disableController() {
		controller = null;
		drive(0, 0);
	}

	private void drive(double tank, double turn) {
		right.set(tank - turn);
		left.set(tank + turn);
	}

	private void driveTank(double leftSpeed, double rightSpeed) {
		right.set(rightSpeed);
		left.set(leftSpeed);
	}

	public void driveTankOpenLoop(double leftSpeed, double rightSpeed) {
		controller = null;
		driveTank(leftSpeed, rightSpeed);
	}

	public void driveOpenLoop(double tank, double turn) {
		controller = null;
		drive(tank, turn);
	}

	public double getAverageDistance() {
		return (leftSrxEncoder.getDistance() + rightSrxEncoder.getDistance()) / 2;
	}

	public double getAverageVelocity() {
		return (leftSrxEncoder.getVelocity() + rightSrxEncoder.getVelocity()) / 2;
	}

	public double getRightDistance() {
		return rightSrxEncoder.getDistance();
	}

	public double getLeftDistance() {
		return leftSrxEncoder.getDistance();
	}

	public DriveController getController() {
		return controller;
	}

	public double getGyroAngle() {
		return driveGyro.getAngleZ();
	}

	public double getGyroVelocity() {
		return driveGyro.getRateZ();
	}

	public boolean isControllerRunning() {
		return controller != null && !controller.isFinished();
	}

	public void resetEncoders() {
		rightSrxEncoder.reset();
		leftSrxEncoder.reset();
	}

	public void resetGyro() {
		driveGyro.reset();
	}

	/**
	 * Starts a controller to drive to the desired distance
	 * 
	 * @param distanceInches
	 *            The distance in inches you want to travel
	 */
	public void setDistanceTrajectory(double distanceInches) {
		setDistanceTrajectory(distanceInches, Constants.kDriveMaxVelocity);
	}

	/**
	 * Starts a controller to drive to the desired distance and speed
	 * 
	 * @param distanceInches
	 *            The distance in inches you want to travel
	 * @param maxVelocity
	 *            The maximum speed in inches you want to go
	 */
	public void setDistanceTrajectory(double distanceInches, double maxVelocity) {
		controller = new DriveStraightController(this, maxVelocity, distanceInches);
	}

	/**
	 * Starts a controller to make the robot turn toward the desired angle
	 * 
	 * @param angle
	 *            the angle you want to turn to
	 */
	public void setTurnTrajectory(double angle) {
		controller = new DriveTurnController(this, angle);
	}

	/**
	 * Drives a certain path MAKE SURE YOU RESET THE GYRO AND/OR THE ENCODERS IF
	 * THE PATH STARTS AT 0,0 with a 0 heading
	 * 
	 * @param path
	 *            the path object you want to track
	 */
	public void drivePathTrajectory(Path path) {
		controller = new DrivePathController(this, path);
	}

	public ADIS16448_IMU getGyro() {
		return driveGyro;
	}

	@Override
	public void update() {
		if (controller == null) {
			return;
		}

		DriveSignal drive = controller.calculate();
		driveTank(drive.left, drive.right);
	}
}