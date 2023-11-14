package org.firstinspires.ftc.teamcode.utilities;

import static java.lang.Double.max;
import static java.lang.Double.min;
import static java.lang.Math.abs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.utilities.Pair;


public class PullUp {
    //DANIEL comment: For this, we don't really care about degrees so, we deal with
    //everything in encoder ticks or number of rotations.
    public DcMotor pullUpMotor1;
    public DcMotor pullUpMotor2;
    public static double Encoder_Ticks = 537.7;
    public static double PULLUP_POWER_MULTIPLIER = .9; // so it doesnt turn too fast

    public static double topHeight = 24*Encoder_Ticks;
    public static double DESYNC_LIMIT = 30;

    // TODO: check what the actual limits are
    //1 rotation = 8mm of travel. Our full extension is ~188mm, so lets set to be 0, 24
    public static double MAX_LIMIT = 24.5*Encoder_Ticks; // so it doesnt go too far down
    public static double MIN_LIMIT = 0; // stops it from going further down if we use manual lift

    public PullUp(HardwareMap hmap) {
        this.pullUpMotor1 = hmap.dcMotor.get(CONFIG.pullUpMotor1);
        this.pullUpMotor2 = hmap.dcMotor.get(CONFIG.pullUpMotor2);
    }

    public void setInit() {
        pullUpMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // makes it so the motor is not like loose
        pullUpMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pullUpMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pullUpMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // makes it so the motor is not like loose
        pullUpMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pullUpMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void stop(){ // sets power to 0 - everything stops
        pullUpMotor1.setPower(0);
        pullUpMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pullUpMotor2.setPower(0);
        pullUpMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    // true = outside of limits, false = within
    public boolean checkLimits(){
        double currentHeight1 = getPosition().first();
        if (currentHeight1 < MIN_LIMIT) {stop(); return true;}
        if (currentHeight1 > MAX_LIMIT) {stop(); return true;}
        return false;
    }

    public void setPower(double power, boolean dir) {
        if (dir) pullUpMotor1.setPower(power * PULLUP_POWER_MULTIPLIER);
        else pullUpMotor1.setPower(-power * PULLUP_POWER_MULTIPLIER);
        if (dir) pullUpMotor2.setPower(power * PULLUP_POWER_MULTIPLIER);
        else pullUpMotor2.setPower(-power * PULLUP_POWER_MULTIPLIER);
    }

    public Pair getPosition(){
        return (new Pair(pullUpMotor1.getCurrentPosition(), pullUpMotor2.getCurrentPosition()));
    }

    //true = synced, false = desynced.
    public boolean checkSync() {
        return abs(getPosition().first() - getPosition().second()) < DESYNC_LIMIT;
    }

    public void liftUp() {
        // converts angle into encoder ticks and then runs to position
        pullUpMotor1.setTargetPosition((int) (topHeight));
        pullUpMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pullUpMotor2.setTargetPosition((int) (topHeight));
        pullUpMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //with run to position always positive power
        // run to position is always in presets or else itll be jittery
        setPower(1,true);

    }

    public void liftDown() {
        // converts angle into encoder ticks and then runs to position

        pullUpMotor1.setTargetPosition((int) (topHeight - Encoder_Ticks));
        pullUpMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pullUpMotor2.setTargetPosition((int) (topHeight - Encoder_Ticks));
        pullUpMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //with run to position always positive power
        // run to position is always in presets or else itll be jittery
        setPower(0.3, true);
    }

    public void manualLeftUp(){
        pullUpMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);// wont turn if its at the limit
        pullUpMotor1.setPower(1);
    }

    public void manualLeftDown(){
        pullUpMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);// wont turn if its at the limit
        pullUpMotor1.setPower(-1);
    }

    public void manualRightUp(){ //
        pullUpMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); // wont turn if its at the limit
        pullUpMotor2.setPower(-1);
    }

    public void manualRightDown(){
        pullUpMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); // wont turn if its at the limit
        pullUpMotor2.setPower(1);
    }

    public void stopLeft(){
        pullUpMotor1.setPower(0);
        pullUpMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void stopRight(){
        pullUpMotor2.setPower(0);
        pullUpMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
