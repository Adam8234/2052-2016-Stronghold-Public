package com.first.team2052.stronghold.auto.modes;

import com.first.team2052.stronghold.auto.AutoMode;
import com.first.team2052.stronghold.auto.AutoModeEndedException;

public class AutoReachOuterWorks extends AutoMode {

	protected void init() throws AutoModeEndedException {
		driveStraightDistance(6, 3);
	}
}
