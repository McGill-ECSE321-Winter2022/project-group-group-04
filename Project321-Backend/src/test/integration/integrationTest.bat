@echo off

:: All the tests will pass if there are no duplicate objct in side the database.
:: repetitive running of this script without change the primary key of the objct will trigger our safeguard in controller to prevent duplicated object being created and thus fail some tests.
@REM # Received data file
SET JSON_DATA=results.json

@REM # Valid store owner info
SET ownerEmail=owner@email.com
SET ownerName=owner
SET ownerPassword=ownerPwd
SET adminCode=admin

@REM # InValid store owner info
SET InvalidOwnerEmail=InvalidEmailNotInSystem@email.com
SET InvalidAdminCode=wrongKey

@REM # Valid employee info
SET employeeEmail=employee@email.com
SET employeeName=employee
SET employeePassword=employeePwd
SET status=Active

@REM #Valid customer info
SET customerEmail=customer@email.com
SET customerName=customer
SET customerPassword=customerPwd
SET phone=4556846985
SET town=mtl
SET street=mcgill
SET postalcode=H1X1X1
SET unit=806

SET town2=mtl
SET street2=mcgill2
SET postalcode2=H1X1X2
SET unit2=917

@REM #Valid store info
SET storePhone=2222222222
SET storeEmail=store@email.com
SET storeOpeningHours=09:00:00
SET storeClosingHours=17:00:00
SET newStoreOpeningHours=08:00:00
SET newStoreClosingHours=19:00:00
SET storeTown=Test
SET storeStreet=Test
SET storeUnit=101
SET storeOutOfTownFee=10
SET storePostalCode=1X1X1X

@REM #date and hours info for shift
SET startHour=09:00:00
SET endHour=17:00:00
SET date=2022-03-01

@REM #date and hours info for timslot
SET startHour2=10:00:00
SET endHour2=12:00:00

SET startHour3=13:00:00
SET endHour3=15:00:00

@REM #Valid product info
SET type=PER_UNIT
SET productName=chocolate
SET productName2=Butter
SET productName3=Rice
SET online=no
SET online2=yes
SET newOnline=yes
SET price=3
SET price2=5
SET stock=60
SET newStock=100
SET stock3=0

@REM #Invalid product info
SET invalidStock=-10

@REM #Valid cart info
SET shoppingType=Delivery

@REM #Valid timeSlotinfo
SET quantity=1

@REM #Valid cartItem Info
SET purchaseQuantity=10
SET purchaseQuantity2=20

@REM #Valid purcase/Order Info
SET /a sum="purchaseQuantity * price + purchaseQuantity2 * price2 + storeOutOfTownFee"

echo INITIATING INTEGRATION TESTS...
echo       *Make sure the application is running and connected to localhost:8080 to run the integration tests*
:: Call the test methods here in order here
CALL :storeOwnerCreationWrongAdminKeyTest
CALL :storeOwnerCreationTest

CALL :storeCreationTest
CALL :storeCreationWrongOwnerInfoTest
CALL :changeStoreHoursTest

CALL :employeeCreationTest
CALL :employeeCreationWrongOwnerInfoTest
CALL :duplicateEmployeeCreationTest
CALL :listEmployeeTest

CALL :shiftCreationTest
CALL :employeeViewShiftTest
CALL :duplicateShiftCreationTest
CALL :shiftCreationWrongOwnerInfoTest

CALL :customerCreationTest
CALL :duplicateCustomerCreationTest
CALL :changeCustomerAddressTest

CALL :cartCreationTest
CALL :duplicateCartCreationTest

CALL :productCreationTest
CALL :productCreationTest2
CALL :productCreationTest3
CALL :listProductCustomerViewTest
CALL :listProductStaffViewTest
CALL :duplicateProductCreationTest
CALL :changeProductStockTest
CALL :changeProductStockNegativeTest
CALL :changeProductAvailabilityTest
CALL :deleteProductTest

CALL :instorePurchaseCreationTest

