package org.firstinspires.ftc.teamcode.utilities;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    Servo clawOpener;
    Servo clawRotator;

    final double MAX_BUFFER = 2.0;

    // Angle from when arm is vertical, where 0 is the minimum degree that the robot arm can go down (robot centric based)
    double zeroLiftAngle = 135;

    // Angle of the clawRotator when the lift is to its minimum degree (0) robot centric
    double zeroClawAngle = -zeroLiftAngle;

    // if multiplied with the servo angle (which is out of 300), will convert to degrees
    // if divided with angle in degrees (out of 360), will convert to servo angle out of 300
    double servoAngleModifier = (double) 360/300;


    public Claw(HardwareMap hmap) {
        this.clawOpener = hmap.servo.get(CONFIG.clawServo);
        this.clawRotator = hmap.servo.get(CONFIG.clawSpin);
    }

    // sets positions for open and close position of the claw
    // only has two positions because servos only allow for two positions
    // TODO: check if the open and close is not reversed
    public void open() {
        clawOpener.setPosition(0.0);
    }
    public void close() {
        clawOpener.setPosition(1.0);
    }

    // gets position of the clawRotator initially in servo angle and then converts it to degrees
    public double getPosition() {
        return clawRotator.getPosition()/servoAngleModifier;
    }

    //
    public void setPosition(double des){
        // sets rawangle to the real angle of our destination trust it works

        // TODO: figure out how the heck this works
        double rawangle = des + zeroClawAngle;
        clawRotator.setPosition(1 - rawangle/servoAngleModifier);
    }

    public void setZero(){
        clawRotator.setPosition(0);
    }

    public void setOne(){
        clawRotator.setPosition(1);
    }


    public void maintain(double liftAngle) {
        double trueAngle = liftAngle - zeroLiftAngle;
        if (trueAngle > -15) maintainDrop(trueAngle);
        else maintainPickup(trueAngle);
    } //takes angle

    public void maintainDrop(double trueAngle){
        double angle = 180 - trueAngle;
        double delta = 240 - angle;
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
