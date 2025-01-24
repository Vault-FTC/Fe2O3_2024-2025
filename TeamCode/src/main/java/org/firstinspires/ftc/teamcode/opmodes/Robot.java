package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.VoltageUnit;
import org.firstinspires.ftc.teamcode.commands.IntakeToPosition;
import org.firstinspires.ftc.teamcode.commands.LeftTimedAutoPark;
import org.firstinspires.ftc.teamcode.commands.RightTimedAutoPark;
import org.firstinspires.ftc.teamcode.opmodes.auto.TimedLeftPark;
import org.firstinspires.ftc.teamcode.opmodes.auto.TimedRightPark;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSlide;
import org.firstinspires.ftc.teamcode.subsystems.Placer;
import org.firstinspires.ftc.teamcode.subsystems.Slide;
import org.rustlib.config.HardwareConfiguration;
import org.rustlib.config.PreferenceEditor;
import org.rustlib.core.RobotBase;
import org.rustlib.geometry.Pose2d;
import org.rustlib.geometry.Rotation2d;
import org.rustlib.rustboard.Rustboard;

public abstract class Robot extends RobotBase {
    public static Pose2d blueBackdropPose = new Pose2d(17.0, 30, new Rotation2d(-Math.PI / 2));
    public static Pose2d redBackdropPose = new Pose2d(17.0, 110, new Rotation2d(-Math.PI / 2));
    public static Pose2d leftParkPose = new Pose2d(17.0, 110, new Rotation2d(-Math.PI / 2));

    public Drive drive;
    public Intake intake;
    public IntakeSlide intakeSlide;
    public ElapsedTime runtime;
    public LeftTimedAutoPark leftTimedAutoPark;
    public RightTimedAutoPark rightTimedAutoPark;

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
                .addServo("intakeServo1", 0, HardwareConfiguration.Servos.SMART_SERVO, HardwareConfiguration.HubType.CONTROL_HUB)
                .addServo("intakeServo2", 0, HardwareConfiguration.Servos.SMART_SERVO, HardwareConfiguration.HubType.EXPANSION_HUB)
                .addServo("ledDriver", 2, HardwareConfiguration.Servos.REV_BLINKIN_LED_DRIVER, HardwareConfiguration.HubType.CONTROL_HUB)
                .addI2CDevice("slide limit", 0, 0, HardwareConfiguration.I2CDevices.REV_DISTANCE_SENSOR, HardwareConfiguration.HubType.CONTROL_HUB)
                .build();

        //cameraServer = new CameraServer(hardwareMap, "Webcam0");

        drive = new Drive(hardwareMap);
        intake = new Intake(hardwareMap);
        intakeSlide = new IntakeSlide(hardwareMap,intake);
        runtime = new ElapsedTime();
        rightTimedAutoPark = new RightTimedAutoPark(drive,runtime);
        leftTimedAutoPark = new LeftTimedAutoPark(drive,runtime);
    }

    @Override
    public final void robotLoop() {
        Rustboard.updateTelemetryNode("battery voltage", controlHub.getInputVoltage(VoltageUnit.VOLTS));
    }
}