CALL :timeSlotCreationTest
CALL :timeSlotCreationTest2
CALL :duplicateTimeSlotCreationTest
CALL :listTimeSlotTest
CALL :deleteTimeSlotTest
CALL :addTimeSlotToCartTest

CALL :addItemToCartTest
CALL :addItemToCartTest2
CALL :getCartToalTest
CALL :checkoutTest



DEL %JSON_DATA%
EXIT /B %ERRORLEVEL%


:: Test method for the creation of a store owner
:storeOwnerCreationTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/storeOwners?email=%ownerEmail%&name=%ownerName%&password=%ownerPassword%&adminCode=%adminCode%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo storeOwnerCreationTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endStoreOwnerCreation
)
:: If String error is found in the database's response, the test fails.
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

:: Test method for the creation of a store owner with wrong admin code
:storeOwnerCreationWrongAdminKeyTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/storeOwners?email=%ownerEmail%&name=%ownerName%&password=%ownerPassword%&adminCode=%InvalidAdminCode%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo storeOwnerCreationWrongAdminKeyTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endStoreOwnerCreationWrongAdminKey
)
:: If error 500 is found in the database's response when we only change the admin key, test passed as the safeguard works.
SET /p result=<%JSON_DATA%
findstr /m "500" %JSON_DATA%
if %errorlevel%==0 (
    echo   storeOwnerCreationWrongAdminKeyTest: [ PASSED ]
) else (
    echo   storeOwnerCreationWrongAdminKeyTest: [ FAILED ] - %result%
)
:endStoreOwnerCreationWrongAdminKey
EXIT /B 0


:: Test method for the change in store hours
:changeStoreHoursTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/store/changeHours?ownerEmail=%ownerEmail%&ownerPassword=%ownerPassword%&openingHour=%newStoreOpeningHours%&closingHour=%newStoreClosingHours%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo changeStoreHoursTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endChangeStoreHoursTest
)
:: If the new hours are contained in the DTO returned by the database, change is done correctly and test is passed
SET /p result=<%JSON_DATA%
findstr /m "%newStoreOpeningHours%" %JSON_DATA%
if %errorlevel% GTR 0 (
    echo   changeStoreHoursTest: [ FAILED ] - %result%
    goto endChangeStoreHoursTest
)
findstr /m "%newStoreClosingHours%" %JSON_DATA%
if %errorlevel%==0 (
    echo   changeStoreHoursTest: [ PASSED ] - %result%
) else (
    echo   changeStoreHoursTest: [ FAILED ] - %result%
)
:endChangeStoreHoursTest
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

:: Test method safeguard for the creation of a duplcate employee
:duplicateEmployeeCreationTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/employee?employeeEmail=%employeeEmail%&employeeName=%employeeName%&password=%employeePassword%&status=%status%&ownerEmail=%ownerEmail%&ownerPassword=%ownerPassword%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo duplicateEmployeeCreationTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endDuplicateEmployeeCreation
)
SET /p result=<%JSON_DATA%
findstr /m "500" %JSON_DATA%
if %errorlevel%==0 (
    echo   duplicateEmployeeCreationTest: [ PASSED ]
) else (
    echo   duplicateEmployeeCreationTest: [ FAILED ] - %result%
)
:endDuplicateEmployeeCreation
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

:: Test method for the creation of a customer
:changeCustomerAddressTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/customers/address?customeremail=%customerEmail%&customerpassword=%customerPassword%&town=%town2%&street=%street2%&postalcode=%postalcode2%&unit=%unit2%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo changeCustomerAddressTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endChangeCustomerAddressTest
)
SET /p result=<%JSON_DATA%
findstr /m "%town2%" %JSON_DATA%
if not %errorlevel% == 0 (
    echo   changeCustomerAddressTest: [ FAILED ] - %result%
    goto endChangeCustomerAddressTest
)
findstr /m "%street2%" %JSON_DATA%
if not %errorlevel% == 0 (
    echo   changeCustomerAddressTest: [ FAILED ] - %result%
    goto endChangeCustomerAddressTest
)
findstr /m "%postalcode2%" %JSON_DATA%
if %errorlevel%==0 (
    echo   changeCustomerAddressTest: [ PASSED ] - %result%
) else (
    echo   changeCustomerAddressTest: [ FAILED ] - %result%
)
:endChangeCustomerAddressTest
EXIT /B 0

