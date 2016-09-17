<%--
  Created by IntelliJ IDEA.
  User: james
  Date: 9/16/16
  Time: 2:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.*" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>验证码</title>
    <script type="text/javascript">
        //刷新验证码的js函数
        function changeCode() {
            var imgNode = document.getElementById("vimg");

            //重新加载验证码，达到刷新的目的
            imgNode.src = "security/util/CAPTCHA?t=" + Math.random();  // 防止浏览器缓存的问题
        }
    </script>
</head>
<body>
    <form action="checkServlet" method="post">
        <label>输入验证码</label><br/>
        <input type="text" name="randomCode"/><img id="vimg"  title="点击更换" onclick="changeCode();" src="security/util/CAPTCHA"><br/>
        <input type="submit" value="submit">
    </form>
</body>
</html>
