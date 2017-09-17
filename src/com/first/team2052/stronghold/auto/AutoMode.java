package com.first.team2052.stronghold.auto;

import com.first.team2052.lib.trajectory.Path;
import com.first.team2052.stronghold.Constants;
import com.first.team2052.stronghold.Robot;
import com.first.team2052.stronghold.VisionProcessor;
import com.first.team2052.stronghold.auto.actions.WaitUntilAngleAction;
import com.first.team2052.stronghold.auto.actions.WaitUntilDistanceAction;
import com.first.team2052.stronghold.auto.actions.WaitUntilPathFinished;
import com.first.team2052.stronghold.subsystems.Shooter;
import com.first.team2052.stronghold.subsystems.drive.DriveTrain;
import com.google.common.base.Optional;

public abstract class AutoMode extends AutoModeBase {
	private Shooter shooter = Robot.getShooter();
	private VisionProcessor vision = Robot.getVision();
	private DriveTrain driveTrain = Robot.getDriveTrain();

	private Shooter getShooter() {
		return shooter;
	}

	protected DriveTrain getDriveTrain() {
		return driveTrain;
	}

	protected VisionProcessor getVision() {
		return vision;
	}

	public void driveStraightDistance(double distanceFeet, double maxVelFeet) throws AutoModeEndedException {
		isRunningWithThrow();
		getDriveTrain().setDistanceTrajectory(distanceFeet * 12, maxVelFeet * 12);
		waitUntilDistance(distanceFeet * 12);
	}

	public void lineUpVision() throws AutoModeEndedException {
		isRunningWithThrow();
		getDriveTrain().setTurnTrajectory(getVision().getXAngleFromCenter());
	}

	public void driveStraightDistance(double distance) throws AutoModeEndedException {
		driveStraightDistance(distance, Constants.kDriveMaxVelocity / 12);
	}

	public void turnToAngle(double angle) throws AutoModeEndedException {
		getDriveTrain().setTurnTrajectory(angle);
		waitUntilAngle(angle);
	}

	public void waitUntilAngle(double angle) throws AutoModeEndedException {
		runAction(new WaitUntilAngleAction(angle, 1.0));
	}

	public void waitUntilPathFinishes() throws AutoModeEndedException {
		runAction(new WaitUntilPathFinished());
	}

	public void waitUntilAngle(double angle, double error) throws AutoModeEndedException {
		runAction(new WaitUntilAngleAction(angle, error));
	}

	public void waitUntilDistance(double distance) throws AutoModeEndedException {
		runAction(new WaitUntilDistanceAction(distance));
	}

	public void drivePath(Optional<Path> path) throws AutoModeEndedException {
		drivePath(path, false);
	}
	
	public void drivePath(Optional<Path> path, boolean right) throws AutoModeEndedException {
		isRunningWithThrow();
		if (!path.isPresent()) {
			errorStop("Path cannot be null. To avoid running people over, auto will stop ;)");
			return;
		}
		Path path_ = path.get();
		if(right){
			path_.goRight();
		} else {
			path_.goLeft();
		}
		getDriveTrain().drivePathTrajectory(path_);
	}

	public void shootCatapult() throws AutoModeEndedException {
		isRunningWithThrow();
		getShooter().wantShoot = true;
	}
}
