package org.firstinspires.ftc.teamcode.teleop.testers.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.fsm.ActiveCycle;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@TeleOp
public class FSMTest extends OpMode {
    private GamepadMapping controls;
    private ActiveCycle cycle;
    private Robot robot;
    private Intake intake;
    private Outtake outtake;
    private long previousTime;

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

        //robot.specimenClaw.openClaw();
    }

//    @Override
//    public void init_loop() {
//        telemetry.addLine("If our alliance is blue, press gamepad1's x, BEFORE you start");
//        telemetry.addLine("The alliance color defaults to blue");
//        telemetry.addData("Color Sensor Is Blue", robot.intake.activeIntake.colorSensor.getIsBlue());
//        controls.isBlue.update(gamepad1.x);
//        if (controls.isBlue.value()) {
//            intake.activeIntake.colorSensor.setIsBlue(true);
//        } else {
//            intake.activeIntake.colorSensor.setIsBlue(false);
//        }
//    }

    @Override
    public void start() {
        previousTime = System.currentTimeMillis();
    }

    @Override
    public void loop() {
        // already does dt & controls.update();
        cycle.activeIntakeUpdate();
        //telemetry.addData("transferState", cycle.getState().stateName());

        long currentTime = System.currentTimeMillis();
        long loopTime = currentTime - previousTime;
        previousTime = currentTime;
        telemetry.addData("Loop Time (ms)", loopTime);
        telemetry.addData("Loop Rate (hz)", 1000 / loopTime);
        telemetry.update();
    }
}
