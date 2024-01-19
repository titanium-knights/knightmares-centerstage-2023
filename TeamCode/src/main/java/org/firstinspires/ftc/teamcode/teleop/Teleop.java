package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.teleopconfigs.TeleopConfig;
import org.firstinspires.ftc.teamcode.utilities.Bay;
import org.firstinspires.ftc.teamcode.utilities.Arm;
import org.firstinspires.ftc.teamcode.utilities.Intake;
import org.firstinspires.ftc.teamcode.utilities.SimpleMecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.PlaneLauncher;
import org.firstinspires.ftc.teamcode.utilities.PullUp;

@Config
@TeleOp(name="DriveTrain Teleop")
public class Teleop extends OpMode {

    // we are declaring subsystems here:

    SimpleMecanumDrive drive;
    Bay bay;
    Arm arm;
    PlaneLauncher plane;
    PullUp pullup;
    Intake intake;
    TeleopConfig config;



    //Set normal power constant to 1, no point in slowing the robot down
    final double normalPower = 1;

    // in case of joystick drift, ignore very small values
    final float STICK_MARGIN = 0.5f;

    //Treat this as a multiplier so u could make finer adjustments in slowmode by moving the stick just a little bit
    final double slowPower = 0.3;

    boolean slowMode = false;
    //whether or not a preset is currently running
    boolean state = false;

    //Prevents irreversible things such as pullup and plane launcher from running before this button is pressed
    boolean validate = false;
    //makes validate button have to be pressed for a while before features enabled
    int validatecount = 0;

    // keeps track if bay is open
    boolean open = false;

    // hooked = true means that the pullup is hooked, hooked = false means that pullup is not hooked
    boolean hooked = false;

    boolean armUp = false;

    //runs once, setup function
    public void init() {
        this.drive = new SimpleMecanumDrive(hardwareMap);
        this.bay = new Bay(hardwareMap);
        this.arm = new Arm(hardwareMap);
        this.pullup = new PullUp(hardwareMap);
        this.plane = new PlaneLauncher(hardwareMap);
        this.intake = new Intake(hardwareMap);
        this.config = new TeleopConfig(gamepad1, gamepad2);

        // setting up
        // plane.reset();
        intake.setUpUp();
        arm.drivingPos();
        bay.setPosition(0.92);

        telemetry.setAutoClear(false);
        telemetry.update();

    }

    @Override
    public void loop() {
        config.check();


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
            if (armUp == false) {
                arm.dropPreset(bay);
                telemetry.addData("Arm pos:", arm.getPosition());
                telemetry.update();
                state = true;
                armUp = true;
            } else if (armUp == true) {
                arm.pickPreset(bay);
                telemetry.addData("Arm pos:", arm.getPosition());
                telemetry.update();
                state = true;
                armUp = false;
            }
        }

        if (config.bayClose) { // left bumper
            if (open == true) { // if bay is open, close
                bay.close();
                intake.stop();
                intake.setUp();
                if (arm.getPosition() <= 60) {
                    arm.drivingPos();
                    bay.setPosition(0.92);
                }
                open = false;
            } else if (open == false) { // if bay is closed, open
                bay.open();
                intake.noPower();
                intake.runIntake();
                if (arm.getPosition() <= 60) {
                    arm.toPickUp();
                    bay.setPick();
                }
                open = true;
            }
        }

//
//        // bay
//        if(config.bayClose){//Left Bumper
//            //Close bay
//            bay.close();
//            if (arm.getPosition() <= 60) {
//                arm.drivingPos();
//                bay.setPosition(0.92);
//                intake.stop();
//                intake.setUp();
//            } else {
//                bay.setPosition(0.97);
//            }
//
//        } else if (config.bayOpen){//right bumper
//            bay.open();
//            intake.noPower();
//            intake.runIntake();
//            if (arm.getPosition() <= 60) {
//                arm.toPickUp();
//                bay.setPick();
//            }
//
//        }

//        // pullUp manual
//        if (config.pullupUpManual && validate) { //dpad up
//            pullup.liftUp();
////            pullup.manualRightUp();
////            pullup.manualLeftUp();
//            telemetry.addData("pullup Manual Up", pullup.getPosition()[0]);
//            telemetry.update();
//        } else if (config.pullupDownManual && validate) { //dpad down
//            pullup.manualRightDown();
//            pullup.manualLeftDown();
//            telemetry.addData("pullup Manual Down", pullup.getPosition()[0]);
//            telemetry.update();
//
//        } else if (!pullup.isBusy1()) {
//            pullup.stopRight();
//            pullup.stopLeft();
//        }

        if (config.planeRelease && validate) { //X
            plane.reset();
            telemetry.addData("pos: ", plane.getPosition());
            telemetry.update();
        }

        // pullup preset
        if (config.pullupUpManual && validate) {
            if (hooked == false) {
                pullup.reachUp();
                hooked = true;
            } else if (hooked == true) {
                pullup.liftUp();
                hooked = false;
            }
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
            intake.noPower();
            intake.runIntake();
        }
        if (config.intakeStop) {
            intake.stop();
            intake.setUp();
        }
        if (config.intakeReverse) { // right dpad
            intake.noPower();
            intake.runReverse();
        }
    }


}