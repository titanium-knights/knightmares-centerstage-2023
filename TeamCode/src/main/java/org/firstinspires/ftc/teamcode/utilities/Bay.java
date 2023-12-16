package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class Bay {
    Servo bayOpener;
    Servo bayRotator;

    // Angle from when arm is vertical, where 0 is the minimum degree that the robot arm can go down (robot centric based)
    double VERTICAL_ANGLE = 122;
    double INITIAL_ARM_ANGLE = 58; // human centric (0 is vert down)

    public Bay(HardwareMap hmap) {
        this.bayOpener = hmap.servo.get(CONFIG.bayServo);
        this.bayRotator = hmap.servo.get(CONFIG.baySpin);
    }

    // TODO: ensure that open and close are not reversed
    // TODO: tune values
    public void open() {
        bayOpener.setPosition(0.0);
    }
    public void close() {
        bayOpener.setPosition(0.35);
    }

    // gets position of the bayRotator initially in servo angle and then converts it to degrees, robot centric
    public double getPosition() {return bayRotator.getPosition()*300;}

    public void setPosition(double position) {
        bayRotator.setPosition(position);
    }

    public double degreeToServo(double angle) {
        return angle/300;
    }

    public void setZero() {bayRotator.setPosition(0);}
    public void setOne() {bayRotator.setPosition(1);}
    // rotates back to drop position
    public void setPick() {bayRotator.setPosition(0.92);} // 270/300
    public void setDrop() {bayRotator.setPosition(0.35);} // 120/300

    public void disable() {
        bayRotator.getController().pwmDisable();
    }

    public void maintain(Arm arm){
        if (arm.getPosition() >= 0 && arm.getPosition() <= 102) {
            bayRotator.getController().pwmDisable();
            //double liftAngle = INITIAL_ARM_ANGLE + (arm.getPosition());
            //setPosition(degreeToServo(300 - (180 - liftAngle)));
            //setPosition((180-liftAngle)/300.0);
        } else {
            bayRotator.getController().pwmEnable();
            setPosition(0.4);
            //setPosition(270.0/300.0);
        }
    }

}
