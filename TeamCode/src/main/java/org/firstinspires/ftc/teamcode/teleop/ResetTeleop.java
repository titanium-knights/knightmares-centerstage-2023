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
        float pullupUp = gamepad1.left_trigger;
        float pullupDown = gamepad1.right_trigger;

        if (pullupUp > 0){
            pullup.manualUp();
        }
        else if (pullupDown > 0) {
            pullup.manualDown();
        }
        else{
            pullup.stop();
        }
    }
}
