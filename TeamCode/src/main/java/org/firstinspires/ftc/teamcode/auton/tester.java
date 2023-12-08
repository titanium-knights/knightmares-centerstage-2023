package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utilities.InitialVision;
import org.firstinspires.ftc.teamcode.utilities.Intake;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.PlaneLauncher;
import org.firstinspires.ftc.teamcode.utilities.Stick;

@Autonomous(name="tester", group="Linear OpMode")
@Config
public class tester extends LinearOpMode {

    MecanumDrive drivetrain;
    public static int forward_time = 1100;
    public static int strafe_time = 4000;
    public final double POWER = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Initialized: ", "Hopefully");
        telemetry.update();

        ElapsedTime runtime = new ElapsedTime();
        drivetrain = new MecanumDrive(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        PlaneLauncher plane = new PlaneLauncher(hardwareMap);
        plane.reset();
        Stick stick = new Stick(hardwareMap);
        stick.lock();
        stick.unlock();
        InitialVision vis = new InitialVision(hardwareMap, telemetry, "blue");
        waitForStart();
        runtime.reset();

        stick.unlock();

        //backTwo();

//        // RED LEFT /////////////////
//        // red-left-spike2
//        backTwo();
//        stick.unlock();
//        leftFour();
//
//        // red-left-spike1
//        backOne();
//        turnClockwise();
//        forwardOneEighth();
//        stick.unlock();
//        backOneEighth();
//        rightOne();
//        backFour();
//
//        // red-left-spike3
//        backOne();
//        turnCounterClockwise();
//        forwardOneEighth();
//        stick.unlock();
//        backOneEighth();
//        leftOne();
//        forwardFour();
//
//        // RED RIGHT /////////////////
//        // red-right-spike2
//        backTwo();
//        stick.unlock();
//        leftOne();
//        forwardTwo();
//        leftOne();
//
//        // red-right-spike1
//        backOne();
//        turnClockwise();
//        forwardOneEighth();
//        stick.unlock();
//        backOneEighth();
//        leftOne();
//        backTwo();
//
//        // red-right-spike3
//        backOne();
//        turnCounterClockwise();
//        forwardOneEighth();
//        stick.unlock();
//        backOneEighth();
//        rightOne();
//        forwardOne();
//
//        // BLUE LEFT ////////////////
//        // blue-left-spike2
//        backTwo();
//        stick.lock();
//        rightOne();
//        forwardTwo();
//        rightOne();
//
//        // blue-left-spike1
//        backOne();
//        turnClockwise();
//        forwardOneEighth();
//        stick.unlock();
//        backOneEighth();
//        leftOne();
//        forwardTwo();
//
//        // blue-left-spike3
//        backOne();
//        turnCounterClockwise();
//        forwardOneEighth();
//        stick.unlock();
//        backOneEighth();
//        rightOne();
//        backTwo();
//
//        // BLUE RIGHT ////////////////
//        // blue-right-spike1
//        backOne();
//        turnClockwise();
//        forwardOneEighth();
//        stick.unlock();
//        backOneEighth();
//        rightOne();
//        forwardFour();
//
//        // blue-right-spike2
//        backTwo();
//        stick.unlock();
//        rightFour();
//
//        // blue-right-spike3
//        backOne();
//        turnCounterClockwise();
//        forwardOneEighth();
//        stick.unlock();
//        backOneEighth();
//        leftOne();
//        backFour();

        telemetry.addData("Status", "Run Time: " + runtime);
        telemetry.addLine("Please work thanks! ");
        telemetry.update();

    }

    public void stopDrive() {
        drivetrain.move(0, 0, 0);
        sleep(100);
    }

    // FORWARD AND BACKWARD
    public void backOne() {
        drivetrain.move(0,POWER, 0);
        sleep(650);
        stopDrive();
    }

    public void backDrop() {
        drivetrain.move(0,POWER, 0);
        sleep(800); // TODO: tune
        stopDrive();
    }

    public void backTwo() {
        drivetrain.move(0,POWER, 0);
        sleep(1300); // TODO: tune
        stopDrive();
    }
    public void backFour() {
        drivetrain.move(0,POWER, 0);
        sleep(2050);
        stopDrive();
    }
    public void forwardOne() {
        drivetrain.move(0,-POWER, 0);
        sleep(650);
        stopDrive();
    }
    public void forwardTwo() {
        drivetrain.move(0, -POWER, 0);
        sleep(1300); // TODO: tune
        stopDrive();
    }
    public void forwardFour() {
        drivetrain.move(0,-POWER, 0);
        sleep(2050);
        stopDrive();
    }
    public void forwardOneEighth() {
        drivetrain.move(0,-POWER, 0);
        sleep(200);
        stopDrive();
    }
    public void backOneEighth() {
        drivetrain.move(0,POWER, 0);
        sleep(200);
        stopDrive();
    }

    // TURNING
    public void turnClockwise() {
        drivetrain.move(0,0, -POWER);
        sleep(450);
        stopDrive();
    }
    public void turnCounterClockwise() {
        drivetrain.move(0,0, POWER);
        sleep(600); // TODO: tune
        stopDrive();
    }

    // STRAFING
    public void rightOne() {
        drivetrain.move(-POWER, 0, 0);
        sleep(550);
        stopDrive();
    }
    public void rightFour() {
        drivetrain.move(-POWER,0, 0);
        sleep(1300); // TODO: tune
        stopDrive();
    }
    public void leftOne() {
        drivetrain.move(POWER, 0, 0);
        sleep(800);
        stopDrive();
    }
    public void leftFour() {
        drivetrain.move(POWER,0, 0);
        sleep(1300); // TODO: tune
        stopDrive();
    }
}