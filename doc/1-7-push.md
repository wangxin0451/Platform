# 技师端推送消息

## 1. 技师认证
当后台对技师提交的认证内容进行认证操作时, 推送给技师的消息.

### a. 认证通过

```
{
    "action": "VERIFICATION_SUCCEED",
    "title": "你已通过技师资格认证"
}
```


### b. 认证失败

```
{
    "action": "VERIFICATION_FAILED",
    "title": "你的技师资格认证失败,请更新信息后重新申请认证"
}
```


## 2. 推送新订单
当有商户创建订单或后台管理员新建订单时,所有技师收到的推送消息.

```
{
    "action": "NEW_ORDER",
    "title": "你收到新订单推送消息",
    "order": {
         "id": 5,
         "orderNum": "16031109BN39AJ",
         "orderType": 4,
         "photo": null,
         "orderTime": null,
         "addTime": 1457659791000,
         "creatorName": null,
         "contactPhone": null,
         "positionLon": null,
         "positionLat": null,
         "remark": null,
         "mainTech": null,
         "secondTech": null,
         "mainConstruct": null,
         "secondConstruct": null,
         "comment": null,
         "cooperator": {
             "id": 1,
             "fullname": "非常历害的公司",
             "businessLicense": null,
             "corporationName": "王大拿",
             "corporationIdNo": null,
             "bussinessLicensePic": null,
             "corporationIdPicA": null,
             "corporationIdPicB": null,
             "longitude": "114.287685",
             "latitude": "30.639203",
             "invoiceHeader": null,
             "taxIdNo": null,
             "postcode": null,
             "province": "湖北省",
             "city": "武汉市",
             "district": "洪山区",
             "address": "软件园中路",
             "contact": "王大拿",
             "contactPhone": "13072705000",
             "createTime": 1457677133000,
             "statusCode": 1,
             "orderNum": 2
         },
         "creator": {
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
         },
         "status": "NEWLY_CREATED"
     }    
}
```

## 3. 合作邀请

### a. 邀请消息
当有技师向你发出合作邀请时, 被邀技师收到的推送消息.

```
{
    "action": "INVITE_PARTNER",
    "title": "张三向你发起订单合作邀请",
    "order": {
        "id": 47,
        "orderNum": "20160303120243AS54GE",
        "orderType": 0,
        "photo": null,
        "orderTime": null,
        "addTime": 1456977763758,
        "creatorType": 0,
        "creatorId": 0,
        "creatorName": null,
        "contactPhone": null,
        "positionLon": null,
        "positionLat": null,
        "remark": null,
        "mainTechId": 1,
        "secondTechId": 0,
        "status": "NEWLY_CREATED"
    },
    "owner": {
        "id": 1,
        "phone": "18812345678",
        "name": "tom",
        "gender": null,
        "avatar": null,
        "idNo": "422302198608266313",
        "idPhoto": "/etc/a.jpg",
        "bank": "027",
        "bankAddress": "光谷",
        "bankCardNo": "88888888888",
        "verifyAt": null,
        "requestVerifyAt": null,
        "verifyMsg": null,
        "lastLoginAt": 1456195103000,
        "lastLoginIp": "127.0.0.1",
        "createAt": 1455724800000,
        "skill": "1",
        "pushId": null,
        "starRate": null,
        "balance": null,
        "unpaidOrders": null,
        "totalOrders": null,
        "commentCount": null,
        "status": "NEWLY_CREATED"
    }
}
```

### b. 邀请被接受

