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
    name: 'checkout_success',
    data() {
        return {
            response: [],
            error: '',

            yourName: '',
            yourEmail: '',
            yourPassword: '',
            yourAddress: '',
            yourCarts: '',
            yourRecentCart: '',

        }
    },
    created: function() {
        // Get all orders from backEnd
        AXIOS.get('/customer', { params: { "email": window.localStorage.getItem('email') } })
            .then(response => {
                this.yourName = response.data.name
                this.yourEmail = response.data.email
                this.yourPassword = response.data.password
                this.yourAddress = response.data.address
                this.yourCarts = response.data.carts
                this.yourRecentCart = this.yourCarts[this.yourCarts.length - 1]

                console.log(response.data)
            })
            .catch(error => {
                console.log(error)
            })

    },

    methods: {

        back: function() {
            this.$router.push('/customer')
        },
    }
}