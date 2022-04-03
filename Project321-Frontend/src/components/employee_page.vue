<template>
  <div id="employee_page">
    <button class="logout_button" @click="logout()">Logout</button>
    <button class="refresh_botton" @click="refresh()">refresh</button>
    <h2 style="text-align:center">This is the employee page</h2>

    <h3 style="text-align:center; margin-top:50px; margin-left:50px">Orders Waiting for Fulfillment</h3>
    <table class = "tableStyle" v-if="orders.length > 0">
      <tr>
        <th>Date</th>
        <th>Products List</th>
        <th>Confirm Fulfilment</th>
      </tr>
      <tr v-for="order in orders" :key=order.orderDate>
      <td>{{ order.orderDate }}</td>
      <td>
        <p v-for="items in order.cart.cartItems" :key=items.quantity>
          {{ items.product.productName}} with quantity: {{ items.quantity}}
        </p>
      </td>
      <td> <button class="delete_botton" @click="fulFill(order.cart.creationTime, order.cart.creationDate)">Complete</button> </td>
      </tr>
    </table>
    <span v-if="orders.length == 0" style="color:red"> There is no order waiting to be fulfiled </span>

    <h3 style="text-align:center; margin-top:30px">Create an in-store Purchase</h3>
    <table class = "tableStyle" >
      <tr>
        <th>Product name</th>
        <th>Quantity</th>
        <th>Confirm Creation</th>
      </tr>
        <tr> <td> <input type="productNSame" class="input_text" v-model="instorePurchaseName" placeholder="Product name" > </td> 
          <td>  <input type="number" class="input_text" v-model="instorePurchaseQuantity" placeholder="Product Stock"> </td> 
          <td>
              <button class="botton" v-bind:disabled="!instorePurchaseName || !instorePurchaseQuantity" @click="addInstorePurchase(instorePurchaseName, instorePurchaseQuantity)">Add</button>
          </td>
        </tr>
    </table>
    <span v-if="creationComplete.length > 0" style="color:green"> Creation complete</span>
        <span v-if="errorAddInstorePurchase.length > 0" style="color:red"> {{errorAddInstorePurchase}}</span>

    <h3 style="text-align:center; margin-top:50px; margin-left:50px">Myshifts</h3>
    <table class = "tableStyle">
      <tr>
        <th>Date</th>
        <th>StartHour</th>
        <th>EndHour</th>
      </tr>
      <tr v-for="shift in shifts" :key=shift.date>
      <td>{{ shift.date }}</td>
      <td>{{ shift.startHour }}</td>
      <td>{{ shift.endHour }}</td>
      </tr>
    </table>
    
  </div> 
</template>

<style>
  #employee_page {
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
 	.tableStyle, th, td{
  	border:1px solid black;
   	border-collapse: collapse;
    text-align: center;
    text-align: center;
    padding: 10px; 
  }
  .tableStyle {
      width: 60%;
      margin-left: auto;
  		margin-right: auto;
      caption-side: top;
  }
  .delete_botton {
  color:red;
  }
  .refresh_botton {
  margin-top: 5px;
  margin-right: 80px;
  width: 70px;
  top: 0; 
  right: 0;
  position: absolute;
  }
</style>

<script src="./scripts/employee_page.js"></script>