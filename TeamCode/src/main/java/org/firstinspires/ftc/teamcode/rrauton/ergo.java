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
    //TODO etc. etc., and add to the createHardware method

    public void createHardware(HardwareMap hmap) {
        drive = new SampleMecanumDrive(hmap);
        stick = new Stick(hmap);
        vision = new InitialVision(hmap, telemetry, "blue"); //TODO: remember to change to blue for blue side
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

        Trajectory moveOne = drive.trajectoryBuilder(new Pose2d())
                .back(35)
                .addDisplacementMarker(this::dropPixel)
                .build();

        Trajectory toSpotOne = drive.trajectoryBuilder(new Pose2d())
                .strafeLeft(12)
                .build();

        waitForStart();

        if(isStopRequested()) return;
        int pos = vision.getPosition();

        drive.followTrajectory(moveOne);

    }
    public void dropPixel() {
        stick.unlock();
        sleep(300);
    }
    public void paintPixel() {
        intake.setZero();
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
        bay.setPosition(1.0);
        sleep(200);
        arm.drivingPos();
        sleep(2000);
        arm.toPickUp();
        sleep(500);
        bay.setPick();
        sleep(200);
    }

}
