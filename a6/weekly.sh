#!/bin/bash

# ©2017 QA_Ninjas
# Last Modified: 12/01/2017

if (( $# != 2 )); then
    echo "Error: Need to pass 2 args:path to frontend JAR file, path to backend JAR file" >&2
    exit 1
fi

echo "Weekly Script"

# Command-line arguments
PATH_TO_FRONT_END=$1
PATH_TO_BACK_END=$2

touch master-accounts.txt
touch valid-accounts.txt

# creates 5 days of QBASIC operation
for (( i=1; i <= 5; i++ )); do
    echo "================================================="
    echo "Running Day $i"
    echo "=================================================="

	./daily.sh $PATH_TO_FRONT_END $PATH_TO_BACK_END

    echo
    echo
done

exit 0

