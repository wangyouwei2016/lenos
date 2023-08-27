/**
 * 下拉表格
 */
(function ($) {
    /**
     * options 所有datatable配置
     * @param options
     * @returns {*}
     */
    $.fn.dropdownTable = function (options) {
        const defaults = {
            //可传递ajax
            ajax: null,
            //可传递数组 和ajax互斥
            data: [],
            //列定义
            columns: [],
            //datable 所有属性可通过参数options来定义
            //选择表格行事件
            onSelect: function (rowData) {
                //默认点击表格行关闭弹出层
                return true;
            },
            /**
             * 面板展示前回调
             * @param rowData
             * @returns {boolean} true正常显示，false 不显示
             */
            onShowPanel: function () {
                return true;
            },
            /**
             * 面板隐藏前会掉皮
             * @returns {boolean} 返回true正常隐藏，false 不隐藏
             */
            onHidePanel: function () {
                return true;
            }
        };

        let settings = $.extend({}, defaults, options);

        return this.each(function () {
            const input = this;
            let dataTable;


            const createTable = function () {
                const container = $('<div class="table-container dropdown-menu" aria-labelledby="tableInput">');
                const table = $('<table class="dataTable display" style="width:100%;">');
                container.append(table);
                $(input).after(container);

                dataTable = table.DataTable(settings);

                const populateInput = function (e, rowData) {
                    if (rowData === undefined) {
                        return;
                    }
                    $(input).val(rowData[1]);
                    const onSelectResult = settings.onSelect(e, rowData);
                    if (onSelectResult === undefined || onSelectResult) {
                        const hidePanel = settings.onHidePanel(e);
                        if (hidePanel === undefined || hidePanel) {
                            $(input).siblings('.table-container').removeClass('show');
                        }
                    }
                };

                dataTable.on('click', 'tr', function (e) {
                    e.stopPropagation();
                    const rowData = dataTable.row(this).data();
                    populateInput(e, rowData);
                });

                const tableContainer = $(input).siblings('.table-container');

                tableContainer.on('click', function (e) {
                    e.stopPropagation(); // Prevent event from bubbling up
                });


            };

            $(input).on('input', function (e) {
                const searchText = e.target.value;

                const filteredData = settings.data.filter(item =>
                    item.name.toLowerCase().includes(searchText.toLowerCase())
                );

                dataTable.clear();
                dataTable.rows.add(filteredData);
                dataTable.draw();
                if (settings.onHidePanel()) {
                    $(input).siblings('.table-container').toggleClass('show', filteredData.length > 0);
                }
            });

            $(input).on('click', function (e) {
                e.stopPropagation();
                const showPanel = settings.onShowPanel(e);
                if (!dataTable && (showPanel === undefined || showPanel)) {
                    createTable();
                }

                $(input).siblings('.table-container').toggleClass('show');
            });

            $(document).on('click', function (e) {
                if (!$(e.target).closest('.dropdown').length) {
                    const hidePanel = settings.onHidePanel(e);
                    if (hidePanel === undefined || hidePanel) {
                        $(input).siblings('.table-container').removeClass('show');
                    }
                }
            });
        });
    };
})(jQuery);
