package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lift {
    public DcMotor liftMotor;

    public static double LIFT_POWER = .6;
    public static double LIFT_POWER_MULTIPLYER = .9;

    // will need to adjust based on how much the motor rotates
    public static double OVER_MID_POSITION = 0.6;
    public static double OVER_HIGH_POSITION = 0.5;
    public static double HIGH_POSITION = 0.4;
    public static double MID_POSITION = 0.3;
    public static double LOW_POSITION = 0.2;
    public static double GROUND_POSITION = 0.1;

    // to prevent bad things from happening
    public static double MAX_LIMIT = 0.65;
    public static double MIN_LIMIT = 0.01;

    // i have no clue what this is supposed to do
    public static double Y = 0;


    // change based on driver's preference
    public double LEFT_JOYSTICK_Y_Positive = 0;
    public double LEFT_JOYSTICK_Y_Negative = 0;

    // stuff that changes sensitivity of the controls
    public static double SENSITIVITY = 0.05;
    public static double TOLERANCE = 20;

    // higher power lift going up vs going down cause power + gravity = more power
    public static double POWER_UP = 0.4;
    public static double POWER_DOWN = 0.3;


    public Lift(HardwareMap hmap) {
        this.liftMotor = hmap.dcMotor.get(CONFIG.liftMotor);
    }

    public void setPower(double power) {
        liftMotor.setPower(-power * LIFT_POWER_MULTIPLYER);
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

    public int getPositionR() {
        return liftMotor.getCurrentPosition();
    }

    public void runToPosition(int pos, double multiplier){
        int currentPos = liftMotor.getCurrentPosition();
        //double multiplier = Math.min(1, Math.max(0, Math.abs(pos - currentPos) / 150.0));
        if(pos - currentPos > 30){
            setPower(-1 * multiplier);
        }
        else if(pos - currentPos < -30){
            setPower(1 * multiplier);
        }
        else if (pos == 0) {
            setPower(0);
        } else {
            setPower(0);
        }
    }


}
