package com.first.team2052.stronghold.auto.modes.crossing.shoot;

import com.first.team2052.stronghold.auto.AutoModeEndedException;

public class AutoCrossPortcullisHighGoal extends AutoCrossShootBase {
	@Override
	public void crossDefense() throws AutoModeEndedException {
		// drive up to portcullis
		driveStraightDistance(5, 4);

		// add code to manipulate portcullis down
		driveStraightDistance(1, 4);
		// add code to manipulate portcullis up
	}
}
