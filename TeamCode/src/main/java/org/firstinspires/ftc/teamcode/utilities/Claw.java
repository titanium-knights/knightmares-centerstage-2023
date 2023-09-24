package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    Servo clawOpener;
    Servo clawRotator;
    boolean isOpen = false;

    public Claw(HardwareMap hmap){
        this.clawOpener = hmap.servo.get(CONFIG.clawServo);
        this.clawRotator = hmap.servo.get(CONFIG.clawSpin);
    }

    public boolean isOpen() {
        return isOpen;
    }

    //return the servo position
    public int getPosition() {
        //TODO
        return -1;
    }

    public void open() {
        //TODO: move servo to open position
    }

    public void close(){
        //TODO: move servo to close position
    }

    public void turnClaw(double radians) {
        //TODO: turn claw to a certain position
    }
}
