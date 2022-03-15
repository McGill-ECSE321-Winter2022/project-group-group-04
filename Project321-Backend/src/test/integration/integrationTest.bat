@echo off

@REM # Received data file
SET JSON_DATA=results.json

@REM # Valid store owner info
SET ownerEmail=ownerIntegrationTest@email.com
SET ownerName=owner
SET ownerPassword=ownerPwd
SET adminCode=admin

@REM # InValid store owner info
SET InvalidOwnerEmail=InvalidEmailNotInSystem@email.com
SET InvalidAdminCode=wrongKey

@REM # Valid employee info
SET employeeEmail=employeeIntegrationTest3@email.com
SET employeeName=employee
SET employeePassword=employeePwd
SET status=Active

@REM #Valid customer info
SET customerEmail=customerIntegrationTest3@email.com
SET customerName=customer
SET customerPassword=customerPwd
SET phone=4556846985
SET town=mtl
SET street=mcgill
SET postalcode=H1X1X1
SET unit=806

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

@REM #Shared date and hours infor for shift and timeslot tests
SET startHour=09:00:00
SET endHour=17:00:00
SET date=2022-02-05

@REM #Valid product info
SET type=PER_UNIT
SET productName=papaya3
SET online=yes
SET price=3
SET stock=60

@REM #Valid cart info
SET shoppingType=Delivery

@REM #Valid timeSlotinfo
SET quantity=1

echo INITIATING INTEGRATION TESTS...
echo       *Make sure the application is running and connected to localhost:8080 to run the integration tests*
:: Call the test methods here in order here
CALL :storeOwnerCreationTest
CALL :storeOwnerCreationWrongAdminKeyTest

CALL :storeCreationTest
CALL :storeCreationWrongOwnerInfoTest

CALL :employeeCreationTest
CALL :employeeCreationWrongOwnerInfoTest
CALL :listEmployeeTest
CALL :listEmployeeTest

CALL :shiftCreationTest
CALL :shiftCreationWrongOwnerInfoTest

CALL :customerCreationTest

CALL :cartCreationTest

CALL :productCreationTest

CALL :instorePurchaseCreationTest

CALL :timeSlotCreationTest
CALL :listTimeSlotTest

DEL %JSON_DATA%
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

:: Test method for the creation of a store owner with wrong admin code
:storeOwnerCreationWrongAdminKeyTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/storeOwners?email=%ownerEmail%&name=%ownerName%&password=%ownerPassword%&adminCode=%InvalidAdminCode%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo storeOwnerCreationWrongAdminKeyTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endStoreOwnerCreationWrongAdminKey
)
SET /p result=<%JSON_DATA%
findstr /m "500" %JSON_DATA%
if %errorlevel%==0 (
    echo   storeOwnerCreationWrongAdminKeyTest: [ PASSED ]
) else (
    echo   storeOwnerCreationWrongAdminKeyTest: [ FAILED ] - %result%
)
:endStoreOwnerCreationWrongAdminKey
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
    echo   storeCreationTest: [ FAILED ] - %result%
) else (
    echo   storeCreationTest: [ PASSED ] - %result%
)
:endStoreCreation
EXIT /B 0

:: Test method for the creation of a store with wrong owner info
:storeCreationWrongOwnerInfoTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/store?ownerEmail=%InvalidOwnerEmail%&ownerPassword=%ownerPassword%&telephone=%storePhone%&email=%storeEmail%&openingHour=%storeOpeningHours%&closingHour=%storeClosingHours%&town=%storeTown%&street=%storeStreet%&postalcode=%storePostalCode%&unit=%storeUnit%&outoftownfee=%storeOutOfTownFee%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo storeCreationWrongOwnerInfoTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endStoreCreationWrongOwnerInfo
)
SET /p result=<%JSON_DATA%
findstr /m "500" %JSON_DATA%
if %errorlevel%==0 (
    echo   storeCreationWrongOwnerInfoTest: [ PASSED ]
) else (
    echo   storeCreationWrongOwnerInfoTest: [ FAILED ] - %result%
)
:endStoreCreationWrongOwnerInfo
EXIT /B 0

:: Test method for the creation of a employee
:employeeCreationTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/employee?employeeEmail=%employeeEmail%&employeeName=%employeeName%&password=%employeePassword%&status=%status%&ownerEmail=%ownerEmail%&ownerPassword=%ownerPassword%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo employeeCreationTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endEmployeeCreationTest
)
SET /p result=<%JSON_DATA%
findstr /m "error" %JSON_DATA%
if %errorlevel%==0 (
    echo   employeeCreationTest: [ FAILED ] - %result%
) else (
    echo   employeeCreationTest: [ PASSED ] - %result%
)
:endEmployeeCreationTest
EXIT /B 0

:: Test method for the creation of a employee without owner's permission or owner's info is entered incorrectly
:employeeCreationWrongOwnerInfoTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/employee?employeeEmail=%employeeEmail%&employeeName=%employeeName%&password=%employeePassword%&status=%status%&ownerEmail=%InvalidOwnerEmail%&ownerPassword=%ownerPassword%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo employeeCreationWrongOwnerInfoTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endEmployeeCreationWrongOwnerInfoTest
)
SET /p result=<%JSON_DATA%
findstr /m "500" %JSON_DATA%
if %errorlevel%==0 (
    echo   employeeCreationWrongOwnerInfoTest: [ PASSED ]
) else (
    echo   employeeCreationWrongOwnerInfoTest: [ FAILED ] - %result%
)
:endEmployeeCreationWrongOwnerInfoTest
EXIT /B 0

