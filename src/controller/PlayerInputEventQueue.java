package controller;

import controller.enemyobserver.EnemyCreateEvent;
import model.*;
import model.sparklingbubble.SparklingBubble;
import model.bubble.Bubble;
import model.turtlehero.turtleHero;
import model.waterbeam.WaterBeam;
import model.whirlpoolshield.WhirlpoolShield;
import view.MyCanvas;
import view.MyWindow;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import static controller.Main.gameData;
import static controller.Main.gamePaused;

public class PlayerInputEventQueue {

    public static LinkedList<InputEvent> queue = new LinkedList<>();

    public static boolean queueEmptyOK = false;
    public static int queueTempTimeClock = -1;

    public static boolean spaceSwitchOn = false;
    public static int spaceSwitchCount = 0;

    public static boolean enterSwitchOn = false;
    public static int enterSwitchCount = 0;

    public int cheatcodepasswordCount = 0;
    public boolean cheatcodepasswordCorrect = false;

    public static int weaponForm = 0;
    public static final int WEAPON_NORMALBUBBLE = 0;
    public static final int WEAPON_WATERBEAM = 1;
    public static final int WEAPON_SPARKLINGBUBBLE = 2;

    public static boolean tutorialScreenOn = false;

    public static boolean normalBubbleCDOn = false;
    public static int normalBubbleCoolDown = 0;

    public static int waterBeamCoolDown = 0;
    public static boolean waterBeamCDOn = false;
    public static int waterBeamUsedEnergyCD = 0;
    public static boolean waterBeamUsedEnergyOn = false;

    public static int sparklingBubbleCoolDown = 0;
    public static boolean sparklingBubbleCDOn = false;
    public static int sparklingBubbleUsedEnergyCD = 0;
    public static boolean sparklingBubbleUsedEnergyOn = false;

    public static int whirlpoolShieldCoolDown = 0;
    public static boolean whirlpoolShieldCDOn = false;
    public static int whirlpoolShieldUsedEnergyCD = 0;
    public static boolean whirlpoolShieldUsedEnergyOn = false;

    public static int angleCount = 0;

