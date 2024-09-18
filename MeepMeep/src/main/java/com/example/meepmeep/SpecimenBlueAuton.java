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

public class SpecimenBlueAuton {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(70, 55, 2.5, 2, 14)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-60, -12, Math.toRadians(180)))

                                //goes to box
                                //score specimen (preloaded)

                                .back(30)
                                .waitSeconds(.5)
                                .forward(5)

                                //go ti HP
                                .setReversed(true)
                                .lineToSplineHeading(new Pose2d(-35, -36, Math.toRadians(90)))
                                .splineTo(new Vector2d(-60, -60 ), Math.toRadians(180))
                                .waitSeconds(.5)

                                //go to box
                                .setReversed(false)
                                .splineTo(new Vector2d(-35, -36), Math.toRadians(90))
                                .lineToSplineHeading(new Pose2d(-35, -9, Math.toRadians(180)))
                                .back(5)
                                .waitSeconds(.5)
                                .forward(5)

                                //go ti HP
                                .setReversed(true)
                                .lineToSplineHeading(new Pose2d(-35, -36, Math.toRadians(90)))
                                .splineTo(new Vector2d(-60, -60 ), Math.toRadians(180))
                                .waitSeconds(.5)

                                //go to box
                                .setReversed(false)
                                .splineTo(new Vector2d(-35, -36), Math.toRadians(90))
                                .lineToSplineHeading(new Pose2d(-35, -6, Math.toRadians(180)))
                                .back(5)
                                .waitSeconds(.5)
                                .forward(5)






                                //get sample
                                .splineToSplineHeading(new Pose2d(-50,-25,Math.toRadians(-60)),Math.toRadians(270))
                                .lineToSplineHeading(new Pose2d(-50, -48, Math.toRadians(0)))
                                .waitSeconds(0.5)
                                .turn(Math.toRadians(-40))
                                .waitSeconds(0.5)
                                .turn(Math.toRadians(40))
                                .waitSeconds(0.5)
                                .turn(Math.toRadians(-50))
                                .waitSeconds(0.5)
                                .turn(Math.toRadians(50))

                                .splineToLinearHeading(new Pose2d(-40, -7, Math.toRadians(180)),Math.toRadians(90))
                                .setReversed(true)
                                .back(5)
                                .waitSeconds(0.5)



//                                .waitSeconds(0.5)
//                                .turn(Math.toRadians(50))


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


