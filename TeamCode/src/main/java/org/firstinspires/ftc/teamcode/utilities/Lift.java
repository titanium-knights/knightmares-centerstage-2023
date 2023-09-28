package org.firstinspires.ftc.teamcode.utilities;

public class Lift {
    public DcMotor lmr;

    public static double LIFT_POWER = .6;
    public static double LIFT_POWER_MULTIPLYER = .9;

    // since we are rotating the lift all the way around I don't know how these positions are
    // supposed to work?
    public static int OVER_MID_POSITION = 1250;
    public static int OVER_HIGH_POSITION = 1100;
    public static int HIGH_POSITION = 950;
    public static int MID_POSITION = 800;
    public static int LOW_POSITION = 650;
    public static int GROUND_POSITION = 50;

    // i have no clue what this is supposed to do
    public static double Y = 0;

    // change based on driver's preference
    public double LEFT_JOYSTICK_Y_Positive = 0;
    public double LEFT_JOYSTICK_Y_Negative = 0;

    //
    public static double SENSITIVITY = 0.05;
    public static double TOLERANCE = 20;

    public static double MULTIPLIER_DOWN = 0.4;
    public static double MULTIPLIER_UP = 0.6;
    public static double POWER_UP = 0.4;
    public static double POWER_DOWN = 0.3;

    public Lift(HardwareMap hmap) {
        this.lmr = hmap.dcMotor.get(CONFIG.liftMotorRight);
    }

    public void setPower(double power) {
        lmr.setPower(-power * LIFT_POWER_MULTIPLYER);
    }

    public int getPositionR() {
        return lmr.getCurrentPosition();
    }
}