    public void processInputEvents() {

        while (!queue.isEmpty()) {
            queueEmptyOK = false;
            int temptime = Main.TimeClock;
            if (queueTempTimeClock == -1) {
                queueTempTimeClock = temptime;
            } else if (queueTempTimeClock ==temptime){ // queueTempTimeClock == temptime <original
                queueTempTimeClock = -1;
            } else {
                queueTempTimeClock = Main.TimeClock;
            }
            if (!queue.isEmpty()) queueEmptyOK = true;
            if (!queue.isEmpty()&&queueEmptyOK&&queueTempTimeClock!=-1) { // avoid 1 instant run twice
                //double check //java.base/java.util.LinkedList.removeFirst(LinkedList.java:274) NoSuchElementException

                try {
                    InputEvent inputEvent = queue.removeFirst();
                switch (inputEvent.type) {

                    case InputEvent.MOUSE_PRESSED: // do something when click
                        var turtlehero_test = gameData.fixedObject.get(Main.INDEX_TURTLEHERO);
                        MouseEvent e = (MouseEvent) inputEvent.event;
                        Point2D.Float mcLocation = new Point2D.Float(e.getX(), e.getY());
                        System.out.println(mcLocation);

//                        double test_delta_x = mcLocation.x - (turtlehero_test.location.x - 14);
//                        double test_delta_y = mcLocation.y - (turtlehero_test.location.y + 1);
//                        double test_direction = Math.atan2(test_delta_y, test_delta_x);
//                        System.out.println(test_direction);
//
//                        if (!enterSwitchOn) {
//                            Bubble m = new Bubble(e.getX(), e.getY());
//                            gameData.friendObject.add(m);
//                        } else {
//                            SparklingBubble m = new SparklingBubble(e.getX(), e.getY());
//                            gameData.friendObject.add(m);
//                        }

                        break;// break mousepressed

                    case InputEvent.MOUSE_MOVED: // do something when mouse move
                        MousePointer mp = (MousePointer) gameData.fixedObject.get(1);
                        MouseEvent me = (MouseEvent) inputEvent.event;
                        mp.location.x = me.getX();
                        mp.location.y = me.getY();
                        break;// break mousemoved

                    case InputEvent.KEY_PRESSED:
                        var turtlehero = gameData.fixedObject.get(Main.INDEX_TURTLEHERO);
//                    var shooterSub = turtlehero.getClass();
                        KeyEvent ke = (KeyEvent) inputEvent.event; // typecast to keyevent, which is supercls of eventobject

                        switch (ke.getKeyCode()) {
                            case KeyEvent.VK_W:
                                MyWindow.pauseButton.doClick(); // simulate to click the pause button
                                break; // don't forget breeaaaakkkkkkkkk......

                            case KeyEvent.VK_UP:
                                if (turtlehero.location.y > 0) {
                                    turtlehero.location.y -= turtleHero.UNIT_MOVE;
                                }
                                autoAdjustAngle();
                                break;
                            case KeyEvent.VK_DOWN:
                                if (turtlehero.location.y + 124 <= 680) {
                                    turtlehero.location.y += turtleHero.UNIT_MOVE;
                                }
                                autoAdjustAngle();
                                break;
                            case KeyEvent.VK_LEFT:
                                if (turtlehero.location.x - 32 > 0) {
                                    turtlehero.location.x -= turtleHero.UNIT_MOVE;
                                }
                                autoAdjustAngle();
                                turtleHero.moveLeftCountOn = true;
                                turtleHero.moveLeftCount = 0;
                                break;
                            case KeyEvent.VK_RIGHT:
                                if (turtlehero.location.x + 21 <= 1200) {
                                    turtlehero.location.x += turtleHero.UNIT_MOVE;
                                }
                                autoAdjustAngle();
                                turtleHero.moveRightCountOn = true;
                                turtleHero.moveRightCount = 0;
                                break;

                            case KeyEvent.VK_SPACE:
                                if (angleCount == 0) { // set angle setting when angle == 0
                                    turtleHero.tx = turtlehero.location.x - 14;
                                    turtleHero.ty = turtlehero.location.y - 2000;
                                    turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                    turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                    turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                }

                                if (weaponForm == WEAPON_NORMALBUBBLE) {
                                    if (normalBubbleCoolDown ==0) {
                                        normalBubbleCDOn = true;
                                        Point2D.Float target = new Point2D.Float((float) (turtleHero.tx + 500 * Math.cos(turtleHero.direction)),
                                                (float) (turtleHero.ty + 500 * Math.sin(turtleHero.direction)));
                                        Bubble m = new Bubble((int) target.x, (int) target.y);
                                        gameData.friendObject.add(m);
                                    }
                                    break;
                                }

                                if (weaponForm == WEAPON_WATERBEAM ) {
                                    if (waterBeamCoolDown == 0 && turtlehero.energyValue > 0 ) { // WHEN NO CD AND HAS ENERGY
                                        WaterBeam m = new WaterBeam((int) turtlehero.location.x - 114, (int) turtlehero.location.y - 2000);
                                        gameData.friendObject.add(m);
                                        waterBeamCDOn = true;
                                        if(waterBeamUsedEnergyOn == false) { // handle long press load two times
                                                                             // IMPORTANT, USE CD==0 AS CONDITION NOT WORK, 1SECOND CAN SEND MANY TIMES
                                            turtlehero.energyValue--;
                                            waterBeamUsedEnergyOn = true;
                                        }
                                    }
                                    break;
                                }

                                if (weaponForm == WEAPON_SPARKLINGBUBBLE) {
//                                    System.out.println(sparklingBubbleCoolDown);
                                    if (sparklingBubbleCoolDown == 0 && turtlehero.energyValue > 0) { // WHEN NO CD AND HAS ENERGY
                                        Point2D.Float target = new Point2D.Float((float) (turtleHero.tx + 500 * Math.cos(turtleHero.direction)),
                                                (float) (turtleHero.ty + 500 * Math.sin(turtleHero.direction)));
                                        SparklingBubble m = new SparklingBubble((int) target.x, (int) target.y);
                                        gameData.friendObject.add(m);
                                        sparklingBubbleCDOn = true;
                                        if(sparklingBubbleUsedEnergyOn == false) { // handle long press load two times
                                            // IMPORTANT, USE CD==0 AS CONDITION NOT WORK, 1SECOND CAN SEND MANY TIMES
                                            turtlehero.energyValue--;
                                            sparklingBubbleUsedEnergyOn = true;
                                        }
                                    }

                                    break;
                                }

                            case KeyEvent.VK_S:
                                if (whirlpoolShieldCoolDown == 0 && turtlehero.energyValue > 0) { // WHEN NO CD AND HAS ENERGY
                                    WhirlpoolShield m = new WhirlpoolShield((int) turtlehero.location.x - 114, (int) turtlehero.location.y - 2000);
                                    gameData.friendObject.add(m);
                                    whirlpoolShieldCDOn = true;
                                    if(whirlpoolShieldUsedEnergyOn == false) { // handle long press load two times
                                        // IMPORTANT, USE CD==0 AS CONDITION NOT WORK, 1SECOND CAN SEND MANY TIMES
                                        turtlehero.energyValue--;
                                        whirlpoolShieldUsedEnergyOn = true;
                                    }
                                }
                                break;



                            case KeyEvent.VK_A:
                                switch (angleCount) {
                                    case 0:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 2000;
                                        turtleHero.tx -= 2000 * Math.tan(Math.toRadians(10));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount--;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case -1:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 2000;
                                        turtleHero.tx -= 2000 * Math.tan(Math.toRadians(20));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount--;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case -2:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 2000;
                                        turtleHero.tx -= 2000 * Math.tan(Math.toRadians(30));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount--;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case -3:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 2000;
                                        turtleHero.tx -= 2000 * Math.tan(Math.toRadians(40));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount--;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case -4:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 2000;
                                        turtleHero.tx -= 2000 * Math.tan(Math.toRadians(50));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount--;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case -5:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 1500;
                                        turtleHero.tx -= 1500 * Math.tan(Math.toRadians(60));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount--;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case -6:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 1000;
                                        turtleHero.tx -= 1000 * Math.tan(Math.toRadians(70));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount--;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case -7:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 1000;
                                        turtleHero.tx -= 1000 * Math.tan(Math.toRadians(80));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount--;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case -8:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 0;
                                        turtleHero.tx -= 2000;
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount--;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case -9:
                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case 1:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 2000;
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount--;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case 2:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 2000;
                                        turtleHero.tx += 2000 * Math.tan(Math.toRadians(10));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount--;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case 3:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 2000;
                                        turtleHero.tx += 2000 * Math.tan(Math.toRadians(20));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount--;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case 4:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 2000;
                                        turtleHero.tx += 2000 * Math.tan(Math.toRadians(30));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount--;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case 5:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 2000;
                                        turtleHero.tx += 2000 * Math.tan(Math.toRadians(40));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount--;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case 6:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 2000;
                                        turtleHero.tx += 2000 * Math.tan(Math.toRadians(50));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount--;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case 7:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 1500;
                                        turtleHero.tx += 1500 * Math.tan(Math.toRadians(60));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount--;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case 8:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 1000;
                                        turtleHero.tx += 1000 * Math.tan(Math.toRadians(70));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount--;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case 9:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 1000;
                                        turtleHero.tx += 1000 * Math.tan(Math.toRadians(80));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount--;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                }
                                break;

                            case KeyEvent.VK_D:
//                            turtlehero.tx = turtlehero.tx - turtlehero.ty * Math.tan(Math.toRadians(10));
//                            turtlehero.ty = turtlehero.ty +500; //initial value -4500, 9times to make it 0
//                            turtlehero.tx = turtlehero.tx +500;
//                            turtlehero.delta_x = turtlehero.tx -(turtlehero.location.x-14);
//                            turtlehero.delta_y = turtlehero.ty -(turtlehero.location.y);
//                            turtlehero.direction = Math.atan2(turtlehero.delta_y, turtlehero.delta_x);

                                switch (angleCount) {
                                    case 0:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 2000;
                                        turtleHero.tx += 2000 * Math.tan(Math.toRadians(10));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount++;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case 1:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 2000;
                                        turtleHero.tx += 2000 * Math.tan(Math.toRadians(20));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount++;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case 2:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 2000;
                                        turtleHero.tx += 2000 * Math.tan(Math.toRadians(30));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount++;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case 3:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 2000;
                                        turtleHero.tx += 2000 * Math.tan(Math.toRadians(40));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount++;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case 4:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 2000;
                                        turtleHero.tx += 2000 * Math.tan(Math.toRadians(50));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount++;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case 5:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 1500;
                                        turtleHero.tx += 1500 * Math.tan(Math.toRadians(60));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount++;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case 6:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 1000;
                                        turtleHero.tx += 1000 * Math.tan(Math.toRadians(70));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount++;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case 7:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 1000;
                                        turtleHero.tx += 1000 * Math.tan(Math.toRadians(80));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount++;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case 8:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 0;
                                        turtleHero.tx += 2000;
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount++;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case 9: // no add
                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case -1:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 2000;
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount++;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case -2:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 2000;
                                        turtleHero.tx -= 2000 * Math.tan(Math.toRadians(10));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount++;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case -3:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 2000;
                                        turtleHero.tx -= 2000 * Math.tan(Math.toRadians(20));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount++;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case -4:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 2000;
                                        turtleHero.tx -= 2000 * Math.tan(Math.toRadians(30));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount++;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case -5:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 2000;
                                        turtleHero.tx -= 2000 * Math.tan(Math.toRadians(40));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount++;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case -6:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 2000;
                                        turtleHero.tx -= 2000 * Math.tan(Math.toRadians(50));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount++;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case -7:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 1500;
                                        turtleHero.tx -= 1500 * Math.tan(Math.toRadians(60));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount++;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case -8:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 1000;
                                        turtleHero.tx -= 1000 * Math.tan(Math.toRadians(70));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount++;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;

                                    case -9:
                                        turtleHero.tx = turtlehero.location.x - 14;
                                        turtleHero.ty = turtlehero.location.y - 1000;
                                        turtleHero.tx -= 1000 * Math.tan(Math.toRadians(80));
                                        turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
                                        turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
                                        turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
                                        angleCount++;

                                        gameData.textObject.add(new AngleLine((int)turtlehero.location.x - 14,
                                                (int)turtlehero.location.y +1));
                                        break;


                                }

//                            System.out.println(turtlehero.tx);
//                            System.out.println(turtlehero.ty);
//                            System.out.println(turtlehero.direction);
                                break;

                            case KeyEvent.VK_F:
                                if (weaponForm == WEAPON_NORMALBUBBLE) {
                                    weaponForm = WEAPON_WATERBEAM;
                                    break;
                                }
                                if (weaponForm == WEAPON_WATERBEAM) {
                                    weaponForm = WEAPON_SPARKLINGBUBBLE;
                                    break;
                                }
                                if (weaponForm == WEAPON_SPARKLINGBUBBLE) {
                                    weaponForm = WEAPON_NORMALBUBBLE;
                                    break;
                                }
                                break;


                            case KeyEvent.VK_ENTER:
                                if (enterSwitchCount == 0) {
                                    enterSwitchOn = true;
                                    enterSwitchCount++;
                                    break;
                                }
                                if (enterSwitchCount == 1) {
                                    enterSwitchOn = false;
                                    enterSwitchCount = 0;
                                    break;
                                }
                                break;

                            case KeyEvent.VK_0:
                                cheatcodepasswordCount++;
                                if (cheatcodepasswordCount==3) {
                                    cheatcodepasswordCorrect = true;
                                }
                                if (cheatcodepasswordCorrect) {
                                    MyWindow.cheatButton_EnergyFull.setEnabled(true);
                                    MyWindow.cheatButton_SkipToPiranha.setEnabled(true);
                                    MyWindow.cheatButton_SkipToBoss.setEnabled(true);
                                    cheatcodepasswordCount = 0;
                                    cheatcodepasswordCorrect = false;
                                }
                        }

                        break; // break keypressed

                    case InputEvent.LOBM_CREATE:
                        EnemyCreateEvent le = (EnemyCreateEvent) inputEvent.event;
                        Main.addEnemieswithListener(le.getX(), le.getY());
                        break;

                    case InputEvent.STAR_CREATE:
//                        StarCreateEvent se = (StarCreateEvent) inputEvent.event; // crash when mix with the lob die event
                        Main.addStarMwithListener();
                        break; // Don't miss the break again..... = =

                    case InputEvent.PIRANHA_PHASE:
                        EnemyCreateEvent pe = (EnemyCreateEvent) inputEvent.event;
                        Main.addEnemieswithListener(pe.getX(), pe.getY());
                        break; // Don't miss the break again..... = =

                    case InputEvent.PIRANHA_SHOOTICEBERG:
                        EnemyCreateEvent pse = (EnemyCreateEvent) inputEvent.event;
                        Main.addEnemieswithListener(pse.getX(), pse.getY());
                        break; // Don't miss the break again..... = =

                    case InputEvent.BOSS_APPEAR:
                        EnemyCreateEvent bae = (EnemyCreateEvent) inputEvent.event;
                        Main.addEnemieswithListener(bae.getX(), bae.getY());
                        break; // Don't miss the break again..... = =

                    case InputEvent.DSDEVIL_PHASE:
                        EnemyCreateEvent dpe = (EnemyCreateEvent) inputEvent.event;
                        Main.addEnemieswithListener(dpe.getX(), dpe.getY());
                        break; // Don't miss the break again..... = =

                    case InputEvent.DSDEVIL_NORMAL_DC:
                        EnemyCreateEvent ndbe = (EnemyCreateEvent) inputEvent.event;
                        Main.addEnemieswithListener(ndbe.getX(), ndbe.getY());
                        break; // Don't miss the break again..... = =

                    case InputEvent.DSDEVIL_COMBO_DB:
                        EnemyCreateEvent cdbe = (EnemyCreateEvent) inputEvent.event;
                        Main.addEnemieswithListener(cdbe.getX(), cdbe.getY());
                        break; // Don't miss the break again..... = =

                    case InputEvent.DSDEVIL_TENTACLE_STRETCH:
                        EnemyCreateEvent tse = (EnemyCreateEvent) inputEvent.event;
                        Main.addEnemieswithListener(tse.getX(), tse.getY());
                        break; // Don't miss the break again..... = =


                    case InputEvent.CONGRATULATIONS:
                        EnemyCreateEvent congrate = (EnemyCreateEvent) inputEvent.event;
                        Main.addEnemieswithListener(congrate.getX(), congrate.getY());
                        break; // Don't miss the break again..... = =

                    case InputEvent.DO_NOTHING:
                        break; // Don't miss the break again..... = =

                }
            }
            catch (NoSuchElementException exception) {
                System.out.println("Skip this time in input queue ");
                InputEvent DoNothingEvent = new InputEvent();
                DoNothingEvent.event = null;
                DoNothingEvent.type = InputEvent.DO_NOTHING; // let InputEvent handle it...
                Main.playerInputEventQueue.queue.add(DoNothingEvent);
//                MyWindow.restartButton.doClick();
                PlayerInputEventQueue.queue.clear();  //clear all inputevent in the queue
                PlayerInputEventQueue.queueEmptyOK = false; // IMPORTANT, AVOID CLEAR AT THE MOMENT SKIPPED QUEUE.ISEMPTY()

            }
            }
        }
    }// end process input event

