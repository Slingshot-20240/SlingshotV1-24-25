package org.firstinspires.ftc.teamcode.mechanisms.specimen;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class SpecimenClaw {
    public Servo clawServo;
    // open, closed
    private static double[] clawPositions = {1, .65};
    public SpecimenClaw(HardwareMap hwMap) {
        clawServo = hwMap.get(Servo.class, "clawServo");
    }
    // this is for testing only
    public SpecimenClaw(Servo clawServo) {
        this.clawServo = clawServo;
    }

    public void closeClaw() {
        clawServo.setPosition(SpecConstants.CLOSED.getClawPos()); // obviously tune later
    }

    public void openClaw() {
        clawServo.setPosition(SpecConstants.OPEN.getClawPos()); // obviously tune later
    }

    public enum SpecConstants {
        OPEN(clawPositions[0]),
        CLOSED(clawPositions[1]);

        private double clawPos;

        SpecConstants(double clawPos) {
            this.clawPos = clawPos;
        }

        public double getClawPos() {
            return clawPos;
        }

    }
}
