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
import authentification from '@/main'


Vue.use(Router)

function routeLoginGuardian(to, from, next) {
  var status = true
  if(localStorage.getItem('status') === 'true') {
    if (from.name === 'login' || from.name === 'orders' || from.name === 'view_employee') {
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
  if (from.name === 'owner_page') {
    next()
  } else {
    next({ name: 'login' })
  }
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
      beforeEnter: routeLoginGuardian,
    },
    {
      path: '/customer',
      name: 'customer_page',
      component: customer_page,
      beforeEnter: routeLoginGuardian,
    },
    {
      path: '/owner',
      name: 'owner_page',
      component: owner_page,
      beforeEnter: routeLoginGuardian,
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
    }
  ]
})
