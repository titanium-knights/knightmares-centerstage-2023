package org.firstinspires.ftc.teamcode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utilities.InitialVision;

@Autonomous(name="BirdParasiteAuton", group="Linear OpMode")
public class BirdParasiteAuton extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        InitialVision vision = new InitialVision(hardwareMap, telemetry, "red");

        telemetry.setAutoClear(false);
        telemetry.addLine("detected: "+vision.getPosition());
        telemetry.update();
    }


}
