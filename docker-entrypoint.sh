#!/bin/sh

touch /config/application-prod.properties

if [ -d /run/secrets/ ]; then
    for filename in /run/secrets/*; do
      echo "${filename##*/}"=`cat $filename`" >> /config/application-prod.properties"
    done
fi

# shellcheck disable=SC2145
echo "Properties created, running $@"

sh -c "$@"