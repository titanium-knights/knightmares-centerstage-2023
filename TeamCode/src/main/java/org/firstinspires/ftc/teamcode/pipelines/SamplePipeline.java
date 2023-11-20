package org.firstinspires.ftc.teamcode.pipelines;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;

public class SamplePipeline extends OpenCvPipeline {
    Mat YCrCb = new Mat();
    Mat Y = new Mat();
    Mat RectA_Y = new Mat();
    int avg;
    int avgA;

    static final int STREAM_WIDTH = 1280;
    static final int STREAM_HEIGHT = 720;

    static final int WidthRectA = 130;
    static final int HeightRectA = 110;

    static final Point RectATopLeftAnchor = new Point(
            (STREAM_WIDTH - WidthRectA)/2 + 300, //modify this number to move the rect
            (STREAM_HEIGHT - HeightRectA)/2 - 100 //modify this number to move the rect
    );

    void inputToY(Mat input) {
        Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
        ArrayList<Mat> yCrCbChannels = new ArrayList<Mat>(3);
        Core.split(YCrCb, yCrCbChannels);
        Y = yCrCbChannels.get(0);
    }

    @Override
    public void init(Mat firstFrame){
        inputToY(firstFrame);
    }

    @Override
    public Mat processFrame(Mat input) {
        inputToY(input);
        System.out.println("Processing requested");
        avg = (int) Core.mean(Y).val[0];
        YCrCb.release();
        Y.release();
        return input;
    }

    public int getAnalysis() {
        return avg;
    }


}
