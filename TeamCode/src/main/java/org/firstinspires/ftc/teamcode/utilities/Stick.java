package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Stick {
    Servo stickRot;

    public Stick(HardwareMap hmap) {
        this.stickRot = hmap.servo.get(CONFIG.stick);
    }

    public void lock() {
        stickRot.setPosition(0.0); //TODO: tune the value pls
    }
    public void unlock() {
        stickRot.setPosition(0.4); //TODO: pls tune the value
    }
}
