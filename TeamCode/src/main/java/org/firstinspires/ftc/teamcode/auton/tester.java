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
    Stick stick;
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
        stick = new Stick(hardwareMap);
        stick.lock();
        intake.setUp();

        InitialVision vis = new InitialVision(hardwareMap, telemetry, "blue");
        waitForStart();
        runtime.reset();

        // TODO: tune everything
        // RED LEFT (new) - wasn't sure when to put stick.unlock() in any of these I'll need help w that.
        // red-left-spike2
//        backOne();
//        dropPixel();
//        forwardOne();
//        rightHalf();
//        backTwo();
//        turnCounterClockwise();
//        forwardTwo();
//        rightOne();
//        forwardTwo();

//
//        // red-left-spike1
//        backOne();
//        turnCounterClockwise();
//        backOneEighth();
//        dropPixel();
//        forwardOneEighth();
//        leftOne();
//        forwardTwo();
//        rightOne();
//        forwardOne();
//
//        // red-left-spike3
//        backOne();
//        turnClockwise();
//        backOneEighth();
//        dropPixel();
//        forwardOneEighth();
//        rightOne();
//        backFour();
//
//        // RED RIGHT
//        // red-right-spike2
//        backOne();
//        dropPixel();
//        forwardOneEighth();
//        leftTwo();
//
//        // red-right-spike1
//        backOne();
//        turnCounterClockwise();
//        backOneEighth();
//        dropPixel();
//        forwardOneHalf();
//
//        // red-right-spike3
//        backOne();
//        turnClockwise();
//        backOneEighth();
//        dropPixel();
//        forwardOneEighth();
//        leftOne();
//        backTwo();

        telemetry.addData("Status", "Run Time: " + runtime);
        telemetry.addLine("Please work thanks! ");
        telemetry.update();

    }

    public void dropPixel() {
        stick.unlock();
        sleep(300);
    }

    public void stopDrive() {
        drivetrain.move(0, 0, 0);
        sleep(100);
    }

    // FORWARD AND BACKWARD
    public void backOne() {
        drivetrain.move(0,POWER, 0);
        sleep(1250);
        stopDrive();
    }

    public void backTwo() {
        drivetrain.move(0,POWER, 0);
        sleep(2000);
        stopDrive();
    }
    public void backFour() { // turns slightly right
        drivetrain.move(0,POWER, 0);
        sleep(3500);
        stopDrive();
    }
    public void forwardOne() {
        drivetrain.move(0,-POWER, 0);
        sleep(1300);
        stopDrive();
    }
    public void forwardOneHalf() {
        drivetrain.move(0,-POWER, 0);
        sleep(1800);
        stopDrive();
    }

    public void forwardTwo() {
        drivetrain.move(0, -POWER, 0);
        sleep(2200);
        stopDrive();
    }
    public void forwardFour() { // turns slightly left
        drivetrain.move(0,-POWER, 0);
        sleep(3500);
        stopDrive();
    }
    public void forwardOneEighth() {
        drivetrain.move(0,-POWER, 0);
        sleep(300);
        stopDrive();
    }
    public void backOneEighth() {
        drivetrain.move(0,POWER, 0);
        sleep(300);
        stopDrive();
    }

    // TURNING
    public void turnClockwise() {
        drivetrain.move(0,0, -POWER);
        sleep(830);
        stopDrive();
    }
    public void turnCounterClockwise() {
        drivetrain.move(0,0, POWER);
        sleep(925);
        stopDrive();
    }

    // STRAFING
    public void rightHalf() {
        drivetrain.move(-POWER, 0, 0);
        sleep(800);
        stopDrive();
    }
    public void rightOne() {
        drivetrain.move(-POWER, 0, 0);
        sleep(1200);
        stopDrive();
    }
    public void rightFour() {
        drivetrain.move(-POWER,0, 0);
        sleep(5200);
        stopDrive();
    }
    public void leftOneHalf() {
        drivetrain.move(POWER, 0, 0);
        sleep(1200);
        stopDrive();
    }

    public void leftOne() {
        drivetrain.move(POWER, 0, 0);
        sleep(1600);
        stopDrive();
    }

    public void leftTwo() {
        drivetrain.move(POWER, 0, 0);
        sleep(2600);
        stopDrive();
    }
    public void leftFour() { // NOTE: moves slightly forward
        drivetrain.move(POWER,0, 0);
        sleep(5600);
        stopDrive();
    }
}