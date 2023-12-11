package org.firstinspires.ftc.teamcode.utilities;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Slides {
    DcMotor slidesMotorL;
    DcMotor slidesMotorR; //i hope there are two motors

    int Lpos;
    int Rpos;
    int state;

    // i guess we can add different height presets if we want to but maybe we should just do it manually
    int dropHeight = 800; // this is a random value and it sounds right to me

    public Slides(HardwareMap hmap) {
        this.slidesMotorL = hmap.dcMotor.get(CONFIG.slidesMotorL);
        this.slidesMotorR = hmap.dcMotor.get(CONFIG.slidesMotorR);
        setInit();
    }

    public void setInit() {
        // makes it so the motor is not loose when power is 0
        slidesMotorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slidesMotorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slidesMotorL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slidesMotorR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slidesMotorL.setZeroPowerBehavior(BRAKE);
        slidesMotorR.setZeroPowerBehavior(BRAKE);
    }

    public int[] getPosition(){
        return new int[] {slidesMotorL.getCurrentPosition(), slidesMotorR.getCurrentPosition()};
    }

    public boolean isBusy1(){return slidesMotorL.isBusy();}

    public boolean isBusy2(){
        return slidesMotorR.isBusy();
    }

    public void stop(){ // sets power to 0 - everything stops
        slidesMotorL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slidesMotorR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setPower(0);
    }

    public void setPower(double power) {
        slidesMotorL.setPower(power);
        slidesMotorR.setPower(power);
    }

    public void setTargetPosition(int position){
        slidesMotorL.setTargetPosition(position);
        slidesMotorR.setTargetPosition(position);
    }

    public void runToPosition(){
        slidesMotorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slidesMotorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setPower(0.9);
    }
}
