FROM java:8-jre-alpine

RUN rm -rf /var/lib/apt/lists/*
ENV LANG=pt_BR.UTF-8

RUN rm -rf /etc/localtime
RUN ln -s /usr/share/zoneinfo/UTC etc/localtime

ENV APP_HOME "/opt/app"

ADD build/libs/challenge-*.jar $APP_HOME/app.jar

CMD	java -jar /opt/app/app.jar