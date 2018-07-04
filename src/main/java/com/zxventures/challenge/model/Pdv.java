package com.zxventures.challenge.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document
public class Pdv {

    @Id
    private String id;

    private Point geolocation;

    private MultiPolygon coverageArea;

    private String tradingName;

    private String ownerName;

    @Indexed(unique = true)
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

    public String getId() {
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