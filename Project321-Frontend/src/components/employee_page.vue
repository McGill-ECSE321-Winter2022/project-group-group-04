<template>
  <div id="employee_page">
    <button class="logout_button" @click="logout()">Logout</button>
    <h2 style="text-align:center">This is the employee page</h2>
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
</style>

<script src="./scripts/employee_page.js"></script>