package com.first.team2052.stronghold.auto.modes.crossing.shoot;

import com.first.team2052.stronghold.auto.AutoModeEndedException;

public class AutoCrossMoatHighGoal extends AutoCrossShootBase {
	@Override
	public void crossDefense() throws AutoModeEndedException{
		driveStraightDistance(15, 4);
	}
}