:: Test method safeguard for the creation of a duplicate customer
:duplicateCustomerCreationTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/customers?email=%customerEmail%&name=%customerName%&password=%customerPassword%&phone=%phone%&town=%town%&street=%street%&postalcode=%postalcode%&unit=%unit%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo duplicateCustomerCreationTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endDuplicateCustomerCreation
)
SET /p result=<%JSON_DATA%
findstr /m "500" %JSON_DATA%
if %errorlevel%==0 (
    echo   duplicateCustomerCreationTest: [ PASSED ]
) else (
    echo   duplicateCustomerCreationTest: [ FAILED ] - %result%
)
:endDuplicateCustomerCreation
EXIT /B 0


:: Test method for the creation of a shift for a employee
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


:: Test method safeguard for the creation of a shift with same date and overlapping start and endHour
:duplicateShiftCreationTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/shifts?startHour=%startHour%&endHour=%endHour%&date=%date%&employeeEmail=%employeeEmail%&ownerEmail=%ownerEmail%&ownerPassword=%ownerPassword%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo duplicateShiftCreationTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endDuplicateShiftCreationTest
)
SET /p result=<%JSON_DATA%
findstr /m "500" %JSON_DATA%
if %errorlevel%==0 (
    echo   duplicateShiftCreationTest: [ PASSED ]
) else (
    echo   duplicateShiftCreationTest: [ FAILED ] - %result%
)
:endDuplicateShiftCreationTest
EXIT /B 0

:: Test method for the creation of a shift
:shiftCreationWrongOwnerInfoTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/shifts?startHour=%startHour%&endHour=%endHour%&date=%date%&employeeEmail=%employeeEmail%&ownerEmail=%InvalidOwnerEmail%&ownerPassword=%ownerPassword%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo shiftCreationWrongOwnerInfoTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto shiftCreationWrongOwnerInfo
)
SET /p result=<%JSON_DATA%
findstr /m "500" %JSON_DATA%
if %errorlevel%==0 (
    echo   shiftCreationWrongOwnerInfoTest: [ PASSED ]
) else (
    echo   shiftCreationWrongOwnerInfoTest: [ FAILED ] - %result%
)
:shiftCreationWrongOwnerInfo
EXIT /B 0

:: Test method for employee to view his/her shift
:employeeViewShiftTest
curl -s -H "Content-Type: application/json" -X GET "http://localhost:8080/shifts/myshifts?password=%employeePassword%&email=%employeeEmail%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo employeeViewShiftTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endEmployeeViewShiftTest
)
SET /p result=<%JSON_DATA%
findstr /m "%date%" %JSON_DATA%
if not %errorlevel% == 0 (
    echo   employeeViewShiftTest: [ FAILED ] - %result%
    goto endEmployeeViewShiftTest
)
findstr /m "%startHour%" %JSON_DATA%
if not %errorlevel% == 0 (
    echo   employeeViewShiftTest: [ FAILED ] - %result%
    goto endEmployeeViewShiftTest
)
findstr /m "%endHour%" %JSON_DATA%
if %errorlevel%==0 (
    echo   employeeViewShiftTest: [ PASSED ] - %result%
) else (
    echo   employeeViewShiftTest: [ FAILED ] - %result%
)
:endEmployeeViewShiftTest
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

:: Test method safeguard for the creation of a duplicate product
:duplicateProductCreationTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/products?type=%type%&productName=%productName%&online=%online%&price=%price%&stock=%stock%&ownerEmail=%ownerEmail%&ownerPassword=%ownerPassword%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo duplicateProductCreationTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endDuplicateProductCreationTest
)
SET /p result=<%JSON_DATA%
findstr /m "500" %JSON_DATA%
if %errorlevel%==0 (
    echo   duplicateProductCreationTest: [ PASSED ]
) else (
    echo   duplicateProductCreationTest: [ FAILED ] - %result%
)
:endDuplicateProductCreationTest
EXIT /B 0

