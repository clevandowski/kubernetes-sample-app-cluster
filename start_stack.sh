#!/bin/bash

kubectl create namespace app-cluster
if [ ! -f password.txt ]; then
  echo -n coincoin > password.txt
fi

kubectl -n app-cluster create secret generic mysql-pass --from-file=password.txt
kubectl -n app-cluster apply -f mysql-deployment.yaml --wait
kubectl -n app-cluster apply -f mysql-create-database-job.yaml --wait
kubectl -n app-cluster apply -f phpmyadmin-deployment.yaml
kubectl -n app-cluster apply -f resanet-tp-00-deployment.yaml
