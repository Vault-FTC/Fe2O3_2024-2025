package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.VoltageUnit;
import org.firstinspires.ftc.teamcode.commands.TimedPark;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.ExtrudingArm;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Lights;
import org.rustlib.config.HardwareConfiguration;
import org.rustlib.config.PreferenceEditor;
import org.rustlib.core.RobotBase;
import org.rustlib.geometry.Pose2d;
import org.rustlib.geometry.Rotation2d;
import org.rustlib.rustboard.Rustboard;

public abstract class Robot extends RobotBase {
    public static Pose2d blueBackdropPose = new Pose2d(17.0, 30, new Rotation2d(-Math.PI / 2));
    public static Pose2d redBackdropPose = new Pose2d(17.0, 110, new Rotation2d(-Math.PI / 2));
    public Drive drive;
    public static Intake intake;
    public static Lights lights;
    public static TimedPark timedPark;
    //public CameraServer cameraServer;

    @Override
    public final void robotInit() {
        PreferenceEditor.configureNetwork("21865-RC", "@ftc21865");
        HardwareConfiguration.getBuilder()
                .setConfigurationName("default")
                .configureControlHub("Control Hub", 0)
                .configureExpansionHub("Expansion Hub 1", 0)
                .addMotor("rf", 0, HardwareConfiguration.Motors.GOBILDA_5201_SERIES_MOTOR, HardwareConfiguration.HubType.CONTROL_HUB)
                .addMotor("rb", 1, HardwareConfiguration.Motors.GOBILDA_5201_SERIES_MOTOR, HardwareConfiguration.HubType.CONTROL_HUB)
                .addMotor("lf", 2, HardwareConfiguration.Motors.GOBILDA_5201_SERIES_MOTOR, HardwareConfiguration.HubType.CONTROL_HUB)
                .addMotor("lb", 3, HardwareConfiguration.Motors.GOBILDA_5201_SERIES_MOTOR, HardwareConfiguration.HubType.CONTROL_HUB)
                .addMotor("intakeSlide", 0, HardwareConfiguration.Motors.GOBILDA_5201_SERIES_MOTOR, HardwareConfiguration.HubType.EXPANSION_HUB)
                .addMotor("intake", 1, HardwareConfiguration.Motors.GOBILDA_5201_SERIES_MOTOR, HardwareConfiguration.HubType.EXPANSION_HUB)
                .addI2CDevice("slide limit", 0, 0, HardwareConfiguration.I2CDevices.REV_DISTANCE_SENSOR, HardwareConfiguration.HubType.CONTROL_HUB)
                .build();

        //cameraServer = new CameraServer(hardwareMap, "Webcam0");

        drive = new Drive(hardwareMap);
        intake = new Intake(hardwareMap.get(DcMotor.class, "intake"));
//        lights = new Lights(RevBlinkinLedDriver);
//        timedPark = new TimedPark(drive,,gamepad1, getRuntime());
    }

    @Override
    public final void robotLoop() {
        Rustboard.updateTelemetryNode("battery voltage", controlHub.getInputVoltage(VoltageUnit.VOLTS));
    }
}