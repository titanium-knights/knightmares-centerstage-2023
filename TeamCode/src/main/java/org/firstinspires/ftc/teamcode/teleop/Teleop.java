package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.teleopconfigs.TeleopConfig;
import org.firstinspires.ftc.teamcode.utilities.Claw;
import org.firstinspires.ftc.teamcode.utilities.Lift;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.PlaneLauncher;
import org.firstinspires.ftc.teamcode.utilities.PullUp;
import org.firstinspires.ftc.teamcode.utilities.Pair;

@TeleOp(name="DriveTrain Teleop")
public class Teleop extends OpMode {

    // we are declaring subsystems here:

    MecanumDrive drive;
    Claw claw;
    Lift lift;
    PlaneLauncher plane;
    PullUp pullup;
    TeleopConfig config;

    //Set normal power constant to 1, no point in slowing the robot down
    final double normalPower = 1;

    //Treat this as a multiplier so u could make finer adjustments in slowmode by moving the stick just a little bit
    final double slowPower = 0.3;

    boolean slowMode = false;
    //whether or not a preset is currently running
    boolean state = false;

    //runs once, setup function
    public void init() {
        this.drive = new MecanumDrive(hardwareMap);
        this.claw = new Claw(hardwareMap);
        this.lift = new Lift(hardwareMap);
        this.pullup = new PullUp(hardwareMap);
        this.plane = new PlaneLauncher(hardwareMap);
        this.config = new TeleopConfig(gamepad1, gamepad2);
    }

    @Override
    public void loop() {
        config.check();
        //slow mode toggle
        if (config.slowMode) {slowMode = !slowMode;}


        telemetry.setAutoClear(false);
        telemetry.update();
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
        drive.move(x * multiplier, y * multiplier, -turn * multiplier);

        // open and close the claw
        if (config.clawOpen) { //left bumper
            claw.open();
        } else if (config.clawClose) { //right bumper
            claw.close();
        }

        // rotate the claw
//        if (config.clawRotatorScore) {
//            claw.setDrop();
//            telemetry.addData("Rotate back", claw.getPosition());
//        } else if (config.clawRotatorPick) {
//            claw.setPick();
//            telemetry.addData("Rotate front", claw.getPosition())                                                  ;
//        }

        if (config.clawRotatorScore) {
            lift.toDrop();
            telemetry.addData("lift to drop position", lift.getPosition());
            claw.setDrop(); //random value;
            telemetry.addData("claw to drop position", claw.getPosition());
        }

        // pullUp manual
        if (config.pullupUpManual) { //dpad left
            pullup.manualRightUp();

            telemetry.addData("pullup Manual Up", pullup.getPosition()[0]);
            telemetry.update();
            // pullup.manualLeftUp();
        } else if (config.pullupDownManual) {
            pullup.manualRightDown();
            telemetry.addData("pullup Manual Down", pullup.getPosition()[0]);
            telemetry.update();
            // pullup.manualLeftDown();
        }
        else {
            pullup.stopRight();
            // pullup.stopLeft();
        }

        // pullup preset
        if (config.pullupUp){ //y
            pullup.liftUp();
            telemetry.addData("pullup Up", pullup.getPosition()[0]);
            telemetry.update();
        }
        else if (config.pullupDown) { //x
            pullup.liftDown();
            telemetry.addData("pullup down", pullup.getPosition());
            telemetry.update();
        }

        if (pullup.isBusy1()) pullup.stopLeft();
        if (pullup.isBusy2()) pullup.stopRight();

        float armUp = config.armUp; //left-trigger
        float armDown = config.armDown; //right-trigger

        if (armUp > 0.0) {
            lift.toBackBoard();
            state = false;
            telemetry.addData("Up", lift.getPosition());
            telemetry.update();
        } else if (armDown > 0.0) {
            lift.toRobot();
            state = false;
            telemetry.addData("Down", lift.getPosition());
            telemetry.update();
        } else if (!state){
            lift.stop();
            //telemetry.addData("Stop", lift.getPosition());
            telemetry.update();
        }

        if (config.armUpPreset) { //b
            lift.toDrop();
            state = true;
            telemetry.addLine("State: True");
            claw.setPosition(58); // CLAW_ANGLE_DROP (180-VERTICAL_ANGLE)
            telemetry.update();
        } else if (config.armDownPreset) { //a
            lift.toPickUp();
            state = true;
            telemetry.addLine("State: True");
            claw.setPosition(122); // CLAW_ANGLE_PICKUP (VERTICAL_ANGLE)
            telemetry.update();
        }

        if (!lift.isBusy()) {
            state = false;
        }

        if (config.planeLaunch) { //dpad up
            plane.set();
            // plane.reset();
            telemetry.addData("pos: ", plane.getPosition());
            telemetry.update();
        }

        if (config.planeRelease) { //dpad down
            plane.reset();
            telemetry.addData("pos: ", plane.getPosition());
            telemetry.update();

        }

    }
}