//package com.example.meepmeep;
//
//import com.acmerobotics.roadrunner.geometry.Pose2d;
//import com.acmerobotics.roadrunner.geometry.Vector2d;
//import com.noahbres.meepmeep.MeepMeep;
//import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
//import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
//
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//
//import javax.imageio.ImageIO;
//
//public class SampYom {
//    public static void main(String[] args) {
//        MeepMeep meepMeep = new MeepMeep(700);
//
//        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
//                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
//                .setConstraints(75, 90, 2.5, 5, 12)
//                .followTrajectorySequence(drive ->
//                                drive.trajectorySequenceBuilder(new Pose2d(-12, -63, Math.toRadians(0)))
//                                        //preload
//                                        .setReversed(false)
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //raise slides
//                                            //extendo 0.2
//                                        })
//                                        .lineToLinearHeading(new Pose2d(-55,-55, Math.toRadians(45)))
//                                        .back(3)
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //bucket
//                                            //extendo a little
//                                        })
//                                        .waitSeconds(0.4)
//
//                                        // num 1
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //pivot
//                                            // rollers on
//                                        })
//                                        .splineTo(new Vector2d(-49,-37), Math.toRadians(90))
//                                        .waitSeconds(0.1)
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //pivot
//                                            //extendo retract
//                                        })
//                                        .UNSTABLE_addDisplacementMarkerOffset(12, () -> {
//                                            //transfer
//                                        })
//                                        .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
//                                            //transfer off, slide raise
//                                        })
//                                        .lineToLinearHeading(new Pose2d(-55,-55, Math.toRadians(45)))
//                                        .waitSeconds(1.5)
//                                        .back(3)
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //bucket
//                                            //extendo a little
//                                        })
//                                        .waitSeconds(0.4)
//
//                                        //num 2
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //pivot
//                                            // rollers on
//                                        })
//                                        .lineToLinearHeading(new Pose2d(-46,-45, Math.toRadians(130)))
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //extendo more
//                                        })
//                                        .waitSeconds(0.3)
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //pivot
//                                            //extendo retract
//                                        })
//                                        .UNSTABLE_addTemporalMarkerOffset(1, () -> {
//                                            //transfer
//                                        })
//                                        .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
//                                            //transfer off, slide raise
//                                        })
//                                        .lineToLinearHeading(new Pose2d(-55,-55, Math.toRadians(45)))
//                                        .waitSeconds(2.5)
//                                        .back(3)
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //bucket
//                                            //extendo a little
//                                        })
//
//                                        //num 3
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //pivot
//                                            // rollers on
//                                        })
//                                        .lineToLinearHeading(new Pose2d(-46,-40, Math.toRadians(150)))
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //extendo more
//                                        })
//                                        .waitSeconds(0.3)
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //pivot
//                                            //extendo retract
//                                        })
//                                        .UNSTABLE_addTemporalMarkerOffset(1, () -> {
//                                            //transfer
//                                        })
//                                        .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
//                                            //transfer off, slide raise
//                                        })
//                                        .lineToLinearHeading(new Pose2d(-55,-55, Math.toRadians(45)))
//                                        .waitSeconds(2.5)
//                                        .back(3)
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //bucket
//                                        })
//                                        .waitSeconds(0.4)
//
//                                        //lvl 1 asc
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //hangState
//                                        })
//                                        .lineToLinearHeading(new Pose2d(-36,-12, Math.toRadians(180)))
//                                        .back(14)
//                                        .build()
//                );
//
//
//
//        BufferedImage img = null;
//        try { img = ImageIO.read(new File("MeepMeep/intothedeep.png")); }
//        catch (IOException e) {}
//
//        meepMeep.setBackground(img)
//                .setDarkMode(true)
//                .setBackgroundAlpha(0.95f)
//                .addEntity(myBot)
//                .start();
//    }
//}