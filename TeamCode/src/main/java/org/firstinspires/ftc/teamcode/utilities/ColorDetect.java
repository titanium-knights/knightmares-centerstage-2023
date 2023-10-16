package org.firstinspires.ftc.teamcode.utilities;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import android.graphics.Color;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class ColorDetect {
    ColorSensor colorSensor;

    public ColorDetect(HardwareMap hMap) {
        this.colorSensor = hMap.get(ColorSensor.class, "color");
    }

    public int[] readRGB() {
        return new int[] {this.colorSensor.red(), this.colorSensor.green(), this.colorSensor.blue()};
    }

    /**
     * @return Float array [hue, saturation, brightness]
     */
    public float[] readHSV() {
        int[] rgb = this.readRGB();
        float[] hsv = new float[3];
        Color.RGBToHSV(rgb[0], rgb[1], rgb[2], hsv);
        return hsv;
    }

    /**
     *  @return a String "red", "white", or "blue" or something
     */
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