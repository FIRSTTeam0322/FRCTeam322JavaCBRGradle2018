package org.firstinspires.frc.team322.subsystems;

import org.firstinspires.frc.team322.commands.DashboardUpdater;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Dashboard extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new DashboardUpdater());
    }
}

