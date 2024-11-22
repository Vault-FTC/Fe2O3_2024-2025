package org.firstinspires.ftc.teamcode.constants;

public class SubsystemConstants {
    public static final boolean debugMode = false;

    public static final class ExtrudingArm {
        public static final double ServoRetracted = 0.0;//0.5
        public static final double ServoExtended = 0.5;//0.75
    }

    public static final class Intake {
        public static final double defaultSpeed = 1.0;
        public enum IntakeModes {
            INTAKE(defaultSpeed),
            OUTTAKE(-defaultSpeed),
            OFF(0.0);

            public final double speed;

            IntakeModes(double speed) {
                this.speed = speed;
            }
        }
    }

    public static final class IntakeSlide {
        public static final int prepareIntakePosition = 50;
        public static final int stowIntakePosition = 20;
        public static final int maxExtensionPosition = 80;
        public static final int maxTargetError = 10;
        public static final int defaultIntakePosition = 75;
        public static final int stowedPosition = 10;
        public static final int autoPlacePosition = 75;
    }

    public static final class Slide {
        public static final int preparePlacerPosition = 350;
        public static final int stowPlacerPosition = 690;
        public static final int maxExtensionPosition = 1000;
        public static final int maxTargetError = 50;
        public static final int defaultPlacePosition = 760;
        public static final int stowedPosition = -100;
        public static final int autoPlacePosition = 550;
    }

    public static final class Placer {
        public static final double lifter0PlacePosition = 0.1;
        public static final double lifter1PlacePosition = 0.7;
        public static final double lifter0StoragePosition = 0.3;
        public static final double lifter1StoragePosition = 0.5;
        public static final double openPosition = 0.56;
        public static final double closePosition = 0.62;
    }
}
