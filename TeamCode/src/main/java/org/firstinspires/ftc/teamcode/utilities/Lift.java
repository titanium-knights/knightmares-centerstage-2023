package org.firstinspires.ftc.teamcode.utilities;

import static java.lang.Double.max;
import static java.lang.Double.min;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lift {
    public DcMotor liftMotor;

    public static double LIFT_POWER_MULTIPLIER = .9; // so it doesnt turn too fast
    public static double Encoder_Ticks = 537.7;
    public static double Up_Angle = 55; // when the robot is turned on we want this angle to be 0
    //Angle from initial when arm is straight up.

    // TODO check limit values based on degrees,
    // should be between ~ -45 & 180 degrees
    public static double MAX_LIMIT = 240; // so it doesnt go too far down
    public static double MIN_LIMIT = -5; // stops it from going further down if we use manual lift

    //these might end up being the same as min and max limit
    public static double PICKUP_POINT = 0.1; // tells the arm thing where to go
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
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // makes it so the motor is not like loose
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void stop(){ // sets power to 0 everything stops
        liftMotor.setPower(0); // refers to line 41
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public boolean checkLimits(){ // safeties to stop it from going too far
        double angle = getPosition();
        if (angle < MIN_LIMIT) {stop(); return true;}
        if (angle > MAX_LIMIT) {stop(); return true;}
        return false;
    }

    public boolean updateState(){ // dont use
        if (getPosition() > Up_Angle) state = false;
        state = true;
        return state;
    }

    public boolean isBusy() {return liftMotor.isBusy();} // used to make sure you dont interrupt a preset

    public void setPower(boolean dir) {
        setPower(dir, false);
    }

    public void setPower(boolean dir, boolean slowMode) {
        //false = to pick up, true = to drop
        double power = slowMode ? POWER_DOWN : POWER_UP;
        if (dir) liftMotor.setPower(power * LIFT_POWER_MULTIPLIER);
        else liftMotor.setPower(-power * LIFT_POWER_MULTIPLIER);
    }

    public double getPosition(){
        return (liftMotor.getCurrentPosition()*360/Encoder_Ticks);
    } // Returns degrees

    public boolean runToPosition(double angle) {
        // takes the angle we want it to go to and makes sure the angle is within range
        //just in case wrong input
        double orig = angle;
        angle = min(angle, MAX_LIMIT);
        angle = max(angle, MIN_LIMIT);
        if (orig != angle) return false;

        // converts angle into encoder ticks and then runs to position
        liftMotor.setTargetPosition((int) (Encoder_Ticks*angle/360));
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //with run to position always positive power
        // runto position is always in presets or else itll be jittery
        setPower(true);

        return true;
    }

    public boolean toScan(){
        return runToPosition(45);
    }

    public boolean toPickUp(){
        return runToPosition(0);
    }

    public boolean toDrop(){
        //TODO: FIND X
        final double x = 135;
        return runToPosition(x);
    }



    public void toBackBoard(){ // manual drop
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if (checkLimits()) return; // wont turn if its at the limit
        setPower(true);
    }

    public void toRobot(){ // manual pick up
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if (checkLimits()) return;
        setPower(false);
    }
}
