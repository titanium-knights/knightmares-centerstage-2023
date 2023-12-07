package org.firstinspires.ftc.teamcode.utilities;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static java.lang.Double.max;
import static java.lang.Double.min;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm {
    DcMotor armMotor;

    public static double ENCODER_TICKS = 1425*3;

    /* ENCODER TICKS EXPLAINED:
    Originally, there are 103.8 encoder ticks, which means that for one 360 degree rotation, the encoder ticks 103.8 times (for 1150 rpm motor)
    28 rotations of worm gear : 1 rotation of the gear
    since the motor directly turns the worm gear, that means that now, the gear that is connected to the lift will turn slower
    in other words, it will turn 28 times slower
    that means in one motor rotation, there will be 537.6/28 encoder ticks */

    // angle of vertical arm from robot arm's minimum position
    public static double VERTICAL_ANGLE = 122;

    public static double MAX_LIMIT = VERTICAL_ANGLE + 58; // upper limit
    public static double MIN_LIMIT = -5; // lower limit for manual lift

    // higher power lift going up vs going down cause power + gravity = more power
    public static double FULL_POWER = 0.95;
    public static double SLOW_POWER = 0.40;


    public Arm(HardwareMap hmap) {
        this.armMotor = hmap.dcMotor.get(CONFIG.armMotor);
        setInit();
    }

    public void setInit() {
         // makes it so the motor is not loose
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armMotor.setZeroPowerBehavior(BRAKE);
    }

    //completely stop lift
    public void stop() {
        armMotor.setPower(0);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    // purpose: make sure you don't interrupt a preset
    public boolean isBusy() {
        return armMotor.isBusy();
    }

    // overload the other setPower to default slowmode to false

    /**
     * @param dir: false = to pick up, true = to drop
     */
    public void setPower(boolean dir) {
        if (dir) {
            if (getPosition() < VERTICAL_ANGLE - 45) {
                armMotor.setPower(FULL_POWER);
            }
            else if (getPosition() >= VERTICAL_ANGLE - 45 && getPosition() < (MAX_LIMIT)) {
                armMotor.setPower(SLOW_POWER);
            }
            else {
                armMotor.setPower(0);
            }

        }
        else {

            if (getPosition() > VERTICAL_ANGLE - 40) {
                armMotor.setPower(-SLOW_POWER); //TODO: JUNA, DO THIS
            }
            else {
                armMotor.setPower(-SLOW_POWER);
            }
        }
    }

    // Returns lift position in degrees, robot centric (minimum is 0)
    public double getPosition(){
        // Initially gets back position in terms of encoder ticks, which converts to degrees
        return (360* ((double) armMotor.getCurrentPosition())/ENCODER_TICKS);

    }

    public boolean runToPosition(double angle) {
        // takes the angle we want it to go to and makes sure the angle is within range
        // just in case wrong input
        double orig = angle;
        // converts angle into encoder ticks and then runs to position
        armMotor.setTargetPosition((int) (ENCODER_TICKS *angle/360));
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // with run to position always positive power (setPower will be the one determining direction)
        // run to position is always in presets or else it'll be jittery
        armMotor.setPower(-0.7);

        return true; // to confirm that it runs
    }

    public boolean toNeutral(){
        return runToPosition(-25);
    } //preset to wait until pixels taken in

    public boolean toPickUp(){ // preset for picking up pixels
        return runToPosition(0);
    }

    public boolean toDrop(){ // preset for dropping pixels
        final double x = MAX_LIMIT;

        return runToPosition(-x);
    }

    public void toBackBoard(){ // manual drop
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setPower(true);
    }

    public void toRobot(){ // manual pick up
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setPower(false);
    }
}
