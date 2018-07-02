package com.zxventures.challenge.controller;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.jayway.restassured.RestAssured;
import com.zxventures.challenge.controller.dto.MultipolygonDto;
import com.zxventures.challenge.controller.dto.PdvDto;
import com.zxventures.challenge.controller.dto.templates.PdvDtoTemplateLoader;
import com.zxventures.challenge.model.MultiPolygon;
import com.zxventures.challenge.model.Pdv;
import com.zxventures.challenge.model.Point;
import com.zxventures.challenge.model.Polygon;
import com.zxventures.challenge.model.repository.PdvRepository;
import com.zxventures.challenge.model.template.PdvTemplateLoader;
import com.zxventures.challenge.service.PdvService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static com.zxventures.challenge.controller.PdvAssertion.assertPdvsAreEquals;
import static com.zxventures.challenge.controller.PdvController.PDVS_PATH;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PdvControllerTest {

    @LocalServerPort
    private int serverPort;

    @Autowired
    private PdvRepository pdvRepository;

    @SpyBean
    private PdvService pdvService;

    @BeforeClass
    public static void initialize(){
        FixtureFactoryLoader.loadTemplates("com.zxventures.challenge");
    }

    @Before
    public void setup() {
        RestAssured.port = serverPort;
    }

    @After
    public void tearDown(){
        pdvRepository.deleteAll();
    }

    @Test
    public void shouldCreatePdvWithSuccess(){
        PdvDto dto = Fixture.from(PdvDto.class).gimme(PdvDtoTemplateLoader.VALID);
        given().log().all().contentType(JSON).body(dto).expect().statusCode(CREATED.value()).when().post(PDVS_PATH);

        List<Pdv> allPdvs = pdvRepository.findAll();
        assertThat(allPdvs.size(), equalTo(1));
        Pdv createdPdv = allPdvs.get(0);

        assertPdvsAreEquals(createdPdv, dto);
    }

    @Test
    public void shouldReturnBadRequestIfTradingNameIsInvalid(){
        assertBadRequestForInvalidStringProperty("tradingName");
    }

    @Test
    public void shouldReturnBadRequestIfOwnerNameIsInvalid(){
        assertBadRequestForInvalidStringProperty("ownerName");
    }

    @Test
    public void shouldReturnBadRequestIfDocumentIsInvalid(){

        assertBadRequestForInvalidStringProperty("document");

        PdvDto pdvDtoInvalidDocument = Fixture.from(PdvDto.class).gimme(PdvDtoTemplateLoader.VALID, new Rule(){{
            add("document","02622111000110");
        }});

        given().contentType(JSON).body(pdvDtoInvalidDocument).expect().statusCode(BAD_REQUEST.value()).when().post(PDVS_PATH);

        List<Pdv> allPdvs = pdvRepository.findAll();
        assertThat(allPdvs.size(), equalTo(0));
    }

    @Test
    public void shouldReturnBadRequestIfPdvDtoAddressIsInvalid(){
        PdvDto invalidPdvDto = Fixture.from(PdvDto.class).gimme(PdvDtoTemplateLoader.VALID, new Rule(){{
            add("address",null);
        }});
        given().contentType(JSON).body(invalidPdvDto).expect().statusCode(BAD_REQUEST.value()).when().post(PDVS_PATH);

        invalidPdvDto = Fixture.from(PdvDto.class).gimme(PdvDtoTemplateLoader.INVALID_BECAUSE_ADDRESS_TYPE_IS_NULL);
        given().contentType(JSON).body(invalidPdvDto).expect().statusCode(BAD_REQUEST.value()).when().post(PDVS_PATH);

        invalidPdvDto = Fixture.from(PdvDto.class).gimme(PdvDtoTemplateLoader.INVALID_BECAUSE_ADDRESS_TYPE_IS_EMPTY);
        given().contentType(JSON).body(invalidPdvDto).expect().statusCode(BAD_REQUEST.value()).when().post(PDVS_PATH);

        invalidPdvDto = Fixture.from(PdvDto.class).gimme(PdvDtoTemplateLoader.INVALID_BECAUSE_ADDRESS_TYPE_IS_BLANK);
        given().contentType(JSON).body(invalidPdvDto).expect().statusCode(BAD_REQUEST.value()).when().post(PDVS_PATH);

        invalidPdvDto = Fixture.from(PdvDto.class).gimme(PdvDtoTemplateLoader.INVALID_BECAUSE_ADDRESS_TYPE_IS_INVALID);
        given().contentType(JSON).body(invalidPdvDto).expect().statusCode(BAD_REQUEST.value()).when().post(PDVS_PATH);

        invalidPdvDto = Fixture.from(PdvDto.class).gimme(PdvDtoTemplateLoader.INVALID_BECAUSE_ADDRESS_HAS_TOO_MANY_COORDINATIONS);
        given().contentType(JSON).body(invalidPdvDto).expect().statusCode(BAD_REQUEST.value()).when().post(PDVS_PATH);

        invalidPdvDto = Fixture.from(PdvDto.class).gimme(PdvDtoTemplateLoader.INVALID_BECAUSE_ADDRESS_HAS_ONLY_ONE_COORDINATE);
        given().contentType(JSON).body(invalidPdvDto).expect().statusCode(BAD_REQUEST.value()).when().post(PDVS_PATH);

        List<Pdv> allPdvs = pdvRepository.findAll();
        assertThat(allPdvs.size(), equalTo(0));
    }

    @Test
    public void shouldReturnBadRequestIfPdvDtoCoverageAreaIsInvalid(){
        PdvDto invalidPdvDto = Fixture.from(PdvDto.class).gimme(PdvDtoTemplateLoader.VALID, new Rule(){{
            add("coverageArea",null);
        }});
        given().contentType(JSON).body(invalidPdvDto).expect().statusCode(BAD_REQUEST.value()).when().post(PDVS_PATH);

        invalidPdvDto = Fixture.from(PdvDto.class).gimme(PdvDtoTemplateLoader.INVALID_BECAUSE_COVERAGE_AREA_TYPE_IS_NULL);
        given().contentType(JSON).body(invalidPdvDto).expect().statusCode(BAD_REQUEST.value()).when().post(PDVS_PATH);

        invalidPdvDto = Fixture.from(PdvDto.class).gimme(PdvDtoTemplateLoader.INVALID_BECAUSE_COVERAGE_AREA_TYPE_IS_BLANK);
        given().contentType(JSON).body(invalidPdvDto).expect().statusCode(BAD_REQUEST.value()).when().post(PDVS_PATH);

        invalidPdvDto = Fixture.from(PdvDto.class).gimme(PdvDtoTemplateLoader.INVALID_BECAUSE_COVERAGE_AREA_TYPE_IS_EMPTY);
        given().contentType(JSON).body(invalidPdvDto).expect().statusCode(BAD_REQUEST.value()).when().post(PDVS_PATH);

        invalidPdvDto = Fixture.from(PdvDto.class).gimme(PdvDtoTemplateLoader.INVALID_BECAUSE_COVERAGE_AREA_TYPE_IS_INVALID);
        given().contentType(JSON).body(invalidPdvDto).expect().statusCode(BAD_REQUEST.value()).when().post(PDVS_PATH);

        //Polygon must have at least 3 vertices
        MultipolygonDto multiPolygon = new MultipolygonDto("MultiPolygon", asList(asList(asList(asList(BigDecimal.ONE, BigDecimal.ONE)))));
        invalidPdvDto = Fixture.from(PdvDto.class).gimme(PdvDtoTemplateLoader.VALID, new Rule(){{
            add("coverageArea", multiPolygon);
        }});
        given().contentType(JSON).body(invalidPdvDto).expect().statusCode(BAD_REQUEST.value()).when().post(PDVS_PATH);
    }

    @Test
    public void shouldReturnAnExistentPdvById(){
        Polygon firstPdvPolygon = new Polygon(asList(
                new Point(BigDecimal.valueOf(0),BigDecimal.valueOf(0)),
                new Point(BigDecimal.valueOf(0),BigDecimal.valueOf(100)),
                new Point(BigDecimal.valueOf(100),BigDecimal.valueOf(100)),
                new Point(BigDecimal.valueOf(100),BigDecimal.valueOf(0))
        ));

        Pdv pdv = Fixture.from(Pdv.class).gimme(PdvTemplateLoader.VALID, new Rule(){{
            add("coverageArea", new MultiPolygon(asList(firstPdvPolygon)));
        }});

        Pdv savedPdv = pdvRepository.save(pdv);

        PdvDto response =
                given().expect().statusCode(OK.value()).when().get(PDVS_PATH+"/"+ savedPdv.getId()).as(PdvDto.class);

        assertPdvsAreEquals(savedPdv, response);
    }

    @Test
    public void shouldReturnNotFoundIfThereIsNoPdvWithSpecifiedId(){
        given().expect().statusCode(NOT_FOUND.value()).when().get(PDVS_PATH+"/999");
    }

    @Test
    public void shouldReturnTheCloserPdvThatServesPoint(){

        Polygon firstPdvPolygon = new Polygon(asList(
                new Point(BigDecimal.valueOf(0),BigDecimal.valueOf(0)),
                new Point(BigDecimal.valueOf(0),BigDecimal.valueOf(100)),
                new Point(BigDecimal.valueOf(100),BigDecimal.valueOf(100)),
                new Point(BigDecimal.valueOf(100),BigDecimal.valueOf(0))
        ));

        Pdv pdv = Fixture.from(Pdv.class).gimme(PdvTemplateLoader.VALID, new Rule(){{
            add("coverageArea", new MultiPolygon(asList(firstPdvPolygon)));
        }});

        when(pdvService.findCloserFrom(any())).thenReturn(Optional.of(pdv));

        PdvDto responseDto = given().expect().statusCode(OK.value()).when()
                .get(PDVS_PATH + "/closer?lng=50&lat=40").as(PdvDto.class);

        assertPdvsAreEquals(pdv, responseDto);
    }

    @Test
    public void shouldReturnNotFoundIfThereIsPdfThatServesPointArea(){

        Polygon firstPdvPolygon = new Polygon(asList(
                new Point(BigDecimal.valueOf(0),BigDecimal.valueOf(0)),
                new Point(BigDecimal.valueOf(0),BigDecimal.valueOf(100)),
                new Point(BigDecimal.valueOf(100),BigDecimal.valueOf(100)),
                new Point(BigDecimal.valueOf(100),BigDecimal.valueOf(0))
        ));

        Pdv pdv = Fixture.from(Pdv.class).gimme(PdvTemplateLoader.VALID, new Rule(){{
            add("coverageArea", new MultiPolygon(asList(firstPdvPolygon)));
        }});

        when(pdvService.findCloserFrom(any())).thenReturn(Optional.empty());

        given().expect().statusCode(NOT_FOUND.value()).when().get(PDVS_PATH + "/closer?lng=50&lat=40");
    }

    private void assertBadRequestForInvalidStringProperty(String propertyName){
        PdvDto pdvDtoWithNullProperty = Fixture.from(PdvDto.class).gimme(PdvDtoTemplateLoader.VALID, new Rule(){{
            add(propertyName,null);
        }});

        PdvDto pdvDtoWithBlankProperty = Fixture.from(PdvDto.class).gimme(PdvDtoTemplateLoader.VALID, new Rule(){{
            add(propertyName,"     ");
        }});

        PdvDto pdvDtoWithEmptyProperty = Fixture.from(PdvDto.class).gimme(PdvDtoTemplateLoader.VALID, new Rule(){{
            add(propertyName,"");
        }});

        given().contentType(JSON).body(pdvDtoWithNullProperty).expect().statusCode(BAD_REQUEST.value()).when().post(PDVS_PATH);
        given().contentType(JSON).body(pdvDtoWithBlankProperty).expect().statusCode(BAD_REQUEST.value()).when().post(PDVS_PATH);
        given().contentType(JSON).body(pdvDtoWithEmptyProperty).expect().statusCode(BAD_REQUEST.value()).when().post(PDVS_PATH);

        List<Pdv> allPdvs = pdvRepository.findAll();
        assertThat(allPdvs.size(), equalTo(0));
    }
}