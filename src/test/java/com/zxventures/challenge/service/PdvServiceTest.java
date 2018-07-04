package com.zxventures.challenge.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.zxventures.challenge.model.MultiPolygon;
import com.zxventures.challenge.model.Pdv;
import com.zxventures.challenge.model.Point;
import com.zxventures.challenge.model.Polygon;
import com.zxventures.challenge.repository.PdvRepository;
import com.zxventures.challenge.model.template.PdvTemplateLoader;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PdvServiceTest {

    private PdvRepository pdvRepository;

    private PdvService pdvService;

    private Pdv firstPdv;
    private Pdv secondPdv;
    private Pdv thirdPdv;

    @BeforeClass
    public static void initialize(){
        FixtureFactoryLoader.loadTemplates("com.zxventures.challenge");
    }

    @Before
    public void setup(){
        Polygon firstPdvPolygon = new Polygon(asList(
                new Point(BigDecimal.valueOf(0),BigDecimal.valueOf(0)),
                new Point(BigDecimal.valueOf(0),BigDecimal.valueOf(100)),
                new Point(BigDecimal.valueOf(100),BigDecimal.valueOf(100)),
                new Point(BigDecimal.valueOf(100),BigDecimal.valueOf(0))
        ));

        Point firstPdvGeolocation = new Point(BigDecimal.valueOf(100),BigDecimal.valueOf(100));
        this.firstPdv = Fixture.from(Pdv.class).gimme(PdvTemplateLoader.VALID, new Rule(){{
            add("geolocation", firstPdvGeolocation);
            add("coverageArea", new MultiPolygon(asList(firstPdvPolygon)));
        }});


        Polygon secondPdvPolygon = new Polygon(asList(
                new Point(BigDecimal.valueOf(0),BigDecimal.valueOf(0)),
                new Point(BigDecimal.valueOf(0),BigDecimal.valueOf(10)),
                new Point(BigDecimal.valueOf(10),BigDecimal.valueOf(10)),
                new Point(BigDecimal.valueOf(10),BigDecimal.valueOf(0))
        ));
        Point secondPdvGeolocation = new Point(BigDecimal.ONE,BigDecimal.ONE);
        this.secondPdv = Fixture.from(Pdv.class).gimme(PdvTemplateLoader.VALID, new Rule(){{
            add("geolocation", secondPdvGeolocation);
            add("coverageArea", new MultiPolygon(asList(secondPdvPolygon)));
        }});


        Polygon thirdPdvPolygon = new Polygon(asList(
                new Point(BigDecimal.valueOf(-5),BigDecimal.valueOf(-5)),
                new Point(BigDecimal.valueOf(-5),BigDecimal.valueOf(-100)),
                new Point(BigDecimal.valueOf(-100),BigDecimal.valueOf(-100)),
                new Point(BigDecimal.valueOf(-100),BigDecimal.valueOf(-5))
        ));
        Point thirdPdvGeolocation = new Point(BigDecimal.valueOf(-10),BigDecimal.valueOf(-10));
        this.thirdPdv = Fixture.from(Pdv.class).gimme(PdvTemplateLoader.VALID, new Rule(){{
            add("geolocation", thirdPdvGeolocation);
            add("coverageArea", new MultiPolygon(asList(thirdPdvPolygon)));
        }});

        this.pdvRepository = mock(PdvRepository.class);
        when(pdvRepository.findAll()).thenReturn(asList(firstPdv , secondPdv, thirdPdv));
        this.pdvService = new PdvService(pdvRepository);
    }

    @Test
    public void shouldReturnTheCloserPdvThatServesArea(){
        //Point that is served by second pdv and is closer to it
        Point point = new Point(BigDecimal.valueOf(2), BigDecimal.valueOf(2));

        Pdv foundPdv = pdvService.findCloserFrom(point).get();

        assertThat(foundPdv.getId(), equalTo(secondPdv.getId()));
    }

    @Test
    public void shouldIgnorePdvsThatDoesNotServesAreaEvenIfItIsTheCloser(){
        //Point closer to second pdv but just served by first
        Point point = new Point(BigDecimal.valueOf(20), BigDecimal.valueOf(20));

        Pdv foundPdv = pdvService.findCloserFrom(point).get();

        assertThat(foundPdv.getId(), equalTo(firstPdv.getId()));
    }

    @Test
    public void shouldReturnEmptyOptionalIfThereIsNoPdvsThatServesArea(){
        //Point that no pdvs serves
        Point point = new Point(BigDecimal.valueOf(-1), BigDecimal.valueOf(-1));

        assertFalse(pdvService.findCloserFrom(point).isPresent());
    }
}