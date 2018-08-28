# Kubernetes Sample App Cluster
Spécial minikube

## Installation minikube+kubectl

```
https://kubernetes.io/docs/tasks/tools/install-minikube/
```

## Conf démarrage minikube

### Version VirtualBox
```
$ vboxheadless --version
Oracle VM VirtualBox Headless Interface 5.1.34
(C) 2008-2018 Oracle Corporation
All rights reserved.

5.1.34r121010
```

### Version kubectl
```
kubectl version
Client Version: version.Info{Major:"1", Minor:"10", GitVersion:"v1.10.1", GitCommit:"d4ab47518836c750f9949b9e0d387f20fb92260b", GitTreeState:"clean", BuildDate:"2018-04-12T14:26:04Z", GoVersion:"go1.9.3", Compiler:"gc", Platform:"linux/amd64"}
Server Version: version.Info{Major:"1", Minor:"10", GitVersion:"v1.10.0", GitCommit:"fc32d2f3698e36b93322a3465f63a14e9f0eaead", GitTreeState:"clean", BuildDate:"2018-03-26T16:44:10Z", GoVersion:"go1.9.3", Compiler:"gc", Platform:"linux/amd64"}
```

### Version Minikube
```
$ minikube version
minikube version: v0.26.0
```

### Démarrage Minikube
```
$ minikube start --vm-driver=virtualbox --cpus 4 --memory 8192
```

### Création namespace app-cluster

```
kubectl create namespace app-cluster
```

### Monitoring - Déploiement Heapster

```
minikube addons enable heapster
```

OU
```
git clone https://github.com/kubernetes/heapster
```
Installer heapster en suivant la doc avec InfluxDB
cf https://github.com/kubernetes/heapster/blob/master/docs/influxdb.md

### Logging - Déploiement ELK

- Prérequis: le namespace app-cluster doit être créé
- Prérequis ElasticSearch (dans la VM de minikube)
```
$ minikube ssh
$ sudo sysctl -w vm.max_map_count=262144
$ exit
```

Lister les pod:namespace:container (pour vérifier les logs)
```
$ minikube ssh
$ cd /var/log/containers
$ ls | sed -e "s|^\([^_]\+-[a-z0-9]\{5\}\)_\([^_]\+\)_\([^_]\+\)-[0-9a-f]\{64\}\.log$|\1:\2:\3|"
```

Démarrage d'elk
```
kubectl -n app-cluster apply -f elk-deployment.yaml
kubectl -n app-cluster expose deployment elk --type=NodePort
minikube service -n app-cluster elk --url
```
Prendre l'url relative au port 5601 de Kibana pour l'ouvrir dans l'explorateur

### Déploiement filebeat
```
kubectl -n app-cluster apply -f filebeat-deployment.yaml
```


## Cluster App


### Déploiement mysql

```
echo -n coincoin > password.txt
kubectl -n app-cluster create secret generic mysql-pass --from-file=password.txt
kubectl -n app-cluster apply -f mysql-deployment.yaml
```

Si besoin d'exposer mysql (debug)
```
kubectl -n app-cluster delete service mysql
kubectl -n app-cluster expose deployment mysql --type=NodePort
minikube service -n app-cluster mysql --url
```

### Déploiement phpmyadmin
```
kubectl -n app-cluster apply -f phpmyadmin-deployment.yaml
kubectl -n app-cluster expose deployment phpmyadmin --type=NodePort
minikube service -n app-cluster phpmyadmin --url
```

### Déploiement microservice "resanet-tp-00"
TODO: Expliquer le build + push registry
```
kubectl -n app-cluster apply -f resanet-tp-00-deployment.yaml
kubectl -n app-cluster expose deployment resanet-tp-00 --type=NodePort
minikube service -n app-cluster resanet-tp-00 --url
```
Note: Ajouter "/swagger-ui.html" à la fin de l'url retournée


## Pb: Erreur entre filebeat et logstash

