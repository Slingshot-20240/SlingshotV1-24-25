package org.firstinspires.ftc.teamcode.mechanisms.outtake;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

public class Outtake {
    // SLIDES
    private PIDController controller;
    public DcMotorEx outtakeSlideRight;
    public DcMotorEx outtakeSlideLeft;
    private static double p, i, d; //has to be tuned
    private static double f; // usually mass moved * constant G
    // BUCKET
    public Servo rightBucketServo;
    public Servo leftBucketServo;

    // OTHER
    Telemetry telemetry;
    GamepadMapping controls;

    public Outtake(HardwareMap hardwareMap, int direction, double inP, double inI, double inD, double inF, Telemetry telemetry,
    GamepadMapping controls){
        outtakeSlideLeft = hardwareMap.get(DcMotorEx.class, "slideLeft");
        outtakeSlideRight = hardwareMap.get(DcMotorEx.class, "slideRight");
        outtakeSlideLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        outtakeSlideRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        rightBucketServo = hardwareMap.get(Servo.class, "rightBucketServo");
        leftBucketServo = hardwareMap.get(Servo.class, "leftBucketServo");

        if(direction == 0){
            outtakeSlideLeft.setDirection(DcMotorEx.Direction.FORWARD);
            outtakeSlideRight.setDirection(DcMotorEx.Direction.REVERSE);
        }else{
            outtakeSlideLeft.setDirection(DcMotorEx.Direction.REVERSE);
            outtakeSlideRight.setDirection(DcMotorEx.Direction.FORWARD);
        }

        controller = new PIDController(p,i,d);

        p = inP; i = inI; d = inD; f = inF;

        this.telemetry = telemetry;
        this.controls = controls;
    }

    // this is for J-Unit testing only
    public Outtake(DcMotorEx slidesMotorLeft, DcMotorEx slidesMotorRight, Servo rightBucketServo, Servo leftBucketServo, PIDController controller) {
        this.outtakeSlideLeft = slidesMotorLeft;
        this.outtakeSlideRight = slidesMotorRight;
        this.rightBucketServo = rightBucketServo;
        this.leftBucketServo = leftBucketServo;
        this.controller = controller;
    }

    public void moveTicks(double target) {
        controller.setPID(p,i,d);
        int pos = outtakeSlideLeft.getCurrentPosition();
        double pid = controller.calculate(pos, target);
        double power = pid + f;
        outtakeSlideRight.setPower(power);
        outtakeSlideLeft.setPower(power);
    }

    public void changePIDF(double inP, double inI, double inD, double inF){
        p = inP; i = inI; d = inD; f = inF;
    }

    // so this should be the same for both motors?
    public int getPos(){
        return outtakeSlideLeft.getCurrentPosition();
    }

    public void extendToLowBasket() {
        moveTicks(OuttakeConstants.SlidePositions.LOW_BASKET.getSlidePos());
    }

    public void extendToHighBasket() {
        moveTicks(OuttakeConstants.SlidePositions.HIGH_BASKET.getSlidePos());
    }

    public void extendToSpecimenHighRack() {
        moveTicks(OuttakeConstants.SlidePositions.SPECIMEN_HIGH_RACK.getSlidePos()); // tune target obviously
    }

    public void depositToHP() {
        // this just flips bucket at slide pos 0
        moveTicks(OuttakeConstants.SlidePositions.RETRACTED.getSlidePos());
        bucketDeposit();
    }

    public void returnToRetracted() {
        moveTicks(OuttakeConstants.SlidePositions.RETRACTED.getSlidePos());
    }

    public void extendToRemoveSpecFromWall() {
        moveTicks(OuttakeConstants.SlidePositions.GRABBING_SPEC.getSlidePos());
    }

    public void resetHardware() {
        returnToRetracted();
        // other resetting bucket stuff here
        bucketToReadyForTransfer();
    }

    public void resetEncoders() {
        // reset slide motor encoders
        outtakeSlideLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeSlideRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        outtakeSlideLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        outtakeSlideRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void bucketToReadyForTransfer() {
        rightBucketServo.setPosition(OuttakeConstants.BucketPositions.TRANSFER_READY.getRightBucketPos());
        leftBucketServo.setPosition(OuttakeConstants.BucketPositions.TRANSFER_READY.getLeftBucketPos());
    }

    public void bucketDeposit() {
        rightBucketServo.setPosition(OuttakeConstants.BucketPositions.DEPOSIT.getRightBucketPos());
        leftBucketServo.setPosition(OuttakeConstants.BucketPositions.DEPOSIT.getLeftBucketPos());
    }

    public void bucketTilt() {
        rightBucketServo.setPosition(OuttakeConstants.BucketPositions.TILT.getRightBucketPos());
        leftBucketServo.setPosition(OuttakeConstants.BucketPositions.TILT.getLeftBucketPos());
    }

    public void hang() {
        moveTicks(OuttakeConstants.SlidePositions.HANG.getSlidePos());
        bucketDeposit();
    }

    public void setMotorsToTeleOpMode() {
        outtakeSlideLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        outtakeSlideRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void updateTelemetry() {
        telemetry.addData("right motor pos/ticks: ", outtakeSlideRight.getCurrentPosition());
        telemetry.addData("left motor pos/ticks: ", outtakeSlideLeft.getCurrentPosition());
    }
}
