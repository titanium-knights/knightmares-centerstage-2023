package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;

// confused
@Autonomous(name="MudasirCantCode", group="Linear OpMode")
@Config
public class BasicMovingOpMode extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Initialized: ", "Hopefully");
        telemetry.update();

        ElapsedTime runtime = new ElapsedTime();
        MecanumDrive drivetrain = new MecanumDrive(hardwareMap);

        waitForStart();
        runtime.reset();

        drivetrain.move(10, 10, 0);

        telemetry.addLine("I think I should have moved");
        telemetry.update();

        sleep(2000);

        drivetrain.move(20, -10, 6);

        telemetry.addLine("moved more");
        telemetry.addData("Status", "Run Time: " + runtime);
        telemetry.update();

    }
}
