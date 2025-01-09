package org.firstinspires.ftc.teamcode.vision;

import static org.opencv.imgproc.Imgproc.Canny;
import static org.opencv.imgproc.Imgproc.HoughLines;
import static org.opencv.imgproc.Imgproc.HoughLinesP;
import static org.opencv.imgproc.Imgproc.MORPH_ELLIPSE;
import static org.opencv.imgproc.Imgproc.MORPH_RECT;
import static org.opencv.imgproc.Imgproc.dilate;
import static org.opencv.imgproc.Imgproc.erode;
import static org.opencv.imgproc.Imgproc.getStructuringElement;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class birdsEyeSampleSearch implements VisionProcessor {
    //OpenCV sim stuff
    public boolean isBlue = false;
    public boolean isRed = false;
    public boolean seeConvertedFrame = false;
    public boolean seePostThreshold = false;
    public boolean seeMorphology = false;
    public boolean seeCanny = false;
    public boolean seeHough = false;

    public Telemetry telemetry = null;
    //matrices, in order of appearance
    Mat convertedFrame = new Mat();
    Mat groundSubmat = new Mat();


    Size frameSize = new Size();

    Point groundTopLeft = new Point(1,1);
    Point groundBottomRight = new Point(40,40);

    //Matrix Data
    double avg = 0.0;

    //kernals
    public int dilateSize = 0;
    public int erodeSize = 3;

    //line stuff


    //utilties
    private static final Scalar BLUE = new Scalar(0, 0, 255);

    public birdsEyeSampleSearch(Telemetry telemetry){
        this.telemetry = telemetry;

    }
    @Override
    public void init(int width, int height, CameraCalibration calibration) {

    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
//        telemetry.addLine("t opened");

        frameSize = frame.size();
        convertedFrame = frame.clone();
        groundSubmat = convertedFrame.submat(new Rect(groundTopLeft, groundBottomRight));
        avg = (Core.mean(groundSubmat).val[0]+Core.mean(groundSubmat).val[1]+Core.mean(groundSubmat).val[2])/3;


        for(int x = 0;x<frameSize.width;x++) {
            for (int y = 0; y < frameSize.height; y++) {
                double[] colorAtPoint = convertedFrame.get(y,x);

            }
        }

        for(int x = 0;x<frameSize.width;x++){
            for(int y = 0;y<frameSize.height;y++){
                double[] colorAtPoint = convertedFrame.get(y,x);
                double[] hsv = this.HSVcalc(colorAtPoint);
                double result;
                //based on the target sample, match hsv value
                if(isBlue) {
                    result = (hsv[0] > 220 && hsv[0] < 250 && hsv[1] > 30 && hsv[2] > 20 && hsv[2] < 70) ? 255 : 0;
                }else if(isRed){
                    result = ((hsv[0] > 350 || hsv[0] < 10) && hsv[1] > 60 && hsv[2] > 30) ? 255 : 0;

                }else{
                    result = ((hsv[0] > 25 && hsv[0] < 50) && hsv[1] > 60 && hsv[2] > 50) ? 255 : 0;
//                    int top = Math.min(y,10);
//                    int bot = Math.min((int)frameSize.height-y,10);
//                    int left = Math.min(x,10);
//                    int right = Math.min((int)frameSize.width-x,10);
//                    double total = 0;
//                    telemetry.addLine(String.format("t: %s, b: %s, l: %s, r:%s", top, bot, left, right));
//                    Mat submat = convertedFrame.submat(y-top, y+bot, x-left, x+right);
//                    total = this.HSVcalc(Core.mean(submat).val)[1];
//                    telemetry.addLine(String.format("mean: %s, rs: %s, re: %s, cs: %s, ce: %s", total, y-top, y+bot, x-left, x+right));

//                    result /= total;
                }
                //set all pixels to white/black
                colorAtPoint[0]=colorAtPoint[1]=colorAtPoint[2]=result;
                convertedFrame.put(y,x,colorAtPoint);
            }
        }
        Imgproc.cvtColor(convertedFrame, convertedFrame, Imgproc.COLOR_BGR2GRAY);

        if(seeConvertedFrame)
            convertedFrame.copyTo(frame);

        Mat dilateKernel = getStructuringElement(MORPH_RECT,
                new Size(2 * dilateSize + 1, 2 * dilateSize + 5),
                new Point(dilateSize, dilateSize));

        Mat erodeKernel = getStructuringElement(MORPH_RECT,
                new Size(2 * erodeSize + 1, 2 * erodeSize + 5),
                new Point(erodeSize, erodeSize));

        Mat morphTransform = new Mat();
        dilate(convertedFrame, morphTransform, dilateKernel);
        erode(morphTransform, morphTransform, erodeKernel);
        dilate(morphTransform, morphTransform, dilateKernel);
        if(seeMorphology)
            morphTransform.copyTo(frame);



        //findContours
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(morphTransform, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

//show boundRects
        if(!seeMorphology && !seeHough && !seeCanny && !seePostThreshold) {
            for (MatOfPoint x : contours) {
                /// create MatOfPoint -> create boundingRect
                MatOfPoint2f  newX = new MatOfPoint2f( x.toArray() );

                RotatedRect boundRect = Imgproc.minAreaRect(newX);

                Point[] points = new Point[4];
                boundRect.points(points);
                double sl = Math.sqrt(Math.pow(points[0].x, 2)+Math.pow(points[0].y, 2))-Math.sqrt(Math.pow(points[1].x, 2)+Math.pow(points[1].y, 2));
                //show big ones (blue)
                if(Imgproc.contourArea(x)>1500) {
                    for (int i = 0; i < 4; ++i) {
                        Imgproc.line(frame,
                                points[i],
                                points[(i + 1) % 4],
                                BLUE, 2);
                    }

                    Imgproc.putText(frame, String.valueOf(((int)boundRect.angle*100)/100), boundRect.center, 1, 0.75, new Scalar(0,0,0));
                }
                //show square ones (Red)
                else if(Math.abs(sl)<15&&Imgproc.contourArea(x)>500){
                    for (int i = 0; i < 4; ++i) {
                        Imgproc.line(frame,
                                points[i],
                                points[(i + 1) % 4],
                                new Scalar(255,0,0), 2);
                        Imgproc.putText(frame, String.valueOf(((int)sl*100)/100), boundRect.center, 1, 0.75, new Scalar(0,0,0));

                    }
                }
                //rest (blue)
                else{
                    for (int i = 0; i < 4; ++i) {
                        Imgproc.line(frame,
                                points[i],
                                points[(i + 1) % 4],
                                new Scalar(0,255,0), 2);
                        Imgproc.putText(frame, String.valueOf(((int)Imgproc.contourArea(x)*100)/100), boundRect.center, 1, 0.75, new Scalar(0,0,0));

                    }
                }
            }
        }


        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }
//    public ArrayList<double[]> getLines(){
//        return lineList;
//    }
    public static double[] HSVcalc(double[] rgb){
        double[] dout = new double[3];
        double r = rgb[0]/255;
        double g = rgb[1]/255;
        double b = rgb[2]/255;
        double max = Math.max(r,Math.max(g,b));
        double min = Math.min(r,Math.min(g,b));
        double delta = max-min;
        if(delta == 0){
            dout[0]=0;
        }
        else if(r==max){
            dout[0] = (60 * ((g - b) / delta) + 360) % 360;
        }else if(g==max){
            dout[0] = (60 * ((b - r) / delta) + 120) % 360;
        }else if(b==max){
            dout[0] = (60 * ((r - g) / delta) + 240) % 360;
        }
        dout[1]=max==0?0:(delta/max)*100;
        dout[2]=max*100;

        return dout;
    }
}
