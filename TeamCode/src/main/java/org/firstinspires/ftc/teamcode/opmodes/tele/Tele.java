package org.firstinspires.ftc.teamcode.opmodes.tele;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.DriveDefault;
import org.firstinspires.ftc.teamcode.commands.IntakeSlideDefault;
import org.firstinspires.ftc.teamcode.commands.IntakeSlideToPosition;
import org.firstinspires.ftc.teamcode.commands.IntakeToPosition;
import org.firstinspires.ftc.teamcode.commands.PlacerGrip;
import org.firstinspires.ftc.teamcode.commands.PlacerSlideDefault;
import org.firstinspires.ftc.teamcode.commands.PlacerSlideToPosition;
import org.firstinspires.ftc.teamcode.commands.RunIntake;
import org.firstinspires.ftc.teamcode.commands.TiltIntake;
import org.firstinspires.ftc.teamcode.commands.TiltPlacer;
import org.firstinspires.ftc.teamcode.commands.TransferBlock;
import org.firstinspires.ftc.teamcode.constants.SubsystemConstants;
import org.firstinspires.ftc.teamcode.opmodes.Robot;
import org.rustlib.commandsystem.InstantCommand;
import org.rustlib.core.OpModeCore;
import org.rustlib.geometry.Pose2d;
import org.rustlib.rustboard.Rustboard;

@TeleOp(name = "TeleOp")
public class Tele extends Robot implements OpModeCore {
    public static Pose2d backdropPose = blueBackdropPose;
    public static Pose2d parkPose = leftParkPose;

