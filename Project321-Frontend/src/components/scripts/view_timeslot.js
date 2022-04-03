import axios from 'axios'
import authentification from '@/main'
var config = require('../../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: 'timeslotdate',
    data () {
      return {
        response: [],
        error: '',
        timeSlots: [],
        errorTimeSlot: '',
        errorTimeSlot2: '',
        errorTimeSlot3: '',
        errorStatus: '',
        newStartHour: '',
        newEndHour: '',
        newDate: '',
        oldStartHour: '',
        oldEndHour: '',
        oldDate: '',


        

      }
    },
     created: function () {
        // Get all orders from backEnd
        AXIOS.get('/availabletimeslots')
        .then(response => {
          // JSON responses are automatically parsed.
          this.timeSlots = response.data
        })
        .catch(e => {
          this.errorTimeSlot = 'There is no Time Slots in the system'
          console.log(e)
        })
    },

    methods:{
            createTimeSlot: function (startHour, endHour, date) {
          let start = startHour.concat(":00");
          let end = endHour.concat(":00");
          const params2 = new URLSearchParams();
          params2.append('timeslotdate', date);
          params2.append('timeslotstarttime', start);
          params2.append('timeslotendtime', end);
          params2.append('ownerEmail', window.localStorage.getItem('email'));
          params2.append('ownerPassword', window.localStorage.getItem('password'));
          AXIOS.post('/timeslot', params2)
          .then(response => {
              this.response = response.data
              window.location.reload()
          })
          .catch(e => {
            this.errorTimeSlot2 = 'Conflict! Start or End hour out of the store opening hours'
            console.log(e)
          })
      },

      deleteTimeSlot: function(startHour,endHour,date){
        let start = startHour.concat(":00");
          let end = endHour.concat(":00");
          const params3 = new URLSearchParams();
          params3.append('timeslotdate', date);
          params3.append('timeslotstarttime', start);
          params3.append('timeslotendtime', end);
          params3.append('ownerEmail', window.localStorage.getItem('email'));
          params3.append('ownerPassword', window.localStorage.getItem('password'));
          AXIOS.post('/timeslot/delete', params3)
          .then(response => {
              this.response = response.data
              window.location.reload()
          })
          .catch(e => {
            this.errorTimeSlot3 = 'Conflict! Start or End hour out of the store opening hours'
            console.log(e)
          })
      }

    }
  }