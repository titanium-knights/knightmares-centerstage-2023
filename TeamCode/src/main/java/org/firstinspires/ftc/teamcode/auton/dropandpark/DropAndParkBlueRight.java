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

@Autonomous(name="BlueRight-DropPark", group="Linear OpMode")
@Config
public class DropAndParkBlueRight extends DropAndPark {
    MecanumDrive drivetrain;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Initialized: ", "Hopefully");
        telemetry.update();

        InitialVision vision = new InitialVision(hardwareMap, telemetry, "blue");
        ElapsedTime runtime = new ElapsedTime();
        Bay bay = new Bay(hardwareMap);
        PlaneLauncher plane = new PlaneLauncher(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        //drivetrain.move() -y is forward, +x is right, +turn is counterclockwise robot centric
        drivetrain = new MecanumDrive(hardwareMap);

        waitForStart();
        runtime.reset();
        int pos = vision.getPosition();

        switch (pos) {
            case 1:
                backOne();
                rightOneEighth();
                turnCounterClockwise();
                backOneEighth();
                dropPixel();
                forwardOneEighth();
                leftOne();
                backOne();
                rightOneFourth();
                backOne();
                rightHalf();
                backTwo();
                break;
            case 3:
                backOne();
                turnClockwise();
                backOneSixteenth();
                dropPixel();
                forwardOneEighth();
                rightOne();
                forwardTwo();
                leftOne();
                forwardOne();
                break;
            case 2:
            default:
                backOne();
                dropPixel();
                forwardOneEighth();
                turnCounterClockwise();
                forwardOneEighth();
                leftOneHalf();
                backTwo();
                rightOneFourth();
                backOne();
                rightHalf();
                backTwo();
                break;
        }

        telemetry.addData("Run Time:", runtime);
        telemetry.update();
    }
}
