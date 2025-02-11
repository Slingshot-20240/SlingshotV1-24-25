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
//public class YomYom {
//    public static void main(String[] args) {
//        MeepMeep meepMeep = new MeepMeep(700);
//
//        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
//                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
//                .setConstraints(75, 65, 2.5, 2, 12)
//                .followTrajectorySequence(drive ->
//                                drive.trajectorySequenceBuilder(new Pose2d(12, -63, Math.toRadians(270)))
//                                        //preloaded spec
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                                            moveLift(1255);
//                                        })
//                                        .waitSeconds(.1)
//                                        .lineToConstantHeading(new Vector2d(12,-30))
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                                            moveLift(700);
//                                        })
//                                        .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
//                                            //open claw (preloaded spec)
////                                            specimenClaw.openClaw();
//                                        })
//                                        .waitSeconds(.3)
//
//                                        .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
////                                            moveLift(0);
//                                        })
//
//
//                                        //pickup #1
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                                            semi-extend
//                                        })
//                                        .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
////                                            pivot intake
//                                        })
//
//                                        .splineToSplineHeading(new Pose2d(24,-35, Math.toRadians(20)), Math.toRadians(20))
//                                        .forward(15)
//
//
//                                        //O-zone #1
//                                        .setReversed(true)
//                                        .UNSTABLE_addDisplacementMarkerOffset(10, () -> {
////                                            extendo full
//                                        })
//                                        .lineToSplineHeading(new Pose2d(20,-45, Math.toRadians(330)))
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                                            run transfer
//                                        })
//                                        .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
////                                            extendo semi, intake
//                                        })
//                                        .waitSeconds(0.3)
//
//
//
//                                        //pickup #2
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                                            full-extend
//                                        })
//                                        .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
////                                            pivot intake
//                                        })
//                                        .setReversed(false)
//                                        .splineToSplineHeading(new Pose2d(35,-42, Math.toRadians(31)),Math.toRadians(31))
//                                        .forward(5)
//                                        //O-zone #2
//                                        .setReversed(true)
//                                        .UNSTABLE_addDisplacementMarkerOffset(15, () -> {
////                                            extendo full
//                                        })
//                                        .lineToSplineHeading(new Pose2d(30,-45, Math.toRadians(330)))
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                                            run transfer
//                                        })
//                                        .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
////                                            extendo semi, intake
//                                        })
//                                        .waitSeconds(0.3)
//
//
//
//                                        //pickup #3
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                                            full-extend
//                                        })
//                                        .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
////                                            pivot intake
//                                        })
//                                        .setReversed(false)
//                                        .splineToSplineHeading(new Pose2d(45,-43, Math.toRadians(31)),Math.toRadians(31))
//                                        //O-zone #3
//                                        .setReversed(true)
//                                        .UNSTABLE_addDisplacementMarkerOffset(15, () -> {
////                                            extendo full
//                                        })
//                                        .lineToSplineHeading(new Pose2d(40,-45, Math.toRadians(330)))
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                                            run transfer
//                                        })
//                                        .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
////                                            pivot up, intake stop
//                                        })
//                                        .waitSeconds(0.3)
//
//
//                                        //go back to hp #1
//                                        .lineToLinearHeading(new Pose2d(40,-54,Math.toRadians(90)))
//                                        .back(7)
//                                        .UNSTABLE_addTemporalMarkerOffset(0.1, () -> {
//                                            //close claw
////                                            specimenClaw.closeClaw();
//                                        })
//                                        .waitSeconds(0.15)
////
//                                        //go to box #1
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                                            moveLift(1300);
//                                        })
//                                        .lineToSplineHeading(new Pose2d(8,-33,Math.toRadians(270)))
//
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                                            moveLift(700);
//                                        })
//                                        .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
//                                            //open claw (spec)
////                                            specimenClaw.openClaw();
//                                        })
//                                        .waitSeconds(.2)
//                                        .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
////                                            moveLift(0);
//                                        })
//                                        .setReversed(false)
//
//
//                                        //go back to hp #2
//                                        .lineToLinearHeading(new Pose2d( 40,-54,Math.toRadians(90)))
//                                        .back(7)
//                                        .UNSTABLE_addTemporalMarkerOffset(0.1, () -> {
//                                            //close claw
////                                            specimenClaw.closeClaw();
//                                        })
//                                        .waitSeconds(0.15)
//
//                                        //go to box #2
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                                            moveLift(1300);
//                                        })
//                                        .lineToSplineHeading(new Pose2d(8,-33,Math.toRadians(270)))
//
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                                            moveLift(700);
//                                        })
//                                        .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
//                                            //open claw (spec)
////                                            specimenClaw.openClaw();
//                                        })
//                                        .waitSeconds(.2)
//                                        .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
////                                            moveLift(0);
//                                        })
//                                        .setReversed(false)
//
//
//                                        //go back to hp #3
//                                        .lineToLinearHeading(new Pose2d( 40,-54,Math.toRadians(90)))
//                                        .back(7)
//                                        .UNSTABLE_addTemporalMarkerOffset(0.1, () -> {
//                                            //close claw
////                                            specimenClaw.closeClaw();
//                                        })
//                                        .waitSeconds(0.15)
//
//                                        //go to box #3
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                                            moveLift(1300);
//                                        })
//                                        .lineToSplineHeading(new Pose2d(8,-33,Math.toRadians(270)))
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                                            moveLift(700);
//                                        })
//                                        .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
//                                            //open claw (spec)
////                                            specimenClaw.openClaw();
//                                        })
//                                        .waitSeconds(.2)
//
//                                        //go back to hp #4
//                                        .lineToLinearHeading(new Pose2d( 40,-54,Math.toRadians(90)))
//                                        .back(7)
//                                        .UNSTABLE_addTemporalMarkerOffset(0.1, () -> {
//                                            //close claw
////                                            specimenClaw.closeClaw();
//                                        })
//                                        .waitSeconds(0.15)
//
//                                        //go to box #4
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                                            moveLift(1300);
//                                        })
//                                        .lineToSplineHeading(new Pose2d(8,-33,Math.toRadians(270)))
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                                            moveLift(700);
//                                        })
//                                        .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
//                                            //open claw (spec)
////                                            specimenClaw.openClaw();
////                                            extendo full
//                                        })
//                                        .waitSeconds(.2)
//                                        .splineTo(new Vector2d(20,-50),Math.toRadians(-30))
//
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