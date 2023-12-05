package org.firstinspires.ftc.teamcode.utilities;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Bay {
    Servo bayOpener;
    Servo bayRotator;

    // Angle from when arm is vertical, where 0 is the minimum degree that the robot arm can go down (robot centric based)
    double VERTICAL_ANGLE = 122;

    // Angle of the bayRotator when the lift is to its minimum degree (0) robot centric
    double bay_ANGLE_PICKUP = VERTICAL_ANGLE;

    // Angle of the bayRotator when the lift is about to drop
    double bay_ANGLE_DROP = 180-VERTICAL_ANGLE;

    //double ANGLE_SERVO_MODIFIER = 1/300;


    public Bay(HardwareMap hmap) {
        this.bayOpener = hmap.servo.get(CONFIG.bayServo);
        this.bayRotator = hmap.servo.get(CONFIG.baySpin);
    }

    // sets positions for open and close position of the bay
    // only has two positions because servos only allow for two positions
    // TODO: check if the open and close is not reversed
    public void open() {
        bayOpener.setPosition(0.4); // TODO: tune values
    }
    public void close() {
        bayOpener.setPosition(0.5);
    } // TODO: tune values

    // gets position of the bayRotator initially in servo angle and then converts it to degrees, robot centric
    public double getPosition() {return bayRotator.getPosition();}

    public void setPosition(double position) {
        bayRotator.setPosition(position);
    }

    // rotates back to drop position
    public void setPick(){bayRotator.setPosition(0);}
    public void setDrop(){bayRotator.setPosition(0.6666666666666666);} // 120/180

}