:: Test method safeguard for the creation of a additional cart to a customer
:duplicateCartCreationTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/carts?type=%shoppingType%&customeremail=%customerEmail%&customerpassword=%customerPassword%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo duplicateCartCreationTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endDuplicateCartCreationTest
)
SET /p result=<%JSON_DATA%
findstr /m "500" %JSON_DATA%
if %errorlevel%==0 (
    echo   duplicateCartCreationTest: [ PASSED ]
) else (
    echo   duplicateCartCreationTest: [ FAILED ] - %result%
)
:endDuplicateCartCreationTest
EXIT /B 0

:: Test method for the creation of a product
:productCreationTest2
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/products?type=%type%&productName=%productName2%&online=%online2%&price=%price2%&stock=%stock%&ownerEmail=%ownerEmail%&ownerPassword=%ownerPassword%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo productCreationTest2: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endProductCreation2
)
SET /p result=<%JSON_DATA%
findstr /m "error" %JSON_DATA%
if %errorlevel%==0 (
    echo   productCreationTest2: [ FAILED ] - %result%
) else (
    echo   productCreationTest2: [ PASSED ] - %result%
)
:endProductCreation2
EXIT /B 0

:: Test method for the creation of a product
:productCreationTest3
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/products?type=%type%&productName=%productName3%&online=%online2%&price=%price2%&stock=%stock3%&ownerEmail=%ownerEmail%&ownerPassword=%ownerPassword%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo productCreationTest3: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endProductCreation3
)
SET /p result=<%JSON_DATA%
findstr /m "error" %JSON_DATA%
if %errorlevel%==0 (
    echo   productCreationTest3: [ FAILED ] - %result%
) else (
    echo   productCreationTest3: [ PASSED ] - %result%
)
:endProductCreation3
EXIT /B 0

:: Test method for the view products test with staff, staff should have all info of products so they can see all products including product 3 with 0 stock
:listProductStaffViewTest
curl -s -H "Content-Type: application/json" -X GET "http://localhost:8080/products?customerPage=false" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo listProductStaffViewTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endListProductStaffViewTest
)
SET /p result=<%JSON_DATA%
findstr /m "%productName%" %JSON_DATA%
if not %errorlevel% == 0 (
    echo   listProductStaffViewTest: [ FAILED ] - %result%
    goto endListProductStaffViewTest
)
findstr /m "%productName2%" %JSON_DATA%
if not %errorlevel% == 0 (
    echo   listProductStaffViewTest: [ FAILED ] - %result%
    goto endListProductStaffViewTest
)
findstr /m "%productName3%" %JSON_DATA%
if %errorlevel%==0 (
    echo   listProductStaffViewTest: [ PASSED ] - %result%
) else (
    echo   listProductStaffViewTest: [ FAILED ] - %result%
    )
:endListProductStaffViewTest
EXIT /B 0

:: Test method for the view products test with customer's view, so customer will not see the product3 we just created with stock of 0
:listProductCustomerViewTest
curl -s -H "Content-Type: application/json" -X GET "http://localhost:8080/products?customerPage=true" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo listProductCustomerViewTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endListProductCustomerViewTest
)
:: if we did not find product3 in database's response we check if we can view product 1 and product 2.
SET /p result=<%JSON_DATA%
findstr /m "%productName3%" %JSON_DATA%
if %errorlevel%==0 (
    echo   listProductCustomerViewTest: [ FAILED ] - %result%
    goto endListProductCustomerViewTest
)
findstr /m "%productName%" %JSON_DATA%
if %errorlevel% GTR 0 (
    echo   listProductCustomerViewTest: [ FAILED ] - %result%
    goto endListProductCustomerViewTest
)
findstr /m "%productName2%" %JSON_DATA%
if  %errorlevel% == 0 (
    echo   listProductCustomerViewTest: [ PASSED ] - %result%
) else (
    echo   listProductCustomerViewTest: [ FAILED ] - %result%
)
)
:endListProductCustomerViewTest
EXIT /B 0

