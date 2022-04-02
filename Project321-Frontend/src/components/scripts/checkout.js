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
        yourAddress :'',
        yourCarts:[],
        yourCart:'',
        cartItems:[],
        totalPrice :'',
        shippingPrice: '',
        store: [],

        timeSlots :[],

        selectedTimeSlot :'',
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
            this.yourCarts = response.data.carts
            this.yourCart = response.data.carts[0]
            this.cartItems = this.yourCart.cartItems

            ///checks if customer should get free shipping
            if(this.yourCart.shoppingType == "Pickup"){
                this.shippingPrice = 0;
            }
            else{
                if(this.yourAddress.town.toLowerCase() == this.store.address.town.toLowerCase()){
                    this.shippingPrice = 0
                }
                else{
                    this.shippingPrice = this.store.outOfTownFee
                }
            }
            //calculates the item total price then add shipping cost at the end
            this.totalPrice=0
            for(let i = 0; i < this.cartItems.length; i++){
                this.itemPrice = this.cartItems[i].product.price
                this.itemQty = this.cartItems[i].quantity
                this.totalPrice += this.itemPrice * this.itemQty
            }
            this.totalPrice+=this.shippingPrice
        })
        .catch(error => {
            console.log(error)
        })

        AXIOS.get('store')
        .then(response =>{
            this.store = response.data
        })
        .catch(error => {
            console.log(error)
        })

        

        AXIOS.get('/availabletimeslots', {params: {}})
            .then(response =>{
                this.timeSlots = response.data
            })
            .catch(error => {
                console.log(error)
            })
    },

    methods: {
        selectTimeSlot : function(timeslot){
            const params = new URLSearchParams();
            params.append('customeremail',this.yourEmail);
            params.append('customerpassword',this.yourPassword);
            params.append('timeslotdate',timeslot.date);
            params.append('timeslotstarttime',timeslot.startTime);
            params.append('timeslotendtime',timeslot.endTime);
            AXIOS.post('/carts/timeslot',params)
                .then(response =>{
                    console.log(response.data.timeSlot)
                    this.selectedTimeSlot = ''
                })
                .catch(e => {
                    console.log(e)
                })
        },
        back: function () {
            this.$router.push('/customer')
        },
    }
}