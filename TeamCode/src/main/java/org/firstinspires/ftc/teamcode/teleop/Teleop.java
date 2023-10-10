package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utilities.Claw;
import org.firstinspires.ftc.teamcode.utilities.Lift;
import org.firstinspires.ftc.teamcode.utilities.MecanumDrive;

@TeleOp
public class Teleop extends OpMode {

    Telemetry telemetry;
    MecanumDrive drive;
    Claw claw;
    Lift lift;

    public void init() {
        this.drive = new MecanumDrive(hardwareMap);
        this.claw = new Claw(hardwareMap);
        this.lift = new Lift(hardwareMap);
    }

    @Override
    public void loop() {
        // if (button_pressed) do thing
        // else dont do thing
    }
}