:: Test method for change a product's stock
:changeProductStockTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/products/changestock?type=%type%&productName=%productName%&stock=%newStock%&ownerEmail=%ownerEmail%&ownerPassword=%ownerPassword%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo changeProductStockTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endChangeProductStockTest
)
SET /p result=<%JSON_DATA%
findstr /m "%newStock%" %JSON_DATA%
if %errorlevel%==0 (
    echo   changeProductStockTest: [ PASSED ] - %result%
) else (
    echo   changeProductStockTest: [ FAILED ] - %result%
)
:endChangeProductStockTest
EXIT /B 0

:: Test method for the delete of a product, the deleted product will only be returned after a sucessful delection so we can do pattern maching from the database's response.(productName3 found in return -> delete sucessed)
:deleteProductTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/products/delete?productName=%productName3%&ownerEmail=%ownerEmail%&ownerPassword=%ownerPassword%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo deleteProductTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endDeleteProductTest
)
SET /p result=<%JSON_DATA%
findstr /m "%productName3%" %JSON_DATA%
if %errorlevel%==0 (
    echo   deleteProductTest: [ PASSED ] - %result%
) else (
    echo   deleteProductTest: [ FAILED ] - %result%
)
:endDeleteProductTest
EXIT /B 0


:: Test method for the safeguard to prevent a product's stock changed to negative
:changeProductStockNegativeTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/products/changestock?type=%type%&productName=%productName%&stock=%invalidStock%&ownerEmail=%ownerEmail%&ownerPassword=%ownerPassword%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo changeProductStockNegativeTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endChangeProductStockNegativeTest
)
SET /p result=<%JSON_DATA%
findstr /m "500" %JSON_DATA%
if %errorlevel%==0 (
    echo   changeProductStockNegativeTest: [ PASSED ]
) else (
    echo   changeProductStockNegativeTest: [ FAILED ] - %result%
)
:endChangeProductStockNegativeTest
EXIT /B 0


:: Test method for the change product's aviilability
:changeProductAvailabilityTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/products/changeAvailability?type=%type%&productName=%productName%&isAviliableOnline=%newOnline%&ownerEmail=%ownerEmail%&ownerPassword=%ownerPassword%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo changeProductAvailabilityTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endChangeProductAvailabilityTest
)
SET /p result=<%JSON_DATA%
findstr /m "%newOnline%" %JSON_DATA%
if %errorlevel%==0 (
    echo   changeProductAvailabilityTest: [ PASSED ] - %result%
) else (
    echo   changeProductAvailabilityTest: [ FAILED ] - %result%
)
:endChangeProductAvailabilityTest
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
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/timeslot?timeslotdate=%date%&timeslotstarttime=%startHour2%&timeslotendtime=%endHour2%&ownerEmail=%ownerEmail%&ownerPassword=%ownerPassword%" > %JSON_DATA%
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

:: Test method for the creation of an timeSlot
:timeSlotCreationTest2
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/timeslot?timeslotdate=%date%&timeslotstarttime=%startHour3%&timeslotendtime=%endHour3%&ownerEmail=%ownerEmail%&ownerPassword=%ownerPassword%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo timeSlotCreationTest2: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endTimeSlotCreationTest2
)
SET /p result=<%JSON_DATA%
findstr /m "error" %JSON_DATA%
if %errorlevel%==0 (
    echo   timeSlotCreationTest2: [ FAILED ] - %result%
) else (
    echo   timeSlotCreationTest2: [ PASSED ] - %result%
)
:endTimeSlotCreationTest2
EXIT /B 0

