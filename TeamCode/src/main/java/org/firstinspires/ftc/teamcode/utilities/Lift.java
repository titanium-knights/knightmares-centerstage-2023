package org.firstinspires.ftc.teamcode.utilities;

import static java.lang.Double.max;
import static java.lang.Double.min;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lift {
    public DcMotor liftMotor;

    public static double LIFT_POWER_MULTIPLIER = .9; // so it doesn't turn too fast
    public static double ENCODER_TICKS = 537.6/28;
    /* ENCODER TICKS EXPLAINED:
    Originally, there are 537.6 encoder ticks, which means that for one 360 degree rotation, the encoder ticks 537.6 times (for 1150 rpm motor)
    28 rotations of worm gear : 1 rotation of the gear
    since the motor directly turns the worm gear, that means that now, the gear that is connected to the lift will turn slower
    in other words, it will turn 28 times slower
    that means in one motor rotation, there will be 537.6/28 encoder ticks */

    // angle of vertical arm from robot arm's minimum position
    //TODO: consider deleting unused variable
    public static double VERTICAL_ANGLE = 135;

    public static double MAX_LIMIT = 240; // upper limit
    public static double MIN_LIMIT = -5; // lower limit for manual lift

    //these might end up being the same as min and max limit
    public static double PICKUP_POINT = 0.1; // tells the arm thing where to go
    public static double DROP_POINT = 0.60;

    // higher power lift going up vs going down cause power + gravity = more power
    // Not going to use due to difficulty (unless someone comes up w something)
    public static double POWER_UP = 0.75;
    public static double POWER_DOWN = 0.55;

    //True = at drop zone, false = at pickup zone
    boolean atDropZone;

    public Lift(HardwareMap hmap) {
        this.liftMotor = hmap.dcMotor.get(CONFIG.liftMotor);
        this.atDropZone = true;
        setInit();
    }

    public void setInit() {
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // makes it so the motor is not loose
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    //completely stop lift
    public void stop() {
        liftMotor.setPower(0);
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    // purpose: make sure you don't interrupt a preset
    public boolean isBusy() {
        return liftMotor.isBusy();
    }

    // overload the other setPower to default slowmode to false
    public void setPower(boolean dir) {
        setPower(dir, false);
    }

    /**
     * @param dir: false = to pick up, true = to drop
     * @param slowMode: false = normal mode, true = slow mode
     */
    public void setPower(boolean dir, boolean slowMode) {
        //TODO: rename to slow_power and power because we're not actually using them for up/down
        double power = slowMode ? POWER_DOWN : POWER_UP;
        // if slowMode is on, use power down constant, else, use power up constant
        if (dir) liftMotor.setPower(power * LIFT_POWER_MULTIPLIER);
        else liftMotor.setPower(-power * LIFT_POWER_MULTIPLIER);
    }

    // Returns lift position in degrees, robot centric (minimum is 0)
    public double getPosition(){
        // Initially gets back position in terms of encoder ticks, which converts to degrees
        return (liftMotor.getCurrentPosition()*360/ ENCODER_TICKS);
    }

    public boolean runToPosition(double angle) {
        // takes the angle we want it to go to and makes sure the angle is within range
        // just in case wrong input
        double orig = angle;
        angle = min(angle, MAX_LIMIT);
        angle = max(angle, MIN_LIMIT);
        if (orig != angle) return false;

        // converts angle into encoder ticks and then runs to position
        liftMotor.setTargetPosition((int) (ENCODER_TICKS *angle/360));
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // with run to position always positive power (setPower will be the one determining direction)
        // run to position is always in presets or else it'll be jittery
        setPower(true);

        return true; // to confirm that it runs
    }

    public boolean toScan(){
        return runToPosition(45);
    }

    public boolean toPickUp(){ // preset for picking up pixels
        return runToPosition(0);
    }

    public boolean toDrop(){ // preset for dropping pixels
        //TODO: FIND X
        final double x = 155;
        return runToPosition(x);
    }

    public void toBackBoard(){ // manual drop
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //if (checkLimits()) return; // wont turn if its at the limit
        setPower(true);
    }

    public void toRobot(){ // manual pick up
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //if (checkLimits()) return;
        setPower(false);
    }
}
