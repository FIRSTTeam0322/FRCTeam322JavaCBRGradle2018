package org.firstinspires.frc.team322.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.firstinspires.frc.team322.Robot;

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
    	if(Robot.chassis.getEncoderData(1) < -36.0 || Robot.chassis.getEncoderData(2) < -36.0 || 
    			Robot.chassis.getEncoderData(3) < -36.0 || Robot.chassis.getEncoderData(4) < -36.0) {
    		Robot.chassis.brakesOff();
    		Robot.chassis.autonDriveSystem(-0.75, 0.0);
    	}
    	else {
    		Robot.chassis.autonDriveSystem(0.0, 0.0);
    		Robot.chassis.brakesOn();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    	Robot.chassis.autonDriveSystem(0.0, 0.0);
    	Robot.chassis.brakesOff();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    	this.end();
    }
}
