package org.firstinspires.ftc.teamcode.auton.dropandpark;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utilities.Arm;
import org.firstinspires.ftc.teamcode.utilities.Bay;
import org.firstinspires.ftc.teamcode.utilities.InitialVision;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.PlaneLauncher;
import org.firstinspires.ftc.teamcode.utilities.Intake;
import org.firstinspires.ftc.teamcode.utilities.Stick;

@Autonomous(name="RedLeft-DropPark", group="Linear OpMode")
@Config
public class DropAndParkRedLeft extends DropAndPark {
    MecanumDrive drivetrain;
    Stick stick;

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
                leftOne();
                forwardTwo();
                rightOne();
                forwardOne();
                arm.toPickUp();
                break;
            case 3:
                backOne();
                turnClockwise();
                backOneEighth();
                dropPixel();
                forwardOneEighth();
                rightOne();
                backFour();
                arm.toPickUp();
                break;
            case 2:
            default:
                backOne();
                dropPixel();
                forwardOne();
                rightHalf();
                backTwo();
                turnCounterClockwise();
                forwardTwo();
                rightOne();
                forwardTwo();
                arm.toPickUp();
                break;
        }

        telemetry.addData("Run Time:", runtime);
        telemetry.update();
    }
}
