package model;

import controller.Main;


import java.awt.*;
import java.awt.geom.Point2D;

public class Damage extends GameFigure {

    String text;
    Color color;
    Font font;


    int state;
    public static final int STATE_MOVING = 0;
    public static final int STATE_DONE = 1;

    Point2D.Float generateLocation;
    Color originalColor;
    int colorChange =0;

    public Damage(String text, int x, int y, Color color, Font font) {
        super(x,y);
        this.text = text;
        this.color = color;
        originalColor = color;
        this.font = font;
        state = STATE_MOVING;
        generateLocation = new Point2D.Float(x,y);


    }



    @Override
    public void render(Graphics2D g2) {
        this.font = new Font(this.font.getName(),Font.BOLD,this.font.getSize()+1);

        if (colorChange %5 ==0 ) {this.color = new Color(250,93,25); colorChange++;}
        else {this.color = originalColor; colorChange++;}

        g2.setFont(font);
        g2.setColor(color);
        g2.drawString(text, (int)location.x, (int)location.y);


    }

    @Override
    public void update() {
        updateState();
    }

    private void updateState() {
        if (state == STATE_MOVING) {
//            if (hitCount >0 && !(hitByStrongCount >0) && enemyHP <=0) {
//                state = STATE_ESCAPING;
//                animStrategy = new lobsterMonAnimEscaping(this);
//                System.out.println("hi");
//            }
//            if (hitCount >0 && hitByStrongCount >0)  {
//                state = STATE_EXPLODE;
//                animStrategy = new lobsterMonAnimHurt(this);
//            }

            location.y-=1;
            if ((int)location.y %10 ==0) location.x+=1;
            if (generateLocation.y-location.y>=25){
                state = STATE_DONE;
            }

        }

        else if (state == STATE_DONE) {
            super.done = true;
//            Main.createUFO = true;
//
//            notifyEvent(); // lobsterMon died
        }

    }

    @Override
    public int getCollisionRadius() {
        return 0;
    }
}
