package com.first.team2052.stronghold.subsystems.drive.controllers;

import com.first.team2052.lib.trajectory.TrajectoryFollower;
import com.first.team2052.lib.trajectory.TrajectoryFollower.TrajectoryConfig;
import com.first.team2052.lib.trajectory.TrajectoryFollower.TrajectorySetpoint;
import com.first.team2052.stronghold.Constants;
import com.first.team2052.stronghold.subsystems.drive.DriveTrain;

public class DriveStraightController extends DriveController {
	private TrajectoryFollower trajectoryFollower;
	private double startAngle;

	public DriveStraightController(DriveTrain driveTrain, double max_vel, double distance) {
		super(driveTrain);
		startAngle = driveTrain.getGyroAngle();

		TrajectoryConfig config = new TrajectoryConfig();
		config.dt = Constants.kControlLoopPeriod;
		config.max_acc = Constants.kDriveMaxAcceleration;
		config.max_vel = max_vel;
		trajectoryFollower = new TrajectoryFollower();

		trajectoryFollower.configure(Constants.kDriveStraightKp, Constants.kDriveStraightKi, Constants.kDriveStraightKd,
				Constants.kDriveStraightKv, Constants.kDriveStraightKa, config);

		TrajectorySetpoint currentState = new TrajectorySetpoint();
		currentState.pos = driveTrain.getAverageDistance();
		currentState.vel = driveTrain.getAverageVelocity();

		trajectoryFollower.setGoal(currentState, driveTrain.getAverageDistance() + distance);
	}

	public TrajectorySetpoint getTrajectorySetpoint() {
		return trajectoryFollower.getCurrentSetpoint();
	}

	public double getGoal() {
		return trajectoryFollower.getGoal();
	}

	@Override
	public DriveSignal calculate() {
		double output = -trajectoryFollower.calculate(driveTrain.getAverageDistance());
		return DriveSignal.arcadeSignal(output, (driveTrain.getGyroAngle() - startAngle) * 0.015);
	}

	@Override
	public boolean isFinished() {
		return trajectoryFollower.isFinishedTrajectory();
	}
}
