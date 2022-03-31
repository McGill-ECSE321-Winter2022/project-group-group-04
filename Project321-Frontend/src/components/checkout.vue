<template>
  <div id="customer_page">
    <button class="back_botton" @click="back()">back</button>
    <h2 style="text-align:center"> You are now ready for Checkout.</h2>

  </div> 
</template>

<style>
#customer_page {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: white;
}

.back_button {
    margin-top: 5px;
    margin-right: 5px;
    width: 70px;
    top: 0; 
    right: 0;
    position: absolute;
}

.table_style, th, td{
    border-collapse: collapse;
    text-align: left;
    vertical-align: top;
    padding: 5px; 
    margin-left: auto;
    margin-right: auto;
}
.error_message {
    color:red;
    text-align:center;
}

</style>

<script src="./scripts/checkout.js"></script>