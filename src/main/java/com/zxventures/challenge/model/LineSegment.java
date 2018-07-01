package com.zxventures.challenge.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.SortedSet;
import java.util.TreeSet;

public class LineSegment {

    private Point vertexA;
    private Point vertexB;
    private BigDecimal minYAxis;
    private BigDecimal maxYAxis;
    private BigDecimal maxXAxis;

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
        boolean onTheRightOfEdge = rayEndpoint.getX().compareTo(determineXBy(rayEndpoint.getY())) == 1;
        return isInYAxisRange(ray) && !onTheRightOfEdge;
    }

    private boolean isInYAxisRange(Ray ray){
        BigDecimal rayEndpointYAxis = ray.getEndpoint().getY();
        return rayEndpointYAxis.compareTo(minYAxis) >= 0 && rayEndpointYAxis.compareTo(maxYAxis) <= 0;
    }

    private boolean isVertical(){
        return vertexA.getX().equals(vertexB.getX());
    }

    private boolean isHorizontal(){
        return vertexA.getY().equals(vertexB.getY());
    }

    private BigDecimal determineXBy(BigDecimal y){
        if(this.isHorizontal()){
            return maxXAxis;
        }

        if(this.isVertical()){
            return vertexA.getX();
        }
        return  BigDecimal.valueOf(-1).multiply(angularCoefficient().multiply(vertexA.getX())).add(vertexA.getY().subtract(y)).divide(angularCoefficient(), 20, RoundingMode.CEILING).multiply(BigDecimal.valueOf(-1));
    }

    private BigDecimal angularCoefficient(){
        return vertexB.getY().subtract(vertexA.getY()).divide(vertexB.getX().subtract(vertexA.getX()), 20, RoundingMode.CEILING);
    }
}