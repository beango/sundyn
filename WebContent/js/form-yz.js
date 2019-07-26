/**
 * Created by Administrator on 16-5-18.
 */
$(function(){
    $.mvalidateExtend({
        chepai:{
            required : true,
            pattern : /^[A-Z_0-9]{5}$/
        },
        shouji:{
            required : true,
            pattern : /^1[3|4|5|7|8]\d{9}$/
        },
        chepai_total:{
            required : true,
            pattern : /^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$/
        },
        gongsi:{
            required : true,
            pattern : /^[\u4e00-\u9fa5]+$/
        },
        xing:{
            required : true,
            pattern : /^[\u4e00-\u9fa5]+$/
        },
        ming:{
            required : true,
            pattern : /^[\u4e00-\u9fa5]+$/
        },
        sfz:{
            required : true,
            pattern : /(^\d{15}$)|(^\d{17}(\d|X)$)/
        },
        jiemeng:{
            required : true,
            pattern : /^[\u4e00-\u9fa5]+$/
        },
        qq:{
            required : true,
            pattern : /^[1-9][0-9]{4,}$/
        },
        menpai:{
            required : true,
            pattern : /^[A-Z_0-9]{4}$/
        },
        zgzi:{
            required : true,
            pattern : /^[\u4e00-\u9fa5]{3,}$/
        },
        nickname:{
            required : true
        },
        birthday:{
            required : true
        },
        work_situation:{
            required : true
        },
        email:{
            required : true,
            pattern : /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/
        },
        password: {
            required: true,
            minlength: 6
        },
        xingming:{
            required : true,
            pattern : /^[\u4e00-\u9fa5]+$/
        },
        chusheng:{
            required : true,
            pattern : /^[\u4e00-\u9fa5]+$/
        },
        zhanshi:{
            required : true,
            pattern : /^[\u4e00-\u9fa5]+$/
        },
        dianhua:{
            required : true,
            pattern : /^((0\d{2,3})-)?(\d{6,8})(-(\d{3,}))?$/
        },
        biming:{
            required : true,
            pattern : /^[\u4e00-\u9fa5]+$/
        }

    });
    $("#formyz").mvalidate({
        type:1,
        onKeyup:true,
        sendForm:true,
        firstInvalidFocus:false,
        descriptions:{
            username:{
                required : '请输入用户名'
            },
            nickname:{
                required : '请输入昵称'
            },
            birthday:{
                required : '请选择生日'
            },
            work_situation:{
                required : '请输入工作状况'
            },
            email:{
                required : '请输入邮箱',
                pattern :  '邮箱格式不正确'
            },
            chepai:{
                required : '请输入车牌',
                pattern :  '车牌号码格式不正确'
            },
            chepai_total:{
                required : '请输入车牌',
                pattern :  '车牌号码格式不正确'
            },
            shouji:{
                required : '请输入手机号码',
                pattern :  '手机号码格式不正确'
            },
            gongsi:{
                required : '请输入公司名称',
                pattern :  '公司名称格式不正确'
            },
            xing:{
                required : '请输入姓氏',
                pattern :  '姓氏格式不正确'
            },
            ming:{
                required : '请输入名字',
                pattern :  '名字格式不正确'
            },
            sfz:{
                required : '请输入身份证',
                pattern :  '身份证格式不正确'
            },
            jiemeng:{
                required : '请输入梦境',
                pattern :  '梦境格式不正确'
            },
            qq:{
                required : '请输入QQ号',
                pattern :  'QQ号格式不正确'
            },
            menpai:{
                required : '请输入门牌号',
                pattern :  '门牌号格式不正确'
            },
            zgzi:{
                required : '请输入三个汉字',
                pattern :  '请输入三个汉字'
            },
            password:{
                required : '请输入密码',
                pattern :  '密码应在6位或6位字符以上'
            },
            confirmpassword:{
                required : '请再次输入密码',
                pattern : '两次密码不一样'
            },
            age : {
                required : '请输入年龄',
                pattern : '年龄格式不正确'
            },
            address:{
                required : '请选择地址'
            },
            intro:{
                required : '请输入个人介绍'
            },
            favourite:{
                required : '请选择兴趣爱好'
            },
            sex:{
                required : '请选择性别'
            },
            captchacode:{
                required : '请输入验证码',
                pattern : '您输入的验证码不正确'
            },
            xingming:{
                required : '请输入姓名',
                pattern : '您输入的姓名不正确'
            },
            chusheng:{
                required : '请输入出生地',
                pattern : '您输入的出生地不正确'
            },
            zhanshi:{
                required : '请输入占事',
                pattern : '您输入的占事不正确'
            },
            dianhua:{
                required : '请输入电话',
                pattern : '您输入的电话不正确'
            },
            biming:{
                required : '请输入笔名',
                pattern : '您输入的笔名不正确'
            }

        }
    });
})
