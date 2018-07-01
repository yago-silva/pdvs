package com.zxventures.challenge.model;

import javax.sound.sampled.Line;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.math.RoundingMode.CEILING;

public class LineSegment {

    private Point vertexA;
    private Point vertexB;
    private BigDecimal minYAxis;
    private BigDecimal maxYAxis;
    private BigDecimal maxXAxis;
    private BigDecimal minXAxis;
    private static final Integer SCALE = 20;
    private static final RoundingMode ROUNDING_MODE = CEILING;

    /*
    * Here just because of java bean compatibility reasons
    * */
    @Deprecated
    LineSegment(){}

    public LineSegment(Point vertexA, Point vertexB) {
        this.vertexA = vertexA;
        this.vertexB = vertexB;

        SortedSet<BigDecimal> sortedYAxis = new TreeSet();
        sortedYAxis.add(vertexA.getY());
        sortedYAxis.add(vertexB.getY());

        this.minYAxis = sortedYAxis.first();
        this.maxYAxis = sortedYAxis.last();


        SortedSet<BigDecimal> sortedXAxis = new TreeSet();
        sortedXAxis.add(vertexA.getX());
        sortedXAxis.add(vertexB.getX());

        this.minXAxis = sortedXAxis.first();
        this.maxXAxis = sortedXAxis.last();
    }

    public Point getVertexA() {
        return vertexA;
    }

    public Point getVertexB() {
        return vertexB;
    }

    public boolean intersectedBy(Ray ray){
        Point rayEndpoint = ray.getEndpoint();
        boolean onTheRightOfEdge;

        if(isHorizontal()){
            onTheRightOfEdge = rayEndpoint.getX().compareTo(maxXAxis) == 1;
        }else {
            onTheRightOfEdge = rayEndpoint.getX().compareTo(determineXBy(rayEndpoint.getY())) == 1;
        }
        return isInYAxisRange(ray.getEndpoint().getY()) && !onTheRightOfEdge;
    }

    public boolean contains(Point point){
        if(isHorizontal()){
            return isInYAxisRange(point.getY()) && isInXAxisRange(point.getX());
        }

        return isInYAxisRange(point.getY()) &&
                determineXBy(point.getY()).compareTo(point.getX()) == 0;
    }

    private boolean isInYAxisRange(BigDecimal y){
        return y.compareTo(minYAxis) >= 0 && y.compareTo(maxYAxis) <= 0;
    }

    private boolean isInXAxisRange(BigDecimal x){
        return x.compareTo(minXAxis) >= 0 && x.compareTo(maxXAxis) <= 0;
    }

    private boolean isVertical(){
        return vertexA.getX().equals(vertexB.getX());
    }

    public boolean isHorizontal(){
        return vertexA.getY().equals(vertexB.getY());
    }

    private BigDecimal determineXBy(BigDecimal y){
        if(this.isVertical()){
            return vertexA.getX();
        }
        return  BigDecimal.valueOf(-1).multiply(angularCoefficient().multiply(vertexA.getX())).add(vertexA.getY().subtract(y)).divide(angularCoefficient(), SCALE, ROUNDING_MODE).multiply(BigDecimal.valueOf(-1));
    }

    private BigDecimal angularCoefficient(){
        return vertexB.getY().subtract(vertexA.getY()).divide(vertexB.getX().subtract(vertexA.getX()), SCALE, ROUNDING_MODE);
    }
}