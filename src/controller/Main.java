package controller;

import controller.enemyobserver.EnemyObserverAddNew;
import controller.starobserver.StarCreateEvent;
import controller.starobserver.StarObserverAddNew;
import model.*;
import model.compressediceberg.CompressedIceBerg;
import model.darkcrystal.DarkCrystal;
import model.darkenergyball.DarkEnergyBall;
import model.dsdevil.dsDevil;
import model.dsdevil.dsDevilAnimDBComboAttacking;
import model.dsdevil.dsDevilAnimTentacleAttacking;
import model.piranhamon.piranhaMon;
import model.starfish.*;
import model.tentacle.Tentacle;
import model.turtlehero.turtleHero;
import model.lobstermon.lobsterMon;
import view.MyCanvas;
import view.MyWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static controller.enemyobserver.EnemyObserverAddNew.ttAddPiranha;


public class Main {

    public static MyWindow win;
    public static GameData gameData;
    public static boolean running = false;

    public static boolean collisionLooping = false;

    public static PlayerInputEventQueue playerInputEventQueue;

    public static int INDEX_VILLAGE_LINE = 0; // in gameData.fixObject
    public static int INDEX_MOUSE_POINTER = 1; // in gameData.fixObject
    public static int INDEX_TURTLEHERO = 2;


    public static int FPS = 30;

//    public static boolean createUFO = true;
    public static boolean canvasScreenOn = false;

    public static boolean gameOver = false;
    public static boolean calledGameOver = false;

    public static boolean gamePaused = false;

    public static Timer delayGenerateTimer = new Timer();
    public static Timer delayGenerateTimer2 = new Timer();
    public static Timer delayGenerateTimer3 = new Timer();
    public static Timer delayGenerateTimer4 = new Timer();

    public static int TimeClock = 0;
    public static int tempTimeClock = -1;

    public static int blockCD = 0;
    public static boolean blockCDOn = false;

    public static boolean SkipLobPhase = false;

    public static void main(String[] args) {
        win = new MyWindow();
        win.init();
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true);

        gameData = new GameData();
        playerInputEventQueue = new PlayerInputEventQueue();

//        drawingCanvas(); // for testing only  // CAN CHANGE PLAYERINPUTEVENTQUEUE NONPROCESS TO TEST

//        initGame();
        startScreen();

        initGame(); // after startScreen function finish


