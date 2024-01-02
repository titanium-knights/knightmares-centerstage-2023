package org.firstinspires.ftc.teamcode.auton.cycler;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.auton.AutonMethods;
import org.firstinspires.ftc.teamcode.utilities.InitialVision;

@Autonomous(name="BlueRight-DropScorePark", group="Linear OpMode")
@Config
public class BlueRight extends AutonMethods {

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
        telemetry.addData("Initialized: ", "Hopefully");
        telemetry.update();
        ElapsedTime runtime = new ElapsedTime();

        // setting up
        plane.reset();
        stick.lock();
        intake.setUp();
        arm.drivingPos();
        bay.setPick();

        InitialVision vis = new InitialVision(hardwareMap, telemetry, "blue");
        waitForStart();
        runtime.reset();
        int pos = vis.getPosition();

        switch (pos) {
            case 1:
                caseOne();
                leftOne();
                backThree();
                rightOneHalf();
                backHalf();
                paintPixel();
                returnInit();
                break;
            case 3:
                caseThree();
                rightOne();
                forwardThree();
                turnClockwise();
                turnClockwise();
                rightHalf();
                rightOneFourth();
                backHalf();
                paintPixel();
                returnInit();
                break;
            case 2:
            default:
                caseTwo();
                leftOne();
                backOne();
                turnCounterClockwise();
                backFour();
                rightOneHalf();
                backHalf();
                paintPixel();
                returnInit();
                break;
        }

        telemetry.addData("Run Time:", runtime);
        telemetry.update();
    }
}
