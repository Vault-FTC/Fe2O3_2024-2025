package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@Autonomous

public class BluePark25 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor FLDrive = hardwareMap.dcMotor.get("(front left)");
        DcMotor FRDrive = hardwareMap.dcMotor.get("(front right)");
        DcMotor RLDrive = hardwareMap.dcMotor.get("(rear left)");
        DcMotor RRDrive = hardwareMap.dcMotor.get("(rear right)");

        FLDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        RRDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        if (isStopRequested()) return;

       if (opModeIsActive()) {

            FLDrive.setPower(2);
            FRDrive.setPower(-2);
            RLDrive.setPower(2);
            RRDrive.setPower(-2);

            sleep(2000);

            FLDrive.setPower(0);
            FRDrive.setPower(0);
            RLDrive.setPower(0);
            RRDrive.setPower(0);
        }
    }
}
