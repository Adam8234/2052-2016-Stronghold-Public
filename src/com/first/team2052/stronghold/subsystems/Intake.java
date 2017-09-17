package com.first.team2052.stronghold.subsystems;

import com.first.team2052.stronghold.Constants;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;

public class Intake {
	CANTalon intakeTalon;
	Solenoid bottomIn, bottomOut, topIn, topOut;

	public Intake() {
		intakeTalon = new CANTalon(Constants.kIntakeMotorId);

		bottomOut = new Solenoid(4);
		bottomIn = new Solenoid(5);
		topOut = new Solenoid(3);
		topIn = new Solenoid(2);
	}

	private void setTop(boolean out) {
		topOut.set(out);
		topIn.set(!out);
	}

	private void setBottom(boolean out) {
		bottomOut.set(out);
		bottomIn.set(!out);
	}

	public boolean getBottom() {
		return bottomOut.get();
	}

	public boolean getTop() {
		return topOut.get();
	}

	public void setIntakeSpeed(double intakeSpeed) {
		intakeTalon.set(intakeSpeed);
	}

	public void shootPosition() {
		setBottom(false);
		setTop(true);
	}
	
	public void defaultPosition(){
		setBottom(true);
		setTop(true);
	}
	
	public void pickupPosition(){
		setBottom(false);
		setTop(false);
	}
	
	
}