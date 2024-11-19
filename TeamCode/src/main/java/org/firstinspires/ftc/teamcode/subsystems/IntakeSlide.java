package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.constants.SubsystemConstants;
import org.rustlib.commandsystem.InstantCommand;
import org.rustlib.commandsystem.Subsystem;
import org.rustlib.commandsystem.Trigger;
import org.rustlib.control.PIDController;
import org.rustlib.hardware.PairedEncoder;
import org.rustlib.rustboard.Rustboard;

public class IntakeSlide extends Subsystem {
    public final DcMotor motor;
    public final PairedEncoder encoder;
    private final PIDController controller;
//    private final TouchSensor limit;
    private final Intake intake;
    private double feedforward = 0;
    private int targetPosition = 0;
    private double lastSpeed = 0;
    private double lastInput = 0;

    public IntakeSlide(HardwareMap hardwareMap, Intake intake) {
        motor = hardwareMap.get(DcMotor.class, "intakeSlide");
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        encoder = new PairedEncoder(hardwareMap.get(DcMotor.class, "intakeSlide"), false);
        encoder.reset();
//        limit = hardwareMap.get(TouchSensor.class, "limit");
        this.intake = intake;
        controller = new PIDController(0.2, 0, 0);

//        new Trigger(() -> encoder.getTicks() > SubsystemConstants.Slide.preparePlacerPosition && encoder.ticksPerSecond() > 0).onTrue(new InstantCommand(intake::groundedPosition));
//        new Trigger(() -> encoder.getTicks() < SubsystemConstants.Slide.stowPlacerPosition && targetPosition < 10 || encoder.ticksPerSecond() < -400).onTrue(new InstantCommand(intake::liftedPosition));
    }

    private static boolean gamepadActive(double input) {
        return Math.abs(input) > 0.05;
    }

    public void mizoom(double input) {
        double calculatedSpeed;
        if (gamepadActive(input) && !(input > 0 && encoder.getTicks() > SubsystemConstants.IntakeSlide.maxExtensionPosition)) { // If manual control is both requested and allowed
            calculatedSpeed = input + feedforward;
            lastInput = input;
        } else { // If automatic control is requested or manual control is not allowed
            if (gamepadActive(lastInput)) {
                targetPosition = encoder.getTicks();
            }
            calculatedSpeed = controller.calculate(encoder.getTicks(), targetPosition) + feedforward;
            if (encoder.getTicks() < 125 && targetPosition < 80) {
                calculatedSpeed -= 0.5;
            }
            lastInput = 0;
        }
        calculatedSpeed = applyAccelerationLimits(calculatedSpeed);
        Rustboard.updateTelemetryNode("slide speed", calculatedSpeed);
        drive(calculatedSpeed);
    }

    private double applyAccelerationLimits(double speed) {
        double accelMax = Rustboard.getDouble("slide accel", 0.5);
        if (speed > 0) {
            speed = Math.min(lastSpeed + accelMax, speed);
        } else {
            speed = Math.max(lastSpeed - accelMax, speed);
        }
        return speed;
    }

    private void drive(double speed) {
        motor.setPower(speed);
        lastSpeed = speed;
    }

    public void setTargetPosition(int targetPosition) {
        this.targetPosition = Math.min(targetPosition, SubsystemConstants.IntakeSlide.maxExtensionPosition);
    }

    public boolean atTargetPosition() {
        return Math.abs(targetPosition - encoder.getTicks()) < SubsystemConstants.IntakeSlide.maxTargetError;
    }

    public void stop(){
        drive(0);
    }

    @Override
    public void periodic() {
//        if (limit.isPressed()) {
//            encoder.reset();
//            intake.liftedPosition();
//            targetPosition = Math.max(targetPosition, 0);
//        }

        controller.setP(Rustboard.getDouble("slide kP", 0.2));
        controller.setI(Rustboard.getDouble("slide kI", 0));
        controller.setD(Rustboard.getDouble("slide kD", 0));
        feedforward = Rustboard.getDouble("slide feedforward", 0);
//        RustboardServer.log(controller.getGains().toString()); // TODO: add logging again
//        RustboardServer.log("feedforward: " + feedforward);
//        RustboardServer.setNodeValue("slide pose", encoder.getTicks());
//        RustboardServer.setNodeValue("slide velocity", encoder.ticksPerSecond());
//        RustboardServer.setNodeValue("slide target", targetPosition);
//        RustboardServer.setNodeValue("slide limit", limit.isPressed());
    }
}
