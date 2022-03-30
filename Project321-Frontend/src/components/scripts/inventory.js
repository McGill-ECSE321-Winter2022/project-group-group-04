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
    name: 'inventory',
    data () {
      return {
        response: [],
        error: '',
        products: [],
        errorProduct: '',
        search: '',
      }
    },
    created: function () {
        // Get all orders from backEnd
        AXIOS.get('/products', { params: {"customerPage" : 'false'}})
        .then(response => {
          // JSON responses are automatically parsed.
          this.products = response.data
        })
        .catch(e => {
          this.errorProduct = 'There is no products in the system, we broke!'
          console.log(e)
        })
    },
    methods: {
        back: function () {
            this.$router.push('/owner')
        },
    },
    computed: {
        ProductFilter() {
            let selection = []
            if(this.search !== '') {
            selection = this.products.filter(p => 
               p.productName.toLowerCase().includes(this.search.toLowerCase()) ||
               p.priceType.toLowerCase().includes(this.search.toLowerCase()) ||
               p.isAvailableOnline.toLowerCase().includes(this.search.toLowerCase()) ||
               p.stock === Number(this.search)
             )
            } else {
                selection = this.products
            }
            return selection
           }
    }
}