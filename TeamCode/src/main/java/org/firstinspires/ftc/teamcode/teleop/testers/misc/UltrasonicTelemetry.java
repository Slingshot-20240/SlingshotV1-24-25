package org.firstinspires.ftc.teamcode.teleop.testers.misc;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@TeleOp
@Config
public class UltrasonicTelemetry extends OpMode {
    private Robot robot;
    private GamepadMapping controls;
    double backTarget;
    double sideTarget;
    private Telemetry dashboardTelemetry;
    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
        dashboardTelemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void loop() {
        // Target Distances
        backTarget = robot.ultraSonics.getBackDistance(robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
        sideTarget = robot.ultraSonics.getSideDistance(robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));

        // remember to go and change the equation to what you're using & the constants too
        dashboardTelemetry .addData("Back Calculated Distance", backTarget);
        //dashboardTelemetry .addData("Side Calculated Distance", sideTarget);

        dashboardTelemetry .addLine(" ");

        // these are the sensed distances
        dashboardTelemetry .addData("Back Sensed Distance", robot.ultraSonics.backDS.getDistance(DistanceUnit.MM));
        //dashboardTelemetry .addData("Side Sensed Distance", robot.ultraSonics.sideDS.getDistance(DistanceUnit.INCH));

        dashboardTelemetry .addLine(" ");

        // other
        dashboardTelemetry .addData("Current Angle", robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
    }
}
