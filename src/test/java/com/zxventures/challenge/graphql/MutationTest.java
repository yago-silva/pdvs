package com.zxventures.challenge.graphql;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.zxventures.challenge.dto.create.CreateMultipolygonDto;
import com.zxventures.challenge.dto.create.CreatePdvDto;
import com.zxventures.challenge.dto.templates.PdvDtoTemplateLoader;
import com.zxventures.challenge.model.Pdv;
import com.zxventures.challenge.repository.PdvRepository;
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

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static com.zxventures.challenge.resource.PdvAssertion.assertPdvsAreEquals;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MutationTest {

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
    public void shouldCreatePdvWithSuccess() throws JsonProcessingException {
        CreatePdvDto dto = Fixture.from(CreatePdvDto.class).gimme(PdvDtoTemplateLoader.VALID);

        String createPdvDtoJson = objectMapper.writeValueAsString(dto);

        final String graphqlMutation =
                String.format("{\"query\": \"mutation($pdv: PdvInputDto!) { save(input: $pdv) {id} }\", \"variables\": {\"pdv\": %s }}", createPdvDtoJson);

        given().log().all().contentType(JSON).body(graphqlMutation).expect().statusCode(OK.value()).when().post("/graphql");

        List<Pdv> allPdvs = pdvRepository.findAll();
        assertThat(allPdvs.size(), equalTo(1));
        Pdv createdPdv = allPdvs.get(0);

        assertPdvsAreEquals(createdPdv, dto);
    }


    @Test
    public void shouldReturnBadRequestIfTradingNameIsInvalid() throws JsonProcessingException {
        assertBadRequestForInvalidStringProperty("tradingName");
    }

    @Test
    public void shouldReturnBadRequestIfOwnerNameIsInvalid() throws JsonProcessingException {
        assertBadRequestForInvalidStringProperty("ownerName");
    }

    @Test
    public void shouldReturnBadRequestIfDocumentIsInvalid() throws JsonProcessingException {
        assertBadRequestForInvalidStringProperty("document");

        CreatePdvDto createPdvDtoInvalidDocument = Fixture.from(CreatePdvDto.class).gimme(PdvDtoTemplateLoader.VALID, new Rule(){{
            add("document","02622111000110");
        }});

        String graphqlMutation = String.format("{\"query\": \"mutation($pdv: PdvInputDto!) { save(input: $pdv) {id} }\", \"variables\": {\"pdv\": %s }}", objectMapper.writeValueAsString(createPdvDtoInvalidDocument));
        String errorMessage = given().log().all().contentType(JSON).body(graphqlMutation).expect().statusCode(OK.value()).when().post("/graphql")
                .jsonPath().getString(("errors[0].message"));
        assertTrue(errorMessage.contains("document CNPJ inválido"));
    }


    private void assertBadRequestForInvalidStringProperty(String propertyName) throws JsonProcessingException {
        CreatePdvDto createPdvDtoWithNullProperty = Fixture.from(CreatePdvDto.class).gimme(PdvDtoTemplateLoader.VALID, new Rule(){{
            add(propertyName,null);
        }});

        CreatePdvDto createPdvDtoWithBlankProperty = Fixture.from(CreatePdvDto.class).gimme(PdvDtoTemplateLoader.VALID, new Rule(){{
            add(propertyName,"     ");
        }});

        CreatePdvDto createPdvDtoWithEmptyProperty = Fixture.from(CreatePdvDto.class).gimme(PdvDtoTemplateLoader.VALID, new Rule(){{
            add(propertyName,"");
        }});


        String graphqlMutation = String.format("{\"query\": \"mutation($pdv: PdvInputDto!) { save(input: $pdv) {id} }\", \"variables\": {\"pdv\": %s }}", objectMapper.writeValueAsString(createPdvDtoWithNullProperty));
        String errorMessage = given().log().all().contentType(JSON).body(graphqlMutation).expect().statusCode(OK.value()).when().post("/graphql")
                .jsonPath().getString(("errors[0].message"));
        assertTrue(errorMessage.contains(propertyName + " Não pode estar em branco"));



        graphqlMutation = String.format("{\"query\": \"mutation($pdv: PdvInputDto!) { save(input: $pdv) {id} }\", \"variables\": {\"pdv\": %s }}", objectMapper.writeValueAsString(createPdvDtoWithBlankProperty));
        errorMessage = given().log().all().contentType(JSON).body(graphqlMutation).expect().statusCode(OK.value()).when().post("/graphql")
                .jsonPath().getString(("errors[0].message"));
        assertTrue(errorMessage.contains(propertyName + " Não pode estar em branco"));



        graphqlMutation = String.format("{\"query\": \"mutation($pdv: PdvInputDto!) { save(input: $pdv) {id} }\", \"variables\": {\"pdv\": %s }}", objectMapper.writeValueAsString(createPdvDtoWithEmptyProperty));
        errorMessage =  given().log().all().contentType(JSON).body(graphqlMutation).expect().statusCode(OK.value()).when().post("/graphql")
                .jsonPath().getString(("errors[0].message"));
        assertTrue(errorMessage.contains(propertyName + " Não pode estar em branco"));

        List<Pdv> allPdvs = pdvRepository.findAll();
        assertThat(allPdvs.size(), equalTo(0));
    }

    @Test
    public void shouldReturnBadRequestIfPdvDtoAddressIsInvalid() throws JsonProcessingException {
        CreatePdvDto invalidCreatePdvDto = Fixture.from(CreatePdvDto.class).gimme(PdvDtoTemplateLoader.VALID, new Rule(){{
            add("address",null);
        }});
        assertNotCreateInvalidPdv(invalidCreatePdvDto, "address não pode ser nulo");

        invalidCreatePdvDto = Fixture.from(CreatePdvDto.class).gimme(PdvDtoTemplateLoader.INVALID_BECAUSE_ADDRESS_TYPE_IS_NULL);
        assertNotCreateInvalidPdv(invalidCreatePdvDto, "address.type não pode ser nulo");

        invalidCreatePdvDto = Fixture.from(CreatePdvDto.class).gimme(PdvDtoTemplateLoader.INVALID_BECAUSE_ADDRESS_TYPE_IS_EMPTY);
        assertNotCreateInvalidPdv(invalidCreatePdvDto, "address.type deve corresponder à \"Point\"");

        invalidCreatePdvDto = Fixture.from(CreatePdvDto.class).gimme(PdvDtoTemplateLoader.INVALID_BECAUSE_ADDRESS_TYPE_IS_BLANK);
        assertNotCreateInvalidPdv(invalidCreatePdvDto, "address.type deve corresponder à \"Point\"");

        invalidCreatePdvDto = Fixture.from(CreatePdvDto.class).gimme(PdvDtoTemplateLoader.INVALID_BECAUSE_ADDRESS_TYPE_IS_INVALID);
        assertNotCreateInvalidPdv(invalidCreatePdvDto, "address.type deve corresponder à \"Point\"");

        invalidCreatePdvDto = Fixture.from(CreatePdvDto.class).gimme(PdvDtoTemplateLoader.INVALID_BECAUSE_ADDRESS_HAS_TOO_MANY_COORDINATIONS);
        assertNotCreateInvalidPdv(invalidCreatePdvDto, "address.coordinates tamanho deve estar entre 2 e 2");

        invalidCreatePdvDto = Fixture.from(CreatePdvDto.class).gimme(PdvDtoTemplateLoader.INVALID_BECAUSE_ADDRESS_HAS_ONLY_ONE_COORDINATE);
        assertNotCreateInvalidPdv(invalidCreatePdvDto, "address.coordinates tamanho deve estar entre 2 e 2");

        List<Pdv> allPdvs = pdvRepository.findAll();
        assertThat(allPdvs.size(), equalTo(0));
    }

    @Test
    public void shouldReturnBadRequestIfPdvDtoCoverageAreaIsInvalid() throws JsonProcessingException {
        CreatePdvDto invalidCreatePdvDto = Fixture.from(CreatePdvDto.class).gimme(PdvDtoTemplateLoader.VALID, new Rule(){{
            add("coverageArea",null);
        }});
        assertNotCreateInvalidPdv(invalidCreatePdvDto, "coverageArea não pode ser nulo");

        invalidCreatePdvDto = Fixture.from(CreatePdvDto.class).gimme(PdvDtoTemplateLoader.INVALID_BECAUSE_COVERAGE_AREA_TYPE_IS_NULL);
        assertNotCreateInvalidPdv(invalidCreatePdvDto, "coverageArea.type Não pode estar em branco");

        invalidCreatePdvDto = Fixture.from(CreatePdvDto.class).gimme(PdvDtoTemplateLoader.INVALID_BECAUSE_COVERAGE_AREA_TYPE_IS_BLANK);
        assertNotCreateInvalidPdv(invalidCreatePdvDto, "coverageArea.type deve corresponder à \"MultiPolygon\"");

        invalidCreatePdvDto = Fixture.from(CreatePdvDto.class).gimme(PdvDtoTemplateLoader.INVALID_BECAUSE_COVERAGE_AREA_TYPE_IS_EMPTY);
        assertNotCreateInvalidPdv(invalidCreatePdvDto, "coverageArea.type deve corresponder à \"MultiPolygon\"");

        invalidCreatePdvDto = Fixture.from(CreatePdvDto.class).gimme(PdvDtoTemplateLoader.INVALID_BECAUSE_COVERAGE_AREA_TYPE_IS_INVALID);
        assertNotCreateInvalidPdv(invalidCreatePdvDto, "coverageArea.type deve corresponder à \"MultiPolygon\"");

        //Polygon must have at least 3 vertices
        CreateMultipolygonDto multiPolygon = new CreateMultipolygonDto("MultiPolygon", asList(asList(asList(asList(BigDecimal.ONE, BigDecimal.ONE)))));
        invalidCreatePdvDto = Fixture.from(CreatePdvDto.class).gimme(PdvDtoTemplateLoader.VALID, new Rule(){{
            add("coverageArea", multiPolygon);
        }});
        assertNotCreateInvalidPdv(invalidCreatePdvDto, "Polygon must have at least 3 vertices");
    }

    private void assertNotCreateInvalidPdv(CreatePdvDto invalidDto, String expectedErrorMessage) throws JsonProcessingException {
        String graphqlMutation = String.format("{\"query\": \"mutation($pdv: PdvInputDto!) { save(input: $pdv) {id} }\", \"variables\": {\"pdv\": %s }}", objectMapper.writeValueAsString(invalidDto));
        String givenErrorMessage = given().log().all().contentType(JSON).body(graphqlMutation).expect().statusCode(OK.value()).when().post("/graphql")
                .jsonPath().getString(("errors[0].message"));
        assertTrue(givenErrorMessage.contains(expectedErrorMessage));
    }
}
