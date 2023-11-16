package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utilities.Claw;
import org.firstinspires.ftc.teamcode.utilities.InitialVision;
import org.firstinspires.ftc.teamcode.utilities.Lift;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;

//blue Right Side
@Autonomous(name="VisionRedMaybe", group="Linear OpMode")
@Config
public class DetectingPipelineOne extends LinearOpMode {
    public static final double POWER = 0.6;
    public static final long FOOT = 500;
    public static final long TURN_45 = 300;
    MecanumDrive drivetrain;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Initialized: ", "Hopefully");
        telemetry.update();

        InitialVision vision = new InitialVision(hardwareMap, telemetry, "blue");
        ElapsedTime runtime = new ElapsedTime();
        Lift lift = new Lift(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        claw.close();
        drivetrain = new MecanumDrive(hardwareMap);

        waitForStart();
        runtime.reset();

        lift.toScan();
        sleep(FOOT/2);
        int place = vision.getPosition();

        //y is forward x is left
        switch (place) {
            case 1:
                drivetrain.move(0, POWER, 0);
                sleep(FOOT*2);
                stopDrive();
                drivetrain.move(0, 0, POWER);
                sleep(TURN_45*2);
                stopDrive();
                claw.open();
                sleep(300);
                drivetrain.move(-POWER, 0, 0);
                sleep((long) (FOOT*1.5));
                stopDrive();
                drivetrain.move(0, POWER, 0);
                sleep((long) (FOOT*3.5));
                stopDrive();
                break;
            case 2:
                drivetrain.move(0, POWER, 0);
                sleep(FOOT*2);
                stopDrive();
                claw.open();
                sleep(300);
                drivetrain.move(0, POWER, 0);
                sleep((long) (FOOT*1.5));
                stopDrive();
                drivetrain.move(0, 0, POWER);
                sleep(TURN_45*2);
                stopDrive();
            default:
        }

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

    public void stopDrive() {
        drivetrain.move(0, 0, 0);
        sleep(100);
    }

}
