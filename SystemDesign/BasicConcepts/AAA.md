# AAA - Authentication, Authorization, Accounting

- ACL (Access Control List)
  - 这种模型非常简单，即直接将用户与权限进行一一对应
    - e.g.
      - Alice -> read:article, update:article
      - Bob   -> read:article, del:article
- RBAC - Role-Based Access Control
  - 既然用户与权限一一对应很麻烦，那我们不妨在用户与权限之间进行解耦，引进role这个概念
  - e.g.
    - role to access control
      - ArticleEditor -> read:article, update:article
      - ArticleAdmin  -> read:article, del:article
    - user to role
      - Alice -> ArticleEditor
      - Bob   -> ArticleAdmin
      - Lily  -> ArticleEditor

### Authrotization on APIs
https://chinalhr.github.io/post/api-auth-program/
 - authentication --> who are you
 - authorization --> what can you do?
   - 单一的系统授权往往是伴随认证来完成的，但是在开放 API 的多系统结构下，授权可以由不同的系统来完成，例如 OAuth
 - credential --> how to tell your identity
   - e.g. 服务器为每一个访问者颁发 session ID 存放到 cookie，SSH 登录的密匙、JWT 令牌
 - Accounting --> Accounting is carried out by logging of session statistics and usage information and is used for authorization control, billing, trend analysis, resource utilization, and capacity planning activities.

1. HTTP Basic Authentication
  - 用户在浏览器输入用户名和密码
  - 组合用户名和密码然后 Base64 编码
  - 给编码后的字符串添加 Basic 前缀，然后设置名称为 Authorization 的 header 头部
  - 服务端解密比对验证Authorization字段后,会将用户请求数据返回。
  - cons:
    - BASE64仅仅是编码而非加密，如果以HTTP明文传输会有泄露风险。

2. HMAC（AK/SK）--> access key, seceret key
  - 这种基于 AK/SK 的认证方式主要是利用散列的消息认证码 (Hash-based MessageAuthentication Code) 来实现的 --> HMAC是利用带有 key 值的哈希算法生成消息摘要。
  - steps:
    1. client obtained AK and SK from service owner
    2. client generate signature by AK, SK, timestamp, request parameter, etc., using MD5 to generate signature, and send request to service owner's AAA server
    3. AAA server checks the right, if allowed then send to app server

3. OAuth2

4. JWT - JSON Web Token
  - JSON Web Token是一套开放的标准（RFC 7519），它定义了一套简洁（compact）且 URL 安全（URL-safe）的方案，以安全地在客户端和服务器之间传输 JSON 格式的信息。
  - steps:
    - 用户登录之后，服务器会返回一串 token 并保存在本地，
    - 在这之后的服务器访问都要带上这串 token，来获得访问相关路由、服务及资源的权限。
    - 比如单点登录就比较多地使用了 JWT，因为它的体积小，并且经过简单处理（使用 HTTP 头带上 Bearer 属性 + token ）就可以支持跨域操作。

### Authorization on websites
https://zhuanlan.zhihu.com/p/110990545

- HTTP Basic Authentication
  - 用户在浏览器输入用户名和密码
  - 组合用户名和密码然后 Base64 编码
  - 给编码后的字符串添加 Basic 前缀，然后设置名称为 Authorization 的 header 头部
  - 服务端解密比对验证Authorization字段后,会将用户请求数据返回。
  - 基本上就是一种密码机制，中间可能会被截取和修改字段，所以是很不安全的机制。
- session-cookie
  - 利用服务端的session和浏览器的cookie来实现前后端鉴权，我们知道http是一种无状态的请求，用户请求完成就会关闭。如果要维持状态就需要浏览器第一次请求的时候在服务端创建一个session，session有一个唯一的标识就是sessionId。一般生产sessionId之后经过加密（可不用加密）返回给客户端，以cookie的形式保存在浏览器中。
  - 这种方法一般用在老版本的web系统，因为信息也是存储在cookie当中，也有不安全的成分在里面，一般现在的系统也不会采用这种形式的鉴权。
- Token
  - Token隔一段时间是会变化的，上一种利用cookie的形式是不会变的。所以Token的鉴权方式更为安全也用的比较多。
  - 当我们输入用户名和密码点击登陆的时候，加入网站是以Token进行鉴权的话，会有以下的步骤产生：
    - 用户名和密码请求登陆
    - 服务端验证是否为数据库用户成功，下发令牌Token给客户端
    - 客户端以后每次请求都会带上令牌
    - 服务端每次都会验证令牌
  - 其实看起来和上一个的验证方法差不多呀，到底有哪些区别呢？
    - session和cookie机制是在客户端与服务端之间保持一个状态，服务端创建session对象也是需要开辟一定的内存空间来保存登陆状态的，但是利用Token的话就不会保持状态，只需比对令牌是否有效即可。
  - 也就是说Token是不存储在服务器的，这个Token本身就保存着登陆状态，服务器根据事先定义好的规则进行解密就可以知道该Token是否合理。
  - 初次之外，我们知道不只是浏览器是代理客户端，手机APP也是，在手机上面cookie是不起作用的，那么久限制了客户端类型，Token验证就不会有这个问题。
- OAuth2
  - 第三方（CSDN/QQ）向用户请求授权
  - 用户授权，返回凭证code给第三方（CSDN/QQ）
  - 第三方（CSDN/QQ）利用code向授权服务器请求Access Token
  - 返回Access Token
  - 第三方（CSDN/QQ）利用Access Token向资源服务器请求用户资源
  - 获取用户资源，登陆成功

可以参考文章：https://chinalhr.github.io/post/api-auth-program。


