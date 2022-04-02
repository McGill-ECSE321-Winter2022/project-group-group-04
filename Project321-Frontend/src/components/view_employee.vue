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
      <tr v-for="employee in employees" :key=employee.email>
      <td>{{ employee.name }}</td>
      <td>{{ employee.email }}</td>
      <td>{{ employee.status }} 
        <select v-model = "Newstatus">
                <option> Active </option>
                <option> Inactive </option>
                <option> Sick </option>
        </select>
        <button class="botton" v-bind:disabled="!Newstatus" @click="changeStatus(employee.email, Newstatus)">Modify</button>
      </td>
      <td>
        <p v-for="shift in employee.shifts" :key=shift.date>
          {{ shift.date}} from {{ shift.startHour}} to {{ shift.endHour}}
        </p>
      </td>
      </tr>
    </table>
    <h3 style="text-align:center; margin-top:50px; margin-left:50px">Create shift for employee</h3>
    <table class = "tableStyle">
      <th> Employee email </th>
      <th> Start hour </th>
      <th> End hour </th>
      <th> Date </th>
      <th> Confirm modification </th>
        <tr> <td>         
          <select v-for="employee in employees" :key=employee.email v-model = "employeeEmail">
            <option> {{employee.email}} </option>
        </select>
         </td> 
          <td> <input type="time" class="input_text" v-model="newStartHour" placeholder="hh:mm:ss"> </td> 
          <td> <input type="time" class="input_text" v-model="newEndHour" placeholder="hh:mm:ss"> </td> 
          <td> <input type="date" class="input_text" v-model="newDate" placeholder="yyyy:mm:dd"> </td> 
          <td>
            <button class="botton" v-bind:disabled="!employeeEmail || !newStartHour || !newEndHour || !newDate" @click="createShift(employeeEmail, newStartHour, newEndHour, newDate)">Modify</button>
         </td>
        </tr>
    </table>
    <span v-if="this.errorEmployee.length > 0" class="error_message">Error: {{errorEmployee}} </span>
    <span v-if="this.errorStatus.length > 0" class="error_message">Error: {{errorStatus}} </span>
    <span v-if="this.errorShift.length > 0" class="error_message">Error: {{errorShift}} </span>
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
tr:nth-child(even) {
  background-color: #f2f2f2;
}
</style>

<script src="./scripts/view_employee.js"></script>