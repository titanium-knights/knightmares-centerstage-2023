package org.firstinspires.ftc.teamcode.auton.dropandpark;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utilities.Bay;
import org.firstinspires.ftc.teamcode.utilities.InitialVision;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.PlaneLauncher;
import org.firstinspires.ftc.teamcode.utilities.Intake;
import org.firstinspires.ftc.teamcode.utilities.Stick;

@Autonomous(name="RedRight-DropPark", group="Linear OpMode")
@Config
public class DropAndParkRedRight extends DropAndPark {
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
        plane.reset();
        stick = new Stick(hardwareMap);
        stick.lock();
        intake.setUp();

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
                forwardOneHalf();
                break;
            case 3:
                backOne();
                turnClockwise();
                backOneEighth();
                dropPixel();
                forwardOneEighth();
                leftOne();
                backTwo();
                break;
            case 2:
            default:
                backOne();
                dropPixel();
                forwardOneEighth();
                leftTwo();
                break;
        }

        telemetry.addData("Run Time:", runtime);
        telemetry.update();
    }
}
