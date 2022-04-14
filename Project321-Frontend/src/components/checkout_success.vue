<template>
<!-- This page notify the customer that order has being placed with button to go back to the customer page-->
<div id="checkout_success">
    <h2 style="text-align:center">Your payment have successfully made!</h2>
    <button @click="back()">Go back to customer page</button>
</div>
</template>

<style>
#checkout_success {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: white;
}

.success_text {
    margin-top: 50px;
    text-align: center;
}
</style>

<script src="./scripts/checkout_success.js"></script>
