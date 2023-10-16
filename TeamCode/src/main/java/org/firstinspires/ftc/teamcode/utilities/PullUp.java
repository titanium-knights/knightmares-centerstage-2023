package org.firstinspires.ftc.teamcode.utilities;

import static java.lang.Double.max;
import static java.lang.Double.min;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class PullUp {
    public DcMotor pullUpMotor1;
    public DcMotor pullUpMotor2;
    public static double Encoder_Ticks = 537.7;
    public static double PULLUP_POWER_MULTIPLIER = .9; // so it doesnt turn too fast
    public static double desiredHeight = 0.1;

    // TODO: check what the actual limits are
    public static double MAX_LIMIT = 5; // so it doesnt go too far down
    public static double MIN_LIMIT = 0; // stops it from going further down if we use manual lift

    public PullUp(HardwareMap hmap) {
        this.pullUpMotor = hmap.dcMotor.get(CONFIG.pullUpMotor);
    }

    public void setInit() {
        pullUpMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // makes it so the motor is not like loose
        pullUpMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pullUpMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pullUpMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // makes it so the motor is not like loose
        pullUpMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pullUpMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void stop(){ // sets power to 0 everything stops
        pullUpMotor1.setPower(0); // refers to line 13
        pullUpMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pullUpMotor2.setPower(0); // refers to line 13
        pullUpMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public boolean checkLimits(){
        double desiredHeight = getPosition();
        if (desiredHeight < MIN_LIMIT) {stop(); return true;}
        if (desiredHeight > MAX_LIMIT) {stop(); return true;}
        return false;
    }

    public void setPower(boolean dir) {
        double power = 0.4;
        if (dir) pullUpMotor1.setPower(power * PULLUP_POWER_MULTIPLIER);
        else pullUpMotor1.setPower(-power * PULLUP_POWER_MULTIPLIER);
        if (dir) pullUpMotor2.setPower(power * PULLUP_POWER_MULTIPLIER);
        else pullUpMotor2.setPower(-power * PULLUP_POWER_MULTIPLIER);
    }

//    public void setPower(boolean dir) {
//        //false = to pick up, true = to drop
//        double power = 0.4;
//        if (dir) pullUpMotor.setPower(power * PULLUP_POWER_MULTIPLIER);
//        else pullUpMotor.setPower(-power * PULLUP_POWER_MULTIPLIER);
//    }

    public double getPosition(){
        return (pullUpMotor1.getCurrentPosition()*360/Encoder_Ticks);
    } // gets the encoder position in degrees based on encoder ticks

    public void liftUp() {
        // converts angle into encoder ticks and then runs to position
        pullUpMotor1.setTargetPosition((int) (Encoder_Ticks*desiredHeight/360));
        pullUpMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pullUpMotor2.setTargetPosition((int) (Encoder_Ticks*desiredHeight/360));
        pullUpMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //with run to position always positive power
        // run to position is always in presets or else itll be jittery
        setPower(true);

    }

    public void liftDown() {
        // converts angle into encoder ticks and then runs to position
        pullUpMotor1.setTargetPosition((int) (Encoder_Ticks*-desiredHeight/360));
        pullUpMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pullUpMotor2.setTargetPosition((int) (Encoder_Ticks*-desiredHeight/360));
        pullUpMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //with run to position always positive power
        // run to position is always in presets or else itll be jittery
        setPower(true);
    }




}
