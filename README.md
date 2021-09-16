## Build Docker Image
```bash
docker build -t registry.digitalocean.com/tc-dev/em:latest .
```
## Push Docker Image
```bash
docker push registry.digitalocean.com/tc-dev/em:latest
```
## Deploy
```bash
kubectl apply -f em.yml
```
## Redeploy
```bash
kubectl delete -f em.yml && kubectl apply -f em.yml
```

docker build -t registry.digitalocean.com/tc-dev/em:latest . && docker push registry.digitalocean.com/tc-dev/em:latest && kubectl delete -f em.yml && kubectl apply -f em.yml