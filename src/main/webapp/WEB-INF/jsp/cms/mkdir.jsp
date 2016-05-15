<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ request.getContextPath()+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>创建文件夹</title>
</head>
<body>
    当前位置: <a href="<%=basePath%>cms/ls?pos=${pos}">${pos}</a>
    <br/>----------------------<br/>
    
    <form action="<%=basePath %>cms/op/mkdir?pos=${pos}" method="post">
        <div>
            <label for="dirname">目录名</label>
            <input id="dirname" name="dirname" type="text"/> 
        </div>
        <input type="submit" value="创建">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
    <br />
</body>
</html>