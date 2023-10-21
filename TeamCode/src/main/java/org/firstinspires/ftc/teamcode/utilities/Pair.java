package org.firstinspires.ftc.teamcode.utilities;

import static java.lang.Double.max;
import static java.lang.Double.min;
import static java.lang.Math.abs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

// This is a simple pair class. Ignore it.
public class Pair {
    private final double first;
    private final double second;

    public Pair(int first, int second){
        this.first = first;
        this.second = second;
    }
    public double first() {return first;}
    public double second() {return second;}
}
