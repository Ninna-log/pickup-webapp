var app = new Vue({
    el: '#app',
    data: {
        file: "",
        name: "",
        category: "",
        price: null
    },
    methods: {
        handleFileUpload: function () {
            this.file = this.$refs.file.files[0];
            app.file = this.file;
        },
        submitProduct: function() {
            $.post("/api/v1/products/save", { product_name: app.name, price: app.price, category: app.category, photo: app.file })
                .done(function (response) {
                    alert("Product added");
                })
                .fail(function (error) {
                    alert(JSON.parse(error.responseText).error);
                })
        }
    }
});