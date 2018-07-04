package com.zxventures.challenge.dto.read;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public class GetPdvGeolocationDto {

    private String type;

    private List<BigDecimal> coordinates;

    /**
     * Here just because of jackson
     * */
    @Deprecated
    GetPdvGeolocationDto(){}

    public GetPdvGeolocationDto(String type, List<BigDecimal> coordinates) {
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
