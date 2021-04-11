<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>Hello World!</h2>
<script type="text/javascript" src="js/jquery.min.js"></script>
</body>
<form action="/tableOnline/user/login" method="post">
    <label>用户名</label>
    <input type="text" name="username"><br>
    <label>密码</label>
    <input type="password" name="password"><br>
    <p><button>登录</button></p>
</form>

<script>
    // $("button").click(function (e) {
    //     console.log("发生了点击事件");
    //     return;
    // })
</script>
</html>
