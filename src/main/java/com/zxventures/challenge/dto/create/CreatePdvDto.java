package com.zxventures.challenge.dto.create;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class CreatePdvDto {

    @NotBlank
    private String tradingName;

    @NotBlank
    private String ownerName;

    @NotBlank
    @CNPJ
    private String document;

    @NotNull
    @Valid
    private CreateMultipolygonDto coverageArea;

    @NotNull
    @Valid
    private CreatePdvGeolocationDto address;

    /**
     * Here just because of jackson
     * */
    @Deprecated
    CreatePdvDto(){}

    public CreatePdvDto(String tradingName, String ownerName, String document, CreateMultipolygonDto coverageArea, CreatePdvGeolocationDto address) {
        this.tradingName = tradingName;
        this.ownerName = ownerName;
        this.document = document;
        this.coverageArea = coverageArea;
        this.address = address;
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

    public CreateMultipolygonDto getCoverageArea() {
        return coverageArea;
    }

    public CreatePdvGeolocationDto getAddress() {
        return address;
    }

}
