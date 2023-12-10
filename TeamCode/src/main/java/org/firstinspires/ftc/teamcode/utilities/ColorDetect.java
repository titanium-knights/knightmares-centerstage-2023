package org.firstinspires.ftc.teamcode.utilities;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import android.graphics.Color;

import com.acmerobotics.dashboard.config.Config;
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
     *  @return a String "red", "blue", or "gray" (mat color)
     *  Telemetry prints the color detected and hsv value array
     */
    public String readColor() {
        float[] hsv = this.readHSV();
        float hue = hsv[0];
        float saturation = hsv[1];
        String color = "";

        // This color setting can and should be adjusted with testing.
        if (hue > 120 && hue < 300) {
            color = "blue";
        } else {
            color = "yellow";
        }
        if (saturation < 25) {
            color = "gray";
        }

        telemetry.addLine("Color detected: " + color + "(hsv: " + hsv + ")");
        telemetry.update();
        return color;
    }
}