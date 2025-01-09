package org.firstinspires.ftc.teamcode.teleop.testers.mechanisms;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@Config
@TeleOp
public class LockedHeadingPIDTune extends OpMode {
    // ftc dashboard: 192.168.43.1:8080/dash
    private double drive;
    private double strafe;
    private double turn;
    private double currentAngle;
    private Robot robot;
    public static double target;
    public static double p = 0.04, i = 0, d = 0.003, f = 0.00001;

    private Telemetry dashboardTelemetry;
    private GamepadMapping controls;

    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
        telemetry.addData("currentAngle: ", Math.toDegrees(robot.drivetrain.getHeading()));
        currentAngle = robot.drivetrain.getHeading();
        dashboardTelemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void loop() {
        dashboardTelemetry.addData("target angle: ", target);

//        drive = gamepad1.left_stick_y;
//        strafe = gamepad1.left_stick_x;
//        turn = gamepad1.right_stick_x;
//        currentAngle = robot.drivetrain.getHeading();

        robot.drivetrain.changePID(p,i,d,f);
        controls.update();
        robot.drivetrain.update();

        dashboardTelemetry.addData("current heading: ", robot.drivetrain.getHeading());
        dashboardTelemetry.update();
    }
}
