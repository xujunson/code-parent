// 判断时候在Iframe框架内,在则刷新父页面
if (self != top) {
    parent.location.reload(true);
    if (!!(window.attachEvent && !window.opera)) {
        document.execCommand("stop");
    } else {
        window.stop();
    }
}

$(function () {
   // 得到焦点
   //  $("#password").focus(function () {
   //      $("#left_hand").animate({
   //          left: "150",
   //          top: " -38"
   //      }, {
   //          step: function () {
   //              if (parseInt($("#left_hand").css("left")) > 140) {
   //                  $("#left_hand").attr("class", "left_hand");
   //              }
   //          }
   //      }, 2000);
   //      $("#right_hand").animate({
   //          right: "-64",
   //          top: "-38px"
   //      }, {
   //          step: function () {
   //              if (parseInt($("#right_hand").css("right")) > -70) {
   //                  $("#right_hand").attr("class", "right_hand");
   //              }
   //          }
   //      }, 2000);
   //  });
   //  // 失去焦点
   //  $("#password").blur(function () {
   //      $("#left_hand").attr("class", "initial_left_hand");
   //      $("#left_hand").attr("style", "left:100px;top:-12px;");
   //      $("#right_hand").attr("class", "initial_right_hand");
   //      $("#right_hand").attr("style", "right:-112px;top:-12px");
   //  });
    // 验证码
    $("#captcha").click(function() {
        var $this = $(this);
        var url = $this.data("src") + new Date().getTime();
        $this.attr("src", url);
    });
    // 登录
    // $('#loginform').form({
    //     url: basePath + '/login',
    //     onSubmit : function() {
    //         progressLoad();
    //         var isValid = $(this).form('validate');
    //         if(!isValid){
    //             progressClose();
    //         }
    //         return isValid;
    //     },
    //     success:function(result){
    //         progressClose();
    //         result = $.parseJSON(result);
    //         if (result.success) {
    //             window.location.href = basePath + '/index';
    //         }else{
    //             // 刷新验证码
    //             $("#captcha")[0].click();
    //             showMsg(result.msg);
    //         }
    //     }
    // });
});
// 登录
function login(){
    var key=$('#key').val();
    var username=$('[name=username]').val();
     var password=$('[name=password]').val();
     var captcha=$('[name=captcha]').val();
      password=encryptByDES(password,key);
      username=encryptByDES(username,key);
      console.log(username);
    progressLoad();
    $.ajax({
        url:basePath + '/login',
        data:{username:username,password:password,captcha:captcha},
        type:'post',
        dataType:'json',
        success:function(result){
            progressClose();
            if (result.success) {
                window.location.href = basePath + '/index';
            }else{
                // 刷新验证码
                $("#captcha")[0].click();
                showMsg(result.msg);
            }
        }
    })
}
function submitForm(){
    //$('#loginform').submit();
    login();
}
function clearForm(){
    $('#loginform').form('clear');
}
//回车登录
function enterlogin(){
    if (event.keyCode == 13){
        event.returnValue=false;
        event.cancel = true;
        //$('#loginform').submit();
        login();
    }
}

//DES加密
function encryptByDES(message, key) {
    var keyHex = CryptoJS.enc.Utf8.parse(key);
    var encrypted = CryptoJS.DES.encrypt(message, keyHex, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    });
    return encrypted.toString();
}