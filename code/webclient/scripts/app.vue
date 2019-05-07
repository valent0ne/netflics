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
		posterUrl: "https://image.tmdb.org/t/p/w500",
		logInUrl: baseUrl + "login",
		logOutUrl: baseUrl + "logout",
		// TODO
		bestOnesUrl: baseUrl + "movie/bestmovies",
		mostViewedUrl: baseUrl + "movie/mostviewed",
		lastViewedUrl: baseUrl + "movie/lastviewed",

		// application variables
		user: {
			email: null,
			password: null,
		},
		token: null,
		lastViewed: [],
		bestOnes: [],
		mostViewed: [],
	},

	methods: {
		/**
		 * initializes the application
		 */
		init: function () {
			//todo check for lists disappearing
			this.getBestOnes()
			this.getMostViewed()
			// try to retrieve user's data from local storage
			if (localStorage.token) {
				this.token = localStorage.token;
				console.log('token: ' + this.token)
				this.getLastViewed();
			}
		},

		/**
		 * retrieves the list of the best movies
		 */
		getBestOnes: function () {
			var self = this
			axios.get(this.bestOnesUrl)
				.then(response => {
					data = response.data
					if (data !== null && data !== '') {
						console.log(data)
						self.bestOnes = data
					} else {
						throw "Informer service error"
					}
				})
				.catch(e => {
					toastr["error"](e, "Something went wrong ಠ_ಠ");
				})
		},

		/**
		 * retrieves the list of the most viewed movies
		 */
		getMostViewed: function () {
			var self = this

			axios.get(this.mostViewedUrl)
				.then(response => {
					data = response.data
					if (data !== null && data !== '') {
						console.log(data)
						self.mostViewed = data
					} else {
						throw "Informer service error"
					}
				})
				.catch(e => {
					toastr["error"](e, "Something went wrong ಠ_ಠ");
				})
		},

		/**
		 * retrieves the list of the last viewed movies
		 */
		getLastViewed: function () {
			var self = this
			if (!this.checkToken()) {
				toastr["warning"]("You must be logged in to perform this operation [token not valid]", "Something went wrong ಠ_ಠ")
				return
			}
			axios.get(this.lastViewedUrl, { headers: { "Token": this.token } })
				.then(response => {
					data = response.data
					if (data !== null && data !== '') {
						console.log(data)
						self.lastViewed = data
					} else {
						throw "Informer service error"
					}
				})
				.catch(e => {
					toastr["error"](e, "Something went wrong ಠ_ಠ");
				})
		},

		/**
		 * resets user data
		 */
		resetUser: function () {
			this.token = null
			this.user.email = null
			this.user.password = null
			localStorage.removeItem('token')
		},

		/**
		 * requests a token, given the user's data
		 */
		logIn: function () {
			var self = this
			if (!this.checkUser()) {
				toastr["warning"]("Please insert a valid email and password", "Something went wrong ಠ_ಠ")
				return
			}
			axios.post(this.logInUrl, this.user, {})
				.then(response => {
					data = response.data
					if (data !== null && data !== '') {
						console.log(data)
						self.token = data
						localStorage.token = this.token
						toastr["success"]("You have been successfully logged-in", "Success :)")
						this.getLastViewed()
					} else {
						throw "Auth service error"
					}
				})
				.catch(e => {
					toastr["error"](e, "Something went wrong ಠ_ಠ");
				})
		},

		/**
		 * resets the user's data
		 */
		logOut: function () {
			var self = this
			if (!this.checkToken()) {
				toastr["warning"]("You must be logged in to perform this operation [token not valid]", "Something went wrong ಠ_ಠ")
				return
			}
			axios.post(this.logOutUrl, {}, { headers: { "Token": this.token } })
				.then(response => {
					data = response.data
					if (data) {
						console.log(data)
						self.resetUser()
						toastr["success"]("You have been successfully logged-out", "Success :)")
					} else {
						throw "Auth serice error"
					}
				})
				.catch(e => {
					toastr["error"](e, "Something went wrong ಠ_ಠ");
				})
		},

		/**
		 * checks the validity of the token, returns true if the token is valid, false otherwise
		 */
		checkToken: function () {
			return (this.token !== null && this.token !== '')
		},

		/**
		 * checks the validity of the user's credentials, returns true if the user is valid, false otherwise
		 */
		checkUser: function () {
			return (this.user.email !== null && this.user.email !== '' && this.user.password !== null && this.user.password !== '')
		},

		emptyList: function (list) {
			return (list && list.length == 0)
		}
	},

	beforeMount() {
		// initialize the application before the render function is invoked for the first time
		this.init()
	},
})


/*
// instanciate new modal
var modal = new tingle.modal({
	footer: true,
	stickyFooter: false,
	closeMethods: ['overlay', 'button', 'escape'],
	closeLabel: "Close",
	cssClass: ['custom-class-1', 'custom-class-2'],
	onOpen: function () {
		console.log('modal open');
	},
	onClose: function () {
		console.log('modal closed');
	},
	beforeClose: function () {
		// here's goes some logic
		// e.g. save content before closing the modal
		return true; // close the modal
		return false; // nothing happens
	}
});

// set content
modal.setContent('<h1>here\'s some content</h1>');

// add a button
modal.addFooterBtn('Button label', 'tingle-btn tingle-btn--primary', function () {
	// here goes some logic
	modal.close();
});

// add another button
modal.addFooterBtn('Dangerous action !', 'tingle-btn tingle-btn--danger', function () {
	// here goes some logic
	modal.close();
});

// open modal
modal.open();

// close modal
modal.close();
*/