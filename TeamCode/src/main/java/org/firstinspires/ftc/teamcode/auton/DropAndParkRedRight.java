package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utilities.Claw;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.PlaneLauncher;

@Autonomous(name="RedRight-DropPark", group="Linear OpMode")
@Config
public class DropAndParkRedRight extends LinearOpMode {
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
        //drivetrain.move() -y is forward, +x is left
        drivetrain = new MecanumDrive(hardwareMap);

        waitForStart();
        runtime.reset();

        // forward to spike mark
        drivetrain.move(0,-POWER, 0);
        sleep(1000);
        stopDrive();
        claw.open();
        //back up to starting position
        drivetrain.move(0,POWER, 0);
        sleep(1000);
        stopDrive();
        // move right into parking zone (in backstage)
        drivetrain.move(POWER,0, 0);
        sleep(1600);
        stopDrive();
    }

    public void stopDrive() {
        drivetrain.move(0, 0, 0);
        sleep(100);
    }
}
