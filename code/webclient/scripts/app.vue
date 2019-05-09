toastr.options = {
  closeButton: true,
  debug: false,
  newestOnTop: true,
  progressBar: true,
  positionClass: "toast-top-right",
  preventDuplicates: false,
  onclick: null,
  showDuration: "1000",
  hideDuration: "1000",
  timeOut: "5000",
  extendedTimeOut: "2000",
  showEasing: "swing",
  hideEasing: "linear",
  showMethod: "fadeIn",
  hideMethod: "fadeOut"
};

var modal = new tingle.modal({
  footer: false,
  stickyFooter: false,
  closeMethods: ["overlay", "button", "escape"],
  closeLabel: "Close",
  onOpen: function () {
    //console.log("modal open");
  },
  onClose: function () {
    players = document.getElementsByClassName("video-js")
    console.log(players);

    for (var i = 0; i < players.length; i++) {
      var player = videojs(players[i].id);
      player.pause();
      console.log("player: "+players[i].id+" paused")
    }
  }
});

var netflics = new Vue({
  el: "#wrapper",
  data: {
    // rest request urls
    posterUrl: "https://image.tmdb.org/t/p/w500",
    baseUrl: "http://localhost:8080/dispatcher/api/dispatcher/",
    // application variables
    user: {
      email: null,
      password: null
    },
    token: null,
    lastViewed: [],
    bestOnes: [],
    mostViewed: []
  },

  methods: {
    /**
     * initializes the application
     */
    init: function () {
      //todo check for lists disappearing
      this.getBestOnes();
      this.getMostViewed();
      // try to retrieve user's data from local storage
      if (localStorage.token) {
        this.token = localStorage.token;
        console.log("token: " + this.token);
        this.getLastViewed();
      }
    },

    /**
     * retrieves the list of the best movies
     */
    getBestOnes: function () {
      var self = this;
      axios.get(this.baseUrl+"movie/bestones")
        .then(response => {
          data = response.data;
          if (data !== null && data !== "") {
            console.log(data);
            self.bestOnes = data;
          } else {
            throw "Informer service error";
          }
        })
        .catch(e => {
          toastr["error"](e, "Something went wrong ಠ_ಠ");
        });
    },

    /**
     * retrieves the list of the most viewed movies
     */
    getMostViewed: function () {
      var self = this;

      axios.get(this.baseUrl+"movie/mostviewed")
        .then(response => {
          data = response.data;
          if (data !== null && data !== "") {
            console.log(data);
            self.mostViewed = data;
          } else {
            throw "Informer service error";
          }
        })
        .catch(e => {
          toastr["error"](e, "Something went wrong  ಠ_ಠ");
        });
    },

    /**
     * retrieves the list of the last viewed movies
     */
    getLastViewed: function () {
      var self = this;
      if (!this.checkToken()) {
        toastr["warning"]("You must be logged in to perform this operation [token not valid]", "Something went wrong  ಠ_ಠ");
        return;
      }
      axios.get(this.baseUrl+this.token+"/movie/lastviewed")
        .then(response => {
          data = response.data;
          if (data !== null && data !== "") {
            console.log(data);
            self.lastViewed = data;
          } else {
            throw "Informer service error";
          }
        })
        .catch(e => {
          toastr["error"](e, "Something went wrong  ಠ_ಠ");
        });
    },

    /**
     * resets user data
     */
    resetUser: function () {
      this.token = null;
      this.user.email = null;
      this.user.password = null;
      localStorage.removeItem("token");
    },

    /**
     * requests a token, given the user's data
     */
    logIn: function () {
      var self = this;
      if (!this.checkUser()) {
        toastr["warning"]("Please insert a valid email and password", "Something went wrong  ಠ_ಠ");
        return;
      }
      axios.post(this.baseUrl+"login", this.user, {})
        .then(response => {
          data = response.data;
          if (data !== null && data !== "") {
            console.log(data);
            self.token = data;
            localStorage.token = this.token;
            toastr["success"]("You have been successfully logged-in", "Success ヽ(´▽`)/");
            this.getLastViewed();
          } else {
            throw "Auth service error";
          }
        })
        .catch(e => {
          toastr["error"](e, "Something went wrong ಠ_ಠ");
        });
    },

    /**
     * resets the user's data
     */
    logOut: function () {
      var self = this;
      if (!this.checkToken()) {
        toastr["warning"]("You must be logged in to perform this operation [token not valid]", "Something went wrong  ಠ_ಠ");
        return;
      }
      axios.post(this.baseUrl+this.token+"/logout")
        .then(response => {
          data = response.data;
          if (data) {
            console.log(data);
            self.resetUser();
            toastr["success"]("You have been successfully logged-out", "Success ヽ(´▽`)/");
          } else {
            throw "Auth serice error";
          }
        })
        .catch(e => {
          toastr["error"](e, "Something went wrong ಠ_ಠ");
        });
    },

    open: function (movie) {
      let playerid = this.randomString(10);
      modal.setContent('<video id="'+playerid+'" class="video-js vjs-default-skin vjs-big-play-centered" controls preload="auto" width="100%" height="100%" poster="'+this.posterUrl+movie.poster+'" data-setup=\'{"fluid": true}\'> <source src="'+this.baseUrl+this.token+'/movie/stream/'+movie.imdbId+'" type="video/mp4" /> <p class="vjs-no-js">To view this video please enable JavaScript, and consider upgrading to a web browser that <a href="http://videojs.com/html5-video-support/" target="_blank">supports HTML5 video</a></p></video>');
      modal.open();
      var player = videojs(playerid);
      player.on('error', function() {
        toastr["warning"]("Try again later", "Something went wrong  ಠ_ಠ");
      });
    },

   randomString: function(length) {
        var chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghiklmnopqrstuvwxyz'.split('');

        if (! length) {
            length = Math.floor(Math.random() * chars.length);
        }

        var str = '';
        for (var i = 0; i < length; i++) {
            str += chars[Math.floor(Math.random() * chars.length)];
        }
        return str;
    },

    /**
     * checks the validity of the token, returns true if the token is valid, false otherwise
     */
    checkToken: function () {
      return this.token !== null && this.token !== "";
    },

    /**
     * checks the validity of the user's credentials, returns true if the user is valid, false otherwise
     */
    checkUser: function () {
      return (
        this.user.email !== null &&
        this.user.email !== "" &&
        this.user.password !== null &&
        this.user.password !== ""
      );
    },

    emptyList: function (list) {
      return list && list.length == 0;
    }
  },

  beforeMount() {
    // initialize the application before the render function is invoked for the first time
    this.init();
  }
});
