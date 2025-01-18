package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.mechanisms.specimen.SpecimenClaw;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous
public class Auton40 extends LinearOpMode {

    private GamepadMapping controls;
    private Robot robot;
    private static IntakeConstants.ActiveIntakeStates activeIntakeStates;
    private Intake intake;
    private Outtake outtake;
    private SpecimenClaw specimenClaw;
    @Override
    public void runOpMode() throws InterruptedException {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
        intake = robot.intake;
        outtake = robot.outtake;
        specimenClaw = robot.specimenClaw;
        robot.outtake.resetEncoders();
        moveLift(0);
        outtake.bucketToReadyForTransfer();
        intake.extendoFullRetract();
        intake.activeIntake.flipUp();
        specimenClaw.closeClaw();

        //robot.hardwareHardReset();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);


        Pose2d startPose = new Pose2d(12, -64.5, Math.toRadians(270));

        drive.setPoseEstimate(startPose);

        TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPose)
                //preloaded spec
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    moveLift(1250);
                })
                .lineToConstantHeading(new Vector2d(11,-32))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    moveLift(825);
                })
                .UNSTABLE_addTemporalMarkerOffset(0.15, () -> {
                    //open claw (preloaded spec)
                    specimenClaw.openClaw();
                })
                .waitSeconds(.15)

                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
                    moveLift(0);
                })


                //pickup #1
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    semi-extend
                    moveExtendo(0.19);
                })
                .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                                            pivot intake
                    intake.activeIntake.flipDownFull();
                    intake.activeIntake.motorRollerOnToIntake();
                })


                .splineToSplineHeading(new Pose2d(24,-33.6 , Math.toRadians(18)), Math.toRadians(18))
                .forward(12)
                .strafeRight(5)
                .waitSeconds(.1)


                //O-zone #1
                .setReversed(true)
                .UNSTABLE_addDisplacementMarkerOffset(10, () -> {
//                                            extendo full
                    intake.extendoFullExtend();
                })
                .lineToSplineHeading(new Pose2d(20,-52, Math.toRadians(330)))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            run transfer
                    intake.activeIntake.rollerMotor.setPower(.7);
                })
                .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
                // intake off
                    intake.activeIntake.motorRollerOff();
                    intake.extendoFullRetract();
                })
                .waitSeconds(0.2)



                //pickup #2
                .UNSTABLE_addTemporalMarkerOffset(0.16, () -> {
//                                            intake
                    intake.activeIntake.motorRollerOnToIntake();

                })

                .setReversed(false)
                .UNSTABLE_addDisplacementMarkerOffset(8.5, () -> {
//                                            intake
                    moveExtendo(0);
                    intake.activeIntake.flipDownFull();
                })
                .lineToLinearHeading(new Pose2d(35,-35, Math.toRadians(33)))
                .strafeRight(5)
                .waitSeconds(0.1)

                //O-zone #2
                .setReversed(true)
                .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
                    intake.extendoFullExtend();
                })
                .lineToSplineHeading(new Pose2d(30,-47, Math.toRadians(320)))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    intake.activeIntake.rollerMotor.setPower(.7);
                })
                .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
                    intake.activeIntake.motorRollerOff();
                    intake.extendoFullRetract();
                })
                .waitSeconds(0.2)



//                //pickup #3
//
                .UNSTABLE_addTemporalMarkerOffset(0.16, () -> {
                    intake.activeIntake.motorRollerOnToIntake();
                })
                .setReversed(false)
                .UNSTABLE_addDisplacementMarkerOffset(7, () -> {
//                                            intake
                    moveExtendo(0.04);
                })
                .lineToLinearHeading(new Pose2d(42.25,-36, Math.toRadians(29)))
                .waitSeconds(0.3)
                .strafeRight(5)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    moveExtendo(0.18);
                })
                .waitSeconds(0.2)

                //O-zone #3
                .setReversed(true)
                .lineToSplineHeading(new Pose2d(40,-51, Math.toRadians(308)))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    intake.activeIntake.rollerMotor.setPower(.7);
                })
                .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
                    intake.activeIntake.flipToTransfer();
                    intake.activeIntake.motorRollerOff();
                })
                .waitSeconds(0.2)


//                //go back to hp #1
                .lineToLinearHeading(new Pose2d(40,-56,Math.toRadians(90)))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    intake.extendoFullRetract();
                })
                .back(14)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //close claw
                    specimenClaw.closeClaw();
                })
                .waitSeconds(0.15)
//
                //go to box #1
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    moveLift(1250);
                })
                .lineToSplineHeading(new Pose2d(-4,-41,Math.toRadians(270.05)))
                .back(5)

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    moveLift(800);
                })
                .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
                    //open claw (spec)
                    specimenClaw.openClaw();
                })
                .waitSeconds(.2)
                .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
                    moveLift(0);
                })
                .setReversed(false)


                //go back to hp #2
                .lineToLinearHeading(new Pose2d( 40,-60,Math.toRadians(90)))
                .back(16)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //close claw
                    specimenClaw.closeClaw();
                })
                .waitSeconds(0.15)


                //go to box #2
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    moveLift(1250);
                })
                .lineToSplineHeading(new Pose2d(-2,-42.5,Math.toRadians(270.05)))

                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    moveLift(825);
                })
                .UNSTABLE_addTemporalMarkerOffset(0.35, () -> {
                    //open claw (spec)
                    specimenClaw.openClaw();
                })
                .waitSeconds(.2)
                .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
                    moveLift(0);
                })
                .setReversed(false)


                //go back to hp #3
                .lineToLinearHeading(new Pose2d( 40,-60,Math.toRadians(90)))
                .back(18)
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    //close claw
                    specimenClaw.closeClaw();
                })
                .waitSeconds(0.15)

                //go to box #3
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    moveLift(1250);
                })
                .lineToSplineHeading(new Pose2d(0,-42.5,Math.toRadians(270.05)))
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {

                    moveLift(825);
                })
                .UNSTABLE_addTemporalMarkerOffset(0.35, () -> {
                    //open claw (spec)
                    specimenClaw.openClaw();
                    moveLift(0);

                })
                .waitSeconds(.15)
                .lineTo(new Vector2d(40,-80))

                .build();
        waitForStart();

        if (!isStopRequested())
            drive.followTrajectorySequence(trajSeq);
    }

    public void moveLift(int ticks){
        robot.outtake.outtakeSlideLeft.setTargetPosition(ticks);
        robot.outtake.outtakeSlideRight.setTargetPosition(ticks);
        robot.outtake.outtakeSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.outtake.outtakeSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.outtake.outtakeSlideLeft.setPower(1);
        robot.outtake.outtakeSlideRight.setPower(1);
    }
    public void moveExtendo(double pos){
        robot.intake.leftExtendo.setPosition(pos);
        robot.intake.rightExtendo.setPosition(pos);
    }

}