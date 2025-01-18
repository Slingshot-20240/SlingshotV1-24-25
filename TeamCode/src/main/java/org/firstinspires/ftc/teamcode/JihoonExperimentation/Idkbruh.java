package org.firstinspires.ftc.teamcode.JihoonExperimentation;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@Disabled
@Config
@TeleOp
public class Idkbruh extends LinearOpMode {
    private DcMotor slideMotor;
    private PIDController pidController;

    private final int refreshRate = 20; //milliseconds

    //TODO: is need change per each application idk eyeball it
    private final double errorBuffer = 30; // how much error be aloud
    private final double oscillationBuffer = 50; // how much oscillation be allowed

    private final double f = 0;  //TODO: this should be prefound, and set as the value that allows the slides to not move down with the effect of gravity.// how much  oscillation be allowed

    //TODO: Dont change the static variables during running pls

    // PID Gains
    private static double p = 0.005;  // Initial P gain idk its guessitmeeaeated
    private static double d = 0.0001;  // Initial D gain idkk its guessitmated

    //target
    private static double targetPositionTicks = 2000;

    //misc stuf
    private double previousError = 0;
    private double lastErrorChangeRate = 0;

    @Override
    public void runOpMode() {
        // Initialize hardware and PID controller
        slideMotor = hardwareMap.get(DcMotor.class, "slideMotor");
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        pidController = new PIDController(p, 0, d);
        pidController.setSetPoint(targetPositionTicks);

        waitForStart();

        while (opModeIsActive()) {
            controlLoop();
            //wait the defines Refresh rate
            sleep(refreshRate);
        }
    }

    private void controlLoop() {
        int currentTicks = slideMotor.getCurrentPosition();

        double error = targetPositionTicks - currentTicks;

        double derivative = (error - previousError) / (refreshRate / 1000.0); // Convert ms to seconds

        //set power
        double power = pidController.calculate(currentTicks,targetPositionTicks);
        power += f;
        slideMotor.setPower(Range.clip(power, -1, 1));

        //tune
        selfTunePD(error, derivative);

        //update stuff
        previousError = error;

        // Telemetry for debugging
        telemetry.addData("TargetPos", targetPositionTicks);
        telemetry.addData("CurrentPos", currentTicks);
        telemetry.addData("Error", error);
        telemetry.addData("P", p);
        telemetry.addData("D", d);
        telemetry.update();
    }

    private void selfTunePD(double error, double derivative) {
        //make P if error too big
        if (Math.abs(error) > errorBuffer) {
            p += adjustP(error);
            pidController.setP(p); //set to new P
        }

        // Adjust D gain for oscillation
        double errorChangeRate = derivative / Math.abs(error);
        if (Math.abs(errorChangeRate - lastErrorChangeRate) > oscillationBuffer) {
            d += 0.005 * Math.signum(errorChangeRate);
            pidController.setD(d); //set to new D
        }

        lastErrorChangeRate = errorChangeRate;
    }

    private double adjustP(double error) {
        // Simple proportional gain adjustment (placeholder)
        return 0.001 * Math.signum(error);
    }
}