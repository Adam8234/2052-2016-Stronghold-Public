package com.first.team2052.stronghold.auto.modes.crossing;

import com.first.team2052.stronghold.auto.AutoMode;
import com.first.team2052.stronghold.auto.AutoModeEndedException;

public class AutoCrossRockwall extends AutoMode {

	protected void init() throws AutoModeEndedException {
		driveStraightDistance(16, 5);
	}
}