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
//        Thread configRunner = new Thread(config);
//        configRunner.start();
    }

    @Override
    public void loop() {
        config.check();
        //slow mode toggle
        if (config.slowMode) {slowMode = !slowMode;}


        telemetry.setAutoClear(false);
        //telemetry.addLine("hello world");
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
        drive.move(x * multiplier, y * multiplier, turn * multiplier);

        //open and close the claw
        if (config.clawOpen) {
            claw.open();
        } else if (config.clawClose) {
            claw.close();
        }

        // claw rotate
        if (config.clawZero) {
            pullup.manualRightUp();
//            claw.setZero();
//            telemetry.addData("Rotate back", claw.getPosition());

            telemetry.update();
        } else if (config.clawOne) {
            pullup.manualLeftUp();
//            claw.setOne();
//            telemetry.addData("Rotate front", claw.getPosition());
//            telemetry.update();
        }

        float armUp = config.armUp;
        float armDown = config.armDown;

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

        if (config.armUpPreset) {
            lift.toDrop();
            state = true;
            telemetry.addLine("State: True");
            telemetry.update();
        } else if (config.armDownPreset) {
            lift.toPickUp();
            state = true;
            telemetry.addLine("State: True");
            telemetry.update();
        }


        if (config.planeLaunch) {
            plane.release();
        }

    }
}