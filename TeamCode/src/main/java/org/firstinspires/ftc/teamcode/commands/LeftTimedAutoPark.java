package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.rustlib.commandsystem.Command;

public class LeftTimedAutoPark extends Command {
    public ElapsedTime runtime;
    public Drive drive;

    public LeftTimedAutoPark(Drive drive, ElapsedTime runtime){
        this.drive = drive;
        this.runtime = runtime;
        runtime.startTime();
        runtime.reset();
    }

    @Override
    public void execute() {
        drive.drive(0,-0.5,0);
    }

    @Override
    public boolean isFinished() {        return (runtime.seconds() > 3);
    }
}
