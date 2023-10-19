package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utilities.Claw;
import org.firstinspires.ftc.teamcode.utilities.Lift;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.PullUp;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp

public class ResetTeleop extends OpMode {
    PullUp pullup;
    // TODO: manual up and manual down power 0.3

    public void init() {
        this.pullup = new PullUp(hardwareMap);
    }

    public void loop(){
//        float leftPullup = gamepad1.left_stick_y;
//        float rightPullup = gamepad1.right_stick_y;
//
//        if (leftPullup > 0){
//            pullup.manualUp();
//        }
//        else if (leftPullup < 0) {
//            pullup.manualDown();
//        }
//        else if (rightPullup > 0) {
//            pullup.manualUp();
//        }
//        else if (rightPullup <0) {
//            pullup.manualDown();
//        }
//        else{
//            pullup.stop();
//        }
    }
}
