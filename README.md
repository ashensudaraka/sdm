## Build Docker Image
```bash
docker build -t localhost:32000/em:latest .
```
## Push Docker Image
```bash
docker push localhost:32000/em:latest
```
## Deploy
```bash
kubectl apply -f em.yml
```
## Redeploy
```bash
kubectl delete -f em.yml && kubectl apply -f em.yml
```

docker build -t localhost:32000/em:latest . && docker push localhost:32000/em:latest && kubectl delete -f em.yml && kubectl apply -f em.yml