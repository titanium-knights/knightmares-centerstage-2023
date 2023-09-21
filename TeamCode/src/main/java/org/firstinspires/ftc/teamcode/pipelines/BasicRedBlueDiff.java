package org.firstinspires.ftc.teamcode.pipelines;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.HashMap;

public class BasicRedBlueDiff extends OpenCvPipeline {

    private final Telemetry telemetry;

    private static final Scalar RED = new Scalar(255, 0, 0);
    private static final Scalar BLUE = new Scalar(0, 0, 255);

    public enum locations {
        ONE, TWO, THREE
    }

    //todo

    public BasicRedBlueDiff(Telemetry telemetry) {
        this.telemetry = telemetry;
        this.color = RED;
    }

    public BasicRedBlueDiff(Telemetry telemetry, String color) {
        if (color.equals("red")) this.color = RED;
        else if (color.equals("blue")) this.color = BLUE;

        this.telemetry = telemetry;
    }



    @Override
    public Mat processFrame(Mat input) {

    }

    @Override
    public void init(Mat input) {
        Mat redness = new Mat();
        Mat blueness = new Mat();

        //read Image to RGB
        Core.extractChannel(input, redness, 0);
        Core.extractChannel(input, blueness, 2);


    }
}
