package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.constants.SubsystemConstants;
import org.rustlib.commandsystem.Subsystem;
import org.rustlib.control.PIDController;
import org.rustlib.hardware.Encoder;
import org.rustlib.hardware.PairedEncoder;

public class Intake extends Subsystem {
//    public final DcMotor motor;
    private final PIDController controller;
    int targetPosition;
    public final Encoder encoder;
    public final DcMotor motor;
    public final CRServo crServo2;
    public final CRServo crServo1;
    public final Servo intGate;
    public final TouchSensor gateSensor;

    public Intake(HardwareMap hardwareMap) {
        this.motor = hardwareMap.dcMotor.get("intakeTilt");
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        encoder = new PairedEncoder(hardwareMap.get(DcMotor.class, "intakeTilt"), false);
        encoder.reset();

        intGate = hardwareMap.servo.get("intakeGate");
        gateSensor = hardwareMap.touchSensor.get("gateSensor");

        this.crServo2 = hardwareMap.crservo.get("intakeServo2");
        crServo2.setDirection(DcMotorSimple.Direction.REVERSE);
        this.crServo1 = hardwareMap.crservo.get("intakeServo1");

        this.controller = new PIDController(0.0017, 0.0000008, 0.000003);
    }

    public void runIntTiltMotor(double speed){
        motor.setPower(Range.clip(speed, -0.5, 0.5));
    }

    public void run(double speed) {
        crServo1.setPower(Range.clip(speed, -1.0, 1.0));
        crServo2.setPower(Range.clip(speed, -1.0, 1.0));
    }

    public void transferBlock(double position){
        intGate.setPosition(position);
    }

    public void SetIntakeTargetPosition(int targetPosition) {
        this.targetPosition = Range.clip(targetPosition, SubsystemConstants.Intake.minIntakePosition,SubsystemConstants.Intake.maxIntakePosition);
    }


    public boolean atTargetPosition() {
        return Math.abs(targetPosition - encoder.getTicks()) < SubsystemConstants.PlacerSlide.maxTargetError;
    }

    public void runIntakeToPosition() {
        motor.setTargetPosition(targetPosition);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
