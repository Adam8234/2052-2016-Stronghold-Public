package com.first.team2052.stronghold.subsystems;

import com.first.team2052.lib.Loopable;
import com.first.team2052.stronghold.Constants;
import com.first.team2052.stronghold.Util;
import com.first.team2052.stronghold.subsystems.controllers.FriesBangBangController;

import edu.wpi.first.wpilibj.CANTalon;

public class CatAManipulator implements Loopable {
	private CANTalon tal;
	FriesBangBangController controller;

	public CatAManipulator() {
		tal = new CANTalon(Constants.kFriesMotorId);
	}

	public void setOpenLoop(double axisValue) {
		controller = null;
		Util.limit(axisValue, Constants.kCatAManualMaxSpeed);
		tal.set(axisValue);
	}
	
	public double getPosision(){
		return 0.0;//potSensor.getAverageVoltage();
	}

	@Override
	public void update() {
		if (controller == null) {
			return;
		}
		
		tal.set(controller.calculate(getPosision()));
	}
}
