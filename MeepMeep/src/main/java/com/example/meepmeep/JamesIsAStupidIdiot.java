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
//public class JamesIsAStupidIdiot {
//    public static void main(String[] args){
//        MeepMeep meep = new MeepMeep(800);
//        RoadRunnerBotEntity bot = new DefaultBotBuilder(meep).setConstraints(70, 55, 2.5, 2, 14)
//                .followTrajectorySequence(drive ->
//                                drive.trajectorySequenceBuilder(new Pose2d(12, -60, Math.toRadians(270)))
//                                        //preloaded spec
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //raise slides (small)
////                                    outtake.moveTicks(2400);
//                                        })
//                                        .back(30)
//
//                                        .waitSeconds(0.5)
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //lower slides (small)
//                                            //open claw (preloaded spec)
//                                        })
//
//
//                                        .splineToLinearHeading(new Pose2d(24,-40,Math.toRadians(40)), Math.toRadians(40))
//
//                                        //first spec
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
////                                            extendoIntake();
//                                        })
//                                        .waitSeconds(0.5)
//
//                                        .lineToLinearHeading(new Pose2d(30,-50,Math.toRadians(-40)))
//                                        .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                                            //outtake sample
//                                        })
//                                        .waitSeconds(0.5)
//
//                                        //nav to intake
//                                        .lineToLinearHeading(new Pose2d(40,-54,Math.toRadians(90)))
//                                        .back(6)
//                                        .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                                            //close claw (intake spec)
//                                        })
//                                        .waitSeconds(0.5)
//
//                                        //place spec
//                                        .splineToLinearHeading(new Pose2d(10,-30, Math.toRadians(-90)), Math.toRadians(90))
//                                        .waitSeconds(0.5)
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //lower slides (small)
//                                            //open claw (place spec)
//                                        })
//                                        //second spec
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
//                                        //nav
//
//                                        .lineToLinearHeading(new Pose2d(40,-54,Math.toRadians(90)))
//                                        .back(6)
//                                        .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                                            //close claw (intake spec 2)
//                                        })
//                                        .waitSeconds(0.5)
//                                        //place
//                                        .splineToLinearHeading(new Pose2d(8,-30, Math.toRadians(-90)), Math.toRadians(90))
//                                        .waitSeconds(0.5)
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //lower slides (small)
//                                            //open claw (preloaded spec)
//                                        })
//                                        //third spec
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
//                                        //nav
//                                        .lineToLinearHeading(new Pose2d(40,-54,Math.toRadians(90)))
//                                        .back(6)
//                                        .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                                            //close claw (intake spec 2)
//                                        })
//                                        .waitSeconds(0.5)
//                                        //place
//                                        .splineToLinearHeading(new Pose2d(8,-30, Math.toRadians(-90)), Math.toRadians(90))
//                                        .waitSeconds(0.5)
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //lower slides (small)
//                                            //open claw (preloaded spec)
//                                        })
//
//                                        //4th
//                                        //nav
//                                        .lineToLinearHeading(new Pose2d(40,-54,Math.toRadians(90)))
//                                        .back(6)
//                                        .UNSTABLE_addTemporalMarkerOffset(0.25, () -> {
//                                            //close claw (intake spec 2)
//                                        })
//                                        .waitSeconds(0.5)
//                                        //place
//                                        .splineToLinearHeading(new Pose2d(6,-30, Math.toRadians(-90)), Math.toRadians(90))
//                                        .waitSeconds(0.5)
//                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            //lower slides (small)
//                                            //open claw (preloaded spec)
//                                        })
//                                        .build()
//
//
//                );
//
//        BufferedImage img = null;
//        try { img = ImageIO.read(new File("MeepMeep/intothedeep.png")); }
//        catch (IOException e) {}
//
//        meep.setBackground(img)
//                .setDarkMode(true)
//                .setBackgroundAlpha(0.95f)
//                .addEntity(bot)
//                .start();
//    }
//}
