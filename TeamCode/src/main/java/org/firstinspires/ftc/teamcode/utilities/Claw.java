package org.firstinspires.ftc.teamcode.utilities;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    Servo clawOpener;
    Servo clawRotator;

    final double MAX_BUFFER = 2.0;
    boolean state = false;
    // True = dropping move, False = Pickup

    double zeroLiftAngle = 35;
    double zeroClawAngle = -35;
    double servoAngleModifier = (double) 360/300;
    //Angle from bottom when lift is pointing straight up
    //calibrate from motor -> fusion 360
    double angle = 0;


    public Claw(HardwareMap hmap) {
        this.clawOpener = hmap.servo.get(CONFIG.clawServo);
        this.clawRotator = hmap.servo.get(CONFIG.clawSpin);
    }

    public void open() {
        clawOpener.setPosition(1.0);
    }

    public void close() {
        clawOpener.setPosition(0.0);
    }

    public double getPosition() {
        return clawOpener.getPosition()/servoAngleModifier;
    }

    public void setPosition(double des){
        double rawangle = des - zeroClawAngle;
        clawRotator.setPosition(rawangle*servoAngleModifier);
    }

    public void maintain(double liftAngle) {
        double trueAngle = liftAngle - zeroLiftAngle;
        if (trueAngle > 0) maintainDrop(trueAngle);
        else maintainPickup(trueAngle);
    }

    public void maintainDrop(double trueAngle){
        double angle = trueAngle;
        double delta = 240 - angle;
        if (abs(getPosition() - delta) > MAX_BUFFER ){
            setPosition(delta);
        }
    }

    public void maintainPickup(double trueAngle){
        double angle = abs(trueAngle);
        double delta = angle;
        if (abs(getPosition() - delta) > MAX_BUFFER){
            setPosition(delta);
        }
    }
}
