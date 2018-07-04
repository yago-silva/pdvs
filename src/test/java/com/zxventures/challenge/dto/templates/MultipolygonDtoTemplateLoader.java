package com.zxventures.challenge.dto.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.zxventures.challenge.CustomRule;
import com.zxventures.challenge.dto.create.CreateMultipolygonDto;

public class MultipolygonDtoTemplateLoader implements TemplateLoader {

    public static final String VALID = "VALID";
    public static final String INVALID_BECAUSE_TYPE_IS_NULL = "INVALID_BECAUSE_TYPE_IS_NULL";
    public static final String INVALID_BECAUSE_TYPE_IS_BLANK = "INVALID_BECAUSE_TYPE_IS_BLANK";
    public static final String INVALID_BECAUSE_TYPE_IS_EMPTY = "INVALID_BECAUSE_TYPE_IS_EMPTY";
    public static final String INVALID_BECAUSE_HAS_A_INVALID_TYPE = "INVALID_BECAUSE_HAS_A_INVALID_TYPE";

    @Override
    public void load() {
        Fixture.of(CreateMultipolygonDto.class).addTemplate(VALID, new CustomRule() {{
            add("type", "MultiPolygon");
            add("coordinates", randomDtoPolygonCoordinates());
        }});

        Fixture.of(CreateMultipolygonDto.class).addTemplate(INVALID_BECAUSE_TYPE_IS_NULL).inherits(VALID, new CustomRule() {{
            add("type", null);
        }});

        Fixture.of(CreateMultipolygonDto.class).addTemplate(INVALID_BECAUSE_TYPE_IS_BLANK).inherits(VALID, new CustomRule() {{
            add("type", "    ");
        }});

        Fixture.of(CreateMultipolygonDto.class).addTemplate(INVALID_BECAUSE_TYPE_IS_EMPTY).inherits(VALID, new CustomRule() {{
            add("type", "");
        }});

        Fixture.of(CreateMultipolygonDto.class).addTemplate(INVALID_BECAUSE_HAS_A_INVALID_TYPE).inherits(VALID, new CustomRule() {{
            add("type", "Point");
        }});
    }
}
