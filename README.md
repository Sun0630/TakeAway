## 仿美团外卖项目

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

### Android UI


<img src="/gif/1.gif"/>

---

<img src="/gif/2.gif"/>

---
<img src="/gif/2.jpg" width="330px" height="600px"/>

---
<img src="/gif/3.png" width="330px" height="600px"/>

#### Dagger2
> 编译时依赖注入的框架，在MVP架构下更加解耦V层与P层的关联。解决了反射在开发中带来的性能问题

#### 高德地图
> 用到了定位，POI检索，添加覆盖物和绘制运动轨迹等功能

#### APK瘦身
* 代码混淆
> 在混淆ormlite和Gson的时候，因为用到很多实体类，所以需要手动配置不混淆实体类
> `-keep class com.sx.takeaway.model.net.bean.** { *; }`
* 去除无用的图片资源
* 去除无用的语言包
* 压缩图片

#### 友盟多渠道打包
