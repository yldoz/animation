package com.crazypumpkin.animation;


import javax.swing.JPanel;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;

import static com.crazypumpkin.animation.GeomUtils.totalDistance;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class Board extends JPanel {

    private final int B_WIDTH = 382;
    private final int B_HEIGHT = 722;

    private final List<AnimatedCircle> circles;

    public Board() {
        circles = new CopyOnWriteArrayList<>();
        initBoard();
    }

    private void initBoard() {
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        var points = List.of(
            new Point(B_WIDTH / 2, 50),
            new Point(B_WIDTH / 2, 429),
            new Point(310, 429));

        var maxSpeed = 5;
        var amount = 6;
        var interval = 15;

        var dist = totalDistance(points);
        var appearTime = (long) dist * interval / maxSpeed / amount;
        if (appearTime <= 0) {
            appearTime = 1;
        }

        var creator = Executors.newScheduledThreadPool(1);
        creator.scheduleAtFixedRate(() -> {
            circles.add(new AnimatedCircle(points, maxSpeed));
            if (circles.size() == amount) {
                creator.shutdown();
            }
        }, 0, appearTime, MILLISECONDS);

        var drawer = Executors.newScheduledThreadPool(1);
        drawer.scheduleAtFixedRate(() -> {
            circles.forEach(AnimatedCircle::move);
            repaint();
        }, 0, interval, MILLISECONDS);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        var graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        var gp = new GradientPaint(0, 0, new Color(10, 44, 88), 0, B_HEIGHT, new Color(8, 33, 68));
        graphics.setPaint(gp);
        graphics.fillRect(0, 0, B_WIDTH, B_HEIGHT);

        circles.forEach(circle -> drawCircle(circle, graphics));

        var dBlue = 300;
        var dGreen = 300;
        drawArc((B_WIDTH - dBlue) / 2,  (int) (-dBlue / 1.12), dBlue, graphics, new Color(90, 251, 255));
        drawArc(B_WIDTH - (int) (dGreen / 4.5),  430 - dGreen / 2, dGreen, graphics, new Color(90, 250, 63));
    }

    private void drawArc(int x, int y, int d, Graphics2D graphics, Color color) {
        graphics.setStroke(new BasicStroke(8));
        graphics.setColor(color);
        graphics.drawArc(x, y, d, d, 0, 360);
    }

    private void drawCircle(AnimatedCircle circle, Graphics2D graphics) {
        graphics.setStroke(new BasicStroke(1));
        graphics.setColor(new Color(59, 173, 189));

        var CIRCLE_RADIUS = 12;
        graphics.fillOval(circle.getX() - CIRCLE_RADIUS, circle.getY() - CIRCLE_RADIUS,
            CIRCLE_RADIUS * 2, CIRCLE_RADIUS * 2);
    }
}