package org.firstinspires.ftc.teamcode.teleop.testers.misc;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.fsm.ActiveCycle;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.mechanisms.specimen.SpecimenClaw;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@TeleOp
public class PresTele extends OpMode {
    private GamepadMapping controls;
    private ActiveCycle cycle;
    private Robot robot;
    private Intake intake;
    private Outtake outtake;
    private SpecimenClaw specimenClaw;
    private TransferState transferState;
    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, telemetry, controls);
        cycle = new ActiveCycle(telemetry, controls, robot);

        intake = robot.intake;
        outtake = robot.outtake;
        specimenClaw = robot.specimenClaw;

        robot.outtake.setMotorsToTeleOpMode();
        robot.outtake.resetEncoders();

        robot.intake.resetHardware();
        robot.outtake.resetHardware();

        transferState = TransferState.BASE_STATE;
    }

    @Override
    public void loop() {
        controls.presModeUpdate();

        switch (transferState) {
            case BASE_STATE:
                robot.hardwareSoftReset();
                transferState = TransferState.EXTENDO_FULLY_RETRACTED;
                break;
            case EXTENDO_FULLY_RETRACTED:
                // have to constantly set power of slide motors back
                outtake.returnToRetracted();
                if (controls.extend.value()) {
                    transferState = TransferState.EXTENDO_FULLY_EXTENDED;
                    controls.resetOuttakeControls();
                    //intake.extendoFullExtend();
                } else if (controls.transfer.locked()) {
                    transferState = TransferState.TRANSFERING;
                    controls.flipBucket.set(false);
                    //startTime = loopTime.milliseconds();
                } else if (controls.highBasket.value()) {
                    outtake.bucketTilt();
                    transferState = TransferState.HIGH_BASKET;
                    controls.flipBucket.set(false);
                } else if (controls.lowBasket.value()) {
                    outtake.bucketTilt();
                    transferState = TransferState.LOW_BASKET;
                    controls.flipBucket.set(false);
                }
                if (controls.intakeOnToIntake.locked()) {
                    intake.activeIntake.motorRollerOnToIntake();
                } else {
                    intake.activeIntake.motorRollerOff();
                }
                break;
            case EXTENDO_FULLY_EXTENDED:
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
                    transferState = TransferState.SLIDES_RETRACTED;
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
                    transferState = TransferState.SLIDES_RETRACTED;
                }
                if (controls.extend.value()) {
                    intake.extendoFullExtend();
                    transferState = TransferState.EXTENDO_FULLY_EXTENDED;
                    controls.resetOuttakeControls();
                }
                break;
            case SLIDES_RETRACTED:
                controls.resetOuttakeControls();
                controls.resetMultipleControls(controls.transfer, controls.extend);
                // could also do to base state
                outtake.bucketToReadyForTransfer();
                outtake.returnToRetracted();
                intake.extendoFullRetract();
                transferState = TransferState.EXTENDO_FULLY_RETRACTED;
                break;
        }
    }

        public enum TransferState {
            BASE_STATE("BASE_STATE"),
            EXTENDO_FULLY_RETRACTED("EXTENDO_FULLY_RETRACTED"),
            EXTENDO_FULLY_EXTENDED("EXTENDO_FULLY_EXTENDED"),
            INTAKING("INTAKING"),
            TRANSFERING("TRANSFERING"),
            SLIDES_RETRACTED("SLIDES_RETRACTED"),
            HIGH_BASKET("HIGH_BASKET"),
            LOW_BASKET("LOW_BASKET");

            private String state;

            TransferState(String stateName) {
                state = stateName;
            }

            public String stateName() {
                return state;
            }
        }
}
