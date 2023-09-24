package org.firstinspires.ftc.teamcode.pipelines;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;

public class BasicRedBlueDiff extends OpenCvPipeline {

    private final Telemetry telemetry;

    //tentative RGB values for RED and BLUE
    private static final Scalar RED = new Scalar(255, 0, 0);
    private static final Scalar BLUE = new Scalar(0, 0, 255);

    // store the color we're looking for (i.e. team color)
    private final Scalar color;

    // top left and bottom right points for the three rectangles we will crop into
    // will be defined in init()
    ArrayList<Point> rect_points = new ArrayList<>();

    // the three places we might end up
    //TODO: figure out corresponding rr coordinates
    public enum locations {
        ONE, TWO, THREE
    }

    // the location we actually want to go to
    public locations location;


    /**
     * instantiate the object with telemetry and a color, which defaults to RED
     */
    public BasicRedBlueDiff(Telemetry telemetry) {
        this.telemetry = telemetry;
        this.color = RED;
    }

    public BasicRedBlueDiff(Telemetry telemetry, String color) {
        if (color.equals("red")) this.color = RED;
        else if (color.equals("blue")) this.color = BLUE;
        else this.color = RED;

        this.telemetry = telemetry;
    }

    /**
     * @param input the input image
     * @return the input image in the appropriate color channel
     */
    Mat getColor(Mat input) {
        Mat coloredSection = new Mat();

        // convert a Scalar into a RGB channel number
        // i.e. since rgb -> 0,1,2: RED = 0, BLUE = 2
        int colorToNum = (this.color.equals(RED)) ? 0 : 2;

        Core.extractChannel(input, coloredSection, colorToNum);

        return coloredSection;
    }

    //return the likelihood that the section is the team color
    //TODO
    public int processSection(Mat input, Point A, Point B) {
        // get the cropped section in the appropriate color channel
        Mat croppedSection = getColor(input).submat(new Rect(A, B));

        // loop over croppedSection in bigger chunks (not single px)
        // if average redness/blueness is above a margin, return the red/blue value
        // otherwise, return 0
        return 0;
    }

    @Override
    public Mat processFrame(Mat input) {
        for (int i = 0; i < rect_points.size(); i += 2) {
            Point A = rect_points.get(i);
            Point B = rect_points.get(i + 1);

            int colorValue = processSection(input, A, B);

            if (colorValue != 0) {
                telemetry.addData("Color", colorValue);
                telemetry.update();
            }
        }
        return new Mat();
    }

    @Override
    public void init(Mat input) {

        // Rectangle coordinates (upper left [A] and bottom right [B]) for the three cropped sections
        // R1 is the leftmost section, R2 is the middle, and R3 is the rightmost
        //TODO: may require update based on camera resolution/positioning
        //TODO: if using constants, move declaration to global scope
        //this code just currently splits into three equal sections
        rect_points.add(new Point(0, 0));
        rect_points.add(new Point(input.rows()/3.0, input.cols()));
        rect_points.add(new Point(input.rows()/3.0, 0));
        rect_points.add(new Point(2*input.rows()/3.0, input.cols()));
        rect_points.add(new Point(2*input.rows()/3.0, 0));
        rect_points.add(new Point(input.rows(), input.cols()));

//        Mat coloredChannel = getColor(input);
    }
}
