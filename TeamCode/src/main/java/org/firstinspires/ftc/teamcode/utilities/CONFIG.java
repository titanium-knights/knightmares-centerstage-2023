package org.firstinspires.ftc.teamcode.utilities;


import com.acmerobotics.dashboard.config.Config;

@Config
public class CONFIG {
    //drivetrain wheels
    public static String FRONT_LEFT = "fl"; //ch 0
    public static String FRONT_RIGHT = "fr"; //ch 2
    public static String BACK_LEFT = "bl"; //ch 3
    public static String BACK_RIGHT = "br"; //ch 1

    //webcam
    public static String webcam = "Webcam 1";

    //claw open and close
    public static String clawServo = "claw"; //ch 1
    //rotate claw
    public static String clawSpin = "clawSpin"; //ch 0


    //move arm up and down
    public static String liftMotor = "lift"; //exp 2

    public static String pullUpMotor1 = "pullUp1"; // TODO: check what button it is
    public static String pullUpMotor2 = "pullUp2"; // TODO: check what button it is



}
