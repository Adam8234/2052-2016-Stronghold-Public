package com.first.team2052.stronghold.auto.modes;

import com.first.team2052.stronghold.Robot;
import com.first.team2052.stronghold.auto.AutoMode;
import com.first.team2052.stronghold.auto.AutoModeEndedException;

public class TestAutoMode extends AutoMode {
	@Override
	protected void init() throws AutoModeEndedException {
		drivePath(Robot.getPaths().getPath("LowBarHighGoalPath"), true);
		waitUntilPathFinishes();
		System.out.println("Test auto finished");
	}
}
