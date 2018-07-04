package com.zxventures.challenge.model;

import com.zxventures.challenge.exception.InvalidPolygonException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Polygon {

    private List<Point> vertices;

    /*
    * Here just because of java bean compatibility reasons
    * */
    @Deprecated
    Polygon(){}

    public Polygon(List<Point> vertices) {
        if(vertices.size() < 3){
            throw new InvalidPolygonException("Polygon must have at least 3 vertices");
        }
        this.vertices = new ArrayList<>(vertices);
    }

    public List<Point> getVertices() {
        return vertices;
    }

    public List<PolygonEdge> getEdges() {
        List<PolygonEdge> edges = new ArrayList<>();

        Iterator<Point> iterator = this.vertices.iterator();

        Point vertexA = iterator.next();

        PolygonEdge previousEdge = null;

        while(iterator.hasNext()){
            Point vertexB = iterator.next();
            PolygonEdge polygonEdge = new PolygonEdge(vertexA, vertexB);

            if(previousEdge != null){
                previousEdge.setNext(polygonEdge);
            }
            edges.add(polygonEdge);
            vertexA = vertexB;

            previousEdge = polygonEdge;
        }

        PolygonEdge firstPolygonEdge = edges.get(0);
        PolygonEdge actualLastPolygonEdge = edges.get(edges.size() -1);

        Point firstPolygonPoint = this.vertices.get(0);
        Point lastPolygonPoint = this.vertices.get(this.vertices.size() - 1);
        PolygonEdge lastLineSegment = new PolygonEdge(lastPolygonPoint, firstPolygonPoint);
        lastLineSegment.setNext(firstPolygonEdge);
        actualLastPolygonEdge.setNext(lastLineSegment);
        edges.add(lastLineSegment);

        return edges;
    }
}