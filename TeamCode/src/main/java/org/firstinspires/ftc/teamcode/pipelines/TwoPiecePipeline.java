package org.firstinspires.ftc.teamcode.pipelines;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;

@Config
public class TwoPiecePipeline extends OpenCvPipeline {

    private final Telemetry telemetry;

    public static Scalar RED_HIGH = new Scalar(255, 0, 0);
    public static Scalar BLUE_HIGH = new Scalar(0, 0, 255);
    public static Scalar RED_LOW = new Scalar(200, 0, 0);
    public static Scalar BLUE_LOW = new Scalar(0, 0, 200);


    public static double min_blue_score = 20;
    public static double min_red_score = 20;

    // weights for scoring

    /**
     * <p>
     * the following explanation will assume that this pipeline is running
     * for red, although it will work the same way for either color. WANT
     * indicates red in this case, and UNWANT will indicate blue. If the
     * pipeline were running for blue, WANT => blueness, and UNWANT => redness.
     * </p> <br>
     * <p>
     * The weights below can be used to change how scoring occurs. For example,
     * WANT_WEIGHT = 1 ensures that 100% of the redness is factored into the score
     * It is likely that you should not need to adjust the WANT weight.
     * </p><br/><p>
     * UNWANT_WEIGHT = 0.35 implies that 35% of the blue value will be subtracted
     * from the score. Raise this value if you find that colors with a higher
     * blue value are also triggering the red pipeline (i.e., purples, whites, etc.)
     * You can also lower this value if you find that scores are going too low in areas
     * that should be red. If all scores are going to low values, then you can just
     * lower the minimum_red_score and minimum_blue_score
     * </p><br/><p>
     * GREEN_WEIGHT = 0.15 means that 15% of the green value will be subtracted from
     * the score. This value can probably be changed a bit more liberally than the
     * UNWANT_WEIGHT. Raise it if you find that colors with a mix of red and green
     * (e.g. white, brown, look up "rgb color picker" on google) are triggering the
     * system with a higher score. Lower it if scores are becoming abnormally low for
     * some sections which visually appear red.
     * </p>
     */

    public static double WANT_WEIGHT = 1.0;
    public static double UNWANT_WEIGHT = 0.35;
    public static double GREEN_WEIGHT = 0.15;

    // store the color we're looking for (i.e. team color) as its index in the channels array
    public int colorNum;

    // top left and bottom right points for the three rectangles we will crop into
    ArrayList<Point> rect_points = new ArrayList<>();

    // the three places we might end up
    public enum Locations {
        TWO, THREE, ONE
    }

    // the location we actually want to go to
    public volatile Locations location = Locations.ONE;

    // for the 2 sections
    private final Mat[] croppedSections = new Mat[2];

    //instantiate the object with telemetry and a color, which defaults to blue
    // this is the constructor that will be used by easy openCV sim
    public TwoPiecePipeline(Telemetry telemetry) {
        this(telemetry, "blue");
    }

    public TwoPiecePipeline(Telemetry telemetry, String color) {
        this.telemetry = telemetry;

        if (color.equalsIgnoreCase("red")) this.colorNum = 0;
        else if (color.equalsIgnoreCase("blue")) this.colorNum = 2;
        else {
            this.colorNum = 0;
            telemetry.addLine("Invalid color, defaulting to red");
            telemetry.update();
        }
    }

    public Locations getLocation() {
        telemetry.addLine(location.name());
        return location;
    }

    public int getScore(double[] rgb) {
        int want = (int) (rgb[colorNum] * WANT_WEIGHT);
        int unWanted = (int) (rgb[getOppColor()] * UNWANT_WEIGHT);
        int green = (int) (rgb[1] * GREEN_WEIGHT);

        return want - green - unWanted;
    }

    @Override
    public Mat processFrame(Mat input) {
        int maxIndex = 0;
        double[] scores = new double[2];

        for (int i=0;i<croppedSections.length;i++) {
            Mat section = croppedSections[i];
            double[] avg = Core.mean(section).val;

            scores[i] = getScore(avg);
        }

        for (int i=0;i<scores.length;i++) {
            telemetry.addData("Score for section "+i, scores[i]);
            telemetry.update();
            if (scores[i] > scores[maxIndex]) maxIndex = i;
        }

        if (scores[maxIndex] < getMinScore()) {
            telemetry.addLine("min score = "+getMinScore());
            maxIndex = 2;
        }
        location = Locations.values()[maxIndex];

        telemetry.addData("Chose position: ", location.name());
        telemetry.update();


        final int THICKNESS = 7;

//        Imgproc.rectangle(
//                input,
//                rect_points.get(maxIndex * 2),
//                rect_points.get(2 * maxIndex + 1),
//                new Scalar(35, 123, 113),
//                THICKNESS);

        Imgproc.putText(
                input,
                location.name(),
                new Point(50, 200),
                3,
                12,
                new Scalar(36,105,39),
                67
        );
        return input;
    }

    @Override
    public void init(Mat input) {
        // Rectangle coordinates (upper left [A] and bottom right [B]) for the three cropped sections
        //this code just currently splits into three equal sections
        rect_points.add(new Point(0, 0));
        rect_points.add(new Point(input.cols()/2.0, input.rows()));
        rect_points.add(new Point(input.cols()/2.0, 0));
//        rect_points.add(new Point(2*input.cols()/3.0, input.rows()));
//        rect_points.add(new Point(2*input.cols()/3.0, 0));
        rect_points.add(new Point(input.cols(), input.rows()));

        // initialize the dos cropped sections
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

    private double getMinScore() {
        return colorNum == 0 ? min_red_score : min_blue_score;
    }
}
