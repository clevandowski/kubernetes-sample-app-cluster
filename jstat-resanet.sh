#/bin/bash

pod_name=$(kubectl -n app-cluster get pods | grep resanet-tp-00 | awk '{print $1}')
# jdk < 10
# java_pid=$(kubectl -n app-cluster exec -ti $pod_name -- ps -eaf | grep "java \-XX" | grep -v grep | awk '{print $1}')

# jdk >= 10
java_pid=$(kubectl -n app-cluster exec -ti $pod_name -- ps -eaf | grep "java \-\-add-modules java.xml.bind" | grep -v grep | awk '{print $2}')
exec kubectl -n app-cluster exec -ti $pod_name -- jstat -gc -h 10 $java_pid 2s
