// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
    // Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.spark.config.SparkMaxConfig;

import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.LimitSwitchConfig.Type;
import com.revrobotics.spark.config.MAXMotionConfig.MAXMotionPositionMode;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

// //deactivated libraries
// import static edu.wpi.first.units.Units.Amps;
// import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
// import com.ctre.phoenix6.configs.TalonFXConfiguration;
// import com.ctre.phoenix6.configs.TalonFXConfigurator;

/** Add your docs here. */
public class Configs {

    public static final class ElevatorMotorConfigurations{
        public static final SparkMaxConfig leftElevatorConfig = new SparkMaxConfig();
        public static final SparkMaxConfig rightElevatorConfig = new SparkMaxConfig();
        public static final SparkMaxConfig coralWristConfig = new SparkMaxConfig();

        static{
            //idk smart lim current or voltage compensation so like change that ig if we need it
            //basic settings

            //original current limit is 40
            leftElevatorConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(40).voltageCompensation(12);
            rightElevatorConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(40).voltageCompensation(12);

            //limit switch
            leftElevatorConfig.limitSwitch.reverseLimitSwitchEnabled(true).reverseLimitSwitchType(Type.kNormallyOpen);
            rightElevatorConfig.limitSwitch.reverseLimitSwitchEnabled(true).reverseLimitSwitchType(Type.kNormallyOpen);

            
            rightElevatorConfig.follow(12, true);
            leftElevatorConfig.inverted(false);
           
            double p = .5;
            double i = 0;
            double d = 8;
            double velocity = 300;
            double acceleration = 225;
            //same thing as a long line of code btw
            //closed loop controller
            leftElevatorConfig
            .closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            .p(p)
            .i(i)
            .d(d)
            .velocityFF(0.565)
            .outputRange(-0.5, 0.5)
            .maxMotion
            .maxVelocity(velocity) //original value: 2100, same as rightelevatormaxvel
            .maxAcceleration(acceleration)//original value 1500
            .allowedClosedLoopError(0.1);
            //.positionMode(mo, null);

            rightElevatorConfig
            .closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            .p(p)//.1
            .i(i)
            .d(d)//6
            .velocityFF(0.565)
            .outputRange(-0.5, 0.5)
            .maxMotion
            .maxVelocity(velocity)
            .maxAcceleration(acceleration)
            .allowedClosedLoopError(0.1);

        }
    }
}