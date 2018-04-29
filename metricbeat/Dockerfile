# Repris sur https://github.com/joke/metricbeat


FROM frolvlad/alpine-glibc:alpine-3.4
#FROM alpine:3.7
WORKDIR /metricbeat

ARG VERSION=6.2.4
RUN apk update && apk add ca-certificates openssl && update-ca-certificates \
    && wget -O- https://artifacts.elastic.co/downloads/beats/metricbeat/metricbeat-${VERSION}-linux-x86_64.tar.gz | tar xz \
    && mv metricbeat-${VERSION}-linux-x86_64/* . && rm -rf metricbeat-${VERSION}-linux-x86_64 /var/cache/apk/*

ADD metricbeat.yml /metricbeat/metricbeat.yml
RUN chmod go-w /metricbeat/metricbeat.yml

ADD logstash-beats.crt /metricbeat/logstash-beats.crt
ADD jolokia.yml /metricbeat/modules.d/jolokia.yml
RUN chmod go-w /metricbeat/modules.d/jolokia.yml
RUN rm -f /metricbeat/modules.d/jolokia.yml.disabled

ENTRYPOINT [ "/metricbeat/metricbeat" ]
CMD ["-e", "-c", "/metricbeat/metricbeat.yml"]

