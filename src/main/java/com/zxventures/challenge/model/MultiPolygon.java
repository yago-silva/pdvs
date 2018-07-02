package com.zxventures.challenge.model;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class MultiPolygon {

    private List<Polygon> polygons;

    /*
    * Here just because of java bean compatibility reasons
    * */
    @Deprecated
    MultiPolygon(){}

    public MultiPolygon(List<Polygon> polygons){
        this.polygons = new ArrayList<>(polygons);
    }

    public boolean contains(Point point){
        /*
         * This is a technique to increase performance. We just excludes points that is not in max coordinates
         * rectangle, and so is for sure not inside polygon
         **/
        if(!isInsideMaxCoordinatesRectangle(point)){
            return false;
        }

        if(getVertices().contains(point)){
            return true;
        }

        List<PolygonEdge> edges = getEdges();
        for(LineSegment lineSegment : edges){
            if(lineSegment.contains(point)){
                return true;
            }
        }

        //Use even/odd rule to determine of point is or not inside polygon
        int intersectionsCount = countIntersections(point, edges);
        return intersectionsCount != 0 && !(intersectionsCount % 2 == 0);
    }

    private boolean isInsideMaxCoordinatesRectangle(Point point){

        SortedSet<BigDecimal> sortedXAxis = new TreeSet();
        SortedSet<BigDecimal> sortedYAxis = new TreeSet();
        for(Point vertex : getVertices()) {
            sortedXAxis.add(vertex.getX());
            sortedYAxis.add(vertex.getY());
        }

        BigDecimal minX = sortedXAxis.first();
        BigDecimal maxX = sortedXAxis.last();

        BigDecimal minY = sortedYAxis.first();
        BigDecimal maxY = sortedYAxis.last();

        return maxX.compareTo(point.getX()) >= 0 && minX.compareTo(point.getX()) <= 0
                && maxY.compareTo(point.getY()) >= 0 && minY.compareTo(point.getY()) <= 0;
    }

    private int countIntersections(Point point, List<PolygonEdge> edges){

        int intersections = 0;

        Ray ray = new Ray(point);

        for (PolygonEdge edge: edges){

            if(edge.intersectedBy(ray)){
                intersections ++;

                if(intersectsAChangeParityVertex(edge, point)){
                    intersections ++;
                }

                if(intersectsAChangeParityEdge(edge, point)){
                    intersections ++;
                }
            }
        }
        return intersections;
    }

    private boolean intersectsAChangeParityVertex(PolygonEdge edge, Point point){
        boolean pointMatchesVertexBYAxis = edge.getVertexB().getY().compareTo(point.getY()) == 0;
        PolygonEdge nextEdge = edge.getNext();
        boolean bellowOfEdgeVertexA = edge.getVertexA().getY().compareTo(point.getY()) == 1;
        boolean aboveOfNextEdgeVertexB = nextEdge.getVertexB().getY().compareTo(point.getY()) == -1;

        boolean aboveOfEdgeVertexA = edge.getVertexA().getY().compareTo(point.getY()) == -1;
        boolean bellowOfNextEdgeVertexB = nextEdge.getVertexB().getY().compareTo(point.getY()) == +1;

        return pointMatchesVertexBYAxis
                && ( (bellowOfEdgeVertexA && aboveOfNextEdgeVertexB) || (aboveOfEdgeVertexA && bellowOfNextEdgeVertexB));
    }

    private boolean intersectsAChangeParityEdge(PolygonEdge edge, Point point){
        boolean pointMatchesVertexBYAxis = edge.getVertexB().getY().compareTo(point.getY()) == 0;
        PolygonEdge polygonEdge = edge.getPrevious();
        PolygonEdge nextEdge = edge.getNext();
        boolean nextAndPreviousMaxYAxisAreBiggerThatEdgeYAxis = nextEdge.getVertexB().getY().compareTo(point.getY()) > 0
                && polygonEdge.getVertexA().getY().compareTo(point.getY()) > 0;

        boolean nextAndPreviousMaxYAxisAreSmallerThatEdgeYAxis = nextEdge.getVertexB().getY().compareTo(point.getY()) < 0
                && polygonEdge.getVertexA().getY().compareTo(point.getY()) < 0;

        return edge.isHorizontal() && pointMatchesVertexBYAxis
                && (nextAndPreviousMaxYAxisAreBiggerThatEdgeYAxis || nextAndPreviousMaxYAxisAreSmallerThatEdgeYAxis);
    }

    public List<Point> getVertices() {
        return polygons.stream().map(Polygon::getVertices).flatMap(List::stream).collect(Collectors.toList());
    }

    private List<PolygonEdge> getEdges(){
        return polygons.stream().map(Polygon::getEdges).flatMap(List::stream).collect(Collectors.toList());
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }
}