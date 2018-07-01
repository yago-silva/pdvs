package com.zxventures.challenge.model;

import java.util.Optional;

public class Pdv {

    private Point geolocation;

    private MultiPolygon coverageArea;

    public Pdv(Point geolocation, MultiPolygon coverageArea) {
        this.geolocation = geolocation;
        this.coverageArea = coverageArea;
    }

    public Optional<DeliveryProposal> serveRegion(Point point){
        if(coverageArea.contains(point)){
            return Optional.of(new DeliveryProposal(geolocation.distanceFrom(point)));
        }
        return Optional.empty();
    }
}