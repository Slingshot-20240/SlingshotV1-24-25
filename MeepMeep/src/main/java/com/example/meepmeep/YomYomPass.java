package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class YomYomPass {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(700);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(65, 70, 2.5, 4, 12)
                .followTrajectorySequence(drive ->
                                drive.trajectorySequenceBuilder(new Pose2d(12, -63, Math.toRadians(270)))
                                        //preloaded spec
                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            moveLift(1320);
                                        })
                                        .lineToConstantHeading(new Vector2d(11.5,-32))
                                        .waitSeconds(.15)
                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            moveLift(0);
                                        })


                                        //pickup #1
                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    semi-extend
//                                            moveExtendo(0.2);
                                        })
                                        .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
//                                            pivot intake
//                                            intake.activeIntake.flipDownFull();
//                                            intake.activeIntake.motorRollerOnToIntake();
                                        })

                                        .splineToSplineHeading(new Pose2d(24,-34, Math.toRadians(18)), Math.toRadians(18))
                                        .forward(11.5)


                                        //O-zone #1
                                        .setReversed(true)
                                        .UNSTABLE_addDisplacementMarkerOffset(10, () -> {
//                                            extendo full
//                                            intake.extendoFullExtend();
                                        })
                                        .lineToSplineHeading(new Pose2d(20,-47, Math.toRadians(327)))
                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            run transfer
//                                            intake.activeIntake.transferSample();
                                        })
                                        .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
                                            // intake off
//                                            intake.activeIntake.motorRollerOff();
//                                            intake.extendoFullRetract();
                                        })
                                        .waitSeconds(0.15)



                                        //pickup #2
                                        .UNSTABLE_addTemporalMarkerOffset(0.16, () -> {
//                                            intake
//                                            intake.activeIntake.motorRollerOnToIntake();

                                        })

                                        .setReversed(false)
                                        .UNSTABLE_addDisplacementMarkerOffset(8.5, () -> {
//                                            intake
//                                            moveExtendo(0);
//                                            intake.activeIntake.flipDownFull();
                                        })
                                        .lineToLinearHeading(new Pose2d(35,-37, Math.toRadians(33)))
                                        .waitSeconds(0.1)


                                        //O-zone #2
                                        .setReversed(true)
                                        .UNSTABLE_addTemporalMarkerOffset(0.2, () -> {
//                                            intake.extendoFullExtend();
                                        })
                                        .lineToSplineHeading(new Pose2d(30,-42, Math.toRadians(326)))
                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            intake.activeIntake.transferSample();
                                        })
                                        .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
//                                            intake.activeIntake.motorRollerOff();
//                                            intake.extendoFullRetract();
                                        })
                                        .waitSeconds(0.15)



                                        //pickup #3

                                        .UNSTABLE_addTemporalMarkerOffset(0.16, () -> {
//                                            intake.activeIntake.motorRollerOnToIntake();
                                        })
                                        .setReversed(false)
                                        .UNSTABLE_addDisplacementMarkerOffset(8, () -> {
//                                            intake
//                                            moveExtendo(0.04);
                                        })
                                        .lineToLinearHeading(new Pose2d(45,-35, Math.toRadians(29)))
                                        .waitSeconds(0.1)

                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            moveExtendo(0.18);
                                        })
                                        //O-zone #3
                                        .setReversed(true)
                                        .lineToSplineHeading(new Pose2d(40,-51, Math.toRadians(322)))
                                        .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                            intake.activeIntake.transferSample();
                                        })
                                        .UNSTABLE_addTemporalMarkerOffset(0.3, () -> {
//                                            intake.activeIntake.flipToTransfer();
//                                            intake.activeIntake.motorRollerOff();
                                        })
                                        .UNSTABLE_addDisplacementMarkerOffset(0, () -> {
//                                            intake.extendoFullRetract();
//                                            specimenClaw.openClaw();
                                        })
                                        .waitSeconds(0.15)


                                        //go back to hp #1
                                        .lineToLinearHeading(new Pose2d(40,-60,Math.toRadians(270)))
                                        .waitSeconds(0.15)

                                        //go to box #1
                                        .lineToLinearHeading(new Pose2d(2,-33,Math.toRadians(270)))
                                        .waitSeconds(.15)

                                        //go back to hp #2
                                        .lineToLinearHeading(new Pose2d(40,-60,Math.toRadians(270)))
                                        .waitSeconds(.15)

                                        //go to box #2
                                        .lineToLinearHeading(new Pose2d(4,-33,Math.toRadians(270)))
                                        .waitSeconds(.15)

                                        .build()
                );



        BufferedImage img = null;
        try { img = ImageIO.read(new File("MeepMeep/intothedeep.png")); }
        catch (IOException e) {}

        meepMeep.setBackground(img)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}