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

    public void init() {
        this.drive = new MecanumDrive(hardwareMap);
        this.claw = new Claw(hardwareMap);
        this.lift = new Lift(hardwareMap);
        this.pullup = new PullUp(hardwareMap);
        telemetry.setAutoClear(false);

    }

    public void loop() {
        float armUp = gamepad1.left_trigger;
        float armDown = gamepad1.right_trigger;

        if (armUp > 0) {
            lift.toBackBoard();
            telemetry.addData("Up", lift.getPosition());
        } else if (armDown > 0) {
            lift.toRobot();
            telemetry.addData("Down", lift.getPosition());
        } else
            lift.stop();
            telemetry.addData("Stop", lift.getPosition());
        }
    }
