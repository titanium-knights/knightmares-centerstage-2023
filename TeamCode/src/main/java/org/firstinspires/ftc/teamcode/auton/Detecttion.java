package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utilities.Claw;
import org.firstinspires.ftc.teamcode.utilities.InitialVision;
import org.firstinspires.ftc.teamcode.utilities.Lift;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;

//blue Right Side
@Autonomous(name="VisionBlueMaybe", group="Linear OpMode")
@Config
public class Detecttion extends LinearOpMode {
    //constant assumptions for power and time for movement
    public static final double POWER = 0.6;
    public static final long FOOT = 525;
    public static final long TURN_45 = 500; //inaccurate

    MecanumDrive drivetrain;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Initialized: ", "Hopefully");
        telemetry.update();

        InitialVision vision = new InitialVision(hardwareMap, telemetry, "blue");
        ElapsedTime runtime = new ElapsedTime();
        Lift lift = new Lift(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        claw.close();
        //drivetrain.move() -y is forward, +x is left
        drivetrain = new MecanumDrive(hardwareMap);

        waitForStart();
        runtime.reset();

        int place = vision.getPosition();
        telemetry.addData("Position: ", place);
        telemetry.update();
        claw.close();

        switch (place) {
            case 1: // left spike (no specific code yet)
//                forward(32);
                drivetrain.move(0,-POWER, 0);
                sleep(985);
                stopDrive();
                claw.open();
//                back(32);
                drivetrain.move(0,POWER, 0);
                sleep(985);
                stopDrive();
//                right(24);
                drivetrain.move(POWER,0, 0);
                sleep(700);
                stopDrive();
                //                forward(52);
                drivetrain.move(0,-POWER, 0);
                sleep(1600);
                stopDrive();
                //                right(12);
                drivetrain.move(POWER,0, 0);
                sleep(600);
                stopDrive();
                break;
            case 2: //middle spike
//              forward(32);
                drivetrain.move(0,-POWER, 0);
                sleep(985);
                stopDrive();
                claw.open();
//                back(32);
                drivetrain.move(0,POWER, 0);
                sleep(985);
                stopDrive();
//                right(24);
                drivetrain.move(POWER,0, 0);
                sleep(800);
                stopDrive();
//                forward(52);
                drivetrain.move(0,-POWER, 0);
                sleep(1600);
                stopDrive();
//                left(120);
                drivetrain.move(-POWER,0, 0);
                sleep(4200);
                stopDrive();
//                back(24);
                drivetrain.move(0,POWER, 0);
                sleep(1100);
                stopDrive();
                break;
            default:
                break;
        }


        stopDrive();
        telemetry.addData("Run Time:", runtime);
        telemetry.update();
    }

    public void stopDrive() {
        drivetrain.move(0, 0, 0);
        sleep(100);
    }

    public void forward(double inches) {
        long FORWARD_FOOT = 400;
        drivetrain.move(0,-POWER, 0);
        sleep((long) ((inches/12)*FORWARD_FOOT));
        stopDrive();
    }

    public void back(double inches) {
        long BACKWARD_FOOT = 415;
        drivetrain.move(0,POWER, 0);
        sleep((long) ((inches/12)*BACKWARD_FOOT));
        stopDrive();
    }

    public void left(double inches) {
        int STRAFE_FOOT = 525;
        drivetrain.move(-POWER,0, 0);
        sleep((long) ((inches/12)*STRAFE_FOOT));
        stopDrive();
    }

    public void right(double inches) {
        int STRAFE_FOOT = 525;
        drivetrain.move(POWER,0, 0);
        sleep((long) ((inches/12)*STRAFE_FOOT));
        stopDrive();
    }



}
