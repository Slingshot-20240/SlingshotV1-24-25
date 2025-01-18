package org.firstinspires.ftc.teamcode.JihoonExperimentation.Pure;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Disabled
public class LocalTester extends OpMode {
    GoofyLocalizer localizer;

    @Override
    public void init() {
        localizer = new GoofyLocalizer(hardwareMap);
        localizer.setPose(new Pose2d(24.0, 48.0, Math.toRadians(90))); // Set initial position and heading
    }

    @Override
    public void loop() {
        localizer.update(); // Update the odometry calculations

        Pose2d pose = localizer.getPose();

        telemetry.addData("X Position", pose.getX());
        telemetry.addData("Y Position", pose.getY());
        telemetry.addData("Heading (Radians)", pose.getHeading());
        telemetry.update();
    }
}
