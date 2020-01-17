/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * This class is used for storing constants
 */
public class constant {
    public static boolean reverse = false;

    public final static int kPIDLoopIdx = 0;
    public final static int kTimeoutMs = 10;

    //base
    public final static double VCoefficient = 500.0*(4096.0/600.0);
    public static double velocityControl = 10.0;

}
