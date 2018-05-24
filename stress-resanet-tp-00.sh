#!/bin/bash

launch_request() {
  echo "Request sent" >> logs/curl-sent.$$.log
  # Le timeout sert à évacuer les requêtes curl en attente qui peuvent exploser la mémoire du système quand le service ne répond plus assez vite
  # Genre avec 35 threads, la mémoire monte de 2Go en 10s
  # Si on a + de 4 Go à allouer, on peut mettre un timeout à 20s
  if timeout 3s curl -v -f --header 'Accept: application/json' http://192.168.99.100:30745/villes/$(( ( RANDOM % 4 )  + 1 )) >> logs/curl-responses.$$.log 2>> logs/curl-infos.$$.log; then
    echo "Request successful" >> logs/curl-successful.$$.log
  else
    echo "Request failed (code $?)" >> logs/curl-failed.$$.log
  fi
}

start_stress_task() {
  while (true); do
    # date +%Y-%m-%d\ %H:%M:%S.%N >> logs/time-output.$$.log
#    (time (curl GET --header 'Accept: application/json' http://192.168.99.100:30745/villes/$(( ( RANDOM % 4 )  + 1 )) 2>/dev/null) | grep "^real") &
    (date +%Y-%m-%d\ %H:%M:%S.%N && time launch_request &) 1>> logs/time-output.$$.log 2>> logs/time-output.$$.log
    sleep .1
  done
}

start_stress_task
