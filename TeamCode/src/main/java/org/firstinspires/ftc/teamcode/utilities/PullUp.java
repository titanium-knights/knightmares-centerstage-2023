package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class PullUp {
    public DcMotor pullUpMotor;

    public PullUp(HardwareMap hmap) {
        this.pullUpMotor = hmap.dcMotor.get(CONFIG.pullUpMotor);
    }


}
