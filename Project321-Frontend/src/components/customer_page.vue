<template>
  <div id="customer_page">
      <button class="logout_button" @click="logout()">Logout</button>
      <h2 style="text-align:center">This is the customer page</h2>
      <h4 style="text-align:center">To view and purchase products, click here -->
        <button class="inventory_botton" @click="gotoInventory()">Products</button>
      </h4>
  </div> 
</template>

<style>
  #customer_page {
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

<script src="./scripts/customer_page.js"></script>