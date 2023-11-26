package org.firstinspires.ftc.teamcode.utilities;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    Servo clawOpener;
    Servo clawRotator;

    // Angle from when arm is vertical, where 0 is the minimum degree that the robot arm can go down (robot centric based)
    double VERTICAL_ANGLE = 122;

    // Angle of the clawRotator when the lift is to its minimum degree (0) robot centric
    double CLAW_ANGLE_PICKUP = VERTICAL_ANGLE;

    // Angle of the clawRotator when the lift is about to drop
    double CLAW_ANGLE_DROP = 180-VERTICAL_ANGLE;

    //double ANGLE_SERVO_MODIFIER = 1/300;


    public Claw(HardwareMap hmap) {
        this.clawOpener = hmap.servo.get(CONFIG.clawServo);
        this.clawRotator = hmap.servo.get(CONFIG.clawSpin);
    }

    // sets positions for open and close position of the claw
    // only has two positions because servos only allow for two positions
    // TODO: check if the open and close is not reversed
    public void open() {
        clawOpener.setPosition(0.4);
        //clawOpener.getController().pwmDisable();
    }
    public void close() {
        clawOpener.setPosition(0.5);
    }

    // gets position of the clawRotator initially in servo angle and then converts it to degrees, robot centric
    public double getPosition() {return clawRotator.getPosition();}

    public void setPosition(double position) {
        clawRotator.setPosition(position);
    }

    // rotates back to drop position
    public void setZero(){ // rotate forward
        clawRotator.setPosition(0.0);
    }
    public void setOne(){ // rotate to pick up position
        clawRotator.setPosition(0.5);
    }

    public void setPick(){clawRotator.setPosition(0.7);}
    public void setDrop(){clawRotator.setPosition(0.2);}

//    public void maintain(double liftAngle) {
////        double trueAngle = liftAngle - ; // trueAngle is where 0 degrees is vertical
////        // converts robot centric angle --> 0 = vertical angle centric
////        // if anyone is confused about it you can ask Daniel or Aubrey
//
////        if (trueAngle > -15) maintainDrop(trueAngle);
////        else maintainPickup(trueAngle);
//
//        // if over a certain point (when lift goes beyond 135), clawRotator will rotate back
//        // else, clawRotator will rotate to the front
//        if (liftAngle > 135) maintainDrop();
//        else maintainPickup();
//    }
//
//    public void maintainDrop() {
//        if (abs(getPosition() - CLAW_ANGLE_DROP) > MAX_BUFFER) {
//            setPosition(CLAW_ANGLE_DROP);
//        }
//    }
//
//
//
//    public void maintainPickup() {
//        if (abs(getPosition() - CLAW_ANGLE_PICKUP) > MAX_BUFFER) {
//            setPosition(CLAW_ANGLE_PICKUP);
//        }
//    }

}
