package org.firstinspires.ftc.teamcode.utilities;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    Servo clawOpener;
    Servo clawRotator;

    final double MAX_BUFFER = 2.0; // we dont want it constantly adjusting so if its within 2 degrees whatever
    boolean state = false;
    // True = dropping move, False = Pickup

    double zeroLiftAngle = 35;
    double zeroClawAngle = -35;
    double servoAngleModifier = (double) 360/300; // servos only have 300 degrees so we wanna switch it to 360
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

    public void setPosition(double des){ //sets the position fo the claw
        double rawangle = des - zeroClawAngle; // in relation to zeroClawAngle so modified (everything we give it will be in relation to our adjusted zero)
        clawRotator.setPosition(rawangle*servoAngleModifier); // converts the raw angle from 0 to 360 to 0 to 300
    }

    public void maintain(double liftAngle) { // the thing that runs continuously always
        // makes sure the claw is aligned with either the ground or the backboard
        double trueAngle = liftAngle - zeroLiftAngle; // adjusts by the zeroLiftAngle
        if (trueAngle > 0) maintainDrop(trueAngle); // if greater than 0 its on the drop side --> we know which mode to use
        else maintainPickup(trueAngle);
    }

    public void maintainDrop(double trueAngle){
        double angle = 180 - trueAngle;
        double delta = 240 - angle; // we want the angle of the lift and the angle of the claw to equal 240
        if (abs(getPosition() - delta) > MAX_BUFFER ){
            setPosition(delta);
        }
    }

    public void maintainPickup(double trueAngle){
        double angle = abs(trueAngle);
        double delta = -angle;
        if (abs(getPosition() - delta) > MAX_BUFFER){
            setPosition(delta);
        }
    }
}
