package org.firstinspires.ftc.teamcode.utilities;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static java.lang.Double.max;
import static java.lang.Double.min;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    DcMotor intakeMotor;

    public static double POWER = 0.3;
    public static double REVERSE_POWER = -0.3;

    public Intake(HardwareMap hmap) {
        this.intakeMotor = hmap.dcMotor.get(CONFIG.intake);
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
}
