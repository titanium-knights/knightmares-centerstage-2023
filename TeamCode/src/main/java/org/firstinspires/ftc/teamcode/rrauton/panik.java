package org.firstinspires.ftc.teamcode.rrauton;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.rr.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.utilities.Arm;
import org.firstinspires.ftc.teamcode.utilities.Bay;
import org.firstinspires.ftc.teamcode.utilities.InitialVision;
import org.firstinspires.ftc.teamcode.utilities.Intake;
import org.firstinspires.ftc.teamcode.utilities.Stick;

@Config
@Autonomous(name="pain", group="Autonomous")
public class panik extends LinearOpMode {

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
        Pose2d startPose = new Pose2d(-35.5, 60, Math.toRadians(0));
        drive.setPoseEstimate(startPose);

        Trajectory toSpotTwo = drive.trajectoryBuilder(new Pose2d(10, 60, Math.toRadians(90)))
                .splineTo(new Vector2d(10, 30), Math.toRadians(90))
                .splineTo(new Vector2d(50, 38), Math.toRadians(0))
                .build();


        waitForStart();

        if(isStopRequested()) return;

        int pos = vision.getPosition();
        drive.followTrajectory(toSpotTwo);
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
