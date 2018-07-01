package com.zxventures.challenge.model;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;

public class MultiPolygonTest {

    public MultiPolygon multiPolygon;
    private Polygon excludeAreaPolygon;

    @Before
    public void setup(){
        Point pointA = new Point(valueOf(1), valueOf(1));
        Point pointB = new Point(valueOf(1), valueOf(10));
        Point pointC = new Point(valueOf(3), valueOf(8));
        Point pointD = new Point(valueOf(6), valueOf(10));
        Point pointE = new Point(valueOf(7), valueOf(8));
        Point pointF = new Point(valueOf(8), valueOf(10));
        Point pointG = new Point(valueOf(9), valueOf(6));
        Point pointH = new Point(valueOf(6), valueOf(6));
        Point pointI = new Point(valueOf(6), valueOf(4));
        Point pointJ = new Point(valueOf(7), valueOf(4));
        Point pointK = new Point(valueOf(8), valueOf(2));
        Point pointL = new Point(valueOf(9), valueOf(2));
        Point pointM = new Point(valueOf(10), valueOf(4));
        Point pointN = new Point(valueOf(11), valueOf(2));
        Point pointO = new Point(valueOf(10), valueOf(1));

        Polygon principalPolygon = new Polygon(asList(pointA, pointB, pointC, pointD, pointE, pointF, pointG, pointH, pointI, pointJ,
                pointK, pointL, pointM, pointN, pointO));


        Point pointP = new Point(valueOf(2), valueOf(4));
        Point pointQ = new Point(valueOf(3), valueOf(5));
        Point pointR = new Point(valueOf(4), valueOf(4));
        Point pointS = new Point(valueOf(3), valueOf(3));

        excludeAreaPolygon = new Polygon(asList(pointP, pointQ, pointR, pointS));

        this.multiPolygon = new MultiPolygon(asList(principalPolygon, excludeAreaPolygon));
    }

    @Test(expected = IllegalArgumentException.class)
    public void multiPolygonMustHaveAtLeastOnePolygon(){
        new MultiPolygon(asList());
    }

    /**
     *   o----------------o   o   X----------->
     *   |                |  /|  /|
     *   |                | / | / |
     *   |                |/  |/  |
     *   |                o   o   |
     *   |                        |
     *   |                        |
     *   |       o                |
     *   |      / \               |
     *   |     /   \              |
     *   |    o     o     o-------o
     *   |     \   /      |
     *   |      \ /       |
     *   |       o        o---o     o-------o
     *   |                    |     |        \
     *   |                    o-----o         \
     *   |                                     o
     *   |                                    /
     *   |                                   /
     *   o----------------------------------o
     */
    @Test
    public void shouldReturnTrueIfPointsMatchesAVertexOfPolygon(){
        for(Point vertex : multiPolygon.getVertices()){
            assertTrue(multiPolygon.contains(vertex));
        }
    }



    /**
     *   o----------------o   o   o
     *   |                |  /|  /|
     *   |                | / | / |
     *   |                |/  |/  |
     *   |                o   o   |
     *   |                        |
     *   |                        X---------------->
     *   |       o                |
     *   |      / \               |
     *   |     /   \              |
     *   |    o     o     o-------o
     *   |     \   /      |
     *   |      \ /       |
     *   |       o        o---o     o-------o
     *   |                    |     |        \
     *   |                    o-----o         \
     *   |                                     o
     *   |                                    /
     *   |                                   /
     *   o----------------------------------o
     */
    @Test
    public void shouldReturnTrueIfPointsBelongsToPolygonEdge(){
        //Point in multiPolygon's diagonal edge
        assertTrue(multiPolygon.contains(new Point(valueOf(2), valueOf(9))));

        //Point in multiPolygon's vertical edge
        assertTrue(multiPolygon.contains(new Point(valueOf(1), valueOf(5))));

        //Point in multiPolygon's horizontal edge
        assertTrue(multiPolygon.contains(new Point(valueOf(7), valueOf(6))));
    }


