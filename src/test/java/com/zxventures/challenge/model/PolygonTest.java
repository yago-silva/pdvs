package com.zxventures.challenge.model;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

public class PolygonTest {

    @Test(expected = IllegalArgumentException.class)
    public void polygonMustHaveAtLeast3Vertices(){
        List<Point> listWithTwoPoints = asList(new Point(BigDecimal.valueOf(1), BigDecimal.valueOf(1)),
                new Point(BigDecimal.valueOf(2), BigDecimal.valueOf(2)));

        new Polygon(listWithTwoPoints);
    }

}