:: Test method for the delete of time slot, the deleted time slot will only be returned after a sucessful delection so we can do pattern maching from the database's response
:deleteTimeSlotTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/timeslot/delete?timeslotdate=%date%&timeslotstarttime=%startHour3%&timeslotendtime=%endHour3%&ownerEmail=%ownerEmail%&ownerPassword=%ownerPassword%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo deleteTimeSlotTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endDeleteTimeSlotTest
)
SET /p result=<%JSON_DATA%
findstr /m "%date%" %JSON_DATA%
if not %errorlevel%==0 (
    echo   deleteTimeSlotTest: [ FAILED ] - %result%
    goto endDeleteTimeSlotTest
)
findstr /m "%startHour3%" %JSON_DATA%
if not %errorlevel%==0 (
    echo   deleteTimeSlotTest: [ FAILED ] - %result%
    goto endDeleteTimeSlotTest
)
findstr /m "%endHour3%" %JSON_DATA%
if %errorlevel%==0 (
    echo   deleteTimeSlotTest: [ PASSED ] - %result%
) else (
    echo   deleteTimeSlotTest: [ FAILED ] - %result%
)
:endDeleteTimeSlotTest
EXIT /B 0


:: Test method safeguard for the creation of an duplicate timeSlot
:duplicateTimeSlotCreationTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/timeslot?timeslotdate=%date%&timeslotstarttime=%startHour2%&timeslotendtime=%endHour2%&ownerEmail=%ownerEmail%&ownerPassword=%ownerPassword%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo duplicateTimeSlotCreationTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endDuplicateTimeSlotCreationTest
)
SET /p result=<%JSON_DATA%
findstr /m "500" %JSON_DATA%
if %errorlevel%==0 (
    echo   duplicateProductCreationTest: [ PASSED ]
) else (
    echo   duplicateProductCreationTest: [ FAILED ] - %result%
)
:endDuplicateTimeSlotCreationTest
EXIT /B 0

:: Test method for list timeSlots
:listTimeSlotTest
curl -s -H "Content-Type: application/json" -X GET "http://localhost:8080/availabletimeslots" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo listTimeSlotTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endListTimeSlot
)
:: Since the delete time slot test is called after this list test, two time slots just created is expected in the database's response
SET /p result=<%JSON_DATA%
findstr /m "%date%" %JSON_DATA%
if not %errorlevel%==0 (
    echo   listTimeSlotTest: [ FAILED ] - %result%
    goto endListTimeSlot
)
findstr /m "%startHour2%" %JSON_DATA%
if not %errorlevel%==0 (
    echo   listTimeSlotTest: [ FAILED ] - %result%
    goto endListTimeSlot
)
findstr /m "%endHour2%" %JSON_DATA%
if not %errorlevel%==0 (
    echo   listTimeSlotTest: [ FAILED ] - %result%
    goto endListTimeSlot
)
findstr /m "%startHour3%" %JSON_DATA%
if not %errorlevel%==0 (
    echo   listTimeSlotTest: [ FAILED ] - %result%
    goto endListTimeSlot
)
findstr /m "%endHour3%" %JSON_DATA%
if %errorlevel%==0 (
    echo   listTimeSlotTest: [ PASSED ] - %result%
) else (
    echo   listTimeSlotTest: [ FAILED ] - %result%
)
:endListTimeSlot
EXIT /B 0

:: Test method for add item to cart
:addTimeSlotToCartTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/carts/timeslot?customeremail=%customerEmail%&customerpassword=%customerPassword%&timeslotdate=%date%&timeslotstarttime=%startHour2%&timeslotendtime=%endHour2%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo addTimeSlotToCartTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endAddTimeSlotToCartTest
)
SET /p result=<%JSON_DATA%
findstr /m "%startHour2%" %JSON_DATA%
if not %errorlevel%==0 (
    echo   addTimeSlotToCartTest: [ FAILED ] - %result%
    goto endAddTimeSlotToCartTest
)
findstr /m "%endHour2%" %JSON_DATA%
if %errorlevel%==0 (
    echo   addTimeSlotToCartTest: [ PASSED ] - %result%
) else (
    echo   addItemToCartTest: [ FAILED ] - %result%
)
:endAddTimeSlotToCartTest
EXIT /B 0

