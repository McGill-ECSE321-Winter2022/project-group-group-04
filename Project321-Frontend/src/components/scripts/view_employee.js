import axios from 'axios'
import authentification from '@/main'
var config = require('../../../config')

var frontendUrl = process.env.NODE_ENV === 'production' ? 'https://' + config.build.host : 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = process.env.NODE_ENV === 'production' ? 'https://' + config.build.backendHost : 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: 'orders',
    data () {
      return {
        response: [],
        error: '',
        employees: [],
        errorEmployee: '',
        employeeEmail: '',
        Newstatus: '',
        errorStatus: '',
        newStartHour: '',
        newEndHour: '',
        newDate: '',
        errorShift: '',
        employeeEmail2: '',
        newEmployeeEmail:'',
        employeeName: '',
        employeePassword: '',
        newEmployeeStatus:'',
      }
    },
    created: function () {
        // Get all orders from backEnd
        AXIOS.get('/employees', { params: {"ownerEmail" : window.localStorage.getItem('email'), "ownerPassword" : window.localStorage.getItem('password')}})
        .then(response => {
          // JSON responses are automatically parsed.
          this.employees = response.data
        })
        .catch(e => {
          this.errorEmployee = 'There is no employees in the system'
          console.log(e)
        })
    },
    methods: {
        back: function () {
            this.$router.push('/owner')
        },
        changeStatus: function (e, s) {
            const params = new URLSearchParams();
            params.append('status', s);
            params.append('employeeEmail', e);
            console.log(e)
            params.append('ownerEmail', window.localStorage.getItem('email'));
            params.append('ownerPassword', window.localStorage.getItem('password'));
            AXIOS.post('/employee/changeStatus', params)
            .then(response => {
                this.response = response.data
                window.location.reload()
            })
            .catch(e => {
              this.errorStatus = 'there is no employee with the specified email in the system'
              console.log(e)
            })
        },
        createShift: function (email, startHour, endHour, date) {
          let start = startHour.concat(":00");
          let end = endHour.concat(":00");
          const params2 = new URLSearchParams();
          params2.append('startHour', start);
          params2.append('endHour', end);
          params2.append('date', date);
          params2.append('employeeEmail', email);
          params2.append('ownerEmail', window.localStorage.getItem('email'));
          params2.append('ownerPassword', window.localStorage.getItem('password'));
          AXIOS.post('/shifts', params2)
          .then(response => {
              this.response = response.data
              window.location.reload()
          })
          .catch(e => {
            this.errorShift = 'Conflict! Start or End hour out of the store opening hours'
            console.log(e)
          })
      },
              addEmployee: function(e,n,s,pw){
           AXIOS.get('/userexists', { params: {"email": this.newEmployeeEmail}})
            .then(response => {
                if(!response.data) {
                    const params = new URLSearchParams();
                    params.append('employeeEmail', e);
                    params.append('employeeName', n);
                    params.append('password', pw);
                    params.append('status', s);
                    params.append('ownerEmail', window.localStorage.getItem('email'));
                    params.append('ownerPassword', window.localStorage.getItem('password'));
                    AXIOS.post('/employee',  params)
                    .then(response => {
                        this.newEmployeeEmail = ''
                        this.employeeName = ''
                        this.employeePassword = ''
                        this.newEmployeeStatus = ''
                        this.response = response.data
                        window.location.reload()
                    })
                    .catch(e => {
                        console.log(JSON.stringify(e))
                        this.error = "Failed to create an account due to server errors"
                    })
                } else {
                    this.error = "Account with this email already exists"
                }
            })
            .catch(e => {
                console.log(JSON.stringify(e))
                this.error = "Failed to verify if an account with this email exists"
            })
        }
    }
}
