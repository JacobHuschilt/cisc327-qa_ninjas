#!/bin/bash

# Jacob Huschilt 
# ©2017 QA_Ninjas
# Last Modified: 11/29/2017

if [[ $# != 3 ]]; then
    echo "Error: Need to pass 3 args: number of sessions, path to frontend, path to backend"
    exit 1
fi

NUM_OF_SESSIONS=$1
PATH_TO_FRONT_END=$2
PATH_TO_BACK_END=$3

for (( i=0; i < $NUM_OF_SESSIONS; i++ )); do
   echo "Running Script $i"     
# TODO: Run the front end script $NUM_OF_SESSIONS times
done

# TODO: concatenate all TSF files into a mergedTSF file

# TODO: run the $PATH_TO_BACK_END script once


echo "Daily Script"

exit 0
