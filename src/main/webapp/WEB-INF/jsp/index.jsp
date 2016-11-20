<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>index</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <style type="text/css">
        a:link {
            text-decoration: none;
        }
        a:visited {
            text-decoration: none;
        }
        a:hover {
            color: #999999;
            text-decoration: underline;
        }
    </style>
  </head>  
  <body>
    <c:if test="${msg != null}">
      message: ${msg} <br/><br/>
    </c:if>

    <h2>Hello World!</h2>
    <b>hello ${user.username}, welcome to infoweb!</b><br/>
    <br/><a href="<%=basePath%>cms/ls">虚拟文件系统</a>
    <br/><a href="<%=basePath%>cms/file/list">文件列表</a>
    <br/><a href="<%=basePath%>cms/file/upload">上传文件</a>
    <br/><a href="<%=basePath%>bpm/list">bpm</a>
    <br/><a href="<%=basePath%>red5/index.html">red5</a>
  </body>
</html>