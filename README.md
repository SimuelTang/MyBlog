## 设计与规范

### 实体类设计

#### 实体类

> * 博客 Blog
> * 博客分类 Type
> * 博客标签 Tag
> * 博客评论 Comment
> * 用户 User

这里，分类和标签被单独列出来作为实体类是因为根据面向对象的设计方式，这样做后可以通过分类和标签对博客进行管理。用户类，个人博客可以忽略这个，但是为了安全，可以添加用户类来设置权限。

#### 实体关系

![image-20210423143918120](E:\Desktop\Typora Note\Typora-Pic\image-20210423143918120.png)

这里存在着多对一和一对多的关系，具体区分需要看箭头。除此之外，评论类还可以进行细分：

![image-20210423144114225](E:\Desktop\Typora Note\Typora-Pic\image-20210423144114225.png)

某个评论可以被其他所有子评论回复，所以评论类需要自关联。

#### Blog类

![image-20210423144350992](E:\Desktop\Typora Note\Typora-Pic\image-20210423144350992.png)

#### Type类

![image-20210423144448109](E:\Desktop\Typora Note\Typora-Pic\image-20210423144448109.png)

#### Tag类

![image-20210423144506467](E:\Desktop\Typora Note\Typora-Pic\image-20210423144506467.png)

#### Comment 类

![image-20210423144526795](E:\Desktop\Typora Note\Typora-Pic\image-20210423144526795.png)

#### User类

![image-20210423144553432](E:\Desktop\Typora Note\Typora-Pic\image-20210423144553432.png)



### 应用分层

![image-20210423144616840](E:\Desktop\Typora Note\Typora-Pic\image-20210423144616840.png)

### 命名约定

**Dao层规定**

* 获取对象的方法使用 `query` 作为前缀。
* 获取统计值的方法使用 `count` 作为前缀。
* 插入的方法使用 `insert` 作为前缀。
* 删除的方法使用 `delete` 作为前缀。
* 修改的方法使用 `update` 作为前缀。

**Service层规定**

* 获取对象的方法使用 `get`  作为前缀。
* 获取统计值的方法使用 `count` 作为前缀。
* 插入的方法使用 `save` 作为前缀。
* 删除的方法使用 `remove` 作为前缀。
* 修改的方法使用 `update` 作为前缀。

## 后台管理

### 登录功能

#### 登录页面和首页

这部分采用模板框架直接搭建即可

#### UserDao 和 UserService

**UserDao**

考虑到使用的是 `JPA` ，所以直接继承类 `JpaRepository` 即可。它会提供默认的 `CRUD` 方法。

**UserService**

业务层，负责数据的处理。这里我们要判断登陆时使用的 `username` 和 `password` 是否合法。 

#### Controller路由

路由层对来自 `.../admin/*` 的地址进行跳转即可。同时，使用业务层对象对数据进行校验并返回结果。

#### MD5对密码加密

这里的加密，主要是向数据库传递信息时进行加密，网络上依旧是明文传输。

##### MD5原理



#### 拦截器

考虑到，如果不适用拦截器，则游客如果知道后台管理地址就可以直接跳转至后台管理界面，所以我们需要对所有 `admin/` 下的路径进行一次拦截，但过滤掉不应该拦截的几个界面。

### 分类功能

#### 分类管理页面

#### 分类列表分页

#### 分类CRUD

 







