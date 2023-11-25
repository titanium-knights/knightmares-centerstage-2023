package org.firstinspires.ftc.teamcode.utilities;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static java.lang.Double.max;
import static java.lang.Double.min;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    DcMoter intakeMotor;

    public static double POWER = 0.95;

    public Intake(HardwareMap hmap) {
        this.intakeMotor = hmap.dcMotor.get(CONFIG.liftMotor);
        setInit();
    }

    public void setInit() {
        // makes it so the motor is not loose
        intakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setZeroPowerBehavior(BRAKE);
    }

    public void runIntake() {
        intakeMotor.setPower(POWER);
    }
}
