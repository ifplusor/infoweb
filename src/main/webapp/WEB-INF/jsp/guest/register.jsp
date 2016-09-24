<%--
  Created by IntelliJ IDEA.
  User: james
  Date: 9/16/16
  Time: 2:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>验证码</title>
    <script type="text/javascript">
        //刷新验证码的js函数
        function changeCode() {
            var imgNode = document.getElementById("CAPTCHA");

            //重新加载验证码，达到刷新的目的
            imgNode.src = "/security/util/CAPTCHA?sid=" + Math.random();  // 防止浏览器缓存的问题
        }

        function check(code) {
            var csrf=document.getElementById("csrf");
            var data=csrf.name+"="+csrf.value+"&code="+code;

            var xmlhttp;

            if (window.XMLHttpRequest) {
                // code for IE7+, Firefox, Chrome, Opera, Safari
                xmlhttp = new XMLHttpRequest();
            } else {
                // code for IE6, IE5
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }

            xmlhttp.open("POST", "/security/util/checkCAPTCHA?sid=" + Math.random(), true);
            xmlhttp.setRequestHeader("X-Requested-With", "XMLHttpRequest");
            xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    var obj = JSON.parse(xmlhttp.responseText);
                    document.getElementById("msg").innerHTML = obj.message;
                }
            };
            xmlhttp.send(data);
        }
    </script>
</head>
<body>
    <form action="<c:url value="/security/util/checkCAPTCHA"/>" method="post">
        <label>输入验证码</label>
        <input type="text" name="code" title="CAPTCHA" onchange="check(this.value)"/>
        <img id="CAPTCHA"  title="点击更换" onclick="changeCode();" src="<c:url value="/security/util/CAPTCHA"/>"><br/>
        <div id="msg" style="color:red"></div><br/>
        <input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="submit">
    </form>
</body>
</html>
