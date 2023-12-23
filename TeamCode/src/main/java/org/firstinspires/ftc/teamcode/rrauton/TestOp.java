package org.firstinspires.ftc.teamcode.rrauton;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class TestOp extends LinearOpMode {

        @Override
        public void runOpMode() throws InterruptedException {
            waitForStart();
            while (opModeIsActive()) {
                telemetry.addData("Hello", "World");
                telemetry.update();
            }
        }
}
