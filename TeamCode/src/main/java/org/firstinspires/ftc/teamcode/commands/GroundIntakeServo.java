package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.rustlib.commandsystem.Command;

public class GroundIntakeServo extends Command {
    Intake intake;

    public GroundIntakeServo(Intake intake){
        this.intake = intake;
    }

    @Override
    public void execute() {
        intake.groundedPosition();
    }
}
