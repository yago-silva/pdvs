package com.zxventures.challenge.model.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.zxventures.challenge.model.Pdv;
import com.zxventures.challenge.model.Point;

public class PdvTemplateLoader implements TemplateLoader {

    public static final String VALID = "VALID";

    @Override
    public void load() {
        Fixture.of(Pdv.class).addTemplate(VALID, new Rule() {{
            add("id", random(Long.class, range(1, 10000000)));
            add("tradingName", name());
            add("ownerName", name());
            add("document", name());
            add("geolocation", one(Point.class, PointTemplateLoader.VALID));
        }});
    }
}