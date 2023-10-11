package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utilities.Claw;
import org.firstinspires.ftc.teamcode.utilities.Lift;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;

@TeleOp
public class Teleop extends OpMode {

    Telemetry telemetry;
    MecanumDrive drive;
    Claw claw;
    Lift lift;
    double power = 0.5;

    public void init() {
        this.drive = new MecanumDrive(hardwareMap);
        this.claw = new Claw(hardwareMap);
        this.lift = new Lift(hardwareMap);
    }

    @Override
    public void loop() {

        // in case of joystick drift, ignore very small values
        final float STICK_MARGIN = 0.2f;

        /*
         * driving
         */
        float x = gamepad1.left_stick_x;
        float y = gamepad1.left_stick_y;
        float turn = gamepad1.right_stick_x;

        // if the stick movement is negligible, set it to 0
        if (Math.abs(x) <= STICK_MARGIN) x = 0;
        if (Math.abs(y) <= STICK_MARGIN) y = 0;
        if (Math.abs(turn) <= STICK_MARGIN) turn = 0;

        drive.move(x, y, turn);

        /*
         * claw
         */

        //open and close the claw
        if (gamepad1.a) {
            claw.open();
        } else if (gamepad1.b) {
            claw.close();
        }

    }
}
