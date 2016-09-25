<%--
  Created by IntelliJ IDEA.
  User: james
  Date: 9/16/16
  Time: 2:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
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

        function checkUsername(username) {
            var csrf=document.getElementById("csrf");
            var data=csrf.name+"="+csrf.value+"&username="+username;

            var xmlhttp;

            if (window.XMLHttpRequest) {
                // code for IE7+, Firefox, Chrome, Opera, Safari
                xmlhttp = new XMLHttpRequest();
            } else {
                // code for IE6, IE5
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }

            xmlhttp.open("POST", "/security/util/checkUsername?sid=" + Math.random(), true);
            xmlhttp.setRequestHeader("X-Requested-With", "XMLHttpRequest");
            xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    var obj = JSON.parse(xmlhttp.responseText);
                    document.getElementById("MsgUsername").innerHTML = obj.result?"用户名可用":"用户已存在";
                }
            };
            xmlhttp.send(data);
        }

        function checkConfirm(confirm) {
            var password=document.getElementsByName("password")[0].value;
            document.getElementById("MsgConfirm").innerHTML = (confirm==password)?"正确":"两次输入不一致";
        }

        function checkCAPTCHA(code) {
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
                    document.getElementById("MsgCAPTCHA").innerHTML = obj.message;
                }
            };
            xmlhttp.send(data);
        }
    </script>
</head>
<body>
    <form action="<c:url value="/guest/op/register"/>" method="post">
        <table>
            <tr>
                <td>
                    <label>用户名</label>
                </td>
                <td>
                    <input name="username" type="text" title="username" onchange="checkUsername(this.value)"/>
                </td>
                <td>
                    <div id="MsgUsername" style="color:red"></div>
                </td>
            </tr>
            <tr>
                <td>
                    <label>密 码</label>
                </td>
                <td>
                    <input name="password" type="password" title="password"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label>确认密码</label>
                </td>
                <td>
                    <input name="confirm" type="password" title="confirm" onchange="checkConfirm(this.value)"/>
                </td>
                <td>
                    <div id="MsgConfirm" style="color:red"></div>
                </td>
            </tr>
            <tr>
                <td>
                    <label>验证码</label>
                </td>
                <td>
                    <input name="code" type="text" title="CAPTCHA" onchange="checkCAPTCHA(this.value)"/>
                    <img id="CAPTCHA" title="点击更换" onclick="changeCode();" src="<c:url value="/security/util/CAPTCHA"/>">
                </td>
                <td>
                    <div id="MsgCAPTCHA" style="color:red"></div>
                </td>
            </tr>
        </table>
        <input id="csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="submit">
    </form>
</body>
</html>
