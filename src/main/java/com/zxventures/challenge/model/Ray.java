package com.zxventures.challenge.model;

public class Ray {

    private Point endpoint;

    public Ray(Point endpoint){
        this.endpoint = endpoint;
    }

    public Point getEndpoint() {
        return endpoint;
    }
}