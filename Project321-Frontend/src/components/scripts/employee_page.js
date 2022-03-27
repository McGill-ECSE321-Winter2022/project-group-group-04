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
        shifts: []
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
        }
    }
    //...
}