package org.firstinspires.ftc.teamcode.auton.dropandpark;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.auton.AutonMethods;
import org.firstinspires.ftc.teamcode.utilities.InitialVision;

@Autonomous(name="BlueLeft-DropPark", group="Linear OpMode")
@Config
public class DropAndParkBlueLeft extends AutonMethods {

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
        telemetry.addData("Initialized: ", "Hopefully");
        telemetry.update();
        ElapsedTime runtime = new ElapsedTime();

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
