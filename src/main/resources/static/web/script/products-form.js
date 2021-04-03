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
            let form = new FormData();
            form.append("image", this.file);

            var settings = {
              "url": "/api/v1/products/save",
              "method": "POST",
              "timeout": 0,
              "processData": false,
              "mimeType": "multipart/form-data",
              "contentType": false,
              "data": form
            };

            $.ajax(settings).done(function (response) {
              console.log('SUCCESS');
            })
            .fail(function (response) {
              console.log('FAILURE');
            });
        }
    }
});