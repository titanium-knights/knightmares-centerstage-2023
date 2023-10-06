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
import java.util.Arrays;

public class BasicRedBlueDiff extends OpenCvPipeline {

    private final Telemetry telemetry;

    //tentative RGB values for RED and BLUE
    private static final Scalar RED = new Scalar(255, 0, 0);
    private static final Scalar BLUE = new Scalar(0, 0, 255);

    // store the color we're looking for (i.e. team color)
    private final Scalar color;
    private final int colorNum;

    // top left and bottom right points for the three rectangles we will crop into
    // will be defined in init()
    ArrayList<Point> rect_points = new ArrayList<>();

    // the three places we might end up
    public enum Locations {
        ONE, TWO, THREE
    }

    // the location we actually want to go to
    public volatile Locations location;

    // working variables
    private final Mat[] croppedSections = new Mat[3];
    private final Mat[] frames = new Mat[3];
    private final Mat colored = new Mat();

    /**
     * instantiate the object with telemetry and a color, which defaults to RED
     */
    public BasicRedBlueDiff(Telemetry telemetry) {
        this(telemetry, "red");
    }

    public BasicRedBlueDiff(Telemetry telemetry, String color) {
        if (color.equals("red")) this.color = RED;
        else if (color.equals("blue")) this.color = BLUE;
        else this.color = RED;

        colorNum = (this.color.equals(RED)) ? 0 : 2;

        this.telemetry = telemetry;
    }

    //return the likelihood that the section is the team color
    private int processSection(Mat input) {
        final int MARGIN = 10;

        // loop over croppedSection in bigger chunks (not single px)
        // if average redness/blueness is above a margin, return the red/blue value
        // otherwise, return 0

        int colorSum = 0;
        int count = 0; // I could probably calculate this from Point values and resolution but am lazy
        for (double x = 0; x < input.rows(); x += 10) {
            for (double y = 0; y < input.cols(); y += 10) {
                ++count;
                int sum = 0;
                for (int i=0;i<10;i++) {
                    for (int j=0;j<10;j++) {
                        sum += input.get((int) x + i, (int) y + j)[0]; //TODO error
                    }
                }
                if (sum > MARGIN) colorSum += sum;
            }
        }

        Imgproc.cvtColor(input, input, Imgproc.COLOR_RGB2GRAY);

        // avoid divide by zero
        if (count == 0) return 0;

        return colorSum/count; // mean
    }

    public Locations getLocation() {
        return location;
    }

    @Override
    public Mat processFrame(Mat input) {
        int max = 0;
        int maxindex = -1;

        //map croppedSections to color values and then to an array
        int[] colorValues = Arrays.stream(croppedSections)
            .map(this::processSection)
            .mapToInt(Integer::intValue)
            .toArray();

        for (int i=0;i<colorValues.length;i++) {
            telemetry.addData("Color " + i, colorValues[i]);
            telemetry.update();

            if (colorValues[i] > max) {
                max = colorValues[i];
                location = Locations.values()[i];
                maxindex = i;
            }
        }
        if (max != 0) return frames[maxindex];

        return input;
    }

    @Override
    public void init(Mat input) {

        // Rectangle coordinates (upper left [A] and bottom right [B]) for the three cropped sections
        // R1 is the leftmost section, R2 is the middle, and R3 is the rightmost
        //TODO: may require update based on camera resolution/positioning
        //TODO: if using constants, move declaration to global scope
        //this code just currently splits into three equal sections
        rect_points.add(new Point(0, 0));
        rect_points.add(new Point(input.cols()/3.0, input.rows()));
        rect_points.add(new Point(input.cols()/3.0, 0));
        rect_points.add(new Point(2*input.cols()/3.0, input.rows()));
        rect_points.add(new Point(2*input.cols()/3.0, 0));
        rect_points.add(new Point(input.cols(), input.rows()));

        Core.extractChannel(input, colored, colorNum);

        // initialize the three cropped sections
        for (int i = 0; i < rect_points.size(); i += 2) {
            Point A = rect_points.get(i);
            Point B = rect_points.get(i + 1);

            croppedSections[i/2] = colored.submat(new Rect(A, B));
        }
    }
}
