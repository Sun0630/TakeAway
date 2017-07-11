## 外卖项目

### APK瘦身
* 代码混淆
> 在混淆ormlite和Gson的时候，因为用到很多实体类，所以需要手动配置不混淆实体类
`-keep class com.sx.takeaway.model.net.bean.** { *; }`
* 去除无用的图片资源
* 去除无用的语言包
* 压缩图片


