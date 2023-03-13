package com.crazypumpkin.animation;

import java.util.List;

public class AnimatedCircle {

    private int x;
    private int y;
    private final int maxSpeed;
    private final List<Point> controlPoints;
    private int currentControlPointIndex;

    public AnimatedCircle(List<Point> controlPoints, int maxSpeed) {
        this.x = controlPoints.get(0).getX();
        this.y = controlPoints.get(0).getY();
        this.controlPoints = controlPoints;
        this.currentControlPointIndex = 0;
        this.maxSpeed = maxSpeed;
    }

    public void move() {
        checkCycle();
        var controlPoint = controlPoints.get(currentControlPointIndex);
        var xDistance = controlPoint.getX() - x;
        var yDistance = controlPoint.getY() - y;
        var distance = Math.hypot(xDistance, yDistance);
        if (distance > maxSpeed) {
            xDistance *= maxSpeed / distance;
            yDistance *= maxSpeed / distance;
        }

        var distanceSum = Math.abs(xDistance) + Math.abs(yDistance);
        if (distanceSum == 0) {
            collide();
            return;
        }

        var xSpeed = (int) ((double) xDistance / distanceSum * maxSpeed);
        var ySpeed = (int) ((double) yDistance / distanceSum * maxSpeed);

        x += xSpeed;
        y += ySpeed;

        if (checkCollision(controlPoint, x - xSpeed, y - ySpeed)) {
            x = controlPoint.getX();
            y = controlPoint.getY();
            collide();
        }
    }

    private boolean checkCollision(Point point, int prevX, int prevY) {
        return (prevX == point.getX() || ((point.getX() - x) ^ (point.getX()) - prevX) < 0) &&
            (prevY == point.getY() || ((point.getY() - y) ^ (point.getY()) - prevY) < 0);
    }

    private void collide() {
        currentControlPointIndex++;
    }

    private void checkCycle() {
        if (currentControlPointIndex == controlPoints.size()) {
            currentControlPointIndex = 0;
            x = controlPoints.get(0).getX();
            y = controlPoints.get(0).getY();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
