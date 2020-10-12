#!/bin/sh
set -e

# first arg is `-f` or `--some-option`
if [ "${1#-}" != "$1" ]; then
    set -- haproxy "$@"
fi

if [ "$1" = 'haproxy' ]; then
    shift # "haproxy"
    # if the user wants "haproxy", let's add a couple useful flags
    #   -W  -- "master-worker mode" (similar to the old "haproxy-systemd-wrapper"; allows for reload via "SIGUSR2")
    #   -db -- disables background mode
    set -- haproxy -W -db "$@"
fi

#/consul agent -join=consul --data-dir=/tmp/x --config-file=/service.json &> /dev/stdout

/consul-template -consul-addr=consul:8500 -template="/tmp/haproxy.conf.ctmpl:/usr/local/etc/haproxy/haproxy.cfg:bash -c 'killall -9 haproxy; haproxy -f /usr/local/etc/haproxy/haproxy.cfg || true'"

exec "$@"

