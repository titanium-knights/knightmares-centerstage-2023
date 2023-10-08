package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;

@TeleOp(name="Teleop")
public class Teleop extends OpMode {

    Telemetry telemetry;
    MecanumDrive drive;

    public void init() {
        this.telemetry = telemetry;
        this.drive = new MecanumDrive(hardwareMap);
    }

    @Override
    public void runOpMode() {



    }
}