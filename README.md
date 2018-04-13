# Kubernetes Sample App Cluster
Spécial minikube

## Conf démarrage minikube

### Version VirtualBox
```
$ vboxheadless --version
Oracle VM VirtualBox Headless Interface 5.1.34
(C) 2008-2018 Oracle Corporation
All rights reserved.

5.1.34r121010
```

### Version Minikube
```
$ minikube version
minikube version: v0.25.2
```

### Démarrage Minikube
```
$ minikube start --vm-driver=virtualbox --cpus 4 --memory 8192
```

### Prérequis ElasticSearch

```
$ minikube ssh
$ sudo sysctl -w vm.max_map_count=262144
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

### Déploiement ELK
```
kubectl -n app-cluster apply -f elk-deployment.yaml
kubectl -n app-cluster expose deployment elk --type=NodePort
minikube service -n app-cluster elk --url
```

### Déploiement filebeat
```
kubectl -n app-cluster apply -f filebeat-deployment.yaml
```


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

# Monitoring
```
git clone https://github.com/kubernetes/heapster
```
Installer heapster en suivant la doc avec InfluxDB
cf https://github.com/kubernetes/heapster/blob/master/docs/influxdb.md

## Annexe

### Commandes pour tester mysql

kubectl -n app-cluster run -it --rm mysql sh -c 'exec mysql -h"mysql" -P"3306" -uroot -p"coincoin"'

kubectl -n app-cluster run myadmin --link mysql_db_server:db --port 8080 phpmyadmin/phpmyadmin

kubectl -n app-cluster run --name myadmin -e PMA_HOST=mysql --port 80 phpmyadmin/phpmyadmin


kubectl -n app-cluster run phpmyadmin --env PMA_HOST=mysql --port 8080 --expose true --image phpmyadmin/phpmyadmin

mysql -h"mysql" -P"3306" -uroot -p"coincoin"

mysql -h"192.168.99.100" -P"32735" -uroot -p"coincoin"
http://192.168.99.100:32735/




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
