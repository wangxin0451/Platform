# 帐户及登录

## 1. 帐户注册
合作商户帐户注册.需要企业简称,手机号,密码,短信验证码完成注册.
测试时, 可以使用token: `cooperator:pHpP/b+PYAbZ3bcSs7dqsQ==`
### URL及请求方法
POST /api/mobile/coop/register
### 请求参数

| 参数名称 | 说明 | 举例 |
| ------ | ---- | --- |
| shortname | 企业简称 | tomcat |
| phone | 手机号 | 13072705335 |
| password | 密码, 至少6位 | 123456 |
| verifySms| 短信验证码 | 123456 |


### 返回数据
1.请求成功

```
{
    "result": true,
    "message": "",
    "error": "",
    "data": {
        "id": 2,
        "cooperatorId": 0,
        "fired": false,
        "shortname": "tomcat",
        "phone": "13072705335",
        "name": null,
        "gender": false,
        "lastLoginTime": null,
        "lastLoginIp": null,
        "createTime": null,
        "pushId": null,
        "main": false
    }
}

```

2.企业简称已被注册

```
{"result": false,
"message": "企业简称已被注册",
"error": "OCCUPIED_ID",
"data": null}

```

3.手机号格式错误

```
{"result": false,
"message": "手机号格式错误",
"error": "ILLEGAL_PARAM",
"data": null}

```

4.密码至少6位

```
{"result": false,
"message": "密码至少6位",
"error": "ILLEGAL_PARAM",
"data": null}

```
5.验证码错误

```
{"result": false,
"message": "验证码错误",
"error": "ILLEGAL_PARAM",
"data": null}

```


## 2. 帐户登录
### URL及请求方法
POST /api/mobile/coop/login
### 请求参数

| 参数名称 | 说明 | 举例 |
| ------ | ---- | --- |
| shortname | 企业简称 | tomcat |
| phone | 手机号 | 13072705335 |
| password| 密码 | 123456 |

### 返回数据
1.登录成功

```
{
    "result": true,
    "message": "",
    "error": "",
    "data": {
        "reviewCooper": {
            "id": 1,
            "cooperatorId": 1,
            "reviewTime": 1459404430000,
            "reviewerId": 1,
            "remark": "照片不清晰",
            "result": true
        },
        "coopAccount": {
            "id": 1,
            "cooperatorId": 1,
            "fired": false,
            "shortname": "Tom",
            "phone": "13072726003",
            "name": "Tom",
            "gender": true,
            "lastLoginTime": 1459404499031,
            "lastLoginIp": "127.0.0.1",
            "createTime": 1425265871000,
            "pushId": null,
            "main": true
        },
        "cooperator": {
            "id": 1,
            "fullname": null,
            "businessLicense": null,
            "corporationName": null,
            "corporationIdNo": null,
            "bussinessLicensePic": null,
            "corporationIdPicA": null,
            "corporationIdPicB": null,
            "longitude": null,
            "latitude": null,
            "invoiceHeader": null,
            "taxIdNo": null,
            "postcode": null,
            "province": null,
            "city": null,
            "district": null,
            "address": null,
            "contact": null,
            "contactPhone": "13072705000",
            "createTime": 1457677133000,
            "statusCode": 2,
            "orderNum": 2
        }
    }
}

```
2.手机号未注册

```
{"result": false,
"message": "手机号未注册",
"error": "NO_SUCH_USER",
"data": null}

```

3.密码错误

```
{"result": false,
"message": "密码错误",
"error": "PASSWORD_MISMATCH",
"data": null}

```

4.手机号与企业简称不匹配

```
{"result": false,
"message": "手机号与企业简称不匹配",
"error": "NO_SUCH_USER",
"data": null}

```
5.该员工已离职

```
{"result": false,
"message": "该员工已离职",
"error": "USER_FIRED",
"data": null}

```
## 3. 找回密码
通过手机号和短信验证码设定新密码.
### URL及请求方法
POST /api/mobile/coop/resetPassword
### 请求参数

| 参数名称 | 说明 | 举例 |
| ------ | ---- | --- |
| phone | 手机号 | 13072705335 |
| password | 新密码 | 123456 |
| verifySms| 短信验证码 | 123456 |
### 返回数据
1.请求成功

```
{
    "result": true,
    "message": "",
    "error": "",
    "data": null
}

```
2.验证码错误

```
{"result": false,
"message": "验证码错误",
"error": "ILLEGAL_PARAM",
"data": null}
```
3.密码至少6位

```
{"result": false,
"message": "密码至少6位",
"error": "ILLEGAL_PARAM",
"data": null}
```
4.手机号未注册

```
{"result": false,
"message": "手机号未注册",
"error": "NO_SUCH_USER",
"data": null}
```

## 4. 更改密码
已登录的情况下,修改密码
### URL及请求方法
POST /api/mobile/coop/changePassword
### 请求参数

| 参数名称 | 说明 | 举例 |
| ------ | ---- | --- |
| oldPassword| 旧密码 | 123456 |
| newPassword| 新密码 | 111222 |

### 请求Cookie
请求Cookie中必须有有效的autoken.

### 返回数据
1.请求成功

```
{
    "result": true,
    "message": "",
    "error": "",
    "data": null
}

```

2.密码至少6位

