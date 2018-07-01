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
}