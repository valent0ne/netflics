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

var netflics = new Vue({
  el: '#wrapper',
  data: {
    loggedIn: false,
    token: null,
    email: "",
    password: "",
    lastViewed: [],
    bestOnes: [],
    mostViewed: [],
  },
  methods: {
    logIn: function () {
      requestBody = 'ggggg',
        axios.post('', {
          body: requestBody,
        })
          .then(response => {/*todo*/ })
          .catch(e => {
            toastr["error"](e, "Something went wrong :(");
          })
    }
  }
})



