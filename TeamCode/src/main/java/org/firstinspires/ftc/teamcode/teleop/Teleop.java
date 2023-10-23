package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utilities.Claw;
import org.firstinspires.ftc.teamcode.utilities.Lift;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.PlaneLauncher;
import org.firstinspires.ftc.teamcode.utilities.PullUp;
import org.firstinspires.ftc.teamcode.utilities.Pair;

// Used mainly to create what buttons to press and what it controls
@TeleOp(name="DriveTrain Teleop")
public class Teleop extends OpMode {

    // we are declaring subsystems here:
    MecanumDrive drive; // "MecanumDrive" is the variable type, and drive is the name of the variable
    Claw claw;
    Lift lift;
    PlaneLauncher plane;
    PullUp pullup;


    //Set normal power to 1, no point in slowing the robot down
    final double normalPower = 1; // final means that the variable is a constant

    //Treat this as a multiplier so u could make finer adjustments in slowmode by moving the stick just a little bit
    final double slowPower = 0.3;

    boolean slowMode = false;
    boolean state = false; // if a preset is running, state = true, if a preset is not running, state = false

    public void init() { // this only runs once at the very beginning
        this.drive = new MecanumDrive(hardwareMap);
        this.claw = new Claw(hardwareMap);
        this.lift = new Lift(hardwareMap);
        this.pullup = new PullUp(hardwareMap);
        this.plane = new PlaneLauncher(hardwareMap);
//        telemetry.setAutoClear(false);
    }

    @Override
    public void loop() { // after initialize it keeps running

        //slow mode, turns slowmode on and off
        if (gamepad1.start || gamepad2.start) {slowMode = !slowMode;}

        //driving
        float x = gamepad1.left_stick_x;
        float y = gamepad1.left_stick_y;
        float turn = gamepad1.right_stick_x;

        // in case of joystick drift, ignore very small values
        //TODO: TUNE THIS VALUE
        final float STICK_MARGIN = 0.2f;

        // if the stick movement is negligible, set STICK_MARGIN to 0
        if (Math.abs(x) <= STICK_MARGIN) x = .0f;
        if (Math.abs(y) <= STICK_MARGIN) y = .0f;
        if (Math.abs(turn) <= STICK_MARGIN) turn = .0f;

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
            telemetry.addData("Rotate back", claw.getPosition());
        } else if (gamepad1.dpad_right) {
            claw.setOne();
            telemetry.addData("Rotate front", claw.getPosition());
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
            telemetry.addData("Up", lift.getPosition());
        } else if (armDown > 0) {
            lift.toRobot();
            state = false;
            telemetry.addData("Down", lift.getPosition());
        } else if (!state){
            lift.stop();
            telemetry.addData("Stop", lift.getPosition());
        }


        //You want this to react to gamepad.dpad_(up/down/left/right) as that is where preset control should be

        boolean armUpPreset = gamepad1.b;
        boolean armDownPreset = gamepad1.a;
        //You can combine this with the thing above
        if (armUpPreset) {
            lift.toDrop();
            state = true;
            telemetry.addData("State: True", state);
        } else if (armDownPreset) {
            lift.toPickUp();
            state = true;
            telemetry.addData("State: True", state);
        }

        if (!lift.isBusy()) {
            state = false;
        }

        claw.maintain(lift.getPosition());

        boolean pullUpUpPreset = gamepad1.y;
        boolean pullUpDownPreset = gamepad1.x;


        if (pullUpUpPreset){
            pullup.liftUp();
        }
        else if (pullUpDownPreset) {
            pullup.liftDown();
        }

        if (gamepad1.back) {
            telemetry.update();
        }

        if (gamepad1.dpad_up) {
            plane.release();
        }

    }
}
