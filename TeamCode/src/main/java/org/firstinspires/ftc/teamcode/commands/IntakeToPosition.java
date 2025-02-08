package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.rustlib.commandsystem.Command;

import java.util.function.IntSupplier;

public class IntakeToPosition extends Command {

    public Intake intake;
    public IntSupplier targetPosition;

    public IntakeToPosition(Intake intake, IntSupplier targetPosition) {
        this.intake = intake;
        this.targetPosition = targetPosition;
    }

    @Override
    public void initialize() {
        intake.SetIntakeTargetPosition(targetPosition.getAsInt());
    }

    @Override
    public void execute() {
        intake.runIntakeToPosition();
    }

//    @Override
//    public boolean isFinished() {
//        return intake.atTargetPosition();
//    }
}
