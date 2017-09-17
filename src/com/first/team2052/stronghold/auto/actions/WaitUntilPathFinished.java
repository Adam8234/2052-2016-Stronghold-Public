package com.first.team2052.stronghold.auto.actions;

import com.first.team2052.stronghold.auto.AutoModeBase.Action;
import com.first.team2052.stronghold.subsystems.drive.controllers.DriveController;
import com.first.team2052.stronghold.subsystems.drive.controllers.DrivePathController;

public class WaitUntilPathFinished extends Action {
	@Override
	public void done() {
	}

	@Override
	public boolean isFinished() {
		DriveController controller = driveTrain.getController();
		if (controller != null) {
			if (controller instanceof DrivePathController) {
				return controller.isFinished();
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public void start() {
	}

	@Override
	public void update() {
	}
}
