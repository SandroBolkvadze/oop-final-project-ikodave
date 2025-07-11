FROM openjdk:21-slim

RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends \
      gcc \
      g++ \
      make \
      python3 \
      python3-pip \
      ca-certificates \
      procps \
      bash && \
    rm -rf /var/lib/apt/lists/*

RUN useradd -m -s /bin/bash sandboxuser

RUN mkdir -p /app && chown sandboxuser:sandboxuser /app

WORKDIR /app

CMD ["bash"]