(function ($, lenosp) {
    let loadingContainer = null;

    // 显示加载
    lenosp.show = function () {
        if (!loadingContainer) {
            loadingContainer = $('<div>', {id: 'loading-container'});
            loadingContainer.html(`
                <div class="spinner-border"></div>
                <p>Loading...</p>
            `);
            $('body').append(loadingContainer);
        }
    }

    // 销毁加载
    lenosp.hide = function () {
        if (loadingContainer) {
            loadingContainer.remove();
            loadingContainer = null;
        }
    }
})(jQuery, lenosp);