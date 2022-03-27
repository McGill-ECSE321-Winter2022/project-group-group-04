import Vue from "vue"
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
      authentification.setAuthentification(false)
    },
    methods: {
        login: function (username, password) {
          AXIOS.get('/userexists', { params: {"email": this.newUsername}})
            .then(response => {
              if(response.data) {
                AXIOS.get('/login', { params: {"email" : username, "password" : password}})
                  .then(response => {
                      this.$useremail = response.data.email
                      this.$userpassword = response.data.password
                      this.$usertype = response.data.type
                      window.localStorage.setItem('email', response.data.email)
                      window.localStorage.setItem('password', response.data.password)
                      window.localStorage.setItem('usertype', response.data.type)
                      window.localStorage.setItem('status', 'true')
                      authentification.setAuthentification(true)
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
                      console.log(e)
                      console.log(JSON.stringify(e))
                      this.error = "Failed to login, password is wrong"
                  })
              } else {
                this.error = "Failed to login, no account linked to that email"
              }
            })
            .catch(e => {
              console.log(JSON.stringify(e))
              this.error = "Failed to verify if an account with this email exists"
            })
        },
        signup: function() {
          this.$router.push('/signup')
        }
    }
    //...
}