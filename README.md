docker build -t registry.digitalocean.com/srtc/em:latest . && docker push registry.digitalocean.com/srtc/em:latest && kubectl delete -f em.yml && kubectl apply -f em.yml
docker build -t localhost:32000/em:latest . && docker push localhost:32000/em:latest && kubectl delete -f em.yml && kubectl apply -f em.yml
