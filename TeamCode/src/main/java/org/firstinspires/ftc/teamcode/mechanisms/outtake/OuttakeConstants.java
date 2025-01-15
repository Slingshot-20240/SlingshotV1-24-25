package org.firstinspires.ftc.teamcode.mechanisms.outtake;

public class OuttakeConstants {
    // deposit: 0
    // tilt: .5477610423136522
    // transfer ready: .7231126346979301

    // transfer ready, tilt, deposit
    //.95 for transfer previously
    private static double[] leftBucketPositions = {.982, .75, .58};
    private static double[] rightBucketPositions = {1, .773, .603};

    // Retracted, low basket, high basket, specimen high rack, spec mini raise
    private static double[] slidePositions = {0, 1200, 2550, 1400, 1400, 200}; // mini extend
    public enum SlidePositions {
        RETRACTED(slidePositions[0]),
        LOW_BASKET(slidePositions[1]), // probably could work for hang
        HIGH_BASKET(slidePositions[2]),
        SPECIMEN_HIGH_RACK(slidePositions[3]),
        HUMAN_PLAYER(slidePositions[0]),
        BASE_STATE(slidePositions[0]),
        GRABBING_SPEC(slidePositions[5]),
        HANG(slidePositions[4]);

        private final double slidePos;

        SlidePositions(double slidePos) {
            this.slidePos = slidePos;
        }
        public double getSlidePos() { return slidePos; }
    }

    public enum BucketPositions {
        TRANSFER_READY(leftBucketPositions[0], rightBucketPositions[0]),
        TILT(leftBucketPositions[1], rightBucketPositions[1]),
        DEPOSIT(leftBucketPositions[2], rightBucketPositions[2]);

        private final double leftBucketPos;
        private final double rightBucketPos;

        BucketPositions(double lBucketPos, double rBucketPos) {
            this.leftBucketPos = lBucketPos;
            this.rightBucketPos = rBucketPos;
        }

        public double getRightBucketPos() {
            return rightBucketPos;
        }
        public double getLeftBucketPos() {
            return leftBucketPos;
        }
    }

}
