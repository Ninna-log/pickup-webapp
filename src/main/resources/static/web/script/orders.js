function logout() {
    $.post("/api/logout")
        .done(function () {
            location.replace("/web/pickup.html")
        })
        .fail(function () {
            alert("Ha ocurrido un error, por favor pruebe otra vez")
        })
}