package model.turtlehero;

import controller.Main;
import controller.PlayerInputEventQueue;
import model.GameFigure;
import model.MousePointer;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class turtleHero extends GameFigure {

    public int BASE_SIZE = 20;
    public int BARREL_LEN = 20; // 20 pixel

    public static final int UNIT_MOVE = 15; // 10 pixels by 4 arrow keys

    public Rectangle2D.Float base;
    public Line2D.Float barrel;

    public Color color;
    public BasicStroke stroke;

    Color GreenColor1 = new Color(102,153,0);
    Color GreenColor2 = new Color(133,213,133);
    Color BrownColor1 = new Color(92,93,0);
    Color BrownColor2 = new Color(122,93,0);
    Color BrownColor3 = new Color(52,33,0);
    Color mouthColor1 = new Color(192,93,0);
    Color mouthColor2 = new Color(182,63,0);

    int strokeNum = 7; // old stuff...
    BasicStroke thisStroke = new BasicStroke(strokeNum);


    public static double delta_x;
    public static double delta_y;
    public static double tx;
    public static double ty;

    public static double direction;

    public static boolean moveLeftCountOn = false;
    public static int moveLeftCount = 0;
    public static boolean moveRightCountOn = false;
    public static int moveRightCount = 0;

    public int movingleftright = 0;
    public final int NOTLEFTRIGHT = 0;
    public final int MOVING_LEFT = 1;
    public final int MOVING_RIGHT = 2;


    public turtleHero(int x, int y) {
        super(x,y);
        base = new Rectangle2D.Float(x - BASE_SIZE/2, y - BASE_SIZE/2,
                BASE_SIZE, BASE_SIZE); // width and height
        barrel = new Line2D.Float(x, y, x, y - BARREL_LEN); // 3rd x is in the middle, but 4th y goes down to center

        tx = location.x-14;
        ty = location.y-2000;

        delta_x = tx - (location.x-14);
        delta_y = ty - (location.y);
        direction = Math.atan2(delta_y, delta_x);

        friendINDEX = 1;
        energyValue = 0;

        characterLife = 3;
        villageLife = 3;
    }


    @Override
    public void render(Graphics2D g2) {
        //draw turtle hero head
        stroke = new BasicStroke(2);
        g2.setStroke(stroke);

        if (movingleftright == NOTLEFTRIGHT) {
            //        color = new Color(31,113,86); g2.setColor(color);// turtle body color
            g2.setColor(GreenColor1);
            g2.fillOval((int) location.x - 30 - 1 - 0+2, (int) location.y + 5 - 2 - 1 - 1, 32, 38);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
            g2.setColor(GreenColor2);
            g2.drawOval((int) location.x - 30 - 1 + 0+2, (int) location.y + 5 - 2 - 1 - 1, 32, 38);
        } else if (movingleftright == MOVING_LEFT) {
            g2.setColor(GreenColor1);
            g2.fillOval((int) location.x - 30 - 1 - 0, (int) location.y + 5 - 2 - 1 - 1, 30, 38);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
            g2.setColor(GreenColor2);
            g2.drawOval((int) location.x - 30 - 1 + 0, (int) location.y + 5 - 2 - 1 - 1, 30, 38);
        } else if (movingleftright == MOVING_RIGHT) {
            //        color = new Color(31,113,86); g2.setColor(color);// turtle body color
            g2.setColor(GreenColor1);
            g2.fillOval((int) location.x - 30 - 1 - 0+3, (int) location.y + 5 - 2 - 1 - 1, 30, 38);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
            g2.setColor(GreenColor2);
            g2.drawOval((int) location.x - 30 - 1 + 0+3, (int) location.y + 5 - 2 - 1 - 1, 30, 38);
        }
////        color = new Color(31,113,86); g2.setColor(color);// turtle body color
//        g2.setColor(GreenColor1);
//        g2.fillOval((int)location.x-30,(int)location.y,32,37);
////        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.setColor(GreenColor2);
//        g2.drawOval((int)location.x-30,(int)location.y,32,37);

        //draw right hand (turn left change)
        int[] x1 = {(int) location.x - 1, (int) location.x + 27-3, (int) location.x + 39-3, (int) location.x + 3};
        int[] y1 = {(int) location.y - 57 + 100, (int) location.y - 52 + 100, (int) location.y - 25 + 100, (int) location.y - 31 + 100};
//        color = new Color(31,113,86); g2.setColor(color);// turtle body color
        int[] x8 = {(int) location.x - 1, (int) location.x + 27-10, (int) location.x + 39-10, (int) location.x + 3};
        int[] y8 = {(int) location.y - 57 + 100, (int) location.y - 52 + 100+5, (int) location.y - 25 + 100+5, (int) location.y - 31 + 100};
//        color = new Color(31,113,86); g2.setColor(color);// turtle body color
        int[] x12 = {(int) location.x - 1, (int) location.x + 27-5, (int) location.x + 39-5, (int) location.x + 3};
        int[] y12 = {(int) location.y - 57 + 100, (int) location.y - 52 + 100+1, (int) location.y - 25 + 100-1, (int) location.y - 31 + 100};
//        color = new Color(31,113,86); g2.setColor(color);// turtle body color


        if (movingleftright == NOTLEFTRIGHT) {
            g2.setColor(GreenColor1);
            g2.fillPolygon(x1, y1, 4);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
            g2.setColor(GreenColor2);
            g2.drawPolygon(x1, y1, 4);
        } else if (movingleftright == MOVING_LEFT){
            g2.setColor(GreenColor1);
            g2.fillPolygon(x8, y8, 4);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
            g2.setColor(GreenColor2);
            g2.drawPolygon(x8, y8, 4);
        } else if (movingleftright == MOVING_RIGHT){
            g2.setColor(GreenColor1);
            g2.fillPolygon(x12, y12, 4);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
            g2.setColor(GreenColor2);
            g2.drawPolygon(x12, y12, 4);
        }


        //draw left hand
        int[] x2 = {(int) location.x - 25, (int) location.x - 53+3, (int) location.x - 65+3, (int) location.x - 29};
        int[] y2 = {(int) location.y - 57 + 100, (int) location.y - 52 + 100, (int) location.y - 25 + 100, (int) location.y - 31 + 100};
//        color = new Color(31,113,86); g2.setColor(color);// turtle body color
        int[] x9 = {(int) location.x - 25, (int) location.x - 53+5, (int) location.x - 65+5, (int) location.x - 29};
        int[] y9 = {(int) location.y - 57 + 100, (int) location.y - 52 + 100+1, (int) location.y - 25 + 100-1, (int) location.y - 31 + 100};
//        color = new Color(31,113,86); g2.setColor(color);// turtle body color
        int[] x13 = {(int) location.x - 25, (int) location.x - 53+10, (int) location.x - 65+10, (int) location.x - 29};
        int[] y13 = {(int) location.y - 57 + 100, (int) location.y - 52 + 100+5, (int) location.y - 25 + 100+5, (int) location.y - 31 + 100};

        if (movingleftright == NOTLEFTRIGHT) {
            g2.setColor(GreenColor1);
            g2.fillPolygon(x2, y2, 4);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
            g2.setColor(GreenColor2);
            g2.drawPolygon(x2, y2, 4);

        } else if (movingleftright == MOVING_LEFT) {
            g2.setColor(GreenColor1);
            g2.fillPolygon(x9, y9, 4);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
            g2.setColor(GreenColor2);
            g2.drawPolygon(x9, y9, 4);
        } else if (movingleftright == MOVING_RIGHT) {
            g2.setColor(GreenColor1);
            g2.fillPolygon(x13, y13, 4);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
            g2.setColor(GreenColor2);
            g2.drawPolygon(x13, y13, 4);
        }


        //draw left leg
        int[] x3 = {(int) location.x - 25, (int) location.x - 40, (int) location.x - 31, (int) location.x - 30
                , (int) location.x - 23, (int) location.x - 22, (int) location.x - 14};
        int[] y3 = {(int) location.y + 78, (int) location.y + 114, (int) location.y + 105, (int) location.y + 114
                , (int) location.y + 105, (int) location.y + 116, (int) location.y + 85};
//        color = new Color(31,113,86); g2.setColor(color);// turtle body color
        int[] x10 = {(int) location.x - 25, (int) location.x - 40-2, (int) location.x - 31-2, (int) location.x - 30-2
                , (int) location.x - 23-2, (int) location.x - 22-2, (int) location.x - 14};
        int[] y10 = {(int) location.y + 78, (int) location.y + 114, (int) location.y + 105, (int) location.y + 114
                , (int) location.y + 105, (int) location.y + 116, (int) location.y + 85};
        //        color = new Color(31,113,86); g2.setColor(color);// turtle body color
        int[] x14 = {(int) location.x - 25+3, (int) location.x - 40-2+5, (int) location.x - 31-2+5, (int) location.x - 30-2+5
                , (int) location.x - 23-2+4, (int) location.x - 22-2+4, (int) location.x - 14+3};
        int[] y14 = {(int) location.y + 78, (int) location.y + 114-4, (int) location.y + 105-3, (int) location.y + 114-3
                , (int) location.y + 105-3, (int) location.y + 116-3, (int) location.y + 85};

        if (movingleftright == NOTLEFTRIGHT) {
        g2.setColor(GreenColor1);
        g2.fillPolygon(x3, y3, 7);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
        g2.setColor(GreenColor2);
        g2.drawPolygon(x3, y3, 7);
        } else if (movingleftright == MOVING_LEFT) {
            g2.setColor(GreenColor1);
            g2.fillPolygon(x10, y10, 7);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
            g2.setColor(GreenColor2);
            g2.drawPolygon(x10, y10, 7);
        } else if (movingleftright == MOVING_RIGHT) {
            g2.setColor(GreenColor1);
            g2.fillPolygon(x14, y14, 7);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
            g2.setColor(GreenColor2);
            g2.drawPolygon(x14, y14, 7);
        }

        //draw right leg
        int[] x4 = {(int) location.x + 3 - 2, (int) location.x + 18 - 2, (int) location.x + 9 - 2, (int) location.x + 8 - 2
                , (int) location.x + 1 - 2, (int) location.x + 0 - 2, (int) location.x - 8 - 2};
        int[] y4 = {(int) location.y + 78, (int) location.y + 114, (int) location.y + 105, (int) location.y + 114
                , (int) location.y + 105, (int) location.y + 116, (int) location.y + 85};
//        color = new Color(31,113,86); g2.setColor(color);// turtle body color
        int[] x11 = {(int) location.x + 3 - 2-3, (int) location.x + 18 - 2-5, (int) location.x + 9 - 2-5, (int) location.x + 8 - 2-5
                , (int) location.x + 1 - 2-4, (int) location.x + 0 - 2-4, (int) location.x - 8 - 2-3};
        int[] y11 = {(int) location.y + 78, (int) location.y + 114-4, (int) location.y + 105-3, (int) location.y + 114-3
                , (int) location.y + 105-3, (int) location.y + 116-3, (int) location.y + 85};
        //        color = new Color(31,113,86); g2.setColor(color);// turtle body color
        int[] x15 = {(int) location.x + 3 - 2-3, (int) location.x + 18 - 2 +2, (int) location.x + 9 - 2 +2, (int) location.x + 8 - 2+2
                , (int) location.x + 1 - 2+2, (int) location.x + 0 - 2+2, (int) location.x - 8 - 2};
        int[] y15 = {(int) location.y + 78, (int) location.y + 114-4, (int) location.y + 105-3, (int) location.y + 114-3
                , (int) location.y + 105-3, (int) location.y + 116-3, (int) location.y + 85};

        if (movingleftright == NOTLEFTRIGHT) {
        g2.setColor(GreenColor1);
        g2.fillPolygon(x4, y4, 7);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
        g2.setColor(GreenColor2);
        g2.drawPolygon(x4, y4, 7);
        } else if (movingleftright == MOVING_LEFT) {
            g2.setColor(GreenColor1);
            g2.fillPolygon(x11, y11, 7);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
            g2.setColor(GreenColor2);
            g2.drawPolygon(x11, y11, 7);
        } else if (movingleftright == MOVING_RIGHT) {
            g2.setColor(GreenColor1);
            g2.fillPolygon(x15, y15, 7);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
            g2.setColor(GreenColor2);
            g2.drawPolygon(x15, y15, 7);
        }


        //draw left eye
//        double degrees1 = 0.25;
//        AffineTransform old1 = g2.getTransform();
//        g2.rotate(Math.toRadians(degrees1));
        stroke = new BasicStroke(1);
        g2.setStroke(stroke);

        if (movingleftright == NOTLEFTRIGHT) {
            color = Color.WHITE;
            g2.setColor(color);
            g2.fillOval((int) location.x - 22 + 2 - 2 - 4 - 1 + 1 - 1, (int) location.y + 8 + 2 - 1 + 1 - 1 - 1, 10, 9); // original 2,2
            color = Color.BLACK;
            g2.setColor(color);
            g2.fillOval((int) location.x - 22 - 2 - 4 + 2 + 2 - 1 + 1 + 1 - 1, (int) location.y + 8 + 2 - 1 + 2 - 1 - 1 - 1, 8, 8); // original 5,9
            color = Color.WHITE;
            g2.setColor(color);
            g2.fillOval((int) location.x - 22 - 2 - 4 + 2 + 2 + 2 - 1 + 1 - 1, (int) location.y + 8 + 2 - 1 + 2 + 2 - 2 - 1 - 1 - 1 - 1, 4, 4); // original 5,9

            g2.setColor(new Color(233, 233, 233));
            g2.drawOval((int) location.x - 22 + 2 - 2 - 4 - 1 + 1 - 1 + 1, (int) location.y + 8 + 2 - 1 + 1 - 1 - 1, 10, 8); // original 2,2
            color = Color.BLACK;
            g2.setColor(color);
            g2.drawOval((int) location.x - 22 - 2 - 4 + 2 + 2 - 1 + 1 + 1 - 1, (int) location.y + 8 + 2 - 1 + 2 - 1 - 1 - 1, 8, 7); // original 5,9
        } else if (movingleftright == MOVING_LEFT) {
            color = Color.WHITE;
            g2.setColor(color);
            g2.fillOval((int) location.x - 22 + 2 - 2 - 4 - 1 + 1 - 1 - 2, (int) location.y + 8 + 2 - 1 + 1 - 1 - 1, 9, 9); // original 2,2
            color = Color.BLACK;
            g2.setColor(color);
            g2.fillOval((int) location.x - 22 - 2 - 4 + 2 + 2 - 1 + 1 + 1 - 1 - 2, (int) location.y + 8 + 2 - 1 + 2 - 1 - 1 - 1, 7, 8); // original 5,9
            color = Color.WHITE;
            g2.setColor(color);
            g2.fillOval((int) location.x - 22 - 2 - 4 + 2 + 2 + 2 - 1 + 1 - 1 - 2, (int) location.y + 8 + 2 - 1 + 2 + 2 - 2 - 1 - 1 - 1 - 1, 3, 4); // original 5,9

            g2.setColor(new Color(233, 233, 233));
            g2.drawOval((int) location.x - 22 + 2 - 2 - 4 - 1 + 1 - 1 + 1 - 2, (int) location.y + 8 + 2 - 1 + 1 - 1 - 1, 9, 8); // original 2,2
            color = Color.BLACK;
            g2.setColor(color);
            g2.drawOval((int) location.x - 22 - 2 - 4 + 2 + 2 - 1 + 1 + 1 - 1 - 2, (int) location.y + 8 + 2 - 1 + 2 - 1 - 1 - 1, 7, 7); // original 5,9

        } else if (movingleftright == MOVING_RIGHT) {
            color = Color.WHITE;
            g2.setColor(color);
            g2.fillOval((int) location.x - 22 + 2 - 2 - 4 - 1 + 1 - 1 + 1, (int) location.y + 8 + 2 - 1 + 1 - 1 - 1+1, 9, 9); // original 2,2
            color = Color.BLACK;
            g2.setColor(color);
            g2.fillOval((int) location.x - 22 - 2 - 4 + 2 + 2 - 1 + 1 + 1 - 1 + 1, (int) location.y + 8 + 2 - 1 + 2 - 1 - 1 - 1+1, 7, 8); // original 5,9
            color = Color.WHITE;
            g2.setColor(color);
            g2.fillOval((int) location.x - 22 - 2 - 4 + 2 + 2 + 2 - 1 + 1 - 1 + 1, (int) location.y + 8 + 2 - 1 + 2 + 2 - 2 - 1 - 1 - 1 - 1+1, 3, 4); // original 5,9

            g2.setColor(new Color(233, 233, 233));
            g2.drawOval((int) location.x - 22 + 2 - 2 - 4 - 1 + 1 - 1 + 1 + 1, (int) location.y + 8 + 2 - 1 + 1 - 1 - 1+1, 9, 8); // original 2,2
            color = Color.BLACK;
            g2.setColor(color);
            g2.drawOval((int) location.x - 22 - 2 - 4 + 2 + 2 - 1 + 1 + 1 - 1 + 1, (int) location.y + 8 + 2 - 1 + 2 - 1 - 1 - 1+1, 7, 7); // original 5,9

        }

//        color = Color.BLACK; g2.setColor(color);
//        g2.fillOval((int)location.x-22-2,(int)location.y+8-1,5,9); // original 5,9
//        color = Color.WHITE; g2.setColor(color);
//        g2.drawOval((int)location.x-22-2,(int)location.y+8-1,5,9); // original 5,9
//        color = Color.WHITE; g2.setColor(color);
//        g2.fillOval((int)location.x-22+2-2,(int)location.y+8+2-1,2,2); // original 2,2

//        color = Color.BLACK; g2.setColor(color);
//        g2.fillOval((int)location.x-22-2,(int)location.y+8-1,5,9); // original 5,9
//        color = Color.WHITE; g2.setColor(color);
//        g2.drawOval((int)location.x-22-2,(int)location.y+8-1,5,9); // original 5,9
//        color = Color.WHITE; g2.setColor(color);
//        g2.fillOval((int)location.x-22+2-2,(int)location.y+8+2-1,2,2); // original 2,2


//        g2.setTransform(old1);
//        color = Color.BLACK; g2.setColor(color);
//        g2.drawOval((int)location.x-22+2,(int)location.y+8+2,2,2);

        //draw right eye
//        double degrees2 = 0.25;
//        AffineTransform old2 = g2.getTransform();
//        g2.rotate(Math.toRadians(degrees2));
        stroke = new BasicStroke(1);
        g2.setStroke(stroke);

        if (movingleftright == NOTLEFTRIGHT) {

        color = Color.WHITE; g2.setColor(color);
        g2.fillOval((int)location.x-22+10+1+1+1,(int)location.y+8+2-1+1-1-1,10,9); // original 2,2
        color = Color.BLACK; g2.setColor(color);
        g2.fillOval((int)location.x-22+10+2+1+1-1+1+1,(int)location.y+8+2-1+2-1-1-1,8,8); // original 5,9
        color = Color.WHITE; g2.setColor(color);
        g2.fillOval((int)location.x-22+10+2+2+1+1+1-1,(int)location.y+8+2-1+2+2-2-1-1-1-1,4,4); // original 5,9

        g2.setColor(new Color(233,233,233));
        g2.drawOval((int)location.x-22+10+1+1+1,(int)location.y+8+2-1+1-1-1,10,8); // original 2,2
        color = Color.BLACK; g2.setColor(color);
        g2.drawOval((int)location.x-22+10+2+1+1-1+1+1,(int)location.y+8+2-1+2-1-1-1,8,7); // original 5,9
        } else if (movingleftright == MOVING_LEFT) {
            color = Color.WHITE;
            g2.setColor(color);
            g2.fillOval((int) location.x - 22 + 10 + 1 + 1 + 1-4, (int) location.y + 8 + 2 - 1 + 1 - 1 - 1+1, 9, 9); // original 2,2
            color = Color.BLACK;
            g2.setColor(color);
            g2.fillOval((int) location.x - 22 + 10 + 2 + 1 + 1 - 1 + 1 + 1-4, (int) location.y + 8 + 2 - 1 + 2 - 1 - 1 - 1+1, 7, 8); // original 5,9
            color = Color.WHITE;
            g2.setColor(color);
            g2.fillOval((int) location.x - 22 + 10 + 2 + 2 + 1 + 1 + 1 - 1-4, (int) location.y + 8 + 2 - 1 + 2 + 2 - 2 - 1 - 1 - 1 - 1+1, 3, 4); // original 5,9

            g2.setColor(new Color(233, 233, 233));
            g2.drawOval((int) location.x - 22 + 10 + 1 + 1 + 1-4, (int) location.y + 8 + 2 - 1 + 1 - 1 - 1+1, 9, 8); // original 2,2
            color = Color.BLACK;
            g2.setColor(color);
            g2.drawOval((int) location.x - 22 + 10 + 2 + 1 + 1 - 1 + 1 + 1-4, (int) location.y + 8 + 2 - 1 + 2 - 1 - 1 - 1+1, 7, 7); // original 5,9

        } else if (movingleftright == MOVING_RIGHT) {
            color = Color.WHITE;
            g2.setColor(color);
            g2.fillOval((int) location.x - 22 + 10 + 1 + 1 + 1+0, (int) location.y + 8 + 2 - 1 + 1 - 1 - 1, 9, 9); // original 2,2
            color = Color.BLACK;
            g2.setColor(color);
            g2.fillOval((int) location.x - 22 + 10 + 2 + 1 + 1 - 1 + 1 + 1+0, (int) location.y + 8 + 2 - 1 + 2 - 1 - 1 - 1, 7, 8); // original 5,9
            color = Color.WHITE;
            g2.setColor(color);
            g2.fillOval((int) location.x - 22 + 10 + 2 + 2 + 1 + 1 + 1 - 1+0, (int) location.y + 8 + 2 - 1 + 2 + 2 - 2 - 1 - 1 - 1 - 1, 3, 4); // original 5,9

            g2.setColor(new Color(233, 233, 233));
            g2.drawOval((int) location.x - 22 + 10 + 1 + 1 + 1+0, (int) location.y + 8 + 2 - 1 + 1 - 1 - 1, 9, 8); // original 2,2
            color = Color.BLACK;
            g2.setColor(color);
            g2.drawOval((int) location.x - 22 + 10 + 2 + 1 + 1 - 1 + 1 + 1+0, (int) location.y + 8 + 2 - 1 + 2 - 1 - 1 - 1, 7, 7); // original 5,9

        }


        //        color = Color.BLACK; g2.setColor(color);
//        g2.fillOval((int)location.x-22+12,(int)location.y+8-1-1,5,9); // original 5,9
//        color = Color.WHITE; g2.setColor(color);
//        g2.drawOval((int)location.x-22+12,(int)location.y+8-1-1,5,9); // original 5,9
//        color = Color.WHITE; g2.setColor(color);
//        g2.fillOval((int)location.x-22+12+2-1,(int)location.y+8+2-1-1,2,2); // original 2,2

//        g2.setTransform(old2);

        //draw tail
        stroke = new BasicStroke(2);
        g2.setStroke(stroke);
        int[] x5 = {(int)location.x-19, (int)location.x-4, (int)location.x-11 };
        int[] y5 = {(int)location.y+85,(int)location.y+85,(int)location.y+103 };
//        color = new Color(31,113,86); g2.setColor(color);// turtle body color
        int[] x16 = {(int)location.x-19-3, (int)location.x-4-3, (int)location.x-11-3-2 };
        int[] y16 = {(int)location.y+85,(int)location.y+85,(int)location.y+103 };
//        color = new Color(31,113,86); g2.setColor(color);// turtle body color
        int[] x17 = {(int)location.x-19+3, (int)location.x-4+3, (int)location.x-11+0+3+2 };
        int[] y17 = {(int)location.y+85,(int)location.y+85,(int)location.y+103 };

        if (movingleftright == NOTLEFTRIGHT) {

            g2.setColor(GreenColor1);
            g2.fillPolygon(x5,y5,3);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
            g2.setColor(GreenColor2);
            g2.drawPolygon(x5,y5,3);

        } else if (movingleftright == MOVING_LEFT) {
            g2.setColor(GreenColor1);
            g2.fillPolygon(x16, y16, 3);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
            g2.setColor(GreenColor2);
            g2.drawPolygon(x16, y16, 3);
        } else if (movingleftright == MOVING_RIGHT) {
            g2.setColor(GreenColor1);
            g2.fillPolygon(x17, y17, 3);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
            g2.setColor(GreenColor2);
            g2.drawPolygon(x17, y17, 3);
        }

        //draw shell
if (movingleftright == NOTLEFTRIGHT){

    //        color = new Color(34,137,52); g2.setColor(color);
    stroke = new BasicStroke(2);
    g2.setColor(BrownColor1);
    g2.fillOval((int)location.x-36+3,(int)location.y-30+60,41,59);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
    g2.setColor(BrownColor3);
    g2.drawOval((int)location.x-36+3,(int)location.y-30+60,41,59);
}
else if (movingleftright == MOVING_LEFT) {
    g2.setColor(BrownColor2);
    g2.fillOval((int)location.x-36+3-2,(int)location.y-30+60,41,59);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
    g2.setColor(BrownColor3);
    g2.drawOval((int)location.x-36+3-2,(int)location.y-30+60,41,59);

    g2.setColor(BrownColor1);
    g2.fillOval((int)location.x-36+3-2-3,(int)location.y-30+60,38,59);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
    g2.setColor(BrownColor3);
    g2.drawOval((int)location.x-36+3-2-3,(int)location.y-30+60,38,59);
} else if (movingleftright == MOVING_RIGHT) {
    //        color = new Color(34,137,52); g2.setColor(color);
    stroke = new BasicStroke(2);
    g2.setColor(BrownColor2);
    g2.fillOval((int)location.x-36+3+2-2,(int)location.y-30+60,41,59);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
    g2.setColor(BrownColor3);
    g2.drawOval((int)location.x-36+3+2-2,(int)location.y-30+60,41,59);

    g2.setColor(BrownColor1);
    g2.fillOval((int)location.x-36+3+2+3+1,(int)location.y-30+60,38,59);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
    g2.setColor(BrownColor3);
    g2.drawOval((int)location.x-36+3+2+3+1,(int)location.y-30+60,38,59);
}

//        //draw nose
//        color = Color.BLACK; g2.setColor(color);
//        g2.fillOval((int)location.x-17,(int)location.y+0+3,7,3); //+5

        //draw mouth
        g2.setColor(mouthColor2);
        g2.fillOval((int)location.x-17-1-1,(int)location.y+0+2-1,10,4); //+5
        g2.setColor(Color.BLACK);
        g2.drawOval((int)location.x-17-1-1,(int)location.y+0+2-1,10,4); //+5


//        //draw face orange
//        g2.setStroke(new BasicStroke(1));
//        color = new Color(229,164,22); g2.setColor(color);
//        g2.fillOval((int)location.x-23+1-2+1-1,(int)location.y+2+2+1,5,2); //+5
//        g2.fillOval((int)location.x-8-3+2+1+1,(int)location.y+2+2+1,5,2); //+5
//        g2.setColor(color);
//        g2.drawOval((int)location.x-23+1-2+1-1,(int)location.y+2+2+1,5,2); //+5
//        g2.drawOval((int)location.x-8-3+2+1+1,(int)location.y+2+2+1,5,2); //+5

        //draw shell pattern
        stroke = new BasicStroke(2);
        g2.setStroke(stroke);
        int[] x6 = {(int)location.x-20, (int)location.x-5, (int)location.x+0 , (int)location.x-5
                , (int)location.x-20, (int)location.x-25 };
        int[] y6 = {(int)location.y+45+3,(int)location.y+45+3,(int)location.y+55+3 ,(int)location.y+65+3
                ,(int)location.y+65+3,(int)location.y+55+3};

        int[] x7 = {(int)location.x-20-2-5, (int)location.x-5-5-5, (int)location.x+0-5-5 , (int)location.x-5-5-5
                , (int)location.x-20-2-5, (int)location.x-25-2-5 };
        int[] y7 = {(int)location.y+45+3,(int)location.y+45+3,(int)location.y+55+3 ,(int)location.y+65+3
                ,(int)location.y+65+3,(int)location.y+55+3};

        int[] x18 = {(int)location.x-20+2+5, (int)location.x-5+5, (int)location.x+0+5 , (int)location.x-5+5
                , (int)location.x-20+2+5, (int)location.x-25+2+5 };
        int[] y18 = {(int)location.y+45+3,(int)location.y+45+3,(int)location.y+55+3 ,(int)location.y+65+3
                ,(int)location.y+65+3,(int)location.y+55+3};

        if (movingleftright == NOTLEFTRIGHT) {

            color = new Color(115, 113, 40);
            g2.setColor(color);// turtle shell pattern color
            g2.fillPolygon(x6, y6, 6);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
            g2.setColor(BrownColor3);
            g2.drawPolygon(x6, y6, 6);

        } else if (movingleftright == MOVING_LEFT) {
            color = new Color(115, 113, 40);
            g2.setColor(color);// turtle shell pattern color
            g2.fillPolygon(x7, y7, 6);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
            g2.setColor(BrownColor3);
            g2.drawPolygon(x7, y7, 6);
        } else if (movingleftright == MOVING_RIGHT) {
            color = new Color(115, 113, 40);
            g2.setColor(color);// turtle shell pattern color
            g2.fillPolygon(x18, y18, 6);
//        color = new Color(233,230,224); g2.setColor(color); // white outline
            g2.setColor(BrownColor3);
            g2.drawPolygon(x18, y18, 6);
        }

        //draw line pattern
//        color = new Color(233,230,224); g2.setColor(color);
        if (movingleftright == NOTLEFTRIGHT) {
            g2.setColor(BrownColor3);
            g2.drawLine((int)location.x-20,(int)location.y+45+3,(int)location.x-28+2,(int)location.y+37);
            g2.drawLine((int)location.x-5,(int)location.y+45+3,(int)location.x+2-2,(int)location.y+37);
            g2.drawLine((int)location.x-5,(int)location.y+65+3,(int)location.x+2,(int)location.y+75);
            g2.drawLine((int)location.x-20,(int)location.y+65+3,(int)location.x-28,(int)location.y+75);
        } else if (movingleftright == MOVING_LEFT) {
            g2.setColor(BrownColor3);
            g2.drawLine((int) location.x - 20-2-5, (int) location.y + 45 + 3, (int) location.x - 28 -5, (int) location.y + 37);
            g2.drawLine((int) location.x - 5-5-5, (int) location.y + 45 + 3, (int) location.x + 2 - 2-5-4, (int) location.y + 37);
            g2.drawLine((int) location.x - 5-5-5, (int) location.y + 65 + 3, (int) location.x + 2-5-3, (int) location.y + 75+3);
            g2.drawLine((int) location.x - 20-2-5, (int) location.y + 65 + 3, (int) location.x - 28-5+1, (int) location.y + 75+3);

        } else if (movingleftright == MOVING_RIGHT) {
            g2.setColor(BrownColor3);
            g2.drawLine((int) location.x - 20+2+5, (int) location.y + 45 + 3, (int) location.x - 28 +9, (int) location.y + 37);
            g2.drawLine((int) location.x - 5+5, (int) location.y + 45 + 3, (int) location.x + 2 - 2+3+4, (int) location.y + 37);
            g2.drawLine((int) location.x - 5+5, (int) location.y + 65 + 3, (int) location.x + 2+5+3, (int) location.y + 75+3);
            g2.drawLine((int) location.x - 20+2+5, (int) location.y + 65 + 3, (int) location.x - 28+4, (int) location.y + 75+3);

        }



        //draw left hand pattern
        if (movingleftright == NOTLEFTRIGHT) {
        color = new Color(52,73,49); g2.setColor(color);
        g2.fillOval((int)location.x-59,(int)location.y+65,9,3);
        g2.fillOval((int)location.x-52,(int)location.y+59,3,6);
        g2.fillOval((int)location.x-43,(int)location.y+49,6,3);
        g2.fillOval((int)location.x-50,(int)location.y+54,9,6);
        g2.fillOval((int)location.x-45,(int)location.y+61,4,6);

        } else if (movingleftright == MOVING_LEFT) {
            color = new Color(52, 73, 49);
            g2.setColor(color);
            g2.fillOval((int) location.x - 59, (int) location.y + 65, 9, 3);
            g2.fillOval((int) location.x - 52, (int) location.y + 59, 3, 6);
            g2.fillOval((int) location.x - 43, (int) location.y + 49, 6, 3);
            g2.fillOval((int) location.x - 50, (int) location.y + 54, 9, 6);
            g2.fillOval((int) location.x - 45, (int) location.y + 61, 4, 6);
        } else if (movingleftright == MOVING_RIGHT) {
            color = new Color(52, 73, 49);
            g2.setColor(color);
            g2.fillOval((int) location.x - 59+6+1, (int) location.y + 65+3, 8, 3);
            g2.fillOval((int) location.x - 52+6+1, (int) location.y + 59+3, 2, 6);
            g2.fillOval((int) location.x - 43+6+1, (int) location.y + 49+3, 5, 3);
            g2.fillOval((int) location.x - 50+6+1, (int) location.y + 54+3, 8, 6);
            g2.fillOval((int) location.x - 45+6+1, (int) location.y + 61+3, 3, 6);
        }

        //draw right hand pattern (turn left change)
        if (movingleftright == NOTLEFTRIGHT) {
            color = new Color(52, 73, 49);
            g2.setColor(color);
            g2.fillOval((int) location.x + 25, (int) location.y + 65 - 2, 5, 8);
            g2.fillOval((int) location.x + 20, (int) location.y + 59, 3, 6);
            g2.fillOval((int) location.x + 11, (int) location.y + 49, 6, 3);
            g2.fillOval((int) location.x + 15 + 3, (int) location.y + 54 - 3, 8, 7);
            g2.fillOval((int) location.x + 13, (int) location.y + 61 - 4, 4, 9);
        } else if (movingleftright == MOVING_LEFT) {
            color = new Color(52, 73, 49);
            g2.setColor(color);
            g2.fillOval((int) location.x + 25-6, (int) location.y + 65 - 2+3, 4, 8);
            g2.fillOval((int) location.x + 20-6, (int) location.y + 59+3, 2, 6);
            g2.fillOval((int) location.x + 11-6, (int) location.y + 49+3, 5, 3);
            g2.fillOval((int) location.x + 15 + 3-6, (int) location.y + 54 - 3+3, 7, 7);
            g2.fillOval((int) location.x + 13-6, (int) location.y + 61 - 4+3, 3, 9);
        } else if (movingleftright == MOVING_RIGHT) {
            color = new Color(52, 73, 49);
            g2.setColor(color);
            g2.fillOval((int) location.x + 25-1, (int) location.y + 65 - 2, 5, 6);
            g2.fillOval((int) location.x + 20-1, (int) location.y + 59, 3, 6);
            g2.fillOval((int) location.x + 11-1, (int) location.y + 49, 6, 3);
            g2.fillOval((int) location.x + 15 + 3-1, (int) location.y + 54 - 3, 8, 7);
            g2.fillOval((int) location.x + 13-1, (int) location.y + 61 - 4, 4, 6);

        }


//        g2.setColor(Color.YELLOW);
//        g2.draw(barrel);
//        g2.draw(base);
    }

    @Override
    public void update() {
        MousePointer mousePointer = (MousePointer) Main.gameData.fixedObject.get(Main.INDEX_MOUSE_POINTER);
        // Game Figure type to Mouse Pointer type ^^^

        if (moveLeftCountOn == true && moveRightCountOn == false) {
            movingleftright = MOVING_LEFT;
        }else if (moveRightCountOn == true && moveLeftCountOn == false) {
            movingleftright = MOVING_RIGHT;
        }else if (moveLeftCountOn == true && moveRightCountOn == true){
            if (moveLeftCount < moveRightCount) {
                movingleftright = MOVING_LEFT;
            } else if (moveLeftCount > moveRightCount) {
                movingleftright = MOVING_RIGHT;
            } else movingleftright = NOTLEFTRIGHT;
        }else{
            movingleftright = NOTLEFTRIGHT;
        }

    }

    @Override
    public int getCollisionRadius() {
        return 40;
    } // turtle hero center

}
