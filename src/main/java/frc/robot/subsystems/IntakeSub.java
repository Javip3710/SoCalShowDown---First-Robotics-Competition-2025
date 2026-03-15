// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


// deactivated libraries
// import com.ctre.phoenix.motorcontrol.ControlFrame;
// import com.revrobotics.servohub.ServoHub.ResetMode;
// import com.revrobotics.spark.SparkLowLevel;

public class IntakeSub extends SubsystemBase {
  TalonFX intakemotor;
  TalonFXConfiguration config;
  public IntakeSub() {
   intakemotor = new TalonFX(Constants.intakemotorCAN);
    intakemotor.setNeutralMode(NeutralModeValue.Brake);

  }

  public void IntakeSetPower(double power){
    intakemotor.set(power);
  }

  public void IntakeStop(double power){
    intakemotor.set(0);
  }


    @Override
  public void periodic() {
    SmartDashboard.putNumber("intake motor \"get()\" ", intakemotor.get());
  }

}