    /**
     *   o----------------o   o   o
     *   |                |  /|  /|
     *   |                | / | / |
     *   |                |/  |/  |
     *   |                o   o   |
     *   |                        |
     *   |                o-------X-------->
     *   |       o                |
     *   |      / \               |
     *   |     /   \              |
     *   |    o     o     o-------o
     *   |     \   /      |
     *   |      \ /       |
     *   |       o        o---o     o-------o
     *   |                    |     |        \
     *   |                    o-----o         \
     *   |                                     o
     *   |                                    /
     *   |                                   /
     *   o----------------------------------o
     */
    @Test
    public void shouldReturnTrueWhenRayIntersectsPolygonEdgeOddTimes(){
        Point point = new Point(valueOf(2), valueOf(7));
        assertTrue(multiPolygon.contains(point));
    }


    /**
     *   o----------------o   o   o
     *   |                |  /|  /|
     *   |                | / | / |
     *   |                |/  |/  |
     *   |                o   o   |
     *   |                        |
     * o-X------------------------X------->
     *   |       o                |
     *   |      / \               |
     *   |     /   \              |
     *   |    o     o     o-------o
     *   |     \   /      |
     *   |      \ /       |
     *   |       o        o---o     o-------o
     *   |                    |     |        \
     *   |                    o-----o         \
     *   |                                     o
     *   |                                    /
     *   |                                   /
     *   o----------------------------------o
     */
    @Test
    public void shouldReturnFalseWhenRayIntersectsPolygonEdgeEvenTimes(){
        Point point = new Point(valueOf(0), valueOf(7));
        Point point2 = new Point(valueOf(0), valueOf(4.5));
        assertFalse(multiPolygon.contains(point));
        assertFalse(multiPolygon.contains(point2));
    }

    /**
     *   o----------------o o-X---X------>
     *   |                |  /|  /|
     *   |                | / | / |
     *   |                |/  |/  |
     *   |                o   o   |
     *   |                        |
     *   |                        |
     *   |       o                |
     *   |      / \               |
     *   |     /   \              |
     *   |    o     o     o-------o
     *   |     \   /      |
     *   |      \ /       |
     *   |       o        o---o     o-------o
     *   |                    |     |        \
     *   |                    o-----o         \
     *   |                                     o
     *   |                                    /
     *   |                                   /
     *   o----------------------------------o
     */
    @Test
    public void shouldReturnFalseIfRayIntersectsOnlyPolygonExternalExtremities(){
        Point point1 = new Point(valueOf(0), valueOf(10));
        Point point2 = new Point(valueOf(7), valueOf(10));
        assertFalse(multiPolygon.contains(point1));
        assertFalse(multiPolygon.contains(point2));
    }


    /**
     *   o----------------o   o   o
     *   |                |  /|  /|
     *   |                | / | / |
     *   |                |/  |/  |
     *   |         o------X---X---X--------->
     *   |                        |
     *   |                        |
     *   |       o                |
     *   |      / \               |
     *   |     /   \              |
     *   |    o     o     o-------o
     *   |     \   /      |
     *   |      \ /       |
     *   |       o        o---o     o-------o
     *   |                    |     |        \
     *   |                    o-----o         \
     *   |                                     o
     *   |                                    /
     *   |                                   /
     *   o----------------------------------o
     */
    @Test
    public void shouldReturnTrueWhenPointInsidePolygonHasARayThatIntersectsANonChangeParityVertex(){
        Point point1 = new Point(valueOf(4), valueOf(8));
        Point point2 = new Point(valueOf(2), valueOf(8));
        assertTrue(multiPolygon.contains(point1));
        assertTrue(multiPolygon.contains(point2));
    }


