package com.zxventures.challenge.controller.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public class PdvGeolocationDto {

    @NotNull
    @Pattern(regexp = "Point")
    private String type;

    @NotNull
    @Size(min = 2, max = 2)
    private List<BigDecimal> coordinates;

    /**
     * Here just because of jackson
     * */
    @Deprecated
    PdvGeolocationDto(){}

    public PdvGeolocationDto(String type, List<BigDecimal> coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    public List<BigDecimal> getCoordinates() {
        return coordinates;
    }

    public String getType() {
        return type;
    }
}
