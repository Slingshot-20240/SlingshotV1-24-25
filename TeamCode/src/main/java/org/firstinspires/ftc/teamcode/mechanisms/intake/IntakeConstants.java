package org.firstinspires.ftc.teamcode.mechanisms.intake;

public class IntakeConstants {

    // pivoted up, pivoted down, transfer pos, clearing samples, failsafe clear
    // axon programmed for 0-255, 66 PMW (inverted)
    private static final double[] pivotPositions = {.27, .91, .27, .49, .4};

    // neutral pos, back roller push out sample (extendo), back roller transfer
    private static final double[] backRollerPositions = {0.5, 1, -1};

    // right linkage in, right linkage extended, outtaking
    // axon programmed for 0-255, 66 PMW
    private static final double[] rightLinkagePositions = {.33, .035, .3};

    // left linkage in, left linkage extended
    // axon programmed for 0-255, 66 PMW
    private static final double[] leftLinkagePositions = {.33, .035, .3};

    // pivot positions for the v4b for active claw
    // transfer pos, hovering, intaking
    private static final double[] v4bPositions = {0, 0.5, 1};

    public enum ActiveIntakeStates {
        FULLY_RETRACTED(pivotPositions[0], backRollerPositions[0], rightLinkagePositions[0], leftLinkagePositions[0]), // pivoted up, idle back roller, retracted
        CLEARING(pivotPositions[3], backRollerPositions[1], rightLinkagePositions[1], leftLinkagePositions[1]),
        WRONG_ALLIANCE_COLOR_SAMPLE(pivotPositions[1], backRollerPositions[1], rightLinkagePositions[1], leftLinkagePositions[1]), // pivoted down, pushing out sample, extended
        FULLY_EXTENDED(pivotPositions[1], backRollerPositions[0], rightLinkagePositions[1], leftLinkagePositions[1]), // pivoted down, idle back roller, extended
        TRANSFER(pivotPositions[2], backRollerPositions[2], rightLinkagePositions[0], leftLinkagePositions[0]), // pivoted up, back roller push, retracted
        FAILSAFE_CLEARING(pivotPositions[4], backRollerPositions[0], rightLinkagePositions[1], leftLinkagePositions[1]),
        OUTTAKING(pivotPositions[2], backRollerPositions[0], rightLinkagePositions[2], leftLinkagePositions[2]);

        private final double pivotPos;
        private final double rLinkagePos;
        private final double backRollerPos;
        private final double lLinkagePos;

        ActiveIntakeStates(double pivotPos, double backRollerPos, double rLinkagePos, double lLinkagePos) {
            this.pivotPos = pivotPos;
            this.backRollerPos = backRollerPos;
            this.rLinkagePos = rLinkagePos;
            this.lLinkagePos = lLinkagePos;
        }

        public double pivotPos() { return pivotPos; }
        public double backRollerPos() { return backRollerPos;}
        public double rLinkagePos() { return rLinkagePos; }
        public double lLinkagePos() { return lLinkagePos; }

    }
    public enum SampleTypes{
        NONE(new double[]{57,95,114}, "NONE"),
        YELLOW(new double[]{215,287,110}, "YELLOW"),
        BLUE(new double[]{55,99,156}, "BLUE"),
        RED(new double[]{173,126,85}, "RED");
        public double[] color;
        public String name;
        SampleTypes(double[] color, String name){
            this.color = color;
            this.name = name;
        }
    }

    public enum v4bActiveStates {
        FULLY_RETRACTED(v4bPositions[0], rightLinkagePositions[0], leftLinkagePositions[0]),
        CLEARING(v4bPositions[1], rightLinkagePositions[1], leftLinkagePositions[1]),
        FULLY_EXTENDED(v4bPositions[1], rightLinkagePositions[1], leftLinkagePositions[1]), // pivoted down, idle back roller, extended
        TRANSFER(v4bPositions[1], rightLinkagePositions[0], leftLinkagePositions[0]), // pivoted up, back roller push, retracted
        OUTTAKING(v4bPositions[0], rightLinkagePositions[2], leftLinkagePositions[2]),
        HOVERING(v4bPositions[1], rightLinkagePositions[1], leftLinkagePositions[1]);
        private double v4bPos;
        private final double rLinkagePos;
        private final double lLinkagePos;
        v4bActiveStates (double v4bPos, double rightLinkagePos, double leftLinkagePos) {
            this.v4bPos = v4bPos;
            this.rLinkagePos = rightLinkagePos;
            this.lLinkagePos = leftLinkagePos;
        }

        public double v4bPos() { return v4bPos; }
        public double rLinkagePos() { return rLinkagePos; }
        public double lLinkagePos() { return lLinkagePos; }
    }
}
