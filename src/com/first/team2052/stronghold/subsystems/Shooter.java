package com.first.team2052.stronghold.subsystems;

import com.first.team2052.lib.Loopable;
import com.first.team2052.stronghold.Constants;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter implements Loopable {
	private DigitalInput bottomReed, catapultReed;
	private Solenoid catapultSol1, catapultSol2, puncherSol;
	private Intake intake;
	private Timer timer = new Timer();
	private Timer safeStateTimer = new Timer();
	private AnalogInput pressureSensor = new AnalogInput(0);

	private ShooterState state = ShooterState.NORMAL;
	public boolean wantPickup, wantOutake, wantShoot;

	public Shooter() {
		// Catapult
		catapultSol1 = new Solenoid(0);
		catapultSol2 = new Solenoid(1);

		// Puncher
		puncherSol = new Solenoid(6);

		bottomReed = new DigitalInput(0);
		catapultReed = new DigitalInput(9);

		intake = new Intake();

		timer.start();
		safeStateTimer.start();
	}

	private void setCatapult(boolean fire) {
		catapultSol1.set(fire);
		catapultSol2.set(fire);
	}

	private void setPuncher(boolean out) {
		puncherSol.set(out);
	}

	public double returnPressure() {
		return ((50 * pressureSensor.getVoltage()) - 20);
	}

	public void update() {
		ShooterState newState = state;
		SmartDashboard.putNumber("Shooter Pressure", returnPressure());
		switch (state) {
		case NORMAL:
			intake.setIntakeSpeed(0.0);
			intake.defaultPosition();
			setCatapult(false);
			setPuncher(false);
			if (wantShoot) {
				newState = ShooterState.SHOOT_START;
			} else if (wantPickup) {
				newState = ShooterState.PICKUP;
			} else if (wantOutake) {
				newState = ShooterState.LOW_GOAL_SHOOT;
			}
			break;
		case PICKUP:
			intake.setIntakeSpeed(Constants.kIntakePickupSpeed);
			intake.pickupPosition();
			if (!wantPickup) {
				newState = ShooterState.NORMAL;
			}
			break;
		case SHOOT_START:
			newState = ShooterState.SHOOTING_PICKUP;
			break;
		case SHOOTING_PICKUP:
			intake.shootPosition();
			if (!bottomReed.get()) {
				newState = ShooterState.SHOOTING_DELAY;
			}
			break;
		case SHOOTING_DELAY:
			if (timer.get() > 0.2) { // Pop shot 5
				newState = ShooterState.SHOOTING_SHOOT;
			}
			break;
		case SHOOTING_SHOOT:
			setCatapult(true);
			if (timer.get() > 0.5) { // Pop shot .12
				newState = ShooterState.SHOOTING_FINISH;
			}
			break;
		case SHOOTING_FINISH:
			setCatapult(false);
			if (!catapultReed.get()) {
				newState = ShooterState.NORMAL;
			}
			wantShoot = false;
			break;
		case LOW_GOAL_SHOOT:
			intake.pickupPosition();
			if (!bottomReed.get()) {
				if (safeStateTimer.get() > 1) {
					safeStateTimer.reset();
				}
				if (safeStateTimer.get() > .5) {
					setPuncher(true);
				} else {
					setPuncher(false);
				}
			}
			if (timer.get() > 0.3) {
				intake.setIntakeSpeed(Constants.kLowgoalShootingSpeed);
			}
			if (!wantOutake) {
				newState = ShooterState.NORMAL;
			}
			break;
		}

		if (newState != state) {
			System.out.print("New State: ");
			System.out.println(newState);
			state = newState;
			safeStateTimer.reset();
			timer.reset();
		}
	}

	public void resetState() {
		state = ShooterState.NORMAL;
		wantShoot = false;
		wantOutake = false;
		wantPickup = false;
	}

	public enum ShooterState {
		PICKUP, SHOOT_START, SHOOTING_PICKUP, SHOOTING_DELAY, SHOOTING_SHOOT, SHOOTING_FINISH, LOW_GOAL_SHOOT, NORMAL;
	}
}