package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.fsm.ActiveCycle;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@TeleOp
public class ASlingshotTeleop extends OpMode {
    private GamepadMapping controls;
    private ActiveCycle cycle;
    private Robot robot;
    private long previousTime;

    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
        cycle = new ActiveCycle(telemetry, controls, robot);

        robot.outtake.setMotorsToTeleOpMode();
    }

// Only needed for active intake
//    @Override
//    public void init_loop() {
//        telemetry.addLine("If our alliance is blue, press gamepad1's x, BEFORE you start");
//        telemetry.addLine("The alliance color defaults to blue");
//        telemetry.addData("Color Sensor Is Blue", robot.intake.colorSensor.getIsBlue());
//        controls.isBlue.update(gamepad1.x);
//        if (controls.isBlue.value()) {
//            robot.intake.colorSensor.setIsBlue(true);
//        } else {
//            robot.intake.colorSensor.setIsBlue(false);
//        }
//    }
    @Override
    public void start() {
        // run once when we start
        robot.hardwareSoftReset();
        robot.intake.extendoFullRetract();

        previousTime = System.currentTimeMillis();
    }

    @Override
    public void loop() {
        cycle.activeIntakeUpdate();

        long currentTime = System.currentTimeMillis();
        long loopTime = currentTime - previousTime;
        previousTime = currentTime;
        telemetry.addData("Loop Time (ms)", loopTime);
        telemetry.addData("Loop Rate (hz)", 1000 / loopTime);
        telemetry.update();
    }
}
