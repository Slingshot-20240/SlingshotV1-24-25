package org.firstinspires.ftc.teamcode.misc.gamepad;

import com.qualcomm.robotcore.hardware.Gamepad;

public class GamepadMapping {

    private Gamepad gamepad1;
    private Gamepad gamepad2;

    // INTAKE
    // Extend/Retract Intake (Intake automatically runs, this extends linkage) -> left bumper

    // Pivot Down - comes out in the middle of extension, automatic
    // Pivot Out
    // Outtake bad sample (automatically does this)

    // OUTTAKE
    // Slides go up/down -> toggle
    // Bucket flips & releases sample -> one of the buttons on the right side

    // SCORING
    // slides increment pos -> button to latch on (button once to go up, again to latch)
    // claw releases/closes -> on misc controller, toggle, buttons on right side

    // DRIVETRAIN
    // --------------
    public static double drive = 0.0;
    public static double strafe = 0.0;
    public static double turn = 0.0;

    // INTAKE (ACTIVE)
    // --------------
    public static Toggle extend;
    public static Toggle transfer;
    public static Toggle intakeOnToIntake;
    public static Toggle toClear;
    public static Toggle clear;


    // INTAKE (CLAW)
    public static double wristYaw = 0.0;

    // INTAKE (v4b ACTIVE)
    // --------------
    // this might be where we go between intaking and hovering, and then transfer pos is automatic reset when we extendo back in? (and transfer button moves it back too)
    // also a trigger
    // TODO edit these pivots, needs to be more automatic
    public static Toggle pivot;
    // transfer sample should be automatic here
    // button, driver 1
    public static Toggle transferHover;
    // public static Toggle openClaw;


    // OUTTAKE
    // --------------
    public static Toggle flipBucket;
    public static Toggle highBasket;
    public static Toggle lowBasket;

    // SCORING
    // --------------
    public static Toggle scoreSpec;
    public static Toggle openClaw;
    public static Toggle L1hang;

    // LOCKED HEADING
    // -----------------
    // public static Toggle toggleLockedHeading;
    public static boolean lock90 = false;
    public static boolean lock180 = false;
    public static boolean lock270 = false;
    public static boolean lock360 = false;
    public static Toggle lockedMode;

    // OTHER
    // --------------
    public static Toggle botToBaseState;
    public static Toggle isBlue;
    public static Toggle slowMode;

    // TESTING BUTTONS
    // NOT TO BE USED FOR COMP
    // -------------------------------

    public GamepadMapping(Gamepad gamepad1, Gamepad gamepad2) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;

        lockedMode = new Toggle(false);

        // INTAKE
        extend = new Toggle(false);
        intakeOnToIntake = new Toggle(false);
        toClear = new Toggle(false);
        transfer = new Toggle(false);
        clear = new Toggle(false);

        pivot = new Toggle(false);
        transferHover = new Toggle(false);
        // openClaw = new Toggle(false);

        // OUTTAKE
        flipBucket = new Toggle(false);
        highBasket = new Toggle(false);
        lowBasket = new Toggle(false);
        L1hang = new Toggle(false);

        // spec
        openClaw = new Toggle(false);
        scoreSpec = new Toggle(false);

        // OTHER
        botToBaseState = new Toggle(false);
        isBlue = new Toggle(false);
        slowMode = new Toggle(false);
    }

    public void joystickUpdate() {
        drive = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;
    }

    public void clawUpdate() {
        pivot.update(gamepad2.right_trigger > 0.5); // hover and intaking, button held
        // first driver
        transferHover.update(gamepad1.left_bumper);
        wristYaw = gamepad2.right_stick_x;
        // openClaw.update(gamepad2.b);
    }

    public void v4bActiveUpdate() {
        extend.update(gamepad1.right_bumper);
        pivot.update(gamepad2.a); // hover and intaking, button held

        // first driver
        transferHover.update(gamepad1.left_bumper);

        intakeOnToIntake.update(gamepad2.right_trigger > 0.5);
        toClear.update(gamepad2.left_trigger > 0.5);
    }

    // v1 robot
    public void update() {
        joystickUpdate();

        activeIntakeUpdate();

        lockedMode.update(gamepad2.x);

        extend.update(gamepad1.right_bumper);
        // This is only when Souren drives
        // retract.update(gamepad2.a);
        clear.update(gamepad1.x); // square

        // Outtake (All Gamepad2)
        lowBasket.update(gamepad2.left_trigger > 0.3);
        highBasket.update(gamepad2.left_bumper);
        flipBucket.update(gamepad2.a);

        //L1hang.update(gamepad2.dpad_down); // TODO Ask Drivers

        // spec
        openClaw.update(gamepad2.right_trigger > 0.3);
        scoreSpec.update(gamepad2.right_bumper);

        // Reset/Fail Safes (Both controllers should have these)
//        botToBaseState.update(gamepad1.dpad_down);
//        botToBaseState.update(gamepad2.dpad_down);
    }

    public void activeIntakeUpdate() {
        intakeOnToIntake.update(gamepad1.right_trigger > 0.5);
        toClear.update(gamepad1.left_trigger > 0.5);
        transfer.update(gamepad2.dpad_up);

        //clearFailsafe.update(gamepad1.x);
    }

    public void presModeUpdate() {
        lockedMode.update(gamepad1.x);

        extend.update(gamepad1.right_bumper);
        // This is only when Souren drives
        // retract.update(gamepad2.a);
        clear.update(gamepad1.x); // square

        // Outtake (All Gamepad2)
        highBasket.update(gamepad1.left_bumper);
        flipBucket.update(gamepad1.a);

        intakeOnToIntake.update(gamepad1.right_trigger > 0.5);
        toClear.update(gamepad1.left_trigger > 0.5);
        transfer.update(gamepad1.dpad_up);
    }

    public void resetIntakeControls() {
        extend.set(false);
        intakeOnToIntake.set(false);
        toClear.set(false);
        transfer.set(false);
        clear.set(false);
    }

    public void resetOuttakeControls() {
        flipBucket.set(false);
        highBasket.set(false);
        lowBasket.set(false);
    }

    public void resetSpecControls() {
        openClaw.set(false);
        scoreSpec.set(false);
    }

    public void resetMultipleControls(Toggle... toggles) {
        for (Toggle toggle : toggles) {
            toggle.set(false);
        }
    }
}

