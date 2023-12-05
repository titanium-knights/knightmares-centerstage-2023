package org.firstinspires.ftc.teamcode.utilities;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static java.lang.Double.max;
import static java.lang.Double.min;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    DcMotor intakeMotor;
    Servo intakeRotator;

    public static double POWER = 0.5;
    public static double REVERSE_POWER = -0.8;

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

    public void setZero() {
        intakeRotator.setPosition(0.5);
    }

    public void setUp() {
        intakeRotator.setPosition(0.0);
    }
}
