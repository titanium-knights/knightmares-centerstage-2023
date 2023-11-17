package org.firstinspires.ftc.teamcode.teleopconfigs;

import com.qualcomm.robotcore.hardware.Gamepad;

public class TeleopConfig {

    Gamepad gamepad1;
    Gamepad gamepad2;

    public boolean slowMode;
    public float x_movement;
    public float y_movement;
    public float turn;
    public boolean clawOpen;
    public boolean clawClose;
    public boolean clawRotatorScore;
    public boolean clawRotatorPick;
    public boolean pullupUpManual;
    public boolean pullupDownManual;
    public float armUp;
    public float armDown;
    public boolean armUpPreset;
    public boolean armDownPreset;
    public boolean pullupUp;
    public boolean pullupDown;
    public boolean updateTelemetry;
    public boolean planeLaunch;

    public TeleopConfig(Gamepad gamepad1, Gamepad gamepad2) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
    }

    public void check() {
        slowMode = this.gamepad1.start || this.gamepad2.start;
        x_movement = this.gamepad1.left_stick_x;
        y_movement = this.gamepad1.left_stick_y;
        turn = this.gamepad1.right_stick_x;
        clawOpen = this.gamepad1.left_bumper || this.gamepad2.left_bumper;
        clawClose = this.gamepad1.right_bumper || this.gamepad2.right_bumper;
        clawRotatorScore = this.gamepad1.b;
        clawRotatorPick = this.gamepad1.a;
        pullupUpManual = this.gamepad1.dpad_left;
        pullupDownManual = this.gamepad1.dpad_right;
        armUp = this.gamepad1.left_trigger;
        armDown = this.gamepad1.right_trigger;
        // TODO: fix lift preset
        //armUpPreset = this.gamepad2.b;
        //armDownPreset = this.gamepad1.a;
        pullupUp = this.gamepad1.y;
        pullupDown = this.gamepad1.x;
        updateTelemetry = this.gamepad1.back;
        planeLaunch = this.gamepad1.dpad_up;
    }


}