:: Test method for list employees
:listEmployeeTest
curl -s -H "Content-Type: application/json" -X GET "http://localhost:8080/employees?ownerEmail=%ownerEmail%&ownerPassword=%ownerPassword%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo listEmployeeTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endListEmployeeTest
)
SET /p result=<%JSON_DATA%
findstr /m "%employeeEmail%" %JSON_DATA%
if %errorlevel%==0 (
    echo   listEmployeeTest: [ PASSED ] - %result%
) else (
    echo   listEmployeeTest: [ FAILED ] - %result%
)
:endListEmployeeTest
EXIT /B 0

:: Test method for the creation of a customer
:customerCreationTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/customers?email=%customerEmail%&name=%customerName%&password=%customerPassword%&phone=%phone%&town=%town%&street=%street%&postalcode=%postalcode%&unit=%unit%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo customerCreationTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endCustomerCreationTest
)
SET /p result=<%JSON_DATA%
findstr /m "error" %JSON_DATA%
if %errorlevel%==0 (
    echo   customerCreationTest: [ FAILED ] - %result%
) else (
    echo   customerCreationTest: [ PASSED ] - %result%
)
:endCustomerCreationTest
EXIT /B 0

:: Test method for the creation of a shift
:shiftCreationTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/shifts?startHour=%startHour%&endHour=%endHour%&date=%date%&employeeEmail=%employeeEmail%&ownerEmail=%ownerEmail%&ownerPassword=%ownerPassword%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo shiftCreationTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endShiftCreationTest
)
SET /p result=<%JSON_DATA%
findstr /m "error" %JSON_DATA%
if %errorlevel%==0 (
    echo   shiftCreationTest: [ FAILED ] - %result%
) else (
    echo   shiftCreationTest: [ PASSED ] - %result%
)
:endShiftCreationTest
EXIT /B 0

:: Test method for the creation of a shift
:shiftCreationWrongOwnerInfoTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/shifts?startHour=%startHour%&endHour=%endHour%&date=%date%&employeeEmail=%employeeEmail%&ownerEmail=%InvalidOwnerEmail%&ownerPassword=%ownerPassword%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo shiftCreationWrongOwnerInfoTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto shiftCreationWrongOwnerInfo
)
SET /p result=<%JSON_DATA%
findstr /m "error" %JSON_DATA%
findstr /m "500" %JSON_DATA%
if %errorlevel%==0 (
    echo   shiftCreationWrongOwnerInfoTest: [ PASSED ]
) else (
    echo   shiftCreationWrongOwnerInfoTest: [ FAILED ] - %result%
)
:shiftCreationWrongOwnerInfo
EXIT /B 0


:: Test method for the creation of a cart
:cartCreationTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/carts?type=%shoppingType%&customeremail=%customerEmail%&customerpassword=%customerPassword%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo cartCreationTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endCartCreationTest
)
SET /p result=<%JSON_DATA%
findstr /m "error" %JSON_DATA%
if %errorlevel%==0 (
    echo   cartCreationTest: [ FAILED ] - %result%
) else (
    echo   cartCreationTest: [ PASSED ] - %result%
)
:endCartCreationTest
EXIT /B 0

:: Test method for the creation of a product
:productCreationTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/products?type=%type%&productName=%productName%&online=%online%&price=%price%&stock=%stock%&ownerEmail=%ownerEmail%&ownerPassword=%ownerPassword%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo productCreationTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endProductCreation
)
SET /p result=<%JSON_DATA%
findstr /m "error" %JSON_DATA%
if %errorlevel%==0 (
    echo   productCreationTest: [ FAILED ] - %result%
) else (
    echo   productCreationTest: [ PASSED ] - %result%
)
:endProductCreation
EXIT /B 0

:: Test method for the creation of an instorePurchase
:instorePurchaseCreationTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/instorepurchase?useremail=%employeeEmail%&userpassword=%employeePassword%&productname=%productname%&quantity=%quantity%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo instorePurchaseCreationTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endInstorePurchaseCreation
)
SET /p result=<%JSON_DATA%
findstr /m "error" %JSON_DATA%
if %errorlevel%==0 (
    echo   instorePurchaseCreationTest: [ FAILED ] - %result%
) else (
    echo   instorePurchaseCreationTest: [ PASSED ] - %result%
)
:endInstorePurchaseCreation
EXIT /B 0

:: Test method for the creation of an timeSlot
:timeSlotCreationTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/timeslot?timeslotdate=%date%&timeslotstarttime=%startHour%&timeslotendtime=%endHour%&timeslotdate=%date%&ownerEmail=%ownerEmail%&ownerPassword=%ownerPassword%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo timeSlotCreationTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endTimeSlotCreation
)
SET /p result=<%JSON_DATA%
findstr /m "error" %JSON_DATA%
if %errorlevel%==0 (
    echo   timeSlotCreationTest: [ FAILED ] - %result%
) else (
    echo   timeSlotCreationTest: [ PASSED ] - %result%
)
:endTimeSlotCreation
EXIT /B 0

:: Test method for list timeSlots
:listTimeSlotTest
curl -s -H "Content-Type: application/json" -X GET "http://localhost:8080/availabletimeslots" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo listTimeSlotTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endListTimeSlot
)
SET /p result=<%JSON_DATA%
findstr /m "%date%" %JSON_DATA%
findstr /m "%startHour%" %JSON_DATA%
findstr /m "%endHour%" %JSON_DATA%
if %errorlevel%==0 (
    echo   listTimeSlotTest: [ PASSED ] - %result%
) else (
    echo   listTimeSlotTest: [ FAILED ] - %result%
)
:endListTimeSlot
EXIT /B 0
