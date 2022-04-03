<template>
  <div id="view_timeslot">
    <button class="back_botton" @click="back()">back</button>
    <h3 style="text-align:center; margin-top:50px; margin-left:50px">All Time Slots</h3>
    <table class = "tableStyle">
      <tr>
        <th>Start Time</th>
        <th>End Time</th>
        <th>Day</th>
        <th>Max Order Per Slot</th>
      </tr>
      <tr v-for="timeslot in timeSlots" :key=timeslot.date>
      <td>{{ timeslot.startTime }}</td>
      <td>{{ timeslot.endTime }}</td>
      <td>{{ timeslot.date }} </td>
      <td>{{ timeslot.maxOrderPerSlot }} </td>
      </tr>
    </table>

        <h3 style="text-align:center; margin-top:50px; margin-left:50px">Create New Time Slot</h3>
    <table class = "tableStyle">
      <th> Start hour </th>
      <th> End hour </th>
      <th> Date </th>
      <th>Add Time Slot</th>
        <tr> 
          <td> <input type="time" class="input_text" v-model="newStartHour" placeholder="hh:mm:ss"> </td> 
          <td> <input type="time" class="input_text" v-model="newEndHour" placeholder="hh:mm:ss"> </td> 
          <td> <input type="date" class="input_text" v-model="newDate" placeholder="yyyy:mm:dd"> </td> 
          <td>
            <button class="botton" v-bind:disabled=" !newStartHour || !newEndHour || !newDate" @click="createTimeSlot(newStartHour, newEndHour, newDate)">Add</button>
         </td>
        </tr>
    </table>

     <h3 style="text-align:center; margin-top:50px; margin-left:50px">Delete Time Slot</h3>
    <table class = "tableStyle">
      <th> Start hour </th>
      <th> End hour </th>
      <th> Date </th>
      <th>Delete Time Slot</th>
        <tr> 
          <td> <input type="time" class="input_text" v-model="oldStartHour" placeholder="hh:mm:ss"> </td> 
          <td> <input type="time" class="input_text" v-model="oldEndHour" placeholder="hh:mm:ss"> </td> 
          <td> <input type="date" class="input_text" v-model="oldDate" placeholder="yyyy:mm:dd"> </td> 
          <td>
            <button class="botton" v-bind:disabled=" !oldStartHour || !oldEndHour || !oldDate" @click="deleteTimeSlot(oldStartHour, oldEndHour, oldDate)">Delete</button>
         </td>
        </tr>
    </table>

    <span v-if="this.errorTimeSlot.length > 0" class="error_message">Error: {{errorTimeSlot}} </span>
    <span v-if="this.errorTimeSlot2.length > 0" class="error_message">Error: {{errorTimeSlot2}} </span>
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

<script src="./scripts/view_timeslot.js"></script>