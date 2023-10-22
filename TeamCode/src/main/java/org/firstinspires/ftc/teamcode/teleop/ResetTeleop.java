package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utilities.Claw;
import org.firstinspires.ftc.teamcode.utilities.Lift;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.PullUp;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name="Calibration/Reset Teleop")
public class ResetTeleop extends OpMode {

    final float STICK_MARGIN = 0.2f;

    MecanumDrive drive;
    Claw claw;
    Lift lift;

    PullUp pullup;

    boolean reset = false;
    boolean toggle = true;

    public void init() {
        this.drive = new MecanumDrive(hardwareMap);
        this.claw = new Claw(hardwareMap);
        this.lift = new Lift(hardwareMap);
        this.pullup = new PullUp(hardwareMap);
        telemetry.setAutoClear(false);

    }

    public void loop(){

        float x = gamepad1.left_stick_x;
        float y = gamepad1.left_stick_y;
        float turn = gamepad1.right_stick_x;

        telemetry.addLine("x="+x+"y="+y+"turn="+turn);

        if (Math.abs(x) <= STICK_MARGIN) x = .0f;
        if (Math.abs(y) <= STICK_MARGIN) y = .0f;
        if (Math.abs(turn) <= STICK_MARGIN) turn = .0f;

        drive.move(x, y, turn);

        if (gamepad1.dpad_up){
            lift.toBackBoard();
            telemetry.addData("Lift", "Backboard");
        }
        if (gamepad1.dpad_down){
            lift.toRobot();
            telemetry.addData("Lift", "Robot");
        }

        if (gamepad1.dpad_right) claw.setOne();
        if (gamepad1.dpad_left) claw.setZero();

        if (gamepad1.a) toggle = !toggle;
        if (toggle) claw.open();
        else claw.close();

        boolean leftUp = gamepad1.left_trigger > 0;
        boolean leftDown = gamepad1.right_trigger > 0;
        boolean rightUp = gamepad1.left_bumper;
        boolean rightDown = gamepad1.right_bumper;

        if (leftUp){
            pullup.manualLeftUp();
            telemetry.addData("Left", "Up");
        }
        else if (leftDown) {
            pullup.manualLeftDown();
            telemetry.addData("Left", "Down");
        }
        else{
            pullup.stopLeft();
        }

        if (rightUp){
            pullup.manualRightUp();
            telemetry.addData("Right", "Up");
        }
        else if (rightDown) {
            pullup.manualRightDown();
            telemetry.addData("Right", "Down");
        }
        else{
            pullup.stopRight();
        }

        if (gamepad1.b && reset){
            reset = false;
            telemetry.update();
        } else if (gamepad1.b){
            reset = true;
        }
    }
}
