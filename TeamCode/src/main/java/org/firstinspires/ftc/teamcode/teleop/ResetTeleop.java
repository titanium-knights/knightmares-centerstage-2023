package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utilities.Bay;
import org.firstinspires.ftc.teamcode.utilities.Arm;
import org.firstinspires.ftc.teamcode.utilities.Intake;
import org.firstinspires.ftc.teamcode.utilities.SimpleMecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.PlaneLauncher;
import org.firstinspires.ftc.teamcode.utilities.PullUp;

@TeleOp(name="Calibration/Reset Teleop")
public class ResetTeleop extends OpMode {

    final float STICK_MARGIN = 0.2f;

    SimpleMecanumDrive drive;
    Bay bay;
    Arm lift;
    PullUp pullup;
    PlaneLauncher plane;
    Intake intake;
    boolean state = false;

    public void init() {
        this.drive = new SimpleMecanumDrive(hardwareMap);
        this.bay = new Bay(hardwareMap);
        this.lift = new Arm(hardwareMap);
        this.pullup = new PullUp(hardwareMap);
        this.plane = new PlaneLauncher(hardwareMap);
        this.intake = new Intake(hardwareMap);
    }

    public void loop() {
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
        }

//         bay rotate
        if (gamepad1.y) {
            bay.setZero();
            telemetry.addData("Rotate 0", bay.getPosition());

        } else if (gamepad1.b) {
            bay.setOne();
            telemetry.addData("Rotate 1", bay.getPosition());                                                  ;
        }

        if (gamepad1.left_bumper){
            pullup.reachUp();
        } else if (gamepad1.right_bumper){
            pullup.liftUp();
        }


        //LEFT
        if (gamepad1.dpad_left){
            pullup.manualLeftUp();
        } else if (gamepad1.dpad_down){
            pullup.manualLeftDown();
        } else if (!pullup.isBusy1()){
            pullup.stopLeft();
        }

        //RIGHT
        if (gamepad1.dpad_up){
            pullup.manualRightUp();
        } else if (gamepad1.dpad_right){
            pullup.manualRightDown();
        } else if (!pullup.isBusy2()){
            pullup.stopRight();
        }

//        if (gamepad1.b){
//            telemetry.clear();
//            telemetry.addData("positionLeft", pullup.getPosition()[0]);
//            telemetry.addData("positionRight", pullup.getPosition()[1]);
//            telemetry.addData("BusyLeft", pullup.isBusy1());
//            telemetry.addData("BusyRight", pullup.isBusy2());
//            telemetry.update();
//        }

        if (gamepad1.x){
            plane.set();
        }
        if(gamepad1.y){
            plane.reset();
        }

        if (gamepad1.a) intake.setZero();
        else if (gamepad1.b) intake.setUp();
    }
}
