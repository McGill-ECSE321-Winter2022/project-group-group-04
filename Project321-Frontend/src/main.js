// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import BootstrapVue from "bootstrap-vue"
import App from './App'
import router from './router'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)
Vue.config.productionTip = false

Vue.prototype.$useremail = ''
Vue.prototype.$userpassword = ''
Vue.prototype.$usertype = ''

const authentification = {
  isAuthentificated() {
    // if(this.$useremail) {
    //   return true
    // }
    // return false
    return this.status
  },
  setAuthentification(status) {
    this.status = status
  }
}
export default authentification

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})
