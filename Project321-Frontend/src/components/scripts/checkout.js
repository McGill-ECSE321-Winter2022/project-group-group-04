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
    name: 'checkout',
    data () {
      return {
        response: [],
        error: '',

        yourName :'',
        yourEmail :'',
        yourPassword : '',
        yourAddress :''
      }
    },
    created: function () {
        // Get all orders from backEnd
        AXIOS.get('/customer', { params: {"email" : window.localStorage.getItem('email')}})
        .then(response => {
            this.yourName = response.data.name
            this.yourEmail = response.data.email
            this.yourPassword = response.data.password
            this.yourAddress = response.data.address
        })
        .catch(error => {
            console.log(error)
        })
    },
    methods: {
        back: function () {
            this.$router.push('/customer')
        },
    }
}