    public void nonRunningProcessInputEvents() {

        while (!queue.isEmpty()) {                                  //MODIFIED THIS AFTER DRAW CANVAS

            queueEmptyOK = false;
            int temptime = Main.TimeClock;
            if (queueTempTimeClock == -1) {
                queueTempTimeClock = temptime;
            } else if (queueTempTimeClock == temptime){
                queueTempTimeClock = -1;
            } else {
                queueTempTimeClock = Main.TimeClock;
            }
            if (!queue.isEmpty()) queueEmptyOK = true;
            if (queueEmptyOK && queueTempTimeClock !=-1) { //avoid 1 instant run twice
                //double check //java.base/java.util.LinkedList.removeFirst(LinkedList.java:274) NoSuchElementException

                try {
                    InputEvent inputEvent = queue.removeFirst();


                    switch (inputEvent.type) {

                        case InputEvent.MOUSE_PRESSED: // do something when click
                            MouseEvent e = (MouseEvent) inputEvent.event;
                            Point2D.Float mcLocation = new Point2D.Float(e.getX(), e.getY());
                            System.out.println(mcLocation);

                            //draw canvas comment start

                            if (mcLocation.x >= 447 && mcLocation.x <= 663 + 50
                                    && mcLocation.y >= 216 - 5 +100 && mcLocation.y <= 250 + 10 +100) {
//                        System.out.println("Start Game");
                                Main.running = true;
                                MyWindow.restartButton.setEnabled(true); //button on

                                MyWindow.pauseButton.setEnabled(true);

                                //cheat code button on
//                                MyWindow.cheatButton_EnergyFull.setEnabled(true);
//                                MyWindow.cheatButton_SkipToPiranha.setEnabled(true);
//                                MyWindow.cheatButton_SkipToBoss.setEnabled(true);

//                                MyCanvas.Canvasg2.setBackground(new Color(24,52,78)); // cannot do this bc mycanvas keep updating black
                                MyCanvas.g2BgColor = new Color(16,33,40);
                            }

                            if (mcLocation.x >= 447 && mcLocation.x <= 678 + 50
                                    && mcLocation.y >= 316 - 5 +100 && mcLocation.y <= 353 + 10 +100) {
//                        System.out.println("How To Play");
                                System.out.println(tutorialScreenOn);
                                tutorialScreenOn = true;
                                Main.tutorialScreen();// call the function here will not end and come back until running
                                //DO NOT PUT THINGS HERE
                            }

                            if (mcLocation.x >= 447 && mcLocation.x <= 646 + 50
                                    && mcLocation.y >= 416 - 5 +100 && mcLocation.y <= 450 + 10 +100) {
//                        System.out.println("Exit Game");
                                System.exit(0);
                            }

                            if (mcLocation.x >= 140 && mcLocation.x <= 520
                                    && mcLocation.y >= 637 && mcLocation.y <= 687
                                    && tutorialScreenOn == true) {
//                            System.out.println(tutorialScreenOn);
                                tutorialScreenOn = false;
                                Main.startScreen();// call the function here will not end and come back until running
                                //DO NOT PUT THINGS HERE
                            }


                            // draw canvas comment end

                            if (Main.canvasScreenOn) {
                                gameData.friendObject.add(new GameFigure() {
                                    @Override
                                    public void render(Graphics2D g2) {
                                        g2.setColor(Color.WHITE);
                                        g2.drawLine((int) mcLocation.x, (int) mcLocation.y, (int) mcLocation.x, (int) mcLocation.y);
                                    }

                                    @Override
                                    public void update() {

                                    }

                                    @Override
                                    public int getCollisionRadius() {
                                        return 0;
                                    }
                                });
                            }

                            break;// break mousepressed

                        case InputEvent.MOUSE_MOVED: // do something when mouse move
//                    MousePointer mp = (MousePointer) Main.gameData.fixedObject.get(0);
//                    MouseEvent me = (MouseEvent) inputEvent.event;
//                    mp.location.x = me.getX();
//                    mp.location.y = me.getY();
                            break;// break mousemoved

                        case InputEvent.KEY_PRESSED:
                            KeyEvent ke = (KeyEvent) inputEvent.event; // typecast to keyevent, which is supercls of eventobject

                            switch (ke.getKeyCode()) {
                                case KeyEvent.VK_UP:

                                    break;
                                case KeyEvent.VK_DOWN:

                                    break;
                                case KeyEvent.VK_LEFT:

                                    break;
                                case KeyEvent.VK_RIGHT:

                                    break;
                                case KeyEvent.VK_SPACE:

                                    break;
                                case KeyEvent.VK_ENTER:

                                    break;
                            }

                            break; // break keypressed

                        case InputEvent.DO_NOTHING:
                            break; // Don't miss the break again..... = =

                    }
                }   catch (NoSuchElementException exception) {
                    System.out.println("Skip this time in input queue ");
                    InputEvent DoNothingEvent = new InputEvent();
                    DoNothingEvent.event = null;
                    DoNothingEvent.type = InputEvent.DO_NOTHING; // let InputEvent handle it...
                    Main.playerInputEventQueue.queue.add(DoNothingEvent);
//                    MyWindow.restartButton.doClick();
                    PlayerInputEventQueue.queue.clear();  //clear all inputevent in the queue
                    PlayerInputEventQueue.queueEmptyOK = false; // IMPORTANT, AVOID CLEAR AT THE MOMENT SKIPPED QUEUE.ISEMPTY()


                }
            }
        }
    }

