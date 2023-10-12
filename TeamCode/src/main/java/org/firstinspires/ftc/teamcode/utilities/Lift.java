package org.firstinspires.ftc.teamcode.utilities;

import static java.lang.Double.max;
import static java.lang.Double.min;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lift {
    public DcMotor liftMotor;

    public static double LIFT_POWER_MULTIPLIER = .9;
    public static double Encoder_Ticks = 537.7;
    public static double Up_Angle = 55;
    //Angle from initial when arm is straight up.

    // TODO check limit values based on degrees,
    // should be between ~ -45 & 180 degrees
    public static double MAX_LIMIT = 240 - Up_Angle;
    public static double MIN_LIMIT = -5 - Up_Angle;

    //these might end up being the same as min and max limit
    public static double PICKUP_POINT = 0.1;
    public static double DROP_POINT = 0.60;

    // higher power lift going up vs going down cause power + gravity = more power
    //Not going to use due to difficulty (unless someone comes up w something)
    public static double POWER_UP = 0.4;
    public static double POWER_DOWN = 0.3;

    boolean state;
    //True = on drop zone, false = on pickup zone

    public Lift(HardwareMap hmap) {
        this.liftMotor = hmap.dcMotor.get(CONFIG.liftMotor);
        this.state = true;
        setInit();
    }

    public void setInit() {
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void stop(){
        liftMotor.setPower(0);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public boolean checkLimits(){
        double angle = getPosition();
        if (angle < MIN_LIMIT) {stop(); return true;}
        if (angle > MAX_LIMIT) {stop(); return true;}
        return false;
    }

    public boolean updateState(){
        if (getPosition() > Up_Angle) state = false;
        state = true;
        return state;
    }

    public boolean isBusy() {return liftMotor.isBusy();}

    public void setPower(boolean dir) {
        //false = to pick up, true = to drop
        if (dir) liftMotor.setPower(POWER_DOWN * LIFT_POWER_MULTIPLIER);
        else liftMotor.setPower(-POWER_DOWN * LIFT_POWER_MULTIPLIER);
    }

    public double getPosition(){
        return (liftMotor.getCurrentPosition()*360/Encoder_Ticks);
    }

    public boolean runToPosition(double angle) {
        //just in case wrong input
        double orig = angle;
        angle = min(angle, MAX_LIMIT);
        angle = max(angle, MIN_LIMIT);
        if (orig != angle) return false;

        liftMotor.setTargetPosition((int) (Encoder_Ticks*angle/360));
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //with run to position always positive power
        setPower(true);

        return true;
    }

    public boolean toScan(){
        return runToPosition(45);
    }

    public boolean toBottomPreset(){
        return runToPosition(0);
    }

    public void toDrop(){
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if (checkLimits()) return;
        setPower(true);

    }

    public void toPickUp(){
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if (checkLimits()) return;
        setPower(false);
    }
}
