// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class Elevatorlv1Command extends Command {
  /** Creates a new RollerOut. */
  public Elevatorlv1Command() {
    addRequirements(RobotContainer.mElevatorSub);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.mElevatorSub.SetElevatorlv1();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.mElevatorSub.ElevatorStop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
 if(RobotContainer.mElevatorSub.getEncoderPos() >= Constants.elevatorLevels.lv1 - 0.1){
      return true;
    }
    else{
    return false;
    }
    // return true;
  }
}