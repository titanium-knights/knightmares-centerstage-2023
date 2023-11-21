package org.firstinspires.ftc.teamcode.pipelines;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class EdgePipeline extends OpenCvPipeline {

    private static int MAX_LOW_THRESHOLD = 100;
    private static int RATIO = 7;
    private static int KERNEL_SIZE = 3;
    private static Size BLUR_SIZE = new Size(20,20);
    private static int lowThresh = 9;
    private Mat srcBlur = new Mat();
    private Mat detectedEdges = new Mat();
    private Mat dst = new Mat();

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.blur(input, srcBlur, BLUR_SIZE);
        Imgproc.Canny(srcBlur, detectedEdges, lowThresh, lowThresh * RATIO, KERNEL_SIZE, false);
        dst = new Mat(input.size(), CvType.CV_8UC3, Scalar.all(0));
        input.copyTo(dst, detectedEdges);

        return detectedEdges;
    }

    @Override
    public void init(Mat firstFrame) {
        System.out.println("Processing requested");
    }
}
