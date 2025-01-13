package org.firstinspires.ftc.teamcode.teleop.testers.misc;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.fsm.ActiveCycle;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

public class PresTele extends OpMode {
    private GamepadMapping controls;
    private ActiveCycle cycle;
    private Robot robot;
    private Intake intake;
    private Outtake outtake;
    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
        cycle = new ActiveCycle(telemetry, controls, robot);

        intake = robot.intake;
        outtake = robot.outtake;

        robot.outtake.setMotorsToTeleOpMode();
        robot.outtake.resetEncoders();

        robot.intake.resetHardware();
        robot.outtake.resetHardware();
    }

    @Override
    public void loop() {

    }
}
