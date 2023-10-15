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
    //Set normal power to 1, no point in slowing the robot down
    final double normalPower = 1;
    //Treat this as a multiplier so u could make finer adjustments in slowmode by moving the stick just a little bit
    final double slowPower = 0.3;

    boolean slowMode = false;

    public void init() {
        this.drive = new MecanumDrive(hardwareMap);
        this.claw = new Claw(hardwareMap);
        this.lift = new Lift(hardwareMap);
    }

    @Override
    public void loop() {

        // in case of joystick drift, ignore very small values
        //TODO: TUNE THIS VALUE
        final float STICK_MARGIN = 0.2f;

        //slow mode
        if (gamepad1.y || gamepad2.y) {slowMode = !slowMode;}

        //driving
        float x = gamepad1.left_stick_x;
        float y = gamepad1.left_stick_y;
        float turn = gamepad1.right_stick_x;

        // if the stick movement is negligible, set STICK_MARGIN to 0
        if (Math.abs(x) <= STICK_MARGIN) x = 0;
        if (Math.abs(y) <= STICK_MARGIN) y = 0;
        if (Math.abs(turn) <= STICK_MARGIN) turn = 0;

        //Notation of a ? b : c means if a is true do b, if it isn't then do c.
        double multiplier = (slowMode ? slowPower : normalPower);
        drive.move(x * multiplier, y * multiplier, turn * multiplier);

        //claw

        //open and close the claw
        if (gamepad1.left_bumper || gamepad2.left_bumper) {
            claw.open();
        } else if (gamepad1.right_bumper || gamepad2.right_bumper) {
            claw.close();
        }

//        //rotate the claw
//        float rotation = gamepad2.left_stick_y;
//        // if the rotation is negligible, set it to 0
//        if (!(Math.abs(rotation) <= STICK_MARGIN))
//            claw.setPosition(claw.getPosition() + (rotation * 10));

        /*
         * lift
         */

//        float liftPower = gamepad1.right_stick_y;
//        if (Math.abs(liftPower) <= STICK_MARGIN) liftPower = 0;
//        lift.setPower(liftPower>0, slowMode); //up is towards drop off

//        boolean liftUpPreset = gamepad2.dpad_up;
//        boolean liftDownPreset = gamepad2.dpad_down;

        float armUp = gamepad1.left_trigger;
        boolean liftUpPreset = false;
        float armDown = gamepad1.right_trigger;
        boolean liftDownPreset = false;

        //ToDO: Add a manual section that acts on armUp and armDown using .toBackBoard and .toRobot

        //You want this to react to gamepad.dpad_(up/down/left/right) as that is where preset control should be
        if (armUp > 0) {
            liftUpPreset = true;
        } else if (armDown > 0) {
            liftDownPreset = true;
        }

        //You can combine this with the thing above
        if (liftUpPreset) {
            lift.toDrop();
            // Can just do claw.maintain(lift.getPosition()) as it does this check for you
            //claw.maintainDrop(lift.getPosition());
        } else if (liftDownPreset) {
            lift.toPickUp();
            //Same as above
            //claw.maintainPickup(lift.getPosition());
        }

        claw.maintain(lift.getPosition());
    }
}
