@echo off

@REM # Received data file
SET JSON_DATA=results.json

@REM # Valid store owner info
SET ownerEmail=owner@email.com
SET ownerName=owner
SET ownerPassword=pwd
SET adminCode=admin

@REM #Valid store info
SET storePhone=2222222222
SET storeEmail=store@email.com
SET storeOpeningHours=09:00:00
SET storeClosingHours=17:00:00
SET storeTown=TOWN
SET storeStreet=STREET
SET storeUnit=10
SET storeOutOfTownFee=10
SET storePostalCode=1X1X1X

echo INITIATING INTEGRATION TESTS...
echo       *Make sure the application is running and connected to localhost:8080 to run the integration tests*
:: Call the test methods here in order here
CALL :storeOwnerCreationTest
CALL :storeCreationTest
EXIT /B %ERRORLEVEL%

:: Test method for the creation of a store owner
:storeOwnerCreationTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/storeOwners?email=%ownerEmail%&name=%ownerName%&password=%ownerPassword%&adminCode=%adminCode%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo storeOwnerCreationTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endStoreOwnerCreation
)
SET /p result=<%JSON_DATA%
findstr /m "error" %JSON_DATA%
if %errorlevel%==0 (
    echo   storeOwnerCreationTest: [ FAILED ] - %result%
) else (
    echo   storeOwnerCreationTest: [ PASSED ] - %result%
)
:endStoreOwnerCreation
EXIT /B 0

:: Test method for the creation of a store
:storeCreationTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/store?ownerEmail=%ownerEmail%&ownerPassword=%ownerPassword%&telephone=%storePhone%&email=%storeEmail%&openingHour=%storeOpeningHours%&closingHour=%storeClosingHours%&town=%storeTown%&street=%storeStreet%&postalcode=%storePostalCode%&unit=%storeUnit%&outoftownfee=%storeOutOfTownFee%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo storeCreationTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endStoreCreation
)
SET /p result=<%JSON_DATA%
findstr /m "error" %JSON_DATA%
if %errorlevel%==0 (
    echo   storeOwnerCreationTest: [ FAILED ] - %result%
) else (
    echo   storeOwnerCreationTest: [ PASSED ] - %result%
)
:endStoreCreation
EXIT /B 0
