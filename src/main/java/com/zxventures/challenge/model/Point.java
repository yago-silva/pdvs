package com.zxventures.challenge.model;

import java.math.BigDecimal;

public class Point {

    private BigDecimal x;

    private BigDecimal y;

    /*
    * Here just because of java bean compatibility reasons
    * */
    @Deprecated
    Point(){}

    public Point(BigDecimal x, BigDecimal y) {
        this.x = x;
        this.y = y;
    }

    public BigDecimal getX() {
        return x;
    }

    public BigDecimal getY() {
        return y;
    }

    public BigDecimal distanceFrom(Point point) {
        return BigDecimal.valueOf(Math.sqrt(point.getX().subtract(x).pow(2).add(point.getY().subtract(y).pow(2)).doubleValue()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if ( x.compareTo(point.x) != 0) return false;
        return y.compareTo(point.y) == 0;
    }

    @Override
    public int hashCode() {
        int result = x.hashCode();
        result = 31 * result + y.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}