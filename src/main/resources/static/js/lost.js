/*网页进度条监测*/
document.onreadystatechange = function () {
    console.log(document.readyState)
    if(document.readyState == 'complete') {
        $('.loading').fadeOut();
    }
};

function winLocationHref(account) {
    if (account != null) {
        //隐藏登录与注册按钮，显示登录账号
        $('#login-btn').css('visibility', 'hidden');
        $('#register-btn').css('visibility', 'hidden');
        $('#account').addClass('account');
        $('#account').html('登录账号：' + account);
        $('#account').attr('title','账号：' + account);

    }
}

//页面加载完毕后触发
window.onload = function () {

    var loginDivDialogTop = 0;

    var hrefUrl = decodeURI(location.search);
    var account = null;
    if (hrefUrl.indexOf("?") != -1) {
        var str = hrefUrl.substr(1); //substr()方法返回从参数值开始到结束的字符串;
        if (str.split("=")[0] == 'name') {
            account = str.split("=")[1];
        }
        console.log(account);
    }
    winLocationHref(account);

    /*js-轮播*/
    var pic=document.getElementById('lunbo-pic').getElementsByTagName('li');
    var list=document.getElementById('lunbo-list').getElementsByTagName('li');
    var prev=document.getElementById('lunbo-prev');
    var next=document.getElementById('lunbo-next');
    var index=0;
    var lunboTimer=null;

    auto();
    /*鼠标滑过图片与下面的...*/
    for(var i=0;i<list.length;i++){
        list[i].index=i;
        list[i].onmouseover=function(){
            clearInterval(lunboTimer);
            Change(this.index);
        };
        list[i].onmouseout=function(){
            auto();
        };
        pic[i].onmouseover=function(){
            clearInterval(lunboTimer);
        };
        pic[i].onmouseout=function(){
            auto();
        };
    }
    prev.onclick=function(){
        clearInterval(lunboTimer);
        index--;
        if(index<0){
            index=list.length-1;
        }
        Change(index);
    };
    next.onclick=function(){
        clearInterval(lunboTimer);
        index++;
        if(index>=list.length){
            index=0;
        }
        Change(index);
    };

    prev.onmousemove=function(){
        clearInterval(lunboTimer);
    };
    prev.onmouseout=function(){
        auto();
    };
    next.onmouseover=function(){
        clearInterval(lunboTimer);
    };
    next.onmouseout=function(){
        auto();
    };

    function Change(curIndex){
        for(var i=0;i<list.length;i++){
            list[i].className="";
            pic[i].className="lunbo-hidden";
        }
        list[curIndex].className="lunbo-on";
        pic[curIndex].className="lunbo-show";
        index=curIndex;
    }
    function auto(){
        lunboTimer=setInterval(function(){
            index++;
            if(index>=list.length){
                index=0
            }
            Change(index);
        },3000);
    }


    /*回到顶部*/
    var backToTop = document.getElementById('back-to-top');
    var timer = null;
    var isTop = true;

    //获取页面可视区域的高度
    var clientHeight = document.documentElement.clientHeight;

    //监听滚动条，只要滚动条滚动的时候，就会触发该事件
    window.onscroll = function() {

        var scrollTopHeight = document.documentElement.scrollTop || document.body.scrollTop;
        if(scrollTopHeight >= clientHeight) {
            backToTop.style.display = "block";
        }else {
            backToTop.style.display = "none";
        }

        if(!isTop) {
            clearInterval(timer);
        }
        isTop = false;
    };

    backToTop.onclick = function () {
        //设置定时器   30毫秒后执行函数
        timer = setInterval(function () {
            //获取滚动条距离顶部的高度   考虑浏览器兼容问题
            var scrollTopHeight = document.documentElement.scrollTop || document.body.scrollTop;
            var speed = Math.floor(-scrollTopHeight / 7);
            document.documentElement.scrollTop = document.body.scrollTop = scrollTopHeight + speed;

            isTop = true;

            if(scrollTopHeight == 0) {
                //清除定时器
                clearInterval(timer);
            }
        },30);
    }

    /*登录*/
    var loginBtn = document.getElementById("login-btn");
    var maskDiv = document.getElementById("mask"); //遮罩层
    var loginDialog = document.getElementById("login-dialog");  //登录框

    loginBtn.onclick = function () {
        //获取页面的高度和宽度
        var pageHeight = document.documentElement.scrollHeight; /*滚动大小，包含滚动内容的高度（元素内容的总高度）*/
        var pageWidth = document.documentElement.scrollWidth;

        maskDiv.style.height = pageHeight + "px";
        maskDiv.style.width = pageWidth + "px";
        //在页面尾部追加元素节点
        //document.body.appendChild(maskDiv);
        maskDiv.style.display = "block";

        //获取可视区域的高度和宽度
        //如果说页面是一个竖向的页面，那么可视区域的宽度和页面的宽度是一样的
        //var viewHeight = document.documentElement.clientHeight;

        loginDialog.style.display = "block";

        //获取弹出框的高度和宽度
        var loginDivHeight = loginDialog.offsetHeight;
        var loginDivWidth = loginDialog.offsetWidth;

        loginDialog.style.left = (pageWidth - loginDivWidth)/2 + "px";
        loginDialog.style.top = (clientHeight - loginDivHeight)/2 + "px";

        loginDivDialogTop = (clientHeight - loginDivHeight)/2;

    };
    /*关闭登录按钮*/
    var closeI = document.getElementById("close");

    function maskAndLoginClose() {
        maskDiv.style.display = "none";
        loginDialog.style.display = "none";

        $('#login-back-msg').html('');
        $('#login-back-msg').css('display','none');

        $('#user-in-account').val('').removeClass('add-red-border');
        $('#user-in-password').val('').removeClass('add-red-border');

        //$('#check').attr('checked',true);
        $('#check').prop({checked:true});
    };

    maskDiv.onclick = closeI.onclick = maskAndLoginClose;

    $('#user-in-account').focus(function () {
        $('#user-in-account').removeClass('add-red-border');
    });
    $('#user-in-password').focus(function () {
        $(this).removeClass('add-red-border');
    });

    /*$('#check').change(function () {
        var loginCheckBox = $(this).is(':checked');
        if(loginCheckBox) {
            console.log('选中'+ ',' + loginCheckBox);
        }else {
            console.log('未选中' + ',' + loginCheckBox);
        }
    });*/

    $('#user-login').click(function () {
        var userAccount = $('#user-in-account').val();
        var userPassword = $('#user-in-password').val();
        $('#login-back-msg').css('top','0');
        if(userAccount == '') {
            $('#user-in-account').animate({left: "-10px"}, 100).animate({left: "10px"}, 100)
                                 .animate({left: "-10px"}, 100).animate({left: "10px"}, 100)
                                 .animate({left: "0px"}, 100);
            $('#user-in-account').addClass('add-red-border');
        }
        if(userPassword == '') {
            $('#user-in-password').animate({left: "-10px"}, 100).animate({left: "10px"}, 100)
                .animate({left: "-10px"}, 100).animate({left: "10px"}, 100)
                .animate({left: "0px"}, 100);
            $('#user-in-password').addClass('add-red-border');
        }
        if(userAccount != '' && userPassword != '') {
            //后台验证账号密码
            $.ajax({
                url: '/api/lost/check-login?account=' + userAccount + '&password=' + userPassword,
                type: 'GET',
                /*dataType: 'json',*/
                async: false,
                success: function (res) {
                    if(res.success == 0) {
                        $('#login-back-msg').html(res.errorMessage);
                        $('#login-back-msg').css('display','block');
                        var tempTop = loginDivDialogTop -46;
                        $("#login-back-msg").animate({top: tempTop + 'px'});
                    }else {
                        window.location.href = 'lost.html?name=' + userAccount;
                        //winLocationHref(userAccount);
                        //maskAndLoginClose();
                    }
                },
                error: function (e) {
                    console.log('check account and password fail. ' + e);
                    $('#login-back-msg').html('异常错误！');
                    $('#login-back-msg').css('display','block');
                    var tempTop = loginDivDialogTop -46;
                    $("#login-back-msg").animate({top: tempTop + 'px'});
                }
            });
        }

    });

    /*信息滚动无间歇*/
    var ulDiv = document.getElementById('scroll-swapper');
    var ul1 = document.getElementById('scroll-loop1');
    var ul2 = document.getElementById('scroll-loop2');
    var timeScroll = 30;
    ul2.innerHTML = ul1.innerHTML;
    
    function scrollUpInfo() {
        if(ulDiv.scrollTop >= ul1.offsetHeight) {
            ulDiv.scrollTop = 0;
        }else {
            ulDiv.scrollTop += 1 ;
        }
    };

    var timerScroll = setInterval(scrollUpInfo, timeScroll);
    ulDiv.onmouseover = function () {
        clearInterval(timerScroll);
    };
    ulDiv.onmouseout = function () {
        timerScroll = setInterval(scrollUpInfo, timeScroll);
    };

    /*信息滚动间歇性*/
    /*var ulDiv = document.getElementById('scroll-swapper');
    ulDiv.innerHTML += ulDiv.innerHTML;
    ulDiv.scrollTop = 0;
    var liCurrentHeight = 106;  //滚动高度
    var timerScroll = null;
    function startScrollUp() {
        ulDiv.scrollTop ++;
        timerScroll = setInterval(scrollUp,30);
    }
    function scrollUp() {
        if(Math.ceil(ulDiv.scrollTop) % liCurrentHeight == 0) {
            clearInterval(timerScroll);
            setTimeout(startScrollUp, 2000);
        }else {
            ulDiv.scrollTop ++;
            if(ulDiv.scrollTop >= ulDiv.scrollHeight/2) {
                ulDiv.scrollTop = 0;
            }
        }//else
    }

    setTimeout(startScrollUp,2000);*/



};