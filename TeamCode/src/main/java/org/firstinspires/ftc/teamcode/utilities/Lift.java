package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lift {
    public DcMotor liftMotor;

    public static double LIFT_POWER_MULTIPLIER = .9;

    // TODO check limit values based on degrees,
    // should be between ~ -45 & 135 degrees
    public static double MAX_LIMIT = 0.65;
    public static double MIN_LIMIT = 0.01;

    //these might end up being the same as min and max limit
    public static double PICKUP_POINT = 0.1;
    public static double DROP_POINT = 0.60;

    // higher power lift going up vs going down cause power + gravity = more power
    public static double POWER_UP = 0.4;
    public static double POWER_DOWN = 0.3;


    public Lift(HardwareMap hmap) {
        this.liftMotor = hmap.dcMotor.get(CONFIG.liftMotor);
    }

    public void setPower(double power) {
        liftMotor.setPower(-power * LIFT_POWER_MULTIPLIER);
    }

    public void setPosition(int pos, double power) {
        liftMotor.setTargetPosition(pos);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setPower(power);
    }

    public void setInit() {
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public int getPosition() {
        return liftMotor.getCurrentPosition();
    }

    public void runToPosition(int pos) {
        runToPosition(pos, LIFT_POWER_MULTIPLIER);
    }

    public void runToPosition(int pos, double multiplier){
        int currentPos = liftMotor.getCurrentPosition();

        //consider creating a more fine grained control loop
        if(pos - currentPos > 30){
            setPower(-1 * multiplier);
        } else if(pos - currentPos < -30){
            setPower(multiplier);
        } else {
            setPower(0);
        }
    }
}