    void autoAdjustAngle(){
    var turtlehero = gameData.fixedObject.get(Main.INDEX_TURTLEHERO);
    switch (angleCount) {
        case 1:
            turtleHero.tx = turtlehero.location.x - 14;
            turtleHero.ty = turtlehero.location.y - 2000;
            turtleHero.tx += 2000 * Math.tan(Math.toRadians(10));
            turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
            turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
            turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
            break;

        case 2:
            turtleHero.tx = turtlehero.location.x - 14;
            turtleHero.ty = turtlehero.location.y - 2000;
            turtleHero.tx += 2000 * Math.tan(Math.toRadians(20));
            turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
            turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
            turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
            break;

        case 3:
            turtleHero.tx = turtlehero.location.x - 14;
            turtleHero.ty = turtlehero.location.y - 2000;
            turtleHero.tx += 2000 * Math.tan(Math.toRadians(30));
            turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
            turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
            turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
            break;

        case 4:
            turtleHero.tx = turtlehero.location.x - 14;
            turtleHero.ty = turtlehero.location.y - 2000;
            turtleHero.tx += 2000 * Math.tan(Math.toRadians(40));
            turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
            turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
            turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
            break;

        case 5:
            turtleHero.tx = turtlehero.location.x - 14;
            turtleHero.ty = turtlehero.location.y - 2000;
            turtleHero.tx += 2000 * Math.tan(Math.toRadians(50));
            turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
            turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
            turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
            break;

        case 6:
            turtleHero.tx = turtlehero.location.x - 14;
            turtleHero.ty = turtlehero.location.y - 1500;
            turtleHero.tx += 1500 * Math.tan(Math.toRadians(60));
            turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
            turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
            turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
            break;

        case 7:
            turtleHero.tx = turtlehero.location.x - 14;
            turtleHero.ty = turtlehero.location.y - 1000;
            turtleHero.tx += 1000 * Math.tan(Math.toRadians(70));
            turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
            turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
            turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
            break;

        case 8:
            turtleHero.tx = turtlehero.location.x - 14;
            turtleHero.ty = turtlehero.location.y - 1000;
            turtleHero.tx += 1000 * Math.tan(Math.toRadians(80));
            turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
            turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
            turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
            break;

        case 9:
            turtleHero.tx = turtlehero.location.x - 14;
            turtleHero.ty = turtlehero.location.y - 0;
            turtleHero.tx += 2000;
            turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
            turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
            turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
            break;

        case 0:
            turtleHero.tx = turtlehero.location.x - 14;
            turtleHero.ty = turtlehero.location.y - 2000;
            turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
            turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
            turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
            break;

        case -1:
            turtleHero.tx = turtlehero.location.x - 14;
            turtleHero.ty = turtlehero.location.y - 2000;
            turtleHero.tx -= 2000 * Math.tan(Math.toRadians(10));
            turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
            turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
            turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
            break;

        case -2:
            turtleHero.tx = turtlehero.location.x - 14;
            turtleHero.ty = turtlehero.location.y - 2000;
            turtleHero.tx -= 2000 * Math.tan(Math.toRadians(20));
            turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
            turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
            turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
            break;

        case -3:
            turtleHero.tx = turtlehero.location.x - 14;
            turtleHero.ty = turtlehero.location.y - 2000;
            turtleHero.tx -= 2000 * Math.tan(Math.toRadians(30));
            turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
            turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
            turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
            break;

        case -4:
            turtleHero.tx = turtlehero.location.x - 14;
            turtleHero.ty = turtlehero.location.y - 2000;
            turtleHero.tx -= 2000 * Math.tan(Math.toRadians(40));
            turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
            turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
            turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
            break;

        case -5:
            turtleHero.tx = turtlehero.location.x - 14;
            turtleHero.ty = turtlehero.location.y - 2000;
            turtleHero.tx -= 2000 * Math.tan(Math.toRadians(50));
            turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
            turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
            turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
            break;

        case -6:
            turtleHero.tx = turtlehero.location.x - 14;
            turtleHero.ty = turtlehero.location.y - 1500;
            turtleHero.tx -= 1500 * Math.tan(Math.toRadians(60));
            turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
            turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
            turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
            break;

        case -7:
            turtleHero.tx = turtlehero.location.x - 14;
            turtleHero.ty = turtlehero.location.y - 1000;
            turtleHero.tx -= 1000 * Math.tan(Math.toRadians(70));
            turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
            turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
            turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
            break;

        case -8:
            turtleHero.tx = turtlehero.location.x - 14;
            turtleHero.ty = turtlehero.location.y - 1000;
            turtleHero.tx -= 1000 * Math.tan(Math.toRadians(80));
            turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
            turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
            turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
            break;

        case -9:
            turtleHero.tx = turtlehero.location.x - 14;
            turtleHero.ty = turtlehero.location.y - 0;
            turtleHero.tx -= 2000;
            turtleHero.delta_x = turtleHero.tx - (turtlehero.location.x - 14);
            turtleHero.delta_y = turtleHero.ty - (turtlehero.location.y);
            turtleHero.direction = Math.atan2(turtleHero.delta_y, turtleHero.delta_x);
            break;
    }
    }

}
