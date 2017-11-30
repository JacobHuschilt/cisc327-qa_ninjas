#!/bin/bash

# Jacob Huschilt 
# ©2017 QA_Ninjas
# Last Modified: 11/29/2017

if (( $# != 2 )); then
    echo "Error: Need to pass 2 args:path to frontend, path to backend" >&2
    exit 1
fi

echo "Daily Script"

# Command-line arguments
NUM_OF_SESSIONS=3
PATH_TO_FRONT_END=$2
PATH_TO_BACK_END=$3

# File paths
TSF_PATH="transaction-summary"
MERGED_TSF_PATH="merged-tsf.txt"
VALID_ACCOUNTS_PATH="valid-accounts.txt"
MASTER_ACCOUNTS_PATH="master-accounts.txt"
DAILY_OUTPUT_BASENAME="daily-output"
DAILY_INPUT_BASENAME="daily-input"
NEW_VALID_ACCOUNTS_PATH="new-valid-accounts.txt"
NEW_MASTER_ACCOUNTS_PATH="new-master-accounts.txt"

# Run the front-end program $NUM_OF_SESSIONS times
for (( i=1; i <= $NUM_OF_SESSIONS; i++ )); do
    echo "------------------------------------------------------------"
    echo "Running Daily Session #$i:"
    echo "------------------------------------------------------------"

    java -jar cisc327-qa_ninjas.jar $VALID_ACCOUNTS_PATH outputs/${TSF_PATH}${i}.txt inputs/${DAILY_INPUT_BASENAME}${i}.txt > outputs/${DAILY_OUTPUT_BASENAME}${i}.log 
done

# TODO: concatenate all TSF files into a mergedTSF file

# Runs back-end script
# TODO: package JAR files for front end and back end into convenient locations inside the a6 folder
java -jar qa_ninjas-back-office.jar $MASTER_ACCOUNTS_PATH outputs/$MERGED_TSF_PATH $MASTER_ACCOUNTS_PATH $VALID_ACCOUNTS_PATH

exit 0
