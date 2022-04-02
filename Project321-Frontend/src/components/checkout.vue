<template>
  <div id="customer_page">
    <button class="back_button" @click="back()">back</button>
    <h2 style="text-align:center"> You are now ready for Checkout.<br></h2>
    <h4><b> Step 1 </b>&nbsp;Review your items in cart and total price </h4>
        <div class="section">
            <table id="item_lists_table">
                <tr>
                <th> product name </th>
                <th> price (price type) </th>
                <th> qty </th>
                <th> subtotal </th>
                </tr>
                <tr v-for="cartItem in cartItems" :key=cartItem.name>
                <td> {{cartItem.product.productName}} </td>
                <td> {{cartItem.product.price}} ({{cartItem.product.priceType}}) </td>
                <td> {{cartItem.quantity}} </td>
                <td> {{cartItem.quantity * cartItem.product.price}} </td>
                </tr>
            </table>
            <p class="right">
                total price : ${{totalPrice}} <br>
                shipping fee : ${{shippingPrice}} <br>
                type(Delivery OR Pickup) : {{yourCart.shoppingType}}
            </p>
        </div>
    <h4><b> Step 2 </b>&nbsp;Schedule your delivery/pick-up time </h4>
        <div class="section">
            <table id="item_lists_table">
                <tr>
                    <th> Date </th>
                    <th> Time </th>
                    <th> availability </th>
                    <th> select </th>
                </tr>
                <tr v-for="(timeSlot,i) in timeSlots" :key=i>
                    <td> {{timeSlot.date}} </td>
                    <td> {{timeSlot.startTime}} ~ {{timeSlot.endTime}} </td>
                    <td> {{timeSlot.maxOrderPerSlot>0}} </td>
                    <td>
                        <input type="radio" id="i" name="picked" :value="timeSlot" v-model="selectedTimeSlot">

                    </td>
                </tr>

            </table>
            <p class="right">
                you selected : {{selectedTimeSlot}} <br>
                <button v-bind:disabled="!selectedTimeSlot" @click="selectTimeSlot()">Confirm this Timeslot</button>
            </p>
        </div>
    <h4><b> Step 3 </b>&nbsp;Complete your payment </h4>
        <div class="section">
            To complete your payment, go to next page
        </div>
  </div> 
</template>

<style>
#customer_page {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: white;
}
div.section{
    padding : 15px;
    margin-left : 10%;
}

#item_lists_table{
    table-layout: fixed;
    width: 100%;
    text-align: center;
    margin-bottom : 5%;
}
p.right{
    text-align: right;
    margin-right : 15%
}

.back_button {
    margin-top: 5px;
    margin-right: 5px;
    width: 70px;
    top: 0; 
    right: 0;
    position: absolute;
}
h4 {
    text-align : left;
    padding-left : 5%;
    padding-top : 20px;
}
h4 b{
    color : white;
    background : #2c3e50;
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