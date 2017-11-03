#!/bin/bash

# CISC 327 Automated Testing Script
# ©2017 Jacob Huschilt
# Last Modified: 10/31/2017

if (( $# != 1 )); then
    echo "Error: Need to provide input directory containing test files"
    exit 1
fi

INPUT_DIR=$1

function runTests {
    for FILE in $INPUT_DIR/*.txt; do
        FILENAME="$(basename $FILE .txt)"
        echo "Running Test: $FILENAME:"
        OUTPUT_FILENAME="${FILENAME/Input/Output}"

        ./qbasic validaccounts.txt "outputs/${OUTPUT_FILENAME}.txt" $FILE > outputs/${OUTPUT_FILENAME}.log
    done

    echo "All tests have been run!"
}

function validateTestResults {
   for FILE in $INPUT_DIR/*.txt; do
        FILENAME="$(basename $FILE .txt)"
        echo "Validating otputs for test: $FILENAME"
        OUTPUT_FILENAME="${FILENAME/Input/Output}"
        EXPECTED_FILENAME="${FILENAME/Input/ExpectedOutput}"

        diff outputs/${OUTPUT_FILENAME}.txt expected/${EXPECTED_FILENAME}.txt
        diff outputs/${OUTPUT_FILENAME}.log expected/${EXPECTED_FILENAME}.log
    done
    
    echo "All tests have been validated!" 
}

runTests

validateTestResults

exit 0
