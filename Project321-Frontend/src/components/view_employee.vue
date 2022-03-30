<template>
  <div id="view_employee">
    <button class="back_botton" @click="back()">back</button>
    <h3 style="text-align:center; margin-top:50px; margin-left:50px">All Employees</h3>
    <table class = "tableStyle">
      <tr>
        <th>Name</th>
        <th>Email</th>
        <th>Status</th>
        <th>Shifts</th>
      </tr>
      <tr v-for="employee in employees" :key=employee.name>
      <td>{{ employee.name }}</td>
      <td>{{ employee.email }}</td>
      <td>{{ employee.status }}</td>
      <td>
        <p v-for="shift in employee.shifts" :key=shift.date>
          {{ shift.date}} from {{ shift.startHour}} to {{ shift.endHour}}
        </p>
      </td>
      </tr>
    </table>
    <h3 style="text-align:center; margin-top:50px; margin-left:50px">Edit employee working status</h3>
    <table class = "tableStyle">
        <tr> <td> <input type="employeeName" class="input_text" v-model="employeeEmail" placeholder="employee name"> </td> 
        <td> <label> New status</label>
            <select v-model = "Newstatus">
                <option> Active </option>
                <option> Inactive </option>
                <option> Sick </option>
            </select>
        </td>
        <td>
            <button class="botton" v-bind:disabled="!employeeEmail || !Newstatus" @click="changeStatus(employeeEmail, Newstatus)">Modify</button>
        </td>
        </tr>
    </table>
    <span v-if="this.errorEmployee.length > 0" class="error_message">Error: {{errorEmployee}} </span>
    <span v-if="this.errorStatus.length > 0" class="error_message">Error: {{errorStatus}} </span>
  </div> 
</template>

<style>
  #orders {
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

<script src="./scripts/view_employee.js"></script>