#!/bin/bash

touch MasterAccountsFile.txt
touch ValidAccountsList.txt

# creates 5 days of QBASIC operation
for (( i=0; i < 5; i++ )); do
	./daily.sh #run daily script
done

exit 0

