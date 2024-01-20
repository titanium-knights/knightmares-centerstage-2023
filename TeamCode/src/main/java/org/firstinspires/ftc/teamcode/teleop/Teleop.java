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


    //Prevents irreversible things such as pullup and plane launcher from running before this button is pressed
    boolean validate = false;

    //makes validate button have to be pressed for a while before features enabled
    int validatecount = 0;

    public enum LiftState {
        INTAKE_RUNNING,
        LIFT_DRIVING_POS,
        LIFT_TO_DROP,
        BAY_TO_DROP,
        LIFT_DROPPING,
        BAY_RETRACTED,
        LIFT_TO_PICKUP
    };
    LiftState liftState = LiftState.LIFT_DRIVING_POS;

    public enum PullUpState {
        NEUTRAL,
        REACH_UP,
        PULL_UP
    }
    PullUpState pullupstate = PullUpState.NEUTRAL;

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
        intake.setZero(); // TODO: may need to tune
        arm.drivingPos();
        bay.setPosition(0.92);

        telemetry.setAutoClear(false);
        telemetry.update();

    }

    @Override
    public void loop() {
        config.check();

        //VALIDATE
        if (config.validate) {++validatecount;}
        if (validatecount > 5) {validate = true;}

        //DRIVE
        float x = config.x_movement;
        float y = config.y_movement;
        float turn = config.turn;
        move(-x, y, turn);

        //ARM
        switch (liftState) {
            case INTAKE_RUNNING:
                if (Math.abs(arm.getPosition() - 0) < 2 && config.intake) {
                    bay.close();
                    bay.setPosition(0.92);
                    intake.stop();
                    arm.drivingPos();
                    liftState = LiftState.LIFT_DRIVING_POS;
                }
                if (config.lift) {
                    bay.close();
                    bay.setPosition(0.92);
                    intake.stop();
                    arm.toDrop();
                    liftState = LiftState.LIFT_TO_DROP;
                }
                break;
            case LIFT_DRIVING_POS:
                if (config.lift) {
                    arm.toDrop();
                    liftState = LiftState.LIFT_TO_DROP;
                }
                if (Math.abs(arm.getPosition() - 10) < 2 && config.intake) {
                    bay.open();
                    bay.setPick();
                    intake.runIntake();
                    arm.toPickUp();
                    liftState = LiftState.INTAKE_RUNNING;
                }
                break;
            case LIFT_TO_DROP:
                if (Math.abs(arm.getPosition() - 135) < 10) {
                    bay.setDrop();
                    liftState = LiftState.BAY_TO_DROP;
                }
                break;
            case BAY_TO_DROP:
                if (config.bay) {
                    bay.open();
                    liftState = liftState.LIFT_DROPPING;
                }
                break;
            case LIFT_DROPPING:
                if (config.lift) {
                    bay.close();
                    bay.setPosition(0.97);
                    arm.runToPosition(100);
                    liftState = LiftState.LIFT_TO_PICKUP;
                }
                break;
            case LIFT_TO_PICKUP:
                if (Math.abs(arm.getPosition() - 90) < 10) {
                    arm.drivingPos();
                    liftState = LiftState.LIFT_DRIVING_POS;
                }
                break;
            default:
                liftState = LiftState.LIFT_DRIVING_POS;
        }

        //PLANE LAUNCHER
        if (config.planeRelease && validate) {
            plane.reset();
            telemetry.addData("pos: ", plane.getPosition());
            telemetry.update();
        }

        //PULL UP
        switch (pullupstate) {
            case NEUTRAL:
                if (config.pullup && validate) {
                    pullup.manualLeftUp();
                    pullup.manualRightUp();
                    pullupstate = PullUpState.REACH_UP;
                }
                break;
            case REACH_UP:
                if (config.pullup) {
                    pullup.manualLeftDown();
                    pullup.manualRightDown();
                    intake.setUpUp();
                    pullupstate = PullUpState.NEUTRAL;
                }
                break;
            default:
                pullupstate = PullUpState.NEUTRAL;
        }

    }

    public void move(float x, float y, float turn) {
        // if the stick movement is negligible, set STICK_MARGIN to 0
        if (Math.abs(x) <= STICK_MARGIN) x = .0f;
        if (Math.abs(y) <= STICK_MARGIN) y = .0f;
        if (Math.abs(turn) <= STICK_MARGIN) turn = .0f;

        //Notation of a ? b : c means if a is true do b, else do c.
        double multiplier = normalPower;
        drive.move(x * multiplier, y * multiplier, -turn * multiplier);

    }


}








// OLD CODE


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

//        if (config.validate) { //Right Trigger
//            if (armUp == false) {
//                intake.noPower();
//                arm.dropPreset(bay);
//                telemetry.addData("Arm pos:", arm.getPosition());
//                telemetry.update();
//                state = true;
//                armUp = true;
//            } else if (armUp == true) {
//                arm.pickPreset(bay);
//                telemetry.addData("Arm pos:", arm.getPosition());
//                telemetry.update();
//                state = true;
//                armUp = false;
//            }
//        }

//        if (config.intakeStop) { // left bumper
//            if (open == true) { // if bay is open, close
//                bay.close();
//                intake.stop();
//                intake.setUp();
//                if (arm.getPosition() <= 60) {
//                    arm.drivingPos();
//                    bay.setPosition(0.92);
//                }
//                open = false;
//            } else if (open == false) { // if bay is closed, open
//                bay.open();
//                intake.noPower();
//                intake.runIntake();
//                if (arm.getPosition() <= 60) {
//                    arm.toPickUp();
//                    bay.setPick();
//                }
//                open = true;
//            }
//        }

//       if (config.pullupUpManual && validate) {
//            if (hooked == false) {
//                pullup.reachUp();
//                hooked = true;
//            } else if (hooked == true) {
//                pullup.liftUp();
//                hooked = false;
//            }
//        }