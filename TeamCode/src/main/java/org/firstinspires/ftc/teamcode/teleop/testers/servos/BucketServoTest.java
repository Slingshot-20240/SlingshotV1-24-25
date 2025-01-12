package org.firstinspires.ftc.teamcode.teleop.testers.servos;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@Config
@TeleOp
public class BucketServoTest extends OpMode {
    private GamepadMapping controls;
    private Outtake outtake;
    public static double lservoPos;
    public static double rservoPos;
    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        outtake = new Outtake(hardwareMap, 0, 0, 0, 0, 0, telemetry, controls);
        outtake.rightBucketServo.setPosition(1);
        outtake.leftBucketServo.setPosition(.982);

    }

    @Override
    public void loop() {
//        servoPos += gamepad1.left_stick_y * speed;
//        servoPos = Math.min(Math.max(servoPos, 0.0), 1.0);

        //outtake.rightBucketServo.setPosition(servoPos);
        outtake.leftBucketServo.setPosition(lservoPos);
        outtake.rightBucketServo.setPosition(rservoPos);


//        telemetry.addLine("Bucket Servo Test");
//        telemetry.addLine("Directions: Move left stick up/down to move stack servos");
//        telemetry.addData("servoLeft: ", servoPos);
//        telemetry.addData("y: ", -gamepad1.left_stick_y);
    }
}
