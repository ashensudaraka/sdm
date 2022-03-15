docker build -t localhost:32000/em:latest . && docker push localhost:32000/em:latest && kubectl delete -f em.yml && kubectl apply -f em.yml
docker build -t salesreckon.jfrog.io/sdm-docker/em:latest . && docker push salesreckon.jfrog.io/sdm-docker/em:latest
kubectl delete -f em.yml && kubectl apply -f em.yml