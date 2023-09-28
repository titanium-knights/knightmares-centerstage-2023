package org.firstinspires.ftc.teamcode.utilities;


import com.acmerobotics.dashboard.config.Config;

@Config
public class CONFIG {
    //drivetrain wheels :OOO
    public static String FRONT_LEFT = "fl"; //ch 0
    public static String FRONT_RIGHT = "fr"; //ch 2
    public static String BACK_LEFT = "bl"; //ch 3
    public static String BACK_RIGHT = "br"; //ch 1

    //webcam
    public static String webcam = "Webcam 1";

    //subsystems
    public static String clawServoLeft = "claw"; //ch 1

    public static String clawServoRight = "clawLift"; //exp 2

    public static String clawSpin = "clawSpin"; //ch 0

    public static String encServo = "encServo"; //ch 2


    // so I'm pretty sure we are only using one motor for this year's robot so I will choose
    // the right motor.
    public static String liftMotorRight = "lmr"; //exp 1
    public static String liftMotorLeft = "lml";  //exp 0

    public static String O_C = "fr"; //center odo, ch 2
    public static String O_L = "fl"; //left odo, ch 0
    public static String O_R = "br"; //right odo, ch 1

    //OLD
    public static String liftMotor = "lm"; //

}
