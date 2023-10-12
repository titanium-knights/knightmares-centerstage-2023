package org.firstinspires.ftc.teamcode.utilities;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.pipelines.BasicRedBlueDiff;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

public class InitialVision {
    OpenCvCamera camera;
    public BasicRedBlueDiff pipeline;
    Telemetry telemetry;

    public InitialVision(HardwareMap hmap, Telemetry telemetry, String color) {
        int cameraMonitorViewId = hmap.appContext.getResources().getIdentifier("cameraMonitorViewId",
                "id", hmap.appContext.getPackageName());

        WebcamName webcamName = hmap.get(WebcamName.class, "Webcam 1");
        camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        this.telemetry = telemetry;

        pipeline = new BasicRedBlueDiff(telemetry, color);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
                FtcDashboard.getInstance().startCameraStream(camera, 0);
                camera.setPipeline(pipeline);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Error in camera", errorCode);
            }
        });
    }

    public InitialVision(HardwareMap hmap, Telemetry telemetry){
        this(hmap, telemetry, "red");
    }

    public int getPosition(){
        BasicRedBlueDiff.Locations location = pipeline.getLocation();
        switch (location) {
            case ONE:
                return 1;
            case TWO:
                return 2;
            case THREE:
                return 3;
            default:
                telemetry.addData("Fatal Error in getPosition()", "detection failed");
                return 2;
        }
    }
}
