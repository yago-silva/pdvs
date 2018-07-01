package com.zxventures.challenge.controller.dto.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.zxventures.challenge.CustomRule;
import com.zxventures.challenge.controller.dto.MultipolygonDto;
import com.zxventures.challenge.controller.dto.PdvDto;
import com.zxventures.challenge.controller.dto.PdvGeolocationDto;


public class PdvDtoTemplateLoader  implements TemplateLoader {

    public static final String VALID = "VALID";
    public static final String INVALID_BECAUSE_ADDRESS_TYPE_IS_NULL = "INVALID_BECAUSE_ADDRESS_TYPE_IS_NULL";
    public static final String INVALID_BECAUSE_ADDRESS_TYPE_IS_BLANK = "INVALID_BECAUSE_ADDRESS_TYPE_IS_BLANK";
    public static final String INVALID_BECAUSE_ADDRESS_TYPE_IS_EMPTY = "INVALID_BECAUSE_ADDRESS_TYPE_IS_EMPTY";
    public static final String INVALID_BECAUSE_ADDRESS_TYPE_IS_INVALID = "INVALID_BECAUSE_ADDRESS_TYPE_IS_INVALID";

    public static final String INVALID_BECAUSE_ADDRESS_HAS_TOO_MANY_COORDINATIONS = "INVALID_BECAUSE_ADDRESS_HAS_TOO_MANY_COORDINATIONS";
    public static final String INVALID_BECAUSE_ADDRESS_HAS_ONLY_ONE_COORDINATE = "INVALID_BECAUSE_ADDRESS_HAS_ONLY_ONE_COORDINATE";

    public static final String INVALID_BECAUSE_COVERAGE_AREA_TYPE_IS_NULL = "INVALID_BECAUSE_COVERAGE_AREA_TYPE_IS_NULL";
    public static final String INVALID_BECAUSE_COVERAGE_AREA_TYPE_IS_BLANK = "INVALID_BECAUSE_COVERAGE_AREA_TYPE_IS_BLANK";
    public static final String INVALID_BECAUSE_COVERAGE_AREA_TYPE_IS_EMPTY = "INVALID_BECAUSE_COVERAGE_AREA_TYPE_IS_EMPTY";
    public static final String INVALID_BECAUSE_COVERAGE_AREA_TYPE_IS_INVALID = "INVALID_BECAUSE_COVERAGE_AREA_TYPE_IS_INVALID";

    @Override
    public void load() {
        Fixture.of(PdvDto.class).addTemplate(VALID, new CustomRule() {{
            add("tradingName", name());
            add("ownerName", name());
            add("document", cnpj());
            add("address", one(PdvGeolocationDto.class, PdvGeolocationDtoTemplateLoader.VALID));
            add("coverageArea", one(MultipolygonDto.class, MultipolygonDtoTemplateLoader.VALID));
        }});

        Fixture.of(PdvDto.class).addTemplate(INVALID_BECAUSE_ADDRESS_TYPE_IS_NULL).inherits(VALID, new CustomRule() {{
            add("address", one(PdvGeolocationDto.class, PdvGeolocationDtoTemplateLoader.INVALID_BECAUSE_TYPE_IS_NULL));
        }});

        Fixture.of(PdvDto.class).addTemplate(INVALID_BECAUSE_ADDRESS_TYPE_IS_BLANK).inherits(VALID, new CustomRule() {{
            add("address", one(PdvGeolocationDto.class, PdvGeolocationDtoTemplateLoader.INVALID_BECAUSE_TYPE_IS_BLANK));
        }});

        Fixture.of(PdvDto.class).addTemplate(INVALID_BECAUSE_ADDRESS_TYPE_IS_EMPTY).inherits(VALID, new CustomRule() {{
            add("address", one(PdvGeolocationDto.class, PdvGeolocationDtoTemplateLoader.INVALID_BECAUSE_TYPE_IS_EMPTY));
        }});

        Fixture.of(PdvDto.class).addTemplate(INVALID_BECAUSE_ADDRESS_TYPE_IS_INVALID).inherits(VALID, new CustomRule() {{
            add("address", one(PdvGeolocationDto.class, PdvGeolocationDtoTemplateLoader.INVALID_BECAUSE_HAS_A_INVALID_TYPE));
        }});

        Fixture.of(PdvDto.class).addTemplate(INVALID_BECAUSE_ADDRESS_HAS_TOO_MANY_COORDINATIONS).inherits(VALID, new CustomRule() {{
            add("address", one(PdvGeolocationDto.class, PdvGeolocationDtoTemplateLoader.INVALID_BECAUSE_TOO_MANY_BIG_DECIMALS_IN_COORDINATES));
        }});

        Fixture.of(PdvDto.class).addTemplate(INVALID_BECAUSE_ADDRESS_HAS_ONLY_ONE_COORDINATE).inherits(VALID, new CustomRule() {{
            add("address", one(PdvGeolocationDto.class, PdvGeolocationDtoTemplateLoader.INVALID_BECAUSE_COORDINATES_HAS_ONLY_ONE_BIG_DECIMAL));
        }});


        Fixture.of(PdvDto.class).addTemplate(INVALID_BECAUSE_COVERAGE_AREA_TYPE_IS_NULL).inherits(VALID, new CustomRule() {{
            add("coverageArea", one(MultipolygonDto.class, MultipolygonDtoTemplateLoader.INVALID_BECAUSE_TYPE_IS_NULL));
        }});

        Fixture.of(PdvDto.class).addTemplate(INVALID_BECAUSE_COVERAGE_AREA_TYPE_IS_BLANK).inherits(VALID, new CustomRule() {{
            add("coverageArea", one(MultipolygonDto.class, MultipolygonDtoTemplateLoader.INVALID_BECAUSE_TYPE_IS_BLANK));
        }});

        Fixture.of(PdvDto.class).addTemplate(INVALID_BECAUSE_COVERAGE_AREA_TYPE_IS_EMPTY).inherits(VALID, new CustomRule() {{
            add("coverageArea", one(MultipolygonDto.class, MultipolygonDtoTemplateLoader.INVALID_BECAUSE_TYPE_IS_EMPTY));
        }});

        Fixture.of(PdvDto.class).addTemplate(INVALID_BECAUSE_COVERAGE_AREA_TYPE_IS_INVALID).inherits(VALID, new CustomRule() {{
            add("coverageArea", one(MultipolygonDto.class, MultipolygonDtoTemplateLoader.INVALID_BECAUSE_HAS_A_INVALID_TYPE));
        }});
    }
}
