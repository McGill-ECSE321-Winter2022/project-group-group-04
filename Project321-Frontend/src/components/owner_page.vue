<template>
  <div id="owner_page">
      <button class="logout_button" @click="logout()">Logout</button>
      <h2 style="text-align:center">This is the customer page</h2>
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