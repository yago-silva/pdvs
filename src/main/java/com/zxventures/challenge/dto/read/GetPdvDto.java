package com.zxventures.challenge.dto.read;

public class GetPdvDto {

    private String id;

    private String tradingName;

    private String ownerName;

    private String document;

    private GetMultipolygonDto coverageArea;

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
