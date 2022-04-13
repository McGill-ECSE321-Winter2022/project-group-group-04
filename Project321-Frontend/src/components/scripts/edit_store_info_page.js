import axios, { Axios } from 'axios'
import authentification from '@/main'
var config = require('../../../config')

var frontendUrl = process.env.NODE_ENV === 'production' ? 'https://' + config.build.host : 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = process.env.NODE_ENV === 'production' ? 'https://' + config.build.backendHost : 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: 'edit_store_info_page',
    data() {
        return {
            response: [],
            store: [],
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
    created: function() {
        AXIOS.get('store')
            .then(response => {
                // JSON responses are automatically parsed.
                this.store = response.data

                var unit = this.store.address.unit;
                var street = this.store.address.street;
                var town = this.store.address.town;
                var postalCode = this.store.address.postalCode;
                document.getElementById("town").innerHTML = "Unit " + unit + " - " + street + ", " + town + ", " + postalCode;

                console.log("Store Exists")
                document.getElementById("createStore").style.display = "none";
                document.getElementById("store_info").style.display = "block";
            })
            .catch(e => {
                this.error = "Must create Store first"
                console.log("Store Doesn't Exists")
                document.getElementById("createStore").style.display = "block";
                document.getElementById("store_info").style.display = "none";
            })
    },
    methods: {
        back: function() {
            this.$router.push('/owner')
        },
        createStore: function(newTelephone, newEmail, newOpeningHour, newClosingHour, newUnit, newStreet, newTown, newPostalCode, newOutOfTownFee) {
            const params = new URLSearchParams();
            params.append('telephone', this.newTelephone);
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
                .then(response => {
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
                .catch(e => {
                    console.log(JSON.stringify(e))
                    this.error = "Failed to create store, Please try again!"
                })
        },
        edit: function(newOpeningHour, newClosingHour) {
            const params = new URLSearchParams();
            //checks if one of the input parameters is empty and if it is then it keeps the current store time for the missing parameter
            var openingHour = document.getElementById("editOpeningHour").value;
            var closingHour = document.getElementById("editClosingHour").value;
            if (closingHour == "" && openingHour != "") {
                params.append('openingHour', this.newOpeningHour);
                params.append('closingHour', this.store.closingHour);
            } else if (openingHour == "" && closingHour != "") {
                params.append('openingHour', this.store.openingHour);
                params.append('closingHour', this.newClosingHour);
            } else {
                params.append('openingHour', this.newOpeningHour);
                params.append('closingHour', this.newClosingHour);
            }
            params.append('ownerEmail', window.localStorage.getItem('email'));
            params.append('ownerPassword', window.localStorage.getItem('password'));
            AXIOS.post('/store/changeHours', params)
                .then(response => {
                    this.newOpeningHour = ''
                    this.newClosingHour = ''
                    window.location.reload()
                })
                .catch(e => {
                    console.log(JSON.stringify(e))
                    this.error = "Failed to update information, Please try again!"
                })
        }
    }
    //...
}