FROM docker.elastic.co/beats/filebeat:6.5.4

COPY filebeat.yml /usr/share/filebeat
USER root
RUN chown -R root /usr/share/filebeat/
RUN chmod -R go-w /usr/share/filebeat/