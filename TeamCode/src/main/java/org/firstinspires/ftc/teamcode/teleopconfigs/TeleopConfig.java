package org.firstinspires.ftc.teamcode.teleopconfigs;

import com.qualcomm.robotcore.hardware.Gamepad;

public class TeleopConfig {

    Gamepad gamepad1;
    Gamepad gamepad2;
    public float x_movement;
    public float y_movement;
    public float turn;
    public boolean bay;
    public boolean lift;
    public boolean pullup;
    public boolean updateTelemetry;
    public boolean planeRelease;
    public boolean intake;
    public boolean validate; //prevents plane and pullup

    public TeleopConfig(Gamepad gamepad1, Gamepad gamepad2) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
    }

    public void check() {
        // drivetrain, the usual
        x_movement = this.gamepad1.left_stick_x;
        y_movement = this.gamepad1.left_stick_y;
        turn = this.gamepad1.right_stick_x;

        // endgame stuff: validate must be pushed with corresponding button to work
        validate = this.gamepad1.x;
        planeRelease = this.gamepad1.dpad_right;
        pullup = this.gamepad1.dpad_up; // press to extend, press again to pull up. **also folds intake in so it is not touching the ground

        // for pixels
        lift = this.gamepad1.y; // to move the lift up and down, automatically controls bay rotator
        bay = this.gamepad1.a; // to release pixel when the lift is at the dropping position
        intake = this.gamepad1.b; // activates/deactivates intake, sets bay to according position at the same time

        // other
        updateTelemetry = this.gamepad1.back;

    }


}


// OLD CODE
//        slowMode = this.gamepad1.b || this.gamepad2.b;
//        x_movement = this.gamepad1.left_stick_x;
//        y_movement = this.gamepad1.left_stick_y;
//        turn = this.gamepad1.right_stick_x;
//        bayClose = this.gamepad1.left_bumper || this.gamepad2.left_bumper;
//        bayOpen = this.gamepad1.right_bumper || this.gamepad2.right_bumper;
//        pullupUpManual = this.gamepad1.dpad_up;
//        pullupDownManual = this.gamepad1.dpad_down;
//        armUpPreset = this.gamepad1.right_trigger;
//        armDownPreset = this.gamepad1.left_trigger;
//        updateTelemetry = this.gamepad1.back;
//        planeRelease = this.gamepad1.x;
//        validate = this.gamepad1.y;
//        intakeForward = this.gamepad1.dpad_right;
//        intakeReverse = this.gamepad1.dpad_left;
//        intakeStop = this.gamepad1.a;
//        intakeNeutral = this.gamepad1.b;