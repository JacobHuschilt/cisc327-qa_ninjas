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
        echo "Running Test: $FILE:"
    done

    echo "All tests have been run!"
}

function validateTestResults {
   for FILE in $INPUT_DIR/*.txt; do
        echo "Validating otputs for test: $FILE"
        diff outputs/$FILE.txt expected/$FILE.txt
        diff outputs/$FILE.log expected/$FILE.log
    done
    
    echo "All tests have been validated!" 
}

runTests

validateTestResults

exit 0
