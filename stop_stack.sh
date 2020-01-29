kubectl -n app-cluster delete -f resanet-tp-00-deployment.yaml
kubectl -n app-cluster delete -f phpmyadmin-deployment.yaml
kubectl -n app-cluster delete -f mysql-create-database-job.yaml --wait
kubectl -n app-cluster delete -f mysql-deployment.yaml --wait
kubectl -n app-cluster delete secret mysql-pass
