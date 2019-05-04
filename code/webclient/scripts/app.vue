toastr.options = {
  "closeButton": true,
  "debug": false,
  "newestOnTop": true,
  "progressBar": true,
  "positionClass": "toast-top-right",
  "preventDuplicates": false,
  "onclick": null,
  "showDuration": "1000",
  "hideDuration": "1000",
  "timeOut": "5000",
  "extendedTimeOut": "2000",
  "showEasing": "swing",
  "hideEasing": "linear",
  "showMethod": "fadeIn",
  "hideMethod": "fadeOut"
};

var baseUrl = "http://localhost:8080/dispatcher/api/dispatcher/";

var netflics = new Vue({
  el: '#wrapper',
  data: {
    // rest request urls
    logInUrl: baseUrl + "logIn",
    logOutUrl: baseUrl + "logOut",

    // application variables
    user: {
      token: null,
      email: null,
      password: null,
      loggedIn: false
    },
    lastViewed: [],
    bestOnes: [],
    mostViewed: [],
  },
  methods: {
    /**
     * resets user data
     */
    resetUser: function () {
      this.user.token = null
      this.user.email = null
      this.user.password = null
      this.user.loggedIn = false
    },

    /**
     * requests a token, given the user's data
     */
    logIn: function () {

      axios.post(this.logInUrl, { body: this.user }, { headers: { "Token": this.user.token } })
        .then(response => {
          data = response.data.token
          if (data !== null && data !== '') {
            this.user.token = data.token
            this.user.loggedIn = true
          } else {
            throw "Authentication server error"
          }
        })
        .catch(e => {
          toastr["error"](e, "Something went wrong :(");
        })
    },

    /**
     * resets the user's data
     */
    logOut: function () {
      axios.post(this.logOutUrl, {}, { headers: { "Token": this.user.token } })
        .then(response => {
          data = response.data
          if (data) {
            this.resetUser()
          } else {
            throw "Authentication server error"
          }
        })
        .catch(e => {
          toastr["error"](e, "Something went wrong :(");
        })
    }
  }
})



