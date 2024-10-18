package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.ExtrudingArm;
import org.rustlib.commandsystem.Command;

public class RunExtrudingArm extends Command {
    ExtrudingArm subsystem;
    double position;

    public RunExtrudingArm(ExtrudingArm subsystem, double position){
        this.subsystem = subsystem;
        this.position = position;
        addRequirements(subsystem);
    }

    @Override
    public void execute(){ subsystem.run(position); }
}