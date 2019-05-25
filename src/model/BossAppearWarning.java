package model;

import controller.enemyobserver.EnemyObserver;
import controller.enemyobserver.EnemySubject;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class BossAppearWarning extends GameFigure implements EnemySubject {

    String text;
    Color color;
    Font font;


    Color redColor = new Color (255,51,0);
    public static boolean WARNINGFlashingOn = false;
    public static int WARNINGcolorChangeTime =0;
    public static int WARNINGcolorChange =0;

    int state;
    public static final int STATE_FLASHING = 0;
    public static final int STATE_DONE = 1;
    public static boolean WARNINGFlashingEnded = false;

    Point2D.Float generateLocation;
    Point2D.Float biggerSizeLocation;
    Point2D.Float biggestSizeLocation;

    Font normalfont;
    Font biggerfont;
    Font biggestfont;

    ArrayList<EnemyObserver> listeners = new ArrayList<>();

    public BossAppearWarning(String text, int x, int y, Color color, Font font) {
        super(x,y);
        this.text = text;
        this.color = color;
        this.font = font;


        state = STATE_FLASHING;

        this.normalfont = font;
        this.biggerfont = new Font(this.font.getName(),Font.BOLD,this.font.getSize()+20);
        this.biggestfont = new Font(this.font.getName(),Font.BOLD,this.font.getSize()+40);

        generateLocation = new Point2D.Float(x,y);
        biggerSizeLocation = new Point2D.Float(x+20-10+5,y-5);
        biggestSizeLocation = new Point2D.Float(x-10-5-5-5,y);

        WARNINGFlashingOn = true;
        WARNINGcolorChange = 0; //RESET THE WARNING color change everytime restart
    }



    @Override
    public void render(Graphics2D g2) {

        switch(WARNINGcolorChange){ // FORGOT break; again= =
            case 0:
                color = Color.BLACK;
                font = normalfont;
                break;
            case 1:
                color = Color.WHITE;
                font = biggerfont;
                break;
            case 2:
                color = Color.RED;
                font = biggestfont;
                break;
        }

        g2.setFont(font);
        g2.setColor(color);
        g2.drawString("WARNING", (int)location.x, (int)location.y);


    }

    @Override
    public void update() {
        updateState();
    }

    private void updateState() {
        if (state == STATE_FLASHING) {
            if (WARNINGcolorChangeTime % 7 ==0){
                if (WARNINGcolorChange <2) WARNINGcolorChange++;
                else WARNINGcolorChange =0;
            }

//            if (hitCount >0 && !(hitByStrongCount >0) && enemyHP <=0) {
//                state = STATE_ESCAPING;
//                animStrategy = new lobsterMonAnimEscaping(this);
//                System.out.println("hi");
//            }
//            if (hitCount >0 && hitByStrongCount >0)  {
//                state = STATE_EXPLODE;
//                animStrategy = new lobsterMonAnimHurt(this);
//            }

            switch(WARNINGcolorChange){ // switch statement always break = =...
                case 0:
                    location = generateLocation;
                    break;
                case 1:
                    location = biggerSizeLocation;
                    break;
                case 2:
                    location = biggestSizeLocation;
                    break;
            }

            if (WARNINGFlashingOn == false){
                state = STATE_DONE;
                BossAppearWarning.WARNINGFlashingEnded = true; // THIS SHOULD BE SET TO TRUE AFTER BOSS APPEAR FINISH
                notifyEvent();
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

    @Override
    public void attachListener(EnemyObserver o) {
        listeners.add(o);
    }

    @Override
    public void detachListener(EnemyObserver o) {
        listeners.remove(o);
    }

    @Override
    public void notifyEvent() {
        for (var o: listeners) {
            o.eventReceived();
        }

    }
}
