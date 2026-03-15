// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

// import com.revrobotics.CANSparkMax;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.sim.SparkMaxAlternateEncoderSim;
import com.revrobotics.sim.SparkMaxSim;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


// //deactivated libraries
// import com.ctre.phoenix.motorcontrol.ControlFrame;
// import com.revrobotics.servohub.ServoHub.ResetMode;
// import com.revrobotics.spark.SparkLowLevel;

public class ElevatorSub extends SubsystemBase {
  /** Creates a new ElevatorSub. */
  SparkMax LeftElevator, RightElevator;
  // AbsoluteEncoder elevatorAbsEncoder;
  // RelativeEncoder elevatorRelEncoder;
  SparkClosedLoopController elevatorClosedLoopController;
  RelativeEncoder elevatorRELencoder;

  
  PIDController pidcontroller; 
  // DCMotor neoVortexDcMotor;

  public ElevatorSub() {
    LeftElevator = new SparkMax(12, MotorType.kBrushless);
    RightElevator = new SparkMax(11, MotorType.kBrushless);
    // elevatorRELencoder = LeftElevator.getEncoder();
    elevatorClosedLoopController = LeftElevator.getClosedLoopController();
    elevatorRELencoder = LeftElevator.getEncoder();

    pidcontroller = new PIDController(0, 0, 0);
    //Configures the Spark Maxes to use them
    LeftElevator.configure(frc.robot.Configs.ElevatorMotorConfigurations.leftElevatorConfig, com.revrobotics.spark.SparkBase.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    RightElevator.configure(frc.robot.Configs.ElevatorMotorConfigurations.rightElevatorConfig, com.revrobotics.spark.SparkBase.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  
  }
  public void elevatorSetPower(double power){
    LeftElevator.set(power);
  }

  public void ElevatorStop(){
    LeftElevator.set(0);
  }

  public void SetElevatorlv1(){
    elevatorClosedLoopController.setReference(Constants.elevatorLevels.lv1, ControlType.kPosition); //ClosedLoopSlot.kSlot0, .5);
  }

  public void SetElevatorlv2(){
    elevatorClosedLoopController.setReference(Constants.elevatorLevels.lv2,ControlType.kPosition, ClosedLoopSlot.kSlot0, .5);
  }

  public void SetElevatorlv3(){
    elevatorClosedLoopController.setReference(Constants.elevatorLevels.lv3,ControlType.kPosition, ClosedLoopSlot.kSlot0, .05);
  }

  public void SetElevatorlv0(){
    elevatorClosedLoopController.setReference(Constants.elevatorLevels.lv0, ControlType.kPosition);//, ClosedLoopSlot.kSlot0, .5);
  }

  public void resetEncoder(){
    elevatorRELencoder.setPosition(0);
  }

  public double getEncoderPos(){
    return elevatorRELencoder.getPosition();
  }

  @Override
  public void periodic() {
    // SmartDashboard.putNumber("Pos (rotations)");
    SmartDashboard.putNumber("Encoder Elevator value", elevatorRELencoder.getPosition());
  }
}
