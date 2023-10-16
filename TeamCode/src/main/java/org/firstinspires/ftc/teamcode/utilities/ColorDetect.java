package org.firstinspires.ftc.teamcode.utilities;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import android.graphics.Color;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
@Config
public class ColorDetect {
    ColorSensor colorSensor;

    public ColorDetect(ColorSensor colorSensor) {
        this.colorSensor = colorSensor;
    }

    public int[] readRGB() {
        return new int[] {this.colorSensor.red(), this.colorSensor.green(), this.colorSensor.blue()};
    }

    public float[] readHSV() {
        int[] rgb = this.readRGB();
        float[] hsv = new float[3];
        Color.RGBToHSV(rgb[0], rgb[1], rgb[2], hsv);
        return hsv;
    }

    public String readColor() {
        float[] hsv = this.readHSV();
        float hue = hsv[0];
        if(hue<=127) {
            return "yellow";
        } else if(hue<=160) {
            return "pink";
        } else {
            return "blue";
        }
    }
}