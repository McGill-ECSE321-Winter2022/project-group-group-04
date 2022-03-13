@echo off

SET bold=\033[1m
SET normal=\033[0m
SET red=\033[0;31m
SET green=\033[0;32m
SET yellow=\033[0;33m
SET nc=\033[0m

@REM # Received data file
SET JSON_DATA="src/test/integration/results.json"

@REM # Valid store owner info
SET ownerEmail="owner@email.com"
SET ownerName="owner"
SET ownerPassword="pwd"
SET adminCode="admin"

@REM #Valid store info
SET storePhone="2222222222"
SET storeEmail="store@email.com"
SET storeOpeningHours="09:00:00"
SET storeClosingHours="17:00:00"
SET storeTown="TOWN"
SET storeStreet="STREET"
SET storeUnit="10"
SET storeOutOfTownFee="10"
SET storePostalCode="1X1X1X"


ECHO -e "%bold%Initiating Integration tests...%normal%"
ECHO -e "      %yellow%Make sure the application is running and connected to localhost:8080 to run the integration tests%nc%"
CALL :storeOwnerCreationTest
CALL :storeCreationTest

:storeOwnerCreationTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/storeOwners?email=$ownerEmail&name=$ownerName&password=$ownerPassword&adminCode=$adminCode" > $JSON_DATA
for /f %%i in ("%JSON_DATA%") do set size=%%~zi
if %size% equ 0 echo -e "  storeOwnerCreationTest: [ %red%FAILED%nc% ] -> Application does not seem to be running on localhost:8080"

FOR /F "tokens=* USEBACKQ" %%F IN ("grep status %JSON_DATA%") DO (
SET error=%%F
)
if %error% neq "" (
    echo -e "  storeOwnerCreationTest: [ %green%PASSED%nc% ] "
) else (
    FOR /F "tokens=* USEBACKQ" %%F IN ("cat %JSON_DATA%") DO (
    SET error=%%F
    )
    echo -e "  storeOwnerCreationTest: [ %red%FAILED%nc% ] -> %error%"
)
EXIT /B 0

:storeCreationTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/store?ownerEmail=$ownerEmail&ownerPassword=$ownerPassword&telephone=$storePhone&email=$storeEmail&openingHour=$storeOpeningHours&closingHour=$storeClosingHours&town=$storeTown&street=$storeStreet&postalcode=$storePostalCode&unit=$storeUnit&outoftownfee=$storeOutOfTownFee" > $JSON_DATA
for /f %%i in ("%JSON_DATA%") do set size=%%~zi
if %size% equ 0 echo -e "  storeCreationTest: [ %red%FAILED%nc% ] -> Application does not seem to be running on localhost:8080"
FOR /F "tokens=* USEBACKQ" %%F IN ("grep status %JSON_DATA%") DO (
SET error=%%F
)
if %error% neq "" (
    echo -e "  storeCreationTest: [ %green%PASSED%nc% ] "
) else (
    FOR /F "tokens=* USEBACKQ" %%F IN ("cat %JSON_DATA%") DO (
    SET error=%%F
    )
    echo -e "  storeCreationTest: [ %red%FAILED%nc% ] -> %error%"
)
EXIT /B 0