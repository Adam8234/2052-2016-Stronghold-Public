package com.first.team2052.lib;

import edu.wpi.first.wpilibj.Notifier;

/**
 * Possibly fix looping code will always be called at desired period instead of the java timer looper this is the WPILib implementation
 */
public class NotifierLooper extends Looper {

	private double period;
	protected Loopable loopable;
	protected String m_name;

	Notifier m_notifier;

	Runnable m_handler = new Runnable() {
		@Override
		public void run() {
			NotifierLooper.this.update();
		}
	};

	public NotifierLooper(String name, Loopable loopable, double period) {
		super(name, loopable, period);
		this.period = period;
		this.loopable = loopable;
		this.m_name = name;
		this.m_notifier = new Notifier(m_handler);
	}

	@Override
	public void start() {
		m_notifier.startPeriodic(period);
	}

	@Override
	public void stop() {
		m_notifier.stop();
	}

	protected void update() {
		loopable.update();
	}
}
