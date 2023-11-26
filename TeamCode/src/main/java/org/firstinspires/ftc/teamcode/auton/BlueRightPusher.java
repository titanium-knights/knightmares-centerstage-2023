package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utilities.Intake;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;

@Autonomous(name="BlueRightBrokenPushbot", group="Linear OpMode")
@Config
public class BlueRightPusher extends LinearOpMode {

    MecanumDrive drivetrain;
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Initialized: ", "Hopefully");
        telemetry.update();

        final double POWER = 0.85;
        ElapsedTime runtime = new ElapsedTime();
        drivetrain = new MecanumDrive(hardwareMap);
        Intake intake = new Intake(hardwareMap);

        waitForStart();
        runtime.reset();

        drivetrain.move(0, POWER, 0);
        sleep(1800);
        stopDrive();

        intake.runReverse();
        sleep(600);
        intake.stop();

        drivetrain.move(POWER, 0, 0);
        sleep(5555);
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
