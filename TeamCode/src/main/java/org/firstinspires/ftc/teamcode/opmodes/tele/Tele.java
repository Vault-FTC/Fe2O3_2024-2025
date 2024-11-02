package org.firstinspires.ftc.teamcode.opmodes.tele;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.commands.DriveDefault;
import org.firstinspires.ftc.teamcode.commands.RunExtrudingArm;
import org.firstinspires.ftc.teamcode.commands.RunIntake;
import org.firstinspires.ftc.teamcode.constants.SubsystemConstants;
import org.firstinspires.ftc.teamcode.opmodes.Robot;
import org.firstinspires.ftc.teamcode.subsystems.ExtrudingArm;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.rustlib.commandsystem.InstantCommand;
import org.rustlib.core.OpModeCore;
import org.rustlib.geometry.Pose2d;
import org.rustlib.rustboard.Rustboard;

@TeleOp(name = "TeleOp")
public class Tele extends Robot implements OpModeCore {
    public static Pose2d backdropPose = blueBackdropPose;

    @Override
    public void opModeInit() {
        Rustboard.updateInputNode("input_1", "Hello from the robot");
        if (alliance == Alliance.RED) {
            backdropPose = redBackdropPose;
        }

        drive.setDefaultCommand(new DriveDefault(drive, () -> -controller1.leftStickY.getAsDouble(), () -> controller1.leftStickX.getAsDouble(), () -> -controller1.rightStickX.getAsDouble()));
        controller1.b.and(controller1.x).and(controller1.y).onTrue(new InstantCommand(() -> drive.getOdometry().setPosition(new Pose2d())));
        controller1.leftTrigger.andNot(controller1.rightTrigger).onTrue(new RunIntake(intake, SubsystemConstants.Intake.IntakeModes.OUTTAKE.speed));
        controller1.rightTrigger.andNot(controller1.leftTrigger).onTrue(new RunIntake(intake, SubsystemConstants.Intake.IntakeModes.INTAKE.speed));
        controller1.leftTrigger.andNot(controller1.rightTrigger).onFalse(new RunIntake(intake, SubsystemConstants.Intake.IntakeModes.OFF.speed));
        controller1.rightTrigger.andNot(controller1.leftTrigger).onFalse(new RunIntake(intake, SubsystemConstants.Intake.IntakeModes.OFF.speed));
        controller1.a.and(controller1.b).andNot(controller1.x).andNot(controller1.y).onTrue(new InstantCommand(() -> drive.enableFastMode()));
        controller1.a.and(controller1.x).andNot(controller1.b).andNot(controller1.y).onTrue(new InstantCommand(() -> drive.enableSlowMode()));
    }

    @Override
    public void opModeLoop() {
        telemetry.addData("heading", drive.getOdometry().getPosition().rotation.getAngleDegrees());
        telemetry.addData("Input 1", Rustboard.getString("input_1", ""));
        telemetry.addData("active rustboard uuid", Rustboard.getActiveRustboard().getUuid());
        //Rustboard.notifyActiveClient("op mode running", NoticeType.POSITIVE);
    }
}

