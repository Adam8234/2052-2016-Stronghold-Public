package com.first.team2052.stronghold.auto.modes.crossing.shoot;

import com.first.team2052.stronghold.Robot;
import com.first.team2052.stronghold.auto.AutoMode;
import com.first.team2052.stronghold.auto.AutoModeEndedException;
import com.first.team2052.stronghold.auto.AutoModes;

public abstract class AutoCrossShootBase extends AutoMode {
	private void driveToCenter(int startPos) throws AutoModeEndedException {
		String path = null;
		boolean right = false;
		boolean backwards = true;
		switch (startPos) {
		case 1:
			// Ignore pls for now
			return;
		case 2:
			path = "Position2ToCenterPath";
			right = true;
			break;
		case 3:
			path = "Position3ToCenterPath";
			right = true;
			break;
		case 4:
			path = "Position4ToCenterPath";
			backwards = false;
			right = false;
			break;
		case 5:
			path = "Position5ToCenterPath";
			right = false;
			break;
		case 6:
			path = "Position5bToCenterPath";
			backwards = false;
			right = false;
		}
		getDriveTrain().disableController();
		getDriveTrain().resetEncoders();
		getDriveTrain().resetGyro();

		delay(.25);

		drivePath(Robot.getPaths().getPath(path), right);
		waitUntilPathFinishes();

		if (backwards) {
			driveStraightDistance(-1.5, 3);
		}

		lineUpVision();
		delay(1.0);
		lineUpVision();
		delay(1.0);
		shootCatapult();
	}

	@Override
	protected void init() throws AutoModeEndedException {
		crossDefense();

		driveToCenter(getSelectedAutoPos());
	}

	public abstract void crossDefense() throws AutoModeEndedException;

	protected int getSelectedAutoPos() {
		return AutoModes.getAutoPosition();
	}
}
