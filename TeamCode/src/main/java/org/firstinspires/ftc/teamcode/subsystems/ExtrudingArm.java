package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.rustlib.commandsystem.Subsystem;

public class ExtrudingArm extends Subsystem{

    public final Servo servo;

    public ExtrudingArm(Servo servo) { this.servo = servo; }

    public void run(double position) {
        servo.setPosition(Range.clip(position, 0.0, 1.0));
    }
}
