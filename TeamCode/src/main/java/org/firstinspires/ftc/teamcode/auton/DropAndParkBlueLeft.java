package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utilities.Claw;
import org.firstinspires.ftc.teamcode.utilities.Lift;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.PlaneLauncher;

@Autonomous(name="BlueLeft-DropPark", group="Linear OpMode")
@Config
public class DropAndParkBlueLeft extends LinearOpMode {
    //constant assumptions for power and time for movement
    public static final double POWER = 0.6;
    public static final long FOOT = 525;

    MecanumDrive drivetrain;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Initialized: ", "Hopefully");
        telemetry.update();

        ElapsedTime runtime = new ElapsedTime();
        Claw claw = new Claw(hardwareMap);
        PlaneLauncher plane = new PlaneLauncher(hardwareMap);
        plane.set();
        // rotate claw down to keep pixel in place
        claw.setZero();
        //drivetrain.move() -y is forward, +x is right, +turn is counterclockwise robot centric
        drivetrain = new MecanumDrive(hardwareMap);

        waitForStart();
        runtime.reset();

        // SPIKE 2
        drivetrain.move(0,POWER, 0);
        sleep(1300); // guess #1
        stopDrive();
        // then drop pixel
        drivetrain.move(POWER, 0, 0);
        sleep(800);
        stopDrive();
        drivetrain.move(-POWER, 0, 0);
        sleep(1300); // guess #1
        stopDrive();
        drivetrain.move(POWER, 0, 0);
        sleep(800);
        stopDrive();

        // SPIKE 1
        drivetrain.move(0,POWER, 0);
        sleep(900);
        stopDrive();
        drivetrain.move(0,0, -POWER);
        sleep(550);
        stopDrive();
        drivetrain.move(0,-POWER, 0);
        sleep(150);
        stopDrive();
        // drop the pixel
        drivetrain.move(0,POWER, 0);
        sleep(150);
        stopDrive();
        drivetrain.move(-POWER, 0, 0);
        sleep(800);
        stopDrive();
        drivetrain.move(0,-POWER, 0);
        sleep(1600);
        stopDrive();

        // SPIKE 3
        drivetrain.move(0,POWER, 0);
        sleep(900);
        stopDrive();
        drivetrain.move(0,0, POWER);
        sleep(600);
        stopDrive();
        drivetrain.move(0,-POWER, 0);
        sleep(150);
        stopDrive();
        // drop the pixel
        drivetrain.move(0,POWER, 0);
        sleep(150);
        stopDrive();
        drivetrain.move(POWER, 0, 0);
        sleep(800);
        stopDrive();
        drivetrain.move(0,POWER, 0);
        sleep(1600);
        stopDrive();

        telemetry.addData("Run Time:", runtime);
        telemetry.update();
    }

    public void stopDrive() {
        drivetrain.move(0, 0, 0);
        sleep(100);
    }

    //TODO: either remove these methods or incorporate them
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
