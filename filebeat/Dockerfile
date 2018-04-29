FROM komljen/filebeat

ADD filebeat.yml /etc/filebeat/filebeat.yml
RUN chmod go-w /etc/filebeat/filebeat.yml
ADD docker_app.yml /etc/filebeat/conf.d/docker_app.yml
RUN chmod 644 /etc/filebeat/conf.d/docker_app.yml
ADD logstash-beats.crt /etc/filebeat/logstash-beats.crt
