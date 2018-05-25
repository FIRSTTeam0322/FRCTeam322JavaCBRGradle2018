package org.firstinspires.frc.team322.commands;

import org.firstinspires.frc.team322.Robot;
import org.firstinspires.frc.team322.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

/**
 *
 */
public class AutonCrossLineOutside extends Command {

    EncoderFollower left, right;

    public AutonCrossLineOutside() {
        requires(Robot.chassis);
        requires(Robot.chassisSensors);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Waypoint[] points = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(0, 3.25, 0)
        };
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0);
        Trajectory trajectory = Pathfinder.generate(points, config);
        TankModifier modifier = new TankModifier(trajectory).modify(RobotMap.AXLE_TRACK);

        left = new EncoderFollower(modifier.getLeftTrajectory());
        right = new EncoderFollower(modifier.getRightTrajectory());

        left.configureEncoder(Robot.chassis.getRawEncoderData(1), 1440, RobotMap.WHEEL_DIAMETER_METRIC);
        right.configureEncoder(Robot.chassis.getRawEncoderData(3), 1440, RobotMap.WHEEL_DIAMETER_METRIC);

        // The first argument is the proportional gain. Usually this will be quite high
        // The second argument is the integral gain. This is unused for motion profiling
        // The third argument is the derivative gain. Tweak this if you are unhappy with the tracking of the trajectory
        // The fourth argument is the velocity ratio. This is 1 over the maximum velocity you provided in the 
        //      trajectory configuration (it translates m/s to a -1 to 1 scale that your motors can read)
        // The fifth argument is your acceleration gain. Tweak this if you want to get to a higher or lower speed quicker
        left.configurePIDVA(1.0, 0.0, 0.0, 1 / 2.0, 0);
        right.configurePIDVA(1.0, 0.0, 0.0, 1 / 2.0, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        double l = left.calculate(Robot.chassis.getRawEncoderData(1));
        double r = right.calculate(Robot.chassis.getRawEncoderData(3));

        double gyro_heading = Robot.chassisSensors.getAngleX();    // Assuming the gyro is giving a value in degrees
        double desired_heading = Pathfinder.r2d(left.getHeading());  // Should also be in degrees

        double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
        double turn = 0.8 * (-1.0/80.0) * angleDifference;
        
        Robot.chassis.pathfinderDriveSystem(l + turn, r - turn);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return left.isFinished();
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
