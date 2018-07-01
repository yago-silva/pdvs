package com.zxventures.challenge.model;

public class Ray {

    private Point endpoint;

    /*
    * Here just because of java bean compatibility reasons
    * */
    @Deprecated
    Ray(){}

    public Ray(Point endpoint){
        this.endpoint = endpoint;
    }

    public Point getEndpoint() {
        return endpoint;
    }
}