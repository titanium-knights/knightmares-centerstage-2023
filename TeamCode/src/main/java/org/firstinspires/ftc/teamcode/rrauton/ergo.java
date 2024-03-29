package org.firstinspires.ftc.teamcode.rrauton;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.rr.drive.SampleMecanumDrive;

import org.firstinspires.ftc.teamcode.rr.drive.StandardTrackingWheelLocalizer;
import org.firstinspires.ftc.teamcode.utilities.Bay;
import org.firstinspires.ftc.teamcode.utilities.InitialVision;
import org.firstinspires.ftc.teamcode.utilities.Intake;
import org.firstinspires.ftc.teamcode.utilities.Stick;
import org.firstinspires.ftc.teamcode.utilities.Arm;

import java.util.ArrayList;

@Config
@Autonomous(name="ergo", group="Autonomous")
public class ergo extends LinearOpMode {

    public static double ANGLEONE = 0.0;
    public static double ANGLEDOS = 0;
    public static double ANGLETRES = 0;

    public static double STARTANGLE = 0;

    public SampleMecanumDrive drive;
    public Stick stick;
    public InitialVision vision;
    public Arm arm;
    public Intake intake;
    public Bay bay;
    public int rot = 76; // intended to be 90 but the turn overturns it
    //TODO etc. etc., and add to the createHardware method

    public void createHardware(HardwareMap hmap) {
        drive = new SampleMecanumDrive(hmap);
        stick = new Stick(hmap);
        vision = new InitialVision(hmap, telemetry, "blue"); //TODO: remember to change to red for blue side
        arm = new Arm(hmap);
        stick = new Stick(hardwareMap);
        intake = new Intake(hardwareMap);
        bay = new Bay(hardwareMap);
    }

    @Override
    public void runOpMode() {
        createHardware(hardwareMap);
        stick.lock();
        intake.setUpUp();
        arm.drivingPos();
        bay.setPick();
        waitForStart();

        //TODO check this value
        Pose2d startPose = new Pose2d(-35.5, 60, Math.toRadians(STARTANGLE));
        drive.setPoseEstimate(startPose);

        Trajectory toSpotTwo = drive.trajectoryBuilder(new Pose2d())
                .back(33)
                .addDisplacementMarker(this::dropPixel)
                .build();

        Trajectory backToDropPixel = drive.trajectoryBuilder(new Pose2d())
                .back(33)
                .build();

        Trajectory dropPixel = drive.trajectoryBuilder(new Pose2d())
                .back(8)
                .addDisplacementMarker(this::dropPixel)
                .build();
        Trajectory forwardFromPixel = drive.trajectoryBuilder(new Pose2d())
                .forward(8)
                .build();

        Trajectory backOne = drive.trajectoryBuilder(new Pose2d())
                .back(20)
                .build();

        Trajectory backOnee = drive.trajectoryBuilder(new Pose2d())
                .back(30)
                .build();
        Trajectory forwardOne = drive.trajectoryBuilder(new Pose2d())
                .forward(30)
                .build();
        Trajectory backThree = drive.trajectoryBuilder(new Pose2d())
                .back(82)
                .build();
        Trajectory rightOne = drive.trajectoryBuilder(new Pose2d()) // must be between 44 and 30
                        .strafeRight(35)
                                .build();
        Trajectory rightOneHalf = drive.trajectoryBuilder(new Pose2d())
                .strafeRight(44)
                .build();
        Trajectory leftOne = drive.trajectoryBuilder(new Pose2d())
                .strafeLeft(35)
                .build();
        Trajectory leftOneHalf = drive.trajectoryBuilder(new Pose2d())
                .strafeLeft(44)
                .build();
        Trajectory toPaint = drive.trajectoryBuilder(new Pose2d())
                        .back(20)
                                .addDisplacementMarker(this::paintPixel)
                                        .addDisplacementMarker(this::returnInit)
                                                .build();
        Trajectory forwardFromToPaint = drive.trajectoryBuilder(new Pose2d())
                        .forward(5)
                                .build();
        Trajectory rightOneCloseBackDrop = drive.trajectoryBuilder(new Pose2d())
                .addDisplacementMarker(this::liftArm)
                .strafeRight(35)
                .build();

        Trajectory leftOneCloseBackBackDrop = drive.trajectoryBuilder(new Pose2d())
                .addDisplacementMarker(this::liftArm)
                .strafeLeft(35)
                .build();

        Trajectory backOneCloseBackDrop = drive.trajectoryBuilder(new Pose2d())
                        .back(20)
                                .addDisplacementMarker(this::dropArm)
                                        .build();

        waitForStart();

        if(isStopRequested()) return;

        int pos = vision.getPosition();
        drive.followTrajectory(toSpotTwo);
        drive.followTrajectory(forwardFromPixel);
        drive.turn(Math.toRadians(rot));
        drive.followTrajectory(backOnee);
        drive.followTrajectory(toPaint);
        drive.followTrajectory(forwardFromToPaint);
        drive.followTrajectory(rightOneCloseBackDrop);
        drive.followTrajectory(backOneCloseBackDrop);

    }
    public void dropPixel() {
        stick.unlock();
    }
    public void paintPixel() {
        intake.setZero();
        sleep(200);
        bay.setPosition(0.97);
        sleep(200);
        arm.toDrop();
        sleep(2000);
        bay.setDrop();
        sleep(2000);
        bay.open();
        sleep(500);
    }
    public void returnInit() {
        bay.close();
        sleep(200);
        bay.setPosition(0.97);
        sleep(200);
        arm.drivingPos();
        sleep(2000);
        bay.setPick();
        sleep(200);
        arm.toPickUp();
        sleep(500);
    }
    public void liftArm() {
        arm.drivingPos();
    }
    public void dropArm() {
        arm.toPickUp();
    }


}
