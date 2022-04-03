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
    name: 'employee_page',
    data () {
      return {
        response: [],
        error: '',
        shifts: [],
        orders: [],
        errorOrder: '',

      }
    },
    created: function () {
      // Get all shiftes from backEnd
      console.log(localStorage.getItem('email'))
      console.log(localStorage.getItem('password'))
      AXIOS.get('/shifts/myshifts', { params: {"email" : window.localStorage.getItem('email'), "password" : window.localStorage.getItem('password')}})
      .then(response => {
        // JSON responses are automatically parsed.
        this.shifts = response.data
      })
      .catch(e => {
        console.log(e)
      })

      // Get all incomplete orders from backEnd
      AXIOS.get('/orders/fulfill', { params: {"employeeEmail" : window.localStorage.getItem('email'), "employeePassword" : window.localStorage.getItem('password')}})
      .then(response => {
        // JSON responses are automatically parsed.
        this.orders = response.data
      })
      .catch(e => {
        this.errorOrder = 'There is no active orders to fulfill'
        console.log(e)
      })
    },
    methods: {
        logout: function () {
            this.$useremail = ''
            this.$userpassword = ''
            this.$usertype = ''
            authentification.setAuthentification(false)
            this.$router.push('/')
            window.localStorage.setItem('email', '')
            window.localStorage.setItem('password', '')
            window.localStorage.setItem('usertype', '')
            window.localStorage.setItem('status', 'false')
        },
        fulFill: function (time, date) {
          const params = new URLSearchParams();
          params.append('employeeEmail',window.localStorage.getItem('email'));
          params.append('employeePassword', window.localStorage.getItem('password'));
          params.append('creationDate',date);
          params.append('creationTime',time);
          AXIOS.post('/orders/fulfill', params)
          .then(response => {
            // JSON responses are automatically parsed.
            this.orders = response.data
          })
          .catch(e => {
            console.log(e)
          })
      },
    }
    //...
}