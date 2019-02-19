//var flag = 0;
var submitTag = false;

function getElById(id) {
    return typeof id === 'string'?document.getElementById(id):id;
}
function getLength(str) {
    //str.replace(/[^\x00-xff]/g,'xx');  //非单字节的字符串替换成xx
    return str.replace(/[^\x00-xff]/g,'xx').length;
}
function findSameStr(str,n) {
    var temp = 0;
    for(var i = 0; i<str.length; i++) {
        if(str.charAt(i) == n) {
            temp ++;
        }
    }
    return temp;
}

function setElStyle(elI,elSpan) {
    submitTag = true;
    elI.className = 'tip-error';
    elSpan.style.display = 'inline-block';
    elSpan.style.color = 'red';
}

function setElStyleOK(elI,elSpan) {
    elI.className = 'tip-ok';
    elSpan.style.display = 'none';
    submitTag = false;
}

/*SweetAlert框架*/
function warningAlert(title) {
    swal({
        title: '',
        text: title,
        type: "warning",
        showConfirmButton: false,
        /*confirmButtonText: '关闭',
        confirmButtonColor: '#ccc',*/
        timer: 1500,
        allowOutsideClick: true
    })
}

function errorAlert(title) {
    swal({
        title: '',
        /*text: title,*/
        text: '<stong style="font-weight: bold;">' + title + '</stong>',
        type: "error",
        showConfirmButton: false,
        timer: 1500,
        html: true,
        allowOutsideClick: true
    });
}

function successAlert(title,text) {
    swal({
        title: title,
        text: text,
        type: "success",
        showCancelButton: true,
        confirmButtonText: '登录',
        cancelButtonText: "关闭",
        confirmButtonColor: '#FF7600',
        cancelButtonColor: '#E6E6E6',
        /*animation: "slide-from-bottom",*/
        allowOutsideClick: true,
        closeOnConfirm: false
    },function (isConfirm) {
        if(isConfirm) {
            var account = getElById('name').value;
            window.location.href = 'lost.html?name=' + account;
        }
    });
}


/*后台验证账号*/
function accountVerify(name) {
    var isExist = null;
    $.ajax({
        url: 'http:127.0.0.1:7000/api/lost/account-verify?account=' + name,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (res) {
            isExist = res.data;
        },
        error: function (e) {
            console.log('verify account fail. ' + e);
        }
    });
    return isExist;
}

//重置后设置提示信息
function resetTipInfo(el,info) {
    var s = el.getElementsByTagName('span')[0];
    if(s.innerHTML == '') {
        s.innerHTML = info;
        s.className = 'tip';
        s.style.display = 'inline-block';
    }

}

/*同意注册协议*/
function agreeClick() {
    var cbLabel = document.getElementsByClassName('check-agree');
    var cbLabelColor = cbLabel[0].style.backgroundColor;
    if(cbLabelColor == '' || cbLabelColor == 'rgb(252, 201, 157)') {
        cbLabel[0].style.backgroundColor = '#fff';
        //弹出模态框提示
        $('#my-modal-register').modal();
    }else {
        cbLabel[0].style.backgroundColor = '#FCC99D';
    }
}

/*注册*/
function registerFn() {
    var checkAgreeBox = document.getElementsByClassName('check-agree')[0];
    var url = 'http:127.0.0.1:7000/api/lost/register';
    var account = getElById('name').value;
    var ageOriginal = getElById('age').value;
    var age = ageOriginal == '' ? -1: ageOriginal;
    var radioSexs=document.getElementsByName("sex");
    var sex=-1;
    for(var i=0;i<radioSexs.length;i++){
        if(radioSexs[i].checked==true) {
            var radioSexValue =radioSexs[i].value;
            if(radioSexValue == 'male') {
                sex = 1;
            }else if(radioSexValue == 'female') {
                sex= 0;
            }
            break;
        }
    }

    if(checkAgreeBox.style.backgroundColor == 'rgb(255, 255, 255)') {
        //动画提示
        // if(flag == 1) {
        //     checkAgreeBox.className = 'check-agree' + ' ' + 'check-agree-box';
        //     flag = 2;
        // }else {
        //     checkAgreeBox.className = 'check-agree' + ' ' + 'check-agree-box2';
        //     flag = 1;
        // }

        //弹出模态框提示
        $('#my-modal-register').modal();
    }else if(submitTag) {
        errorAlert("填写信息存在错误，请核对！");
    }else {
        //保存用户注册信息
        $.ajax({
            url: url,
            type: 'POST',
            /*dataType: 'json',*/
            /*data: JSON.stringify({ "user":
                    [{ 'account': name.value, 'password': password.value, 'email': email.value,
                       'qq': qq.value, 'phone': phone.value, 'age':age,'sex':sex }]
            }),*/
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(
                {
                    'account': account,
                    'password': password.value,
                    'email': email.value,
                    'qq': qq.value,
                    'phone': phone.value,
                    'age':age,
                    'sex':sex
                }),
            success: function (res) {
                console.log(JSON.stringify(
                    {
                        'account': account,
                        'password': password.value,
                        'email': email.value,
                        'qq': qq.value,
                        'phone': phone.value,
                        'age':age,
                        'sex':sex
                    }));
                console.log('请求后台注册接口成功');
                if(res.success == 1) {
                    successAlert('注册成功','信息已保存');
                }else if(res.success == 0) {
                    var msg = res.errorMessage;
                    console.log("接口抛出错误信息：" + msg);
                    errorAlert('注册失败');
                }
            },
            error: function (e) {
                console.log(JSON.stringify(
                    {
                        'account': account,
                        'password': password.value,
                        'email': email.value,
                        'qq': qq.value,
                        'phone': phone.value,
                        'age':age,
                        'sex':sex
                    }));
                console.log(e);
                console.log('save register info fail.');
                warningAlert('发生异常错误');
            }
        });
    }//else

}



