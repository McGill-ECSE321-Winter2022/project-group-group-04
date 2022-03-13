#!/bin/bash

bold=$(tput bold)
normal=$(tput sgr0)
red='\033[0;31m'
green='\033[0;32m'
yellow='\033[0;33m'
nc='\033[0m' # No Color

# Received data file
JSON_DATA="src/test/integration/results.json"

# Valid store owner info
ownerEmail="owner@email.com"
ownerName="owner"
ownerPassword="pwd"
adminCode="admin"

#Valid store info
storePhone="2222222222"
storeEmail="store@email.com"
storeOpeningHours="09:00:00"
storeClosingHours="17:00:00"
storeTown="TOWN"
storeStreet="STREET"
storeUnit="10"
storeOutOfTownFee="10"
storePostalCode="1X1X1X"

storeOwnerCreationTest () {
    curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/storeOwners?email=$ownerEmail&name=$ownerName&password=$ownerPassword&adminCode=$adminCode" > $JSON_DATA
    if [ ! -s $JSON_DATA ]
    then
        echo -e "  storeOwnerCreationTest: [ ${red}FAILED${nc} ] -> Application does not seem to be running on localhost:8080"
        return
    fi

    error=$(grep status $JSON_DATA) # If there is a 'status' field in the file, owner was not created
    if [ -z "$error" ]
    then
        echo -e "  storeOwnerCreationTest: [ ${green}PASSED${nc} ] "
    else
        echo -e "  storeOwnerCreationTest: [ ${red}FAILED${nc} ] -> $(cat $JSON_DATA)"
    fi
}

storeCreationTest () {
    curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/store?ownerEmail=$ownerEmail&ownerPassword=$ownerPassword&telephone=$storePhone&email=$storeEmail&openingHour=$storeOpeningHours&closingHour=$storeClosingHours&town=$storeTown&street=$storeStreet&postalcode=$storePostalCode&unit=$storeUnit&outoftownfee=$storeOutOfTownFee" > $JSON_DATA
    if [ ! -s $JSON_DATA ]
    then
        echo -e "  storeCreationTest: [ ${red}FAILED${nc} ] -> Application does not seem to be running on localhost:8080"
        return
    fi
    
    error=$(grep status $JSON_DATA) # If there is a 'status' field in the file, store was not created
    if [ -z "$error" ]
    then
        echo -e "  storeCreationTest: [ ${green}PASSED${nc} ] "
    else
        echo -e "  storeCreationTest: [ ${red}FAILED${nc} ] -> $(cat $JSON_DATA)"
    fi
}

echo -e "${bold}Initiating Integration tests...${normal}"
echo -e "      ${yellow}Make sure the application is running and connected to localhost:8080 to run the integration tests${nc}"
storeOwnerCreationTest
storeCreationTest

