package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.rustlib.commandsystem.Command;

public class IntakeToPosition extends Command {

    public Intake intake;
    public int targetPosition;

    public IntakeToPosition(Intake intake, int targetPosition) {
        this.intake = intake;
        this.targetPosition = targetPosition;
    }

    @Override
    public void initialize() {
        intake.SetIntakeTargetPosition(targetPosition);
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
