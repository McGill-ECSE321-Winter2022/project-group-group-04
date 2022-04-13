import Vue from "vue"
import axios from 'axios'
var config = require('../../../config')

var frontendUrl = process.env.NODE_ENV === 'production' ? 'https://' + config.build.host : 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = process.env.NODE_ENV === 'production' ? 'https://' + config.build.backendHost : 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: 'login',
    data() {
        return {
            newEmail: '',
            newFirstname: '',
            newLastname: '',
            newPassword: '',
            newPhone: '',
            newStreet: '',
            newTown: '',
            newUnit: '',
            newPostalCode: '',
            response: [],
            error: '',
        }
    },
    created: function() {},
    methods: {
        createCustomerAccount: function() {
            AXIOS.get('/userexists', { params: { "email": this.newEmail } })
                .then(response => {
                    if (!response.data.exists) {
                        var name = this.newFirstname + " " + this.newLastname
                        const params = new URLSearchParams();
                        params.append('email', this.newEmail);
                        params.append('name', name);
                        params.append('password', this.newPassword);
                        params.append('phone', this.newPhone);
                        params.append('town', this.newTown);
                        params.append('street', this.newStreet);
                        params.append('postalcode', this.newPostalCode);
                        params.append('unit', this.newUnit);
                        AXIOS.post('/customers', params)
                            .then(response => {
                                this.newEmail = ''
                                this.newPassword = ''
                                this.newFirstname = ''
                                this.newLastname = ''
                                this.newPhone = ''
                                this.newPostalCode = ''
                                this.newStreet = ''
                                this.newTown = ''
                                this.newUnit = ''
                                this.$router.push('/signup_success')
                            })
                            .catch(e => {
                                console.log(JSON.stringify(e))
                                this.error = "Failed to create an account due to server errors"
                            })
                    } else {
                        this.error = "Account with this email already exists"
                    }
                })
                .catch(e => {
                    console.log(JSON.stringify(e))
                    this.error = "Failed to verify if an account with this email exists"
                })
        },
        signupEnabled: function() {
            if (!this.newEmail || !this.newFirstname || !this.newLastname || !this.newPhone || !this.newPostalCode ||
                !this.newStreet || !this.newTown || !this.newUnit || !(this.newPassword.length > 6)) {
                return true
            }
            return false
        }
    }
    //...
}