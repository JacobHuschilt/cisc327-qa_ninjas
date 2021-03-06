#!/bin/bash

# Jacob Huschilt 
# ©2017 QA_Ninjas
# Last Modified: 11/29/2017

if (( $# != 3 )); then
    echo "Error: Need to pass 3 args: day number in week, path to frontend JAR file, path to backend JAR file" >&2
    exit 1
fi

echo "Daily Script"

# Command-line arguments
DAY_NUMBER=$1
PATH_TO_FRONT_END=$2
PATH_TO_BACK_END=$3

NUM_OF_SESSIONS=3

# File paths
TSF_FILENAME="transaction-summary"
MERGED_TSF_FILENAME="merged-tsf"
VALID_ACCOUNTS_FILENAME="valid-accounts.txt"
MASTER_ACCOUNTS_FILENAME="master-accounts.txt"
DAILY_OUTPUT_BASENAME="daily-output"
DAILY_INPUT_BASENAME="daily-input"

function runFrontEndSessions {
    for (( i=1; i<=$NUM_OF_SESSIONS; i++ )); do
        echo "Running Daily Session #$i for day $DAY_NUMBER:"
        echo "------------------------------------------------------------"

        java -jar $PATH_TO_FRONT_END $VALID_ACCOUNTS_FILENAME outputs/${TSF_FILENAME}${DAY_NUMBER}_${i}.txt inputs/${DAILY_INPUT_BASENAME}${DAY_NUMBER}_${i}.txt > outputs/${DAILY_OUTPUT_BASENAME}${DAY_NUMBER}_${i}.log 
    done
}

function mergeTSF {
    echo "Merging TSF Files"
    echo "----------------------------------------------------------------"
    touch outputs/${MERGED_TSF_FILENAME}${DAY_NUMBER}.txt

    for (( i=1; i<=$NUM_OF_SESSIONS; i++ )); do
	    FILE=outputs/${TSF_FILENAME}${DAY_NUMBER}_${i}.txt

	    if [ ! -f "$FILE" ]; then
		    echo "Error: File DNE!">&2
	    else
		    if [[ ! -s FILE ]]; then
			    cat $FILE >> outputs/${MERGED_TSF_FILENAME}${DAY_NUMBER}.txt
		    fi
	    fi
    done
}

function runBackOffice {
    echo "Running Back Office"
    echo "------------------------------------------------------------------"

    java -jar $PATH_TO_BACK_END $MASTER_ACCOUNTS_FILENAME outputs/${MERGED_TSF_FILENAME}${DAY_NUMBER}.txt $MASTER_ACCOUNTS_FILENAME $VALID_ACCOUNTS_FILENAME
}

# Main Program Execution
runFrontEndSessions
mergeTSF
runBackOffice

exit 0
