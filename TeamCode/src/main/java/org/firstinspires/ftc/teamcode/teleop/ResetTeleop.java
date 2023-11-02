package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utilities.Claw;
import org.firstinspires.ftc.teamcode.utilities.Lift;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.PlaneLauncher;
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
    PlaneLauncher plane;

    boolean reset = false;
    boolean toggle = true;
    boolean state = false;

    public void init() {
        this.drive = new MecanumDrive(hardwareMap);
        this.claw = new Claw(hardwareMap);
        this.lift = new Lift(hardwareMap);
        this.pullup = new PullUp(hardwareMap);
        telemetry.setAutoClear(false);
    }

    public void loop() {
        float armUp = gamepad1.right_trigger;
        float armDown = gamepad1.left_trigger;

        if (armUp > 0) {
            lift.toBackBoard();
            state = false;
            telemetry.addData("Up", lift.getPosition());
        } else if (armDown > 0) {
            lift.toRobot();
            state = false;
            telemetry.addData("Down", lift.getPosition());
        } else if (!state){
            lift.stop();
            telemetry.addData("Stop", lift.getPosition());
        }

        //open and close the claw
        if (gamepad1.x) {
            claw.open();
            telemetry.addData("Open claw", claw.getPosition());
        } else if (gamepad1.a) {
            claw.close();
            telemetry.addData("Close claw", claw.getPosition());
        }

        // claw rotate
        if (gamepad1.y) {
            claw.setZero();
            telemetry.addData("Rotate back", claw.getPosition());
        } else if (gamepad1.b) {
            claw.setOne();
            telemetry.addData("Rotate front", claw.getPosition());
        }
    }
}
