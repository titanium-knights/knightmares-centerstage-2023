package org.firstinspires.ftc.teamcode.utilities;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class PullUp {
    //DANIEL comment: For this, we don't really care about degrees so, we deal with
    //everything in encoder ticks or number of rotations
    DcMotor pullUpMotor1;
    DcMotor pullUpMotor2;
    public static double Encoder_Ticks = 537.7;

    public static int topHeight = -100; // 24 * Encoder_Ticks

    public PullUp(HardwareMap hmap) {
        this.pullUpMotor1 = hmap.dcMotor.get(CONFIG.pullUpMotor1);
        this.pullUpMotor2 = hmap.dcMotor.get(CONFIG.pullUpMotor2);
        setInit();
    }

    public void setInit() {
         // makes it so the motor is not loose when power is 0
        pullUpMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pullUpMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pullUpMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pullUpMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pullUpMotor1.setZeroPowerBehavior(BRAKE);
        pullUpMotor2.setZeroPowerBehavior(BRAKE);
    }

    public int[] getPosition(){
        return new int[] {pullUpMotor1.getCurrentPosition(), pullUpMotor2.getCurrentPosition()};
    }

    public boolean isBusy1(){
        return pullUpMotor1.isBusy();
    }

    public boolean isBusy2(){
        return pullUpMotor2.isBusy();
    }

    public void stop(){ // sets power to 0 - everything stops
        pullUpMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pullUpMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setPower(0);
    }

    public void setPower(double power) {
        pullUpMotor1.setPower(power);
        pullUpMotor2.setPower(power);
    }

    public void setTargetPosition(int position){
        pullUpMotor1.setTargetPosition(position);
        pullUpMotor2.setTargetPosition(position);
    }

    public void reachUp() {
        setTargetPosition(topHeight);
        pullUpMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pullUpMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // run to position is always in presets or else itll be jittery
        setPower(0.9);
    }

    public void liftUp() {
        setTargetPosition(0);
        pullUpMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pullUpMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // run to position is always in presets or else itll be jittery
        setPower(0.9);
    }

    // pullUpMotor1 and 2 are reversed. If you want it to go up, power will be negative. If you want it to go down, power will be positive.
    public void manualRightDown(){
        pullUpMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pullUpMotor2.setPower(-1);
    }

    public void manualRightUp(){
        pullUpMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pullUpMotor2.setPower(1);
    }

    public void manualLeftUp(){
        pullUpMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pullUpMotor1.setPower(1);
    }

    public void manualLeftDown(){
        pullUpMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pullUpMotor1.setPower(-1);
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
