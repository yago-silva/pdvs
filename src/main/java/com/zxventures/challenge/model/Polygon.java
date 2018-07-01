package com.zxventures.challenge.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Polygon {

    private List<Point> vertices;
    private List<PolygonEdge> edges;

    public Polygon(List<Point> vertices) {
        if(vertices.size() < 3){
            throw new IllegalArgumentException("MultiPolygon must have at least 3 vertices");
        }
        this.vertices = vertices;

        this.edges = new ArrayList<>();

        Iterator<Point> iterator = this.vertices.iterator();

        Point vertexA = iterator.next();

        PolygonEdge previousEdge = null;

        while(iterator.hasNext()){
            Point vertexB = iterator.next();
            PolygonEdge polygonEdge = new PolygonEdge(vertexA, vertexB);

            if(previousEdge != null){
                previousEdge.setNext(polygonEdge);
            }
            this.edges.add(polygonEdge);
            vertexA = vertexB;

            previousEdge = polygonEdge;
        }

        PolygonEdge firstPolygonEdge = this.edges.get(0);
        PolygonEdge actualLastPolygonEdge = this.edges.get(this.edges.size() -1);


        Point firstPolygonPoint = this.vertices.get(0);
        Point lastPolygonPoint = this.vertices.get(this.vertices.size() - 1);
        PolygonEdge lastLineSegment = new PolygonEdge(lastPolygonPoint, firstPolygonPoint);
        lastLineSegment.setNext(firstPolygonEdge);
        actualLastPolygonEdge.setNext(lastLineSegment);
        this.edges.add(lastLineSegment);
    }

    public List<Point> getVertices() {
        return vertices;
    }

    public List<PolygonEdge> getEdges() {
        return edges;
    }
}