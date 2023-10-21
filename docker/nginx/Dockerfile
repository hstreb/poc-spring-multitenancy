FROM --platform=amd64 nginx:1.23.1

ARG OPENTELEMETRY_CPP_VERSION=1.0.3

ENV LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/opt/opentelemetry-webserver-sdk/sdk_lib/lib

ADD https://github.com/open-telemetry/opentelemetry-cpp-contrib/releases/download/webserver%2Fv${OPENTELEMETRY_CPP_VERSION}/opentelemetry-webserver-sdk-x64-linux.tgz /tmp

RUN apt-get update \
  && apt-get install -y --no-install-recommends dumb-init unzip \
  && tar xvfz /tmp/opentelemetry-webserver-sdk-x64-linux.tgz -C /opt \
  && rm -rf /tmp/opentelemetry-webserver-sdk-x64-linux.tgz \
  && /opt/opentelemetry-webserver-sdk/install.sh

EXPOSE 8085

STOPSIGNAL SIGQUIT