    @Override
    public void opModeInit() {
        Rustboard.updateInputNode("input_1", "Hello from the robot");
        if (alliance == Alliance.RED) {
            backdropPose = redBackdropPose;
        }
        drive.enableFastMode();
    }
        public void opModeStart() {
        drive.setDefaultCommand(new DriveDefault(drive, () -> -controller1.leftStickY.getAsDouble(), () -> controller1.leftStickX.getAsDouble(), () -> -controller1.rightStickX.getAsDouble()));

        controller1.b.and(controller1.x).and(controller1.y).onTrue(new InstantCommand(() -> drive.getOdometry().setPosition(new Pose2d())));
        controller1.a.and(controller1.b).andNot(controller1.x).andNot(controller1.y).onTrue(new InstantCommand(() -> drive.enableFastMode()));
        controller1.a.and(controller1.x).andNot(controller1.b).andNot(controller1.y).onTrue(new InstantCommand(() -> drive.enableSlowMode()));

        controller1.leftTrigger.andNot(controller1.rightTrigger).onTrue(new RunIntake(intake, SubsystemConstants.Intake.IntakeModes.OUTTAKE.speed));
        controller1.rightTrigger.andNot(controller1.leftTrigger).onTrue(new RunIntake(intake, SubsystemConstants.Intake.IntakeModes.INTAKE.speed));
        controller1.leftTrigger.andNot(controller1.rightTrigger).onFalse(new RunIntake(intake, SubsystemConstants.Intake.IntakeModes.OFF.speed));
        controller1.rightTrigger.andNot(controller1.leftTrigger).onFalse(new RunIntake(intake, SubsystemConstants.Intake.IntakeModes.OFF.speed));

        controller2.leftTrigger.andNot(controller2.rightTrigger.or(controller1.rightTrigger)).onTrue(new RunIntake(intake, SubsystemConstants.Intake.IntakeModes.OUTTAKE.speed));
        controller2.rightTrigger.andNot(controller2.leftTrigger.or(controller1.leftTrigger)).onTrue(new RunIntake(intake, SubsystemConstants.Intake.IntakeModes.INTAKE.speed));
        controller2.leftTrigger.andNot(controller2.rightTrigger.or(controller1.rightTrigger)).onFalse(new RunIntake(intake, SubsystemConstants.Intake.IntakeModes.OFF.speed));
        controller2.rightTrigger.andNot(controller2.leftTrigger.or(controller1.leftTrigger)).onFalse(new RunIntake(intake, SubsystemConstants.Intake.IntakeModes.OFF.speed));

        controller1.dpadUp.andNot(controller1.dpadDown).onTrue(new TiltIntake(intake, () -> -1.0));
        controller1.dpadDown.andNot(controller1.dpadUp).onTrue(new TiltIntake(intake, () -> 1.0));
        controller1.dpadUp.andNot(controller1.dpadDown).onFalse(new TiltIntake(intake, () -> 0));
        controller1.dpadDown.andNot(controller1.dpadUp).onFalse(new TiltIntake(intake, () -> 0));

        controller1.leftBumper.andNot(controller1.rightBumper).onTrue(new IntakeSlideDefault(intakeSlide,() -> 1.0));
        controller1.rightBumper.andNot(controller1.leftBumper).onTrue(new IntakeSlideDefault(intakeSlide,() -> -1.0));
        controller1.leftBumper.andNot(controller1.rightBumper).onFalse(new IntakeSlideDefault(intakeSlide,() -> 0));
        controller1.rightBumper.andNot(controller1.leftBumper).onFalse(new IntakeSlideDefault(intakeSlide,() -> 0));
        controller2.leftBumper.andNot(controller2.rightBumper).onTrue(new IntakeSlideToPosition(intakeSlide, 1.0));// In
        controller2.rightBumper.andNot(controller2.leftBumper).onTrue(new IntakeSlideToPosition(intakeSlide, 0.3));// Out
        controller2.dpadLeft.andNot(controller2.dpadRight).onTrue( new TransferBlock(intake,() -> SubsystemConstants.Intake.closeGatePosition));
        controller2.dpadRight.andNot(controller2.dpadLeft).onTrue( new TransferBlock(intake,() -> SubsystemConstants.Intake.openGatePosition));

        controller2.dpadDown.andNot(controller2.dpadUp).onTrue( new TiltPlacer(placer, () -> -1.0));
        controller2.dpadUp.andNot(controller2.dpadDown).onTrue( new TiltPlacer(placer, () -> 1.0));
        controller2.dpadDown.andNot(controller2.dpadUp).onFalse( new TiltPlacer(placer, () -> 0));
        controller2.dpadUp.andNot(controller2.dpadDown).onFalse( new TiltPlacer(placer, () -> 0));
        controller2.a.andNot(controller2.b).onTrue( new PlacerGrip(placer,() -> SubsystemConstants.Placer.openPosition));
        controller2.b.andNot(controller2.a).onTrue( new PlacerGrip(placer,() -> SubsystemConstants.Placer.closePosition));

        placerSlide.setDefaultCommand(new PlacerSlideDefault(placerSlide, () -> controller2.leftStickY.getAsDouble()));
        controller2.leftBumper.andNot(controller2.rightBumper).onTrue(new PlacerSlideToPosition(placerSlide, SubsystemConstants.PlacerSlide.PlacerSlideStowedPosition));
//        controller2.rightBumper.andNot(controller2.leftBumper).onTrue(new PlacerSlideToPosition(placerSlide, SubsystemConstants.PlacerSlide.PlacerSlideMediumPosition));
    }

    @Override
    public void opModeLoop() {
        telemetry.addData("Placer Position", placer.placer.getPosition());
        telemetry.addData("Gate Position", intake.intGate.getPosition());
        telemetry.addData("gateSensor", intake.gateSensor.isPressed());
        telemetry.addData("XPosition", drive.getOdometry().getPosition().x);
        telemetry.addData("YPosition", drive.getOdometry().getPosition().y);
        telemetry.addData("heading", drive.getOdometry().getPosition().rotation.getAngleDegrees());
        telemetry.addData("Input 1", Rustboard.getString("input_1", ""));
        telemetry.addData("active rustboard uuid", Rustboard.getActiveRustboard().getUuid());
        telemetry.addData("SlidePosition",placerSlide.encoder.getPosition());
        //Rustboard.notifyActiveClient("op mode running", NoticeType.POSITIVE);
    }
}

