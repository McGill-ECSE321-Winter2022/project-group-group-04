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
        newTelephone: '',
        newEmail: '',
        newOpeningHour: '',
        newClosingHour: '',
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
        },
        createStore: function (newTelephone,newEmail,newOpeningHour,newClosingHour,newUnit,newStreet,newTown,newPostalCode,newOutOfTownFee){
          const params = new URLSearchParams();
                    params.append('telephone', this.newPhone);
                    params.append('email', this.newEmail);
                    params.append('openingHour', this.newOpeningHour);
                    params.append('closingHour', this.newClosingHour);
                    params.append('town', this.newTown);
                    params.append('street', this.newStreet);
                    params.append('postalcode', this.newPostalCode);
                    params.append('unit', this.newUnit);
                    params.append('outoftownfee', this.newOutOfTownFee);
                    params.append('ownerEmail', window.localStorage.getItem('email'));
                    params.append('ownerPassword', window.localStorage.getItem('password'));
          AXIOS.post('/store', params)
          .then(response=>{
            this.newTelephone = ''
            this.newEmail = ''
            this.newOpeningHour = ''
            this.newClosingHour = ''
            this.newTown = ''
            this.newStreet = ''
            this.newPostalCode = ''
            this.newUnit = ''
            this.newOutOfTownFee = ''
            window.location.reload()
          })
          .catch(e=>{
            console.log(JSON.stringify(e))
            this.error = "Failed to create store, Please try again!"
          })
        }
    }
    //...
}