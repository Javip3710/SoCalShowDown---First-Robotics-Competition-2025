// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.auto.AutoBuilder;
import com.ctre.phoenix6.swerve.SwerveDrivetrain;
import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.commands.ElevatorDownCommand;
import frc.robot.commands.ElevatorUpCommand;
import frc.robot.commands.Elevatorlv0Command;
import frc.robot.commands.Elevatorlv1Command;
import frc.robot.commands.Elevatorlv2Command;
import frc.robot.commands.Elevatorlv3Command;
import frc.robot.commands.IntakeIn;
import frc.robot.commands.IntakeOut;
import frc.robot.commands.swerveTurnCommand;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.ElevatorSub;
import frc.robot.subsystems.Limelight;
// import frc.robot.subsystems.IntakeSub;
import frc.robot.subsystems.IntakeSub;

import com.pathplanner.lib.auto.AutoBuilder;

//Deactivated libraries

// import edu.wpi.first.wpilibj.simulation.ElevatorSim;
// import edu.wpi.first.wpilibj2.command.Commands;

public class RobotContainer {

    // configureAutoBuilder();

    private double PercentSlow = 1;
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    public static ElevatorSub mElevatorSub = new ElevatorSub();
    public static IntakeSub mIntakeSub = new IntakeSub();
    public static Limelight mlimelight = new Limelight();

    private final CommandXboxController joystick = new CommandXboxController(0);
    public static CommandXboxController codriver = new CommandXboxController(1);

    public static CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    //path 
   // private final SendableChooser<Command> autoChooser = AutoBuilder.buildAutoChooser();
    
   private final SendableChooser<Command> autoChooser;

    public RobotContainer() {
        NamedCommands.registerCommand("lv1 elevator", new Elevatorlv1Command());
        NamedCommands.registerCommand("lv2 elevator", new Elevatorlv2Command());
        NamedCommands.registerCommand("lv3 elevator", new Elevatorlv3Command());
        NamedCommands.registerCommand("lv0 elevator", new Elevatorlv0Command());
        NamedCommands.registerCommand("Intake out", new IntakeOut());
        NamedCommands.registerCommand("Intake in", new IntakeIn());
        autoChooser = AutoBuilder.buildAutoChooser();



        autoChooser.setDefaultOption("OutWayTop", new PathPlannerAuto("OutWayTop"));
        autoChooser.addOption("Auto1", new PathPlannerAuto("Auto1"));
        autoChooser.addOption("MidOutWay", new PathPlannerAuto("MidOutWay"));        
        autoChooser.addOption("OutWayTop", new PathPlannerAuto("OutWayTop"));
        autoChooser.addOption("Bottom Auto", new PathPlannerAuto("Bottom Auto"));


        
        SmartDashboard.putData(autoChooser);
        SmartDashboard.putData(new RunCommand(()->drivetrain.setOperatorPerspectiveForward(CommandSwerveDrivetrain.kRedAlliancePerspectiveRotation)).withName("Set Red Forward").ignoringDisable(true));
        SmartDashboard.putData(new RunCommand(()->drivetrain.setOperatorPerspectiveForward(CommandSwerveDrivetrain.kBlueAlliancePerspectiveRotation)).withName("Set Blue Forward").ignoringDisable(true));
        configureBindings();

    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention, 
        
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-joystick.getLeftY() * MaxSpeed*PercentSlow*.2) // Drive forward with negative Y (forward)
                    .withVelocityY(-joystick.getLeftX() * MaxSpeed*PercentSlow*.2) // Drive left with negative X (left)
                    .withRotationalRate(-joystick.getRightX() * MaxAngularRate*PercentSlow*.5) // Drive counterclockwise with negative X (left)
            )
        );

        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

        joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
        joystick.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))
        ));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        joystick.b().whileTrue(new swerveTurnCommand(drivetrain, drive, brake));
        // reset the field-centric heading on left bumper press
        // joystick.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));
        joystick.leftBumper().onTrue(new InstantCommand(() -> boring()));

        drivetrain.registerTelemetry(logger::telemeterize);

        //start with codriver commands
        codriver.b().whileTrue(new ElevatorUpCommand());
        codriver.a().whileTrue(new ElevatorDownCommand());
        codriver.pov(180).onTrue(new Elevatorlv0Command());
        codriver.pov(270).onTrue(new Elevatorlv1Command());
        codriver.pov(90).onTrue(new Elevatorlv2Command());
        codriver.pov(0).whileTrue(new Elevatorlv3Command());
        // codriver.pov(45).whileTrue(new Elevatorlv4Command());
        
        // set co-driver to manually set elevator up and down while holding controller left bumper
        // codriver.pov(180).whileTrue(new ElevatorDownCommand());
        // codriver.pov(0).whileTrue(new ElevatorUpCommand());
        codriver.leftBumper().whileTrue(new IntakeIn());

        double tx = LimelightHelpers.getTX("Get x");
        double ty = LimelightHelpers.getTY("Get y");
        SmartDashboard.putNumber("get x", tx);
        SmartDashboard.putNumber("get y", ty);
    }

    public void boring(){
        if(PercentSlow == 1){
            PercentSlow = Constants.PercentSlow;
        }
        else{ PercentSlow = 1;}
    }

     public Command getAutonomousCommand() {
    //     // return new PathPlannerAuto("test");
        return autoChooser.getSelected();
    //     return SwerveDrivetrain.getAutonomousCommand("new Auto");
    // }

}
}
