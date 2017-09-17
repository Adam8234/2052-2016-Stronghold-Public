package com.first.team2052.stronghold.auto;

public class AutoModeRunner {
	Thread autoThread;
	AutoMode autoMode;

	public void start() {
		if (autoMode == null)
			return;
		autoThread = new Thread(new Runnable() {
			@Override
			public void run() {
				autoMode.start();
			}
		});
		autoThread.start();
	}

	public void setAutoMode(AutoMode autoMode) {
		this.autoMode = autoMode;
	}

	public void stop() {
		if (autoMode != null)
			autoMode.stop();
		autoThread = null;
	}
}
