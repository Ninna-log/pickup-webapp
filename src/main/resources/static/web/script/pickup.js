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
                        $.post("/api/login", { username: app.username, password: app.password })
                            .done(function () {
                                alert("You're successfully logged in!")
                                location.replace('products.html')
                            })
                            .fail(function () {
                                alert("Incorrect data")
                            })
                    } else {
                        alert("Missing data")
                    }
        },
        getData: function (){
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
        }
     }
});

app.getData();


