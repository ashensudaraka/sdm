docker build -t localhost:32000/em:1.0.1 . && docker push localhost:32000/em:1.0.1 && kubectl delete -f em.yml && kubectl apply -f em.yml
docker buildx build -t salesreckon.jfrog.io/sdm-docker/em:1.0.1 --push . --platform linux/amd64,linux/arm64
kubectl delete -f em.yml && kubectl apply -f em.yml