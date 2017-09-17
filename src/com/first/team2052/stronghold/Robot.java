package com.first.team2052.stronghold;

import com.first.team2052.lib.Looper;
import com.first.team2052.lib.MultiLooper;
import com.first.team2052.stronghold.auto.AutoModeRunner;
import com.first.team2052.stronghold.auto.AutoModes;
import com.first.team2052.stronghold.auto.AutoPaths;
import com.first.team2052.stronghold.subsystems.CatAManipulator;
import com.first.team2052.stronghold.subsystems.DriverCameras;
import com.first.team2052.stronghold.subsystems.Shooter;
import com.first.team2052.stronghold.subsystems.drive.DriveTrain;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	private MultiLooper controlLoop;
	private Looper cameraLoopoer;
	private AutoModeRunner autoModeRunner;

	private Joystick primaryJoystick1, primaryJoystick2, secondaryJoystick;

	// Sub systems
	private static VisionProcessor vision;
	private static DriveTrain driveTrain;
	private static CatAManipulator catAManipulator;
	private static Shooter shooter; // Shooter includes catapult and intake
	private DriverCameras cameras;

	private static AutoPaths autoPaths;

	private boolean visionTurn = false;

	@Override
	public void robotInit() {
		// Sub systems
		driveTrain = new DriveTrain();
		shooter = new Shooter();
		vision = new VisionProcessor();
		catAManipulator = new CatAManipulator();

		AutoModes.putToSmartDashboard();

		primaryJoystick1 = new Joystick(1);
		primaryJoystick2 = new Joystick(0);
		secondaryJoystick = new Joystick(2);

		autoModeRunner = new AutoModeRunner();

		cameras = new DriverCameras();
		// Control loops and such
		controlLoop = new MultiLooper("Control Loop", Constants.kControlLoopPeriod, true);
		controlLoop.addLoopable(driveTrain);
		controlLoop.addLoopable(shooter);
		cameraLoopoer = new Looper("Camera Looper", cameras, 1.0 / 10.0);

		autoPaths = new AutoPaths();
	}

	@Override
	public void autonomousInit() {
		controlLoop.start();
		driveTrain.resetEncoders();
		driveTrain.disableController();
		driveTrain.resetGyro();
		// Delay to reduce initialization issues
		Timer.delay(0.25);
		autoModeRunner.setAutoMode(AutoModes.getAutoInstance());
		autoModeRunner.start();
	}

	@Override
	public void autonomousPeriodic() {
	}

	@Override
	public void disabledInit() {
		autoModeRunner.stop();
		controlLoop.stop();
		cameraLoopoer.stop();
		driveTrain.resetGyro();

		driveTrain.disableController();
		driveTrain.driveOpenLoop(0, 0);

		shooter.resetState();

		System.gc();
	}

	@Override
	public void teleopInit() {
		controlLoop.start();
		cameraLoopoer.start();
		driveTrain.resetEncoders();
		driveTrain.resetGyro();
		driveTrain.disableController();
	}

	@Override
	public void teleopPeriodic() {
		SmartDashboard.putNumber("Gyro Angle", driveTrain.getGyroAngle());
		SmartDashboard.putNumber("Gyro Y", getDriveTrain().getGyro().getAngleY());
		if (primaryJoystick1.getRawButton(2)) {
			if (!visionTurn) {
				double angle = 0.0;
				try {
					angle = vision.getXAngleFromCenter();
				} catch (NullPointerException e) {
					System.out.println("GRIP data not found; this problem should fix itself.");
				}

				driveTrain.setTurnTrajectory(angle);
				System.out.print("Vision Angle: ");
				System.out.println(angle);
			}

			visionTurn = primaryJoystick1.getRawButton(2);
		} else {
			visionTurn = false;
			double tank = Util.checkForDeadzone(primaryJoystick1.getY(), Constants.kDriveDeadZone);
			double turn = Util.checkForDeadzone(-primaryJoystick2.getX(), Constants.kDriveDeadZone);

			tank = Util.curve(tank, Constants.kDriveSpeedCurveTank);
			tank = Util.limit(tank, 1.0);

			turn *= .75;

			turn = Math.sin(Math.PI / 2.0 * Constants.kDriveSpeedCurveTurn * turn) / Math.sin(Math.PI / 2.0 * Constants.kDriveSpeedCurveTurn);
			turn = Math.sin(Math.PI / 2.0 * Constants.kDriveSpeedCurveTurn * turn) / Math.sin(Math.PI / 2.0 * Constants.kDriveSpeedCurveTurn);

			if (primaryJoystick1.getRawButton(6)) {
				tank *= .5;
			}

			if (!primaryJoystick1.getTrigger()) {
				tank = -tank;
			}

			driveTrain.driveOpenLoop(tank, turn);
		}

		shooter.wantPickup = secondaryJoystick.getRawButton(4);
		shooter.wantOutake = secondaryJoystick.getRawButton(5);
		shooter.wantShoot = primaryJoystick2.getTrigger();

		if (primaryJoystick2.getRawButton(11)) {
			shooter.resetState();
		}

		cameras.setCurrentCam(secondaryJoystick.getRawButton(7));

		catAManipulator.setOpenLoop(-secondaryJoystick.getY());

	}

	public static DriveTrain getDriveTrain() {
		return driveTrain;
	}

	public static Shooter getShooter() {
		return shooter;
	}

	public static VisionProcessor getVision() {
		return vision;
	}

	public static AutoPaths getPaths() {
		return autoPaths;
	}
}