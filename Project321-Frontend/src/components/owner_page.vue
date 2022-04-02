<template>
  <div id="owner_page">
      <button class="logout_button" @click="logout()">Logout</button>
      <h2 style="text-align:center">This is the owner page</h2>
      <h4 style="text-align:center; margin-top:20px"> To see and modify the store info, click here -->
        <button class="edit_store_info_page_button" @click="gotoStoreInfo()">Store Info Page</button>
      </h4>
      <h4 style="text-align:center; margin-top:20px">The current total is {{this.orderTotal}}. For details of each order, click here -->
        <button class="orders_botton" @click="gotoOrders()">Order Management</button>
      </h4>
      <h4 style="text-align:center; margin-top:20px">To view and modify employees and their status/shifts, click here -->
        <button class="employee_botton" @click="gotoViewEmployee()">Employees Management</button>
      </h4>
      <h4 style="text-align:center; margin-top:20px">To view inventory and modify the stock, click here -->
        <button class="inventory_botton" @click="gotoInventory()">Inventory Management</button>
      </h4>
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

</style>

<script src="./scripts/owner_page.js"></script>