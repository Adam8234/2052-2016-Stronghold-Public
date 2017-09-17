package com.first.team2052.lib;

import java.util.Vector;

public class MultiLooper implements Loopable {
	private Vector<Loopable> loopables = new Vector<Loopable>();
	private Looper looper;

	public MultiLooper(String name, double period, boolean notifier) {
		if (notifier) {
			looper = new NotifierLooper(name, this, period);
		} else {
			looper = new Looper(name, this, period);
		}
	}

	public void addLoopable(Loopable c) {
		loopables.addElement(c);
	}

	public void start() {
		looper.start();
	}

	public void stop() {
		looper.stop();
	}

	public void update() {
		int i;
		for (i = 0; i < loopables.size(); ++i) {
			Loopable c = loopables.elementAt(i);
			if (c != null) {
				c.update();
			}
		}
	}
}