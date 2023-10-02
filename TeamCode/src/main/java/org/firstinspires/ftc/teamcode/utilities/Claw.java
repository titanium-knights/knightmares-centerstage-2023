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

    // return the servo position
    public double getPosition() {
        return clawOpener.getPosition();
    }

    // moves servo to open position
    public void open() {
        isOpen = true;
        clawOpener.setPosition(1); // i put a random number idk
    }

    // moves servo to closed position
    public void close(){
        isOpen = false;
        clawOpener.setPosition(0); // random number
    }

    // turns claw to a certain position
    public void turnClaw(double radians) { //where radians is the amount the arm is turning
        double degrees = (180*radians)/Math.PI; // how much the claw is turning in degrees
        clawRotator.setPosition(240-degrees);
    }
}
