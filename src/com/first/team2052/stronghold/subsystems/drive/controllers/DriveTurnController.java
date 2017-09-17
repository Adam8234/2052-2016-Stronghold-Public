package com.first.team2052.stronghold.subsystems.drive.controllers;

import com.first.team2052.lib.trajectory.TrajectoryFollower;
import com.first.team2052.lib.trajectory.TrajectoryFollower.TrajectoryConfig;
import com.first.team2052.lib.trajectory.TrajectoryFollower.TrajectorySetpoint;
import com.first.team2052.stronghold.Constants;
import com.first.team2052.stronghold.Util;
import com.first.team2052.stronghold.subsystems.drive.DriveTrain;

public class DriveTurnController extends DriveController {
	double angle;
	private TrajectoryFollower trajectoryFollower;
	

	public DriveTurnController(DriveTrain driveTrain, double angle) {
		super(driveTrain);
		this.angle = angle;
	
		TrajectoryConfig config = new TrajectoryConfig();
		config.dt = Constants.kControlLoopPeriod;
		config.max_acc = Constants.kDriveTurnMaxAcceleration;
		config.max_vel = Constants.kDriveTurnMaxVelocity;

		trajectoryFollower = new TrajectoryFollower();
		trajectoryFollower.configure(Constants.kDriveTurnKp, Constants.kDriveTurnKi, Constants.kDriveTurnKd,
				Constants.kDriveTurnKv, Constants.kDriveTurnKa, config);

		TrajectorySetpoint currentState = new TrajectorySetpoint();
		currentState.pos = Util.toRadians(driveTrain.getGyroAngle());
		currentState.vel = Util.toRadians(driveTrain.getAverageVelocity());

		trajectoryFollower.setGoal(currentState, Util.toRadians(driveTrain.getGyroAngle()) +  Util.toRadians(angle));
	}

	@Override
	public DriveSignal calculate() {
		return DriveSignal.arcadeSignal(0, -trajectoryFollower.calculate(driveTrain.getGyroAngle() * Math.PI / 180));
	}

	@Override
	public boolean isFinished() {
		return true;
	}

}