```
{"result": false,
"message": "密码至少6位",
"error": "ILLEGAL_PARAM",
"data": null}

```
2.原密码错误

```
{"result": false,
"message": "原密码错误",
"error": "ILLEGAL_PARAM",
"data": null}

```

## 5. 更新个推ID
### URL及请求方法
POST /api/mobile/coop/pushId
### 请求参数

| 参数名称 | 说明 | 举例 |
| ------ | ---- | --- |
| pushId | 个推ID | 9a05f0154913d57ef537fdf51ffc14bd |


## 6.根据主账户查询这个账户下有账户集合
### URL及请求方法
POST /api/mobile/coop/getSaleList

### 返回数据
1.请求成功

```
{
    "result": true,
    "message": "",
    "error": "",
    "data": [
        {
            "id": 1,
            "cooperatorId": 1,
            "fired": false,
            "shortname": "Tom",
            "phone": "13072726003",
            "name": "Tom",
            "gender": true,
            "lastLoginTime": 1425525071000,
            "lastLoginIp": "192.168.1.1",
            "createTime": 1425265871000,
            "pushId": null,
            "main": true
        }
    ]
}

```

2.该账户不是管理账号

```
{
    "result": false,
    "message": "当前账户不是管理账号",
    "error": "",
    "data": null
}

```

## 7.离职员工信息修改
### URL及请求方法
POST /api/mobile/coop/saleFired
### 请求参数

| 参数名称 | 说明 | 举例 |
| ------ | ---- | --- |
| coopAccountId | 合作商户id | 1 |

### 返回数据
1.请求成功

```
{
    "result": true,
    "message": "",
    "error": "",
    "data": {
        "id": 1,
        "cooperatorId": 1,
        "fired": true,
        "shortname": "Tom",
        "phone": "13072726003",
        "name": "Tom",
        "gender": true,
        "lastLoginTime": 1425525071000,
        "lastLoginIp": "192.168.1.1",
        "createTime": 1425265871000,
        "pushId": null,
        "main": true
    }
}

```

2.当前账户不是管理账号

```
{
    "result": false,
    "message": "当前账户不是管理账号",
    "error": "",
    "data": null
}

```
3.商户id不正确

```
{
    "result": false,
    "message": "商户id不正确",
    "error": "",
    "data": null
}}
```
4.该账户不是你的下属账户

```
{
    "result": false,
    "message": "该账户不是你的下属账户",
    "error": "",
    "data": null
}}

```

## 8.管理账户添加账户
### URL及请求方法
POST /api/mobile/coop/addAccount
默认密码123456
### 请求参数

| 参数名称 | 说明 | 举例 |
| ------ | ---- | --- |
| phone | 手机号 | 13072735003 |
| name | 姓名 | Bush |
| gender | 性别 | false |

### 返回数据
1.请求成功

```
{
    "result": true,
    "message": "",
    "error": "",
    "data": {
        "id": 2,
        "cooperatorId": 1,
        "fired": false,
        "shortname": null,
        "phone": "13072735003",
        "name": "Bush",
        "gender": false,
        "lastLoginTime": null,
        "lastLoginIp": null,
        "createTime": 1460012722503,
        "pushId": null,
        "main": false
    }
}

```

2.当前账户不是管理账号

```
{
    "result": false,
    "message": "当前账户不是管理账号",
    "error": "",
    "data": null
}

```
3.手机号已存在

```
{
    "result": false,
    "message": "手机号已存在",
    "error": "",
    "data": null
}

```

## 9.修改账户密码
### URL及请求方法
POST /api/mobile/coop/changeAccountPassword

### 请求参数

| 参数名称 | 说明 | 举例 |
| ------ | ---- | --- |
| coopAccountId | 合作商户id | 1 |
| oldPassword | 老密码 | 123456 |
| newPassword | 修改后的密码 | 123456 |

### 返回数据
1.请求成功

```
{
    "result": true,
    "message": "",
    "error": "",
    "data": null
}

```

2.密码至少6位

```
{
    "result": false,
    "message": "密码至少6位",
    "error": "",
    "data": null
}

```

3.原密码错误

```
{
    "result": false,
    "message": "原密码错误",
    "error": "",
    "data": null
}

```

## 10. 修改员工帐户
### URL及请求方法
`POST /api/mobile/coop/account/{accountId}`

### 请求参数

| 参数名称 | 是否可选 | 说明 | 举例 |
| ------ | ------- | ---- | ---- |
| accountId | 否 | URL占参数, 员工帐ID | 1 |
| phone | 否 | 员工登录手机号 | 18812345678 |
| name | 否 | 员工姓名 | 张三 |
| gender | 否 | 员工性别, false为男, true为女 | false |

### 返回数据
1. 请求成功

    ```
    {
        "result": true,
        "message": "",
        "error": "",
        "data": null
    }
    ```

2. 手机号格式错误

    ```
    {
        "result": false,
        "message": "手机号格式错误, 正确格式: 18812345678",
        "error": "ILLEGAL_PHONE_NO",
        "data": null
    }
    ```
    
3. 仅允许管理员修改员工帐户

    ```
    {
            "result": false,
            "message": "仅允许管理员修改员工帐户",
            "error": "ILLEGAL_OPERATION",
            "data": null
        }
    ```