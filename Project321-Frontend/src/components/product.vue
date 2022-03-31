<template>
  <div id="product">
    <button class="back_botton" @click="back()">back</button>
    <h3 style="text-align:center; margin-top:50px; margin-left:50px">All Produts</h3>
    <div class="search-wrapper panel-heading col-sm-12">
    <input type="text" v-model="search" placeholder="Search" /> <br> <br>
    </div> 
    <table class = "tableStyle">
        <thead>
            <tr>
                <th>product name</th>
                <th>price type</th>
                <th>Online availability</th>
                <th>stock</th>
                <th>price</th>
                <th>add to cart</th>
            </tr>
         </thead>
        <tbody>
            <tr v-for="(product, i) in ProductFilter" :key=product.productName>
              <td>{{ product.productName }} </td>
              <td>{{ product.priceType }}</td>
              <td>{{ product.isAvailableOnline }}</td>
              <td>{{ product.stock }}</td>
              <td>{{ product.price }}</td>
              <td> 
                <p v-if="product.isAvailableOnline === 'no'" class="error_message">Not availableOnline</p>
                <input v-if="product.isAvailableOnline === 'yes'" type="number" id="tentacles"
                        name="tentacles" min="0" :max="product.stock" v-model="newQuantity[i]">
                
                <button v-bind:disabled="newQuantity[i]<1 || newQuantity.length<i+1 || !newQuantity[i]" @click="toCart(product.productName, newQuantity)">Add</button>
        
              </td>
            </tr>
        </tbody>
    </table>
    <span v-if="this.errorProduct.length > 0" class="error_message">Error: {{errorStatus}} </span>
  </div> 
</template>

<style>
  #product {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: white;
  }
  .back_botton {
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
  .error_message {
    color:red;
    text-align:center;
}
</style>

<script src="./scripts/product.js"></script>