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
import java.util.Locale;

public class BasicRedBlueDiff extends OpenCvPipeline {

    private final Telemetry telemetry;

    //tentative RGB values for RED and BLUE
    private static final Scalar RED = new Scalar(255, 0, 0);
    private static final Scalar BLUE = new Scalar(0, 0, 255);

    // store the color we're looking for (i.e. team color) and its index
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
    public volatile Locations location = Locations.ONE;

    // working variables for the 3 sections and the colored channel extraction
    private final Mat[] croppedSections = new Mat[3];
    private final Mat colored = new Mat();


    //instantiate the object with telemetry and a color, which defaults to RED
    public BasicRedBlueDiff(Telemetry telemetry) {
        this(telemetry, "red");
    }

    public BasicRedBlueDiff(Telemetry telemetry, String color) {
        this.telemetry = telemetry;

        if (color.equalsIgnoreCase("red")) this.color = RED;
        else if (color.equalsIgnoreCase("blue")) this.color = BLUE;
        else {
            this.color = RED;
            telemetry.addLine("Invalid color, defaulting to red");
        }

        // store which RGB channel we're looking at
        colorNum = (this.color.equals(RED)) ? 0 : 2;
    }

    /**
     * @return likelihood that the given section is the team color
     */
    private int processSection(Mat input) {
        final int MARGIN = 70;

        // loop over croppedSection in bigger chunks (not single px)
        // if average redness/blueness is above a margin, return the red/blue value
        // otherwise, return 0

        int colorSum = 0;
        int count = 0;
        for (int x = 0; x < input.rows()-10; x += 10) {
            for (int y = 0; y < input.cols()-10; y += 10) {
                ++count;
                double sum = 0;
                for (int i=0;i<10;i++) {
                    for (int j=0;j<10;j++) {
                        double s = input.get((int) x + i, (int) y + j)[0];
                        if (s > MARGIN) sum += s;
                    }
                }
                if (sum > MARGIN) colorSum += sum;
            }
        }

        // avoid divide by zero
        if (count == 0) return 0;

        return colorSum/count;
    }

    public Locations getLocation() {
        telemetry.addLine(location.name());
        return location;
    }

    @Override
    public Mat processFrame(Mat input) {
        int max = -1;
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

        // draw a rectangle around the section with the highest color value
        final int THICKNESS = 7;
        if (maxindex > -1) {
            Imgproc.rectangle(
                    input,
                    rect_points.get(maxindex * 2),
                    rect_points.get(2 * maxindex + 1),
                    new Scalar(125, 182, 73),
                    THICKNESS);
        }

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

        Core.extractChannel(input, colored, colorNum);

        // initialize the three cropped sections
        for (int i = 0; i < rect_points.size(); i += 2) {
            Point A = rect_points.get(i);
            Point B = rect_points.get(i + 1);

            croppedSections[i/2] = colored.submat(new Rect(A, B));
        }
    }
}
