package com.zxventures.challenge.graphql;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.zxventures.challenge.dto.create.CreatePdvDto;
import com.zxventures.challenge.dto.read.GetPdvDto;
import com.zxventures.challenge.dto.templates.PdvDtoTemplateLoader;
import com.zxventures.challenge.model.MultiPolygon;
import com.zxventures.challenge.model.Pdv;
import com.zxventures.challenge.model.Point;
import com.zxventures.challenge.model.Polygon;
import com.zxventures.challenge.model.repository.PdvRepository;
import com.zxventures.challenge.model.template.PdvTemplateLoader;
import com.zxventures.challenge.service.PdvService;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static com.zxventures.challenge.resource.PdvAssertion.assertPdvsAreEquals;
import static com.zxventures.challenge.resource.PdvResource.PDVS_PATH;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QueryTest {

    @LocalServerPort
    private int serverPort;

    @Autowired
    private PdvRepository pdvRepository;

    @SpyBean
    private PdvService pdvService;

    @Autowired
    private ObjectMapper objectMapper;

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

        final String graphqlQuery =
                String.format("{getPdvById(id:\"%s\"){id,tradingName,ownerName,document,address{type,coordinates},coverageArea{type,coordinates}}}", savedPdv.getId());

        GetPdvDto response =
                given().queryParam("query", graphqlQuery)
                        .expect().statusCode(OK.value()).when().get("/graphql")
                        .jsonPath().getObject("data.getPdvById", GetPdvDto.class);

        assertPdvsAreEquals(savedPdv, response);
    }

    @Test
    public void shouldReturnNullIfThereIsNoPdvWithSpecifiedId(){
        final String graphqlQuery =
                String.format("{getPdvById(id:\"999\"){id,tradingName,ownerName,document,address{type,coordinates},coverageArea{type,coordinates}}}");

        GetPdvDto response =
                given().queryParam("query", graphqlQuery)
                        .expect().statusCode(OK.value()).when().get("/graphql")
                        .jsonPath().getObject("data.getPdvById", GetPdvDto.class);

        assertNull(response);
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

        final String graphqlQuery =
                String.format("{getCloserFrom(lng: 30, lat: 30){id,tradingName,ownerName,document,address{type,coordinates},coverageArea{type,coordinates}}}");

        GetPdvDto response =
                given().queryParam("query", graphqlQuery)
                        .expect().statusCode(OK.value()).when().get("/graphql")
                        .jsonPath().getObject("data.getCloserFrom", GetPdvDto.class);

        assertPdvsAreEquals(pdv, response);
    }

    @Test
    public void shouldReturnNullIfThereIsPdfThatServesPointArea(){

        when(pdvService.findCloserFrom(any())).thenReturn(Optional.empty());

        final String graphqlQuery =
                String.format("{getCloserFrom(lng: 30, lat: 30){id,tradingName,ownerName,document,address{type,coordinates},coverageArea{type,coordinates}}}");

        GetPdvDto response =
                given().queryParam("query", graphqlQuery)
                        .expect().statusCode(OK.value()).when().get("/graphql")
                        .jsonPath().getObject("data.getCloserFrom", GetPdvDto.class);

        assertNull(response);
    }
}