    /**
     *   o----------------o   o   o
     *   |                |  /|  /|
     *   |                | / | / |
     *   |                |/  |/  |
     *   |                o   o   |
     *   |                        |
     *   |                        |
     *   |       o                |
     *   |      / \               |
     *   |     /   \              |
     *   |    o     o     o-------o
     *   |     \   /      |
     *   |      \ /       |
     *   |       o        o---o     o-------o
     *   |                    |     |        \
     *   |                    o-----o         \
     *   |                              o------X----->
     *   |                                    /
     *   |                                   /
     *   o----------------------------------o
     */
    @Test
    public void shouldReturnTrueWhenPointInsidePolygonHasARayThatIntersectsAChangeParityVertex(){
        Point point = new Point(valueOf(10), valueOf(2));
        assertTrue(multiPolygon.contains(point));
    }


    /**
     *   o----------------o   o   o
     *   |                |  /|  /|
     *   |                | / | / |
     *   |                |/  |/  |
     *   |                o   o   |
     *   |                        |
     *   |                        |
     *   |       o                |
     *   |      / \               |
     *   |     /   \              |
     *   |    o     o  o--X-------X---------->
     *   |     \   /      |
     *   |      \ /       |
     *   |       o        o---o     o-------o
     *   |                    |     |        \
     *   |                    o-----o         \
     *   |                                     o
     *   |                                    /
     *   |                                   /
     *   o----------------------------------o
     */
    @Test
    public void shouldReturnTrueForPointsInsidePolygonThatRayIsCollinearWithSomePolygonEdge(){
        Point point1 = new Point(valueOf(4), valueOf(6));
        Point point2 = new Point(valueOf(5), valueOf(4));
        Point point3 = new Point(valueOf(1.5), valueOf(4));
        assertTrue(multiPolygon.contains(point1));
        assertTrue(multiPolygon.contains(point2));
        assertTrue(multiPolygon.contains(point3));
    }


    /**
     *   o----------------o   o   o
     *   |                |  /|  /|
     *   |                | / | / |
     *   |                |/  |/  |
     *   |                o   o   |
     *   |                        |
     *   |                        |
     *   |       o                |
     *   |      / \               |
     *   |     /   \              |
     * o-X----X-----X-----X-------X-------->
     *   |     \   /      |
     *   |      \ /       |
     *   |       o        o---o     o-------o
     *   |                    |     |        \
     *   |                    o-----o         \
     *   |                                     o
     *   |                                    /
     *   |                                   /
     *   o----------------------------------o
     */
    @Test
    public void shouldReturnFalseForPointsOutsidePolygonThatHasARayCollinearWithSomePolygonEdge(){
        Point point1 = new Point(valueOf(0), valueOf(6));
        Point point2 = new Point(valueOf(0), valueOf(4));
        Point point3 = new Point(valueOf(0), valueOf(1));
        assertFalse(multiPolygon.contains(point1));
        assertFalse(multiPolygon.contains(point2));
        assertFalse(multiPolygon.contains(point3));
    }


    /**
     *   o----------------o   o   o
     *   |                |  /|  /|
     *   |                | / | / |
     *   |                |/  |/  |
     *   |                o   o   |
     *   |                        |
     *   |                        |
     *   |       o                |
     *   |      / \               |
     *   |     /   \              |
     *   |    o     o     o-------o
     *   |     \   /      |
     *   |      \ /       |
     *   |       o        o---o     o-------o
     *   |                    |     |        \
     *   |          o---------X-----X---------X------->
     *   |                                     o
     *   |                                    /
     *   |                                   /
     *   o----------------------------------o
     */
    @Test
    public void shouldReturnTrueWhenPointInsidePolygonHasARayThatIntersectsAChangeParityEdge(){
        Point point = new Point(valueOf(5), valueOf(2));
        assertTrue(multiPolygon.contains(point));
    }


