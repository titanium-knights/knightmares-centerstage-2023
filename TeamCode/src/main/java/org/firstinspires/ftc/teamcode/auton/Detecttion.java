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
            case 1: // left spike
                forward(30);
                claw.open();
                back(30);
                right(30);
                forward(48);
                left(120);
                back(30);
                break;
            case 2: //middle spike
                claw.open();
                sleep(300);
                drivetrain.move(0, -POWER, 0);
                sleep((long) (FOOT*1.5));
                stopDrive();
                drivetrain.move(0, 0, POWER);
                sleep(TURN_45*2);
                stopDrive();
                break;
            default: // right spike (or we somehow dont know)
                drivetrain.move(0,0,-POWER);
                sleep(TURN_45*2);
                stopDrive();
                claw.open();
        }


        stopDrive();
        telemetry.addData("Run Time:", runtime);
        telemetry.update();
    }

    public void stopDrive() {
        drivetrain.move(0, 0, 0);
        sleep(100);
    }

    public void forward(int inches) {
        drivetrain.move(0,-POWER, 0);
        sleep((long) ((inches/12)*FOOT));
        stopDrive();
    }

    public void left(int inches) {
        drivetrain.move(-POWER,0, 0);
        sleep((long) ((inches/12)*FOOT));
        stopDrive();
    }

    public void right(int inches) {
        drivetrain.move(POWER,0, 0);
        sleep((long) ((inches/12)*FOOT));
        stopDrive();
    }

    public void back(int inches) {
        drivetrain.move(0,POWER, 0);
        sleep((long) ((inches/12)*FOOT));
        stopDrive();
    }

}
