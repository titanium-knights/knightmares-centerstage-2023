package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utilities.Claw;
import org.firstinspires.ftc.teamcode.utilities.Lift;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.PullUp;

@TeleOp
public class Teleop extends OpMode {

    Telemetry telemetry;
    MecanumDrive drive;
    Claw claw;
    Lift lift;
    PullUp pullup;
    //Set normal power to 1, no point in slowing the robot down
    final double normalPower = 1;
    //Treat this as a multiplier so u could make finer adjustments in slowmode by moving the stick just a little bit
    final double slowPower = 0.3;

    boolean slowMode = false;
    boolean state = false;

    public void init() {
        this.drive = new MecanumDrive(hardwareMap);
        this.claw = new Claw(hardwareMap);
        this.lift = new Lift(hardwareMap);
        this.pullup = new PullUp(hardwareMap);
    }

    @Override
    public void loop() {
        // in case of joystick drift, ignore very small values
        //TODO: TUNE THIS VALUE
        final float STICK_MARGIN = 0.2f;

        //slow mode
        if (gamepad1.start || gamepad2.start) {slowMode = !slowMode;}

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

        // claw rotate
        if (gamepad1.dpad_left) {
            claw.setZero(); // TODO: check if this is actually rotating back
        } else if (gamepad1.dpad_right) {
            claw.setOne();
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
        float armDown = gamepad1.right_trigger;

        if (armUp > 0) {
            lift.toBackBoard();
            state = false;
        } else if (armDown > 0) {
            lift.toRobot();
            state = false;
        } else if (!state){
            lift.stop();
        }

        //You want this to react to gamepad.dpad_(up/down/left/right) as that is where preset control should be

        boolean armUpPreset = gamepad1.b;
        boolean armDownPreset = gamepad1.a;
        //You can combine this with the thing above
        if (armUpPreset) {
            lift.toDrop();
            state = true;
        } else if (armDownPreset) {
            lift.toPickUp();
            state = true;
        }

        if (!lift.isBusy()) state = false;

        claw.maintain(lift.getPosition());

        boolean pullUpUpPreset = gamepad1.y;
        boolean pullUpDownPreset = gamepad1.x;

        if (pullUpUpPreset){
            pullup.liftUp();
        }
        else if (pullUpDownPreset) {
            pullup.liftDown();
        }
    }
}
