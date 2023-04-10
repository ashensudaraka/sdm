docker buildx build -t salesreckon.jfrog.io/sdm-docker/em:1.0.1 --push . --platform linux/amd64,linux/arm64
docker build -t salesreckon.jfrog.io/sdm-docker/em:1.0.1 .