### Log ELK:
```
[2018-04-10T20:46:02,535][WARN ][io.netty.channel.DefaultChannelPipeline] An exceptionCaught() event was fired, and it reached at the tail of the pipeline. It usually means the last handler in the pipeline did not handle the exception.
io.netty.handler.codec.DecoderException: javax.net.ssl.SSLHandshakeException: error:100000f7:SSL routines:OPENSSL_internal:WRONG_VERSION_NUMBER
  at io.netty.handler.codec.ByteToMessageDecoder.callDecode(ByteToMessageDecoder.java:459) ~[netty-all-4.1.18.Final.jar:4.1.18.Final]
  at io.netty.handler.codec.ByteToMessageDecoder.channelRead(ByteToMessageDecoder.java:265) ~[netty-all-4.1.18.Final.jar:4.1.18.Final]
  at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362) ~[netty-all-4.1.18.Final.jar:4.1.18.Final]
  at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348) ~[netty-all-4.1.18.Final.jar:4.1.18.Final]
  at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:340) ~[netty-all-4.1.18.Final.jar:4.1.18.Final]
  at io.netty.channel.DefaultChannelPipeline$HeadContext.channelRead(DefaultChannelPipeline.java:1359) ~[netty-all-4.1.18.Final.jar:4.1.18.Final]
  at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362) ~[netty-all-4.1.18.Final.jar:4.1.18.Final]
  at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348) ~[netty-all-4.1.18.Final.jar:4.1.18.Final]
  at io.netty.channel.DefaultChannelPipeline.fireChannelRead(DefaultChannelPipeline.java:935) ~[netty-all-4.1.18.Final.jar:4.1.18.Final]
  at io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:141) ~[netty-all-4.1.18.Final.jar:4.1.18.Final]
  at io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:645) ~[netty-all-4.1.18.Final.jar:4.1.18.Final]
  at io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:580) ~[netty-all-4.1.18.Final.jar:4.1.18.Final]
  at io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:497) ~[netty-all-4.1.18.Final.jar:4.1.18.Final]
  at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:459) [netty-all-4.1.18.Final.jar:4.1.18.Final]
  at io.netty.util.concurrent.SingleThreadEventExecutor$5.run(SingleThreadEventExecutor.java:858) [netty-all-4.1.18.Final.jar:4.1.18.Final]
  at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30) [netty-all-4.1.18.Final.jar:4.1.18.Final]
  at java.lang.Thread.run(Thread.java:748) [?:1.8.0_151]
Caused by: javax.net.ssl.SSLHandshakeException: error:100000f7:SSL routines:OPENSSL_internal:WRONG_VERSION_NUMBER
  at io.netty.handler.ssl.ReferenceCountedOpenSslEngine.sslReadErrorResult(ReferenceCountedOpenSslEngine.java:1120) ~[netty-all-4.1.18.Final.jar:4.1.18.Final]
  at io.netty.handler.ssl.ReferenceCountedOpenSslEngine.unwrap(ReferenceCountedOpenSslEngine.java:1080) ~[netty-all-4.1.18.Final.jar:4.1.18.Final]
  at io.netty.handler.ssl.ReferenceCountedOpenSslEngine.unwrap(ReferenceCountedOpenSslEngine.java:1146) ~[netty-all-4.1.18.Final.jar:4.1.18.Final]
  at io.netty.handler.ssl.ReferenceCountedOpenSslEngine.unwrap(ReferenceCountedOpenSslEngine.java:1189) ~[netty-all-4.1.18.Final.jar:4.1.18.Final]
  at io.netty.handler.ssl.SslHandler$SslEngineType$1.unwrap(SslHandler.java:216) ~[netty-all-4.1.18.Final.jar:4.1.18.Final]
  at io.netty.handler.ssl.SslHandler.unwrap(SslHandler.java:1248) ~[netty-all-4.1.18.Final.jar:4.1.18.Final]
  at io.netty.handler.ssl.SslHandler.decodeNonJdkCompatible(SslHandler.java:1171) ~[netty-all-4.1.18.Final.jar:4.1.18.Final]
  at io.netty.handler.ssl.SslHandler.decode(SslHandler.java:1196) ~[netty-all-4.1.18.Final.jar:4.1.18.Final]
  at io.netty.handler.codec.ByteToMessageDecoder.decodeRemovalReentryProtection(ByteToMessageDecoder.java:489) ~[netty-all-4.1.18.Final.jar:4.1.18.Final]
  at io.netty.handler.codec.ByteToMessageDecoder.callDecode(ByteToMessageDecoder.java:428) ~[netty-all-4.1.18.Final.jar:4.1.18.Final]
  ... 16 more
```

