FROM openjdk:21-slim

RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends \
      gcc \
      g++ \
      make \
      python3 \
      python3-pip \
      ca-certificates && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

CMD ["bash"]