```
{
    "action": "INVITATION_ACCEPTED",
    "title": "李四已接受你的邀请",
    "order": {
        "id": 47,
        "orderNum": "20160303120243AS54GE",
        "orderType": 0,
        "photo": null,
        "orderTime": null,
        "addTime": 1456977763758,
        "creatorType": 0,
        "creatorId": 0,
        "creatorName": null,
        "contactPhone": null,
        "positionLon": null,
        "positionLat": null,
        "remark": null,
        "mainTechId": 1,
        "secondTechId": 0,
        "status": "NEWLY_CREATED"
    },
    "owner": {
        "id": 3,
        "phone": "18827075330",
        "name": "张三",
        "gender": null,
        "avatar": null,
        "idNo": null,
        "idPhoto": null,
        "bank": null,
        "bankAddress": null,
        "bankCardNo": null,
        "verifyAt": null,
        "lastLoginAt": null,
        "lastLoginIp": null,
        "createAt": 1455868034249,
        "star": 0,
        "voteRate": 0,
        "skill": null,
        "pushId": null,
        "status": "VERIFIED"
    },
    "partner": {
        "id": 5,
        "phone": "18827071234",
        "name": "李四",
        "gender": null,
        "avatar": null,
        "idNo": null,
        "idPhoto": null,
        "bank": null,
        "bankAddress": null,
        "bankCardNo": null,
        "verifyAt": null,
        "lastLoginAt": null,
        "lastLoginIp": null,
        "createAt": 1455868034249,
        "star": 0,
        "voteRate": 0,
        "skill": null,
        "pushId": null,
        "status": "VERIFIED"
    }
}
```

### c. 邀请被拒绝

```
{
    "action": "INVITATION_REJECTED",
    "title": "李四已拒绝你的邀请",
    "order": {
        "id": 47,
        "orderNum": "20160303120243AS54GE",
        "orderType": 0,
        "photo": null,
        "orderTime": null,
        "addTime": 1456977763758,
        "creatorType": 0,
        "creatorId": 0,
        "creatorName": null,
        "contactPhone": null,
        "positionLon": null,
        "positionLat": null,
        "remark": null,
        "mainTechId": 1,
        "secondTechId": 0,
        "status": "NEWLY_CREATED"
    },
    "owner": {
        "id": 3,
        "phone": "18827075330",
        "name": "张三",
        "gender": null,
        "avatar": null,
        "idNo": null,
        "idPhoto": null,
        "bank": null,
        "bankAddress": null,
        "bankCardNo": null,
        "verifyAt": null,
        "lastLoginAt": null,
        "lastLoginIp": null,
        "createAt": 1455868034249,
        "star": 0,
        "voteRate": 0,
        "skill": null,
        "pushId": null,
        "status": "VERIFIED"
    },
    "partner": {
        "id": 5,
        "phone": "18827071234",
        "name": "李四",
        "gender": null,
        "avatar": null,
        "idNo": null,
        "idPhoto": null,
        "bank": null,
        "bankAddress": null,
        "bankCardNo": null,
        "verifyAt": null,
        "lastLoginAt": null,
        "lastLoginIp": null,
        "createAt": 1455868034249,
        "star": 0,
        "voteRate": 0,
        "skill": null,
        "pushId": null,
        "status": "VERIFIED"
    }
}
```

## 4. 通知消息

```
{
    "action": "NEW_MESSAGE",
    "message": {
        "id": 3,
        "title": "这是通知消息标题",
        "content": "这是通知消息正文",
        "sendTo": 1,
        "createTime": 1460371749000,
        "publishTime": 1460375231000,
        "status": 1
    },
    "title": "这是通知消息标题"
}
```

## 5. 派单消息

```
{
    "action": "ASSIGN_ORDER",    
    "title": "你收到派单消息",
    "order": {
         "id": 47,
         "orderNum": "20160303120243AS54GE",
         "orderType": 0,
         "photo": null,
         "orderTime": null,
         "addTime": 1456977763758,
         "creatorType": 0,
         "creatorId": 0,
         "creatorName": null,
         "contactPhone": null,
         "positionLon": null,
         "positionLat": null,
         "remark": null,
         "mainTechId": 1,
         "secondTechId": 0,
         "status": "TAKEN_UP"
    }
}
```
