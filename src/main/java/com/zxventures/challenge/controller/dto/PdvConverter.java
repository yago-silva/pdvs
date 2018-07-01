package com.zxventures.challenge.controller.dto;

import com.zxventures.challenge.model.MultiPolygon;
import com.zxventures.challenge.model.Pdv;
import com.zxventures.challenge.model.Point;
import com.zxventures.challenge.model.Polygon;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Component
public class PdvConverter {

    public Pdv fromDtoToModel(PdvDto dto){

        List<List<List<List<BigDecimal>>>> coverageAreaCoordinates = dto.getCoverageArea().getCoordinates();

        List<Polygon> polygons = coverageAreaCoordinates.stream().flatMap(List::stream)
                .map(lists -> lists.stream().map(bigDecimals -> new Point(bigDecimals.get(0), bigDecimals.get(1))).collect(Collectors.toList()))
                .map(Polygon::new).collect(Collectors.toList());

        MultiPolygon multiPolygon = new MultiPolygon(polygons);

        List<BigDecimal> coordinates = dto.getAddress().getCoordinates();
        Point geolocation = new Point(coordinates.get(0), coordinates.get(1));

        return new Pdv(geolocation, multiPolygon, dto.getTradingName(), dto.getOwnerName(), dto.getDocument());
    }

    public PdvDto fromModelToDto(Pdv pdv) {

        List<List<List<List<BigDecimal>>>> coordinates = pdv.getCoverageArea().getPolygons().stream().map(Polygon::getVertices)
                .map(vertices -> vertices.stream().map(vertex -> asList(vertex.getX(), vertex.getY())).collect(Collectors.toList()))
                .map(list -> asList(list))
                .collect(Collectors.toList());

        MultipolygonDto coverageArea = new MultipolygonDto("MultiPolygon",coordinates);

        Point pdvGeolocation = pdv.getGeolocation();
        PdvGeolocationDto address = new PdvGeolocationDto("Point", asList(pdvGeolocation.getX(), pdvGeolocation.getY()));

        PdvDto dto = new PdvDto(pdv.getId(), pdv.getTradingName(), pdv.getOwnerName(), pdv.getDocument(), coverageArea, address);

        return dto;
    }
}
