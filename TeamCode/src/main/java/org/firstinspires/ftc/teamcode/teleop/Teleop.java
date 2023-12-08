package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.teleopconfigs.TeleopConfig;
import org.firstinspires.ftc.teamcode.utilities.Bay;
import org.firstinspires.ftc.teamcode.utilities.Arm;
import org.firstinspires.ftc.teamcode.utilities.Intake;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.PlaneLauncher;
import org.firstinspires.ftc.teamcode.utilities.PullUp;

@TeleOp(name="DriveTrain Teleop")
public class Teleop extends OpMode {

    // we are declaring subsystems here:

    MecanumDrive drive;
    Bay bay;
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

    //Prevents irreversible things such as pullup and plane launcher from running before this button is pressed
    boolean validate = false;
    //makes validate button have to be pressed for a while before features enabled
    int validatecount = 0;

    //runs once, setup function
    public void init() {
        this.drive = new MecanumDrive(hardwareMap);
        this.bay = new Bay(hardwareMap);
        this.arm = new Arm(hardwareMap);
        this.pullup = new PullUp(hardwareMap);
        this.plane = new PlaneLauncher(hardwareMap);
        this.intake = new Intake(hardwareMap);
        this.config = new TeleopConfig(gamepad1, gamepad2);
        plane.reset();

        telemetry.setAutoClear(false);
        telemetry.update();
        bay.setPick();
    }

    @Override
    public void loop() {
        config.check();
        bay.maintain(arm);

        if (config.validate) {++validatecount;}
        if (validatecount > 5) {validate = true;}
        //slow mode toggle
        if (config.slowMode) {slowMode = !slowMode;}

        //DRIVE
        float x = config.x_movement;
        float y = config.y_movement;
        float turn = config.turn;
        move(-x, y, turn, slowMode);

        //ARM
        if (config.armUpPreset > STICK_MARGIN) { //Right Trigger
            arm.toDrop();
            bay.setDrop(); // bay_ANGLE_DROP (180-VERTICAL_ANGLE)
            telemetry.addLine("Arm to drop preset");
            telemetry.update();

            state = true;
        } else if (config.armDownPreset > STICK_MARGIN) { //Left Trigger
            arm.toNeutral();
            bay.setPick(); // bay_ANGLE_PICKUP (VERTICAL_ANGLE)
            telemetry.addLine("Arm to pickup preset");
            telemetry.update();

            state = true;
        }

        // bay
        if(config.bayClose){//Left Bumper
            //Lower Arm and Close bay
            arm.toPickUp();
            while (arm.isBusy()); // wait
            bay.close();
        } else if (config.bayOpen){//right bumper
            bay.open();
        }

//        if (!arm.isBusy()) {
//            state = false;
//            telemetry.addData("stopped", arm.getPosition());
//            telemetry.update();
//        }

        // pullUp manual
        if (config.pullupUpManual && validate) { //dpad up
            pullup.manualRightUp();
            pullup.manualLeftUp();
            telemetry.addData("pullup Manual Up", pullup.getPosition()[0]);
            telemetry.update();
        } else if (config.pullupDownManual && validate) { //dpad down
            pullup.manualRightDown();
            pullup.manualLeftDown();
            telemetry.addData("pullup Manual Down", pullup.getPosition()[0]);
            telemetry.update();

        } else if (!pullup.isBusy1()) {
            pullup.stopRight();
            pullup.stopLeft();
        }

        if (config.planeRelease && validate) { //X
            plane.set();
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
        if (config.intakeForward) {
            intake.setZero();
            intake.runIntake();
        }
        if (config.intakeReverse) {
            intake.setZero();
            intake.runReverse();
        }
        if (config.intakeStop) {
            intake.setUp();
            intake.stop();
        }
    }


}