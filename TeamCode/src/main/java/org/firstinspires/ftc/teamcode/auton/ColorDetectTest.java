package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utilities.ColorDetect;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;

@Autonomous(name="ColorDetectTest", group="Linear OpMode")
@Config
public class ColorDetectTest extends LinearOpMode {

    /**
     * Tests ColorDetect.readColor() every second for some number of seconds.
     * @throws InterruptedException
     */
    @Override
    public void runOpMode() throws InterruptedException {
        final int SECONDS = 10;

        telemetry.addData("Initialized: ", "Hopefully");
        telemetry.update();

        final double POWER = 0.85;
        ElapsedTime runtime = new ElapsedTime();
        MecanumDrive drivetrain = new MecanumDrive(hardwareMap);

        waitForStart();
        runtime.reset();

        sleep(2000);
        telemetry.addLine("ColorDetect Test beginning!");
        telemetry.update();

        ColorDetect colorDetect = new ColorDetect(hardwareMap);

        for (int i = 0; i < SECONDS; i++) {
            colorDetect.readColor();
            sleep(1000);
        }

        telemetry.addData("Status", "Run Time: " + runtime);
        telemetry.update();
    }
}
