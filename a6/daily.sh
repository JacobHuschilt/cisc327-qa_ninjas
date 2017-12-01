#!/bin/bash

# Jacob Huschilt 
# ©2017 QA_Ninjas
# Last Modified: 11/29/2017

if (( $# != 2 )); then
    echo "Error: Need to pass 2 args:path to frontend JAR file, path to backend JAR file" >&2
    exit 1
fi

echo "Daily Script"

# Command-line arguments
PATH_TO_FRONT_END=$1
PATH_TO_BACK_END=$2

NUM_OF_SESSIONS=3

# File paths
TSF_FILENAME="transaction-summary"
MERGED_TSF_FILENAME="merged-tsf.txt"
VALID_ACCOUNTS_FILENAME="valid-accounts.txt"
MASTER_ACCOUNTS_FILENAME="master-accounts.txt"
DAILY_OUTPUT_BASENAME="daily-output"
DAILY_INPUT_BASENAME="daily-input"

function runFrontEndSessions {
    for (( i=1; i<=$NUM_OF_SESSIONS; i++ )); do
        echo "Running Daily Session #$i:"
        echo "------------------------------------------------------------"

        java -jar $PATH_TO_FRONT_END $VALID_ACCOUNTS_FILENAME outputs/${TSF_FILENAME}${i}.txt inputs/${DAILY_INPUT_BASENAME}${i}.txt > outputs/${DAILY_OUTPUT_BASENAME}${i}.log 
    done
}

function mergeTSF {
    echo "Merging TSF Files"
    echo "----------------------------------------------------------------"
    touch outputs/$MERGED_TSF_FILENAME

    for (( i=1; i<=$NUM_OF_SESSIONS; i++ )); do
	    FILE=outputs/${TSF_FILENAME}${i}.txt

	    if [ ! -f "$FILE" ]; then
		    echo "Error: File DNE!">&2
	    else
		    if [[ ! -s FILE ]]; then
			    cat $FILE >> outputs/${MERGED_TSF_FILENAME}
		    fi
	    fi
    done
    #for FILE in outputs/${TSF_PATH}?*.txt; do
     #   cat $FILE >> outputs/$MERGED_TSF_FILENAME
    #done
}

function runBackOffice {
    echo "Running Back Office"
    echo "------------------------------------------------------------------"

    java -jar $PATH_TO_BACK_END $MASTER_ACCOUNTS_FILENAME outputs/$MERGED_TSF_FILENAME $MASTER_ACCOUNTS_FILENAME $VALID_ACCOUNTS_FILENAME
}

# Main Program Execution
runFrontEndSessions
mergeTSF
runBackOffice
# remove the merged tsf file
rm outputs/$MERGED_TSF_FILENAME

exit 0
