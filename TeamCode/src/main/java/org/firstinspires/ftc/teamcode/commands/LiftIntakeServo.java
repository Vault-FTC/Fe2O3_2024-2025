package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.rustlib.commandsystem.Command;

public class LiftIntakeServo extends Command {
    Intake intake;

    public LiftIntakeServo(Intake intake){
        this.intake = intake;
    }

    @Override
    public void execute() {
        intake.liftedPosition();
    }
}
