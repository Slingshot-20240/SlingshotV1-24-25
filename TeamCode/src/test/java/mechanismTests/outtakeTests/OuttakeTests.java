package mechanismTests.outtakeTests;

import static org.mockito.Mockito.*;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.OuttakeConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OuttakeTests {
    private Outtake outtake;
    private PIDController controller = new PIDController(0.1, 0, 0.0001);

    // MOCKS
    @Mock
    DcMotorEx outtakeSlideLeft;
    @Mock
    DcMotorEx outtakeSlideRight;
    @Mock
    Servo leftBucketServo;
    @Mock
    Servo rightBucketServo;

    @BeforeEach
    public void setUp() {
        outtake = new Outtake(outtakeSlideLeft, outtakeSlideRight, rightBucketServo, leftBucketServo, controller);
    }

    @Test
    public void testMoveTicks() {
        outtake.moveTicks(1000);
        verify(outtakeSlideLeft).setPower(anyDouble());
        verify(outtakeSlideRight).setPower(anyDouble());
    }

    @Test
    public void canExtendToHighBasket() {
        outtake.extendToHighBasket();
        verify(outtakeSlideLeft).setPower(anyDouble());
        verify(outtakeSlideRight).setPower(anyDouble());
    }

    @Test
    public void canExtendToLowBasket() {
        outtake.extendToLowBasket();
        verify(outtakeSlideLeft).setPower(anyDouble());
        verify(outtakeSlideRight).setPower(anyDouble());
    }

    @Test
    public void canExtendSpecHighRack() {
        outtake.extendToSpecimenHighRack();
        verify(outtakeSlideLeft).setPower(anyDouble());
        verify(outtakeSlideRight).setPower(anyDouble());
    }

    @Test
    public void canReturnToRetracted() {
        outtake.returnToRetracted();
        verify(outtakeSlideLeft).setPower(anyDouble());
        verify(outtakeSlideRight).setPower(anyDouble());
    }

    @Test
    public void testResetHardware() {
        outtake.resetHardware();
        verify(outtakeSlideLeft, never()).setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        verify(outtakeSlideRight, never() ).setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        verify(outtakeSlideLeft, never()).setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        verify(outtakeSlideRight, never()).setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        verify(rightBucketServo).setPosition(OuttakeConstants.BucketPositions.TRANSFER_READY.getRightBucketPos());
    }

    @Test
    public void testResetEncoders() {
        outtake.resetEncoders();
        verify(outtakeSlideLeft).setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        verify(outtakeSlideRight).setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        verify(outtakeSlideLeft).setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        verify(outtakeSlideRight).setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Test
    public void testBucketToReadyForTransfer() {
        outtake.bucketToReadyForTransfer();
        verify(rightBucketServo).setPosition(OuttakeConstants.BucketPositions.TRANSFER_READY.getRightBucketPos());
    }

    @Test
    public void testBucketDeposit() {
        outtake.bucketDeposit();
        verify(rightBucketServo).setPosition(OuttakeConstants.BucketPositions.DEPOSIT.getRightBucketPos());
    }

    @Test
    public void testBucketTilt() {
        outtake.bucketTilt();
        verify(rightBucketServo).setPosition(OuttakeConstants.BucketPositions.TILT.getRightBucketPos());
    }
}
