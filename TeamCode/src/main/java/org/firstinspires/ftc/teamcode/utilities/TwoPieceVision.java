package org.firstinspires.ftc.teamcode.utilities;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.pipelines.TwoPiecePipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

public class TwoPieceVision {
    OpenCvCamera camera;
    public TwoPiecePipeline pipeline;
    Telemetry telemetry;

    public TwoPieceVision(HardwareMap hmap, Telemetry telemetry, String color) {
        int cameraMonitorViewId = hmap.appContext.getResources().getIdentifier("cameraMonitorViewId",
                "id", hmap.appContext.getPackageName());

        WebcamName webcamName = hmap.get(WebcamName.class, "Webcam 1");
        camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);

        pipeline = new TwoPiecePipeline(telemetry, color);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
                FtcDashboard.getInstance().startCameraStream(camera, 0);
                camera.setPipeline(pipeline);
            }

            @Override
            public void onError(int errorCode) {
            }
        });
    }

    public TwoPieceVision(HardwareMap hmap, Telemetry telemetry){
        this(hmap, telemetry, "red");
    }

    public int getPosition(){
        TwoPiecePipeline.Locations location = pipeline.getLocation();
        if (location == TwoPiecePipeline.Locations.ONE) return 1;
        else if (location == TwoPiecePipeline.Locations.TWO) return 2;
        else if (location == TwoPiecePipeline.Locations.THREE) return 3;
        telemetry.addLine("Something went wrong with the vision");
        return 1;
    }
}
