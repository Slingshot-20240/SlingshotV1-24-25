package org.firstinspires.ftc.teamcode.fsm.archived;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.mechanisms.drive.DriveTrain;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.mechanisms.specimen.SpecimenClaw;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

public class BoopPracticeFSM {

    BoopPracticeStates state;
    Robot robot;
    Intake intake;
    Outtake outtake;
    SpecimenClaw specClaw;
    DriveTrain dt;

    private double startTime = 0.0;
    private ElapsedTime time;

    private GamepadMapping controls;

    public BoopPracticeFSM(Robot robot, GamepadMapping controls) {
        this.robot = robot;
        this.intake = robot.intake;
        this.outtake = robot.outtake;
        this.specClaw = robot.specimenClaw;
        this.dt = robot.drivetrain;
        state = BoopPracticeStates.BASE_STATE;
        time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        startTime = time.milliseconds();
        this.controls = controls;
    }

    public void update() {
        controls.update();
        dt.update();

        switch (state) {
            case BASE_STATE:

                if(controls.highBasket.value()) {

                    //controls.flipBucket.set(false);
                    outtake.bucketTilt();
                    state = BoopPracticeStates.HIGH_BASKET_STATE;

                }
                if(controls.lowBasket.value()) {

                    //controls.flipBucket.set(false);
                    outtake.bucketTilt();
                    state = BoopPracticeStates.LOW_BASKET_STATE;
                }

                if(controls.transfer.locked()){
                    state = BoopPracticeStates.TRANSFER_STATE;
                }
                if (controls.extend.value()){
                    state = BoopPracticeStates.INTAKE_STATE;

                }
                if(controls.openClaw.value()){
                    specClaw.closeClaw();
                }
                else if (!controls.openClaw.value()){
                    specClaw.openClaw();
                }
                if (controls.scoreSpec.value()){
                    state = BoopPracticeStates.SPEC_STATE;
                }

                // extendo in
                intake.extendoFullRetract();
                //pivot up
                intake.activeIntake.flipUp();
                //bucket down
                outtake.bucketToReadyForTransfer();
                //slides down
                outtake.returnToRetracted();

                break;
            case HIGH_BASKET_STATE:

                //extendo in
                intake.extendForOuttake();
                //pivot up
                intake.activeIntake.flipUp();
                //slides high pos
                outtake.extendToHighBasket();
                //claw open
                specClaw.openClaw();
                //bucket tilted


                if (controls.flipBucket.value()) {
                    outtake.bucketDeposit();
                }
                if (!controls.highBasket.value()) {
                    // return to base state
                    state = BoopPracticeStates.BASE_STATE;
                    controls.extend.set(false);
                    controls.flipBucket.set(false);
                    controls.lowBasket.set(false);
                }
                break;
            case LOW_BASKET_STATE:

                intake.extendForOuttake();
                //pivot up
                intake.activeIntake.flipUp();
                //slides high pos
                outtake.extendToLowBasket();
                //claw open
                specClaw.openClaw();
                //bucket tilted


                if (controls.flipBucket.value()){
                    outtake.bucketDeposit();
                }
                if (!controls.lowBasket.value()) {
                    // return to base state
                    state = BoopPracticeStates.BASE_STATE;
                    controls.extend.set(false);
                    controls.flipBucket.set(false);
                    controls.lowBasket.set(false);
                }
                break;
            case TRANSFER_STATE:

                //pivot up

                //bucket down
                outtake.returnToRetracted();
                intake.activeIntake.transferSample();

                if (!controls.transfer.locked()){
                    intake.activeIntake.transferOff();
                    state = BoopPracticeStates.BASE_STATE;
                }
                break;

            case SPEC_STATE:
                //slides up
                outtake.extendToSpecimenHighRack();
                if(!controls.scoreSpec.value()){
                    state = BoopPracticeStates.SPECIMEN_RELEASE_STATE;
                    startTime = time.milliseconds();
                }
                break;
            case SPECIMEN_RELEASE_STATE:
                if(time.milliseconds() - startTime >= 200 && time.milliseconds() - startTime <= 400) {
                    // open claw
                    specClaw.openClaw();
                } else if (time.milliseconds() - startTime > 400) {
                    // move to base state
                    controls.resetMultipleControls(controls.scoreSpec, controls.openClaw);
                    state  = BoopPracticeStates.BASE_STATE;
                }

                break;
            case INTAKE_STATE:
                //extendo out
                intake.extendoFullExtend();

                //slides down
                outtake.returnToRetracted();
                if (controls.intakeOnToIntake.locked()){
                    intake.activeIntake.flipDownFull();
                    intake.activeIntake.motorRollerOnToIntake();
                }

                else if (controls.toClear.locked()){
                    intake.activeIntake.flipDownToClear();
                    if(controls.clear.value()){
                        intake.activeIntake.motorRollerOnToClear();
                    }
                    else if(!controls.clear.value()){
                        intake.activeIntake.motorRollerOff();
                    }
                }
                else if (!controls.intakeOnToIntake.locked() || !controls.toClear.locked()) {
                    intake.activeIntake.flipUp();
                    intake.activeIntake.motorRollerOff();
                    controls.clear.set(false);
                }
                if(!controls.extend.value()){
                    state = BoopPracticeStates.BASE_STATE;
                }
                break;


        }
    }
    public enum BoopPracticeStates {
        INIT("init"),
        BASE_STATE("base"),
        INTAKE_STATE("intake"),
        TRANSFER_STATE("transfer"),
        SPEC_STATE("Specimen"),
        SPECIMEN_RELEASE_STATE("SpecOut"),
        HIGH_BASKET_STATE("high basket"),
        LOW_BASKET_STATE("low basket");

        private final String state;
        BoopPracticeStates(String state) {
            this.state = state;
        }
        public String getState() {
            return state;
        }

    }
}
