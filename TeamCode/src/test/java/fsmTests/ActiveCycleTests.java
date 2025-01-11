package fsmTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.fsm.ActiveCycle;
import org.firstinspires.ftc.teamcode.mechanisms.drive.DriveTrain;
import org.firstinspires.ftc.teamcode.mechanisms.intake.ActiveIntake;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.misc.ReLocalizer;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.mechanisms.specimen.SpecimenClaw;
import org.firstinspires.ftc.teamcode.misc.PIDFControllerEx;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ActiveCycleTests {
    // intake hardware
    @Mock
    Servo rightExtendo;
    @Mock
    Servo leftExtendo;
    @Mock
    DcMotorEx rollerMotor;
    @Mock
    Servo pivotAxon;

    // outtake hardware
    @Mock
    DcMotorEx slideRight;
    @Mock
    DcMotorEx slideLeft;
    @Mock
    Servo bucketServo;
    PIDController controller = new PIDController(0, 0, 0);

    // specimen claw hardware
    @Mock
    Servo clawServo;

    // drivetrain hardware
    @Mock
    DcMotorEx leftFront;
    @Mock
    DcMotorEx rightFront;
    @Mock
    DcMotorEx leftBack;
    @Mock
    DcMotorEx rightBack;
    @Mock
    IMU imu;

    // other hardware
    Gamepad gamepad1 = new Gamepad();
    Gamepad gamepad2 = new Gamepad();

    // -- actual objects --
    ActiveIntake activeIntake;
    Intake intake;
    Outtake outtake;
    SpecimenClaw specClaw;
    DriveTrain drivetrain;

    // this may not work...
    GamepadMapping controls = new GamepadMapping(gamepad1, gamepad2);
    ActiveCycle cycle;
    Robot robot;

    @BeforeEach
    public void setUp() {
        activeIntake = new ActiveIntake(rollerMotor, pivotAxon);
        intake = new Intake(rightExtendo, leftExtendo, activeIntake);
        outtake = new Outtake(slideLeft, slideRight, bucketServo, controller);
        specClaw = new SpecimenClaw(clawServo);
        drivetrain = new DriveTrain(leftFront, rightFront, leftBack, rightBack, imu);

        robot = new Robot(controls, drivetrain, outtake, intake, specClaw);
        cycle = new ActiveCycle(null, controls, robot);
    }

    @Test
    public void testInRetractedToExtended() {
        cycle.setState(ActiveCycle.TransferState.EXTENDO_FULLY_RETRACTED);
        // push the extend button
        controls.extend.set(true);
        // loop!
        cycle.activeIntakeUpdate();
        // we should've moved to the fully extended state
        assertEquals(ActiveCycle.TransferState.EXTENDO_FULLY_EXTENDED, cycle.getState());

        cycle.activeIntakeUpdate();

        verify(rightExtendo).setPosition(anyDouble());
        verify(leftExtendo).setPosition(anyDouble());

        verify(slideRight, times(2)).setPower(anyDouble());
        verify(slideLeft, times(2)).setPower(anyDouble());
    }

    @Test
    public void testInRetractedToTransfer() {
        cycle.setState(ActiveCycle.TransferState.EXTENDO_FULLY_RETRACTED);

        controls.extend.set(false);
        // hold transfer button
        controls.transfer.setLocked(true);

        cycle.activeIntakeUpdate();

        controls.transfer.setLocked(true);

        verify(rollerMotor).setPower(anyDouble());

        controls.transfer.setLocked(false);

        cycle.activeIntakeUpdate();

        assertEquals(ActiveCycle.TransferState.EXTENDO_FULLY_RETRACTED, cycle.getState());
    }


    @Test
    public void testInRetractedToHighBasket() {
        cycle.setState(ActiveCycle.TransferState.EXTENDO_FULLY_RETRACTED);

        controls.extend.set(false);
        // hold transfer button
        controls.highBasket.setLocked(true);

        cycle.activeIntakeUpdate();

        controls.highBasket.setLocked(true);

        verify(rollerMotor).setPower(anyDouble());

        controls.highBasket.setLocked(false);

        cycle.activeIntakeUpdate();

        assertEquals(ActiveCycle.TransferState.EXTENDO_FULLY_RETRACTED, cycle.getState());
    }

}
