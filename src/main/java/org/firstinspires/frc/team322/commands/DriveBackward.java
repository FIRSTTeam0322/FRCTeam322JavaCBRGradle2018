package org.firstinspires.frc.team322.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.firstinspires.frc.team322.Robot;
import org.firstinspires.frc.team322.RobotMap;

/**
 *
 */
public class DriveBackward extends Command {

    public DriveBackward() {
    	requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    	Robot.chassis.autonDriveSystem(-(RobotMap.autonSpeed), RobotMap.autonRotation);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        if((timeSinceInitialized() > RobotMap.autonTime) || (Math.abs(Robot.chassis.getEncoderData(1)) > RobotMap.autonDistance ||
        		Math.abs(Robot.chassis.getEncoderData(2)) > RobotMap.autonDistance ||
        		Math.abs(Robot.chassis.getEncoderData(3)) > RobotMap.autonDistance ||
        		Math.abs(Robot.chassis.getEncoderData(4)) > RobotMap.autonDistance)) {
        	System.out.println("True " + timeSinceInitialized());
        	return true;
        }
        else {
        	System.out.println("False " + timeSinceInitialized());
        	return false;
        }
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    	Robot.chassis.brakesOff();
    	Robot.chassis.autonDriveSystem(0.0, 0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    	this.end();
    }
}
