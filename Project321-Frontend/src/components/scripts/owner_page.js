import axios from 'axios'
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
      }
    },
    created: function () {
    },
    methods: {
        logout: function () {
            this.$useremail = ''
            this.$userpassword = ''
            this.$usertype = ''
            this.$router.push('/')
        }
    }
    //...
}