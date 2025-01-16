package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.mechanisms.drive.DriveTrain;
import org.firstinspires.ftc.teamcode.mechanisms.misc.ReLocalizer;
import org.firstinspires.ftc.teamcode.mechanisms.specimen.SpecimenClaw;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;

public class Robot{
    // TODO stuff souren wants to remember:
    // - 435 motors
    // - 245 M belt (for intake)

    // odo:
    // 0 expansion hub -> encoder, parallel
    // 0 control hub -> rightBack, perpendicular

    // intake:
    // pivot (max) -> 1 on expansion hub
    // left linkage (max) -> 4 on control hub
    // right linkage (max) -> 0 on control hub
    // back roller (mini) -> 0 on expansion hub
    // roller motor -> 1 on expansion hub
    // analog encoder -> 0 on expansion analog ports
    // color sensor -> 1 on control hub i2c ports

    // wrist (gobilda torque) -> 2 on expansion hub
    // claw (mini) -> 0 on expansion hub
    // v4b (max) -> 1 on expansion hub

    // drivetrain:
    // rightBack = 0 control
    // rightFront = 1 control
    // leftBack = 3 control
    // leftFront = 2 control

    // outtake:
    // slideLeft = expansion 3
    // slideRight = expansion 2
    // bucketServoRight = 3 expansion
    // bucketServoLeft = 5 expansion

    // spec:
    // spec claw = 2 on control hub


    public DriveTrain drivetrain;
    public ReLocalizer ultraSonics;
    public IMU imu;
    public Outtake outtake;
    public Intake intake;
    public GamepadMapping controls;
    public SpecimenClaw specimenClaw;

    public Robot(HardwareMap hardwareMap, Telemetry telemetry, GamepadMapping controls) {
        imu = hardwareMap.get(IMU.class, "imu");
        // params for slingshot robot
                IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.LEFT));

 //          params for papaya (tester bot)
       // IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
             //   RevHubOrientationOnRobot.LogoFacingDirection.UP,
               // RevHubOrientationOnRobot.UsbFacingDirection.RIGHT));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);
        // will reset imu before auton only, in resetHardware

        this.controls = controls;
        drivetrain = new DriveTrain(hardwareMap, imu, telemetry, controls);
        intake = new Intake(hardwareMap, telemetry, controls);
        outtake = new Outtake(hardwareMap, 0, 0.012, 0, 0.0001, 0.03, telemetry, controls); // tune PID values
        //ultraSonics = new ReLocalizer(hardwareMap, imu);
        specimenClaw = new SpecimenClaw(hardwareMap);
    }

    // this is for junit testing only
    public Robot(GamepadMapping controls, DriveTrain drivetrain, Outtake outtake, Intake intake, SpecimenClaw specimenClaw) {
        this.controls = controls;
        this.drivetrain = drivetrain;
        this.outtake = outtake;
        this.intake = intake;
        this.specimenClaw = specimenClaw;
    }

//    public Pose2d reLocalize(){
//        double currentAngle = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) + 90;
//        double backDistance = ultraSonics.getBackDistance(currentAngle);
//        double sideDistance = ultraSonics.getSideDistance(currentAngle);
//        return new Pose2d(-72 + backDistance, -72 + sideDistance, Math.toRadians(currentAngle));
//    }

    // This is for EVERYTHING, to be called before auton and only before auton (or in the resetHardware auton class if we don't run auton)
    public void hardwareHardReset() {
        // reset outtake
        outtake.resetEncoders();
        outtake.resetHardware();
        // reset intake
        intake.resetHardware();
        // reset dt & ultrasonics
        imu.resetYaw();
        // reset specimen claw
        specimenClaw.openClaw();
    }

    // this is for teleop, when we ant to preserve encoder and sensor input
    public void hardwareSoftReset() {
        outtake.resetHardware();
        intake.resetHardware();
    }
}