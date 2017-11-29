#!/bin/bash

touch MergedTransactionSummary.txt

for (( i=1; i<=5; i++ )); do
	FILE="outputs/transaction-summary${i}.txt"

	if [ ! -f "$FILE" ]; then
		echo "Error: File DNE!"
	else
		if [[ ! -s FILE ]]; then
			cat "$FILE" >> MergedTransactionSummary.txt
			echo "" >> MergedTransactionSummary.txt
		fi
	fi
done

























