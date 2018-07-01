package com.zxventures.challenge.model.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.zxventures.challenge.model.Point;

import java.math.BigDecimal;

public class PointTemplateLoader implements TemplateLoader {

    public static final String VALID = "VALID";

    @Override
    public void load() {
        Fixture.of(Point.class).addTemplate(VALID, new Rule() {{
            add("x", random(BigDecimal.class, range(1, 10000000)));
            add("y", random(BigDecimal.class, range(1, 10000000)));
        }});
    }
}
