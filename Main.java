package sample;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;

public class Main extends Canvas {

    boolean isMoving = false;

    int fade = 0;

    boolean fadeUp = false;
    boolean fadeDown = false;

    File file = new File("Air Music.wav");
    Clip clip = AudioSystem.getClip();
    AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);

    File file2 = new File("Boss Music.wav");
    Clip clip2 = AudioSystem.getClip();
    AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(file2);

    boolean canJump = false;

    Point[] respawns = {new Point(-250, 900), new Point(500, 700), new Point(-250, 400), new Point(-200, 1810), new Point(880, 900), new Point(880, 300), new Point(1800, 50), new Point(1200, -500), new Point(1500, -500), new Point(2000, -500)};

    int HP = 3;

    int fireX = 0;
    int fireX2 = 0;
    int fireX3 = 0;
    int fireX4 = 0;
    int fireX5 = 0;

    int fireY = 0;

    int planeX = 2000;
    int planeX2 = 0;

    float speed = 20;
    float gravity = 20;

    float x = -250;
    float y = 900;

    ImageIcon levelsIcoIco = new ImageIcon("Level Icon.png");
    Image levelsIco = levelsIcoIco.getImage();

    ImageIcon backgroundIco = new ImageIcon("Background.png");
    Image background = backgroundIco.getImage();

    ImageIcon flagIco = new ImageIcon("Flag.png");
    Image flag = flagIco.getImage();

    ImageIcon airshipIco = new ImageIcon("Airship.png");
    Image airship = airshipIco.getImage();

    ImageIcon healthIco = new ImageIcon("HP.png");
    Image health = healthIco.getImage();

    ImageIcon creditsIcoIco = new ImageIcon("Credits.png");
    Image creditsIco = creditsIcoIco.getImage();

    ImageIcon arrowIco = new ImageIcon("Go This Way.png");
    Image arrow = arrowIco.getImage();

    ImageIcon planeRightIco = new ImageIcon("Plane Right.png");
    Image planeRight = planeRightIco.getImage();

    ImageIcon planeLeftIco = new ImageIcon("Plane Left.png");
    Image planeLeft = planeLeftIco.getImage();

    ImageIcon grasslandIco = new ImageIcon("Grassland.png");
    Image grassland = grasslandIco.getImage();

    ImageIcon skyIco = new ImageIcon("Sky.png");
    Image sky = skyIco.getImage();

    ImageIcon grassIco = new ImageIcon("Grass.png");
    Image grass = grassIco.getImage();

    int level = 0;

    float playerAngle = 0;
    float pi = 3.14159f;

    boolean jump = false;
    boolean left = false;
    boolean right = false;

    JFrame frame = new JFrame("Red Circle 2.0");
    JFrame frame2 = new JFrame("Credits");

    JButton levels = new JButton();
    JButton credits = new JButton();

    JTextArea displayCredits = new JTextArea();

    ActionListener AL = e -> {

        JButton button = (JButton) e.getSource();

        if (button == levels) {
            level = 1;
            clip.start();
            clip.setMicrosecondPosition(7000000);
            respawn();
            HP = 3;
        } else if (button == credits) {
            frame2.setVisible(true);
        }

    };

    KeyAdapter KL = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {

            char key = e.getKeyChar();

            if (canJump) {
                if (key == 'w') {
                    jump = true;
                }
            } if (key == 'a') {
                left = true;
            } if (key == 'd') {
                right = true;
            } if (key == 0x1b) {
                level = 0;
                clip.stop();
                clip.setMicrosecondPosition(0);
                frame2.setVisible(false);
                HP = 3;
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {

            char key = e.getKeyChar();

            if (key == 'a') {
                    left = false;
            } if (key == 'd') {
                right = false;
            }

        }
    };

    int jumpCounter = 10;


    Main() throws LineUnavailableException, UnsupportedAudioFileException, IOException {

        clip.open(audioStream);
        clip2.open(audioStream2);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(700, 700);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.getContentPane().setBackground(Color.black);
        frame.addKeyListener(KL);
        this.addKeyListener(KL);
        frame.requestFocus();

        frame2.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame2.setLocationRelativeTo(null);
        frame2.setSize(700, 700);
        frame2.setResizable(false);
        frame2.setVisible(false);
        frame2.setFocusable(true);
        frame2.getContentPane().setBackground(Color.black);
        frame2.addKeyListener(KL);
        frame2.requestFocus();

        levels.setFont(new Font("Arial", Font.BOLD, 100));
        levels.setForeground(Color.white);
        levels.addActionListener(AL);
        levels.setContentAreaFilled(false);
        levels.setBounds(10, 10, 670, 200);
        levels.setBackground(Color.black);
        levels.setBorder(new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.setColor(Color.white);
                g.drawRoundRect(x, y, width - 1, height - 1, 50, 50);
                g.drawImage(levelsIco, x + 10, y + 10, width - 10, height - 10, null);
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(100, 100, 100, 100);
            }

            @Override
            public boolean isBorderOpaque() {
                return true;
            }
        });

        frame.add(levels);

        credits.setFont(new Font("Arial", Font.BOLD, 100));
        credits.setForeground(Color.white);
        credits.addActionListener(AL);
        credits.setContentAreaFilled(false);
        credits.setBounds(10, 10, 670, 200);
        credits.setBackground(Color.black);
        credits.setBorder(new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.setColor(Color.white);
                g.drawRoundRect(x, y, width - 1, height - 1, 50, 50);
                g.drawImage(creditsIco, x + 10, y + 10, width - 10, height - 10, null);
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(100, 100, 100, 100);
            }

            @Override
            public boolean isBorderOpaque() {
                return true;
            }
        });

        frame.add(credits);

        displayCredits.setFont(new Font("Monospaced", Font.PLAIN, 15));
        displayCredits.setBackground(Color.black);
        displayCredits.setForeground(Color.white);
        displayCredits.setEditable(false);
        displayCredits.setHighlighter(null);
        displayCredits.setText("Me");
        frame2.add(displayCredits);

        frame.add(this);
        tick.start();

    }

    boolean overide = false;

    Timer tick = new Timer(1000 / 24, e -> {

        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        if (clip.getMicrosecondPosition() >= clip.getMicrosecondLength()) {
            clip.setMicrosecondPosition(-1000000);
            clip.start();
        }

        g.setColor(Color.black);
        g.fillRect(0, 0, 700, 700);

        g.drawImage(background, -600 - ((int) x / 4), -600 - ((int) y / 4), 2300, 2200, null);

        if (HP <= 0) {
            level = 0;
            HP = 3;
            clip.stop();
            clip2.stop();
            clip.setMicrosecondPosition(0);
            clip2.setMicrosecondPosition(0);
        }

        if (y > 2500) {
            respawn();
        }

        if (level != 0) {

                if (!overide && !jump) {
                    y += gravity;
                }

            if (jump && level != 10 && level != 20 && level != 30 && level != 40 && level != 50) {
                if (jumpCounter > 0) {
                    y -= gravity;
                    jumpCounter--;
                } else {
                    jump = false;
                    jumpCounter = 10;
                }
            }
            if (left) {
                x -= speed;
                if (canJump)
                    playerAngle += speed / pi + 15;
            }
            if (right) {
                x += speed;
                if (canJump)
                    playerAngle -= speed / pi + 15;
            }
        }

        if (level == -1) {
            g.clearRect(0, 0, 700, 700);
        }

        if (level != 0) {

            levels.setBounds(0, 0, 0, 0);
            levels.setBorder(null);

            credits.setBounds(0, 0, 0, 0);
            credits.setBorder(null);

            g.setColor(Color.red);
            g.fillArc(frame.getWidth() / 2 - 20, frame.getHeight() / 2 - 15, 40, 40, (int) playerAngle, 90);
            g.fillArc(frame.getWidth() / 2 - 20, frame.getHeight() / 2 - 15, 40, 40, (int) playerAngle + 180, 90);

            g.setColor(Color.white);
            g.fillArc(frame.getWidth() / 2 - 20, frame.getHeight() / 2 - 15, 40, 40, (int) playerAngle + 90, 90);
            g.fillArc(frame.getWidth() / 2 - 20, frame.getHeight() / 2 - 15, 40, 40, (int) playerAngle + 270, 90);

            for (int i = 0; i < HP; i++) {
                g.drawImage(health, 700 - (i * 25) - 40, 10, 20, 20, null);
            }

        } else {
            levels.setLocation(10, 10);
            levels.setSize(670, 200);
            credits.setLocation(10, 220);
            credits.setSize(670, 200);
            levels.setSize(670, 200);
            g.clearRect(0, 0, 700, 700);
            levels.setBorder(new Border() {
                @Override
                public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                    g.setColor(Color.white);
                    g.drawRoundRect(x, y, width - 1, height - 1, 50, 50);
                    g.drawImage(levelsIco, x + 10, y + 10, width - 10, height - 10, null);
                }

                @Override
                public Insets getBorderInsets(Component c) {
                    return new Insets(100, 100, 100, 100);
                }

                @Override
                public boolean isBorderOpaque() {
                    return true;
                }
            });

            credits.setBorder(new Border() {
                @Override
                public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                    g.setColor(Color.white);
                    g.drawRoundRect(x, y, width - 1, height - 1, 50, 50);
                    g.drawImage(creditsIco, x + 10, y + 10, width - 10, height - 10, null);
                }

                @Override
                public Insets getBorderInsets(Component c) {
                    return new Insets(100, 100, 100, 100);
                }

                @Override
                public boolean isBorderOpaque() {
                    return true;
                }
            });
        }

        if (level == 1) {

            drawPlatform(calcX(0), calcY(1310), 200, 20, g);
            drawPlatform(calcX(400), calcY(1210), 200, 20, g);
            drawPlatform(calcX(800), calcY(1110), 200, 20, g);
            drawPlatform(calcX(1200), calcY(1110), 200, 20, g);

            g.drawImage(flag, calcX(1260), calcY(1010), 100, 100, null);

            if (over(calcX(1260), calcY(1010), 100, 130)) {
                level = 2;
                HP++;
                overide = false;
            }

            if (over(calcX(0), calcY(1310), 200, 20) || over(calcX(400), calcY(1210), 200, 20)
                    || over(calcX(800), calcY(1110), 200, 20) || over(calcX(1200), calcY(1110), 200, 20)) {
                overide = true;
                canJump = true;
            } else {
                overide = false;
                canJump = false;
            }

        } else if (level == 2) {

            drawPlatform(calcX(0), calcY(800), 200, 20, g);
            drawPlatform(calcX(350), calcY(990), 300, 20, g);
            drawPlatform(calcX(800), calcY(1090), 200, 20, g);
            drawPlatform(calcX(1200), calcY(1110), 200, 20, g);

            if (over(calcX(0), calcY(800), 200, 20) || over(calcX(350), calcY(990), 300, 20)
                    || over(calcX(800), calcY(1090), 200, 20) || over(calcX(1200), calcY(1110), 200, 20)) {
                overide = true;
                canJump = true;
            } else {
                overide = false;
                canJump = false;
            }

            g.setColor(Color.red);
            g.fillRect(calcX(490), calcY(890), 20, 100);

            if (over(calcX(490), calcY(890), 20, 100)) {
                respawn();
            }

            g.drawImage(flag, calcX(0), calcY(700), 100, 100, null);

            if (over(calcX(0), calcY(800), 100, 130)) {
                level = 3;
                HP++;
                overide = false;
            }

        } else if (level == 3) {

            drawPlatform(calcX(0), calcY(820), 200, 20, g);
            if (over(calcX(0), calcY(820), 200, 20) || over(calcX(0), calcY(2240), 400, 20)) {
                overide = true;
                canJump = true;
            } else {
                overide = false;
                canJump = false;
            }

            g.setColor(Color.red);

            g.fillRect(calcX(50), calcY(890), 30, 30);
            g.fillRect(calcX(100), calcY(1210), 30, 30);
            g.fillRect(calcX(150), calcY(1490), 30, 30);
            g.fillRect(calcX(-150), calcY(1980), 200, 30);
            g.fillRect(calcX(250), calcY(1980), 200, 30);

            if (over(calcX(50), calcY(890), 30, 30) || over(calcX(100), calcY(1210), 30, 30)
            || over(calcX(150), calcY(1390), 30, 30) || over(calcX(-150), calcY(1980), 200, 30)
            || over(calcX(250), calcY(1980), 200, 30)) {
                respawn();
            }

            drawPlatform(calcX(0), calcY(2240), 400, 20, g);

            g.drawImage(flag, calcX(200), calcY(2140), 100, 100, null);
            if (over(calcX(200), calcY(2140), 100, 130)) {
                level = 4;
                overide = false;
                canJump = false;
                HP++;
            }

        } else if (level == 4) {

            drawPlatform(calcX(100), calcY(2290), 200, 20, g);
            drawPlatform(calcX(450), calcY(2090), 200, 20, g);
            drawPlatform(calcX(100), calcY(1890), 200, 20, g);
            drawPlatform(calcX(450), calcY(1690), 200, 20, g);
            drawPlatform(calcX(100), calcY(1490), 200, 20, g);
            drawPlatform(calcX(450), calcY(1290), 200, 20, g);
            drawPlatform(calcX(1050), calcY(1290), 200, 20, g);

            g.drawImage(arrow, calcX(450), calcY(900), 500, 200, null);

            if (over(calcX(100), calcY(2290), 200, 20) || over(calcX(450), calcY(2090), 200, 20)
            || over (calcX(100), calcY(1890), 200, 20) || over(calcX(450), calcY(1690), 200, 20)
            || over(calcX(100), calcY(1490), 200, 20) || over(calcX(450), calcY(1290), 200, 20)
            || over(calcX(1050), calcY(1290), 200, 20)) {
                overide = true;
                canJump = true;
            } else {
                overide = false;
                canJump = false;
            }

            g.drawImage(flag, calcX(1150), calcY(1190), 100, 100, null);
            if (over(calcX(1150), calcY(1190), 100, 130)) {
                level = 5;
                overide = false;
                canJump = false;
                HP++;
            }

        } else if (level == 5) {

            drawPlatform(calcX(1060), calcY(1350), 200, 20, g);
            drawPlatform(calcX(1460), calcY(1200), 200, 20, g);
            drawPlatform(calcX(1060), calcY(1000), 200, 20, g);

            g.drawImage(flag, calcX(1160), calcY(900), 100, 100, null);

            if (over(calcX(1060), calcY(1350), 200, 20) || over(calcX(1460), calcY(1200), 200, 20)
            || over(calcX(1060), calcY(1000), 200, 20)) {
                overide = true;
                canJump = true;
            } else {
                overide = false;
                canJump = false;
            }

            if (over(calcX(1160), calcY(900), 100, 130)) {
                level++;
                HP++;
                canJump = false;
                overide = false;
            }

            if (over(calcX(planeX), calcY(1100), 100, 40)) {
                respawn();
            }

            g.drawImage(planeLeft, calcX(planeX), calcY(1100), 100, 40, null);

            if (planeX < 900) {
                planeX = 2000;
            } else {
                planeX-=20;
            }

        } else if (level == 6) {

            drawPlatform(calcX(1060), calcY(1050), 200, 20, g);

            drawPlatform(calcX(760), calcY(1050), 200, 20, g);
            drawPlatform(calcX(460), calcY(1050), 200, 20, g);
            drawPlatform(calcX(160), calcY(1050), 200, 20, g);
            drawPlatform(calcX(-160), calcY(1050), 200, 20, g);
            drawPlatform(calcX(-460), calcY(1050), 200, 20, g);
            drawPlatform(calcX(-760), calcY(1050), 200, 20, g);

            g.setColor(Color.black);
            g.drawLine(calcX(-760), calcY(1050), calcX(2100), calcY(400));
            g.fillRect(calcX(-765), calcY(1035), 30, 30);

            if (over(calcX(-765), calcY(1035), 30, 30)) {
                isMoving = true;
                gravity = 0;
                speed = 0;
            } if (over(calcX(2100), calcY(420), 200, 20)) {
                isMoving = false;
                speed = 20;
                gravity = 20;
            }if (isMoving) {
                goTo(1900, 15);
            }

            drawPlatform(calcX(2100), calcY(420), 200, 20, g);

            g.drawImage(flag, calcX(2200), calcY(350), 100, 100, null);

            if (over(calcX(1060), calcY(1050), 200, 20) || over(calcX(760), calcY(1050), 200, 20)
            || over(calcX(460), calcY(1050), 200, 20) || over(calcX(160), calcY(1050), 200, 20)
            || over(calcX(-160), calcY(1050), 200, 20) || over(calcX(-460), calcY(1050), 200, 20)
            || over(calcX(-760), calcY(1050), 200, 20) || over(calcX(2100), calcY(420), 200, 20)) {
                overide = true;
                canJump = true;
            } else {
                overide = false;
                canJump = false;
            }

            if (over(calcX(2200), calcY(350), 100, 130)) {
                level = 7;
                HP++;
                planeX = 3000;
                planeX2 = 3500;
            }

        } else if (level == 7) {

            if (planeX < 1000) {
                planeX = 3000;
            } else {
                planeX -= 40;
            } if (planeX2 < 1000) {
                planeX2 = 3500;
            } else {
                planeX2 -= 40;
            }

            g.drawImage(planeLeft, calcX(planeX), calcY(325), 100, 40, null);
            g.drawImage(planeLeft, calcX(planeX2), calcY(125), 100, 40, null);

            if (over(calcX(planeX), calcY(325), 100, 40) || over(calcX(planeX2), calcY(125), 100, 40)) {
                respawn();
            }

            drawPlatform(calcX(2100), calcY(455), 200, 20, g);
            drawPlatform(calcX(1750), calcY(250), 200, 20, g);
            drawPlatform(calcX(1400), calcY(50), 200, 20, g);

            if (over(calcX(2100), calcY(455), 200, 20) || over(calcX(1750), calcY(250), 200, 20)
            || over(calcX(1400), calcY(50), 200, 20)) {
                overide = true;
                canJump = true;
            } else {
                overide = false;
                canJump = false;
            }

            g.drawImage(flag, calcX(1500), calcY(-50), 100, 100, null);

            if (over(calcX(1500), calcY(-50), 100, 100)) {
                level = 8;
                HP++;
            }

        } else if (level == 8) {
            drawPlatform(calcX(1400), calcY(50), 200, 20, g);
            drawPlatform(calcX(1850), calcY(50), 200, 20, g);

            if (over(calcX(1400), calcY(50), 200, 20) || over(calcX(1850), calcY(50), 200, 20)) {
                canJump = true;
                overide = true;
            } else {
                canJump = false;
                overide = false;
            }

            g.drawImage(flag, calcX(1950), calcY(-50), 100, 100, null);

            if (over(calcX(1950), calcY(-50), 100, 130)) {
                level = 9;
            }

        } else if (level == 9) {

            drawPlatform(calcX(1860), calcY(50), 200, 20, g);
            drawPlatform(calcX(2350), calcY(50), 200, 20, g);

            if (over(calcX(1860), calcY(50), 200, 20) || over(calcX(2350), calcY(50), 200, 20)) {
                canJump = true;
                overide = true;
            } else {
                canJump = false;
                overide = false;
            }

            g.drawImage(flag, calcX(2450), calcY(-50), 100, 100, null);
            if (over(calcX(2450), calcY(-50), 100, 130)) {
                level = 10;
                canJump = true;
                jumpCounter = 0;
                gravity = 0;
                clip2.start();
                clip.stop();
            }

        } if (level == 10) {

            if (fireY > 700) {
                fireY = 0;
                fireX = (int) (Math.random() * 680 + 2100);
                fireX2 = (int) (Math.random() * 680 + 2100);
                fireX3 = (int) (Math.random() * 680 + 2100);
                fireX4 = (int) (Math.random() * 680 + 2100);
                fireX5 = (int) (Math.random() * 680 + 2100);
            } else {
                fireY += 20;
            }

            g.setColor(Color.red);

            g.fillRoundRect(calcX(fireX), fireY, 50, 50, 10, 10);
            g.fillRoundRect(calcX(fireX2), fireY, 50, 50, 10, 10);
            g.fillRoundRect(calcX(fireX3), fireY, 50, 50, 10, 10);
            g.fillRoundRect(calcX(fireX4), fireY, 50, 50, 10, 10);
            g.fillRoundRect(calcX(fireX5), fireY, 50, 50, 10, 10);

            if (over(calcX(fireX), fireY, 50, 50) || over(calcX(fireX2), fireY, 50, 50)
            || over(calcX(fireX3), fireY, 50, 50) || over(calcX(fireX4), fireY, 50, 50)
            || over(calcX(fireX5), fireY, 50, 50)) {
                respawn();
            }

            y -= 20;

            g.setColor(new Color(0, 0, 0, 127));
            g.fillRoundRect(calcX(2100) - 5, 380, 700, 20, 10, 10);
            g.setColor(Color.white);
            g.fillRoundRect(calcX(2100), 375, 700, 20, 10, 10);

            g.setColor(new Color(200, 0, 0));
            g.fillRect(calcX(1700), 0, 400, 700);
            g.fillRect(calcX(2790), 0, 400, 700);

            if (over(calcX(1700), 0, 400, 700) || over(calcX(2790), 0, 400, 700)) {
                respawn();
                y = -500;
            }

            g.setColor(Color.green);
            g.fillRect(calcX(2100), calcY(-5000), 700, 50);

            if (over(calcX(2100), calcY(-5000), 700, 50)) {
                level = -2;
                canJump = false;
                overide = false;
                gravity = 20;
                clip2.stop();
            }


        } else if (level == 11) {

            g.drawImage(grass, calcX(100), calcY(300), 100, 100, null);
            g.drawImage(grass, calcX(200), calcY(300), 100, 100, null);
            g.drawImage(grass, calcX(300), calcY(300), 100, 100, null);
            g.drawImage(grass, calcX(400), calcY(300), 100, 100, null);

            if (over(calcX(100), calcY(300), 100, 100) || over(calcX(200), calcY(300), 100, 100)
            || over(calcX(300), calcY(300), 100, 100) || over(calcX(400), calcY(300), 100, 100)) {
                overide = false;
                canJump = false;
            } else {
                overide = false;
                canJump = false;
            }

        }

        if (level == -2) {

            if (fade == 1 && fadeDown) {
                level = 11;
                gravity = 20;
                canJump = false;
                overide = false;
                x = 300;
                y = -200;
                background = sky;
            }

            if (fade == 0) {
                fadeUp = true;
                fadeDown = false;
            } if (fade == 255) {
                fadeDown = true;
                fadeUp = false;
                background = grassland;
            }

            gravity = 0;
            canJump = false;

            g.drawImage(background, 0, 0, 700, 700, null);

            if (fadeUp) {
                fade++;
            } else {
                fade--;
            }

            g.setColor(new Color(0, 0, 0, fade));
            g.fillRect(0, 0, 700, 700);

        }

        bs.show();
        g.dispose();

    });

    void goTo(float x, float y) {

        float difX = x - this.x;
        float difY = y - this.y;

        float differance = (float) Math.sqrt((difX * difX) + (difY * difY));

        float velX = difX / differance * 20;
        float velY = difY / differance * 20;

        this.x += velX;
        this.y += velY;

    }

    void respawn() {
        jumpCounter = 10;
        x = (float) respawns[level - 1].getX();
        y = (float) respawns[level - 1].getY();
        jump = false;
        HP--;
    }

    int calcX(int X) {
        return (int) (X - this.x);
    }

    int calcY(int Y) {
        return (int) (Y - this.y);
    }

    boolean over(int X, int Y, int width, int height) {

        Rectangle box = new Rectangle(X, Y, width, height);
        Rectangle player = new Rectangle(frame.getWidth() / 2 - 20, (frame.getHeight() / 2 - 15) + 35, 40, 5);

        return box.intersects(player);

    }

    void drawPlatform(int X, int Y, int width, int height, Graphics g) {

        g.setColor(new Color(0, 0, 0, 127));
        g.fillRoundRect(X - 5, Y + 5, width, height, 10, 10);
        g.setColor(Color.white);
        g.fillRoundRect(X, Y, width, height, 10, 10);
        g.setColor(Color.black);
        g.drawLine(X + 20, Y, X + 20, Y - (int) (width * 0.75) + 50);
        g.drawLine(X + width - 20, Y, X + width - 20, Y - (int) (width * 0.75) + 50);
        int imgHeight = (width + 20) / 10 * 7;
        g.drawImage(airship, X - 20, (int) (Y - width * 0.75), width + 20, imgHeight, null);

    }

    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        new Main();
    }

}
