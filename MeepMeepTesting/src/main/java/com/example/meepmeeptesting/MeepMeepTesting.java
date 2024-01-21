package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);


        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-35.5, 60, Math.toRadians(90)))
                                .splineTo(new Vector2d(-35.5, 60), Math.toRadians(90))
                                .splineTo(new Vector2d(50, -38), Math.toRadians(0))
                                .build()
                                // paint pixel

                );

//        .back(28)
//                .turn(Math.toRadians(-90))
//                .back(3)
//                // drop pixel
//                .forward(3)
//                .turn(Math.toRadians(90))
//                .back(28)
//                .turn(Math.toRadians(90))
//                .back(70)
//                .strafeRight(36)
//                .back(14)
//                // paint pixel
//                // return init
//                .build();

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
