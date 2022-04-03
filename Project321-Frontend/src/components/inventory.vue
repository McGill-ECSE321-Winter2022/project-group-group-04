<template>
  <div id="inventory">
    <button class="back_botton" @click="back()">back</button>
    <button class="refresh_botton" @click="refresh()">refresh</button>
    <h3 style="text-align:center">Add a product</h3>
    <table class = "tableStyle">
        <tr> <td> <label> Product name</label> <input type="productName" class="input_text" v-model="newProductName" placeholder="Product name" > </td> 
          <td> <label> Product Stock</label> <input type="productStock" class="input_text" v-model="newStock" placeholder="Product Stock"> </td> 
          <td> <label> Product Price</label> <input type="productPrice" class="input_text" v-model="newPrice" placeholder=""> </td> 
          <td> <label> New status</label>
              <select v-model = "newType">
                  <option> PER_UNIT </option>
                  <option> PER_KILOS </option>
              </select>
          </td>
          <td> <label> Online availability</label>
              <select v-model = "newAvailability">
                  <option> yes </option>
                  <option> no </option>
              </select>
          </td>
          <td>
              <button class="botton" v-bind:disabled="!newProductName || !newStock || !newPrice || !newType || !newAvailability" @click="addProduct(newProductName, newStock, newPrice, newType, newAvailability)">Add</button>
          </td>
        </tr>
    </table>
    <span v-if="this.errorAddProduct.length > 0" class="error_message">Error: {{errorAddProduct}}. You can refresh the page with the refresh botton</span>
    <h3 style="text-align:center; margin-top:50px">All Products</h3>
    <div class="search-wrapper panel-heading col-sm-12">
    <input type="text" v-model="search" placeholder="Search" /> <br> <br>
    </div> 
    <table class = "tableStyle">
        <thead>
            <tr>
                <th>product name</th>
                <th>preview</th>
                <th>price type</th>
                <th>Online availability</th>
                <th>stock</th>
                <th>price</th>
                <th>Delete Product</th>
            </tr>
         </thead>
        <tbody>
            <tr v-for="product in ProductFilter" :key=product.productName>
            <td>{{ product.productName }}
              <p v-if="Number(product.stock) === 0" class="error_message">Out Of Stock </p>
            </td>
            <td>
              <img :src="'static/' + product.productName + '.jpg'" alt="N/A" style="width:60px;height:60px;" >
            </td>
            <td>{{ product.priceType }}</td>
            <td>{{ product.isAvailableOnline }}</td>
            <td>{{ product.stock }}</td>
            <td>{{ product.price }}</td>
            <td> <button class="delete_botton" @click="deleteProduct(product.productName)">Delete</button>
            </td>
            </tr>
        </tbody>
    </table>
    <span v-if="this.errorProduct.length > 0" class="error_message">Error: {{errorProduct}}</span>
    <span v-if="this.errorDelete.length > 0" class="error_message">Error: {{errorDelete}}</span>
  </div> 
</template>

<style>
  #inventory {
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
  .refresh_botton {
  margin-top: 5px;
  margin-right: 80px;
  width: 70px;
  top: 0; 
  right: 0;
  position: absolute;
  }
  .delete_botton {
    color:red;
  }
  tr:nth-child(even) {
  background-color: #f2f2f2;
  }
</style>

<script src="./scripts/inventory.js"></script>