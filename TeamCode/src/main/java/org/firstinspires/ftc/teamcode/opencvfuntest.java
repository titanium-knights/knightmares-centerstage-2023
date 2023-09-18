package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class opencvfuntest extends LinearOpMode {

    protected MecanumDrive drive;
//    protected SignalParkVision vision;

    public static int FORWARD1_TIME = 600;
    public static int STRAFE_TIME = 1200;

    protected void setupDevices(){
        drive = new MecanumDrive(hardwareMap);

//        vision = new SignalParkVision(hardwareMap, null);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        setupDevices();

        sleep(4000);
//        int position =  vision.getPosition();
        sleep(2000);

        telemetry.addData("Vision does not work :(: ", 5);

        telemetry.update();
        waitForStart();

//        if(vision.getPosition() == 1){
            drive.strafeLeftWithPower(0.8);
            sleep(STRAFE_TIME);
//        } else if (vision.getPosition() == 3){
            drive.strafeRightWithPower(0.8);
            sleep(STRAFE_TIME);
//        }

        drive.forwardWithPower(0.8);
        sleep(FORWARD1_TIME);
    }
}
