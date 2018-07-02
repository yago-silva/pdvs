package com.zxventures.challenge.controller.dto;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.zxventures.challenge.model.MultiPolygon;
import com.zxventures.challenge.model.Pdv;
import com.zxventures.challenge.model.Point;
import com.zxventures.challenge.model.Polygon;
import com.zxventures.challenge.model.template.PdvTemplateLoader;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.zxventures.challenge.controller.PdvAssertion.assertPdvsAreEquals;
import static java.util.Arrays.asList;


//TODO: better use fixture factory to improve this code...
public class PdvConverterTest {

    private PdvConverter pdvConverter;

    @BeforeClass
    public static void initialize(){
        FixtureFactoryLoader.loadTemplates("com.zxventures.challenge");
    }

    @Before
    public void setup(){
        pdvConverter = new PdvConverter();
    }

    @Test
    public void shouldConvertFromDtoToModel(){
        List<BigDecimal> point1 = asList(BigDecimal.valueOf(0), BigDecimal.valueOf(0));
        List<BigDecimal> point2 = asList(BigDecimal.valueOf(0), BigDecimal.valueOf(10));
        List<BigDecimal> point3 = asList(BigDecimal.valueOf(10), BigDecimal.valueOf(10));
        List<BigDecimal> point4 = asList(BigDecimal.valueOf(10), BigDecimal.valueOf(0));

        List<List<List<List<BigDecimal>>>> coordinates = new ArrayList<>();
        coordinates.add(asList(asList(point1, point2, point3, point4)));

        MultipolygonDto multipolygonDto = new MultipolygonDto("MultiPolygon", coordinates);

        PdvGeolocationDto pdvGeolocationDto = new PdvGeolocationDto("Point", asList(BigDecimal.ONE, BigDecimal.TEN));

        PdvDto dto = new PdvDto(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                multipolygonDto, pdvGeolocationDto);

        Pdv pdv = pdvConverter.fromDtoToModel(dto);

        assertPdvsAreEquals(pdv, dto);
    }

    @Test
    public void shouldConvertFromModelToDto(){
        Polygon firstPdvPolygon = new Polygon(asList(
                new Point(BigDecimal.valueOf(0),BigDecimal.valueOf(0)),
                new Point(BigDecimal.valueOf(0),BigDecimal.valueOf(100)),
                new Point(BigDecimal.valueOf(100),BigDecimal.valueOf(100)),
                new Point(BigDecimal.valueOf(100),BigDecimal.valueOf(0))
        ));

        Pdv pdv = Fixture.from(Pdv.class).gimme(PdvTemplateLoader.VALID, new Rule(){{
            add("coverageArea", new MultiPolygon(asList(firstPdvPolygon)));
        }});

        PdvDto pdvDto = pdvConverter.fromModelToDto(pdv);
        assertPdvsAreEquals(pdv, pdvDto);
    }
}
