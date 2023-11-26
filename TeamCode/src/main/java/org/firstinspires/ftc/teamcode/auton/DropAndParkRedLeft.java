package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utilities.Claw;
import org.firstinspires.ftc.teamcode.utilities.InitialVision;
import org.firstinspires.ftc.teamcode.utilities.Intake;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.PlaneLauncher;
import org.firstinspires.ftc.teamcode.utilities.Intake;

@Autonomous(name="RedLeft-DropPark", group="Linear OpMode")
@Config
public class DropAndParkRedLeft extends LinearOpMode {
    MecanumDrive drivetrain;
    final double POWER = 0.6;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Initialized: ", "Hopefully");
        telemetry.update();

        InitialVision vision = new InitialVision(hardwareMap, telemetry);
        ElapsedTime runtime = new ElapsedTime();
        Claw claw = new Claw(hardwareMap);
        PlaneLauncher plane = new PlaneLauncher(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        //drivetrain.move() -y is forward, +x is right, +turn is counterclockwise robot centric
        drivetrain = new MecanumDrive(hardwareMap);

        waitForStart();
        runtime.reset();
        int pos = vision.getPosition();

        switch (pos) {
            case 1:
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
                intake.runReverse();
                drivetrain.move(0,POWER, 0);
                sleep(150);
                stopDrive();
                drivetrain.move(POWER, 0, 0);
                sleep(800);
                stopDrive();
                drivetrain.move(0,-POWER, 0);
                sleep(4500); // guess #2
                stopDrive();
                break;
            case 2:
                // SPIKE 2
                drivetrain.move(0,-POWER, 0);
                sleep(1300); // guess #1
                stopDrive();
                intake.runReverse();
                drivetrain.move(-POWER,0, 0);
                sleep(4200);
                stopDrive();
                break;
            case 3:
            default:
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
                intake.runReverse();
                drivetrain.move(0,POWER, 0);
                sleep(150);
                stopDrive();
                drivetrain.move(-POWER, 0, 0);
                sleep(800);
                stopDrive();
                drivetrain.move(0,-POWER, 0);
                sleep(4500); // guess #2
                stopDrive();
                break;
        }

        telemetry.addData("Run Time:", runtime);
        telemetry.update();
    }

    public void stopDrive() {
        drivetrain.move(0, 0, 0);
        sleep(100);
    }
}
