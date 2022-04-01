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
        newProductName: '',
        newType: '',
        newStock: '',
        newPrice: '',
        newAvailability: '',
        errorAddProduct: '',
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
        refresh: function () {
          window.location.reload()
        },
        addProduct: function(name, stock, price, type, availability) {
        // Add a proudct to backend
        const params = new URLSearchParams();
        params.append('ownerEmail',window.localStorage.getItem('email'));
        params.append('ownerPassword', window.localStorage.getItem('password'));
        params.append('productName',name);
        params.append('stock',stock);
        params.append('price',price);
        params.append('type',type);
        params.append('online',availability);
        AXIOS.post('/products', params)
        .then(response => {
          console.log('add product complete')
          window.location.reload()
        })
        .catch(e => {
          this.errorAddProduct = 'There is a duplicate product with same name in the system'
          console.log(e)
        })
        },
        wait: function (ms) {
          var start = new Date().getTime();
          var end = start;
          while(end < start + ms) {
            end = new Date().getTime();
         }
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