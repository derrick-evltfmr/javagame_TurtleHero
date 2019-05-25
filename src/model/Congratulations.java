package model;

import controller.enemyobserver.EnemyObserver;
import controller.enemyobserver.EnemySubject;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Congratulations extends GameFigure implements EnemySubject {

    String text;
    Color color;
    Font font;


    Color orgYellowColor = new Color(255,204,0);
    Color deepYellowColor = new Color(255,255,102);
    Color lightYellowColor = new Color (255,255,179);
    Color redColor = new Color (255,51,0);
    public static boolean CongratulationsFlashingOn = false;
    public static int CongratulationscolorChangeTime =0;
    public static int CongratulationscolorChange =0;

    int state;
    public static final int STATE_FLASHING = 0;
    public static final int STATE_DONE = 1;
    public static boolean CongratulationsFlashingEnded = false;

    Point2D.Float generateLocation;
    Point2D.Float biggerSizeLocation;
    Point2D.Float biggestSizeLocation;

    Font normalfont;
    Font biggerfont;
    Font biggestfont;

    ArrayList<EnemyObserver> listeners = new ArrayList<>();

    public Congratulations(String text, int x, int y, Color color, Font font) {
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

        CongratulationsFlashingOn = true;
        CongratulationscolorChange = 0; //RESET THE WARNING color change everytime restart
    }



    @Override
    public void render(Graphics2D g2) {

        switch(CongratulationscolorChange){ // FORGOT break; again= =
            case 0:
                color = deepYellowColor;
                font = biggestfont;
                break;
            case 1:
                color = lightYellowColor;
                font = biggestfont;
                break;
            case 2:
                color = orgYellowColor;
                font = biggestfont;
                break;
        }

        g2.setFont(font);
        g2.setColor(color);
        g2.drawString("CONGRATULATIONS!", (int)location.x, (int)location.y);


    }

    @Override
    public void update() {
        updateState();
    }

    private void updateState() {
        if (state == STATE_FLASHING) {
            if (CongratulationscolorChangeTime % 5 ==0){
                if (CongratulationscolorChange <2) CongratulationscolorChange++;
                else CongratulationscolorChange =0;
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

            location = biggestSizeLocation;

//            switch(CongratulationscolorChange){ // switch statement always break = =...
//                case 0:
//                    location = generateLocation;
//                    break;
//                case 1:
//                    location = biggerSizeLocation;
//                    break;
//                case 2:
//                    location = biggestSizeLocation;
//                    break;
//            }

//            if (CongratulationsFlashingOn == false){
//                state = STATE_DONE;
//                Congratulations.CongratulationsFlashingEnded = true; // THIS SHOULD BE SET TO TRUE AFTER BOSS APPEAR FINISH
//                notifyEvent();
//            }

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