:: Test method for add item to cart
:addItemToCartTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/cart/item?useremail=%customerEmail%&userpassword=%customerPassword%&productname=%productName%&quantity=%purchaseQuantity%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo addItemToCartTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endAddItemToCartTest
)
SET /p result=<%JSON_DATA%
findstr /m "%productname%" %JSON_DATA%
if not %errorlevel%==0 (
    echo   addItemToCartTest: [ FAILED ] - %result%
    goto endAddItemToCartTest
)
findstr /m "%purchaseQuantity%" %JSON_DATA%
if %errorlevel%==0 (
    echo   addItemToCartTest: [ PASSED ] - %result%
) else (
    echo   addItemToCartTest: [ FAILED ] - %result%
)
:endAddItemToCartTest
EXIT /B 0

:: Test method for add item to cart
:addItemToCartTest2
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/cart/item?useremail=%customerEmail%&userpassword=%customerPassword%&productname=%productName2%&quantity=%purchaseQuantity2%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo addItemToCartTest2: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endAddItemToCartTest2
)
SET /p result=<%JSON_DATA%
findstr /m "%productname2%" %JSON_DATA%
if not %errorlevel%==0 (
    echo   addItemToCartTest2: [ FAILED ] - %result%
    goto endAddItemToCartTest2
)
findstr /m "%purchaseQuantity2%" %JSON_DATA%
if %errorlevel%==0 (
    echo   addItemToCartTest2: [ PASSED ] - %result%
) else (
    echo   addItemToCartTest2: [ FAILED ] - %result%
)
:endAddItemToCartTest2
EXIT /B 0

:: Test method for get total of the cart
:getCartToalTest
curl -s -H "Content-Type: application/json" -X GET "http://localhost:8080/cart/total?customeremail=%customerEmail%&customerpassword=%customerPassword%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo getCartToalTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endGetCartToalTest
)
SET /p result=<%JSON_DATA%
findstr /m "%sum%" %JSON_DATA%
if %errorlevel%==0 (
    echo   getCartToalTest: [ PASSED ] - cart total is %result%
) else (
    echo   getCartToalTest: [ FAILED ] - %result%
)
:endGetCartToalTest
EXIT /B 0

:: Test method for checkout
:checkoutTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/cart/pay?customeremail=%customerEmail%&customerpassword=%customerPassword%&paymentcode=fromThirdPartyPaymentSolution" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo checkoutTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endcheckoutTest
)
SET /p result=<%JSON_DATA%
findstr /m "error" %JSON_DATA%
if %errorlevel%==0 (
    echo   checkoutTest: [ FAILED ] - %result%
) else (
    echo   checkoutTest: [ PASSED ] - %result%
)
:endcheckoutTest
EXIT /B 0

:: Test method for the delete of a store, the deleted store will only be returned after a sucessful delection so we can do pattern maching from the database's response
:deleteStoreTest
curl -s -H "Content-Type: application/json" -X POST "http://localhost:8080/store/delete?ownerEmail=%ownerEmail%&ownerPassword=%ownerPassword%" > %JSON_DATA%
for %%A in (%JSON_DATA%) do if %%~zA==0  (
    echo deleteStoreTest: [ FAILED ] - Application does not seem to be running on localhost:8080
    goto endDeleteStoreTest
)
SET /p result=<%JSON_DATA%
findstr /m "%storePhone%" %JSON_DATA%
if not %errorlevel%==0 (
    echo   deleteStoreTest: [ FAILED ] - %result%
    goto endDeleteStoreTest
)
findstr /m "%storeEmail%" %JSON_DATA%
if not %errorlevel%==0 (
    echo   deleteStoreTest: [ FAILED ] - %result%
    goto endDeleteStoreTest
)
findstr /m "%newStoreOpeningHours%" %JSON_DATA%
if not %errorlevel%==0 (
    echo   deleteStoreTest: [ FAILED ] - %result%
    goto endDeleteStoreTest
)
findstr /m "%newStoreClosingHours%" %JSON_DATA%
if %errorlevel%==0 (
    echo   deleteStoreTest: [ PASSED ] - %result%
) else (
    echo   deleteStoreTest: [ FAILED ] - %result%
)
:endDeleteStoreTest
EXIT /B 0
