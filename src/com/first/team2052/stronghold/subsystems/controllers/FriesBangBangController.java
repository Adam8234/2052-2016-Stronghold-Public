package com.first.team2052.stronghold.subsystems.controllers;

public class FriesBangBangController {
	private double goal;
	
	public FriesBangBangController(double goal){
		this.goal = goal;
	}
	
	public double calculate(double currentPos){
		double error = goal - currentPos;
		double absError = Math.abs(error);
		
		if(currentPos < goal){
			return 1.0;
		} else if(currentPos > goal){
			return -1.0;
		}
		
		return 0.0;
	}
	
	public void setGoal(double goal){
		
	}
}
