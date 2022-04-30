layui.define([], function (exports) {
    var layrouter = {
        version: '0.0.5',
        // 关键词
        key: '',
        // 路由表
        routes: [],
        // 当前 hash
        currentHash: '',
        // 注册路由
        register: function (hash, callback) {
            var that = this;
            if (!hash) {
                return;
            }

            // 给不同的 hash 设置不同的回调函数
            if (typeof hash == 'string') {
                that.routes[hash] = callback || function () {
                };
            } else if (hash instanceof Array) {
                // 路由数组
                for (var i in hash) {
                    that.register.apply(that, [].concat(hash[i]).concat(callback || function () {
                    }));
                }
            }
            return that;
        },
        go: function (hash) {
            var that = this;
            window.location.hash = '#'.concat(that.key).concat(hash || '');
            return that;
        },
        // 刷新
        refresh: function () {
            var that = this;
            // 获取相应的 hash 值
            console.log(location.hash);
            console.log(location.hash.slice(1));
            // 如果存在 hash 则获取, 否则为 /
            that.currentHash = location.hash.slice(1) || '/';
            if (that.currentHash && this.currentHash != '/') {
                // 根据当前 hash 调用对应的回调函数
                that.routes[that.currentHash]();
            }
        },
        // 初始化
        init: function (options) {
            var that = this;

            // 使用自定义关键词
            that.key = options.key || that.key;

            window.addEventListener('load', that.refresh.bind(that), false);
            window.addEventListener('hashchange', that.refresh.bind(that), false);
            return that;
        },

        /**
         * 获取当前路由
         */
        getRouter: function () {
            var that = this;
            // 获取相应的 hash 值
            console.log(location.hash);
            console.log(location.hash.slice(1));
            // 如果存在 hash 则获取, 否则为 /
            that.currentHash = location.hash.slice(1) || '/';
            return that.currentHash;
        },
        // Removes the current page from the session history and navigates to the given URL.
        replace: function (url) {
            window.location.replace(url);
        },
        // Navigate to the given URL.
        href: function (url) {
            window.location.href = url;
        },
        // Reloads the current page.
        reload: function () {
            window.location.reload();
        }
    };
    exports('layrouter', layrouter);
});