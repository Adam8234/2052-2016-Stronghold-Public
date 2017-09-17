package com.first.team2052.stronghold.auto.modes.crossing.shoot;

import com.first.team2052.stronghold.Robot;
import com.first.team2052.stronghold.auto.AutoMode;
import com.first.team2052.stronghold.auto.AutoModeEndedException;

public class AutoLowBarHighGoal extends AutoMode {

	protected void init() throws AutoModeEndedException {
		drivePath(Robot.getPaths().getPath("LowBarHighGoalPath"), true);
		waitUntilPathFinishes();
		lineUpVision();
		delay(1.0);
		lineUpVision();
		delay(1.0);
		shootCatapult();
	}
}