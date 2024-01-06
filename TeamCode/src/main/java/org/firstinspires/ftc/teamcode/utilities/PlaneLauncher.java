package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class PlaneLauncher {
    Servo launcher;


    public PlaneLauncher(HardwareMap hmap) {
        this.launcher = hmap.servo.get(CONFIG.planeServo);
    }
    // when releasing or resetting just move the servo...
    public void set(){
        launcher.setPosition(0.12);
    } // 0.8 before
    public void reset() {launcher.setPosition(0.5);}
    public void setPosition(double angle) {
        launcher.setPosition(angle);
    }
    public double getPosition() {
        return launcher.getPosition();
    }
}
