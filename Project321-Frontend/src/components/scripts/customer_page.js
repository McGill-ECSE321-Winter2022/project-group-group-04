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
    name: 'customer_page',
    data() {
        return {
            responseCart: [],
            errorCart: '',

            yourName: '',
            yourEmail: '',
            yourPassword: '',
            yourAddress: '',
            yourPhone: '',

            yourCarts: [],
            yourCart: '',

            newCartType: '',
            noCart: true,
            emptyCart: true,
            cartItems: [],
            detail: false,
        }
    },
    created: function() {
        // Get the customer information from backEnd
        AXIOS.get('/customer', { params: { "email": window.localStorage.getItem('email') } })
            .then(response => {
                console.log(response.data)
                this.yourName = response.data.name
                this.yourEmail = response.data.email
                this.yourPassword = response.data.password
                this.yourAddress = response.data.address
                this.yourPhone = response.data.phone
                    // this.yourCarts = response.data.carts
                    //this.yourCart = response.data.carts[0]
                    //this.cartItems = this.yourCart.cartItems
                    //if (this.yourCarts.length>0){
                    //  console.log(yourCarts)
                    //   this.noCart = false
                    // }
            })
            .catch(error => {
                console.log(error)
            }),
            // Get the customer's cart
            AXIOS.get('/cart', { params: { "customeremail": window.localStorage.getItem('email'), "customerpassword": window.localStorage.getItem('password') } })
            .then(response => {
                console.log(response.data)
                this.yourCart = response.data
                this.cartItems = this.yourCart.cartItems
                this.noCart = false
            })
            .catch(error => {
                console.log(error)
                this.noCart = true
            })
        AXIOS.get('/cart/history', { params: { "customeremail": window.localStorage.getItem('email'), "customerpassword": window.localStorage.getItem('password') } })
            .then(response => {
                console.log(response.data)
                this.yourCarts = response.data
            })
            .catch(error => {
                console.log(error)
            })
    },
    methods: {
        logout: function() {
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

        create_cart: function() {
            const params2 = new URLSearchParams();
            params2.append('type', this.newCartType);
            params2.append('customeremail', this.yourEmail);
            params2.append('customerpassword', this.yourPassword);
            AXIOS.post('/carts', params2)
                .then(response => {
                    this.yourCart = response.data
                    this.cartItems = this.yourCart.cartItems
                    this.noCart = false
                    this.newCartType = ''
                })
                .catch(e => {
                    this.errorCart = "Cart cannot be made"
                    console.log(e)
                })
        },
        clear_cart: function() {
            const params = new URLSearchParams();
            params.append('customeremail', window.localStorage.getItem('email'))
            params.append('customerpassword', window.localStorage.getItem('password'))
            AXIOS.post('/carts/clear', params)
                .then(response => {
                    window.location.reload()
                })
                .catch(e => {
                    this.errorCart = "Cart cannot be cleared"
                    console.log(e)
                })
        },
        delete_cart: function() {
            const params = new URLSearchParams();
            params.append('customeremail', window.localStorage.getItem('email'))
            params.append('customerpassword', window.localStorage.getItem('password'))
            AXIOS.post('/carts/clear', params)
                .then(response => {
                    AXIOS.post('/carts/delete', params)
                        .then(response => {
                            window.location.reload()
                        })
                        .catch(e => {
                            this.errorCart = "Cart cannot be deleted"
                            console.log(e)
                        })
                })
                .catch(e => {
                    this.errorCart = "Cart cannot be deleted"
                    console.log(e)
                })
        },

        gotoInventory: function() {
            this.$router.push('/product')
        },

        gotoCheckout: function() {
            this.$router.push('/checkout')
        },

        showDetail: function() {
            this.detail = true;
        },
    }
    //...
}