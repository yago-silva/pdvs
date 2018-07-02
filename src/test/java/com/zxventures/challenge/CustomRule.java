package com.zxventures.challenge;

import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.base.Range;
import br.com.six2six.fixturefactory.function.AtomicFunction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import br.com.six2six.fixturefactory.function.Function;
import br.com.six2six.fixturefactory.function.impl.RandomFunction;

import static java.util.Arrays.asList;

public class CustomRule extends Rule {

    public Function randomDtoGeolocationCoordinates(){
        return new AtomicFunction() {
            @Override
            public List<BigDecimal> generateValue() {
                return asList(BigDecimal.valueOf(new Random().nextDouble()), BigDecimal.valueOf(new Random().nextDouble()));
            }
        };
    }

    public Function randomDtoPolygonCoordinates(){
        return new AtomicFunction() {
            @Override
            public  List<List<List<List<BigDecimal>>>> generateValue() {

                List<BigDecimal> point1 = asList(BigDecimal.valueOf(new Random().nextDouble()), BigDecimal.valueOf(new Random().nextDouble()));
                List<BigDecimal> point2 = asList(BigDecimal.valueOf(new Random().nextDouble()), BigDecimal.valueOf(new Random().nextDouble()));
                List<BigDecimal> point3 = asList(BigDecimal.valueOf(new Random().nextDouble()), BigDecimal.valueOf(new Random().nextDouble()));
                List<BigDecimal> point4 = asList(BigDecimal.valueOf(new Random().nextDouble()), BigDecimal.valueOf(new Random().nextDouble()));

                List<List<List<List<BigDecimal>>>> coordinates = new ArrayList<>();
                coordinates.add(asList(asList(point1, point2, point3, point4)));

                return coordinates;
            }
        };
    }

    public Function uuid(){
        return new AtomicFunction() {
            @Override
            public String generateValue() {
                return UUID.randomUUID().toString();
            }
        };
    }

    public Function cnpj(){
        return new AtomicFunction() {
            @Override
            public String generateValue() {
                RandomFunction random = new RandomFunction(Integer.class, new Range(1, 9));
                Integer a  = random.generateValue();
                Integer b  = random.generateValue();
                Integer c  = random.generateValue();
                Integer d  = random.generateValue();
                Integer e  = random.generateValue();
                Integer f  = random.generateValue();
                Integer g  = random.generateValue();
                Integer h  = random.generateValue();
                Integer i  = 0;
                Integer j = 0;
                Integer l = 0;
                Integer m = 1;

                int n = m*2+l*3+j*4+i*5+h*6+g*7+f*8+e*9+d*2+c*3+b*4+a*5;
                n = n % 11 < 2 ? 0 : 11 - (n % 11);

                int o = n*2+m*3+l*4+j*5+i*6+h*7+g*8+f*9+e*2+d*3+c*4+b*5+a*6;
                o = o % 11 < 2 ? 0 : 11 - (o % 11);

                return String.format("%d%d%d%d%d%d%d%d%d%d%d%d%d%d", a, b, c, d, e, f, g, h, i, j, l, m, n, o);
            }
        };
    }
}
