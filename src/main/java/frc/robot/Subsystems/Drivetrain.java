// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.Commands.DriveTeleop;

/** Add your docs here. */
public class Drivetrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  //fields

	//mecanum motor controllers
  public static CANSparkMax frontRight = new CANSparkMax(RobotMap.frontRightPort, MotorType.kBrushless);
  public static CANSparkMax rearRight = new CANSparkMax(RobotMap.rearRightPort, MotorType.kBrushless);
  public static CANSparkMax frontLeft = new CANSparkMax(RobotMap.frontLeftPort, MotorType.kBrushless);
  public static CANSparkMax rearLeft = new CANSparkMax(RobotMap.rearLeftPort, MotorType.kBrushless);

  public static AnalogGyro gyro = new AnalogGyro(RobotMap.gyroPort);

  public static RelativeEncoder rightEncoder = frontRight.getEncoder();
  public static RelativeEncoder leftEncoder = frontLeft.getEncoder();
  public static MecanumDrive mecanum = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);

  //PID fields
  /*public final static double Kp = 0.01;
  public final static double Ki = 0.0;
  public final static double Kd = 0.0;
  //public double distance, left_speed, right_speed;
  public double left_speed, right_speed;
  static double min_error = 0.1; //sets an error deadband/ minimum value
  static double min_command = 0.0;
  static double current_error = 0; 
  static double previous_error = 0;
  static double integral = 0;
  static double derivative = 0;
  static double adjust = 0;
  static double time = 0.1; // 0.1 seconds = 100 milliseconds */

  public Drivetrain(){
    
  }

  public static void driveTeleop() {
    double xSpeed = OI.driveJoy1.getRawAxis(0); //strafe left and right
    double ySpeed = -OI.driveJoy1.getRawAxis(1); //forward and backward
    double zRotation = OI.driveJoy2.getRawAxis(0); //rotation

    //mecanum.driveCartesian(ySpeed, xSpeed, zRotation, gyro.getAngle());
    mecanum.driveCartesian(zRotation, xSpeed, ySpeed);
    //mecanum.driveCartesian(zRotation, xSpeed, ySpeed, 0);
  }

  public static void driveAuton(double xSpeed, double ySpeed, double zRotation, double angle){
    mecanum.driveCartesian(ySpeed, xSpeed, zRotation, gyro.getAngle()); //probably needs to be fixed
  }

  public static void driveStraightDistance(double distance, double angle){ 
    /*double rightDistance = rightEncoder.getPosition();
    double leftDistance = leftEncoder.getPosition();

    mecanum.driveCartesian(0.3, 0.3, angle, gyro.getAngle());

    while(distance < rightDistance && distance < leftDistance){
      driveStraight(angle);
    }
    //driveStop();*/
  }

  public static void driveStraight(double angle){
    /*while(gyro.getAngle() != angle){
      previous_error = current_error;
      current_error = angle - gyro.getAngle();
      integral = (current_error+previous_error)/2*(time);
      derivative = (current_error-previous_error)/time;
      adjust = Kp*current_error + Ki*integral + Kd*derivative;

      if (current_error > min_error){
        adjust += min_command;
      }
      else if (current_error < -min_error){
        adjust -= min_command;
      }

      if(gyro.getAngle() > angle){
        mecanum.driveCartesian(0.3, 0.3, angle - adjust, gyro.getAngle());
      }
      else if(gyro.getAngle() < angle){
        mecanum.driveCartesian(0.3, 0.3, angle + adjust, gyro.getAngle());
      }
    }  */
  }

  public static void turnDegrees(double angle){ //fix this! - use driveCartesian
    /*if(angle > 180){
      angle = -(360 - angle);
    }

    while(gyro.getAngle() != angle){
      if(angle < 0){
        mecanum.driveCartesian(0, 0, -0.3, gyro.getAngle());
        frontLeft.set(0.3);
        rearLeft.set(0.3);
        frontRight.set(-0.3);
        rearRight.set(-0.3);
      }
      else if(angle > 0){
        mecanum.driveCartesian(0, 0, 0.3, gyro.getAngle());
        frontLeft.set(-0.3);
        rearLeft.set(-0.3);
        frontRight.set(0.3);
        rearRight.set(0.3);

      }
    }
    driveStop();
    */
  }

  public static void driveStop(){
    frontLeft.set(0.0);
    rearLeft.set(0.0);
    frontRight.set(0.0);
    rearRight.set(0.0);
  }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new DriveTeleop());
  }
}