package com.zxventures.challenge.model;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PdvTest {

    private Pdv pdv;
    private MultiPolygon multiPolygon;

    @Before
    public void setup(){
        multiPolygon = mock(MultiPolygon.class);
        pdv = new Pdv(new Point(BigDecimal.ONE, BigDecimal.ONE), multiPolygon);
    }

    @Test
    public void shouldReturnTrueWhenPvdMeetsRegion(){
        Point point = new Point(BigDecimal.valueOf(5), BigDecimal.valueOf(4));
        when(multiPolygon.contains(point)).thenReturn(true);
        DeliveryProposal deliveryProposal = pdv.serveRegion(point).get();
        assertThat(deliveryProposal.getDistance(), equalTo(BigDecimal.valueOf(5.0)));
    }

    @Test
    public void shouldReturnFalseWhenPvdNotMeetsRegion(){
        Point point = new Point(BigDecimal.valueOf(5), BigDecimal.valueOf(4));
        when(multiPolygon.contains(point)).thenReturn(false);
        assertFalse(pdv.serveRegion(point).isPresent());
    }
}