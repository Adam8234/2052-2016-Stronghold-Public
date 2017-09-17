package com.first.team2052.stronghold.auto.modes.crossing.shoot;

import com.first.team2052.stronghold.auto.AutoModeEndedException;

public class AutoCrossShovelFriesHighGoal extends AutoCrossShootBase {
	@Override
	public void crossDefense() throws AutoModeEndedException {
		// drive up to french fries
		driveStraightDistance(61 / 12, 4);
		delay(2.0);
		// add code to manipulate french fries down
		driveStraightDistance(7, 2);
		// add code to manipulate french fries up
		// driveStraightDistance(7, 6);
	}

}
