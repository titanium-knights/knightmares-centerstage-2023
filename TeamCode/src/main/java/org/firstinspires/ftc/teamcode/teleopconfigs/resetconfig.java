package org.firstinspires.ftc.teamcode.teleopconfigs;

import com.qualcomm.robotcore.hardware.Gamepad;

public class resetconfig extends TeleopConfig {

    public resetconfig(Gamepad gamepad1, Gamepad gamepad2) {
        super(gamepad1, gamepad2);
    }

    @Override
    public void check() {
        super.check();
        //here you can override certain controls
        // make sure to remove the buttons from other controls if you add it something else
        clawClose = this.gamepad1.x;
        armUpPreset = this.gamepad1.dpad_up;
        armDownPreset = this.gamepad1.dpad_down;
        pullupUp = this.gamepad1.dpad_left;
        pullupDown = this.gamepad1.dpad_right;
        //TODO: add more controls here for reset teleop
    }
}
