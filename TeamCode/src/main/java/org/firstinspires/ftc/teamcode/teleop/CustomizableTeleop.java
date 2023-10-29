package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.teleopconfigs.TeleopConfig;
import org.firstinspires.ftc.teamcode.utilities.Claw;
import org.firstinspires.ftc.teamcode.utilities.Lift;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.PlaneLauncher;
import org.firstinspires.ftc.teamcode.utilities.PullUp;

// Used mainly to create what buttons to press and what it controls
@TeleOp(name="DriveTrain Teleop Customizable")
public class CustomizableTeleop extends OpMode {

    // declare subsystems here
    MecanumDrive drive;
    Claw claw;
    Lift lift;
    PlaneLauncher plane;
    PullUp pullup;
    TeleopConfig config;

    //Set normal power to 1, no point in slowing the robot down
    final double normalPower = 1;

    //Treat this as a multiplier so u could make finer adjustments in slowmode by moving the stick just a little bit
    final double slowPower = 0.3;

    boolean slowMode = false;
    //whether or not a preset is currently running
    boolean state = false;

    public void init() {
        this.drive = new MecanumDrive(hardwareMap);
        this.claw = new Claw(hardwareMap);
        this.lift = new Lift(hardwareMap);
        this.pullup = new PullUp(hardwareMap);
        this.plane = new PlaneLauncher(hardwareMap);
        this.config = new TeleopConfig(gamepad1, gamepad2);
    }

    @Override
    public void loop() { // after initialize it keeps running

        //slow mode, turns slowmode on and off
        if (config.slowMode) {slowMode = !slowMode;}

        //driving
        float x = config.x_movement;
        float y = config.y_movement;
        float turn = config.turn;

        // in case of joystick drift, ignore very small values
        final float STICK_MARGIN = 0.2f;

        // if the stick movement is negligible, set STICK_MARGIN to 0
        if (Math.abs(x) <= STICK_MARGIN) x = .0f;
        if (Math.abs(y) <= STICK_MARGIN) y = .0f;
        if (Math.abs(turn) <= STICK_MARGIN) turn = .0f;

        //Notation of a ? b : c means if a is true do b, else do c.
        double multiplier = (slowMode ? slowPower : normalPower);
        drive.move(x * multiplier, y * multiplier, turn * multiplier);

        //open and close the claw
        if (config.clawOpen) {
            claw.open();
        } else if (config.clawClose) {
            claw.close();
        }

        // claw rotate
        if (config.clawZero) {
            claw.setZero();
            telemetry.addData("Rotate back", claw.getPosition());
        } else if (config.clawOne) {
            claw.setOne();
            telemetry.addData("Rotate front", claw.getPosition());
        }
        /*
         * lift
         */

//        float liftPower = gamepad1.right_stick_y;
//        if (Math.abs(liftPower) <= STICK_MARGIN) liftPower = 0;
//        lift.setPower(liftPower>0, slowMode); //up is towards drop off

//        boolean liftUpPreset = gamepad2.dpad_up;
//        boolean liftDownPreset = gamepad2.dpad_down;

        float armUp = config.armUp;
        float armDown = config.armDown;

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

        if (config.armUpPreset) {
            lift.toDrop();
            state = true;
            telemetry.addData("State: True", state);
        } else if (config.armDownPreset) {
            lift.toPickUp();
            state = true;
            telemetry.addData("State: True", state);
        }

        if (!lift.isBusy()) {
            state = false;
        }

        claw.maintain(lift.getPosition());

        if (config.pullupUp){
            pullup.liftUp();
        } else if (config.pullupDown) {
            pullup.liftDown();
        }

        if (config.updateTelemetry) {
            telemetry.update();
        }

        if (config.planeLaunch) {
            plane.release();
        }

    }
}
