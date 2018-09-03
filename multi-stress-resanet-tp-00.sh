#!/bin/bash

NB_THREADS=10
TIMEOUT=10
RESANET_SERVICE_ENDPOINT=http://192.168.99.100:30745

usage() {
  echo "Usage $0:"
  echo "\t-n NB_THREADS (défaut 10)"
  echo "\t-t TIMEOUT en seconde (défaut 10s)"
  echo "\t-s Adresse du service resanet (ex: http://a4e4245f3ab1311e8a1890e5ce83fc93-1143218904.eu-west-3.elb.amazonaws.com:8000)"
}

process_params() {

  while getopts "n:t:s:" option; do
    case $option in
      n)
        NB_THREADS=$OPTARG
        ;;
      t)
        TIMEOUT=$OPTARG
        ;;
      s)
        RESANET_SERVICE_ENDPOINT=$OPTARG
        ;;
      h)
        usage
        exit 0
        ;;
      :)
        echo "[$HOSTNAME] L'option $OPTARG requiert un argument"
        usage
        exit 1
        ;;
      \?)
        echo "[$HOSTNAME] $OPTARG: option invalide"
        usage
        exit 1
        ;;
    esac
  done
 
  shift $((OPTIND-1))
}

process_params "$@"

if [ ! -d logs ]; then 
  if ! mkdir logs; then
    echo "Impossible de créer le répertoire $PWD/logs."
    exit 1
  fi
fi

ls logs/curl-responses*.log >/dev/null 2>&1 && rm logs/curl-responses*.log
ls logs/curl-infos*.log >/dev/null 2>&1 && rm logs/curl-infos*.log
ls logs/curl-sent*.log >/dev/null 2>&1 && rm logs/curl-sent*.log
ls logs/curl-successful*.log >/dev/null 2>&1 && rm logs/curl-successful*.log
ls logs/curl-failed*.log >/dev/null 2>&1 && rm logs/curl-failed*.log
ls logs/time-output*.log >/dev/null 2>&1 && rm logs/time-output*.log

# Voir le nombre de threads effectifs:
# watch "ps -eaf | grep stress | grep -v grep | grep -v /bin/bash | wc -l"
i=0
echo "NB_THREADS=$NB_THREADS"
echo "TIMEOUT=$TIMEOUT"
while [ $i -lt $NB_THREADS ]; do
  timeout "$TIMEOUT"s ./stress-resanet-tp-00.sh $RESANET_SERVICE_ENDPOINT &
  i=$((i+1))
done
wait

ls logs/curl-sent*.log >/dev/null 2>&1 && echo "Requests sent: $(cat logs/curl-sent*.log | grep "^Request sent" | wc -l)"
ls logs/curl-successful*.log >/dev/null 2>&1 && echo "Requests successful: $(cat logs/curl-successful*.log | grep "^Request successful" | wc -l)"
ls logs/curl-failed*.log >/dev/null 2>&1 && echo "Requests failed: $(cat logs/curl-failed*.log | grep "^Request failed" | wc -l)"
