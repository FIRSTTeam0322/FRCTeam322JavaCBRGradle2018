package org.firstinspires.frc.team322.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.firstinspires.frc.team322.Robot;

/**
 *
 */
public class DebugOutput extends Command {

    public DebugOutput() {
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.debugOutput();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    	this.end();
    }
}
