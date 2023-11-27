package org.firstinspires.ftc.teamcode.auton.pushbot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utilities.InitialVision;
import org.firstinspires.ftc.teamcode.utilities.Intake;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;

@Autonomous(name="JustParkRedLeft", group="Linear OpMode")
@Config
public class RedLeftPusher extends LinearOpMode {

    MecanumDrive drivetrain;
    public static int forward_time = 925;
    public static int strafe_time = 4000;
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Initialized: ", "Hopefully");
        telemetry.update();

        final double POWER = 0.85;

        ElapsedTime runtime = new ElapsedTime();
        drivetrain = new MecanumDrive(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        InitialVision vis = new InitialVision(hardwareMap, telemetry, "blue");
        waitForStart();
        runtime.reset();

        telemetry.addData("detectede", vis.getPosition());
        drivetrain.move(0, POWER, 0);
        sleep(forward_time);
        stopDrive();

        intake.runReverse();
        sleep(600);
        intake.stop();
        drivetrain.move(0, POWER, 0);
        sleep(250);
        stopDrive();
        drivetrain.move(POWER, 0, 0);
        sleep(strafe_time);
        stopDrive();

        telemetry.addData("Status", "Run Time: " + runtime);
        telemetry.addLine("Please work thanks! ");
        telemetry.update();

    }

    public void stopDrive() {
        drivetrain.move(0, 0, 0);
        sleep(100);
    }
}
