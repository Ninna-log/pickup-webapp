var app = new Vue({
    el: '#app',
    data: {
        username: "",
        password: "",
    },
    methods: {
        login: function () {
            if (app.username.length != 0 && app.password.length != 0) {
                $.post("/api/v1/login", { username: app.username, password: app.password })
                    .done(function () {
                        alert("You're successfully logged in!")
                        location.reload()
                    })
                    .fail(function () {
                        alert("Incorrect data")
                    })
            } else {
                alert("Missing data")
            }
        },
        showRegisterForm: function(){
        $('.login_form').hide('toggle');
          $('.signup_form').show('toggle');
       },
    }
});