        gameLoop(); // if too fast may cause concurrentmodification exception, can use iterator to fix...
//        endScreen_Clear();
//
//        gameLoop();

    }

    static void drawingCanvas() {
        canvasScreenOn = true;
        gameData.clear();
        int x = Main.win.getWidth() / 2;
        int y = Main.win.getHeight() - 200;
        gameData.fixedObject.add(new turtleHero(x+4,y));
//        gameData.enemyObject.add(new lobsterMon(x,y-500));
//        gameData.friendObject.add(new starfishEnergy(400,400));
        gameData.friendObject.add(new lobsterMon(x-300,y-100));
        gameData.friendObject.add(new piranhaMon(x+300,y-200));

        gameData.friendObject.add(new dsDevil(592,150));



        while (!running) {
            Main.win.canvas.render();
            playerInputEventQueue.nonRunningProcessInputEvents(); // handle the start screen mouse input

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // finish when running == true;
    }

    static void startScreen() {
        // when running != true, just hold the start screen
        // show initial message on canvas
        // Although we just display sth, we need game figure, that's how canvas draw

        gameData.clear();

        gameData.friendObject.add(new GameFigure() { // turtle icon
            @Override
            public void render(Graphics2D g2) {
                Color GreenColor1 = new Color(102,153,0);
                Color GreenColor2 = new Color(133,213,133);
                Color BrownColor1 = new Color(92,93,0);
                Color BrownColor2 = new Color(122,93,0);
                Color BrownColor3 = new Color(52,33,0);
                Color mouthColor1 = new Color(192,93,0);
                Color mouthColor2 = new Color(182,63,0);

                g2.setStroke(new BasicStroke(1));

                //hands
                g2.setColor(GreenColor1);
                g2.fillOval(615-20,122+15,30,30);
                g2.setColor(GreenColor2);
                g2.drawOval(615-20,122+15,30,30);

                g2.setColor(GreenColor1);
                g2.fillOval(615+20+10,122+15,30,30);
                g2.setColor(GreenColor2);
                g2.drawOval(615+20+10,122+15,30,30);

                //shell
                g2.setColor(BrownColor1);
                g2.fillOval(585,95+20,100,50);
                g2.setColor(GreenColor1);
                g2.drawOval(585,95+20,100,50);

                g2.setColor(BrownColor1);
                g2.fillOval(585+10,85+20,80,50);
                g2.setColor(BrownColor1);
                g2.drawOval(585+10,85+20,80,50);

                //shell pattern
                g2.setStroke(new BasicStroke(2));
                g2.setColor(BrownColor3);
                g2.drawLine(612,89+20,624,108+20);
                g2.drawLine(624,108+20,644,108+20);
                g2.drawLine(644,108+20,655,89+20);

                g2.drawLine(624,108+20,614-5,118+10+20);
                g2.drawLine(644,108+20,654+5,118+10+20);

                g2.drawLine(614-5,118+10+20,594,134+20);
                g2.drawLine(654+5,118+10+20,673,134+20);

                //head
                g2.setColor(GreenColor1);
                g2.fillOval(615,125+15,40,30);
                g2.setColor(GreenColor2);
                g2.drawOval(615,125+15,40,30);

                //eyes white
                g2.setColor(Color.white);
                g2.fillOval(621,133+15,10,10);
                g2.drawOval(621,133+15,10,10);

                g2.setColor(Color.white);
                g2.fillOval(638,133+15,10,10);
                g2.drawOval(638,133+15,10,10);

                //eyes black
                g2.setColor(Color.black);
                g2.fillOval(624,135+15,7,7);
                g2.drawOval(624,135+15,7,7);

                g2.setColor(Color.black);
                g2.fillOval(640,135+15,7,7);
                g2.drawOval(640,135+15,7,7);

                //eyes white (little)
                g2.setColor(Color.white);
                g2.fillOval(625,137+15,2,2);
                g2.drawOval(625,137+15,2,2);

                g2.setColor(Color.white);
                g2.fillOval(641,137+15,2,2);
                g2.drawOval(641,137+15,2,2);



                //mouth
                g2.setColor(Color.BLACK);
                g2.drawOval(629,144+15,12,10);

                g2.setColor(mouthColor1);
                g2.fillOval(629,144+15,12,10);


            }

            @Override
            public void update() {

            }

            @Override
            public int getCollisionRadius() {
                return 0;
            }
        });

        Font font1 = new Font("Courier New", Font.BOLD,40);
        Font font2 = new Font("Arial", Font.BOLD,40);
        Font font3 = new Font("Comic Sans MS", Font.BOLD,120);
        Font font4 = new Font("Comic Sans MS", Font.BOLD,72);
        Font font5 = new Font("Comic Sans MS", Font.BOLD,150);
        Font font6 = new Font("Comic Sans MS", Font.BOLD,40);
//        gameData.friendObject.add(new Text("Protect my Turtle Village", 150, 100, Color.GREEN, font1));
//        gameData.friendObject.add(new Text("P o e t  y  u t e  i l g ", 150, 100, new Color(102,153,0), font1));

        gameData.friendObject.add(new Text("P", 253+40, 150+50, new Color(83,83,83), font3)); // shade
        gameData.friendObject.add(new Text("P", 250+40, 150+50, new Color(33,233,33), font3));
        gameData.friendObject.add(new Text(" rotec", 261+40, 150+50, new Color(83,83,83), font4)); // shade
        gameData.friendObject.add(new Text(" rotec", 258+40, 150+50, new Color(102,153,0), font4));
        gameData.friendObject.add(new Text(" my", 394+40, 175+50, new Color(52,53,53), font6)); // shade
        gameData.friendObject.add(new Text(" my", 390+40, 175+50, new Color(92,93,0), font6));
        gameData.friendObject.add(new Text("  o", 261+40, 150+50, new Color(92,93,0), font4));
        gameData.friendObject.add(new Text("    e", 271+40, 150+50, new Color(92,93,0), font4));

        gameData.friendObject.add(new Text("T", 434+40, 180+50, new Color(83,83,83), font5)); // shade
        gameData.friendObject.add(new Text("T", 430+40, 180+50, new Color(33,233,33), font5));
        gameData.friendObject.add(new Text(" urtle", 478+40, 180+50, new Color(83,83,83), font4)); // shade
        gameData.friendObject.add(new Text(" urtle", 475+40, 180+50, new Color(102,153,0), font4));
        gameData.friendObject.add(new Text("  r", 480+40, 180+50, new Color(92,93,0), font4));
        gameData.friendObject.add(new Text("    l", 487+40, 180+50, new Color(92,93,0), font4));

        gameData.friendObject.add(new Text("V", 654+40, 180+50, new Color(83,83,83), font3)); // shade
        gameData.friendObject.add(new Text("V", 650+40, 180+50, new Color(33,233,33), font3));
        gameData.friendObject.add(new Text(" illage", 683+40, 180+50, new Color(83,83,0), font4));
        gameData.friendObject.add(new Text(" illage", 680+40, 180+50, new Color(102,153,0), font4));
        gameData.friendObject.add(new Text("   l", 657+40, 180+50, new Color(92,93,0), font4));
        gameData.friendObject.add(new Text("     g", 655+40, 180+50, new Color(92,93,0), font4));


        gameData.friendObject.add(new Text("Start Game", 480, 250+100, Color.WHITE, font2));
        gameData.friendObject.add(new Text("How to Play", 480, 350+100, Color.WHITE, font2));
        gameData.friendObject.add(new Text("Exit Game", 480, 450+100, Color.WHITE, font2));

        while (!running) {
            Main.win.canvas.render();
            playerInputEventQueue.nonRunningProcessInputEvents(); // handle the start screen mouse input

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // finish when running == true;
    }

    static void tutorialScreen() {
        gameData.clear();
        Font font1 = new Font("Courier New", Font.BOLD,60);
        Font font2 = new Font("Arial", Font.BOLD,14);
        Font font3 = new Font("Arial", Font.BOLD,25);
        Font font4 = new Font("Courier New", Font.BOLD,30);
        Color colorWhite = new Color(233,230,224);

        gameData.friendObject.add(new Text("Tutorial", 150, 100, Color.GREEN, font1));
        gameData.friendObject.add(new Text("T t r a ", 150, 100, new Color(102,153,0), font1));

        gameData.friendObject.add(new Text("Use [↑] [↓] [←] [→] to move the Turtle Hero", 150, 140, colorWhite, font2));
        gameData.friendObject.add(new Text("Use [Space] to attack", 150, 160, colorWhite, font2));
        gameData.friendObject.add(new Text("Use [A][D] to adjust the Turtle Hero'sattack angle", 150, 180, colorWhite, font2));
        gameData.friendObject.add(new Text("Use [F] to change the attack form (special attacks and shield need to consume starfish energy)", 150, 200, colorWhite, font2));
        gameData.friendObject.add(new Text("Use [S] to summon the whirlpool shield to resist enemies' attack (can only block weak enemies or attacks)", 150, 220, colorWhite, font2));

        gameData.friendObject.add(new Text("There are different kinds of starfishes appearing. You can move the Turtle Hero to absorb the starfish to get benefit effects.", 150, 250, colorWhite, font2));
        gameData.friendObject.add(new Text("The most common one is the energy starfish, you can absorb it to charge your character's energy so that you can use special attacks and shield.", 150, 270, colorWhite, font2));
        gameData.friendObject.add(new Text("The red color life starfish can recover one character life if you get hurt by the enery's attack.", 150, 290, colorWhite, font2));
        gameData.friendObject.add(new Text("The purple (POWER) and the skyblue (SPEED) starfishes always appear in a pair. You can only choose either one to abosrb. ", 150, 310, colorWhite, font2));
        gameData.friendObject.add(new Text("The POWER starfish will give you stronger power in your attack form for 20seconds.", 150, 330, colorWhite, font2));
        gameData.friendObject.add(new Text("The SPEED starfish will give you faster speed in your attack form for 20seconds.", 150, 350, colorWhite, font2));

        gameData.friendObject.add(new Text("Normal bubble attack and whirlpool shield can give enemies small damage (5hp)", 150, 380, colorWhite, font2));
        gameData.friendObject.add(new Text("Waterbeam and Sparkling Bubble attack can give enemies strong damage (30hp)", 150, 400, colorWhite, font2));
        gameData.friendObject.add(new Text("Whirlpool shield can repel enemies/attacks which are less than 30hp.", 150, 420, colorWhite, font2));

        gameData.friendObject.add(new Text("Lobster Monster has 30hp life, Piranha Monster has 75hp life.", 150, 450, colorWhite, font2));
        gameData.friendObject.add(new Text("Deepsea Devil has 360hp life (and its tentacles have 40hp life for each).", 150, 470, colorWhite, font2));

        gameData.friendObject.add(new Text("If the enemies catch the character or reach the village the game is over.", 150, 490, colorWhite, font2));
        gameData.friendObject.add(new Text("The character and the village have three lives respectively, lives will be lost if hurt by the enemies' attacks.", 150, 510, colorWhite, font2));
        gameData.friendObject.add(new Text("(Game is over if either one has no lives left)", 150, 530, colorWhite, font2));

        gameData.friendObject.add(new Text("Use your attack to repel the enemies and ", 150, 580, Color.YELLOW, font3));
        gameData.friendObject.add(new Text("cancel out the enemies' attacks to protect the village!!!", 150, 610, Color.YELLOW, font3));


        gameData.friendObject.add(new GameFigure() {
            @Override
            public void render(Graphics2D g2) {
                g2.setColor(Color.WHITE);
                g2.fillRect(140,637, 380, 50);
                g2.setColor(Color.BLACK);
                g2.fillRect(145,642, 370, 40);
                g2.setColor(Color.ORANGE);
                g2.drawRect(140,637, 380, 50);
            }

            @Override
            public void update() {

            }

            @Override
            public int getCollisionRadius() {
                return 0;
            }
        });
        gameData.friendObject.add(new Text("Back to Start Screen", 150, 670, Color.ORANGE, font4));




        while (!running) {
            Main.win.canvas.render();
            playerInputEventQueue.nonRunningProcessInputEvents(); // handle the start screen mouse input

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // finish when running == true;
    }


    public static void initGame() {
        gameData.clear();

        gameData.fixedObject.add(new GameFigure() {
            @Override
            public void render(Graphics2D g2) {
                // village line
                BasicStroke stroke = new BasicStroke(2);
                g2.setStroke(stroke);
                g2.setColor(Color.WHITE);
                g2.drawLine(0, 676, 1200, 676);
                int x1 = 20;
                int x2 = 50;
                int y = 676;
                g2.setColor(Color.BLACK);
                g2.drawLine(x1, y, x2, y);
                x1 += 80;
                x2 += 80;
                while (x2 <= 1200) {
                    g2.drawLine(x1, y, x2, y);
                    x1 += 80;
                    x2 += 80;
                }

                // village body (draft)

                Color color, color1, color2, color3, color4;

                color1 = new Color(32, 171, 163);
                color2 = new Color(28, 99, 95);
                color3 = new Color(167, 163, 53);
                color4 = new Color(167, 115, 53);

                int colorCount = 0;
//                int colorSwitch =0;

                double value5 = 0;
                double value6 = 69.282;
                double value7 = 138.564;
                double value8 = -69.282;

                while (value5 <= 1200) {
                    int[] x5 = {(int) value5, (int) value6, (int) value7};
                    int[] y5 = {(int) 680, (int) 800, (int) 680};

                    int[] x6 = {(int) value6, (int) value5, (int) value8};
                    int[] y6 = {(int) 800, (int) 680, (int) 800};

                    if (Main.gameOver == true) {
                        color = new Color(45, 44, 42);
                    } else color = new Color(233, 230, 224);
                    g2.setColor(color); // white outline
                    g2.drawPolygon(x5, y5, 3);

                    if (Main.gameOver == true) {
                        g2.setColor(new Color(178, 178, 178));
                    } else if (colorCount % 2 == 0) g2.setColor(color2);
                    else g2.setColor(color1);
//                    g2.setColor(color1);
                    g2.fillPolygon(x5, y5, 3);

                    if (Main.gameOver == true) {
                        g2.setColor(new Color(118, 117, 119));
                    } else if (colorCount % 2 == 0) g2.setColor(color2);
                    else g2.setColor(color1);
//                    g2.setColor(color2);
                    g2.fillPolygon(x6, y6, 3);

//                    color = new Color(233, 230, 224);
//                    g2.setColor(color); // white outline
//                    g2.drawPolygon(x6, y6, 3);
//                    if (colorCount%2==0) g2.setColor(color2);
//                    else g2.setColor(color1);
//                    g2.setColor(color2);
//                    g2.fillPolygon(x6, y6, 3);
//                    if (colorCount%2==0) g2.setColor(color2);
//                    else g2.setColor(color1);
////                    g2.setColor(color1);
//                    g2.fillPolygon(x5, y5, 3);


                    value5 += 69.282;
                    value6 += 69.282;
                    value7 += 69.282;
                    value8 += 69.282;
                    ;


//                    if (colorCount%2==0) g2.setColor(color4);
//                    else g2.setColor(color3);


//
//                    value5 -= 34.282;
//                    value6 -= 34.282;
//                    value7 -= 34.282;
//                    value8 -= 34.282;

//                    value1 -= 30;
//                    value2 -= 30;
//                    value3 -= 30;
//                    value4 -= 30;

                    colorCount++;
                }

                double value1 = 0;
                double value2 = 30;
                double value3 = 60;
                double value4 = -30;
                while (value1 <= 1200) {
                    int[] x3 = {(int) value1, (int) value2, (int) value3};
                    int[] y3 = {(int) 705, (int) 730, (int) 705};

                    int[] x4 = {(int) value4, (int) value1, (int) value2};
                    int[] y4 = {(int) 730, (int) 705, (int) 730};

//                    if (colorCount%2==0) g2.setColor(color2);
//                    else g2.setColor(color1);
//                    g2.fillPolygon(x3, y3, 3);
//                    color = new Color(233, 230, 224);
//                    g2.setColor(color); // white outline
//                    g2.drawPolygon(x3, y3, 3);
//
                    value1 += 25;
                    value2 += 25;
                    value3 += 25;
                    value4 += 25;

//                    if (colorCount%2==0) g2.setColor(color4);
//                    else g2.setColor(color3);
                    g2.setColor(color4);
                    g2.fillPolygon(x4, y4, 3);
//                    color = new Color(233, 230, 224);
                    g2.setColor(new Color(75, 50, 20)); // white outline
                    g2.drawPolygon(x4, y4, 3);

//                    value1 -= 30;
//                    value2 -= 30;
//                    value3 -= 30;
//                    value4 -= 30;

                    colorCount++;
                }

            }

            @Override
            public void update() {

            }

            @Override
            public int getCollisionRadius() {
                return 0;
            }
        });


        gameData.fixedObject.add(new MousePointer(0, 0)); //where the ptr is is determined by mousemove

        if (MousePointer.currentMousePointer == null) {gameData.fixedObject.add(new MousePointer(0, 0));} // double check

        int x = Main.win.getWidth() / 2;
        int y = Main.win.getHeight() - 100 - 100 - 60; // of win size, not canvas size, highest is bottom, bottom-100 // turtle hero is bigger move up a bit more
        gameData.fixedObject.add(new turtleHero(x+4, y));

//        Random rand = new Random();
//        gameData.friendObject.add(new starfishEnergy(rand.nextInt(1100)+50, rand.nextInt(200)+450));

        Font fontEnergyBar = new Font("Courier New", Font.BOLD, 20);
        gameData.fixedObject.add(new EnergyBar(10, 30, new Color(223, 242, 60), fontEnergyBar));

        gameData.fixedObject.add(new LifeIcons(10, 100, new Color(223, 242, 60), fontEnergyBar));

        gameData.fixedObject.add(new AttackForm(10, 200, new Color(223, 242, 60), fontEnergyBar));

//        if (SkipLobPhase == false) {
            addEnemieswithListener(100, -100);
//        }
//        gameData.enemyObject.add(new lobsterMon(100,100)); // put inside above func

        Random rand = new Random();
        delayGenerateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                addStarMwithListener();
            }
        }, rand.nextInt(1500) + 1500); //(1.5-3second)



    }

    public static void addEnemieswithListener(int x, int y) {

        // Lobster monster part
        if (EnemyObserverAddNew.EnemydiedCount < 3) {
            Random rand = new Random();
            x = rand.nextInt(1100) + 50;
            var lobM = new lobsterMon(x, y);
            lobM.attachListener(new EnemyObserverAddNew());
            gameData.enemyObject.add(lobM);
        }

        // Piranha monster part
        int x1=0, y1=0, x2=0, y2=0;
        if (EnemyObserverAddNew.EnemydiedCount == 3) {
            if (piranhaMon.currentPiranhaMon1 == null && EnemyObserverAddNew.callPiranhaMonOn == true) {
                Random rand = new Random();
                x1 = rand.nextInt(1040) + 80;
                y1 = -180;
                var piranM = new piranhaMon(x1, y1);
                piranM.attachListener(new EnemyObserverAddNew());
                gameData.enemyObject.add(piranM);
            }

            if (piranhaMon.currentPiranhaMon1 != null && piranhaMon.currentPiranhaMon2 == null
                    && EnemyObserverAddNew.callPiranhaMonOn == true) {
                if (gameOver != true) {
                    Random rand1 = new Random();

                    x2 = rand1.nextInt(1040) + 80;  // initialize x
                    y2 = -180;
                    boolean locationOK = false;

                    while (locationOK == false) { // while the location is uncheck, loop
                        x2 = rand1.nextInt(1100) + 50;  // generate x

                        if (Math.abs(x2 - x1) > 200) { // piranha m radius assume 100+100
                            locationOK = true;
                        }
                    }

                    var piranM2 = new piranhaMon(x2, y2);
                    piranM2.attachListener(new EnemyObserverAddNew());

                    ttAddPiranha = new TimerTask() {
                        @Override
                        public void run() {
                            while(true) {
                                if (MyCanvas.canvaslooping == false) { // while loop until the canvas not looping that
                                    gameData.enemyObject.add(piranM2);
                                    EnemyObserverAddNew.callPiranhaMonOn = false; // when the second is generated, stop it
                                    break; // success once and break
                                }
                            }
                        }
                    };
                    delayGenerateTimer2.schedule(ttAddPiranha,rand1.nextInt(2000)+2500); //(2.5-4.5second));

                }
            }

            if (piranhaMon.currentPiranhaMon1 !=null && piranhaMon.piranha1AttackingOn) {
                CompressedIceBerg m = new CompressedIceBerg((int) piranhaMon.currentPiranhaMon1.location.x - 14,
                        (int) piranhaMon.currentPiranhaMon1.location.y + 135 + 36);
                Random temprand = new Random();
                int q = temprand.nextInt(60)+20; // 20=1sec, so 2-4
//                piranhaMon.ttTentacle1attack = new TimerTask() {
//                    @Override
//                    public void run() {
                if (TimeClock %q ==0 && piranhaMon.piranha1AttackCoolDown==0) {
                    if (MyCanvas.canvaslooping == false) {
                        gameData.enemyObject.add(m);
                        piranhaMon.piranha1AttackCDOn = true;
                    }
                }
//                    }
//                };
//                delayGenerateTimer3.schedule(piranhaMon.ttTentacle1attack, temprand.nextInt(2000)+2000); //(2.5-4.5second));
            }

            if (piranhaMon.currentPiranhaMon2 !=null && piranhaMon.piranha2AttackingOn) {
                CompressedIceBerg m = new CompressedIceBerg((int) piranhaMon.currentPiranhaMon2.location.x - 14,
                        (int) piranhaMon.currentPiranhaMon2.location.y + 135 + 36);
                Random temprand = new Random();
                int q = temprand.nextInt(60)+20;
//                piranhaMon.ttTentacle2attack = new TimerTask() {
//                    @Override
//                    public void run() {
                if (TimeClock %q ==0 && piranhaMon.piranha2AttackCoolDown==0) {
                    if (MyCanvas.canvaslooping == false) {
                        gameData.enemyObject.add(m);
                        piranhaMon.piranha2AttackCDOn = true;
                    }
                }
//                    }
//                };
//                delayGenerateTimer4.schedule(piranhaMon.ttTentacle2attack,temprand.nextInt(2000)+2000); //(2.5-4.5second));
            }


        } // END PIRANHA PART

        //START BOSS APPEAR PART
        if (EnemyObserverAddNew.EnemydiedCount == 5) {
            if (piranhaMon.currentPiranhaMon1 == null && piranhaMon.currentPiranhaMon2 ==null){
                Font font = new Font("Courier New", Font.BOLD,150);
                x = 300-50-30+5;
                y = 300+50;
                var BossAppear = new BossAppearWarning("WARNING",x, y, Color.BLACK, font);
                BossAppear.attachListener(new EnemyObserverAddNew());
                gameData.enemyObject.add(BossAppear);
            }

        }

        //START DS DEVIL PART
        if (EnemyObserverAddNew.EnemydiedCount == 6 && BossAppearWarning.WARNINGFlashingEnded == true ) {
            
            //add new boss
            if (piranhaMon.currentPiranhaMon1 == null && piranhaMon.currentPiranhaMon2 ==null
                    && dsDevil.currentBoss == null){ // currentboss == null is the important piece
                Font font = new Font("Courier New", Font.BOLD,150);
                x = 592;
                y = -200;
                var dsDevil = new dsDevil(x,y);
                dsDevil.attachListener(new EnemyObserverAddNew());
                gameData.enemyObject.add(dsDevil);
            }
            
            //Normal DC attack
            if (dsDevil.currentBoss !=null && dsDevil.DCNormalAttackOn == true) {
                DarkCrystal m = new DarkCrystal((int) dsDevil.currentBoss.location.x,
                        (int) dsDevil.currentBoss.location.y + 18+20);
                Random temprand = new Random();
                int q = temprand.nextInt(30)+20 + 20; // 20=1sec, so 2-4
//                piranhaMon.ttTentacle1attack = new TimerTask() {
//                    @Override
//                    public void run() {
                if (TimeClock %q ==0 && dsDevil.DCNormalAttackCoolDown ==0 && dsDevil.DCcount<dsDevil.DCdecision) {
                    if (MyCanvas.canvaslooping == false) {
                        if (dsDevil.DCNormalAttackOn != false) {
                            gameData.enemyObject.add(m);
                            dsDevil.DCcount++;
                        }

                        dsDevil.DCNormalAttackCDOn = true;
                    }
                }
//                    }
//                };
//                delayGenerateTimer3.schedule(piranhaMon.ttTentacle1attack, temprand.nextInt(2000)+2000); //(2.5-4.5second));
            }

            //DB Combo attack
            if (dsDevil.currentBoss !=null && dsDevil.DBComboAttackingOn == true
                    && dsDevilAnimDBComboAttacking.DBAddCount >0 && dsDevilAnimDBComboAttacking.DBAddCount <=3 ) {
                Random temprand = new Random();
                int xrange1, yrange1, xrange2, yrange2, xrange3, yrange3;
                //ball1 generated
                xrange1 = temprand.nextInt(60+1)-225; // -205 to 145
                yrange1 = temprand.nextInt(80+1)-70; // -70 to 10
                DarkEnergyBall m1 = new DarkEnergyBall((int) dsDevil.currentBoss.location.x +xrange1,
                        (int) dsDevil.currentBoss.location.y +yrange1);
                //ball2 generated
                xrange2 = temprand.nextInt(120+1)-60; // -120 to 120
                yrange2 = temprand.nextInt(61+1)+75; // 75 to 165
                DarkEnergyBall m2 = new DarkEnergyBall((int) dsDevil.currentBoss.location.x +xrange2,
                        (int) dsDevil.currentBoss.location.y +yrange2);
                //ball3 generated
                xrange3 = temprand.nextInt(60+1)+145; // 145 to 205
                yrange3 = temprand.nextInt(80+1)-70; // -70 to 10
                DarkEnergyBall m3 = new DarkEnergyBall((int) dsDevil.currentBoss.location.x +xrange3,
                        (int) dsDevil.currentBoss.location.y +yrange3);

                if (dsDevilAnimDBComboAttacking.DBAddCount == 1) {
                    switch(dsDevilAnimDBComboAttacking.firstball){
                        case 1:
                            if (MyCanvas.canvaslooping == false && dsDevilAnimDBComboAttacking.shotBall1!=true ) {
                                gameData.enemyObject.add(m1);
                                dsDevil.DBComboAttackCDOn = true;
                                dsDevilAnimDBComboAttacking.shotBall1 = true;
                            }
                            break;
                        case 2:
                            if (MyCanvas.canvaslooping == false && dsDevilAnimDBComboAttacking.shotBall2!=true) {
                                gameData.enemyObject.add(m2);
                                dsDevil.DBComboAttackCDOn = true;
                                dsDevilAnimDBComboAttacking.shotBall2 = true;
                            }
                            break;
                        case 3:
                            if (MyCanvas.canvaslooping == false&& dsDevilAnimDBComboAttacking.shotBall3!=true) {
                                gameData.enemyObject.add(m3);
                                dsDevil.DBComboAttackCDOn = true;
                                dsDevilAnimDBComboAttacking.shotBall3 = true;
                            }
                            break;
                    }

                } else if (dsDevilAnimDBComboAttacking.DBAddCount == 2) {
                    switch(dsDevilAnimDBComboAttacking.secondball){
                        case 1:
                            if (MyCanvas.canvaslooping == false && dsDevilAnimDBComboAttacking.shotBall1!=true ) {
                                gameData.enemyObject.add(m1);
                                dsDevil.DBComboAttackCDOn = true;
                                dsDevilAnimDBComboAttacking.shotBall1 = true;
                            }
                            break;
                        case 2:
                            if (MyCanvas.canvaslooping == false && dsDevilAnimDBComboAttacking.shotBall2!=true) {
                                gameData.enemyObject.add(m2);
                                dsDevil.DBComboAttackCDOn = true;
                                dsDevilAnimDBComboAttacking.shotBall2 = true;
                            }
                            break;
                        case 3:
                            if (MyCanvas.canvaslooping == false&& dsDevilAnimDBComboAttacking.shotBall3!=true) {
                                gameData.enemyObject.add(m3);
                                dsDevil.DBComboAttackCDOn = true;
                                dsDevilAnimDBComboAttacking.shotBall3 = true;
                            }
                            break;
                    }
                } else if (dsDevilAnimDBComboAttacking.DBAddCount == 3) {
                    switch(dsDevilAnimDBComboAttacking.thirdball){
                        case 1:
                            if (MyCanvas.canvaslooping == false && dsDevilAnimDBComboAttacking.shotBall1!=true ) {
                                gameData.enemyObject.add(m1);
                                dsDevil.DBComboAttackCDOn = true;
                                dsDevilAnimDBComboAttacking.shotBall1 = true;
                            }
                            break;
                        case 2:
                            if (MyCanvas.canvaslooping == false && dsDevilAnimDBComboAttacking.shotBall2!=true) {
                                gameData.enemyObject.add(m2);
                                dsDevil.DBComboAttackCDOn = true;
                                dsDevilAnimDBComboAttacking.shotBall2 = true;
                            }
                            break;
                        case 3:
                            if (MyCanvas.canvaslooping == false&& dsDevilAnimDBComboAttacking.shotBall3!=true) {
                                gameData.enemyObject.add(m3);
                                dsDevil.DBComboAttackCDOn = true;
                                dsDevilAnimDBComboAttacking.shotBall3 = true;
                            }
                            break;
                    }
                }

                if (dsDevilAnimDBComboAttacking.DBAddCount >= 4) { // here should have handled 3 balls, otherwise:
                    while(!dsDevilAnimDBComboAttacking.shotBall1
                            ||!dsDevilAnimDBComboAttacking.shotBall2
                            ||!dsDevilAnimDBComboAttacking.shotBall3) { // while either one is false, loop
                        if (dsDevilAnimDBComboAttacking.shotBall1 == false) {
                            if (MyCanvas.canvaslooping == false) {
                                gameData.enemyObject.add(m1);
                                dsDevil.DBComboAttackCDOn = true;
                                dsDevilAnimDBComboAttacking.shotBall1 = true;
                            }
                        }
                        if (dsDevilAnimDBComboAttacking.shotBall2 == false) {
                            if (MyCanvas.canvaslooping == false) {
                                gameData.enemyObject.add(m2);
                                dsDevil.DBComboAttackCDOn = true;
                                dsDevilAnimDBComboAttacking.shotBall2 = true;
                            }
                        }
                        if (dsDevilAnimDBComboAttacking.shotBall3 == false) {
                            if (MyCanvas.canvaslooping == false) {
                                gameData.enemyObject.add(m3);
                                dsDevil.DBComboAttackCDOn = true;
                                dsDevilAnimDBComboAttacking.shotBall3 = true;
                            }
                        }
                    }
                }
//                    }
//                };
//                delayGenerateTimer3.schedule(piranhaMon.ttTentacle1attack, temprand.nextInt(2000)+2000); //(2.5-4.5second));
            }

            //Tentacle1 attack
            if (dsDevil.currentBoss !=null && dsDevil.tentacle1AttackingOn == true) {
                Tentacle m = new Tentacle( 100+30-10-10,-100);
                Random temprand = new Random();
                int q = temprand.nextInt(20)+20; // 20=1sec, so 2-4
//                piranhaMon.ttTentacle1attack = new TimerTask() {
//                    @Override
//                    public void run() {
                if (dsDevil.currenttentacle1 == null && !dsDevilAnimTentacleAttacking.chosen1) { // last add
                    if (TimeClock % q == 0 && dsDevil.tentacleAttackCoolDown == 0) {

                            if (MyCanvas.canvaslooping == false) {
                                gameData.enemyObject.add(m);
                                dsDevil.currenttentacle1 = m;
                                dsDevilAnimTentacleAttacking.chosen1 = true;

                                dsDevil.tentacle1AttackingOn = false; // last add

                                dsDevil.tentacleAttackCDOn = true;
                            }
                    }
                }
//                    }
//                };
//                delayGenerateTimer3.schedule(piranhaMon.ttTentacle1attack, temprand.nextInt(2000)+2000); //(2.5-4.5second));
            }

            //Tentacle2 attack
            if (dsDevil.currentBoss !=null && dsDevil.tentacle2AttackingOn == true) {
                Tentacle m = new Tentacle( 100+200+40-20-10,-100);
                Random temprand = new Random();
                int q = temprand.nextInt(20)+20; // 20=1sec, so 2-4
//                piranhaMon.ttTentacle1attack = new TimerTask() {
//                    @Override
//                    public void run() {
                if (dsDevil.currenttentacle2 == null && !dsDevilAnimTentacleAttacking.chosen2) { // last add
                    if (TimeClock % q == 0 && dsDevil.tentacleAttackCoolDown == 0) {

                            if (MyCanvas.canvaslooping == false) {
                                gameData.enemyObject.add(m);
                                dsDevil.currenttentacle2 = m;
                                dsDevilAnimTentacleAttacking.chosen2 = true;

                                dsDevil.tentacle2AttackingOn = false; // last add

                                dsDevil.tentacleAttackCDOn = true;
                            }

                    }
                }
//                    }
//                };
//                delayGenerateTimer3.schedule(piranhaMon.ttTentacle1attack, temprand.nextInt(2000)+2000); //(2.5-4.5second));
            }

            //Tentacle3 attack
            if (dsDevil.currentBoss !=null && dsDevil.tentacle3AttackingOn == true&& dsDevilAnimTentacleAttacking.chosen3 == false) {
                Tentacle m = new Tentacle( 1200-(100+200+40-20),-100);
                Random temprand = new Random();
                int q = temprand.nextInt(20)+20 ; // 20=1sec, so 2-4
//                piranhaMon.ttTentacle1attack = new TimerTask() {
//                    @Override
//                    public void run() {
                if (dsDevil.currenttentacle3 == null && !dsDevilAnimTentacleAttacking.chosen3) { // last add
                    if (TimeClock % q == 0 && dsDevil.tentacleAttackCoolDown == 0) {

                            if (MyCanvas.canvaslooping == false) {
                                gameData.enemyObject.add(m);
                                dsDevil.currenttentacle3 = m;
                                dsDevilAnimTentacleAttacking.chosen3 = true;

                                dsDevil.tentacle3AttackingOn = false; // last add

                                dsDevil.tentacleAttackCDOn = true;
                            }
                    }
                }
//                    }
//                };
//                delayGenerateTimer3.schedule(piranhaMon.ttTentacle1attack, temprand.nextInt(2000)+2000); //(2.5-4.5second));
            }

            //Tentacle4 attack
            if (dsDevil.currentBoss !=null && dsDevil.tentacle4AttackingOn == true&& dsDevilAnimTentacleAttacking.chosen4 == false) {
                Tentacle m = new Tentacle( 1200-(100+30-10),-100);
                Random temprand = new Random();
                int q = temprand.nextInt(20)+20; // 20=1sec, so 2-4
//                piranhaMon.ttTentacle1attack = new TimerTask() {
//                    @Override
//                    public void run() {
                if (dsDevil.currenttentacle4 == null && !dsDevilAnimTentacleAttacking.chosen4) { // last add
                    if (TimeClock % q == 0 && dsDevil.tentacleAttackCoolDown == 0) {

                            if (MyCanvas.canvaslooping == false) {
                                gameData.enemyObject.add(m);
                                dsDevil.currenttentacle4 = m;
                                dsDevilAnimTentacleAttacking.chosen4 = true;

                                dsDevil.tentacle4AttackingOn = false; // last add

                                dsDevil.tentacleAttackCDOn = true;
                            }
                    }
                }
//                    }
//                };
//                delayGenerateTimer3.schedule(piranhaMon.ttTentacle1attack, temprand.nextInt(2000)+2000); //(2.5-4.5second));
            }



        }



        //CONGRATULATIONS PART
        if (EnemyObserverAddNew.EnemydiedCount == 7) {
                Font font = new Font("Courier New", Font.BOLD,60);
                x = 300-50-30+5-75;
                y = 300+50;
                var CongratulationsVar = new Congratulations("CONGRATULATIONS!",x, y, Color.BLACK, font);
                CongratulationsVar.attachListener(new EnemyObserverAddNew());
                gameData.enemyObject.add(CongratulationsVar);
            }




        } // END addEnemyWithListener


        //Piranha Phase
