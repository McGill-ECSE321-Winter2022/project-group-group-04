import Vue from 'vue'
import Router from 'vue-router'
import login from '@/components/login'
import employee_page from '@/components/employee_page'
import customer_page from '@/components/customer_page'
import owner_page from '@/components/owner_page'
import signup from '@/components/signup'
import edit_store_info_page from '@/components/edit_store_info_page'
import signup_success from '@/components/signup_success'
import orders from '@/components/orders'
import view_employee from '../components/view_employee'
import view_timeslot from '../components/view_timeslot'
import inventory from '@/components/inventory'
import product from '@/components/product'
import checkout from '@/components/checkout'
import checkout_success from '@/components/checkout_success'
import authentification from '@/main'


import axios from 'axios'
var config = require('../../config')

var frontendUrl = process.env.NODE_ENV === 'production' ? 'https://' + config.build.host : 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = process.env.NODE_ENV === 'production' ? 'https://' + config.build.backendHost : 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

var userType = '';


Vue.use(Router)

function routeLoginGuardian(to, from, next) {
  var status = true
  if(localStorage.getItem('status') === 'true') {
    if (from.name === 'login' || from.name === 'orders' || from.name === 'view_employee' || from.name === 'inventory' || from.name === 'product'|| from.name === 'checkout'|| from.name === 'checkout_success' || from.name === 'edit_store_info_page' || from.name === 'customer_page') {
      status = false
      next()
    } 
  }
  if(status) {
    next({ name: 'login' })
  }
}

function routeSignupSuccessGuardian(to, from, next) {
  if (from.name === 'signup') {
    next()
  } else {
    next({ name: 'login' })
  }
}

function routeOwnerGuardian(to, from, next) {
  AXIOS.get('/login', { params: {"email" : localStorage.getItem('email'), "password" : localStorage.getItem('password')}})
  .then(response => {
    if(response.data.type === 'owner') {
    next()
  }
  })
  .catch(e => {
    next({ name: 'login' })
  })
}

function routeEmployeeGuardian(to, from, next) {
  AXIOS.get('/login', { params: {"email" : localStorage.getItem('email'), "password" : localStorage.getItem('password')}})
  .then(response => {
    if(response.data.type === 'employee') {
    next()
  }
  })
  .catch(e => {
    next({ name: 'login' })
  })
}

function routeCustomerGuardian(to, from, next) {
  AXIOS.get('/login', { params: {"email" : localStorage.getItem('email'), "password" : localStorage.getItem('password')}})
  .then(response => {
    if(response.data.type === 'customer') {
    next()
  }
  })
  .catch(e => {
    next({ name: 'login' })
  })
}

export default new Router({
  routes: [
    {
      path: '/',
      name: 'login',
      component: login
    },
    {
      path: '/employee',
      name: 'employee_page',
      component: employee_page,
      beforeEnter: routeEmployeeGuardian,
    },
    {
      path: '/customer',
      name: 'customer_page',
      component: customer_page,
      beforeEnter: routeCustomerGuardian,
    },
    {
      path: '/owner',
      name: 'owner_page',
      component: owner_page,
      beforeEnter: routeOwnerGuardian,
    },
    {
      path: '/signup',
      name: 'signup',
      component: signup,
    },
    {
      path: '/signup_success',
      name: 'signup_success',
      component: signup_success,
      beforeEnter: routeSignupSuccessGuardian,
    },
    {
      path: '/edit_store_info_page',
      name: 'edit_store_info_page',
      component: edit_store_info_page,
      beforeEnter: routeOwnerGuardian,
    },
    {
      path: '/orders',
      name: 'orders',
      component: orders,
      beforeEnter: routeOwnerGuardian,
    },
    {
      path: '/view_employee',
      name: 'view_employee',
      component: view_employee,
      beforeEnter: routeOwnerGuardian,
    },
    {
      path: '/view_timeslot',
      name: 'view_timeslot',
      component: view_timeslot,
      beforeEnter: routeOwnerGuardian,
    },
    {
      path: '/inventory',
      name: 'inventory',
      component: inventory,
      beforeEnter: routeOwnerGuardian,
    },
    {
      path: '/product',
      name: 'product',
      component: product,
      beforeEnter: routeCustomerGuardian,
    }, 
    {
      path: '/checkout',
      name: 'checkout',
      component: checkout,
      befroreEnter: routeCustomerGuardian,
    },
    {
      path: '/checkout_success',
      name: 'checkout_success',
      component: checkout_success,
      befroreEnter: routeCustomerGuardian,
    },
  
  ]
})