### Solution

cf https://github.com/logstash-plugins/logstash-input-beats/issues/293

Principe: Il faut trouver le crt de logstash, le pousser sur filebeat et le prendre en compte dans la conf

==> Emplacement du crt de logstash: se connecter sur le service elk
```
root@db873ed4ae5e:/# cat /etc/logstash/conf.d/02-beats-input.conf 
input {
  beats {
    port => 5044
    ssl => true
    ssl_certificate => "/etc/pki/tls/certs/logstash-beats.crt"
    ssl_key => "/etc/pki/tls/private/logstash-beats.key"
  }
}
```
==> Copier-coller le contenu de /etc/pki/tls/certs/logstash-beats.crt



```
root@filebeat-54kfd:/# cat /etc/filebeat/filebeat.yml
---
filebeat.prospectors: []
filebeat.modules:
  path: /etc/filebeat/modules.d/*.yml
  reload.enabled: false
filebeat.config.prospectors:
  enabled: true
  path: /etc/filebeat/conf.d/*.yml
filebeat.registry_file: /var/log/containers/filebeat_registry
logging.files:
  path: "/etc/filebeat/log"
logging.level: ${LOG_LEVEL}
output.logstash:
  enabled: true
  hosts: ["${LOGSTASH_HOSTS}"]
  index: "${INDEX_PREFIX}"
  loadbalance: true
  pipelining: 0
  save_topology: false
setup.kibana:
    host: "${LOGSTASH_HOSTS}"
root@filebeat-54kfd:/# echo $LOGSTASH_HOSTS 
elk:5044
```

```
$ cat filebeat/Dockerfile 
FROM komljen/filebeat

ADD filebeat.yml /etc/filebeat/filebeat.yml
ADD logstash-beats.crt /etc/filebeat/logstash-beats.crt
```

Build/push image
```
$ docker build -t clevandowski/filebeat .

$ docker push clevandowski/filebeat
```


## Annexe

### Commandes pour tester mysql

kubectl -n app-cluster run -it --rm mysql sh -c 'exec mysql -h"mysql" -P"3306" -uroot -p"coincoin"'

kubectl -n app-cluster run myadmin --link mysql_db_server:db --port 8080 phpmyadmin/phpmyadmin

kubectl -n app-cluster run --name myadmin -e PMA_HOST=mysql --port 80 phpmyadmin/phpmyadmin


kubectl -n app-cluster run phpmyadmin --env PMA_HOST=mysql --port 8080 --expose true --image phpmyadmin/phpmyadmin

mysql -h"mysql" -P"3306" -uroot -p"coincoin"

mysql -h"192.168.99.100" -P"32735" -uroot -p"coincoin"
http://192.168.99.100:32735/

Sous kops:
kubectl -n app-cluster run -it --image mysql mysql-client -- sh -c 'exec mysql -h"mysql" -P"3306" -uroot -pcoincoin'


### Conf ELK
```
sudo sysctl vm.max_map_count
sudo sysctl -w vm.max_map_count=262144
```

### Editer /etc/sysctl.d/vm_max_map_count.conf de la façon suivante
```
$ cat /etc/sysctl.d/vm_max_map_count.conf
vm.max_map_count=262144
```



### Métriques Mémoire de référence pour resanet-tp-00
```
cyrille@cyrille-XPS-15-9560:~$ kubectl -n app-cluster exec -ti resanet-tp-00-5d545f47b9-pcdqs -- ps -ef | grep "java -Xms" | grep -v grep | awk '{print $1}'
6
cyrille@cyrille-XPS-15-9560:~$ kubectl -n app-cluster exec -ti resanet-tp-00-5d545f47b9-pcdqs -- jstat -gc 6
 S0C    S1C    S0U    S1U      EC       EU        OC         OU       MC     MU    CCSC   CCSU   YGC     YGCT    FGC    FGCT     GCT   
5120.0 4608.0 1799.7  0.0   33792.0   3568.2   86016.0    31063.3   60120.0 58975.3 7424.0 7258.9     92    1.721   3      1.479    3.200
```
