package org.firstinspires.ftc.teamcode.auton.mudasirisbored;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class redright extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        PIDFController control = new PIDFController(new PIDCoefficients(0,0,0));

        waitForStart();
        
    }
}
