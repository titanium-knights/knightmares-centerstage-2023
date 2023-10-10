package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    Servo clawOpener;
    Servo clawRotator;

    boolean state = false;
    // True = dropping move, False = Pickup


    double zeroAngle = 0;
    //Angle in degrees of bar such that claw is pointing straight down at pickup

    double angle = 0;


    public Claw(HardwareMap hmap){
        this.clawOpener = hmap.servo.get(CONFIG.clawServo);
        this.clawRotator = hmap.servo.get(CONFIG.clawSpin);
    }

    public double getPosition() {
        return clawOpener.getPosition();
    }

    public void setPosition(double des){
        angle = des;
        clawRotator.setPosition(des/360);
    }
    public void open() {
        clawOpener.setPosition(1.0);
    }

    public void close() {
        clawOpener.setPosition(0.0);
    }

    public void maintain(double liftAngle) {
        double trueAngle = liftAngle + zeroAngle;
        final double MAX_BUFFER = 2.0;
        double theta = 180 - Math.toDegrees(liftAngle);
        double phi = 180 - angle;
        while (Math.abs(theta + phi - 240.0) > MAX_BUFFER) {
            double delta = (240.0 - theta - 180.0) * -1.0;
            setPosition(delta);
        }
    }

    public void maintainDrop(double liftAngle){

    }

    public void maintainPickup(double liftAngle){
        setPosition(180 - liftAngle);
    }
}
