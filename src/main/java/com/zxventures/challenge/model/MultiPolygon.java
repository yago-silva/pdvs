package com.zxventures.challenge.model;

import java.util.*;

public class MultiPolygon {

    private List<PolygonEdge> edges;
    private List<Point> vertices;

    public MultiPolygon(List<Polygon> polygons){

        if(polygons.size() < 1){
            throw new IllegalArgumentException("MultiPolygon must have at least 1 polygon");
        }

        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();

        for (Polygon polygon: polygons) {
            this.vertices.addAll(polygon.getVertices());
            this.edges.addAll(polygon.getEdges());
        }
    }


    //TODO: Compare point with max coordinates rectangle before continue (to gain performance if point is outside rectangle)
    public boolean contains(Point point){
        if(vertices.contains(point)){
            return true;
        }

        for(LineSegment lineSegment : edges){
            if(lineSegment.contains(point)){
                return true;
            }
        }

        //TODO: Create a class that encapsulates even/odd rule
        //Use even/odd rule to determine of point is or not inside polygon
        int intersectionsCount = countIntersections(point);
        return intersectionsCount != 0 && !(intersectionsCount % 2 == 0);
    }

    private int countIntersections(Point point){

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

    List<Point> getVertices() {
        return vertices;
    }
}