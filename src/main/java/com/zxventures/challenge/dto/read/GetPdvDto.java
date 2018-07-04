package com.zxventures.challenge.dto.read;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class GetPdvDto {

    @Null
    private String id;

    @NotBlank
    private String tradingName;

    @NotBlank
    private String ownerName;

    @NotBlank
    @CNPJ
    private String document;

    @NotNull
    @Valid
    private GetMultipolygonDto coverageArea;

    @NotNull
    @Valid
    private GetPdvGeolocationDto address;

    /**
     * Here just because of jackson
     * */
    @Deprecated
    GetPdvDto(){}

    public GetPdvDto(String id, String tradingName, String ownerName, String document, GetMultipolygonDto coverageArea, GetPdvGeolocationDto address) {
        this.id = id;
        this.tradingName = tradingName;
        this.ownerName = ownerName;
        this.document = document;
        this.coverageArea = coverageArea;
        this.address = address;
    }

    public String getId() {
        return id;
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

    public GetMultipolygonDto getCoverageArea() {
        return coverageArea;
    }

    public GetPdvGeolocationDto getAddress() {
        return address;
    }
}
