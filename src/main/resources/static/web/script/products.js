var app = new Vue({
    el: '#app',
    data: {
        userAuthenticated: null,
        orders: [],
        username: "",
        password: "",
    },
    methods: {
        logout: function () {
                    $.post("/api/v1/logout")
                        .done(function () {
                            alert("You're successfully logged out")
                            location.replace("/web/pickup.html")
                        })
                        .fail(function () {
                            alert("There's been an error. Please, try again")
                        })
                },
    }
});

