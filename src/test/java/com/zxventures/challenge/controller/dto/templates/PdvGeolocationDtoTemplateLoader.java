package com.zxventures.challenge.controller.dto.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.zxventures.challenge.CustomRule;
import com.zxventures.challenge.controller.dto.PdvGeolocationDto;

import java.math.BigDecimal;
import java.util.Arrays;

import static java.util.Arrays.asList;

public class PdvGeolocationDtoTemplateLoader implements TemplateLoader {

    public static final String VALID = "VALID";
    public static final String INVALID_BECAUSE_TYPE_IS_NULL = "INVALID_BECAUSE_TYPE_IS_NULL";
    public static final String INVALID_BECAUSE_TYPE_IS_BLANK = "INVALID_BECAUSE_TYPE_IS_BLANK";
    public static final String INVALID_BECAUSE_TYPE_IS_EMPTY = "INVALID_BECAUSE_TYPE_IS_EMPTY";
    public static final String INVALID_BECAUSE_HAS_A_INVALID_TYPE = "INVALID_BECAUSE_HAS_A_INVALID_TYPE";

    public static final String INVALID_BECAUSE_TOO_MANY_BIG_DECIMALS_IN_COORDINATES = "INVALID_BECAUSE_TOO_MANY_BIG_DECIMALS_IN_COORDINATES";
    public static final String INVALID_BECAUSE_COORDINATES_HAS_ONLY_ONE_BIG_DECIMAL = "INVALID_BECAUSE_COORDINATES_HAS_ONLY_ONE_BIG_DECIMAL";

    @Override
    public void load() {
        Fixture.of(PdvGeolocationDto.class).addTemplate(VALID, new CustomRule() {{
            add("type", "Point");
            add("coordinates", randomDtoGeolocationCoordinates());
        }});

        Fixture.of(PdvGeolocationDto.class).addTemplate(INVALID_BECAUSE_HAS_A_INVALID_TYPE).inherits(VALID, new CustomRule() {{
            add("type", "Multipolygon");
        }});

        Fixture.of(PdvGeolocationDto.class).addTemplate(INVALID_BECAUSE_TYPE_IS_NULL).inherits(VALID, new CustomRule() {{
            add("type", null);
        }});

        Fixture.of(PdvGeolocationDto.class).addTemplate(INVALID_BECAUSE_TYPE_IS_BLANK).inherits(VALID, new CustomRule() {{
            add("type", "    ");
        }});

        Fixture.of(PdvGeolocationDto.class).addTemplate(INVALID_BECAUSE_TYPE_IS_EMPTY).inherits(VALID, new CustomRule() {{
            add("type", "");
        }});

        Fixture.of(PdvGeolocationDto.class).addTemplate(INVALID_BECAUSE_TOO_MANY_BIG_DECIMALS_IN_COORDINATES).inherits(VALID, new CustomRule() {{
            add("coordinates", asList(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN));
        }});

        Fixture.of(PdvGeolocationDto.class).addTemplate(INVALID_BECAUSE_COORDINATES_HAS_ONLY_ONE_BIG_DECIMAL).inherits(VALID, new CustomRule() {{
            add("coordinates", asList(BigDecimal.TEN));
        }});
    }
}
