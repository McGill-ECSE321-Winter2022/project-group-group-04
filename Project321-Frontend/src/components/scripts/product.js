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
    name: 'product',
    data () {
      return {
        response: [],
        error: '',
        products: [],
        errorProduct: '',
        search: '',
        newQuantity:[],
        items: [],
      }
    },
    created: function () {
        // Get all orders from backEnd
        AXIOS.get('/products', { params: {"customerPage" : 'true'}})
        .then(response => {
          // JSON responses are automatically parsed.
          this.products = response.data
          this.products.sort((a, b) => a.productName.localeCompare(b.productName))
        })
        .catch(e => {
          this.errorProduct = 'There is no products in the system, we broke!'
          console.log(e)
        })
        // Get the customer's cart
        AXIOS.get('/cart', { params: {"customeremail" : window.localStorage.getItem('email'), "customerpassword" : window.localStorage.getItem('password')}})
        .then(response => {
          console.log(response.data)
          this.items = response.data.cartItems
        })
        .catch(error => {
          console.log(error)
        })
    },
    methods: {
        back: function () {
            this.$router.push('/customer')
        },
        toCart: function (p, q) {
            const params = new URLSearchParams();
            params.append('useremail',window.localStorage.getItem('email'));
            params.append('userpassword', window.localStorage.getItem('password'));
            params.append('productname',p);
            params.append('quantity',q);
            AXIOS.post('/cart/item', params)
            .then(response => {
                this.response = response.data
                console.log('successfully added to cart')
                window.location.reload()
            })
            .catch(e => {
              console.log(e)
            })
        },
    },
    computed: {
        ProductFilter() {
            this.newQuantity = []
            let selection = []
            if(this.search !== '') {
            selection = this.products.filter(p => 
               p.productName.toLowerCase().includes(this.search.toLowerCase()) ||
               p.priceType.toLowerCase().includes(this.search.toLowerCase()) ||
               p.isAvailableOnline.toLowerCase().includes(this.search.toLowerCase()) ||
               p.price <= Number(this.search)
             )
            } else {
                selection = this.products
            }
            return selection
           },
    }
}