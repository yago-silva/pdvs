package com.zxventures.challenge.model;

import java.math.BigDecimal;

public class Pdv {

    private Long id;

    private Point geolocation;

    private MultiPolygon coverageArea;

    private String tradingName;

    private String ownerName;

    private String document;

    /*
    * Here just because of java bean compatibility reasons
    * */
    @Deprecated
    Pdv(){}

    public Pdv(Point geolocation, MultiPolygon coverageArea, String tradingName, String ownerName, String document) {
        this.geolocation = geolocation;
        this.coverageArea = coverageArea;
        this.tradingName = tradingName;
        this.ownerName = ownerName;
        this.document = document;
    }

    public boolean serveRegion(Point point){
        return coverageArea.contains(point);
    }

    public BigDecimal distanceFrom(Point point){
        return geolocation.distanceFrom(point);
    }

    public Long getId() {
        return id;
    }

    public MultiPolygon getCoverageArea() {
        return coverageArea;
    }

    public Point getGeolocation() {
        return geolocation;
    }

    public String getTradingName() {
        return tradingName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getDocument() {
        return document;
    }
}