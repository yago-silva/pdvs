package com.zxventures.challenge.model;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class PointTest {

    @Test
    public void pointsThatAllAxesAreEqualShouldBeEqual(){
        Point firstPoint = new Point(BigDecimal.valueOf(1), BigDecimal.valueOf(5));
        Point secondPoint = new Point(BigDecimal.valueOf(1), BigDecimal.valueOf(5));

        assertThat(firstPoint, equalTo(secondPoint));
        assertThat(firstPoint.hashCode(), equalTo(secondPoint.hashCode()));
    }

    @Test
    public void pointsWithDifferentXAxisShouldBeDifferent(){
        Point firstPoint = new Point(BigDecimal.valueOf(1), BigDecimal.valueOf(5));
        Point secondPoint = new Point(BigDecimal.valueOf(2), BigDecimal.valueOf(5));

        assertThat(firstPoint, not(equalTo(secondPoint)));
        assertThat(firstPoint.hashCode(), not(equalTo(secondPoint.hashCode())));
    }

    @Test
    public void pointsWithDifferentYAxisShouldBeDifferent(){
        Point firstPoint = new Point(BigDecimal.valueOf(1), BigDecimal.valueOf(5));
        Point secondPoint = new Point(BigDecimal.valueOf(1), BigDecimal.valueOf(10));

        assertThat(firstPoint, not(equalTo(secondPoint)));
        assertThat(firstPoint.hashCode(), not(equalTo(secondPoint.hashCode())));
    }

    @Test
    public void pointsWithNoCommonAxesShouldBeDifferent(){
        Point firstPoint = new Point(BigDecimal.valueOf(1), BigDecimal.valueOf(5));
        Point secondPoint = new Point(BigDecimal.valueOf(2), BigDecimal.valueOf(10));

        assertThat(firstPoint, not(equalTo(secondPoint)));
        assertThat(firstPoint.hashCode(), not(equalTo(secondPoint.hashCode())));
    }

    @Test
    public void shouldCalculateDistanceFromOtherPoint(){
        Point point = new Point(BigDecimal.valueOf(1), BigDecimal.valueOf(1));
        Point verticalOtherPoint = new Point(BigDecimal.valueOf(1), BigDecimal.valueOf(3));
        Point horizontalOtherPoint = new Point(BigDecimal.valueOf(3), BigDecimal.valueOf(1));
        Point diagonalOtherPoint = new Point(BigDecimal.valueOf(5), BigDecimal.valueOf(4));

        assertThat(point.distanceFrom(verticalOtherPoint), equalTo(BigDecimal.valueOf(2.0)));
        assertThat(point.distanceFrom(horizontalOtherPoint), equalTo(BigDecimal.valueOf(2.0)));
        assertThat(point.distanceFrom(diagonalOtherPoint), equalTo(BigDecimal.valueOf(5.0)));
    }

    @Test
    public void distanceBetweenToEqualPointsShouldBeZero(){
        Point point = new Point(BigDecimal.valueOf(1), BigDecimal.valueOf(1));
        Point otherPoint = new Point(BigDecimal.valueOf(1), BigDecimal.valueOf(1));
        assertThat(point.distanceFrom(otherPoint), equalTo(BigDecimal.valueOf(0.0)));
    }
}