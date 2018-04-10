FROM komljen/filebeat

ADD filebeat.yml /etc/filebeat/filebeat.yml
RUN chmod go-w /etc/filebeat/filebeat.yml
ADD logstash-beats.crt /etc/filebeat/logstash-beats.crt
