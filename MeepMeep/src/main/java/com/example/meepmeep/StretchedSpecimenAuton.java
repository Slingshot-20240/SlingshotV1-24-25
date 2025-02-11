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
//public class StretchedSpecimenAuton {
//    public static void main(String[] args) {
//        MeepMeep meepMeep = new MeepMeep(700);
//
//        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
//                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
//                .setConstraints(70, 55, 2.5, 2, 14)
//                .followTrajectorySequence(drive ->
//                                drive.trajectorySequenceBuilder(new Pose2d(12, -60, Math.toRadians(270)))
//                                        //preloaded spec
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //raise slides (small)
////                                    outtake.moveTicks(2400);
//                                        })
//                                        .back(30)
//
//                                        .waitSeconds(.5)
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //lower slides (small)
//                                            //open claw (preloaded spec)
//                                        })
//
//
//                                        //go2other3samples
//                                        .splineToLinearHeading(new Pose2d(24,-40,Math.toRadians(40)), Math.toRadians(40))
//
//                                        //first sample
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                                    extendoIntake();
//                                        })
//                                        .waitSeconds(0.5)
//
//                                        .lineToLinearHeading(new Pose2d(30,-50,Math.toRadians(-40)))
//                                        .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                                            //outtake sample
//                                        })
//                                        .waitSeconds(0.5)
//
//                                        //second sample
//                                        .setReversed(true)
//                                        .splineToLinearHeading(new Pose2d(37,-40,Math.toRadians(40)), Math.toRadians(0))
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                                    extendoIntake();
//                                        })
//                                        .waitSeconds(0.5)
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                                    deextend();
//                                        })
//                                        .lineToLinearHeading(new Pose2d(30,-50,Math.toRadians(-40)))
//                                        .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                                            //outtake sample
//                                        })
//                                        .waitSeconds(0.5)
//
//                                        //third sample
//
//                                        .splineToLinearHeading(new Pose2d(45,-30,Math.toRadians(10)), Math.toRadians(0))
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                                    extendoIntake();
//                                        })
//                                        .waitSeconds(0.5)
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                                    deextend();
//                                        })
//                                        .lineToLinearHeading(new Pose2d(40,-45,Math.toRadians(-40)))
//                                        .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                                            //outtake sample
//                                        })
//                                        .waitSeconds(0.5)
//
//                                        //get spec
//                                        .lineToLinearHeading(new Pose2d(40,-54,Math.toRadians(90)))
//                                        .back(6)
//                                        .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                                            //close claw (intake spec 2)
//                                        })
//                                        .waitSeconds(0.5)
//
//                                        //go to box
//                                        .lineToSplineHeading(new Pose2d(8,-36,Math.toRadians(270)))
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //raise slides (small)
////                                    outtake.moveTicks(2400);
//                                        })
//                                        .setReversed(true)
//                                        .back(6)
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //tiny slides raise
//                                        })
//                                        .waitSeconds(.5)
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //lower slides (small)
//                                            //open claw (preloaded spec)
//                                        })
//                                        .setReversed(false)
//                                        .forward(6)
//
//                                        //go back to hp
//                                        .lineToLinearHeading(new Pose2d(40,-54,Math.toRadians(90)))
//                                        .back(6)
//                                        .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                                            //close claw (intake spec 2)
//                                        })
//                                        .waitSeconds(0.5)
//
//                                        //go back to box
//                                        .lineToSplineHeading(new Pose2d(6,-36,Math.toRadians(270)))
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //raise slides (small)
////                                    outtake.moveTicks(2400);
//                                        })
//                                        .setReversed(true)
//                                        .back(6)
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //tiny slides raise
//                                        })
//                                        .waitSeconds(.5)
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //lower slides (small)
//                                            //open claw (preloaded spec)
//                                        })
//                                        .setReversed(false)
//                                        .forward(6)
//
//
//                                        //go back to hp
//                                        .lineToLinearHeading(new Pose2d(40,-54,Math.toRadians(90)))
//                                        .back(6)
//                                        .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                                            //close claw (intake spec 2)
//                                        })
//                                        .waitSeconds(0.5)
//
//
////                                        //potential last spec :_D
////                                        //go back to box
////                                        .lineToSplineHeading(new Pose2d(4,-36,Math.toRadians(270)))
////                                        .setReversed(true)
////                                        .back(6)
////                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                                            //tiny slides raise
////                                        })
////                                        .waitSeconds(.5)
////                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                                            //open claw (preloaded spec)
////                                        })
//
//
//
//
//
//
//
//
//
//
//
//
//                                        .build()
//
//
//
//
//
//
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
//
//
//
