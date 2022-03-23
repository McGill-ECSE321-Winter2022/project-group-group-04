import Vue from 'vue'
import Router from 'vue-router'
import login from '@/components/login'
import employee_page from '@/components/employee_page'
import customer_page from '@/components/customer_page'
import owner_page from '@/components/owner_page'

Vue.use(Router)

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
      beforeEnter: (to, from, next) => {
        if (from.name === 'login' && this.$useremail) {
          next()
        } else {
          next({ name: 'login' })
        }
      }
    },
    {
      path: '/customer',
      name: 'customer_page',
      component: customer_page,
      beforeEnter: (to, from, next) => {
        if (from.name === 'login' && this.$useremail) {
          next()
        } else {
          next({ name: 'login' })
        }
      }
    },
    {
      path: '/owner',
      name: 'owner_page',
      component: owner_page,
      beforeEnter: (to, from, next) => {
        if (from.name === 'login' && this.$useremail) {
          next()
        } else {
          next({ name: 'login' })
        }
      }
    }
  ]
})
