package org.firstinspires.ftc.teamcode.auton.dropandpark;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.utilities.InitialVision;

@Autonomous(name="RedLeft-DropPark", group="Linear OpMode")
@Config
public class DropAndParkRedLeft extends DropAndPark {

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
        //*/
        telemetry.addData("Run Time:", runtime);
        telemetry.update();
    }
}
