package org.firstinspires.ftc.teamcode.teleopconfigs;

import com.qualcomm.robotcore.hardware.Gamepad;

public class TeleopConfig {

    Gamepad gamepad1;
    Gamepad gamepad2;

    public boolean slowMode;
    public float x_movement;
    public float y_movement;
    public float turn;
    public boolean bayOpen;
    public boolean bayClose;
    public boolean bayRotatorScore;
    public boolean bayRotatorPick;
    public boolean pullupUpManual;
    public boolean pullupDownManual;
    public float armUpManual;
    public float armDownManual;
    public float armUpPreset;
    public float armDownPreset;
    public boolean pullupUp;
    public boolean pullupDown;
    public boolean updateTelemetry;
    public boolean planeLaunch;
    public boolean planeRelease;
    public boolean intakeForward;
    public boolean intakeReverse;
    public boolean intakeStop;
    public boolean intakeNeutral;
    public boolean validate; //prevents plane and pullup

    public TeleopConfig(Gamepad gamepad1, Gamepad gamepad2) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
    }

    public void check() {
        slowMode = this.gamepad1.b || this.gamepad2.b;
        x_movement = this.gamepad1.left_stick_x;
        y_movement = this.gamepad1.left_stick_y;
        turn = this.gamepad1.right_stick_x;
        bayClose = this.gamepad1.left_bumper || this.gamepad2.left_bumper;
        bayOpen = this.gamepad1.right_bumper || this.gamepad2.right_bumper;
        pullupUpManual = this.gamepad1.dpad_up;
        pullupDownManual = this.gamepad1.dpad_down;
        armUpPreset = this.gamepad1.right_trigger;
        armDownPreset = this.gamepad1.left_trigger;
        updateTelemetry = this.gamepad1.back;
        planeRelease = this.gamepad1.x;
        validate = this.gamepad1.y;
        intakeForward = this.gamepad1.dpad_right;
        intakeReverse = this.gamepad1.dpad_left;
        intakeStop = this.gamepad1.a;
        intakeNeutral = this.gamepad1.b;
    }


}
