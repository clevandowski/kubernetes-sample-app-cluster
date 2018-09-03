
# Connextion bdd
kubectl -n app-cluster run -ti --rm --restart=Never --image=mysql mysql-cli -- /usr/bin/mysql -hmysql -P3306 -uroot -pcoincoin

# Création schéma resanet
create database if not exists resanet character set utf8;
