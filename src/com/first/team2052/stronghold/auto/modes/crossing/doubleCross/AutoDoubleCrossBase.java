package com.first.team2052.stronghold.auto.modes.crossing.doubleCross;

import com.first.team2052.stronghold.auto.AutoMode;
import com.first.team2052.stronghold.auto.AutoModeEndedException;

public class AutoDoubleCrossBase extends AutoMode {
	protected void init(double distanceForward, double distanceBack, double maxVelocity) throws AutoModeEndedException {
		driveStraightDistance(distanceForward, maxVelocity);
		turnToAngle(180);
		driveStraightDistance(distanceBack, maxVelocity);
		turnToAngle(180);
	}
}
