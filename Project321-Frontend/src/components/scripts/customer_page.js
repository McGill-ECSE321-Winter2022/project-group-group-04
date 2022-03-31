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
        responseCart: [],
        errorCart: '',

        yourName :'',
        yourEmail :'',
        yourPassword : '',
        yourAddress :'',
        yourPhone:'',

        yourCart:[],

        needCart: false,
        newCartType : '',
        emptyCart : true,
        cartItems : [],
      }
    },
    created: function () {
      // Get the customer information from backEnd
      AXIOS.get('/customer', { params: {"email" : window.localStorage.getItem('email')}})
      .then(response => {
        this.yourName = response.data.name
        this.yourEmail = response.data.email
        this.yourPassword = response.data.password
        this.yourAddress = response.data.address
        this.yourPhone = response.data.phone
        this.yourCart = response.data.carts
        if (!this.yourCart.length){
          console.log("This customer has no cart.")
          this.needCart = true
        }
      })
      .catch(error => {
        console.log(error)
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

        create_cart: function(){
          const params2 = new URLSearchParams()
          params2.append('type', this.newCartType);
          params2.append('customeremail',this.yourEmail);
          params2.append('customerpassword',this.yourPassword);
          AXIOS.post('/carts',params2)
            .then(response => {
              this.responseCart = response.data
              this.needCart = False
              console.log(this.responseCart)
            })
            .catch(e => {
              this.errorCart = "Cart cannot be made"
              console.log(e)
            })
        },

        gotoInventory: function (){
          this.$router.push('/product')
        }, 

        gotoCheckout: function (){
          this.$router.push('/checkout')
        }
    }
    //...
}
