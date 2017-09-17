package com.first.team2052.stronghold.auto;

import com.first.team2052.stronghold.auto.modes.AutoReachOuterWorks;
import com.first.team2052.stronghold.auto.modes.DontMove;
import com.first.team2052.stronghold.auto.modes.TestAutoMode;
import com.first.team2052.stronghold.auto.modes.crossing.AutoCrossLowBar;
import com.first.team2052.stronghold.auto.modes.crossing.AutoCrossMoat;
import com.first.team2052.stronghold.auto.modes.crossing.AutoCrossRamparts;
import com.first.team2052.stronghold.auto.modes.crossing.AutoCrossRockwall;
import com.first.team2052.stronghold.auto.modes.crossing.AutoCrossRoughTerrain;
import com.first.team2052.stronghold.auto.modes.crossing.doubleCross.AutoDoubleCrossLowBar;
import com.first.team2052.stronghold.auto.modes.crossing.doubleCross.AutoDoubleCrossMoat;
import com.first.team2052.stronghold.auto.modes.crossing.doubleCross.AutoDoubleCrossRockwall;
import com.first.team2052.stronghold.auto.modes.crossing.doubleCross.AutoDoubleCrossRoughTerrain;
import com.first.team2052.stronghold.auto.modes.crossing.shoot.AutoCrossMoatHighGoal;
import com.first.team2052.stronghold.auto.modes.crossing.shoot.AutoCrossRampartsHighGoal;
import com.first.team2052.stronghold.auto.modes.crossing.shoot.AutoCrossRockwallHighGoal;
import com.first.team2052.stronghold.auto.modes.crossing.shoot.AutoCrossRoughTerrainHighGoal;
import com.first.team2052.stronghold.auto.modes.crossing.shoot.AutoCrossShovelFriesHighGoal;
import com.first.team2052.stronghold.auto.modes.crossing.shoot.AutoLowBarHighGoal;
import com.first.team2052.stronghold.auto.modes.crossing.shoot.AutoLowBarLowGoal;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoModes {
	static SendableChooser sendableChooserDefence;
	static SendableChooser sendableChooserPosition;
	
	public enum AutoModeDefinition {
		DONT_MOVE("Don't move", DontMove.class),
		REACH_OUTERWORKS("Reach outerworks", AutoReachOuterWorks.class),
		//Cross
		CROSS_LOW_BAR("Cross low bar", AutoCrossLowBar.class),
		CROSS_ROCKWALL("Cross rockwall", AutoCrossRockwall.class),
		CROSS_ROUGH_TERRAIN("Cross rough terrain", AutoCrossRoughTerrain.class),
		CROSS_RAMPARTS("Cross ramparts", AutoCrossRamparts.class),
		CROSS_MOAT("Cross moat", AutoCrossMoat.class),
		//High Goal
		CROSS_MOAT_HIGH_GOAL("Cross moat and high goal", AutoCrossMoatHighGoal.class),
		CROSS_ROUGH_TERRAIN_HIGH_GOAL("Cross Rough Terrain High Goal", AutoCrossRoughTerrainHighGoal.class),
		CROSS_ROCKWALL_HIGH_GOAL("Cross Rock Wall High Goal", AutoCrossRockwallHighGoal.class),
		CROSS_RAMPARTS_HIGH_GOAL("Cross ramparts and high goal", AutoCrossRampartsHighGoal.class),

		CROSS_FRIES_HIGH_GOAL("Cross fies and high goal", AutoCrossShovelFriesHighGoal.class),
		//Double Cross
		DOUBLE_CROSS_LOW_BAR("Double cross low bar", AutoDoubleCrossLowBar.class),
		DOUBLE_CROSS_ROCKWALL("Double cross rockwall", AutoDoubleCrossRockwall.class),
		DOUBLE_CROSS_ROUGH_TERRAIN("Double cross rough terrain", AutoDoubleCrossRoughTerrain.class),
		//DOUBLE_CROSS_RAMPARTS("Double cross ramparts", AutoDoubleCrossRamparts.class),
		DOUBLE_CROSS_MOAT("Double cross moat", AutoDoubleCrossMoat.class),
		//Low Bar Autos
		LOW_BAR_LOW_GOAL("Low bar: low goal", AutoLowBarLowGoal.class),
		LOW_BAR_HIGH_GOAL("Low Bar: High Goal", AutoLowBarHighGoal.class),
		//Other
		TEST_AUTO("Test Auto (For testing new features)", TestAutoMode.class);

		private Class<? extends AutoMode> clazz;
		private String name;

		private AutoModeDefinition(String name, Class<? extends AutoMode> clazz) {
			this.clazz = clazz;
			this.name = name;
		}

		public AutoMode getInstance() {
			try {
				return clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
			}
			return null;
		}
	}

	public static void putToSmartDashboard() {
		sendableChooserDefence = new SendableChooser();
		for (int i = 0; i < AutoModeDefinition.values().length; i++) {
			AutoModeDefinition mode = AutoModeDefinition.values()[i];
			if (i == 0) {
				sendableChooserDefence.addDefault(mode.name, mode);
			} else {
				sendableChooserDefence.addObject(mode.name, mode);
			}
		}
		SmartDashboard.putData("auto_defence", sendableChooserDefence);
		
		sendableChooserPosition = new SendableChooser();
		sendableChooserPosition.addDefault("1", 1);
		sendableChooserPosition.addObject("2", 2);
		sendableChooserPosition.addObject("3", 3);
		sendableChooserPosition.addObject("4", 4);
		sendableChooserPosition.addObject("5", 5);
		sendableChooserPosition.addObject("5 - Straight", 6);
		SmartDashboard.putData("auto_position", sendableChooserPosition);
	}

	public static AutoMode getAutoInstance() {
		return ((AutoModeDefinition) sendableChooserDefence.getSelected()).getInstance();
	}
	
	public static int getAutoPosition() {
		return (int)sendableChooserPosition.getSelected();
	}
}