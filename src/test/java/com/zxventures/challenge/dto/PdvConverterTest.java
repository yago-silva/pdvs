package com.zxventures.challenge.dto;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.zxventures.challenge.dto.create.CreateMultipolygonDto;
import com.zxventures.challenge.dto.create.CreatePdvDto;
import com.zxventures.challenge.dto.create.CreatePdvGeolocationDto;
import com.zxventures.challenge.dto.read.GetPdvDto;
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

import static com.zxventures.challenge.resource.PdvAssertion.assertPdvsAreEquals;
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

        CreateMultipolygonDto createMultipolygonDto = new CreateMultipolygonDto("MultiPolygon", coordinates);

        CreatePdvGeolocationDto createPdvGeolocationDto = new CreatePdvGeolocationDto("Point", asList(BigDecimal.ONE, BigDecimal.TEN));

        CreatePdvDto dto = new CreatePdvDto(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                createMultipolygonDto, createPdvGeolocationDto);

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

        GetPdvDto getPdvDto = pdvConverter.fromModelToDto(pdv);
        assertPdvsAreEquals(pdv, getPdvDto);
    }
}
