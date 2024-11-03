package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.rustlib.commandsystem.Subsystem;

public class Intake extends Subsystem {
    public final DcMotor motor;
    public final Servo servo1;
    public final Servo servo2;

    public Intake(HardwareMap hardwareMap) {
        this.motor = hardwareMap.dcMotor.get("intake");
        this.servo1 = hardwareMap.servo.get("");
        this.servo2 = hardwareMap.servo.get("");
    }

    public void run(double speed) {
        motor.setPower(Range.clip(speed, -1.0, 1.0));
    }

    public void groundedPosition(){
//  todo: figure out these values
        servo1.setPosition(1);
        servo2.setPosition(1);
    }

    public void liftedPosition(){
//  todo: figure out these values
        servo1.setPosition(1);
        servo2.setPosition(1);
    }
}