window.onload = function () {

    /*小屏幕时栅格系统设置不换行*/
    var bpCols2 = document.getElementsByClassName('col-sm-2');
    var bpCols4 = document.getElementsByClassName('col-sm-4');
    var bpCols6 = document.getElementsByClassName('col-sm-6');
    for (var i = 0; i < bpCols2.length; i++) {
        var bpColOriginalClass2 = bpCols2[i].className;
        bpCols2[i].className = bpColOriginalClass2 + ' ' + 'col-xs-2';
    };
    for (var i = 0; i < bpCols4.length; i++) {
        var bpColOriginalClass4 = bpCols4[i].className;
        bpCols4[i].className = bpColOriginalClass4 + ' ' + 'col-xs-4';
    };
    for (var i = 0; i < bpCols4.length; i++) {
        var bpColOriginalClass6 = bpCols6[i].className;
        bpCols6[i].className = bpColOriginalClass6 + ' ' + 'col-xs-6';
    };
    var regBtn = getElById('register'),resBtn = getElById('reset');
    regBtn.classList.remove('col-xs-2');
    resBtn.classList.remove('col-xs-2');

    /*注册表单验证*/
    var name = getElById('name'),
        password = getElById('password'),
        passwordConfirm = getElById('passwordConfirm'),
        email = getElById('email'),
        qq = getElById('qq'),
        phone = getElById('phone');

    var tipInfo = document.getElementsByClassName('tip-info');


    /*账号验证*/
    name.onfocus = function () {
        resetTipInfo(tipInfo[0],'5-20个字符，一个汉字为两个字符。支持数字，字母和下划线');
        tipInfo[0].style.display = 'block';
    };
    //name.onblur = function () {
    name.onkeyup = function () {
        var re = /[^\w\u4e00-\u9fa5]/g;
        var i = tipInfo[0].getElementsByTagName('i');
        var span = tipInfo[0].getElementsByTagName('span');
        var name_length = getLength(this.value);

        //后台验证账号是否已经存在
        var isExist = accountVerify(this.value);

        //含有非法字符
        if(re.test(this.value)) {
            setElStyle(i[0],span[0]);
            span[0].innerHTML = '含有非法字符！';
        }
        //不能为空
        else if(this.value == "") {
            setElStyle(i[0],span[0]);
            span[0].innerHTML = '不能为空！';
        }
        else if(isExist) {
            setElStyle(i[0],span[0]);
            span[0].innerHTML = '账号已注册，请重新填写账号或直接登录！';
        }
        //长度超过25个字符
        else if(name_length >20) {
            setElStyle(i[0],span[0]);
            span[0].innerHTML = '长度超过20个字符！';
        }
        //长度少于5个字符
        else if(name_length <5) {
            setElStyle(i[0],span[0]);
            span[0].innerHTML = '长度少于5个字符！';
        }
        //验证通过
        else {
            //显示验证通过的图标
            setElStyleOK(i[0], span[0]);
        }

    };

    /*密码验证*/
    password.onfocus = function () {
        resetTipInfo(tipInfo[1],'密码长度6～14位，字母区分大小写');
        tipInfo[1].style.display = 'block';
    };
    password.onkeyup  = function () {
        var re = /(?=.*?[#?!@$%^&*-])/g;
        var pwdRank = getElById('pwd-rank'),
            pwdFont = getElById('active');
        //大于6个字符且不超过10个字符-弱  包含特殊字符-一般
        if(this.value.length > 6 && this.value.length <= 10) {
            pwdFont.style.display = 'inline-block';
            if(re.test(this.value)) {
                //一般
                pwdRank.style.backgroundPositionY = '-40px';
                pwdFont.style.backgroundPosition = '-98px -64px';
            }else {
                //弱
                pwdRank.style.backgroundPositionY = '-20px';
                pwdFont.style.backgroundPosition = '-114px -44px';
            }
        }else {
            if(this.value.length == 0) {
                pwdFont.style.display = 'none';
                pwdRank.style.backgroundPosition = '58px 0';
            }else {
                //弱
                pwdFont.style.display = 'inline-block';
                pwdRank.style.backgroundPositionY = '-20px';
                pwdFont.style.backgroundPosition = '-114px -44px';
            }
        }

        if(this.value.length > 10) {
            pwdFont.style.display = 'inline-block';
            if(re.test(this.value)) {
                //大于10个字符且包含特殊字符-强
                pwdRank.style.backgroundPositionY = '-63px';
                pwdFont.style.backgroundPosition = '-98px -106px';
            }else {
                //弱
                pwdRank.style.backgroundPositionY = '-20px';
                pwdFont.style.backgroundPosition = '-114px -44px';
            }
        }

        var re_n = /[^\d]/g;
        //var re_t = /[^a-zA-Z]/g;
        var i = tipInfo[1].getElementsByTagName('i');
        var span = tipInfo[1].getElementsByTagName('span');
        var sameLength = findSameStr(this.value,this.value[0]);
        //不能为空
        if(this.value == '') {
            setElStyle(i[0],span[0]);
            span[0].innerHTML = '不能为空！';
        }

        //不能用相同字符
        else if(sameLength == this.value.length) {
            setElStyle(i[0],span[0]);
            span[0].innerHTML = '不能用相同字符作为密码！';
        }

        //长度应为6-14个字符
        else if(this.value.length < 6 || this.value.length > 14) {
            setElStyle(i[0],span[0]);
            span[0].innerHTML = '长度应为6-14个字符！';
        }

        //不能全是数字
        else if(!re_n.test(this.value)) {
            setElStyle(i[0],span[0]);
            span[0].innerHTML = '不能全是数字！';
        }

        //验证通过 ok
        else {
            /*i[0].className = 'tip-ok';
            span[0].style.display = 'none';
            submitTag = false;*/
            setElStyleOK(i[0], span[0]);
        }
    };

    /*密码确认验证*/
    passwordConfirm.onfocus = function () {
        resetTipInfo(tipInfo[2],'须和上方的密码一致');
        tipInfo[2].style.display = 'block';
    };
    passwordConfirm.onkeyup = function () {
        var i = tipInfo[2].getElementsByTagName('i');
        var span = tipInfo[2].getElementsByTagName('span');
        if(this.value != password.value || this.value.length == 0) {
            setElStyle(i[0],span[0]);
            span[0].innerHTML = '两次输入的密码不一致！';
        }else {
            setElStyleOK(i[0], span[0]);
        }
    };

    /*邮箱验证*/
    email.onfocus = function () {
        resetTipInfo(tipInfo[3],'请输入有效的邮件地址，当密码遗失时凭此领取');
        tipInfo[3].style.display = 'block';
    };
    email.onkeyup =  function () {
        var re = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/g;
        var i = tipInfo[3].getElementsByTagName('i');
        var span = tipInfo[3].getElementsByTagName('span');
        //不能为空
        if(this.value == '') {
            setElStyle(i[0],span[0]);
            span[0].innerHTML = '不能为空！';
        }
        else if(!re.test(this.value)) {
            setElStyle(i[0],span[0]);
            span[0].innerHTML = '邮箱格式错误！';
        }
        else {
            setElStyleOK(i[0], span[0]);
        }
    };

    qq.onkeyup = function () {
        var re = /^[1-9][0-9]{4,10}$/;
        var qqDiv = getElById('qq-number');
        var i = tipInfo[4].getElementsByTagName('i');
        var span = tipInfo[4].getElementsByTagName('span');
        if(this.value != '') {
            qqDiv.style.display = 'block';
            if(!re.test(this.value)) {
                setElStyle(i[0],span[0]);
                span[0].innerHTML = 'QQ账号输入有误！';
            }else {
                setElStyleOK(i[0], span[0]);
            }
        }else {
            qqDiv.style.display = 'none';
            submitTag = false;
        }
    }

    phone.onkeyup = function () {
        var re = /^1[34578]\d{9}$/;
        var phoneDiv = getElById('phone-number');
        var i = tipInfo[5].getElementsByTagName('i');
        var span = tipInfo[5].getElementsByTagName('span');
        if(this.value != '') {
            phoneDiv.style.display = 'block';
            if(!re.test(this.value)) {
                setElStyle(i[0],span[0]);
                span[0].innerHTML = '手机号输入有误！';
            }else {
                setElStyleOK(i[0], span[0]);
            }
        }else {
            phoneDiv.style.display = 'none';
            submitTag = false;
        }
    }

    /*重置*/
    getElById('reset').onclick = function() {
        getElById('pwd-rank').style.backgroundPosition = '58px 0';
        getElById('active').style.display = 'none';
        for(var i = 0; i < tipInfo.length; i++) {
            tipInfo[i].style.display = 'none';
            var elI = tipInfo[i].getElementsByTagName('i');
            var elSpan = tipInfo[i].getElementsByTagName('span');
            elI[0].className = '';
            elSpan[0].style.display = 'none';
            elSpan[0].innerHTML = '';
            elSpan[0].style.color = '';
        }
        //同意注册协议清空选择
        document.getElementsByClassName('check-agree')[0].style.backgroundColor = '#FCC99D';
        submitTag = false;
    };






};