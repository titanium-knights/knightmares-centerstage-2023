package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utilities.TwoPieceVision;

@TeleOp(name = "Ppipeline testesr", group = "Testers")
public class PipelineTester extends LinearOpMode {

//    @Override
//    public void init() {}
    @Override
    public void runOpMode() {
        TwoPieceVision vis = new TwoPieceVision(hardwareMap, telemetry, "red");
        telemetry.setAutoClear(false);

        while (!isStopRequested()) {
            telemetry.addLine(vis.getPosition()+"");
            telemetry.update();
            sleep(100);
        }
    }
}
