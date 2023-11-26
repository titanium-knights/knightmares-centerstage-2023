package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;

//Red Right Side
@Autonomous(name="JustParkRedRight", group="Linear OpMode")
@Config
public class BrokenIgnorableOpMode extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Initialized: ", "Hopefully");
        telemetry.update();

        final double POWER = 0.85;
        ElapsedTime runtime = new ElapsedTime();
        MecanumDrive drivetrain = new MecanumDrive(hardwareMap);

        waitForStart();
        runtime.reset();

        drivetrain.move(-POWER, POWER, 0);

        telemetry.addLine("I think I should have moved");
        telemetry.update();

        sleep(1800);

        drivetrain.move(-POWER, -POWER, 0);

        sleep(2000);
        telemetry.addLine("moved more");
        telemetry.update();


        drivetrain.move(0, 0, 0);
        telemetry.addData("Status", "Run Time: " + runtime);
        telemetry.update();

    }
}
