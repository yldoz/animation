package com.crazypumpkin.animation;

import java.util.List;

public final class GeomUtils {

    public static double totalDistance(List<Point> points) {
        var prevPoint = points.get(0);
        var dist = 0.0;
        for (var i = 1; i < points.size(); i++) {
            dist += Math.hypot(
                prevPoint.getX() - points.get(i).getX(),
                prevPoint.getY() - points.get(i).getY()
            );
            prevPoint = points.get(i);
        }
        return dist;
    }
}
