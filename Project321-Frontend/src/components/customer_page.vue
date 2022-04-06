<template>
  <div id="customer_page">
    <button class="logout_button" @click="logout()">Logout</button>
    <h2 style="text-align:center;margin-left:35%">Welcome, {{yourName}} !</h2>
    <div id="personal_info">
      <h4 style="text-align:left"> Personal information </h4>
    
      <table class="table_style">
        <tr>
          <th>Email</th>
          <td>{{yourEmail}}</td>
        </tr>
        <tr>
          <th>Phone</th>
          <td>{{yourPhone}}</td>
        </tr>
        <tr>
          <th>Address</th>
          <td>
            {{yourAddress.postalCode}} <br>
            {{yourAddress.street}}, {{yourAddress.town}} <br>
            (unit: {{yourAddress.unit}}) 
          </td>
        </tr>
      </table>
    </div>
    
    <div id="cart">
      <h4 style="text-align:left"> Cart </h4>
        <p v-if="noCart" class="error_message">No cart</p>

        <p v-if="noCart">
          You can start shopping online after creating your own cart.<br>
          Create one by choosing one of Cart Type below! <br>
          Cart Type : 
          <select v-model = "newCartType">
              <option> Delivery </option>
              <option> Pickup </option>
          </select>
          <button v-bind:disabled="!newCartType" @click="create_cart()"> create new cart </button>
          <ul class="description">
            <li>You can get your items in the cart delivered by choosing <b>Delivery</b> option.
            Delivery service fee might be applied for customers outside the town borders. </li>
            <li>You can order your items by choosing <b>PickUp</b> option.
            There is no extra service fee for PickUp. </li>
            <li> Not all items are available for pickup or delivery.
            (There are some items that can only be bought in person)</li>
          </ul>

        </p>
        
        <div id="item_lists" v-if="!noCart">
          <h6>Your cart type : {{yourCart.shoppingType}}</h6>
          <p v-if="cartItems.length<0" class="error_message"> Your Cart is empty. </p>

          <div  v-if="cartItems.length>0" >
            <table id="item_lists_table">
              <tr>
                <th> product name </th>
                <th> price <br> (price type) </th>
                <th> quantity </th>
                <th> subtotal </th>
              </tr>
              <tr v-for="cartItem in cartItems" :key=cartItem.name>
                <td> {{cartItem.product.productName}} </td>
                <td> {{cartItem.product.price}} ({{cartItem.product.priceType}}) </td>
                <td> {{cartItem.quantity}} </td>
                <td> {{cartItem.quantity * cartItem.product.price}} </td>
              </tr>
            </table>
          </div>
        </div>
        <p text-align="right" v-if="!noCart">
          To view and purchase products, 
          <button class="inventory_botton" @click="gotoInventory()">Go Shopping</button>
          <br> If you are ready for payment, 
          <button v-bind:disabled="cartItems.length<1" class="checkout_botton" @click="gotoCheckout()">Checkout</button>
          <br>To clear the cart,
          <button class="clear_cart_botton" @click="clear_cart()">Clear Cart</button>
          <br>To delete the cart,
          <button class="delete_cart_botton" @click="delete_cart()">Delete Cart</button>
        </p>
    </div>
  </div> 
</template>

<style>
  #customer_page {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: white;
  }

  #personal_info{
    float : left;
    background : #c7dbf0;
    width : 30%;
    border-radius: 8px;
    padding : 15px;
    margin-left : 5%;
  }

  #cart{
    float : right;
    background : #c7dbf0;
    width : 55%;
    border-radius: 8px;
    padding : 15px;
    margin-right :5%;
  }

  #item_lists{
    background : white;
    margin-left: 25px;
    margin-right: auto;
    margin-bottom: 10px;
    padding: 15px;
  }

  #item_lists_table{
    table-layout: fixed;
    width: 100%;
  }

  h6{
    text-align: right;
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
  .edit_button {
    color : white;
    background-color: #2c3e50;
    font-size : 12px;
    border : none;
    margin-left: 5px;
    margin-right: 5px;
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
  .description {
    font-size : 14px;
    color:navy;
    text-align:left;
    margin : 15px;
  }

</style>

<script src="./scripts/customer_page.js"></script>