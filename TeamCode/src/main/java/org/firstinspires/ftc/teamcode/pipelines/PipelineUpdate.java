package org.firstinspires.ftc.teamcode.pipelines;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;

public class PipelineUpdate extends OpenCvPipeline {
    Mat YCrCb = new Mat();
    Mat Cr = new Mat();
    Mat Cb = new Mat();
    Mat RectA_Y = new Mat();
    int avg;
    int avgBlue;

    static final int STREAM_WIDTH = 1280;
    static final int STREAM_HEIGHT = 720;
    ArrayList<Mat> yCrCbChannels = new ArrayList<Mat>(3);
    static final int WidthRectA = 130;
    static final int HeightRectA = 110;

    static final Point RectATopLeftAnchor = new Point(
            (STREAM_WIDTH - WidthRectA)/3.0 + 300, //modify this number to move the rect
            (STREAM_HEIGHT - HeightRectA)/3.0 - 100 //modify this number to move the rect
    );

    void inputToY(Mat input) {
        Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
        ArrayList<Mat> yCrCbChannels = new ArrayList<Mat>(3);
        Core.split(YCrCb, yCrCbChannels);
        Cr = yCrCbChannels.get(1);
        Cb = yCrCbChannels.get(2);
    }

    @Override
    public void init(Mat firstFrame){
        inputToY(firstFrame);
    }

    @Override
    public Mat processFrame(Mat input) {
        inputToY(input);
        System.out.println("Processing requested");
        avg = (int) Core.mean(Cr).val[0];
        avgBlue = (int) Core.mean(Cb).val[0];
        YCrCb.release();
        Cr.release();
        Cb.release();



        return input;
    }

    public int getAnalysis() {
        return avg;
    }


}
