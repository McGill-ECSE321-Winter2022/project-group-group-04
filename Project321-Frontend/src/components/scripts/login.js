import Vue from "vue"
import axios from 'axios'
var config = require('../../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: 'login',
    data () {
      return {
        newUsername: '',
        newPassword: '',
        response: [],
        error: '',
      }
    },
    created: function () {
    },
    methods: {
        login: function (username, password) {
          AXIOS.get('/login', { params: {"email" : username, "password" : password}})
            .then(response => {
                this.$useremail = response.data.email
                this.$userpassword = response.data.password
                this.$usertype = response.data.type
                this.newUsername = ''
                this.newPassword = ''
                if(response.data.type === 'employee') {
                    this.$router.push('/employee')
                } else if(response.data.type === 'customer') {
                    this.$router.push('/customer')
                } else if(response.data.type === 'owner') {
                    this.$router.push('/owner')
                }
            })
            .catch(e => {
                console.log(JSON.stringify(e))
                this.error = "Failed to login, email or password is wrong"
            })
        }
    }
    //...
}