package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utilities.InitialVision;
import org.firstinspires.ftc.teamcode.utilities.Intake;
import org.firstinspires.ftc.teamcode.utilities.SimpleMecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.PlaneLauncher;
import org.firstinspires.ftc.teamcode.utilities.Stick;
import org.firstinspires.ftc.teamcode.utilities.Arm;
import org.firstinspires.ftc.teamcode.utilities.Bay;

@Autonomous(name="tester", group="Linear OpMode")
@Config
public class tester extends LinearOpMode {

    SimpleMecanumDrive drivetrain;
    Arm arm;
    Bay bay;
    Stick stick;
    public static int forward_time = 1100;
    public static int strafe_time = 4000;
    public final double POWER = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Initialized: ", "Hopefully");
        telemetry.update();

        ElapsedTime runtime = new ElapsedTime();
        drivetrain = new SimpleMecanumDrive(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        PlaneLauncher plane = new PlaneLauncher(hardwareMap);
        Arm arm = new Arm(hardwareMap);
        Bay bay = new Bay(hardwareMap);
        plane.reset();
        stick = new Stick(hardwareMap);
        stick.lock();
        //intake.setUp();

        arm.drivingPos();

        InitialVision vis = new InitialVision(hardwareMap, telemetry, "blue");
        waitForStart();
        runtime.reset();
        int pos = vis.getPosition();

        bay.disable();
        sleep(100);
        arm.toDrop();
        sleep(2000);
        bay.setDrop();
        sleep(2000);
        bay.open();
        sleep(500);

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
    public void backHalf() {
        drivetrain.move(0,POWER, 0);
        sleep(800);
        stopDrive();
    }
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
    public void backThree() { // TODO: tune this and update
        drivetrain.move(0,POWER, 0);
        sleep(3200);
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
        sleep(1300); // TODO: update this
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
        sleep(1300); //TODO: update this in auton methods
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

    public void paintPixel() { // TODO: figure out how this works
        bay.disable();
        sleep(100);
        arm.toDrop();
        sleep(500);
        bay.setDrop();
        sleep(100);
        bay.open();
        sleep(100);
    }

    public void returnInit() { // TODO: figure out if this even works
        bay.close();
        bay.setPosition(1.0);
        arm.drivingPos();
        bay.disable();
        if (arm.getPosition() <= 60) {
            bay.setPick();
        }
        arm.toPickUp();
        sleep(100);
    }
}