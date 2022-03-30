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
    name: 'owner_page',
    data () {
      return {
        response: [],
        error: '',
        orders: [],
        orderTotal: ''
      }
    },
    created: function () {
        // Get order total from backEnd
        AXIOS.get('/orders/total', { params: {"ownerEmail" : window.localStorage.getItem('email'), "ownerPassword" : window.localStorage.getItem('password')}})
        .then(response => {
          // JSON responses are automatically parsed.
          this.orderTotal = response.data
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
        },
        gotoStoreInfo : function() {
          this.$router.push('/edit_store_info_page')
      },
      gotoOrders : function (){
        this.$router.push('/orders')
     },
     gotoViewEmployee: function (){
      this.$router.push('/view_employee')
   },
   gotoInventory: function (){
    this.$router.push('/inventory')
 }
    }
    //...
}