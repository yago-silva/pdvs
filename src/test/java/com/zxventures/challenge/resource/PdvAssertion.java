package com.zxventures.challenge.resource;

import com.zxventures.challenge.dto.create.CreatePdvDto;
import com.zxventures.challenge.dto.read.GetPdvDto;
import com.zxventures.challenge.model.MultiPolygon;
import com.zxventures.challenge.model.Pdv;
import com.zxventures.challenge.model.Point;
import com.zxventures.challenge.model.Polygon;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class PdvAssertion {

    public static void assertPdvsAreEquals(Pdv pdv, CreatePdvDto dto){
        assertThat(pdv.getDocument(), equalTo(dto.getDocument()));
        assertThat(pdv.getOwnerName(), equalTo(dto.getOwnerName()));
        assertThat(pdv.getTradingName(), equalTo(dto.getTradingName()));

        Point pdvGeolocation = pdv.getGeolocation();
        assertThat(pdvGeolocation.getX().setScale(2, RoundingMode.CEILING), equalTo(dto.getAddress().getCoordinates().get(0).setScale(2, RoundingMode.CEILING)));
        assertThat(pdvGeolocation.getY().setScale(2, RoundingMode.CEILING), equalTo(dto.getAddress().getCoordinates().get(1).setScale(2, RoundingMode.CEILING)));

        MultiPolygon pdvCoverageArea = pdv.getCoverageArea();
        List<Polygon> pdvPolygons = pdvCoverageArea.getPolygons();

        List<List<List<BigDecimal>>> dtoPolygons = dto.getCoverageArea().getCoordinates().get(0);

        assertThat(pdvPolygons.size(), equalTo(dtoPolygons.size()));

        Iterator<Point> pdvCoverageAreaVerticesIterator = pdvCoverageArea.getVertices().iterator();

        for(List<List<BigDecimal>> dtoPolygon: dtoPolygons){
            Iterator<List<BigDecimal>> dtoPolygonIterator = dtoPolygon.iterator();
            while(dtoPolygonIterator.hasNext()){
                List<BigDecimal> dtoPoint = dtoPolygonIterator.next();
                assertThat(pdvCoverageAreaVerticesIterator.next(), equalTo(new Point(dtoPoint.get(0), dtoPoint.get(1))));
            }
        }
    }

    public static void assertPdvsAreEquals(Pdv pdv, GetPdvDto dto){
        assertThat(pdv.getDocument(), equalTo(dto.getDocument()));
        assertThat(pdv.getOwnerName(), equalTo(dto.getOwnerName()));
        assertThat(pdv.getTradingName(), equalTo(dto.getTradingName()));

        Point pdvGeolocation = pdv.getGeolocation();
        assertThat(pdvGeolocation.getX().setScale(2, RoundingMode.CEILING), equalTo(dto.getAddress().getCoordinates().get(0).setScale(2, RoundingMode.CEILING)));
        assertThat(pdvGeolocation.getY().setScale(2, RoundingMode.CEILING), equalTo(dto.getAddress().getCoordinates().get(1).setScale(2, RoundingMode.CEILING)));

        MultiPolygon pdvCoverageArea = pdv.getCoverageArea();
        List<Polygon> pdvPolygons = pdvCoverageArea.getPolygons();

        List<List<List<BigDecimal>>> dtoPolygons = dto.getCoverageArea().getCoordinates().get(0);

        assertThat(pdvPolygons.size(), equalTo(dtoPolygons.size()));

        Iterator<Point> pdvCoverageAreaVerticesIterator = pdvCoverageArea.getVertices().iterator();

        for(List<List<BigDecimal>> dtoPolygon: dtoPolygons){
            Iterator<List<BigDecimal>> dtoPolygonIterator = dtoPolygon.iterator();
            while(dtoPolygonIterator.hasNext()){
                List<BigDecimal> dtoPoint = dtoPolygonIterator.next();
                assertThat(pdvCoverageAreaVerticesIterator.next(), equalTo(new Point(dtoPoint.get(0), dtoPoint.get(1))));
            }
        }
    }
}
