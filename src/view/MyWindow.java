package view;

import controller.*;
import controller.enemyobserver.EnemyObserverAddNew;
import controller.starobserver.StarCreateEvent;
import controller.starobserver.StarObserverAddNew;
import model.BossAppearWarning;
import model.Congratulations;
import model.MousePointer;
import model.dsdevil.dsDevil;
import model.piranhamon.piranhaMon;
import model.starfish.StarFish;
import model.turtlehero.turtleHero;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static controller.Main.gameData;

public class MyWindow extends JFrame {

    public MyCanvas canvas; // canvas will be accessed frequently, so public
    public JButton quitButton;
    public static JButton restartButton;
    public static JButton pauseButton;

    public static boolean restarting = false;
    public int looping;

    public static JButton cheatButton_EnergyFull;
    public static JButton cheatButton_SkipToPiranha;
    public static JButton cheatButton_SkipToBoss;

    public void init() {
        setSize(1200, 800);
        setLocation(100, 40);
        setTitle("Game Engine");

        canvas = new MyCanvas();

        MouseEventListener mouseEventListener = new MouseEventListener();
        canvas.addMouseListener(mouseEventListener);
        canvas.addMouseMotionListener(mouseEventListener);

        KeyEventListener keyEventListener = new KeyEventListener(); // declared in class scope
        canvas.addKeyListener(keyEventListener);
        canvas.setFocusable(true); // when we set the keyeventlistener, we should set the graphic component setFocusable
        // to true. But other graphic component should set to F, then this is the one receive
        // this means now the convas is receiving the event from key

        var cp = getContentPane();
        cp.add(BorderLayout.CENTER, canvas);

        quitButton = new JButton("Quit");
        quitButton.setFocusable(false); // important to set not focusable

        restartButton = new JButton("Restart");
        restartButton.setFocusable(false); // important to set not focusable
        restartButton.setEnabled(false);

        pauseButton = new JButton("Pause (Key W)");
        pauseButton.setFocusable(false); // important to set not focusable
        pauseButton.setEnabled(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(quitButton);
        buttonPanel.add(restartButton);
        buttonPanel.add(pauseButton);

        //cheat code buttons area
        buttonPanel.add(new JLabel("      Cheat code for demo -> "));

        cheatButton_EnergyFull = new JButton("Energy Full");
        cheatButton_EnergyFull.setFocusable(false);
        cheatButton_EnergyFull.setEnabled(false); // remember go to PlayerInputEventQueue.nonRunningProcessInputEvents to set

        buttonPanel.add(cheatButton_EnergyFull);

        cheatButton_SkipToPiranha = new JButton("Skip to Piranha Phase");
        cheatButton_SkipToPiranha.setFocusable(false);
        cheatButton_SkipToPiranha.setEnabled(false); // remember go to PlayerInputEventQueue.nonRunningProcessInputEvents to set

        buttonPanel.add(cheatButton_SkipToPiranha);

        cheatButton_SkipToBoss = new JButton("Skip to Boss");
        cheatButton_SkipToBoss.setFocusable(false);
        cheatButton_SkipToBoss.setEnabled(false); // remember go to PlayerInputEventQueue.nonRunningProcessInputEvents to set

        buttonPanel.add(cheatButton_SkipToBoss);


        cp.add(BorderLayout.SOUTH, buttonPanel);


        // anonymous class
//        ButtonListener listenerb = new ButtonListener();
//        startButton.addActionListener(listenerb);
//        startButton.addActionListener(new ButtonListener());
//        startButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("button clicked");
//            }
//        });

        // lambda function
        quitButton.addActionListener(e -> {
//                System.out.println("button clicked")
//                    if (!Main.running) { // start button
//                        Main.running = true;
//                        quitButton.setText("Quit"); //change the label
//                        restartButton.setEnabled(true); //button on
//                    } else { // quit button
                        System.exit(0);
//                    }
                }
        );

        restartButton.addActionListener(e -> {
//                System.out.println("button clicked")

                    if (StarObserverAddNew.ttAddStar!= null) {
                        StarObserverAddNew.ttAddStar.cancel(); // cancel the ongoing timer task
                    }
                    if (EnemyObserverAddNew.ttAddPiranha != null) {
                        EnemyObserverAddNew.ttAddPiranha.cancel(); // cancel the ongoing timer task
                    }
                    if (Main.delayGenerateTimer != null) {
                        Main.delayGenerateTimer.cancel(); // cancel the timer
                        Main.delayGenerateTimer.purge(); // clear the cancelled tasks list
                        Main.delayGenerateTimer = new Timer();
                    }

                    PlayerInputEventQueue.queue.clear();  //clear all inputevent in the queue
                    PlayerInputEventQueue.queueEmptyOK = false; // IMPORTANT, AVOID CLEAR AT THE MOMENT SKIPPED QUEUE.ISEMPTY()

                    PlayerInputEventQueue.angleCount = 0; //reset angle
                    PlayerInputEventQueue.weaponForm = 0; //reset weapon to normal
                    Main.gameOver = false;


                    while (Main.collisionLooping == true) {
                        if (Main.collisionLooping == false) {
                            Main.calledGameOver = false; // WAIT UNTIL THE COLLISION LOOPING ENDS AND DO
                            break;
                        }
                    }
                    if (Main.collisionLooping == false) {
                        Main.calledGameOver = false; // WAIT UNTIL THE COLLISION LOOPING ENDS AND DO
                    }


                    EnemyObserverAddNew.EnemydiedCount = 0; // reset lob die numbers
                    piranhaMon.currentPiranhaMon1 = null;
                    piranhaMon.currentPiranhaMon2 = null;
                    Main.SkipLobPhase = false;
                    piranhaMon.callBigBoss = 0; // reset big boss call
//                    if (Main.running == true) { // cannot stop gameloop, the canvas will crash... and main will end

                    BossAppearWarning.WARNINGFlashingEnded = false; // THIS SHOULD BE SET TO TRUE AFTER BOSS APPEAR FINISH

                    // handle all the static var may have used and reset
                    dsDevil.DCNormalAttackCDOn = false;
                    if (dsDevil.ttDCNormalattack !=null) dsDevil.ttDCNormalattack.cancel();
                    dsDevil.DCNormalAttackCoolDown = 0;
                    dsDevil.DCNormalAttackCDOn = false;
                    dsDevil.DBComboAttackingOn = false;
                    dsDevil.DBComboAttackingEnded = false;
                    if (dsDevil.ttDBComboattack != null) {
                        dsDevil.ttDBComboattack.cancel(); // cancel the ongoing timer task
                    }
                    dsDevil.currenttentacle1 = null;
                    dsDevil.currenttentacle2 = null;
                    dsDevil.currenttentacle3 = null;
                    dsDevil.currenttentacle4 = null;
                    dsDevil.tentacle1AttackingOn = false;
                    dsDevil.tentacle2AttackingOn = false;
                    dsDevil.tentacle3AttackingOn = false;
                    dsDevil.tentacle4AttackingOn = false;
                    dsDevil.tentacle1AttackingEnded = false;
                    dsDevil.tentacle2AttackingEnded = false;
                    dsDevil.tentacle3AttackingEnded = false;
                    dsDevil.tentacle4AttackingEnded = false;
                    dsDevil.tentaclesAttackOn = false;
                    dsDevil.tentaclesAttackEnded = false;

                    dsDevil.posingTimeCount = 0;
                    dsDevil.AttackMode = 0;
                    dsDevil.lastAttackMode = 0;
                    dsDevil.comboModeCount = 0;

                    if (dsDevil.ttTentacle1attack != null) dsDevil.ttTentacle1attack.cancel(); // cancel the ongoing timer task
                    if (dsDevil.ttTentacle2attack != null) dsDevil.ttTentacle2attack.cancel(); // cancel the ongoing timer task
                    if (dsDevil.ttTentacle3attack != null) dsDevil.ttTentacle3attack.cancel(); // cancel the ongoing timer task
                    if (dsDevil.ttTentacle4attack != null) dsDevil.ttTentacle4attack.cancel(); // cancel the ongoing timer task
                    dsDevil.currentBoss = null;
                    dsDevil.posingFinished = false;

                    StarFish.starEnergyExisting = false;
                    StarFish.starLifeExisting = false;
                    StarFish.starPowerExisting = false;
                    StarFish.starSpeedExisting = false;
                    StarFish.starfishLifeCDOn = false;
                    StarFish.starfishLifeCoolDown = 0;
                    StarFish.starfishBuffCDOn = false;
                    StarFish.starfishBuffCoolDown = 0;
                    StarFish.starfishPowerEffectLastingCDOn = false;
                    StarFish.starfishPowerEffectLastingCoolDown = 0;
                    StarFish.starfishSpeedEffectLastingCDOn = false;
                    StarFish.starfishSpeedEffectLastingCoolDown = 0;

                    Congratulations.CongratulationsFlashingOn = false;

                    MousePointer.currentMousePointer = null;

                    PlayerInputEventQueue.angleCount = 0;
                    MyWindow.cheatButton_EnergyFull.setEnabled(false);
                    MyWindow.cheatButton_SkipToPiranha.setEnabled(false);
                    MyWindow.cheatButton_SkipToBoss.setEnabled(false);

                    restarting = true;
                    while (MyCanvas.canvaslooping){
                        looping++;                        // the looping hold for milliseconds, when it ends, Change data
                    }
                    while (Main.collisionLooping == true) {
                        if (Main.collisionLooping == false) {
                            gameData.clear(); // CHANGING THE DATA
                            break;
                        }
                    }
                    if (Main.collisionLooping == false) {
                        gameData.clear(); // CHANGING THE DATA
                    }

                    MyCanvas.g2BgColor = new Color(16,33,40);

                    Main.initGame(); // CHANGING THE DATA

            // add Star
                    InputEvent starevent = new InputEvent();
                    Random rand = new Random();
                    int x = rand.nextInt(1100)+50;
                    int y = -100;
//            starevent.event = new StarCreateEvent("starfishEnergy", x, y);
                    starevent.event = new StarCreateEvent("StarFish", x, y);
                    starevent.type = InputEvent.STAR_CREATE; // let InputEvent handle it...


                    StarObserverAddNew.ttAddStar = new TimerTask() {
                        @Override
                        public void run() {
                            Main.playerInputEventQueue.queue.add(starevent); // wait after delay time and add new
                        }
                    };
                    Main.delayGenerateTimer.schedule(StarObserverAddNew.ttAddStar,rand.nextInt(2500)+3000); //(2.5-4.5second -> 3.5-7.5second));

                    restarting = false;
//                    }
//                    else {
//                        Main.running = true;
//                        Main.initGame();
//                        Main.gameLoop();
//                        Main.endScreen_Clear();
//                    }

                }
        );


        pauseButton.addActionListener(e -> {
            if (Main.gamePaused == false) {
                Main.gamePaused = true;
                pauseButton.setText("Resume");
            }
            else {
                Main.gamePaused = false;
                pauseButton.setText("Pause");
            }
//                    }
                }
        );



        // cheat code buttons:
        cheatButton_EnergyFull.addActionListener(e -> {
                    var turtlehero = (turtleHero) Main.gameData.fixedObject.get(Main.INDEX_TURTLEHERO);
                    turtlehero.energyValue = 100;
//                    }
                }
        );

        cheatButton_SkipToPiranha.addActionListener(e -> {
                    if (StarObserverAddNew.ttAddStar != null) {
                        StarObserverAddNew.ttAddStar.cancel(); // cancel the ongoing timer task
                    }
                    if (EnemyObserverAddNew.ttAddPiranha != null) {
                        EnemyObserverAddNew.ttAddPiranha.cancel(); // cancel the ongoing timer task
                    }
                    if (Main.delayGenerateTimer != null) {
                        Main.delayGenerateTimer.cancel(); // cancel the timer
                        Main.delayGenerateTimer.purge(); // clear the cancelled tasks list
                        Main.delayGenerateTimer = new Timer();
                    }

                    PlayerInputEventQueue.queue.clear();  //clear all inputevent in the queue
                    PlayerInputEventQueue.queueEmptyOK = false; // IMPORTANT, AVOID CLEAR AT THE MOMENT SKIPPED QUEUE.ISEMPTY()

//                    PlayerInputEventQueue.angleCount = 0; //reset angle
//                    PlayerInputEventQueue.weaponForm = 0; //reset weapon to normal
                    Main.gameOver = false;

                    while (Main.collisionLooping == true) {
                        if (Main.collisionLooping == false) {
                            Main.calledGameOver = false; // WAIT UNTIL THE COLLISION LOOPING ENDS AND DO
                            break;
                        }
                    }
                    if (Main.collisionLooping == false) {
                        Main.calledGameOver = false; // WAIT UNTIL THE COLLISION LOOPING ENDS AND DO
                    }




//                    EnemyObserverAddNew.LobdiedCount = 0; // reset lob die numbers
                    piranhaMon.currentPiranhaMon1 = null;
                    piranhaMon.currentPiranhaMon2 = null;
                    piranhaMon.callBigBoss = 0; // reset big boss call
//                    if (Main.running == true) { // cannot stop gameloop, the canvas will crash... and main will end

                    BossAppearWarning.WARNINGFlashingEnded = false; // THIS SHOULD BE SET TO TRUE AFTER BOSS APPEAR FINISH

                    // handle all the static var may have used and reset
                    dsDevil.DCNormalAttackCDOn = false;
                    if (dsDevil.ttDCNormalattack !=null) dsDevil.ttDCNormalattack.cancel();
                    dsDevil.DCNormalAttackCoolDown = 0;
                    dsDevil.DCNormalAttackCDOn = false;
                    dsDevil.DBComboAttackingOn = false;
                    dsDevil.DBComboAttackingEnded = false;
                    if (dsDevil.ttDBComboattack != null) {
                        dsDevil.ttDBComboattack.cancel(); // cancel the ongoing timer task
                    }
                    dsDevil.currenttentacle1 = null;
                    dsDevil.currenttentacle2 = null;
                    dsDevil.currenttentacle3 = null;
                    dsDevil.currenttentacle4 = null;
                    dsDevil.tentacle1AttackingOn = false;
                    dsDevil.tentacle2AttackingOn = false;
                    dsDevil.tentacle3AttackingOn = false;
                    dsDevil.tentacle4AttackingOn = false;
                    dsDevil.tentacle1AttackingEnded = false;
                    dsDevil.tentacle2AttackingEnded = false;
                    dsDevil.tentacle3AttackingEnded = false;
                    dsDevil.tentacle4AttackingEnded = false;
                    dsDevil.tentaclesAttackOn = false;
                    dsDevil.tentaclesAttackEnded = false;

                    dsDevil.posingTimeCount = 0;
                    dsDevil.AttackMode = 0;
                    dsDevil.lastAttackMode = 0;
                    dsDevil.comboModeCount = 0;

                    if (dsDevil.ttTentacle1attack != null) dsDevil.ttTentacle1attack.cancel(); // cancel the ongoing timer task
                    if (dsDevil.ttTentacle2attack != null) dsDevil.ttTentacle2attack.cancel(); // cancel the ongoing timer task
                    if (dsDevil.ttTentacle3attack != null) dsDevil.ttTentacle3attack.cancel(); // cancel the ongoing timer task
                    if (dsDevil.ttTentacle4attack != null) dsDevil.ttTentacle4attack.cancel(); // cancel the ongoing timer task
                    dsDevil.currentBoss = null;
                    dsDevil.posingFinished = false;

                    StarFish.starEnergyExisting = false;
                    StarFish.starLifeExisting = false;
                    StarFish.starPowerExisting = false;
                    StarFish.starSpeedExisting = false;
                    StarFish.starfishLifeCDOn = false;
                    StarFish.starfishLifeCoolDown = 0;
                    StarFish.starfishBuffCDOn = false;
                    StarFish.starfishBuffCoolDown = 0;
                    StarFish.starfishPowerEffectLastingCDOn = false;
                    StarFish.starfishPowerEffectLastingCoolDown = 0;
                    StarFish.starfishSpeedEffectLastingCDOn = false;
                    StarFish.starfishSpeedEffectLastingCoolDown = 0;

                    Congratulations.CongratulationsFlashingOn = false;

                    MousePointer.currentMousePointer = null;

                    PlayerInputEventQueue.angleCount = 0;

                    MyWindow.cheatButton_EnergyFull.setEnabled(false);
                    MyWindow.cheatButton_SkipToPiranha.setEnabled(false);
                    MyWindow.cheatButton_SkipToBoss.setEnabled(false);

                    restarting = true;
                    while (MyCanvas.canvaslooping) {
                        looping++;                        // the looping hold for milliseconds, when it ends, Change data
                    }

                    while (Main.collisionLooping == true) {
                        if (Main.collisionLooping == false) {
                            gameData.clear(); // CHANGING THE DATA
                            break;
                        }
                    }
                    if (Main.collisionLooping == false) {
                        gameData.clear(); // CHANGING THE DATA
                    }

                    Main.SkipLobPhase = true;
                    EnemyObserverAddNew.EnemydiedCount = 3; // set lob die numbers to 3
                    EnemyObserverAddNew.callPiranhaMonOn = true; // start to call piranha monster

                    MyCanvas.g2BgColor = new Color(16,33,40);

                    Main.initGame(); // CHANGING THE DATA

                    // add Star
                    InputEvent starevent = new InputEvent();
                    Random rand = new Random();
                    int x = rand.nextInt(1100)+50;
                    int y = -100;
//            starevent.event = new StarCreateEvent("starfishEnergy", x, y);
                    starevent.event = new StarCreateEvent("StarFish", x, y);
                    starevent.type = InputEvent.STAR_CREATE; // let InputEvent handle it...


                    StarObserverAddNew.ttAddStar = new TimerTask() {
                        @Override
                        public void run() {
                            Main.playerInputEventQueue.queue.add(starevent); // wait after delay time and add new
                        }
                    };
                    Main.delayGenerateTimer.schedule(StarObserverAddNew.ttAddStar,rand.nextInt(2500)+3000); //(2.5-4.5second -> 3.5-7.5second));



//                    lobsterMon patheticlobM = new lobsterMon(600,-1000);
//                    gameData.enemyObject.add(patheticlobM);
//
//                    patheticlobM.hitCount++;
//                    patheticlobM.enemyHP -= 999;


                    restarting = false;
                }
        );

        cheatButton_SkipToBoss.addActionListener(e -> {
                    if (StarObserverAddNew.ttAddStar != null) {
                        StarObserverAddNew.ttAddStar.cancel(); // cancel the ongoing timer task
                    }
                    if (EnemyObserverAddNew.ttAddPiranha != null) {
                        EnemyObserverAddNew.ttAddPiranha.cancel(); // cancel the ongoing timer task
                    }
                    if (Main.delayGenerateTimer != null) {
                        Main.delayGenerateTimer.cancel(); // cancel the timer
                        Main.delayGenerateTimer.purge(); // clear the cancelled tasks list
                        Main.delayGenerateTimer = new Timer();
                    }

                    PlayerInputEventQueue.queue.clear();  //clear all inputevent in the queue
                    PlayerInputEventQueue.queueEmptyOK = false; // IMPORTANT, AVOID CLEAR AT THE MOMENT SKIPPED QUEUE.ISEMPTY()

//                    PlayerInputEventQueue.angleCount = 0; //reset angle
//                    PlayerInputEventQueue.weaponForm = 0; //reset weapon to normal
                    Main.gameOver = false;

                    while (Main.collisionLooping == true) {
                        if (Main.collisionLooping == false) {
                            Main.calledGameOver = false; // WAIT UNTIL THE COLLISION LOOPING ENDS AND DO
                            break;
                        }
                    }
                    if (Main.collisionLooping == false) {
                        Main.calledGameOver = false; // WAIT UNTIL THE COLLISION LOOPING ENDS AND DO
                    }




//                    EnemyObserverAddNew.LobdiedCount = 0; // reset lob die numbers
                    piranhaMon.currentPiranhaMon1 = null;
                    piranhaMon.currentPiranhaMon2 = null;
                    piranhaMon.callBigBoss = 0; // reset big boss call
//                    if (Main.running == true) { // cannot stop gameloop, the canvas will crash... and main will end

                    BossAppearWarning.WARNINGFlashingEnded = false; // THIS SHOULD BE SET TO TRUE AFTER BOSS APPEAR FINISH

                    // handle all the static var may have used and reset
                    dsDevil.DCNormalAttackCDOn = false;
                    if (dsDevil.ttDCNormalattack !=null) dsDevil.ttDCNormalattack.cancel();
                    dsDevil.DCNormalAttackCoolDown = 0;
                    dsDevil.DCNormalAttackCDOn = false;

                    dsDevil.DBComboAttackingOn = false;
                    dsDevil.DBComboAttackingEnded = false;
                    if (dsDevil.ttDBComboattack != null) {
                        dsDevil.ttDBComboattack.cancel(); // cancel the ongoing timer task
                    }
                    dsDevil.currenttentacle1 = null;
                    dsDevil.currenttentacle2 = null;
                    dsDevil.currenttentacle3 = null;
                    dsDevil.currenttentacle4 = null;
                    dsDevil.tentacle1AttackingOn = false;
                    dsDevil.tentacle2AttackingOn = false;
                    dsDevil.tentacle3AttackingOn = false;
                    dsDevil.tentacle4AttackingOn = false;
                    dsDevil.tentacle1AttackingEnded = false;
                    dsDevil.tentacle2AttackingEnded = false;
                    dsDevil.tentacle3AttackingEnded = false;
                    dsDevil.tentacle4AttackingEnded = false;
                    dsDevil.tentaclesAttackOn = false;
                    dsDevil.tentaclesAttackEnded = false;

                    dsDevil.posingTimeCount = 0;
                    dsDevil.AttackMode = 0;
                    dsDevil.lastAttackMode = 0;
                    dsDevil.comboModeCount = 0;

                    if (dsDevil.ttTentacle1attack != null) dsDevil.ttTentacle1attack.cancel(); // cancel the ongoing timer task
                    if (dsDevil.ttTentacle2attack != null) dsDevil.ttTentacle2attack.cancel(); // cancel the ongoing timer task
                    if (dsDevil.ttTentacle3attack != null) dsDevil.ttTentacle3attack.cancel(); // cancel the ongoing timer task
                    if (dsDevil.ttTentacle4attack != null) dsDevil.ttTentacle4attack.cancel(); // cancel the ongoing timer task
                    dsDevil.currentBoss = null;
                    dsDevil.posingFinished = false;

                    StarFish.starEnergyExisting = false;
                    StarFish.starLifeExisting = false;
                    StarFish.starPowerExisting = false;
                    StarFish.starSpeedExisting = false;
                    StarFish.starfishLifeCDOn = false;
                    StarFish.starfishLifeCoolDown = 0;
                    StarFish.starfishBuffCDOn = false;
                    StarFish.starfishBuffCoolDown = 0;
                    StarFish.starfishPowerEffectLastingCDOn = false;
                    StarFish.starfishPowerEffectLastingCoolDown = 0;
                    StarFish.starfishSpeedEffectLastingCDOn = false;
                    StarFish.starfishSpeedEffectLastingCoolDown = 0;

                    MousePointer.currentMousePointer = null;
                    PlayerInputEventQueue.angleCount = 0;

                    Congratulations.CongratulationsFlashingOn = false;

                    MyWindow.cheatButton_EnergyFull.setEnabled(false);
                    MyWindow.cheatButton_SkipToPiranha.setEnabled(false);
                    MyWindow.cheatButton_SkipToBoss.setEnabled(false);

                    restarting = true;
                    while (MyCanvas.canvaslooping) {
                        looping++;                        // the looping hold for milliseconds, when it ends, Change data
                    }

                    while (Main.collisionLooping == true) {
                        if (Main.collisionLooping == false) {
                            gameData.clear(); // CHANGING THE DATA
                            break;
                        }
                    }
                    if (Main.collisionLooping == false) {
                        gameData.clear(); // CHANGING THE DATA
                    }

                    Main.SkipLobPhase = true;
                    EnemyObserverAddNew.callPiranhaMonOn = false; // start to call piranha monster
                    EnemyObserverAddNew.EnemydiedCount = 5; // set lob die numbers to 3

                    MyCanvas.g2BgColor = new Color(16,33,40);


                    Main.initGame(); // CHANGING THE DATA

                    // add Star
                    InputEvent starevent = new InputEvent();
                    Random rand = new Random();
                    int x = rand.nextInt(1100)+50;
                    int y = -100;
//            starevent.event = new StarCreateEvent("starfishEnergy", x, y);
                    starevent.event = new StarCreateEvent("StarFish", x, y);
                    starevent.type = InputEvent.STAR_CREATE; // let InputEvent handle it...


                    StarObserverAddNew.ttAddStar = new TimerTask() {
                        @Override
                        public void run() {
                            Main.playerInputEventQueue.queue.add(starevent); // wait after delay time and add new
                        }
                    };
                    Main.delayGenerateTimer.schedule(StarObserverAddNew.ttAddStar,rand.nextInt(2500)+3000); //(2.5-4.5second -> 3.5-7.5second));




//                    lobsterMon patheticlobM = new lobsterMon(600,-1000);
//                    gameData.enemyObject.add(patheticlobM);
//
//                    patheticlobM.hitCount++;
//                    patheticlobM.enemyHP -= 999;


                    restarting = false;
                }
        );

    }

//    class ButtonListener implements ActionListener {
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            System.out.println("button clicked");
//        }
//    }

}
