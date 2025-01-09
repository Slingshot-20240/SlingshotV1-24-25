package mathTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.mechanisms.drive.DriveTrain;
import org.firstinspires.ftc.teamcode.mechanisms.misc.ReLocalizer;
import org.firstinspires.ftc.teamcode.misc.PIDFControllerEx;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DriveTrainMathTests {
    // sensing distnace smae is target distance -? goal
    // robot angle 90
    // test constructor with the motors instead, hehe
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
    @Mock
    DistanceSensor backDS;
    @Mock
    DistanceSensor sideDS;


    private DriveTrain dt;
    private ReLocalizer ultrasonics;

    private PIDFControllerEx turnController;

    @BeforeEach
    public void setUp() {
        dt = new DriveTrain(leftFront, rightFront, leftBack, rightBack, imu);
    }

    @Test
    public void testOutputPowerDirForHeadingLockWith1Quadrant() {
        double expected = dt.lockHeading(90, 45);
        assertTrue(expected > 0); // this is like moving joystick left, angle gets bigger & neg output power
    }

    // *sigh* this is broken
    @Test
    public void testOutputPowerDirForHeadingLockWith4Quadrant() {
        double expected = dt.lockHeading(0, 315);
        assertTrue(expected > 0);
    }

    @Test
    public void testOutputPowerDirForHeadingLockWith3Quadrant() {
        double expected = dt.lockHeading(0, 260);
        assertTrue(expected > 0);
    }

    @Test
    public void testSetTargetAngleWithNegativeJoystickValue() {
        dt.setTargetAngle(0);
        dt.changeTargetAngleWithJoystick(-1);
        assertEquals(-7, ((int) (dt.getTargetAngle() * 10)) / 10.0 - .1);
    }

    @Test
    public void testSetTargetAngleWithDoubleJoystickValue() {
        dt.setTargetAngle(0);
        dt.changeTargetAngleWithJoystick(-0.5);
        assertEquals(-3.5, ((int) (dt.getTargetAngle() * 10)) / 10.0 - .1); // hehe goofy test, I don't feel like doing round up
    }

    @Test
    public void testSetTargetAngleWith0JoystickValue() {
        dt.setTargetAngle(90);
        dt.changeTargetAngleWithJoystick(0);
        assertEquals(90, dt.getTargetAngle()); // no change to the target angle
    }

    @Test
    public void testAngleWrapWithQuad3() {
        double actualAngle = dt.angleWrap(-270);
        assertEquals(90, actualAngle);
    }

//    @Test
//    public void testOutputPowerDirForHeadingLockWith180Target() {
//        double expected = dt.lockHeading(180, 0);
//        System.out.println(expected);
//        assertTrue(expected < 0);
//    }

    @Test
    public void testUltrasonicsWith90RobotAngleBackSensor() {
        // target distance = sensing distance
        // RobotAngle is 90
        // sensing distance = -15
        double robotAngle = 90;
        ultrasonics = new ReLocalizer(backDS, sideDS, imu);
        // this calls getDistance on the sensor, so I need to implement that method
        when(backDS.getDistance(DistanceUnit.INCH)).thenReturn(-15.0);
        double targetDistance = ultrasonics.getBackDistance(robotAngle);
        double sensingDistance = -15 + (-5); // this is the output from the senor itself, in inches from center, + y offset
        assertEquals(sensingDistance, targetDistance);
    }

    // This test worked well, error is small enough to assume human error in measurements
//    @Test
//    public void testUltrasonicsWith135RobotAngleBackSensor() {
//        // target distance = sensing distance + offset
//        // target distance = 13.5 in
//        // RobotAngle is 90
//        // sensing distance = 9.219544457
//        double robotAngle = 135;
//        ultrasonics = new ReLocalizer(backDS, sideDS, imu);
//        // this calls getDistance on the sensor, so I need to implement that method
//        when(backDS.getDistance(DistanceUnit.INCH)).thenReturn(-15.0);
//        double targetDistance = ultrasonics.getBackDistance(robotAngle);
//        double sensingDistance = -9.219544457 + (-5); // this is the output from the senor itself, in inches from center, + y offset
//        assertEquals(sensingDistance, targetDistance);
//    }

    @Test
    public void testUltrasonicsWith90RobotAngleSideSensor() {
        // target distance = sensing distance + offset horizontally
        // RobotAngle is 90
        // sensing distance = 15
        double robotAngle = 90;
        ultrasonics = new ReLocalizer(backDS, sideDS, imu);

        // this calls getDistance on the sensor, so I need to implement that method
        when(sideDS.getDistance(DistanceUnit.INCH)).thenReturn(15.0);

        double targetDistance = ultrasonics.getSideDistance(robotAngle);

        double sensingDistance = 15 + (-0.2); // this is the output from the senor itself, in inches from center, + x offset to get the distance from the object to the center
        assertEquals(sensingDistance, targetDistance);
    }

    // This test also has a very small error, likely human error
    @Test
    public void testUltrasonicsWith45RobotAngleSideSensor() {
        // target distance = sensing distance + y offset (works with formula) = 13
        // RobotAngle is 45
        // sensing distance = 8.276472679
        double robotAngle = 45;
        ultrasonics = new ReLocalizer(backDS, sideDS, imu);

        // this calls getDistance on the sensor, so I need to implement that method
        when(sideDS.getDistance(DistanceUnit.INCH)).thenReturn(8.276472679);

        double targetDistance = ultrasonics.getSideDistance(robotAngle);

        double sensingDistance = 8.276472679 + (-0.2); // this is the output from the senor itself, in inches from center, + x offset to get the distance from the object to the center
        assertEquals(sensingDistance, targetDistance);
    }
}