//        System.out.println(EnemyObserverAddNew.LobdiedCount);
//        System.out.println(EnemyObserverAddNew.callPiranhaMonOn);
//        if (EnemyObserverAddNew.LobdiedCount == 3 && EnemyObserverAddNew.callPiranhaMonOn == true) {
//            if (piranhaMon.currenttentacle1 == null) {
//                System.out.println("Main line391 here");
//                Random rand2 = new Random();
//                int x = rand2.nextInt(1100) + 50;
//                int y = -100;
//                var piranM = new piranhaMon(x, y);
//                gameData.enemyObject.add(piranM);
//            }
//            if (piranhaMon.currenttentacle1 != null && piranhaMon.currenttentacle2 == null) {
//                ttAddPiranha2 = new TimerTask() {
//                    @Override
//                    public void run() {
//                        Random rand2 = new Random();
//                        int x = rand2.nextInt(1100) + 50;
//                        int y = -100;
//                        var piranM = new piranhaMon(x, y);
//                        gameData.enemyObject.add(piranM);   // wait after delay time and add new
//                    }
//                };
//                Random rand2 = new Random();
//                Main.delayGenerateTimer2.schedule(ttAddPiranha2, rand2.nextInt(2000) + 2500); //(2.5-4.5second));
//            }
//
//        }
//    }


    public static void addStarMwithListener(){
        if (gameOver != true && MyCanvas.canvaslooping == false) {

            StarFish.starfishAddCheckDelayOn = true; // AUTO CHECK ADD STARFISH

            Random rand = new Random();

            var turtlehero = (turtleHero) Main.gameData.fixedObject.get(Main.INDEX_TURTLEHERO);
            int x = rand.nextInt(1100) + 50;  // initialize x
            int y = rand.nextInt(200) + 400;  // initialize y
            boolean locationOK = false;

            while (locationOK == false) { // while the location is uncheck, loop
                x = rand.nextInt(1100) + 50;  // generate x
                y = rand.nextInt(200) + 400;  // generate y

                Point2D.Float generateLocation = new Point2D.Float(x, y); // generate location

                Point2D.Float turtleCenter = new Point2D.Float(turtlehero.location.x - 12, turtlehero.location.y + 59);
                double distStarCen = turtleCenter.distance(generateLocation); // check turtle dist

                if (distStarCen > turtlehero.getCollisionRadius() + 25 +50) { // starfish radius 25
                    locationOK = true;
                }
            }

            // important add star
//            var star = new starfishEnergy(x, y);
//            star.attachListener(new StarObserverAddNew());
//            gameData.friendObject.add(star);

            //BUILD DESIGN PATTERN
            if (StarFish.starEnergyExisting == false) {
                StarFishBuildDirector director = new StarFishBuildDirector();
                StarFishBuilder energybuilder = new StarFishEnergyBuilder();
                System.out.println("LINE888");
                director.setStarBuilder(energybuilder);
                director.createStar(x, y);
                StarFish starenergy = director.getStar();

                starenergy.attachListener(new StarObserverAddNew());
                gameData.friendObject.add(starenergy);
                StarFish.starEnergyExisting = true;
            }

            if (EnemyObserverAddNew.EnemydiedCount >=3 && StarFish.starLifeExisting == false
                    && StarFish.starfishLifeCoolDown ==0) {
                StarFishBuildDirector director = new StarFishBuildDirector();
                StarFishBuilder lifebuilder = new StarFishLifeBuilder();

                locationOK = false;

                int x1=0, y1=0;
                while (locationOK == false) { // while the location is uncheck, loop
                    x1 = rand.nextInt(1100) + 50;  // generate x
                    y1 = rand.nextInt(200) + 400;  // generate y

                    Point2D.Float generateLocation = new Point2D.Float(x1, y1); // generate location

                    Point2D.Float turtleCenter = new Point2D.Float(turtlehero.location.x - 12, turtlehero.location.y + 59);
                    double distStarCen = turtleCenter.distance(generateLocation); // check turtle dist

                    if (distStarCen > turtlehero.getCollisionRadius() + 25 +50) { // starfish radius 25
                        locationOK = true;
                    }
                }

                director.setStarBuilder(lifebuilder);
                director.createStar(x1, y1);
                StarFish starlife = director.getStar();

                starlife.attachListener(new StarObserverAddNew());
                gameData.friendObject.add(starlife);
                StarFish.starLifeExisting = true;
            }


            if (EnemyObserverAddNew.EnemydiedCount >=5 && StarFish.starPowerExisting == false
                    && StarFish.starSpeedExisting == false && StarFish.starfishBuffCoolDown ==0) {
                StarFishBuildDirector director = new StarFishBuildDirector();
                StarFishBuilder powerbuilder = new StarFishPowerBuilder();
                StarFishBuilder speedbuilder = new StarFishSpeedBuilder();


                locationOK = false;
                int x2=0, y2=0, x3=0, y3=0;

                while (locationOK == false) { // while the location is uncheck, loop
                    x2 = rand.nextInt(500) + 50;  // initialize x 50 -550
                    y2 = rand.nextInt(200) + 400;  // initialize y

                    x3 = rand.nextInt(500) + 650;  // initialize x 650 -1150
                    y3 = rand.nextInt(200) + 400;  // initialize y

                    Point2D.Float generateLocation1 = new Point2D.Float(x2, y2); // generate location
                    Point2D.Float generateLocation2 = new Point2D.Float(x3, y3); // generate location

                    Point2D.Float turtleCenter = new Point2D.Float(turtlehero.location.x - 12, turtlehero.location.y + 59);
                    double distStarCen1 = turtleCenter.distance(generateLocation1); // check turtle dist
                    double distStarCen2 = turtleCenter.distance(generateLocation2); // check turtle dist

                    if (distStarCen1 > turtlehero.getCollisionRadius() + 25 +50
                        &&distStarCen2 > turtlehero.getCollisionRadius() + 25 +50) { // starfish radius 25
                        locationOK = true;
                    }
                }


                director.setStarBuilder(powerbuilder);
                director.createStar(x2, y2);
                StarFish starpower = director.getStar();

                director.setStarBuilder(speedbuilder);
                director.createStar(x3, y3);
                StarFish starspeed = director.getStar();

                starpower.attachListener(new StarObserverAddNew());
                starspeed.attachListener(new StarObserverAddNew());
                gameData.friendObject.add(starpower);
                gameData.friendObject.add(starspeed);
                StarFish.starPowerExisting = true;
                StarFish.starSpeedExisting = true;
            }


            //        else {
//            var lobM = new lobsterMon(x, y, 200);
//            lobM.attachListener(new EnemyObserverAddNew());
//            gameData.enemyObject.add(lobM);
//        }
        }
    }

    public static void gameLoop(){

        running = true;

        // game loop
        while (running) {
            long startTime = System.currentTimeMillis();

            playerInputEventQueue.processInputEvents(); // when running see if player do like a mouse click press or keyd

            processCollisions(); // the easiest way to detect collsion is to check the distance of 2

            // THIS PART WILL CRASH WITH EX10 lobsterMon OBSERVER INPUTEVENT
//            if (lobsterMon.currentLobsterMon == null && createUFO == true) {
//                gameData.enemyObject.add(new lobsterMon(100, 300));
//                createUFO = false;
//            }

            if (!gamePaused) {
                gameData.update();
                staticDataUpdate();
                win.canvas.render();
            }

            long endTime = System.currentTimeMillis();

            long timeSpent = endTime - startTime;
            long sleepTime = (long)(1000.0/FPS - timeSpent);
            try {
                if (sleepTime > 0) Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void endScreen_Clear() {
        // when running != true, just hold the end screen
        // show initial message on canvas
        // Although we just display sth, we need game figure, that's how canvas draw
        Font font = new Font("Courier New", Font.BOLD,50);
        gameData.friendObject.add(new Text("STAGE CLEAR!!!!!", 150, 200, Color.WHITE, font));
//        while (!running) {
//            Main.win.canvas.render();
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        // finish when running == true;
    }

    public static void endScreen_gameOver() {

        System.out.println("HERE IN ENDSCREEN_GAMEOVER");
        // when running != true, just hold the end screen
        // show initial message on canvas
        // Although we just display sth, we need game figure, that's how canvas draw
        Font font = new Font("Courier New", Font.BOLD,80);
        gameData.friendObject.add(new Text("GAME OVER", 368, 392, Color.WHITE, font));
        MyCanvas.g2BgColor = new Color(37,37,37);
//        while (!running) {
//            Main.win.canvas.render();
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        // finish when running == true;
    }


    static void processCollisions() {

        collisionLooping = true; // HANDLED CONCURRENTMODIFICATION EXCEPTION HERE

        try { // HANDLED VERY RARE OUT OF BOUNDS EXCEPTION WHEN RESTART
            var turtlehero = (turtleHero) Main.gameData.fixedObject.get(Main.INDEX_TURTLEHERO);

            Point2D.Float turtleCenter = new Point2D.Float(turtlehero.location.x - 12, turtlehero.location.y + 59);
            Font font = new Font("Courier New", Font.BOLD, 20);

            for (var enemy : Main.gameData.enemyObject) {
                if (turtlehero.collideWith(enemy)) {
                    turtlehero.hitCount++;
                    enemy.hitCount++;
                    enemy.hitTheTurtleHero++;

                    // HANDLE CHARACTER DAMAGE HERE!!!!!!!!
                    if ((enemy.enemyINDEX == 3 || enemy.enemyINDEX == 5)  // 3: iceberg/dark crystal  5: dark ball
                            && enemy.hitTheTurtleHero == 1) {
                        turtlehero.characterLife--;
                        if (turtlehero.characterLife == 0) {
                            Main.gameOver = true;
                            if (Main.calledGameOver == false) { // avoid calling gameover text twice, will crash with concurrentmod exception
                                Main.endScreen_gameOver();
                                Main.calledGameOver = true;
                            }
                        }
                    }

                }
            }

            for (var friend : Main.gameData.friendObject) {
                if (friend.friendINDEX == 3) { //starfish
//                System.out.println(friend);
//                System.out.println(friend.collideWith(turtlehero)); // DON'T KNOW WHY friend cannot collidwith turtle hero
//                System.out.println(turtlehero.collideWith(friend)); // but turtle hero can collidwith friend
                    if (turtlehero.collideWith(friend)) {
                        friend.hitTheTurtleHero++;
                        if (friend.hitTheTurtleHero == 1 && friend.starType == StarFish.TYPE_STARFISHENERGY) {
                            if (turtlehero.energyValue < 96) turtlehero.energyValue += 5; // when 0-95 add5
                            else if (turtlehero.energyValue == 100)
                                turtlehero.energyValue += 0; // when full, not add more
                            else
                                turtlehero.energyValue = 100; // when not <96, and not ==100, then 97-99 add 5, will be just 100
                        }

                        if (friend.hitTheTurtleHero == 1 && friend.starType == StarFish.TYPE_STARFISHLIFE) {
                            if (turtlehero.characterLife < 3) turtlehero.characterLife += 1; // when 0-2, +1
                            else if (turtlehero.characterLife == 3)
                                turtlehero.characterLife += 0; // when full, not add more
                        }

                        if (friend.hitTheTurtleHero == 1 && friend.starType == StarFish.TYPE_STARFISHPOWER) {
                            if (StarFish.starfishPowerEffectLastingCDOn == false && StarFish.starfishSpeedEffectLastingCDOn == false) {
                                StarFish.starfishPowerEffectLastingCDOn = true;
                            }
                        }

                        if (friend.hitTheTurtleHero == 1 && friend.starType == StarFish.TYPE_STARFISHSPEED) {
                            if (StarFish.starfishSpeedEffectLastingCDOn == false && StarFish.starfishPowerEffectLastingCDOn == false) {
                                StarFish.starfishSpeedEffectLastingCDOn = true;
                            }
                        }


//                    System.out.print(friend.hitTheTurtleHero);
                    }
                }
                for (var enemy : Main.gameData.enemyObject) {
                    if (friend.collideWith(enemy) && enemy.location.y > -100) { // avoid the enemy hasn't come out and disappear
                        friend.hitCount++;
                        enemy.hitCount++;

                        if (friend.hitThisGameFigure == enemy) {
                            friend.hitThisGameFigureAlready = true;
                        } else if (friend.hitThisGameFigure == null) {
                            friend.hitThisGameFigure = enemy;
                        } else { //hit other enemy
                            friend.hitThisGameFigureAlready = false;
                            friend.hitThisGameFigure = enemy;
                        }

                        //CANNOT USE THIS, THERE'S A BUG WHEN THIS IS TRUE AND THE ENEMY HIT A STARFISH AND DISAPPEAR LOL...
//                    if (PlayerInputEventQueue.weaponForm == PlayerInputEventQueue.WEAPON_WATERBEAM ||
//                            PlayerInputEventQueue.weaponForm == PlayerInputEventQueue.WEAPON_SPARKLINGBUBBLE ) {
//                        enemy.hitByStrongCount++;
//                    }

                        if (friend.friendINDEX == 2 && friend.hitCount == 1) {
                            if (enemy.enemyINDEX != 4 && StarFish.starfishPowerEffectLastingCDOn == true) { // not ds Devil POWER EFFECT
                                enemy.enemyHP -= 5 + 5;
                                if (enemy.enemyINDEX == 1) { // lobM
                                    gameData.textObject.add(new Damage("-10HP", (int) enemy.location.x + 40, (int) enemy.location.y - 40, Color.YELLOW, font));
                                }
                                if (enemy.enemyINDEX == 2) { // piranM
                                    gameData.textObject.add(new Damage("-10HP", (int) enemy.location.x - 14 + 40, (int) enemy.location.y + 135 - 40, Color.YELLOW, font));
                                }

                                if (enemy.enemyINDEX == 6) { // tentacles
                                    gameData.textObject.add(new Damage("-10HP", (int) enemy.location.x - 14 + 14 + 40, (int) enemy.location.y + 135 - 135 - 40, Color.YELLOW, font));
                                }
                            } else if (enemy.enemyINDEX != 4) { // not ds Devil
                                enemy.enemyHP -= 5;
                                if (enemy.enemyINDEX == 1) { // lobM
                                    gameData.textObject.add(new Damage("-5HP", (int) enemy.location.x + 40, (int) enemy.location.y - 40, Color.YELLOW, font));
                                }
                                if (enemy.enemyINDEX == 2) { // piranM
                                    gameData.textObject.add(new Damage("-5HP", (int) enemy.location.x - 14 + 40, (int) enemy.location.y + 135 - 40, Color.YELLOW, font));
                                }

                                if (enemy.enemyINDEX == 6) { // tentacles
                                    gameData.textObject.add(new Damage("-5HP", (int) enemy.location.x - 14 + 14 + 40, (int) enemy.location.y + 135 - 135 - 40, Color.YELLOW, font));
                                }
                            } else if (enemy.enemyINDEX == 4 && !Main.gameOver && StarFish.starfishPowerEffectLastingCDOn == true) { // dsDevil
                                if (dsDevil.posingFinished == true) {
                                    enemy.enemyHP -= 5 + 5;
                                    gameData.textObject.add(new Damage("-10HP", (int) enemy.location.x - 14 + 40, (int) enemy.location.y + 135 - 40, Color.YELLOW, font));
                                }
                            } else if (enemy.enemyINDEX == 4 && !Main.gameOver) { // dsDevil
                                if (dsDevil.posingFinished == true) {
                                    enemy.enemyHP -= 5;
                                    gameData.textObject.add(new Damage("-5HP", (int) enemy.location.x - 14 + 40, (int) enemy.location.y + 135 - 40, Color.YELLOW, font));
                                }
                            }
                        }
//                    System.out.println("frinedI "+ friend.friendINDEX);
//                    System.out.println("hitC "+ friend.hitCount);

                        if ((friend.friendINDEX == 4 || friend.friendINDEX == 5) && friend.hitCount == 1 && friend.hitThisGameFigureAlready == false) {
                            enemy.hitByStrongCount++;
                            int temptime = TimeClock;
                            if (tempTimeClock == -1) {
                                tempTimeClock = temptime;
                            } else if (tempTimeClock == temptime) {
                                tempTimeClock = -1;
                            } else {
                                tempTimeClock = TimeClock;
                            }


                            if (enemy.enemyINDEX != 4 && StarFish.starfishPowerEffectLastingCDOn == true) { // POWER EFFECT
                                if (friend.hitThisGameFigureAlready == false && tempTimeClock != -1)
                                    enemy.enemyHP -= 30 + 10; // AVOID WATER BEAM DETERMINE TOO FAST
                                if (enemy.enemyINDEX == 1) { // lobM
                                    gameData.textObject.add(new Damage("-40HP", (int) enemy.location.x + 40, (int) enemy.location.y - 40, Color.YELLOW, font));
                                }
                                if (enemy.enemyINDEX == 2) { // piranM
                                    gameData.textObject.add(new Damage("-40HP", (int) enemy.location.x - 14 + 40, (int) enemy.location.y + 135 - 40, Color.YELLOW, font));
                                }
                                if (enemy.enemyINDEX == 6) { // tentacles
                                    gameData.textObject.add(new Damage("-40HP", (int) enemy.location.x - 14 + 14 + 40, (int) enemy.location.y + 135 - 135 - 40, Color.YELLOW, font));
                                }

                            } else if (enemy.enemyINDEX != 4) {
                                if (friend.hitThisGameFigureAlready == false && tempTimeClock != -1)
                                    enemy.enemyHP -= 30; // AVOID WATER BEAM DETERMINE TOO FAST
                                if (enemy.enemyINDEX == 1) { // lobM
                                    gameData.textObject.add(new Damage("-30HP", (int) enemy.location.x + 40, (int) enemy.location.y - 40, Color.YELLOW, font));
                                }
                                if (enemy.enemyINDEX == 2) { // piranM
                                    gameData.textObject.add(new Damage("-30HP", (int) enemy.location.x - 14 + 40, (int) enemy.location.y + 135 - 40, Color.YELLOW, font));
                                }
                                if (enemy.enemyINDEX == 6) { // tentacles
                                    gameData.textObject.add(new Damage("-30HP", (int) enemy.location.x - 14 + 14 + 40, (int) enemy.location.y + 135 - 135 - 40, Color.YELLOW, font));
                                }

                            } else if (enemy.enemyINDEX == 4 && StarFish.starfishPowerEffectLastingCDOn == true) { // dsDevil
                                if (dsDevil.posingFinished == true) {
                                    if (friend.hitThisGameFigureAlready == false && tempTimeClock != -1 && !Main.gameOver)
                                        enemy.enemyHP -= 30 + 10; // AVOID WATER BEAM DETERMINE TOO FAST
                                    gameData.textObject.add(new Damage("-40HP", (int) enemy.location.x - 14 + 40, (int) enemy.location.y + 135 - 40, Color.YELLOW, font));

                                }
                            } else if (enemy.enemyINDEX == 4) { // dsDevil
                                if (dsDevil.posingFinished == true) {
                                    if (friend.hitThisGameFigureAlready == false && tempTimeClock != -1 && !Main.gameOver)
                                        enemy.enemyHP -= 30; // AVOID WATER BEAM DETERMINE TOO FAST
                                    gameData.textObject.add(new Damage("-30HP", (int) enemy.location.x - 14 + 40, (int) enemy.location.y + 135 - 40, Color.YELLOW, font));

                                }
                            }
                        }

                        if (friend.friendINDEX == 6 && (friend.hitCount % 2 == 0 && enemy.done == false)) { // SHIELD, OK BUT NOT STABLE IF THE PLAYER RUSH FAST
                            if (enemy.blockedByShield == 0) {
                                blockCDOn = true;
                                if (enemy.enemyINDEX != 4) {
                                    enemy.enemyHP -= 30;
                                    if (enemy.blockedByShield < 3) enemy.blockedByShield++;
                                    if (enemy.enemyINDEX == 1) { // lobM
                                        gameData.textObject.add(new Damage("-10HP", (int) enemy.location.x + 40 + 10 * enemy.blockedByShield, (int) enemy.location.y - 40 - 10 * enemy.blockedByShield, Color.YELLOW, font));
                                    }
                                    if (enemy.enemyINDEX == 2) { // piranM
                                        gameData.textObject.add(new Damage("-10HP", (int) enemy.location.x - 14 + 40 + 10 * enemy.blockedByShield, (int) enemy.location.y + 135 - 40 - 10 * enemy.blockedByShield, Color.YELLOW, font));
                                    }

                                    if (enemy.enemyINDEX == 6) { // tentacles
                                        gameData.textObject.add(new Damage("-10HP", (int) enemy.location.x - 14 + 14 + 40 + 10 * enemy.blockedByShield, (int) enemy.location.y + 135 - 135 - 40 - 10 * enemy.blockedByShield, Color.YELLOW, font));
                                    }

                                }
                                if (enemy.blockedByShield == 1) {
                                    blockCDOn = true;
                                    if (enemy.blockedByShield < 3) enemy.blockedByShield++;
                                    if (enemy.enemyINDEX == 1) { // lobM
                                        gameData.textObject.add(new Damage("-10HP", (int) enemy.location.x + 40 + 10 * enemy.blockedByShield, (int) enemy.location.y - 40 - 10 * enemy.blockedByShield, Color.YELLOW, font));
                                    }
                                    if (enemy.enemyINDEX == 2) { // piranM
                                        gameData.textObject.add(new Damage("-10HP", (int) enemy.location.x - 14 + 40 + 10 * enemy.blockedByShield, (int) enemy.location.y + 135 - 40 - 10 * enemy.blockedByShield, Color.YELLOW, font));
                                    }

                                }
                                if (enemy.blockedByShield == 2) {
                                    blockCDOn = true;
                                    if (enemy.blockedByShield < 3) enemy.blockedByShield++;
                                    if (enemy.enemyINDEX == 1) { // lobM
                                        gameData.textObject.add(new Damage("-10HP", (int) enemy.location.x + 40 + 10 * enemy.blockedByShield, (int) enemy.location.y - 40 - 10 * enemy.blockedByShield, Color.YELLOW, font));
                                    }
                                    if (enemy.enemyINDEX == 2) { // piranM
                                        gameData.textObject.add(new Damage("-10HP", (int) enemy.location.x - 14 + 40 + 10 * enemy.blockedByShield, (int) enemy.location.y + 135 - 40 - 10 * enemy.blockedByShield, Color.YELLOW, font));
                                    }

                                }
                            } else if (enemy.enemyINDEX == 4) {
                                if (dsDevil.posingFinished == true && !Main.gameOver) {
                                    enemy.enemyHP -= 30;
                                    if (enemy.blockedByShield < 3) enemy.blockedByShield++;
                                    gameData.textObject.add(new Damage("-10HP", (int) enemy.location.x - 14 + 40 + 10 * enemy.blockedByShield, (int) enemy.location.y + 135 - 40 - 10 * enemy.blockedByShield, Color.YELLOW, font));

                                    if (enemy.blockedByShield == 1) {
                                        blockCDOn = true;
                                        if (enemy.blockedByShield < 3) enemy.blockedByShield++;
                                        gameData.textObject.add(new Damage("-10HP", (int) enemy.location.x - 14 + 40 + 10 * enemy.blockedByShield, (int) enemy.location.y + 135 - 40 - 10 * enemy.blockedByShield, Color.YELLOW, font));
                                    }

                                    if (enemy.blockedByShield == 2) {
                                        blockCDOn = true;
                                        if (enemy.blockedByShield < 3) enemy.blockedByShield++;
                                        gameData.textObject.add(new Damage("-10HP", (int) enemy.location.x - 14 + 40 + 10 * enemy.blockedByShield, (int) enemy.location.y + 135 - 40 - 10 * enemy.blockedByShield, Color.YELLOW, font));
                                    }
                                }
                            }


                        }
//                    if (friend.friendINDEX == 6 && friend.hitCount >1){
//                        int tempi = enemy.blockedByShield;
//
//                        if (tempTime ==0) tempTime = TimeClock;
//                        int currentTimeClock = TimeClock;
//                        if (tempTime -currentTimeClock > 1000) currentTimeClock+=1200; // in case the TimeClock turn back to 0
//
//                        if (currentTimeClock - tempTime >=10 && currentTimeClock - tempTime <=15  ) {
//                            if (enemy.blockedByShield<3) enemy.blockedByShield++;
//                        }
//                        if (currentTimeClock - tempTime >=20 && currentTimeClock - tempTime <=25  ) {
//                            if (enemy.blockedByShield<3) enemy.blockedByShield++;
//                        }
//                        if (currentTimeClock - tempTime >=30 && currentTimeClock - tempTime <=35  ) {
//                            if (enemy.blockedByShield<3) enemy.blockedByShield++;
//                        }
//
//                        if (enemy.blockedByShield>tempi){
//                                    enemy.enemyHP -= 10;
//                                    gameData.textObject.add(new Damage("-10HP", (int) enemy.location.x + 40 +10 *enemy.blockedByShield, (int) enemy.location.y - 40 -10 *enemy.blockedByShield, Color.YELLOW, font));
//                        }
//                        System.out.println(enemy.blockedByShield);
                    }


                }

                //Tentacle arms collision
                // just work for the friends had set correct radius
                if (friend.friendINDEX != 3) { // not starfish
                    if (dsDevil.currenttentacle1 != null) { //x (int)location.x-100/2, y originalY-100/2 w 100 h locationy-originaly
                        //x 110-        y o.location.y-25 to -100
//                    if (friend.friendINDEX!=1) { // not turtle hero
//                        System.out.println("LINE1184 friend location " + friend.location.x + " " + friend.location.y);
                        if (friend.location.x + friend.getCollisionRadius() > dsDevil.currenttentacle1.location.x - 100 / 2 //within left and right, and bottom edge
                                && friend.location.x - friend.getCollisionRadius() < dsDevil.currenttentacle1.location.x - 100 / 2 + 100
                                && friend.location.y < dsDevil.currenttentacle1.location.y) {
                            friend.hitCount++;
                        }

//                    }
//                    else if (friend.friendINDEX==1){
//                        Point2D.Float turtleCenter = new Point2D.Float(friend.location.x - 12, friend.location.y + 59);
//                        if (turtleCenter.x + friend.getCollisionRadius() > dsDevil.currenttentacle1.location.x - 100 / 2
//                                && turtleCenter.x - friend.getCollisionRadius() < dsDevil.currenttentacle1.location.x - 100 / 2 + 100
//                                && turtleCenter.y < dsDevil.currenttentacle1.location.y) {
//                            friend.hitCount++;
//                            dsDevil.currenttentacle1.hitTheTurtleHero++;
//                        }
//
//                    }

                    }

                    if (dsDevil.currenttentacle2 != null) { //x (int)location.x-100/2, y originalY-100/2 w 100 h locationy-originaly
                        //x 110-        y o.location.y-25 to -100
//                    if (friend.friendINDEX!=1) { // not turtle hero
                        if (friend.location.x + friend.getCollisionRadius() > dsDevil.currenttentacle2.location.x - 100 / 2
                                && friend.location.x - friend.getCollisionRadius() < dsDevil.currenttentacle2.location.x - 100 / 2 + 100
                                && friend.location.y < dsDevil.currenttentacle2.location.y) {
                            friend.hitCount++;
                        }

//                    }
//                    else if (friend.friendINDEX==1){
//                        Point2D.Float turtleCenter = new Point2D.Float(friend.location.x - 12, friend.location.y + 59);
//                        if (turtleCenter.x + friend.getCollisionRadius() > dsDevil.currenttentacle2.location.x - 100 / 2
//                                && turtleCenter.x - friend.getCollisionRadius() < dsDevil.currenttentacle2.location.x - 100 / 2 + 100
//                                && turtleCenter.y < dsDevil.currenttentacle2.location.y) {
//                            friend.hitCount++;
//                            dsDevil.currenttentacle2.hitTheTurtleHero++;
//                        }
//                    }

                    }

                    if (dsDevil.currenttentacle3 != null) { //x (int)location.x-100/2, y originalY-100/2 w 100 h locationy-originaly
                        //x 110-        y o.location.y-25 to -100
//                    if (friend.friendINDEX!=1) { // not turtle hero
                        if (friend.location.x + friend.getCollisionRadius() > dsDevil.currenttentacle3.location.x - 100 / 2
                                && friend.location.x - friend.getCollisionRadius() < dsDevil.currenttentacle3.location.x - 100 / 2 + 100
                                && friend.location.y < dsDevil.currenttentacle3.location.y) {
                            friend.hitCount++;
                        }

//                    }
//                    else if (friend.friendINDEX==1){
//                        Point2D.Float turtleCenter = new Point2D.Float(friend.location.x - 12, friend.location.y + 59);
//                        if (turtleCenter.x + friend.getCollisionRadius() > dsDevil.currenttentacle3.location.x - 100 / 2
//                                && turtleCenter.x - friend.getCollisionRadius() < dsDevil.currenttentacle3.location.x - 100 / 2 + 100
//                                && turtleCenter.y < dsDevil.currenttentacle3.location.y) {
//                            friend.hitCount++;
//                            dsDevil.currenttentacle3.hitTheTurtleHero++;
//                        }
//                    }

                    }

                    if (dsDevil.currenttentacle4 != null) { //x (int)location.x-100/2, y originalY-100/2 w 100 h locationy-originaly
                        //x 110-        y o.location.y-25 to -100
//                    if (friend.friendINDEX!=1) { // not turtle hero
                        if (friend.location.x + friend.getCollisionRadius() > dsDevil.currenttentacle4.location.x - 100 / 2
                                && friend.location.x - friend.getCollisionRadius() < dsDevil.currenttentacle4.location.x - 100 / 2 + 100
                                && friend.location.y < dsDevil.currenttentacle4.location.y) {
                            friend.hitCount++;
                        }

//                    }
//                    else if (friend.friendINDEX==1){
//                        Point2D.Float turtleCenter = new Point2D.Float(friend.location.x - 12, friend.location.y + 59);
//                        if (turtleCenter.x + friend.getCollisionRadius() > dsDevil.currenttentacle4.location.x - 100 / 2
//                                && turtleCenter.x - friend.getCollisionRadius() < dsDevil.currenttentacle4.location.x - 100 / 2 + 100
//                                && turtleCenter.y < dsDevil.currenttentacle4.location.y) {
//                            friend.hitCount++;
//                            dsDevil.currenttentacle4.hitTheTurtleHero++;
//                        }
//
//                    }

                    }

                }


            } // end friend loop


            // turtle hero and tentacles collision  // turtle hero has friendINDEX... but not friendObject...
            {
                if (dsDevil.currenttentacle1 != null) { //x (int)location.x-100/2, y originalY-100/2 w 100 h locationy-originaly

                    if (turtleCenter.x + turtlehero.getCollisionRadius() > dsDevil.currenttentacle1.location.x - 100 / 2
                            && turtleCenter.x - turtlehero.getCollisionRadius() < dsDevil.currenttentacle1.location.x - 100 / 2 + 100
                            && turtleCenter.y < dsDevil.currenttentacle1.location.y) {
                        turtlehero.hitCount++;
                        dsDevil.currenttentacle1.hitTheTurtleHero++;
                    }


                }

                if (dsDevil.currenttentacle2 != null) { //x (int)location.x-100/2, y originalY-100/2 w 100 h locationy-originaly

                    if (turtleCenter.x + turtlehero.getCollisionRadius() > dsDevil.currenttentacle2.location.x - 100 / 2
                            && turtleCenter.x - turtlehero.getCollisionRadius() < dsDevil.currenttentacle2.location.x - 100 / 2 + 100
                            && turtleCenter.y < dsDevil.currenttentacle2.location.y) {
                        turtlehero.hitCount++;
                        dsDevil.currenttentacle2.hitTheTurtleHero++;
                    }


                }

                if (dsDevil.currenttentacle3 != null) { //x (int)location.x-100/2, y originalY-100/2 w 100 h locationy-originaly


                    if (turtleCenter.x + turtlehero.getCollisionRadius() > dsDevil.currenttentacle3.location.x - 100 / 2
                            && turtleCenter.x - turtlehero.getCollisionRadius() < dsDevil.currenttentacle3.location.x - 100 / 2 + 100
                            && turtleCenter.y < dsDevil.currenttentacle3.location.y) {
                        turtlehero.hitCount++;
                        dsDevil.currenttentacle3.hitTheTurtleHero++;
                    }


                }

                if (dsDevil.currenttentacle4 != null) { //x (int)location.x-100/2, y originalY-100/2 w 100 h locationy-originaly


                    if (turtleCenter.x + turtlehero.getCollisionRadius() > dsDevil.currenttentacle4.location.x - 100 / 2
                            && turtleCenter.x - turtlehero.getCollisionRadius() < dsDevil.currenttentacle4.location.x - 100 / 2 + 100
                            && turtleCenter.y < dsDevil.currenttentacle4.location.y) {
                        turtlehero.hitCount++;
                        dsDevil.currenttentacle4.hitTheTurtleHero++;
                    }
//


                }
            }
        } catch(ArrayIndexOutOfBoundsException aie) {
            MyWindow.restartButton.doClick();
        } catch(IndexOutOfBoundsException ie) {
            MyWindow.restartButton.doClick();
        }

        collisionLooping = false; // HANDLING THE CONCURRENT MODIFICATION


    }



    static void staticDataUpdate(){

        // normal bubble CD   // for game balence
        if (PlayerInputEventQueue.normalBubbleCDOn == true) {
            PlayerInputEventQueue.normalBubbleCoolDown++; // 20FPS, 1second add 20times

            if (PlayerInputEventQueue.normalBubbleCoolDown == 3 && StarFish.starfishSpeedEffectLastingCDOn == true) { // speed effect
                PlayerInputEventQueue.normalBubbleCDOn = false; //1 too fast...
                PlayerInputEventQueue.normalBubbleCoolDown = 0;
            }
            else if (PlayerInputEventQueue.normalBubbleCoolDown == 5) { // so 4 seconds after cd start // 40->25
                PlayerInputEventQueue.normalBubbleCDOn = false;
                PlayerInputEventQueue.normalBubbleCoolDown = 0;
            }
        }

        // water beam CD
        if (PlayerInputEventQueue.waterBeamCDOn == true){
            PlayerInputEventQueue.waterBeamCoolDown++; // 20FPS, 1second add 20times
            if (PlayerInputEventQueue.waterBeamCoolDown == 25 && StarFish.starfishSpeedEffectLastingCDOn == true) { // speed effect
                PlayerInputEventQueue.waterBeamCDOn = false; // 15 too fast...
                PlayerInputEventQueue.waterBeamCoolDown = 0;
            }
            else if (PlayerInputEventQueue.waterBeamCoolDown == 35) { // so 4 seconds after cd start // 40->25
                PlayerInputEventQueue.waterBeamCDOn = false;
                PlayerInputEventQueue.waterBeamCoolDown = 0;
            }
        }
        if (PlayerInputEventQueue.waterBeamUsedEnergyOn == true){
            PlayerInputEventQueue.waterBeamUsedEnergyCD++; // 20FPS, 1second add 20times add sleep time
            if (PlayerInputEventQueue.waterBeamUsedEnergyCD == 15 && StarFish.starfishSpeedEffectLastingCDOn == true) { // so about 2 seconds after cd start
                PlayerInputEventQueue.waterBeamUsedEnergyOn = false;
                PlayerInputEventQueue.waterBeamUsedEnergyCD = 0;
            }
            else if (PlayerInputEventQueue.waterBeamUsedEnergyCD == 20) { // so about 2 seconds after cd start
                PlayerInputEventQueue.waterBeamUsedEnergyOn = false;
                PlayerInputEventQueue.waterBeamUsedEnergyCD = 0;
            }
        }

        // sparkling bubble CD
        if (PlayerInputEventQueue.sparklingBubbleCDOn == true){
            PlayerInputEventQueue.sparklingBubbleCoolDown++; // 20FPS, 1second add 20times add sleep time
            if (PlayerInputEventQueue.sparklingBubbleCoolDown == 30 && StarFish.starfishSpeedEffectLastingCDOn == true) { // so 4 seconds after cd start 40
                PlayerInputEventQueue.sparklingBubbleCDOn = false; // 20 too fast
                PlayerInputEventQueue.sparklingBubbleCoolDown = 0;
            }
            else if (PlayerInputEventQueue.sparklingBubbleCoolDown == 40) { // so 4 seconds after cd start 40
                PlayerInputEventQueue.sparklingBubbleCDOn = false;
                PlayerInputEventQueue.sparklingBubbleCoolDown = 0;
            }
        }
        if (PlayerInputEventQueue.sparklingBubbleUsedEnergyOn == true){
            PlayerInputEventQueue.sparklingBubbleUsedEnergyCD++; // 20FPS, 1second add 20times add sleep time
            if (PlayerInputEventQueue.sparklingBubbleUsedEnergyCD == 15 && StarFish.starfishSpeedEffectLastingCDOn == true) { // so 2 seconds after cd start
                PlayerInputEventQueue.sparklingBubbleUsedEnergyOn = false;
                PlayerInputEventQueue.sparklingBubbleUsedEnergyCD = 0;
            }
            else if (PlayerInputEventQueue.sparklingBubbleUsedEnergyCD == 20) { // so 2 seconds after cd start
                PlayerInputEventQueue.sparklingBubbleUsedEnergyOn = false;
                PlayerInputEventQueue.sparklingBubbleUsedEnergyCD = 0;
            }
        }

        // whirlpool shield CD
        if (PlayerInputEventQueue.whirlpoolShieldCDOn == true){
            PlayerInputEventQueue.whirlpoolShieldCoolDown++; // 20FPS, 1second add 20times add sleep time
            if (PlayerInputEventQueue.whirlpoolShieldCoolDown == 45) { // so 4.5 seconds after cd start
                PlayerInputEventQueue.whirlpoolShieldCDOn = false;
                PlayerInputEventQueue.whirlpoolShieldCoolDown = 0;
            }
        }
        if (PlayerInputEventQueue.whirlpoolShieldUsedEnergyOn == true){
            PlayerInputEventQueue.whirlpoolShieldUsedEnergyCD++; // 20FPS, 1second add 20times add sleep time
            if (PlayerInputEventQueue.whirlpoolShieldUsedEnergyCD == 20) { // so 2 seconds after cd start
                PlayerInputEventQueue.whirlpoolShieldUsedEnergyOn = false;
                PlayerInputEventQueue.whirlpoolShieldUsedEnergyCD = 0;
            }
        }

        // block CD
        if (blockCDOn == true){
            blockCD++; // 20FPS, 1second add 20times add sleep time
            if (blockCD == 10) { // so 0.5 seconds after cd start
                blockCDOn = false;
                blockCD = 0;
            }
        }

        // piranha1 CD
        if (piranhaMon.piranha1AttackCDOn == true){
            piranhaMon.piranha1AttackCoolDown++; // 20FPS, 1second add 20times add sleep time
            if (piranhaMon.piranha1AttackCoolDown == 90) { // so 4.5 seconds after cd start
                piranhaMon.piranha1AttackCDOn = false;
                piranhaMon.piranha1AttackCoolDown= 0;
            }
        }

        // piranha2 CD
        if (piranhaMon.piranha2AttackCDOn == true){
            piranhaMon.piranha2AttackCoolDown++; // 20FPS, 1second add 20times add sleep time
            if (piranhaMon.piranha2AttackCoolDown == 90) { // so 4.5 seconds after cd start
                piranhaMon.piranha2AttackCDOn = false;
                piranhaMon.piranha2AttackCoolDown= 0;
            }
        }

        //BOSS APPEAR WARNING FLASHING TIME
        if (BossAppearWarning.WARNINGFlashingOn == true){
            BossAppearWarning.WARNINGcolorChangeTime++; // 20FPS, 1second add 20times add sleep time
            if (BossAppearWarning.WARNINGcolorChangeTime == 70) { // so 4.5 seconds after cd start
                BossAppearWarning.WARNINGFlashingOn = false;
                BossAppearWarning.WARNINGcolorChangeTime = 0;
            }
        }

        // DEEPSEA DEVIL PART
        // posing time count
        if (dsDevil.posingTimeCountOn == true) {
            dsDevil.posingTimeCount++;
            if (dsDevil.posingTimeCount == 95){
                dsDevil.posingTimeCountOn = false;
            }
        }

        // eye flashing time count
        if (dsDevil.eyeFlashingTimeCountOn == true) {
            dsDevil.eyeFlashingTimeCount++;
            if (dsDevil.eyeFlashingTimeCount == 60){
                dsDevil.eyeFlashingTimeCountOn = false;
                dsDevil.eyeFlashingTimeCount = 0; // this need to reset 0
            }
        }

        // normal dark crystal CD
        if (dsDevil.DCNormalAttackCDOn == true){
            dsDevil.DCNormalAttackCoolDown++; // 20FPS, 1second add 20times add sleep time
            if (dsDevil.DCNormalAttackCoolDown == 80) { // 90 is just about 2 second....
                dsDevil.DCNormalAttackCDOn = false;
                dsDevil.DCNormalAttackCoolDown = 0;
            }
        }

        // after normal dark crystal CD
        if (dsDevil.AfterNormalAttackCDOn == true){
            dsDevil.AfterNormalAttackCountTime++; // 20FPS, 1second add 20times add sleep time
            if (dsDevil.AfterNormalAttackCountTime == 150) { // 90 is just about 2 second....
                dsDevil.AfterNormalAttackCDOn = false;
//                dsDevil.AfterNormalAttackCountTime = 0;
            }
        }

        // dark energy ball CD
        if (dsDevil.DBComboAttackCDOn == true){
            dsDevil.DBComboAttackCoolDown++; // 20FPS, 1second add 20times add sleep time
            if (dsDevil.DBComboAttackCoolDown == 120) { // 90 is just about 2 second....
                dsDevil.DBComboAttackCDOn = false;
                dsDevil.DBComboAttackCoolDown = 0;
            }
        }

        // dark energy ball shooting CD
        if (dsDevilAnimDBComboAttacking.DBAddDelayCountOn == true){
            dsDevilAnimDBComboAttacking.DBAddDelayCountTime++; // 20FPS, 1second add 20times add sleep time
            if (dsDevilAnimDBComboAttacking.DBAddDelayCountTime == 100) { // 90 is just about 2 second....
                dsDevilAnimDBComboAttacking.DBAddDelayCountOn = false;
//                dsDevilAnimDBComboAttacking.DBAddDelayCountTime = 0;
            }
        }

        // dark energy ball after finish CD
        if (dsDevilAnimDBComboAttacking.AfterDBComboCountOn == true){
            dsDevilAnimDBComboAttacking.AfterDBComboCountTime++; // 20FPS, 1second add 20times add sleep time
            if (dsDevilAnimDBComboAttacking.AfterDBComboCountTime == 180 && dsDevil.comboModeCount==2) { // 90 is just about 2 second....
                dsDevilAnimDBComboAttacking.AfterDBComboCountOn = false;
//                dsDevilAnimDBComboAttacking.DBAddDelayCountTime = 0;
            } else if (dsDevilAnimDBComboAttacking.AfterDBComboCountTime == 60 && dsDevil.comboModeCount<2) { // 90 is just about 2 second....
                dsDevilAnimDBComboAttacking.AfterDBComboCountOn = false;
//                dsDevilAnimDBComboAttacking.DBAddDelayCountTime = 0;
            }
        }



        // tentacle CD
        if (dsDevil.tentacleAttackCDOn == true){
            dsDevil.tentacleAttackCoolDown++; // 20FPS, 1second add 20times add sleep time
            if (dsDevil.tentacleAttackCoolDown == 50) { // 90 is just about 2 second....
                dsDevil.tentacleAttackCDOn = false;
                dsDevil.tentacleAttackCoolDown = 0;
            }
        }


        // starfish life CD
        if (StarFish.starfishLifeCDOn == true) {
            StarFish.starfishLifeCoolDown++;
            if (StarFish.starfishLifeCoolDown == 900) { // 30fps, about 40 sec -> 900 30 sec
                StarFish.starfishLifeCDOn = false;
                StarFish.starfishLifeCoolDown = 0;

                if (StarFish.starLifeExisting == false) {
                    InputEvent starevent = new InputEvent();
                    Random rand = new Random();
                    int x = rand.nextInt(1100)+50;
                    int y = -100;
                    starevent.event = new StarCreateEvent("StarFish", x, y);
                    starevent.type = InputEvent.STAR_CREATE; // let InputEvent handle it...

                    StarObserverAddNew.ttAddStar = new TimerTask() {
                        @Override
                        public void run() {
                            Main.playerInputEventQueue.queue.add(starevent); // wait after delay time and add new
                        }
                    };
                    Main.delayGenerateTimer.schedule(StarObserverAddNew.ttAddStar,rand.nextInt(2500)+3000);
                }

            }
        }

        // starfish buff CD
        if (StarFish.starfishBuffCDOn == true) {
            StarFish.starfishBuffCoolDown++;
            if (StarFish.starfishBuffCoolDown == 900) { // 30fps, about 50 sec -> 30 sec
                StarFish.starfishBuffCDOn = false;
                StarFish.starfishBuffCoolDown = 0;

                if (StarFish.starPowerExisting == false && StarFish.starSpeedExisting == false) {
                    InputEvent starevent = new InputEvent();
                    Random rand = new Random();
                    int x = rand.nextInt(1100)+50;
                    int y = -100;
                    starevent.event = new StarCreateEvent("StarFish", x, y);
                    starevent.type = InputEvent.STAR_CREATE; // let InputEvent handle it...

                    StarObserverAddNew.ttAddStar = new TimerTask() {
                        @Override
                        public void run() {
                            Main.playerInputEventQueue.queue.add(starevent); // wait after delay time and add new
                        }
                    };
                    Main.delayGenerateTimer.schedule(StarObserverAddNew.ttAddStar,rand.nextInt(2500)+3000);
                }

            }
        }

        // starfish buff effect lasting CD
        if (StarFish.starfishPowerEffectLastingCDOn == true) {
            StarFish.starfishPowerEffectLastingCoolDown++;
            if (StarFish.starfishPowerEffectLastingCoolDown == 600) { // 30fps, about 30 sec -> 20 sec 600
                StarFish.starfishPowerEffectLastingCDOn = false;
                StarFish.starfishPowerEffectLastingCoolDown = 0;
            }
        }

        // starfish buff effect lasting CD
        if (StarFish.starfishSpeedEffectLastingCDOn == true) {
            StarFish.starfishSpeedEffectLastingCoolDown++;
            if (StarFish.starfishSpeedEffectLastingCoolDown == 600) { // 30fps, about 30 sec -> 20 sec 600
                StarFish.starfishSpeedEffectLastingCDOn = false;
                StarFish.starfishSpeedEffectLastingCoolDown = 0;
            }
        }

        // starfish add wait delay
        if (StarFish.starfishAddCheckDelayOn == true) {
            StarFish.starfishAddCheckDelay++;
            if (StarFish.starfishAddCheckDelay == 30) { // 30fps, about 30 sec
                StarFish.starfishAddCheckDelayOn = false;
                StarFish.starfishAddCheckDelay = 0;
                InputEvent starevent = new InputEvent();
                Random rand = new Random();
                int x = rand.nextInt(1100)+50;
                int y = -100;
                starevent.event = new StarCreateEvent("StarFish", x, y);
                starevent.type = InputEvent.STAR_CREATE; // let InputEvent handle it...

                StarObserverAddNew.ttAddStar = new TimerTask() {
                    @Override
                    public void run() {
                        Main.playerInputEventQueue.queue.add(starevent); // wait after delay time and add new
                    }
                };
                Main.delayGenerateTimer.schedule(StarObserverAddNew.ttAddStar,rand.nextInt(2500)+3000);
            }
        }

        // angle Line count time
        if (AngleLine.angleLineCountOn == true) {
            AngleLine.angleLineCount++;
            if (AngleLine.angleLineCount == 30) { // 30fps, about 30 sec -> 20 sec 600
                AngleLine.angleLineCountOn = false;
                AngleLine.angleLineCount = 0;
            }
        }

        // turtle hero move left count time
        if (turtleHero.moveLeftCountOn == true) {
            turtleHero.moveLeftCount++;
            if (turtleHero.moveLeftCount == 15) { // 30fps, about 30 sec -> 20 sec 600
                turtleHero.moveLeftCountOn = false;
                turtleHero.moveLeftCount = 0;
            }
        }

        // turtle hero move right count time
        if (turtleHero.moveRightCountOn == true) {
            turtleHero.moveRightCount++;
            if (turtleHero.moveRightCount == 15) { // 30fps, about 30 sec -> 20 sec 600
                turtleHero.moveRightCountOn = false;
                turtleHero.moveRightCount = 0;
            }
        }
        


        //CONGRATULATIONS FLASHING TIME
        if (Congratulations.CongratulationsFlashingOn == true){
            Congratulations.CongratulationscolorChangeTime++; // 20FPS, 1second add 20times add sleep time
            if (Congratulations.CongratulationscolorChangeTime == 9999) { // so 4.5 seconds after cd start
//                Congratulations.CongratulationsFlashingOn = false;
                Congratulations.CongratulationscolorChangeTime = 0;
            }
        }




        // time clock
        TimeClock++;
        if(TimeClock>=12000) TimeClock = 0; //12000 go back to 0; avoid int limit 1second add 20times add sleep time about 1 second 10


    }

}

