package org.firstinspires.ftc.teamcode.mechanisms.outtake;

public class OuttakeConstants {
    // deposit: 0
    // tilt: .5477610423136522
    // transfer ready: .7231126346979301

    // transfer ready, tilt, deposit
    private static double[] bucketPositions = {.7231126346979301, .5477610423136522, 0};

    // Retracted, low basket, high basket, specimen high rack, hang
    private static double[] slidePositions = {150, 1200, 2700, 1200, 500}; // tune specimen high rack
    public enum SlidePositions {
        RETRACTED(slidePositions[0]),
        LOW_BASKET(slidePositions[1]), // probably could work for hang
        HIGH_BASKET(slidePositions[2]),
        SPECIMEN_HIGH_RACK(slidePositions[3]),
        HUMAN_PLAYER(slidePositions[0]),
        BASE_STATE(slidePositions[0]),
        HANG(slidePositions[4]);

        private final double slidePos;

        SlidePositions(double slidePos) {
            this.slidePos = slidePos;
        }
        public double getSlidePos() { return slidePos; }
    }

    public enum BucketPositions {
        TRANSFER_READY(bucketPositions[0]),
        TRANSFERING(bucketPositions[1]),
        DEPOSIT(bucketPositions[2]);

        private final double bucketPos;

        BucketPositions(double bucketPos) {
            this.bucketPos = bucketPos;
        }

        public double getBucketPos() {
            return bucketPos;
        }
    }

}
