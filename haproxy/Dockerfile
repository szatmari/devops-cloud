FROM haproxy

RUN apt-get update && apt-get install -y unzip wget rsyslog

RUN wget https://releases.hashicorp.com/consul/1.6.1/consul_1.6.1_linux_amd64.zip && \
    unzip consul_1.6.1_linux_amd64.zip
RUN wget https://releases.hashicorp.com/consul-template/0.22.0/consul-template_0.22.0_linux_amd64.zip &&\
    unzip consul-template_0.22.0_linux_amd64.zip

ADD service.json /
ADD docker-entrypoint.sh /
RUN chmod +x /docker-entrypoint.sh

ADD rsyslog.conf /etc/

ADD haproxy.conf.ctmpl /tmp/haproxy.conf.ctmpl

