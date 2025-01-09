package org.firstinspires.ftc.teamcode.teleop.testers.misc;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

public class UltrasonicTelemetry extends OpMode {
    private Robot robot;
    private GamepadMapping controls;
    double backTarget;
    double sideTarget;
    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
    }

    @Override
    public void loop() {
        // Target Distances
        backTarget = robot.ultraSonics.getBackDistance(robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
        sideTarget = robot.ultraSonics.getSideDistance(robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));

        // remember to go and change the equation to what you're using & the constants too
        telemetry.addData("Back Calculated Distance", backTarget);
        telemetry.addData("Side Calculated Distance", backTarget);

        telemetry.addLine(" ");

        // these are the sensed distances
        telemetry.addData("Back Sensed Distance", robot.ultraSonics.backDS.getDistance(DistanceUnit.INCH));
        telemetry.addData("Side Sensed Distance", robot.ultraSonics.sideDS.getDistance(DistanceUnit.INCH));

        telemetry.addLine(" ");

        // other
        telemetry.addData("Current Angle", robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
    }
}
