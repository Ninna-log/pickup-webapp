var app = new Vue({
    el: '#app',
    data: {
        userAuthenticated: null,
        orders: [],
        username: "",
        password: "",
        first_name: "",
        last_name: ""
    },
    methods: {
        login: function () {
            if (app.username.length != 0 && app.password.length != 0) {
                $.post("/api/login", { username: app.username, password: app.password })
                    .done(function () {
                        alert("Bienvenid@!")
                        location.replace('products.html')
                    })
                    .fail(function () {
                        alert("Datos incorrectos")
                    })
            } else {
                alert("Datos erroneos")
            }
        },
        register: function () {
            if (app.username.length != 0 && app.password.length != 0 && app.first_name.length != 0 && app.last_name.length != 0) {
                var emailValidation = /^[a-zA-Z0-9.!#$%&*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
                if (emailValidation.test(app.username) != true) {
                    alert("Por favor, ingrese un usuario valido")
                    app.password = ""
                } else {
                    $.post("/api/v1/users", { userName: app.username, password: app.password, first_name: app.first_name, last_name: app.last_name })
                        .done(function () {
                            alert("Usuario creado")
                            app.login(app.username, app.password)
                        })
                        .fail(function () {
                            alert("Mail en uso")
                            app.username = ""
                            app.password = ""
                        })
                }
            } else {
                alert("Faltan datos")
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
        }
    }
});

app.getData();