package com.first.team2052.lib;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * Wrapper for Talon SRX encoder to be similar to the Encoder class
 * 
 * @author Adam
 */
public class TalonSRXEncoder {
	private double distancePerPulse;
	private boolean inverted = false;
	private CANTalon talon;
	private double ticksPerRotation = 256;

	public TalonSRXEncoder(CANTalon canTalon) {
		talon = canTalon;
	}

	public TalonSRXEncoder(CANTalon canTalon, boolean inverted) {
		this(canTalon);
		this.inverted = inverted;
	}

	public int get() {
		return (int) (getRaw() * 0.25);
	}

	public double getDistance() {
		return getRaw() * .25 * distancePerPulse;
	}

	public int getRaw() {
		return inverted ? -talon.getEncPosition() : talon.getEncPosition();
	}

	public double getVelocity() {
		return (((inverted ? -talon.getEncVelocity() : talon.getEncVelocity()) / (ticksPerRotation * 4.0) * 10) * (distancePerPulse * ticksPerRotation));
	}

	public void reset() {
		talon.setEncPosition(0);
	}

	public void setDistancePerPulse(double distancePerPulse) {
		this.distancePerPulse = distancePerPulse;
	}

	public void setTicksPerRotation(double ticksPerRotation) {
		this.ticksPerRotation = ticksPerRotation;
	}
}
