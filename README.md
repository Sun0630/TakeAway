## 一个外卖项目

#### 用到的一些技术和第三方库
> 本项目用到了一些目前Android端比较流行的一些技术
* MVP
* Dagger2
* Retrofit
* Picasso
* ButterKnife
* Ormlite
* 短信验证登录
* 高德地图
* 极光推送
* 支付宝支付
* APK瘦身
* 多渠道打包

#### Android UI




#### APK瘦身
* 代码混淆
> 在混淆ormlite和Gson的时候，因为用到很多实体类，所以需要手动配置不混淆实体类
`-keep class com.sx.takeaway.model.net.bean.** { *; }`
* 去除无用的图片资源
* 去除无用的语言包
* 压缩图片

#### 友盟多渠道打包
