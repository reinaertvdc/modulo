var AlertType = Object.freeze({
    SUCCESS: 'success',
    INFO: 'info',
    WARNING: 'warning',
    DANGER: 'danger'
});



showAlert = function (type, headline, message, dismissable) {
    var html = '<div class="alert alert-' + type + '" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button><strong>' + headline + '</strong> ' + message + '</div>';

    var element = document.createElement('div');
    document.getElementById('main').appendChild(element);
    element.outerHTML = html;
};
