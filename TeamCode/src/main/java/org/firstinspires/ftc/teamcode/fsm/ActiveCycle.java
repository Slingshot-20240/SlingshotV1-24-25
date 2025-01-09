package org.firstinspires.ftc.teamcode.fsm;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.mechanisms.specimen.SpecimenClaw;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

public class ActiveCycle {
    private Intake intake;
    private Outtake outtake;
    private GamepadMapping controls;
    private Robot robot;
    private SpecimenClaw specimenClaw;
    private ActiveCycle.TransferState transferState;
    private Telemetry telemetry;
    private ElapsedTime loopTime;
    private double startTime;
    public ActiveCycle(Telemetry telemetry, GamepadMapping controls, Robot robot) {
        this.robot = robot;
        this.intake = robot.intake;
        this.outtake = robot.outtake;
        this.controls = controls;
        this.specimenClaw = robot.specimenClaw;

        this.telemetry = telemetry;

        transferState = ActiveCycle.TransferState.BASE_STATE;

        loopTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        startTime = loopTime.milliseconds();
    }
    public void activeIntakeUpdate() {
        controls.update();
        robot.drivetrain.update();

        switch (transferState) {
            case BASE_STATE:
                robot.hardwareSoftReset();
                transferState = ActiveCycle.TransferState.EXTENDO_FULLY_RETRACTED;
                break;
            case EXTENDO_FULLY_RETRACTED:
                // have to constantly set power of slide motors back
                outtake.returnToRetracted();
                if (controls.extend.value()) {
                    transferState = ActiveCycle.TransferState.EXTENDO_FULLY_EXTENDED;
                    controls.resetOuttakeControls();
                    //intake.extendoFullExtend();
                } else if (controls.transfer.locked()) {
                    transferState = TransferState.TRANSFERING;
                    controls.flipBucket.set(false);
                    //startTime = loopTime.milliseconds();
                } else if (controls.highBasket.value()) {
                    outtake.bucketTilt();
                    transferState = ActiveCycle.TransferState.HIGH_BASKET;
                    controls.flipBucket.set(false);
                } else if (controls.lowBasket.value()) {
                    outtake.bucketTilt();
                    transferState = ActiveCycle.TransferState.LOW_BASKET;
                    controls.flipBucket.set(false);
                }
                if (controls.openClaw.value()) {
                    transferState = TransferState.OPEN_CLAW;
                    controls.flipBucket.set(false);
                }
                if (controls.scoreSpec.value()) {
                    outtake.extendToRemoveSpecFromWall();
                    specimenClaw.closeClaw();
                    transferState = TransferState.SPEC_SCORING;
                    controls.flipBucket.set(false);
                }
                if (controls.intakeOnToIntake.locked()) {
                    intake.activeIntake.motorRollerOnToIntake();
                } else {
                    intake.activeIntake.motorRollerOff();
                }
                break;
            case EXTENDO_FULLY_EXTENDED:
                controls.resetSpecControls();
                outtake.returnToRetracted();
                intake.extendoFullExtend();
                if (!controls.extend.value()) {
                    intake.extendoFullRetract();
                    intake.activeIntake.flipToTransfer();
                    controls.transfer.set(false);
                    controls.resetOuttakeControls();
                    transferState = TransferState.EXTENDO_FULLY_RETRACTED;
                }
                if (controls.intakeOnToIntake.locked() || controls.toClear.locked()) {
                    transferState = TransferState.INTAKING;
                    controls.flipBucket.set(false);
                }
                if (controls.flipBucket.value()) {
                    outtake.bucketDeposit();
                } else {
                    outtake.bucketToReadyForTransfer();
                }
                break;
            case INTAKING:
                outtake.returnToRetracted();
                if (!controls.extend.value()) {
                    transferState = TransferState.EXTENDO_FULLY_EXTENDED;
                    controls.clear.set(false);
                } else if (controls.intakeOnToIntake.locked()) {
                    intake.activeIntake.flipDownFull();
                    intake.activeIntake.motorRollerOnToIntake();
                } else if (controls.toClear.locked()) {
                    intake.activeIntake.flipDownToClear();
                    if (controls.clear.value()) {
                        intake.activeIntake.motorRollerOnToClear();
                    } else {
                        intake.activeIntake.motorRollerOff();
                    }
                } else if (!controls.intakeOnToIntake.locked()) {
                    intake.activeIntake.flipUp();
                    intake.activeIntake.transferOff();
                } else if (!controls.toClear.locked()) {
                    intake.activeIntake.flipUp();
                    intake.activeIntake.transferOff();
                }
                break;
            case TRANSFERING:
                outtake.returnToRetracted();
//                if (loopTime.milliseconds() - startTime <= 700){
//                    intake.activeIntake.transferSample();
//
//                    if (controls.extend.value()) {
//                        transferState = ActiveCycle.TransferState.EXTENDO_FULLY_EXTENDED;
//                        intake.extendoFullExtend();
//                        controls.transfer.set(false);
//                        break;
//                    }
//                } else if (loopTime.milliseconds() - startTime > 700) {
//                    transferState = ActiveCycle.TransferState.EXTENDO_FULLY_RETRACTED;
//                    controls.transfer.set(false);
//                }
                intake.activeIntake.transferSample();
                if (!controls.transfer.locked()) {
                    intake.activeIntake.transferOff();
                    transferState = TransferState.EXTENDO_FULLY_RETRACTED;
                }
                break;
            case HIGH_BASKET:
                intake.activeIntake.pivotUpForOuttake();
                intake.extendForOuttake();
                outtake.extendToHighBasket();
                if (controls.flipBucket.value()) {
                    outtake.bucketDeposit();
                }
                if (!controls.highBasket.value()) {
                    transferState = ActiveCycle.TransferState.SLIDES_RETRACTED;
                }
                if (controls.extend.value()) {
                    intake.extendoFullExtend();
                    transferState = TransferState.EXTENDO_FULLY_EXTENDED;
                    controls.resetOuttakeControls();
                }
                break;
            case LOW_BASKET:
                intake.activeIntake.pivotUpForOuttake();
                intake.extendForOuttake();
                outtake.extendToLowBasket();
                if (controls.flipBucket.value()) {
                    outtake.bucketDeposit();
                }
                if (!controls.lowBasket.value()) {
                    transferState = ActiveCycle.TransferState.SLIDES_RETRACTED;
                }
                if (controls.extend.value()) {
                    intake.extendoFullExtend();
                    transferState = TransferState.EXTENDO_FULLY_EXTENDED;
                    controls.resetOuttakeControls();
                }
                break;
            case SLIDES_RETRACTED:
                controls.resetOuttakeControls();
                controls.resetMultipleControls(controls.transfer, controls.extend, controls.scoreSpec);
                // could also do to base state
                outtake.bucketToReadyForTransfer();
                outtake.returnToRetracted();
                intake.extendoFullRetract();
                transferState = ActiveCycle.TransferState.EXTENDO_FULLY_RETRACTED;
                break;
//            case HANGING:
//                outtake.hang();
//                if (!controls.L1hang.value()) {
//                    outtake.bucketToReadyForTransfer();
//                    transferState = ActiveCycle.TransferState.SLIDES_RETRACTED;
//                }
//                break;
//            case PUSH_OUT_BAD_COLOR:
//                if (loopTime.milliseconds() - startTime <= 1000 && loopTime.milliseconds() - startTime >= 0) {
//                    intake.activeIntake.pushOutSample();
//                } else {
//                    intake.activeIntake.transferOff();
//                    transferState = ActiveCycle.TransferState.INTAKING;
//                }
//                break;
            case SPEC_SCORING:
                outtake.extendToSpecimenHighRack();
                if (!controls.scoreSpec.value()) {
                    transferState = TransferState.SPEC_RETRACTING;
                    startTime = loopTime.milliseconds();
                }
                if (controls.highBasket.value()) {
                    outtake.bucketTilt();
                    transferState = ActiveCycle.TransferState.HIGH_BASKET;
                    controls.resetMultipleControls(controls.flipBucket, controls.scoreSpec);
                }
                if (controls.lowBasket.value()) {
                    outtake.bucketTilt();
                    transferState = ActiveCycle.TransferState.LOW_BASKET;
                    controls.resetMultipleControls(controls.flipBucket, controls.scoreSpec);
                }
                break;
            case SPEC_RETRACTING:
                outtake.returnToRetracted();

                if (loopTime.milliseconds() - startTime <= 400 && loopTime.milliseconds() - startTime >= 200){
                    specimenClaw.openClaw();
                }
                else if (loopTime.milliseconds() - startTime > 400) {
                    transferState = ActiveCycle.TransferState.EXTENDO_FULLY_RETRACTED;
                    controls.openClaw.set(false);
                    controls.resetOuttakeControls();
                }
                break;
            case OPEN_CLAW:
                outtake.returnToRetracted();
                if (controls.openClaw.value()) {
                    //outtake.returnToRetracted();
                    specimenClaw.openClaw();
                }
                if (!controls.openClaw.value()) {
                    //outtake.extendToRemoveSpecFromWall();
                    specimenClaw.closeClaw();
                }
                if (controls.extend.value()) {
                    //outtake.returnToRetracted();
                    transferState = TransferState.EXTENDO_FULLY_EXTENDED;
                }
                if (controls.scoreSpec.value()) {
                    //outtake.returnToRetracted();
                    specimenClaw.closeClaw();
                    transferState = TransferState.SPEC_SCORING;
                }
                if (controls.highBasket.risingEdge()) {
                    transferState = TransferState.SLIDES_RETRACTED;
                    controls.openClaw.set(false);
                }
                if (controls.lowBasket.risingEdge()) {
                    transferState = TransferState.SLIDES_RETRACTED;
                    controls.openClaw.set(false);
                }
                break;
        }
    }

    public TransferState getState() {
        return transferState;
    }
    public void setState(TransferState newState) {
        transferState = newState;
    }

    public enum TransferState {
        BASE_STATE("BASE_STATE"),
        EXTENDO_FULLY_RETRACTED("EXTENDO_FULLY_RETRACTED"),
        EXTENDO_FULLY_EXTENDED("EXTENDO_FULLY_EXTENDED"),
        INTAKING("INTAKING"),
        TRANSFERING("TRANSFERING"),
        SLIDES_RETRACTED("SLIDES_RETRACTED"),
        HIGH_BASKET("HIGH_BASKET"),
        LOW_BASKET("LOW_BASKET"),
        OPEN_CLAW("OPEN_CLAW"),
        SPEC_SCORING("SPEC_SCORING"),
        SPEC_RETRACTING("SPEC_RETRACTING"),
        // PUSH_OUT_BAD_COLOR("PUSH_OUT_BAD_COLOR"),
        HOLD_SAMPLE("HOLD_SAMPLE"),
        HANGING("HANGING");

        private String state;

        TransferState(String stateName) {
            state = stateName;
        }

        public String stateName() {
            return state;
        }
    }
}
