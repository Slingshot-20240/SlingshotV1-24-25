package org.firstinspires.ftc.teamcode.mechanisms.drive;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;
import org.firstinspires.ftc.teamcode.misc.PIDFControllerEx;

public class DriveTrain {

    // 435 -> TODO: Number Souren wants us to remember
    private DcMotorEx leftFront;
    private DcMotorEx rightFront;
    private DcMotorEx leftBack;
    private DcMotorEx rightBack;
    private IMU imu;

    private Telemetry telemetry;
    private GamepadMapping controls;

    private double newX = 0;
    private double newY = 0;
    private double targetAngle = 0;

    // public while being tuned on dashboard
    public static double turnP = 0.013;
    public static double turnI = 0;
    public static double turnD = 0.0001;
    public static double turnF = 0;
    private PIDFControllerEx turnController = new PIDFControllerEx(turnP, turnI, turnD, turnF);

    private DriveMode driveMode;
    private boolean lockedHeadingMode = false;
    private double slowMultiplier = 1;

    public DriveTrain(HardwareMap hardwareMap, IMU imu, Telemetry telemetry, GamepadMapping controls){
        // motors for slingshot bot

        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftFront.setDirection(DcMotorEx.Direction.FORWARD);
        leftFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        rightFront.setDirection(DcMotorEx.Direction.REVERSE);
        rightFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
        leftBack.setDirection(DcMotorEx.Direction.FORWARD);
        leftBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
        rightBack.setDirection(DcMotorEx.Direction.REVERSE);
        rightBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

         // motors for papaya (test bot)
//        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
//        leftFront.setDirection(DcMotorEx.Direction.REVERSE);
//        leftFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
//
//        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
//        rightFront.setDirection(DcMotorEx.Direction.FORWARD);
//        rightFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
//
//        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
//        leftBack.setDirection(DcMotorEx.Direction.REVERSE);
//        leftBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
//
//        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
//        rightBack.setDirection(DcMotorEx.Direction.FORWARD);
//        rightBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        this.imu = imu;

        this.telemetry = telemetry;
        this.controls = controls;

        driveMode = DriveMode.ROBO_CENTRIC;
        // outtake = robot.outtake;
    }

    // this is for testing, only used by testing methods
    public DriveTrain(DcMotorEx lF, DcMotorEx rF, DcMotorEx lB, DcMotorEx rB, IMU imu) {
        leftFront = lF;
        rightFront = rF;
        leftBack = lB;
        rightBack = rB;
        this.imu = imu;

        driveMode = DriveMode.ROBO_CENTRIC;
    }

    public void update() {
        double strafe = controls.strafe;
        double drive = controls.drive;
        double turn = controls.turn;

        if (driveMode.equals(DriveMode.FIELD_CENTRIC)) {
            moveFieldCentric(strafe, drive, turn, getHeading());
        } else if (driveMode.equals(DriveMode.ROBO_CENTRIC)){
            moveRoboCentric(-strafe, drive, -turn);
        }

        if (controls.lockedMode.value()) {
            lockedHeadingMode = !lockedHeadingMode;
        }

        if (lockedHeadingMode) {
            if (controls.lock180) {
                setTargetAngle(90); // add/subtract 90 to each value bc of imu
            } else if (controls.lock270) {
                setTargetAngle(180);
            } else if (controls.lock360) {
                setTargetAngle(270);
            } else if (controls.lock90) {
                setTargetAngle(360);
            }
        }
    }

    public void moveRoboCentric(double strafe, double drive, double turn){
        if (lockedHeadingMode) {
            changeTargetAngleWithJoystick(turn);

            double currentAngle = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            turn = lockHeading(targetAngle, currentAngle);
        }
        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(drive) + Math.abs(strafe) + Math.abs(turn), 1);

        leftFront.setPower(((drive + strafe + turn) / denominator) * slowMultiplier);
        leftBack.setPower(((drive - strafe + turn) / denominator) * slowMultiplier);
        rightFront.setPower(((drive - strafe - turn) / denominator) * slowMultiplier);
        rightBack.setPower(((drive + strafe - turn) / denominator) * slowMultiplier);
    }

    public void moveFieldCentric(double inX, double inY, double turn, double currentAngle){
        currentAngle += 90;
        double radian = Math.toRadians(currentAngle);
        double cosTheta = Math.cos(radian);
        double sinTheta = Math.sin(radian);
        newX = (-inX * sinTheta) - (inY * cosTheta);
        newY = (-inX * cosTheta) + (inY * sinTheta);

        moveRoboCentric(newX,newY,-turn);
    }

    // TODO implement this so that the bot will automatically position itself to the bucket
    public void positionToBucket() {

    }

    public double getHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    public double angleWrap(double angle) {
        angle = Math.toRadians(angle);
        // Changes any angle between [-179,180] degrees
        // If rotation is greater than half a full rotation, it would be more efficient to turn the other way
        while (Math.abs(angle) > Math.PI) {
            angle -= 2 * Math.PI * (angle > 0 ? 1 : -1); // if angle > 0 * 1, < 0 * -1
        }
        return Math.toDegrees(angle);
    }

    // our class
    public double lockHeading(double targetAngle, double currentHeading) {
        double error = angleWrap(targetAngle - currentHeading);
        double pid = turnController.calculate(error, false);
        double power = pid + turnF;
        return -power;
    }
    // This for sure works
//    public double lockHeading(double targetAngle, double currentHeading) {
//        double error = angleWrap(targetAngle - currentHeading);
//        double pid = turnController.calculate(0, -error);
//        double power = pid + turnF;
//        return -power;
//    }

    public void changePID(double inP, double inI, double inD, double inF){
        turnP = inP; turnI = inI; turnD = inD; turnF = inF;
        turnController.setPIDF(inP, inI, inD, inF);
    }

    public void setTargetAngle(double targetAngle) {
        this.targetAngle = angleWrap(targetAngle);
    }

    public double getTargetAngle() { return angleWrap(targetAngle);}

    public void changeTargetAngleWithJoystick(double joystickTurn) {
        if (joystickTurn == 0) {
            setTargetAngle(targetAngle);
        } else {
            double newTargetAngle = targetAngle + joystickTurn * 7;
            if(newTargetAngle > 360) {
                newTargetAngle -= 360;
            } else if (newTargetAngle < 0) {
                newTargetAngle += 360;
            }
            setTargetAngle(newTargetAngle);
        }
    }

    public void setSlowMultiplier(double slow) {
        slowMultiplier = slow;
    }

    public enum DriveMode {
        FIELD_CENTRIC,
        ROBO_CENTRIC
    }
}