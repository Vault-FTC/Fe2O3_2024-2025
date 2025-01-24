package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.rustlib.commandsystem.Subsystem;
import org.rustlib.hardware.Encoder;

public class IntakeSlide extends Subsystem {
    public CRServo intSlideServo1;
    public CRServo intSlideServo2;

    public Intake intake;

//    Encoder

    public IntakeSlide (HardwareMap hardwareMap, Intake intake){
        this.intSlideServo1 = hardwareMap.crservo.get("intSlide1");
        intSlideServo1.setDirection(DcMotorSimple.Direction.FORWARD);
        this.intSlideServo2 = hardwareMap.crservo.get("intSlide2");
        intSlideServo2.setDirection(DcMotorSimple.Direction.REVERSE);

        this.intake = intake;
    }

    private static boolean gamepadActive(double input) {
        return Math.abs(input) > 0.05;
    }
//
//    public void mizoom(double input){
//        if (gamepadActive(input) && !()) {
//
//        }
//    }

    public void run(double speed){
        intSlideServo1.setPower(Range.clip(speed,-0.5,0.5));
        intSlideServo2.setPower(Range.clip(speed,-0.5,0.5));
    }
}
