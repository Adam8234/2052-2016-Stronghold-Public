package com.first.team2052.stronghold.auto.actions;

import com.first.team2052.stronghold.Constants;
import com.first.team2052.stronghold.auto.AutoModeBase.Action;

public class WaitUntilDistanceAction extends Action {
	double curPos, goalPos;

	public WaitUntilDistanceAction(double distance) {
		goalPos = distance + driveTrain.getAverageDistance();
	}

	@Override
	public void done() {
	}

	@Override
	public boolean isFinished() {
		return curPos <= goalPos + Constants.kDriveStraightErrorAuto
				&& curPos >= goalPos - Constants.kDriveStraightErrorAuto;
	}

	@Override
	public void start() {
	}

	@Override
	public void update() {
		curPos = driveTrain.getAverageDistance();
	}
}
