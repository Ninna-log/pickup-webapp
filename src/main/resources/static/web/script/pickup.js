var app = new Vue({
    el: '#app',
    data: {
        userAuthenticated: null,
        orders: [],
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
        getData: function () {
                    fetch('/api/v1/orders')
                        .then(function (res) {
                            if (res.ok) {
                                return res.json();
                            }
                            else {
                                throw new error(res.status)
                            }
                        })
                        .then(function (json) {
                            app.orders = json;
                            app.userAuthenticated = json.customer;
                        })
                },
    }
});

app.getData();