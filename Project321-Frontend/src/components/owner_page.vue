<template>
  <div id="owner_page">
      <button class="logout_button" @click="logout()">Logout</button>

      <div id="header">
      <h2 style="text-align:center">This is the owner page</h2>
      </div>

      <div id="logic_sector">
        <h4 style="text-align:center; margin-top:20px"> To see and modify the store info, click here -->
        <button class="edit_store_info_page_button" @click="gotoStoreInfo()">Store Info Page</button>
      </h4>
      </div>

      <div id="logic_sector">
        <h4 style="text-align:center; margin-top:20px">The current Online sales total is {{this.orderTotal}}$. For details of each order, click here -->
          <button class="orders_botton" v-bind:disabled="this.orderTotal == 0" @click="gotoOrders()">Order Management</button>
        </h4>
      </div>
      
      <div id="logic_sector">
        <h4 style="text-align:center; margin-top:20px">To view and modify employees and their status/shifts, click here -->
         <button class="employee_botton" @click="gotoViewEmployee()">Employees Management</button>
        </h4>
      </div>

      <div id="logic_sector">
        <h4 style="text-align:center; margin-top:20px">To view inventory and modify the stock, click here -->
         <button class="inventory_botton" @click="gotoInventory()">Inventory Management</button>
       </h4>
      </div>

      <div id="logic_sector">
        <h4 style="text-align:center; margin-top:20px">To manage Time Slots click here -->
          <button class="timeslot_button" @click="gotoTimeslot()">Time Slot Management</button>
       </h4>
      </div>
  </div> 
</template>

<style>
  #owner_page {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: white;
  }
  .logout_button {
    margin-top: 5px;
    margin-right: 5px;
    width: 70px;
    top: 0; 
    right: 0;
    position: absolute;
  }
  #logic_sector {
    background : #c7dbf0;
    width : 50%;
    border-radius: 8px;
    padding : 15px;
    margin : auto;
    margin-top: 3%;
  }

    #header {
    background : #bef0cd;
    width : 50%;
    border-radius: 8px;
    padding : 15px;
    margin : auto;
    margin-top: 3%;
  }

</style>

<script src="./scripts/owner_page.js"></script>