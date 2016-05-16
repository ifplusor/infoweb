<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    <h2>Hello World!</h2>
    <br/><a href="<%=basePath%>cms/ls">虚拟文件系统</a>
    <br/><a href="<%=basePath%>cms/file/list">文件列表</a>
    <br/><a href="<%=basePath%>cms/file/upload">上传文件</a>
  </body>
</html>