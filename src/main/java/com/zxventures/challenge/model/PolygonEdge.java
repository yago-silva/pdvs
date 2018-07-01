package com.zxventures.challenge.model;

public class PolygonEdge extends LineSegment {

    private PolygonEdge previous;
    private PolygonEdge next;

    /*
    * Here just because of java bean compatibility reasons
    * */
    @Deprecated
    PolygonEdge(){}

    public PolygonEdge(Point vertexA, Point vertexB) {
        super(vertexA, vertexB);
    }

    public PolygonEdge getPrevious() {
        return previous;
    }
    public PolygonEdge getNext() {
        return next;
    }

    public void setNext(PolygonEdge next) {
        this.next = next;
        next.previous = this;
    }
}
