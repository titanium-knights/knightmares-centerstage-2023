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

    public double getPosition() {
        return clawOpener.getPosition();
    }

    public void open() {
        isOpen = true;
        clawOpener.setPosition(1.0);
    }

    public void close() {
        isOpen = false;
        clawOpener.setPosition(0.0);
    }

    public void turnClaw(double radians) {
        double degrees = Math.toDegrees(radians);
        clawRotator.setPosition(degrees/360);
    }

    public void maintain(double liftAngle) {
        final double MAX_BUFFER = 2.0;
        double theta = 180 - Math.toDegrees(liftAngle);
        double phi = 180 - angle;
        while (Math.abs(theta + phi - 240.0) > MAX_BUFFER) {
            double delta = (240.0 - theta - 180.0) * -1.0;
            turnClaw(Math.toRadians(delta));
        }
    }
}
