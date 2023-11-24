package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utilities.Claw;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.PlaneLauncher;

@Autonomous(name="BlueRight-DropPark", group="Linear OpMode")
@Config
public class DropAndParkBlueRight extends LinearOpMode {
    MecanumDrive drivetrain;
    final double POWER = 0.6;

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry.addData("Initialized: ", "Hopefully");
        telemetry.update();

        ElapsedTime runtime = new ElapsedTime();
        Claw claw = new Claw(hardwareMap);
        PlaneLauncher plane = new PlaneLauncher(hardwareMap);
        claw.close();
        plane.set();
        //drivetrain.move() -y is forward, +x is right, +turn is counterclockwise robot centric
        drivetrain = new MecanumDrive(hardwareMap);

        waitForStart();
        runtime.reset();

        // SPIKE 2
        drivetrain.move(0,POWER, 0);
        sleep(1300); // guess #1
        stopDrive();
        // then drop pixel
        drivetrain.move(POWER,0, 0);
        sleep(4200);
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
        drivetrain.move(POWER, 0, 0);
        sleep(800);
        stopDrive();
        drivetrain.move(0,-POWER, 0);
        sleep(4500); // guess #2
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
        drivetrain.move(-POWER, 0, 0);
        sleep(800);
        stopDrive();
        drivetrain.move(0,-POWER, 0);
        sleep(4500); // guess #2
        stopDrive();

        telemetry.addData("Run Time:", runtime);
        telemetry.update();
    }

    public void stopDrive() {
        drivetrain.move(0, 0, 0);
        sleep(100);
    }
}
