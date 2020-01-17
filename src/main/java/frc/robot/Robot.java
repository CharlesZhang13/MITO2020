/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot{
  
  /**instantiate */
  Joystick MoveStick = new Joystick(pin.movestick);
  Base mBase = new Base(); 
  /**
   * This function is run whesn the robot is first started up and should be
   * used for any initializatsion code.
   */
  @Override
  public void robotInit() {

  }


  @Override
  public void robotPeriodic() {

  }

  @Override
  public void disabledInit() {
    super.disabledInit();
  }


  @Override
  public void autonomousInit() {
  }


  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {

  }




  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
      // basic drive
    mBase.velDrive(-1*MoveStick.getRawAxis(1), MoveStick.getRawAxis(2));

    //disply
    mBase.DisplyInfo();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
