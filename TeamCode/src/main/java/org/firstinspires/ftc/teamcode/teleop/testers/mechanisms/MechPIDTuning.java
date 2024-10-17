package org.firstinspires.ftc.teamcode.teleop.testers.mechanisms;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@TeleOp
@Config
public class MechPIDTuning extends OpMode {

    // private BasicSlides slide;
    // private BasicArm arm;
    private Outtake outtake;
    private Intake intake;
    private GamepadMapping controls;

    //TODO edit every variables value under this line b4 tuning
    //use only for arm
    //private double ticksPerDegree = 0;

    //for both
    // private String configName = "";
    public static int target = 0;
    public static double p = .01, i = 0, d = 0.00001, f = 0.0001;
    private int type = 0; // 0 is slides 1 is arm 2 is analog

    private Telemetry dashboardTelemetry;
    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        // slide = new BasicSlides(hardwareMap, configName, 0,p,i,d,f);
        // arm = new BasicArm(hardwareMap, configName, 0, p,i,d,f, ticksPerDegree);
        outtake = new Outtake(hardwareMap, 0, .01, 0, 0.00001, 0.0001, this.telemetry, controls);
        // intake = new Intake(hardwareMap, telemetry, controls);
        intake = new Intake(hardwareMap, telemetry, controls);
        dashboardTelemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        outtake.returnToRetracted();
        outtake.resetEncoders();
    }

    @Override
    public void loop() {
        dashboardTelemetry.addData("target: ", target);
        if(type == 0){
            outtake.moveTicks(target);
            outtake.changePIDF(p,i,d,f);
            dashboardTelemetry.addData("position: ", outtake.getPos());
        } else if (type == 2) {
            intake.pivotAnalog.runToPos(target);
            intake.pivotAnalog.changePIDF(p,i,d,f);
            dashboardTelemetry.addData("position: ", intake.pivotAnalog.getPosition());
        }
//        else{
//            arm.moveTicks(target);
//            arm.changePIDF(p,i,d,f);
//            dashboardTelemetry.addData("position: ", arm.getPos());
//        }
        dashboardTelemetry.update();
    }
}
// 2500 ticks max pos when starting at 0