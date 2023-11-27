package org.firstinspires.ftc.teamcode.auton.dropandpark;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utilities.Claw;
import org.firstinspires.ftc.teamcode.utilities.InitialVision;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.PlaneLauncher;
import org.firstinspires.ftc.teamcode.utilities.Intake;

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

        InitialVision vision = new InitialVision(hardwareMap, telemetry, "blue");
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
                sleep(500);
                intake.stop();
                drivetrain.move(0,POWER, 0);
                sleep(150);
                stopDrive();
                drivetrain.move(-POWER, 0, 0);
                sleep(800);
                stopDrive();
                drivetrain.move(0,-POWER, 0);
                sleep(1600);
                stopDrive();
                break;
            case 3:
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
                sleep(500);
                intake.stop();
                drivetrain.move(0,POWER, 0);
                sleep(150);
                stopDrive();
                drivetrain.move(POWER, 0, 0);
                sleep(800);
                stopDrive();
                drivetrain.move(0,POWER, 0);
                sleep(1600);
                stopDrive();
                break;
            case 2:
            default:
                // SPIKE 2
                drivetrain.move(0,POWER, 0);
                sleep(1300); // guess #1
                stopDrive();
                intake.runReverse();
                sleep(500);
                intake.stop();
                drivetrain.move(POWER, 0, 0);
                sleep(800);
                stopDrive();
                drivetrain.move(0, -POWER, 0);
                sleep(1300); // guess #1
                stopDrive();
                drivetrain.move(POWER, 0, 0);
                sleep(800);
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
