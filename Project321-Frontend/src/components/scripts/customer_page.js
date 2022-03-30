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
    name: 'customer_page',
    data () {
      return {
        response: [],
        error: '',
      }
    },
    created: function () {
      console.log(localStorage.getItem('email'))
      console.log(localStorage.getItem('password'))
      console.log(localStorage.getItem('usertype'))
      console.log(localStorage.getItem('status'))
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
        gotoInventory: function (){
          this.$router.push('/product')
       }
    }
    //...
}