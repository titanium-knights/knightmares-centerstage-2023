package org.firstinspires.ftc.teamcode.utilities;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    DcMotor intakeMotor;
    Servo intakeRotator;

    public static double POWER = 0.3;
    public static double REVERSE_POWER = -1.0;

    public Intake(HardwareMap hmap) {
        this.intakeMotor = hmap.dcMotor.get(CONFIG.intake);
        this.intakeRotator = hmap.servo.get(CONFIG.intakeRotator);
        setInit();
    }

    public void setInit() {
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setZeroPowerBehavior(BRAKE);
    }

    public void runIntake() {
        intakeMotor.setPower(POWER);
    }

    public void runReverse(){
        intakeMotor.setPower(REVERSE_POWER);
    }

    public void stop(){
        intakeMotor.setPower(0);
    }

    public void noPower() {
        intakeRotator.getController().pwmDisable();
    }


    public void setZero() {
        intakeRotator.setPosition(0.46);
    }

    public void setUp() {
        intakeRotator.setPosition(0.2);
    }

    public void setUpUp() {intakeRotator.setPosition(0.0);}
}
