package com.first.team2052.stronghold.auto.actions;

import com.first.team2052.stronghold.auto.AutoModeBase.Action;

public class WaitUntilAngleAction extends Action {
	double curPos, goalPos;
	double error; 
	

	public WaitUntilAngleAction(double angle, double error) {
		goalPos = driveTrain.getGyroAngle() + angle;
		this.error = error; 
	}

	@Override
	public void done() {
	}

	@Override
	public boolean isFinished() {
		return curPos <= goalPos + error 
				&& curPos >= goalPos -error;
	}

	@Override
	public void start() {
	}

	@Override
	public void update() {
		curPos = driveTrain.getGyroAngle();
	}
}
