/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The class which controls the behaviors of base.
 */

public class Base{

    TalonSRX RTalon = new TalonSRX(pin.RTalonID);
    TalonSRX LTalon = new TalonSRX(pin.LTalonID);
    VictorSPX LVictor = new VictorSPX(pin.LVictorID);
    VictorSPX RVictor = new VictorSPX(pin.RVictorID);
    AHRS ahrs = new AHRS(SPI.Port.kMXP);
    PIDController turnController = new PIDController(0.03, 0.0, 0.0,constant.kTimeoutMs);
    double rotateToAngle;
    double targetAngle;
    double rotateError= 0.0;
    double rotateTimeOutSec=0.0;
    double rotateSpeed = 0.0;
    

    // constants
    private static final double kToleranceDegrees = 2.0f;

    
    public Base(){
        //Constructor of Class Base
            //initialises all talon and victor on Base
        TalonSRXInit(RTalon);
        TalonSRXInit(LTalon);

        turnController.enableContinuousInput(-180f, 180f);
        turnController.setTolerance(kToleranceDegrees);

        LVictor.setInverted(true);
        LTalon.setInverted(true);
        RTalon.setInverted(false);
        RVictor.setInverted(false);

        configPID(LTalon, 0.1097, 0.22, 0, 0, 0.4);
        configPID(RTalon, 0.1097, 0.22, 0, 0, 0.4);

        LVictor.follow(LTalon);
        RVictor.follow(RTalon);

        resetAll();
    }


    public void velDrive(final double forward, final double turn){
        /*
        controlling basic movement of base
        */

        final double LTempV = (forward-turn)
                        *constant.VCoefficient*constant.velocityControl;
        final double RTempV = (forward+turn)
                        *constant.VCoefficient*constant.velocityControl;

        SmartDashboard.putNumber("LV", LTempV);
        SmartDashboard.putNumber("RV", RTempV);
        LTalon.set(ControlMode.Velocity, LTempV);
        RTalon.set(ControlMode.Velocity, RTempV);

    }

    
    public void rotate(double angle){
        turnController.setSetpoint(angle);
        double changeInControl = turnController.calculate(ahrs.getAngle());
        SmartDashboard.putNumber("cic", changeInControl);
        LTalon.set(ControlMode.Velocity, -changeInControl*constant.VCoefficient);
        LTalon.set(ControlMode.Velocity, changeInControl*constant.VCoefficient);
    }
    


    private void TalonSRXInit(final TalonSRX _talon) {
        //set up TalonSRX and closed loop
        
		_talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
				constant.kPIDLoopIdx,constant.kTimeoutMs);

		_talon.setSensorPhase(true);

		_talon.configNominalOutputForward(0, constant.kTimeoutMs);
		_talon.configNominalOutputReverse(0, constant.kTimeoutMs);
		_talon.configPeakOutputForward(1, constant.kTimeoutMs);
		_talon.configPeakOutputReverse(-1, constant.kTimeoutMs);
    }

    private void configPID(final TalonSRX _talon,final double kF, final double kP,final double kI, final double kD, final double kRamp) {
		_talon.config_kF(constant.kPIDLoopIdx, kF, constant.kTimeoutMs);
		_talon.config_kP(constant.kPIDLoopIdx, kP, constant.kTimeoutMs);
		_talon.config_kI(constant.kPIDLoopIdx, kI, constant.kTimeoutMs);
        _talon.config_kD(constant.kPIDLoopIdx, kD, constant.kTimeoutMs);
        _talon.configClosedloopRamp(kRamp, constant.kTimeoutMs);

    }

    public void DisplyInfo(){
        //Disply information on driverstation
        /*
        SmartDashboard.putNumber("Left velocity", LTalon.getSelectedSensorVelocity(constant.kPIDLoopIdx));
        SmartDashboard.putNumber("Left output", LTalon.getMotorOutputPercent());
        SmartDashboard.putNumber("Right velocity", RTalon.getSelectedSensorVelocity(constant.kPIDLoopIdx));
        SmartDashboard.putNumber("Right output", RTalon.getMotorOutputPercent());
        */
        SmartDashboard.putNumber("LPosition", LTalon.getSelectedSensorVelocity());
        SmartDashboard.putNumber("RPosition", RTalon.getSelectedSensorVelocity());
        SmartDashboard.putNumber("ahrsChanges", turnController.calculate(ahrs.getAngle()));
    }

    public void resetPos(){
        LTalon.setSelectedSensorPosition(0);
        RTalon.setSelectedSensorPosition(0);
        ahrs.resetDisplacement();
    }

    public void resetAll(){
        //reset ahrs
        ahrs.reset();
        ahrs.resetDisplacement();
        ahrs.zeroYaw();
    }

/*
    @Override
	public void pidWrite(double output) {
	}
	
	@Override
	public double pidGet() {
		return 0.0;
	}
    
    @Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kRate;
	}
	
	@Override
    public void setPIDSourceType(PIDSourceType pidSource) {	}
    */
}


