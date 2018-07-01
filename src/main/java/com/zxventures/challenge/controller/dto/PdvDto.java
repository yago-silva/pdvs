package com.zxventures.challenge.controller.dto;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class PdvDto {

    @Null
    private Long id;

    @NotBlank
    private String tradingName;

    @NotBlank
    private String ownerName;

    @NotBlank
    @CNPJ
    private String document;

    @NotNull
    @Valid
    private MultipolygonDto coverageArea;

    @NotNull
    @Valid
    private PdvGeolocationDto address;

    /**
     * Here just because of jackson
     * */
    @Deprecated
    PdvDto(){}

    public PdvDto(Long id, String tradingName, String ownerName, String document, MultipolygonDto coverageArea, PdvGeolocationDto address) {
        this.id = id;
        this.tradingName = tradingName;
        this.ownerName = ownerName;
        this.document = document;
        this.coverageArea = coverageArea;
        this.address = address;
    }

    public Long getId() {
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

    public MultipolygonDto getCoverageArea() {
        return coverageArea;
    }

    public PdvGeolocationDto getAddress() {
        return address;
    }
}
