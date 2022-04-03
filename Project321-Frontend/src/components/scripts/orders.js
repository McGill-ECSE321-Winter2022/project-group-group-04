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
    name: 'orders',
    data () {
      return {
        response: [],
        error: '',
        orders: [],
        errorOrder: '',
        instorePurchases: [],
        errorInstorePurchases: '',
      }
    },
    created: function () {
        // Get all orders from backEnd
        AXIOS.get('/orders', { params: {"ownerEmail" : window.localStorage.getItem('email'), "ownerPassword" : window.localStorage.getItem('password')}})
        .then(response => {
          // JSON responses are automatically parsed.
          this.orders = response.data
        })
        .catch(e => {
          this.errorOrder = 'There is no orders in the System yet'
          console.log(e)
        })
        // Get all orders from backEnd
        AXIOS.get('/instorepurchases')
        .then(response => {
          // JSON responses are automatically parsed.
          console.log(response)
          this.instorePurchases = response.data
        })
        .catch(e => {
          this.errorInstorePurchases = 'There is no instore purchases in the system yet'
          console.log(e)
        })
    },
    methods: {
        back: function () {
            this.$router.push('/owner')
        }
    }
    //...
}