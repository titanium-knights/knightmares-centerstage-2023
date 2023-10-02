package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.SignalParkVision;

@Autonomous(name="First Auton", group="Linear OpMode")
@Config
public class BlueAuton extends LinearOpMode {

    protected MechanumDrive drivetrain = new MechanumDrive(hardwareMap);
    protected SignalParkVision vision = new SignalParkVision(hardwareMap, null);

    public static int FORWARD1_TIME = 2000;
    public static int FORWARD2_TIME = 1000;
    public static int DIAGONAL2_TIME = 1000;
    public static double POWER = 0.85;
    public static int position;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Initialized: ", "Hopefully");
        telemetry.update();

        vision.getPosition();
        telemetry.addData("Detected: ", position);
        telemetry.update();

        ElapsedTime runtime = new ElapsedTime();
        MecanumDrive drivetrain = new MecanumDrive(hardwareMap);

        waitForStart();
        runtime.reset();

        // The robot is initially touching the wall.
        drivetrain.move(0, POWER, 0);
        sleep(FORWARD1_TIME);

        switch (position) {
            case 1:
                drivetrain.move(-POWER, POWER, 0);
                sleep(DIAGONAL2_TIME);
            case 2:
                drivetrain.move(0, POWER, 0);
                sleep(FORWARD2_TIME);
            case 3:
                drivetrain.move(-POWER, POWER, 0);
                sleep(DIAGONAL2_TIME);
                
        }

        telemetry.addLine("Moved to position " + position + ".");
        telemetry.update();

        // Spee

        drivetrain.move(0, 0, 0);
        
        telemetry.addData("Status", "Run Time: " + runtime);
        telemetry.update();

    }
}
