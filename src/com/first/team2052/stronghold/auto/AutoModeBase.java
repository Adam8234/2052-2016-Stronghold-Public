package com.first.team2052.stronghold.auto;

import com.first.team2052.stronghold.Robot;
import com.first.team2052.stronghold.subsystems.drive.DriveTrain;

import edu.wpi.first.wpilibj.Timer;

public class AutoModeBase {
	public static abstract class Action {
		protected DriveTrain driveTrain = Robot.getDriveTrain();

		public abstract void done();

		public abstract boolean isFinished();

		public abstract void start();

		public abstract void update();
	}

	private boolean running = false;
	private Timer timer = new Timer();

	public void delay(double seconds) {
		if (!running)
			return;
		Timer.delay(seconds);
	}

	public void errorStop(String message) {
		System.out.println(message);
		stop();
	}

	public double getAutoTime() {
		return timer.get();
	}

	protected void init() throws AutoModeEndedException {
	}

	public boolean isRunning() {
		return running;
	}

	public boolean isRunningWithThrow() throws AutoModeEndedException {
		if (!isRunning()) {
			throw new AutoModeEndedException();
		}
		return isRunning();
	}

	protected void runAction(Action action) throws AutoModeEndedException {
		isRunningWithThrow();
		action.start();
		while (!action.isFinished() && isRunningWithThrow()) {
			action.update();
			try {
				Thread.sleep((long) ((1.0 / 50.0) * 1000.0));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		action.done();
		Timer.delay(0.25);
	}

	public void start() {
		running = true;
		timer.reset();
		timer.start();
		try {
			init();
		} catch (AutoModeEndedException e) {
			System.out.println("Auto Ended Early");
		}
	}

	public void stop() {
		running = false;
		timer.stop();
	}
}
