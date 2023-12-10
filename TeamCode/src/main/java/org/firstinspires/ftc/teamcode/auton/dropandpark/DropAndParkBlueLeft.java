package org.firstinspires.ftc.teamcode.auton.dropandpark;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utilities.InitialVision;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.PlaneLauncher;
import org.firstinspires.ftc.teamcode.utilities.Intake;
import org.firstinspires.ftc.teamcode.utilities.Stick;
import org.firstinspires.ftc.teamcode.utilities.Arm;
import org.firstinspires.ftc.teamcode.utilities.Bay;

@Autonomous(name="BlueLeft-DropPark", group="Linear OpMode")
@Config
public class DropAndParkBlueLeft extends DropAndPark {
    MecanumDrive drivetrain;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Initialized: ", "Hopefully");
        telemetry.update();

        ElapsedTime runtime = new ElapsedTime();
        drivetrain = new MecanumDrive(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        PlaneLauncher plane = new PlaneLauncher(hardwareMap);
        stick = new Stick(hardwareMap);
        Arm arm = new Arm(hardwareMap);
        Bay bay = new Bay(hardwareMap);

        // setting up
        plane.reset();
        stick.lock();
        intake.setUpUp();
        arm.drivingPos();
        bay.setPick();

        InitialVision vis = new InitialVision(hardwareMap, telemetry, "blue");
        waitForStart();
        runtime.reset();
        int pos = vis.getPosition();

        switch (pos) {
            case 1:
                backOne();
                turnCounterClockwise();
                backOneEighth();
                dropPixel();
                forwardOneEighth();
                rightOne();
                backTwo();
                arm.toPickUp();
                break;
            case 3:
                backOne();
                turnClockwise();
                backOneSixteenth();
                dropPixel();
                forwardOneEighth();
                leftOne();
                forwardTwo();
                arm.toPickUp();
                break;
            case 2:
            default:
                backOne();
                dropPixel();
                forwardOne();
                rightTwo();
                arm.toPickUp();
                break;
        }

        telemetry.addData("Run Time:", runtime);
        telemetry.update();
    }
}
