#!/bin/bash
function tabname {
  echo -n -e "\033]0;$1\007"
}
tabname "oscars core"

java -Xmx512m -jar target/core-1.0.0-beta.jar $1 $2 $3 $4 $5
