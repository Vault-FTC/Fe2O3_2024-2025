package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSlide;
import org.firstinspires.ftc.teamcode.subsystems.Slide;
import org.rustlib.commandsystem.Command;

public class IntakeSlideToPosition extends Command {
    private final IntakeSlide subsystem;
    private final int position;
    Gamepad gamepad;

    public IntakeSlideToPosition(IntakeSlide slide, int position) {
        subsystem = slide;
        this.position = position;
    }

    @Override
    public void initialize() {
        subsystem.setTargetPosition(position);
    }
    @Override
    public void execute() {
        subsystem.mizoom(position);
    }

    @Override
    public boolean isFinished() {
        return subsystem.atTargetPosition() || (timeSinceInitialized() > 250 && (gamepad != null && !gamepad.atRest()));
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.stop();
    }
}
