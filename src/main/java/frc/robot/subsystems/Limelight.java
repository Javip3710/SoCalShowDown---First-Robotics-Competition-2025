// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.swerve.SwerveDrivetrain;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;
import frc.robot.RobotContainer;

public class Limelight extends SubsystemBase {
  /** Creates a new Limelight. */
  public Limelight() {
  double tx = LimelightHelpers.getTX("");  // Horizontal offset from crosshair to target in degrees
  double ty = LimelightHelpers.getTY("");  // Vertical offset from crosshair to target in degrees
  double ta = LimelightHelpers.getTA("");  // Target area (0% to 100% of image)
  boolean hasTarget = LimelightHelpers.getTV(""); // Do you have a valid target?

  double txnc = LimelightHelpers.getTXNC("");  // Horizontal offset from principal pixel/point to target in degrees
  double tync = LimelightHelpers.getTYNC("");

  }

  public void center(){

      // drive.withVelocityX();
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
