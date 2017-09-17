package com.first.team2052.stronghold.subsystems.drive.controllers;

import com.first.team2052.lib.trajectory.LegacyTrajectoryFollower;
import com.first.team2052.lib.trajectory.Path;
import com.first.team2052.stronghold.Constants;
import com.first.team2052.stronghold.Util;
import com.first.team2052.stronghold.subsystems.drive.DriveTrain;

public class DrivePathController extends DriveController {
	private LegacyTrajectoryFollower rightPath = new LegacyTrajectoryFollower("right");
	private LegacyTrajectoryFollower leftPath = new LegacyTrajectoryFollower("left");

	public DrivePathController(DriveTrain driveTrain, Path path) {
		super(driveTrain);

		rightPath.configure(Constants.kDriveStraightKp, Constants.kDriveStraightKd, Constants.kDriveStraightKv, Constants.kDriveStraightKa);
		leftPath.configure(Constants.kDriveStraightKp, Constants.kDriveStraightKd, Constants.kDriveStraightKv, Constants.kDriveStraightKa);

		rightPath.setTrajectory(path.getRightWheelTrajectory());
		leftPath.setTrajectory(path.getLeftWheelTrajectory());
	}

	@Override
	public DriveSignal calculate() {
		if (isFinished()) {
			return new DriveSignal(0.0, 0.0);
		}
		// Calculate the wheel speeds for the error and current segment
		double rightSpeed = -rightPath.calculate(driveTrain.getRightDistance());
		double leftSpeed = -leftPath.calculate(driveTrain.getLeftDistance());

		// Get a either side of the path heading direction
		double goalHeading = leftPath.getHeading();
		/*
		 * The go left case is default, so if we want it to go right with a
		 * positive angle, it'll flip the other way, so we make the angle
		 * negative
		 */
		double actualHeading = Util.toRadians(-driveTrain.getGyroAngle());

		// Get the turn error and apply the turn gain
		double angleDiffRad = Util.getDifferenceInAngleRadians(actualHeading, goalHeading);
		double angleDiff = Math.toDegrees(angleDiffRad);
		double turn = Constants.kDrivePathkTurn * angleDiff;

		return new DriveSignal(leftSpeed + turn, rightSpeed - turn);
	}

	@Override
	public boolean isFinished() {
		return leftPath.isFinishedTrajectory();
	}

}
