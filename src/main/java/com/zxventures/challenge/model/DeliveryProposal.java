package com.zxventures.challenge.model;

import java.math.BigDecimal;

public class DeliveryProposal {

    private BigDecimal distance;

    public DeliveryProposal(BigDecimal distance) {
        this.distance = distance;
    }

    public BigDecimal getDistance() {
        return distance;
    }
}
