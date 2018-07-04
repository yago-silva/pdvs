package com.zxventures.challenge.dto.read;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

public class GetMultipolygonDto {

    @NotBlank
    @Pattern(regexp = "MultiPolygon")
    private String type;

    @NotNull
    private List<List<List<List<BigDecimal>>>> coordinates;

    /**
     * Here just because of jackson
     * */
    @Deprecated
    GetMultipolygonDto(){}

    public GetMultipolygonDto(String type, List<List<List<List<BigDecimal>>>> coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }


    public String getType() {
        return type;
    }

    public List<List<List<List<BigDecimal>>>> getCoordinates() {
        return coordinates;
    }
}
