#/bin/bash

pod_name=$(kubectl -n app-cluster get pods | grep resanet-tp-00 | awk '{print $1}')
exec kubectl -n app-cluster logs -f $pod_name
