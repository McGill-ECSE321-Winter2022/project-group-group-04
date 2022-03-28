import axios, { Axios } from 'axios'
import authentification from '@/main'
var config = require('../../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: 'edit_store_info_page',
    data () {
      return {
        response: [],
        store:[],
        newPhone: '',
        newEmail: '',
        newOpeningHour: '',
        newclosingHour: '',
        newUnit: '',
        newStreet: '',
        newTown: '',
        newPostalCode: '',
        newOutOfTownFee: '',
        error: '',
      }
    },
    created: function () {
      AXIOS.get('store')
      .then(response => {
        // JSON responses are automatically parsed.
        this.store = response.data
        console.log("Store Exists")
        document.getElementById("createStore").style.display="none";
      })
      .catch(e => {
        this.error = "Must create Store first"
        console.log("Store Doesn't Exists")
        document.getElementById("createStore").style.display="block";
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
        }
    }
    //...
}