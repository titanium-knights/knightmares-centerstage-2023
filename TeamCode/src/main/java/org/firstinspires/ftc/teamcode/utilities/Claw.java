package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    Servo clawOpener;
    Servo clawRotator;

    boolean isOpen = false;
    double angle = 0;

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
        clawOpener.setPosition(0.5); //TODO update
    }

    // moves servo to closed position
    public void close(){
        isOpen = false;
        clawOpener.setPosition(0); // TODO update
    }

    //how many radians the claw should turn
    public void turnClaw(double radians) {
        double degrees = Math.toDegrees(radians);
        clawRotator.setPosition(degrees);
    }

    //TODO: documentation / prove math
    public void maintain(double liftAngle) {
        double theta = 180 - Math.toDegrees(liftAngle);
        double phi = 180 - angle; // math check?
        while (Math.abs(theta + phi - 240.0) > 2.0) {// maybe do some more accurate stuff
            double delta = (240.0 - theta - 180.0) * -1.0;
            turnClaw(delta);
        }
    }
}
