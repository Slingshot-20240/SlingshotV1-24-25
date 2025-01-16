package org.firstinspires.ftc.teamcode.mechanisms.misc;

import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ReLocalizer {
    public ColorRangeSensor backDS;
    public DistanceSensor sideDS;
    private IMU imu;

    // S = side sensor
    // B = back sensor
    private final double yBOffset = -5.7, xBOffset = -2.1, ySOffset = -0.2, xSOffset = 5.75;

    private double offDistB = Math.sqrt(Math.pow(xBOffset, 2) + Math.pow(yBOffset, 2));
    private double offDistS = Math.sqrt(Math.pow(xSOffset, 2) + Math.pow(ySOffset, 2));
    private double offsetAngleB = Math.atan(-yBOffset/xBOffset);
    private double offsetAngleS = Math.atan(xSOffset/-ySOffset);

    public ReLocalizer(HardwareMap hardwareMap, IMU usedImu){
        backDS = hardwareMap.get(ColorRangeSensor.class, "backDS");
        sideDS = hardwareMap.get(DistanceSensor.class, "sideDS");
        imu = usedImu;
    }

    // This is for JUnit testing only
    public ReLocalizer(ColorRangeSensor backDS, DistanceSensor sideDS, IMU imu) {
        this.backDS = backDS;
        this.sideDS = sideDS;
        this.imu = imu;
    }

    public double getBackDistance(double robotAngle) {
        robotAngle = Math.toRadians(robotAngle);
        double targetDistance = Math.cos(offsetAngleB - robotAngle) * offDistB + Math.sin(robotAngle) * backDS.getDistance(DistanceUnit.MM);
        return targetDistance;
    }

    public double getSideDistance(double robotAngle) {
        robotAngle = Math.toRadians(robotAngle);
        double targetDistance = Math.cos(offsetAngleS - robotAngle) * offDistS + Math.sin(robotAngle) * sideDS.getDistance(DistanceUnit.MM);
        return targetDistance;
    }
}