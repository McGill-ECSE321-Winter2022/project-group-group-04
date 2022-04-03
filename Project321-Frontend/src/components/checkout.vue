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
                total price for item : ${{totalItemPrice}} <br>
                type(Delivery OR Pickup) : {{yourCart.shoppingType}}
                (shipping fee : ${{shippingPrice}})
            </p>
            <p class="review">
                You need to pay : <b>${{totalPrice}}</b>
            </p>
        </div>
    <h4><b> Step 2 </b>&nbsp;Schedule your delivery/pick-up time </h4>
        <div class="section" v-if="!yourTimeSlot">
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
                you selected : {{selectedTimeSlot.date}} {{selectedTimeSlot.startTime}} {{selectedTimeSlot.endTime}} <br>
                <button v-bind:disabled="!selectedTimeSlot" @click="selectTimeSlot(selectedTimeSlot)">Confirm this Timeslot</button>

            </p>
            <div class= "popup" id="popup">
                <h2>Success</h2>
                <p>You have succesfully booked a Time Slot.</p>
                <button type=button @click="closePopup()">OK</button>
            </div>
        </div>
        <div class="section" v-if="yourTimeSlot">
            <p> You have already booked your Time Slot.</p>
            <p class="review">
                Date : <b>{{yourTimeSlot.date}}</b><br>
                Time : <b>{{yourTimeSlot.startTime}} ~ {{yourTimeSlot.endTime}}</b>
            </p>
        </div>
    <h4><b> Step 3 </b>&nbsp;Complete your payment </h4>
        <div class="section">
            To complete your payment, enter your payment code below: <br>
            <input type="text" placeholder="ex. Card Number" v-model="yourPaymentCode">
            <button v-bind:disabled="!yourPaymentCode" @click="makePayment(yourPaymentCode)">Make a payment</button>
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

p.review{
    background: #c7dbf0;
    text-align: center;
    font-style: italic;
    font-size: 20px;
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
.popup{
    width:  400px;
    background: #fff;
    border-radius: 6px;
    position: absolute;
    top: 0;
    left: 50%;
    transform: translate(-50%,-50%) scale(0.1);
    text-align: center;
    padding: 0 30px 30px;
    color: #333;
    visibility: hidden;
    transition: 0.4s, top 0.4s;

}
.open-popup{
    visibility: visible;
    top:  50%;
    transform: translate(-50%,-50%) scale(1);
}
.popup h2{
    font-size: 38px;
    font-weight: 500;
    margin: 30px 0 10px;

}
.popup button{
    width: 100%;
    margin-top: 50px;
    padding: 10px 0 ;
    background: #000000;
    color: #fff;
    border:  0;
    outline: none;
    font-size: 18px;
    border-radius: 4px;
    cursor: pointer;
}

</style>

<script src="./scripts/checkout.js"></script>