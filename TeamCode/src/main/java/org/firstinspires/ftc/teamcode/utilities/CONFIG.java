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

    //bay open and close
    public static String bayServo = "bay"; //ch 1
    //rotate bay
    public static String baySpin = "bayspin"; //ch 0


    //move arm up and down
    public static String armMotor = "arm"; //exp 2

    public static String colorSensor = "color"; //ch 0

    public static String pullUpMotor1 = "pl1"; // Left
    public static String pullUpMotor2 = "pl2"; // Right

    public static String planeServo = "plane";
    public static String intake = "intake";

    public static String intakeRotator = "intakeRotator";

    public static String stick = "stick";

    public static String slide = "slide";
}
