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

@Autonomous(name="testerblue", group="Linear OpMode")
@Config
public class testerblue extends LinearOpMode {

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
        intake.setUp();
        PlaneLauncher plane = new PlaneLauncher(hardwareMap);
        plane.reset();
        stick = new Stick(hardwareMap);
        stick.lock();

        InitialVision vis = new InitialVision(hardwareMap, telemetry, "blue");
        waitForStart();
        runtime.reset();

        int pos = vis.getPosition(); // change this line to pos = 1, 2, or 3 to test a specific position

        //BLUE LEFT
        /*
        switch (pos) {
            case 1:
                telemetry.addLine("Case 1");
                backOne();
                backOneEighth();
                turnCounterClockwise();
                backOneEighth();
                dropPixel();
                forwardOneEighth();
                rightOne();
                rightOne();
                backTwo();
                break;
            case 3:
                telemetry.addLine("Case 3");
                backOne();
                backOneEighth();
                turnClockwise();
                backOneEighth();
                dropPixel();
                forwardOneEighth();
                leftOne();
                leftOne();
                forwardTwo();
                forwardOne();
                break;
            case 2:
                telemetry.addLine("Case 2");
            default:
                telemetry.addLine("Defaulting to case 2");
                backOne();
                backOneEighth();
                dropPixel();
                forwardOne();
                forwardOneEighth();
                rightFour();
                break;
        } **/

        //BLUE RIGHT
        switch (pos) {
            case 1:
                telemetry.addLine("Case 1");
                backOne();
                backOneEighth();
                turnCounterClockwise();
                backOneEighth();
                dropPixel();
                forwardOneEighth();
                rightOne();
                forwardOne();
                leftOne();
                leftOne();
                leftOne();
                backFour();
                backOne();
                break;
            case 3:
                telemetry.addLine("Case 3");
                backOne();
                backOneEighth();
                turnClockwise();
                backOneEighth();
                dropPixel();
                forwardOneEighth();
                leftOne();
                backOne();
                rightOne();
                rightOne();
                forwardFour();
                forwardOne();
                break;
            case 2:
                telemetry.addLine("Case 2");
            default:
                telemetry.addLine("Defaulting to case 2");
                backOne();
                backOneEighth();
                dropPixel();
                forwardOne();
                forwardOneEighth();
                leftOne();
                backTwo();
                rightFour();
                rightOne();
                break;
        }

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
        sleep(1300);
        stopDrive();
    }

    public void backTwo() {
        drivetrain.move(0,POWER, 0);
        sleep(2200);
        stopDrive();
    }
    public void backFour() {
        drivetrain.move(0,POWER, 0);
        sleep(3400); // TODO: tune
        stopDrive();
    }
    public void forwardOne() {
        drivetrain.move(0,-POWER, 0);
        sleep(1300);
        stopDrive();
    }
    public void forwardTwo() {
        drivetrain.move(0, -POWER, 0);
        sleep(2200);
        stopDrive();
    }
    public void forwardFour() {
        drivetrain.move(0,-POWER, 0);
        sleep(3200); // TODO: tune
        stopDrive();
    }
    public void forwardOneEighth() {
        drivetrain.move(0,-POWER, 0);
        sleep(200); // TODO: tune
        stopDrive();
    }
    public void backOneEighth() {
        drivetrain.move(0,POWER, 0);
        sleep(200); // TODO: tune
        stopDrive();
    }

    // TURNING
    public void turnClockwise() {
        drivetrain.move(0,0, -POWER);
        sleep(965);
        stopDrive();
    }
    public void turnCounterClockwise() {
        drivetrain.move(0,0, POWER);
        sleep(950);
        stopDrive();
    }

    // STRAFING
    public void rightOne() {
        drivetrain.move(-POWER, 0, 0);
        sleep(1800);
        stopDrive();
    }
    public void rightFour() {
        drivetrain.move(-POWER,0, 0);
        sleep(5200);
        stopDrive();
    }
    public void leftOne() {
        drivetrain.move(POWER, 0, 0);
        sleep(1800);
        stopDrive();
    }
    public void leftFour() { // NOTE: moves slightly forward
        drivetrain.move(POWER,0, 0);
        sleep(4600);
        stopDrive();
    }
}