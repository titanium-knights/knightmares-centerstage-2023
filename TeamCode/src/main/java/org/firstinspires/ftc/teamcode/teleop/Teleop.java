package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.teleopconfigs.TeleopConfig;
import org.firstinspires.ftc.teamcode.utilities.Claw;
import org.firstinspires.ftc.teamcode.utilities.Arm;
import org.firstinspires.ftc.teamcode.utilities.Intake;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.PlaneLauncher;
import org.firstinspires.ftc.teamcode.utilities.PullUp;

@TeleOp(name="DriveTrain Teleop")
public class Teleop extends OpMode {

    // we are declaring subsystems here:

    MecanumDrive drive;
    Claw claw;
    Arm arm;
    PlaneLauncher plane;
    PullUp pullup;
    Intake intake;
    TeleopConfig config;

    //Set normal power constant to 1, no point in slowing the robot down
    final double normalPower = 1;

    // in case of joystick drift, ignore very small values
    final float STICK_MARGIN = 0.2f;

    //Treat this as a multiplier so u could make finer adjustments in slowmode by moving the stick just a little bit
    final double slowPower = 0.3;

    boolean slowMode = false;
    //whether or not a preset is currently running
    boolean state = false;

    //runs once, setup function
    public void init() {
        this.drive = new MecanumDrive(hardwareMap);
        this.claw = new Claw(hardwareMap);
        this.arm = new Arm(hardwareMap);
        this.pullup = new PullUp(hardwareMap);
        this.plane = new PlaneLauncher(hardwareMap);
        this.intake = new Intake(hardwareMap);
        this.config = new TeleopConfig(gamepad1, gamepad2);

        telemetry.setAutoClear(false);
        telemetry.update();
    }

    @Override
    public void loop() {
        config.check();
        //slow mode toggle
        if (config.slowMode) {slowMode = !slowMode;}

        //DRIVE
        float x = config.x_movement;
        float y = config.y_movement;
        float turn = config.turn;
        move(x, y, turn, slowMode);

        //ARM
        if (config.armUpPreset > STICK_MARGIN) { //Right Trigger
            arm.toDrop();
            //TODO: FIX THIS VALUE
            claw.setPosition(0.6); // CLAW_ANGLE_DROP (180-VERTICAL_ANGLE)
            telemetry.addLine("Arm to drop preset");
            telemetry.update();

            state = true;
        } else if (config.armDownPreset > STICK_MARGIN) { //Left Trigger
            arm.toNeutral();
            claw.setPosition(0); // CLAW_ANGLE_PICKUP (VERTICAL_ANGLE)
            telemetry.addLine("Arm to pickup preset");
            telemetry.update();

            state = true;
        }

        if(config.clawClose){//Left Bumper
            //Lower Arm and Close Claw
            arm.toPickUp();
            while (arm.isBusy());
            claw.close();
        } else if (config.clawOpen){//right bumper
            claw.open();
        }

        if (!arm.isBusy()) {
            state = false;
        }

        // pullUp manual
        if (config.pullupUpManual) { //dpad up
            pullup.manualRightUp();
            pullup.manualLeftUp();
            telemetry.addData("pullup Manual Up", pullup.getPosition()[0]);
            telemetry.update();
        } else if (config.pullupDownManual) { //dpad down
            pullup.manualRightDown();
            pullup.manualLeftDown();
            telemetry.addData("pullup Manual Down", pullup.getPosition()[0]);
            telemetry.update();

        } else if (!pullup.isBusy1()) {
            pullup.stopRight();
        } else if (!pullup.isBusy2()) {
            pullup.stopLeft();
        }

        if (config.planeRelease) { //X
            plane.reset();
            telemetry.addData("pos: ", plane.getPosition());
            telemetry.update();
        }


        intake(); //dpad right = forward, left = reverse, a = stop

    }

    public void move(float x, float y, float turn, boolean slowMode){

        // if the stick movement is negligible, set STICK_MARGIN to 0
        if (Math.abs(x) <= STICK_MARGIN) x = .0f;
        if (Math.abs(y) <= STICK_MARGIN) y = .0f;
        if (Math.abs(turn) <= STICK_MARGIN) turn = .0f;

        //Notation of a ? b : c means if a is true do b, else do c.
        double multiplier = (slowMode ? slowPower : normalPower);
        drive.move(x * multiplier, y * multiplier, -turn * multiplier);
    }

    public void intake(){
        if (config.intakeForward) intake.runIntake();
        if (config.intakeReverse) intake.runReverse();
        if (config.intakeStop) intake.stop();
    }
}