package org.firstinspires.ftc.teamcode.auton.dropandpark;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utilities.Arm;
import org.firstinspires.ftc.teamcode.utilities.Bay;
import org.firstinspires.ftc.teamcode.utilities.Intake;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.PlaneLauncher;
import org.firstinspires.ftc.teamcode.utilities.Stick;

public abstract class DropAndPark extends LinearOpMode {
    MecanumDrive drivetrain;
    Stick stick;
    Intake intake;
    PlaneLauncher plane;
    Arm arm;
    Bay bay;

    @Override
    public void runOpMode() throws InterruptedException {
        drivetrain = new MecanumDrive(hardwareMap);
        stick = new Stick(hardwareMap);
        intake = new Intake(hardwareMap);
        plane = new PlaneLauncher(hardwareMap);
        arm = new Arm(hardwareMap);
        bay = new Bay(hardwareMap);
    }

    public final double POWER = 0.5;

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

    public void backOneSixteenth() {
        drivetrain.move(0,POWER, 0);
        sleep(150);
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
    public void rightOneEighth() {
        drivetrain.move(-POWER, 0, 0);
        sleep(200);
        stopDrive();
    }
    public void rightOneFourth() {
        drivetrain.move(-POWER, 0, 0);
        sleep(400);
        stopDrive();
    }

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

    public void rightTwo() {
        drivetrain.move(-POWER, 0, 0);
        sleep(2600);
        stopDrive();
    }
    public void rightFour() {
        drivetrain.move(-POWER,0, 0);
        sleep(5200);
        stopDrive();
    }

    public void leftHalf() {
        drivetrain.move(POWER, 0, 0);
        sleep(800);
        stopDrive();
    }

    public void leftOne() {
        drivetrain.move(POWER, 0, 0);
        sleep(1200);
        stopDrive();
    }

    public void leftOneHalf() {
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
