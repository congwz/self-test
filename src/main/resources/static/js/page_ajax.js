//总记录数,分页的数据
var num_entries = null,list = null;
//每页显示的记录数
var items_per_page = 10;
var url='/api/lost/page-list' + '?pageNumber=' + 1 + '&pageSize=' + items_per_page;

function pageselectCallback(page_index, jq){
    var pageNumber = page_index + 1;
    url='/api/lost/page-list' + '?pageNumber=' + pageNumber + '&pageSize=' + items_per_page;
    console.log('进入回调函数');
    $('#swapper').empty();

    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (res) {
            list = res.data.data;
            console.log(list);
            for(var j = 0; j < list.length; j ++)
            {

                var divSp = $('<div>').addClass('lost-item');

                var img = $('<img>',{
                    src: list[j].image,
                    alt: list[j].title,
                    title: list[j].title
                });
                var strong = $('<strong>' + list[j].title + '</strong>');
                var aDOM = $('<a>', {
                    href: 'https://www.baidu.com/'
                }).addClass('lost-item-title').append(img).append(strong);
                var div = $('<div>', {

                }).addClass('lost-item-head').append(aDOM);

                var i = $('<i>寻物启事：</i>');
                var span = $('<span>' + list[j].content + '</span>').attr('title',list[j].content);
                var a2 = $('<a>').attr('href','https://y.qq.com/?from=newtab').attr('target','_blank')
                    .append(i).append(span);
                var div2 = $('<div>').addClass('lost-item-info').append(a2);

                var str = list[j].name;
                var p = $('<p>').text(str);
                var span31 = $('<span>' + list[j].create_date + '</span>');
                var span32 = $('<span>&nbsp;发布</span>');
                var p3 = $('<p>').addClass('red-text').append(span31).append(span32);
                var div31 = $('<div>').append(p).append(p3).addClass('person-date');

                var p32 = $('<p>' + list[j].province + '</p>');
                var p33 = $('<p>'+ list[j].city +'</p>');
                var div32 = $('<div>').append(p32).append(p33).addClass('province-city');

                var div3 = $('<div>').addClass('lost-item-foot').append(div31).append(div32).append('<div class="clear"></div>');

                divSp.append(div).append(div2).append(div3).append('<div class="clear"></div>');

                $('#swapper').append(divSp).addClass('swapper');

            }

        },
        error: function (e) {
            console.log('ajax fail! ' + e);
        }
    });

    return false;
}


/**
 * Initialisation function for pagination
 */
function initPagination() {
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (res) {
            console.log('初始化请求默认第一页');
            num_entries = res.data.total;
            list = res.data.data;
            if(num_entries > 0 && list != null) {
                // Create content inside pagination element
                $("#Pagination").pagination(num_entries, {
                    callback: pageselectCallback,
                    items_per_page:items_per_page, // Show only one item per page
                    prev_text: '<<',
                    next_text: '>>',
                    num_display_entries: 2,
                    num_edge_entries: 2
                });
            }else {
                alert("暂无数据！");
            }
        },
        error: function (e) {
            console.log('ajax error!');
        }
    });

}

// When document is ready, initialize pagination
$(document).ready(function(){
    initPagination();
});