    /**
     *   o----------------o   o   o
     *   |                |  /|  /|
     *   |                | / | / |
     *   |                |/  |/  |
     *   |                o   o   |
     *   |                        |
     *   |                        |
     *   |       o                |
     *   |      / \               |
     *   |     /   \              |
     *   |    o     o     o-------o
     *   |     \   /      |
     *   |      \ /       |
     *   |       o        o---o     o-------o
     *   |                    |     |        \
     * o-x--------------------X-----X---------X--------->
     *   |                                     o
     *   |                                    /
     *   |                                   /
     *   o----------------------------------o
     */
    @Test
    public void shouldReturnFalseWhenPointOutsidePolygonHasARayThatIntersectsAChangeParityEdge(){
        Point point = new Point(valueOf(0), valueOf(2));
        assertFalse(multiPolygon.contains(point));
    }


    /**
     *   o----------------o   o   o
     *   |                |  /|  /|
     *   |                | / | / |
     *   |                |/  |/  |
     *   |                o   o   |
     *   |                        |
     *   |                        |
     *   |       o                |
     *   |      / \               |
     *   |     /   \              |
     *   |    o  o--X-----X-------X-------->
     *   |     \   /      |
     *   |      \ /       |
     *   |       o        o---o     o-------o
     *   |                    |     |        \
     *   |                    o-----o         \
     *   |                                     o
     *   |                                    /
     *   |                                   /
     *   o----------------------------------o
     */
    @Test
    public void shouldReturnFalseForPointsInsideExcludeAreaPolygon(){
        Point point1 = new Point(valueOf(3), valueOf(4.5));
        Point point2 = new Point(valueOf(3), valueOf(4));
        assertFalse(multiPolygon.contains(point1));
        assertFalse(multiPolygon.contains(point2));
    }

    /**
     *   o----------------o   o   o
     *   |                |  /|  /|
     *   |                | / | / |
     *   |                |/  |/  |
     *   |                o   o   |
     *   |                        |
     *   |                        |
     *   |       o                |
     *   |      / \               |
     *   |     /   \              |
     *   |    o     X-----X-------X-------->
     *   |     \   /      |
     *   |      \ /       |
     *   |       o        o---o     o-------o
     *   |                    |     |        \
     *   |                    o-----o         \
     *   |                                     o
     *   |                                    /
     *   |                                   /
     *   o----------------------------------o
     */
    @Test
    public void shouldReturnTrueForPointsThatMatchesExcludeAreaPolygonVertex(){
        for (Point vertex : excludeAreaPolygon.getVertices()) {
            assertTrue(multiPolygon.contains(vertex));
        }
    }


    /**
     *   o----------------o   o   o
     *   |                |  /|  /|
     *   |                | / | / |
     *   |                |/  |/  |
     *   |                o   o   |
     *   |                        |
     *   |                        |
     *   |       o                |
     *   |      / \               |
     *   |     /   X--------------X-------->
     *   |    o     o     o-------o
     *   |     \   /      |
     *   |      \ /       |
     *   |       o        o---o     o-------o
     *   |                    |     |        \
     *   |                    o-----o         \
     *   |                                     o
     *   |                                    /
     *   |                                   /
     *   o----------------------------------o
     */
    @Test
    public void shouldReturnTrueForPointsThatBelongsToExcludeAreaPolygonEdge(){
        Point pointInExcludeAreaPolygonEdge1 = new Point(BigDecimal.valueOf(2.5), BigDecimal.valueOf(4.5));
        Point pointInExcludeAreaPolygonEdge2 = new Point(BigDecimal.valueOf(3.5), BigDecimal.valueOf(3.5));
        Point pointInExcludeAreaPolygonEdge3 = new Point(BigDecimal.valueOf(3.5), BigDecimal.valueOf(4.5));
        Point pointInExcludeAreaPolygonEdge4 = new Point(BigDecimal.valueOf(2.5), BigDecimal.valueOf(3.5));
        assertTrue(multiPolygon.contains(pointInExcludeAreaPolygonEdge1));
        assertTrue(multiPolygon.contains(pointInExcludeAreaPolygonEdge2));
        assertTrue(multiPolygon.contains(pointInExcludeAreaPolygonEdge3));
        assertTrue(multiPolygon.contains(pointInExcludeAreaPolygonEdge4));
    }
}