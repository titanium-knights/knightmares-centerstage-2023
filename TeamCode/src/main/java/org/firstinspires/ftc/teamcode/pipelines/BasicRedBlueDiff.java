package org.firstinspires.ftc.teamcode.pipelines;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;

public class BasicRedBlueDiff extends OpenCvPipeline {

    private final Telemetry telemetry;

    //tentative RGB values for RED and BLUE
    public static Scalar RED_HIGH = new Scalar(255, 0, 0);
    public static Scalar BLUE_HIGH = new Scalar(0, 0, 255);
    public static Scalar RED_LOW = new Scalar(200, 0, 0);
    public static Scalar BLUE_LOW = new Scalar(0, 0, 200);

    // store the color we're looking for (i.e. team color) as its index in the channels array
    public int colorNum;

    // top left and bottom right points for the three rectangles we will crop into
    ArrayList<Point> rect_points = new ArrayList<>();

    // the three places we might end up
    public enum Locations {
        ONE, TWO, THREE
    }

    // the location we actually want to go to
    public volatile Locations location = Locations.ONE;

    // for the 3 sections
    private final Mat[] croppedSections = new Mat[3];

    //instantiate the object with telemetry and a color, which defaults to RED
    // this is the constructor that will be used by easy openCV sim
    public BasicRedBlueDiff(Telemetry telemetry) {
        this(telemetry, "blue");
    }

    public BasicRedBlueDiff(Telemetry telemetry, String color) {
        this.telemetry = telemetry;

        if (color.equalsIgnoreCase("red")) this.colorNum = 0;
        else if (color.equalsIgnoreCase("blue")) this.colorNum = 2;
        else {
            this.colorNum = 0;
            telemetry.addLine("Invalid color, defaulting to red");
        }
    }

    public Locations getLocation() {
        telemetry.addLine(location.name());
        return location;
    }

    @Override
    public Mat processFrame(Mat input) {
        int maxIndex = 0;
        double[] scores = new double[3];

        for (int i=0;i<croppedSections.length;i++) {
            Mat section = croppedSections[i];
            double[] avg = Core.mean(section).val;
            int want = (int) avg[colorNum];
            int unWanted = (int) ((avg[getOppColor()]/2 + avg[1])/1.5);
            scores[i] = want - unWanted;
        }

        for (int i=0;i<scores.length;i++) {
            if (scores[i] > scores[maxIndex]) maxIndex = i;
        }

        location = Locations.values()[maxIndex];
        telemetry.addData("Chose position: ", maxIndex);

        final int THICKNESS = 7;
        Imgproc.rectangle(
                input,
                rect_points.get(maxIndex * 2),
                rect_points.get(2 * maxIndex + 1),
                new Scalar(135, 23, 113),
                THICKNESS);

        return input;
    }

    @Override
    public void init(Mat input) {
        // Rectangle coordinates (upper left [A] and bottom right [B]) for the three cropped sections
        //this code just currently splits into three equal sections
        rect_points.add(new Point(0, 0));
        rect_points.add(new Point(input.cols()/3.0, input.rows()));
        rect_points.add(new Point(input.cols()/3.0, 0));
        rect_points.add(new Point(2*input.cols()/3.0, input.rows()));
        rect_points.add(new Point(2*input.cols()/3.0, 0));
        rect_points.add(new Point(input.cols(), input.rows()));

        // initialize the three cropped sections
        for (int i = 0; i < rect_points.size(); i += 2) {
            Point A = rect_points.get(i);
            Point B = rect_points.get(i + 1);

            croppedSections[i/2] = input.submat(new Rect(A, B));
        }
    }

    /**
     * @deprecated older version of the code referenced this, may use again do not delete
     */
    public Scalar[] getColorRange(int channelNum) {
        if (channelNum == 0) return new Scalar[] {RED_LOW, RED_HIGH};
        else if (channelNum == 2) return new Scalar[] {BLUE_LOW, BLUE_HIGH};
        else return new Scalar[] {new Scalar(0, 0, 0), new Scalar(0, 0, 0)};
    }

    /**
     * me when im tired of retyping this ternary statement over and over again
     * */
    public int getOppColor() {
        return colorNum == 0 ? 2 : 0;
    }
}
