package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.HashMap;
import java.util.Objects;

public class MecanumDrive {

    public MecanumDrive(HardwareMap hmap) {
        fl = hmap.get(DcMotor.class, CONFIG.FRONT_LEFT);
        fr = hmap.get(DcMotor.class, CONFIG.FRONT_RIGHT);
        bl = hmap.get(DcMotor.class, CONFIG.BACK_LEFT);
        br = hmap.get(DcMotor.class, CONFIG.BACK_RIGHT);

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.FORWARD);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);

        directions.put(fl, new double[]{1, 1});
        directions.put(fr, new double[]{1, -1});
        directions.put(bl, new double[]{1, -1});
        directions.put(br, new double[]{1, 1});
    }

    public static DcMotor fl, fr, bl, br;

    public static HashMap<DcMotor, double[]> directions = new HashMap<>();

    /**
     * @param x: a positive value will move the robot left, and a negative value will move the robot right
     * @param y: a positive value will move the robot forward, and a negative value will move the robot backward
     * @param turn: a positive value will turn the robot counterclockwise, and a negative value will turn the robot clockwise
     */
    public void move(double x, double y, double turn) {

        // dot of fl and br
        double dot_fl = dot(Objects.requireNonNull(directions.get(fl)), new double[]{x, y}) + turn;
        double dot_fr = (dot(Objects.requireNonNull(directions.get(fr)), new double[]{x, y}) + turn);
        double dot_bl = dot(Objects.requireNonNull(directions.get(bl)), new double[]{x, y}) - turn;
        double dot_br = dot(Objects.requireNonNull(directions.get(br)), new double[]{x, y}) - turn;

        double max = Math.max(1, Math.max(Math.max(Math.abs(dot_fl), Math.abs(dot_fr)), Math.max(Math.abs(dot_bl), Math.abs(dot_br))));
        fl.setPower(dot_fl / max);
        br.setPower(dot_br / max);

        fr.setPower(dot_fr / max);
        bl.setPower(dot_bl / max);
    }

    // Each double[] will be a direction vector of length two
    public double dot(double[] a, double[] b) {
        return a[0] * b[0] + a[1] * b[1];
    }


}
