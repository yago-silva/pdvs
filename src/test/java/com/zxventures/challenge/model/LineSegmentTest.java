package com.zxventures.challenge.model;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class LineSegmentTest {

    private LineSegment diagonalLineSegment;
    private LineSegment verticalLineSegment;
    private LineSegment horizontalLineSegment;

    @Before
    public void setup(){
        Point diagonalLineVertexA = new Point(BigDecimal.valueOf(2), BigDecimal.valueOf(4));
        Point diagonalLineVertexB = new Point(BigDecimal.valueOf(4), BigDecimal.valueOf(1));
        this.diagonalLineSegment = new LineSegment(diagonalLineVertexA, diagonalLineVertexB);

        Point verticalLineVertexA = new Point(BigDecimal.valueOf(2), BigDecimal.valueOf(4));
        Point verticalLineVertexB = new Point(verticalLineVertexA.getX(), BigDecimal.valueOf(2));
        this.verticalLineSegment = new LineSegment(verticalLineVertexA, verticalLineVertexB);


        Point horizontalLineVertexA = new Point(BigDecimal.valueOf(2), BigDecimal.valueOf(4));
        Point horizontalLineVertexB = new Point(BigDecimal.valueOf(3), horizontalLineVertexA.getY());
        this.horizontalLineSegment = new LineSegment(horizontalLineVertexA, horizontalLineVertexB);
    }

    /*
    *    |       *
    *    |       |
    *    |       |
    *    |  -----+------------------->
    *    |       |
    *    |       |
    *    |       *
    * ___|____________________________
    *    |
    **/
    @Test
    public void shouldReturnTrueWhenRayIntersectsTheEdgeOfLine(){
        Point point = new Point(BigDecimal.valueOf(0), BigDecimal.valueOf(3));
        Ray ray = new Ray(point);

        assertTrue(diagonalLineSegment.intersectedBy(ray));
        assertTrue(verticalLineSegment.intersectedBy(ray));

        point = new Point(BigDecimal.valueOf(0), horizontalLineSegment.getVertexA().getY());
        ray = new Ray(point);
        assertTrue(horizontalLineSegment.intersectedBy(ray));
    }


    /*
    *    |
    *    |  -----*---------------*---->
    *    |
    * ___|____________________________
    *    |
    **/
    @Test
    public void shouldReturnTrueWhenRayCollinearWithLineSegment(){
        Point point = new Point(BigDecimal.valueOf(0), horizontalLineSegment.getVertexA().getY());
        Ray ray = new Ray(point);
        assertTrue(horizontalLineSegment.intersectedBy(ray));
    }


    /*
    *    |  -----*--------------->
    *    |       |
    *    |       |
    *    |       |
    *    |       |
    *    |       |
    *    |       *
    * ___|____________________________
    *    |
    **/
    @Test
    public void shouldReturnTrueWhenRayIntersectsVertexAOfLine(){
        Point endpoint = new Point(BigDecimal.valueOf(0), diagonalLineSegment.getVertexA().getY());
        Ray ray = new Ray(endpoint);
        assertTrue(diagonalLineSegment.intersectedBy(ray));

        endpoint = new Point(BigDecimal.valueOf(0), verticalLineSegment.getVertexA().getY());
        ray = new Ray(endpoint);
        assertTrue(verticalLineSegment.intersectedBy(ray));
    }


    /*
    *    |       *
    *    |       |
    *    |       |
    *    |       |
    *    |       |
    *    |       |
    *    |  -----*--------------->
    * ___|____________________________
    *    |
    **/
    @Test
    public void shouldReturnTrueWhenRayIntersectsVertexBOfLine(){
        Point endpoint = new Point(BigDecimal.valueOf(0), diagonalLineSegment.getVertexB().getY());
        Ray ray = new Ray(endpoint);
        assertTrue(diagonalLineSegment.intersectedBy(ray));


        endpoint = new Point(BigDecimal.valueOf(0), verticalLineSegment.getVertexB().getY());
        ray = new Ray(endpoint);
        assertTrue(verticalLineSegment.intersectedBy(ray));
    }


    /*
    *    |       *--------------->
    *    |       |
    *    |       |
    *    |       |
    *    |       |
    *    |       |
    *    |       *
    * ___|____________________________
    *    |
    **/
    @Test
    public void shouldReturnTrueWhenRayEndpointIsEqualToVertexAOfLine(){
        Ray ray = new Ray(diagonalLineSegment.getVertexA());
        assertTrue(diagonalLineSegment.intersectedBy(ray));

        ray = new Ray(verticalLineSegment.getVertexA());
        assertTrue(verticalLineSegment.intersectedBy(ray));
    }

    /*
    *    |       *
    *    |       |
    *    |       |
    *    |       |
    *    |       |
    *    |       |
    *    |       *--------------->
    * ___|____________________________
    *    |
    **/
    @Test
    public void shouldReturnTrueWhenRayEndpointIsEqualToVertexBOfLine(){
        Ray ray = new Ray(diagonalLineSegment.getVertexB());
        assertTrue(diagonalLineSegment.intersectedBy(ray));

        ray = new Ray(verticalLineSegment.getVertexB());
        assertTrue(verticalLineSegment.intersectedBy(ray));
    }

    /*
    *    |       *
    *    |       |
    *    |       |
    *    |       +------------------->
    *    |       |
    *    |       |
    *    |       *
    * ___|____________________________
    *    |
    **/
    @Test
    public void shouldReturnTrueWhenRayStartsInTheEdgeOfLine(){
        //x must be 3 when y is 2.5 for this line segment, so bellow point is on the edge...
        Point point = new Point(BigDecimal.valueOf(3), BigDecimal.valueOf(2.5));
        Ray ray = new Ray(point);
        assertTrue(diagonalLineSegment.intersectedBy(ray));


        point = new Point(verticalLineSegment.getVertexA().getX(), BigDecimal.valueOf(3));
        ray = new Ray(point);
        assertTrue(verticalLineSegment.intersectedBy(ray));

        BigDecimal xAxisAverage = horizontalLineSegment.getVertexA().getX().add(horizontalLineSegment.getVertexB().getX()).divide(BigDecimal.valueOf(2));
        point = new Point(xAxisAverage, horizontalLineSegment.getVertexA().getY());
        ray = new Ray(point);
        assertTrue(horizontalLineSegment.intersectedBy(ray));
    }


    /*
    *    |    ------------------->
    *    |       *
    *    |       |
    *    |       |
    *    |       |
    *    |       |
    *    |       *
    * ___|____________________________
    *    |
    **/
    @Test
    public void shouldReturnFalseWhenRayIsAboveOfLine(){
        Point point = new Point(BigDecimal.valueOf(5), BigDecimal.valueOf(5));
        Ray ray = new Ray(point);

        assertFalse(diagonalLineSegment.intersectedBy(ray));
        assertFalse(verticalLineSegment.intersectedBy(ray));
        assertFalse(horizontalLineSegment.intersectedBy(ray));
    }


    /*
    *    |       *
    *    |       |
    *    |       |
    *    |       |
    *    |       |
    *    |       *
    *    |   ---------------------->
    * ___|____________________________
    *    |
    **/
    @Test
    public void shouldReturnFalseWhenRayIsBelowOfLine(){
        Point point = new Point(BigDecimal.valueOf(0), BigDecimal.valueOf(0));
        Ray ray = new Ray(point);

        assertFalse(diagonalLineSegment.intersectedBy(ray));
        assertFalse(verticalLineSegment.intersectedBy(ray));
        assertFalse(horizontalLineSegment.intersectedBy(ray));
    }


    /*
    *    |
    *    |       *
    *    |       |
    *    |       |    ------------>
    *    |       |
    *    |       |
    *    |       *
    * ___|____________________________
    *    |
    **/
    @Test
    public void shouldReturnFalseWhenRayStartsOnTheRightOfLineEdge(){
        Point point = new Point(BigDecimal.valueOf(3.5), BigDecimal.valueOf(2.5));
        Ray ray = new Ray(point);

        assertFalse(diagonalLineSegment.intersectedBy(ray));
        assertFalse(verticalLineSegment.intersectedBy(ray));


        point = new Point(BigDecimal.valueOf(3.5), horizontalLineSegment.getVertexA().getY());
        ray = new Ray(point);
        assertFalse(horizontalLineSegment.intersectedBy(ray));
    }

    @Test
    public void shouldContainsAPointOfSegment(){
        LineSegment lineSegment = new LineSegment(new Point(BigDecimal.valueOf(3), BigDecimal.valueOf(3)), new Point(BigDecimal.valueOf(2), BigDecimal.valueOf(4)));
        Point pointThatBellowsToSegment = new Point(BigDecimal.valueOf(2.5), BigDecimal.valueOf(3.5));
        assertTrue(lineSegment.contains(pointThatBellowsToSegment));

        Point pointInHorizontalLine = new Point(BigDecimal.valueOf(2.4), BigDecimal.valueOf(4));
        assertTrue(horizontalLineSegment.contains(pointInHorizontalLine));
    }

    @Test
    public void shouldContainsAPointsThatMatchesVertex(){
        LineSegment lineSegment = new LineSegment(new Point(BigDecimal.valueOf(3), BigDecimal.valueOf(3)), new Point(BigDecimal.valueOf(2), BigDecimal.valueOf(4)));
        assertTrue(lineSegment.contains(lineSegment.getVertexA()));
        assertTrue(lineSegment.contains(lineSegment.getVertexB()));

        assertTrue(horizontalLineSegment.contains(horizontalLineSegment.getVertexA()));
        assertTrue(horizontalLineSegment.contains(horizontalLineSegment.getVertexB()));
    }

    @Test
    public void shouldNotContainsAPointOutsideSegment(){
        LineSegment lineSegment = new LineSegment(new Point(BigDecimal.valueOf(3), BigDecimal.valueOf(3)), new Point(BigDecimal.valueOf(2), BigDecimal.valueOf(4)));
        Point pointOutsideToSegment1 = new Point(BigDecimal.valueOf(2.5), BigDecimal.valueOf(3));
        Point pointOutsideToSegment2 = new Point(BigDecimal.valueOf(2), BigDecimal.valueOf(3.5));
        Point pointOutsideToSegment3 = new Point(BigDecimal.valueOf(2), BigDecimal.valueOf(3));
        assertFalse(lineSegment.contains(pointOutsideToSegment1));
        assertFalse(lineSegment.contains(pointOutsideToSegment2));
        assertFalse(lineSegment.contains(pointOutsideToSegment3));

        Point pointIOutHorizontalLine1 = new Point(BigDecimal.valueOf(1), BigDecimal.valueOf(4));
        Point pointIOutHorizontalLine2 = new Point(BigDecimal.valueOf(4), BigDecimal.valueOf(4));
        Point pointIOutHorizontalLine3 = new Point(BigDecimal.valueOf(1), BigDecimal.valueOf(5));
        assertFalse(horizontalLineSegment.contains(pointIOutHorizontalLine1));
        assertFalse(horizontalLineSegment.contains(pointIOutHorizontalLine2));
        assertFalse(horizontalLineSegment.contains(pointIOutHorizontalLine3));
    }

    @Test
    public void shouldReturnTrueWhenLineSegmentVerticesMarchesYAxis(){
        LineSegment lineSegment = new LineSegment(new Point(BigDecimal.valueOf(3), BigDecimal.valueOf(4)), new Point(BigDecimal.valueOf(2), BigDecimal.valueOf(4)));
        assertTrue(lineSegment.isHorizontal());
    }

    @Test
    public void shouldReturnFalseWhenLineSegmentVerticesDoesNotMarchesYAxis(){
        LineSegment lineSegment = new LineSegment(new Point(BigDecimal.valueOf(3), BigDecimal.valueOf(4)), new Point(BigDecimal.valueOf(2), BigDecimal.valueOf(5)));
        assertFalse(lineSegment.isHorizontal());
    }
}