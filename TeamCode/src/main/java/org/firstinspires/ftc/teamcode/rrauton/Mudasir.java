package org.firstinspires.ftc.teamcode.rrauton;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.rr.drive.SampleMecanumDrive;

import org.firstinspires.ftc.teamcode.utilities.InitialVision;
import org.firstinspires.ftc.teamcode.utilities.Stick;
import org.firstinspires.ftc.teamcode.utilities.Arm;

@Autonomous(name="MudasirRed", group="Autonomous")
public class Mudasir extends LinearOpMode {

    public SampleMecanumDrive drive;
    public Stick stick;
    public InitialVision vision;
    public Arm arm;
    //TODO etc. etc., and add to the createHardware method

    public void createHardware(HardwareMap hmap) {
        drive = new SampleMecanumDrive(hmap);
        stick = new Stick(hmap);
        vision = new InitialVision(hmap, telemetry, "red"); //TODO: remember to change to blue for blue side
        arm = new Arm(hmap);
    }

    @Override
    public void runOpMode() {
        createHardware(hardwareMap);
        waitForStart();

        //TODO check this value
        Pose2d startPose = new Pose2d(10, -8, Math.toRadians(90));
        drive.setPoseEstimate(startPose);

        Trajectory toImaginaryPoint = drive.trajectoryBuilder(new Pose2d())
                .strafeRight(10)
                .forward(5)
                .build();

        Trajectory doSomethingElse = drive.trajectoryBuilder(toImaginaryPoint.end())
                .splineTo(new Vector2d(20, 9), Math.toRadians(45))
                .build();

        waitForStart();

        if(isStopRequested()) return;

        drive.followTrajectory(toImaginaryPoint);
    }
}
