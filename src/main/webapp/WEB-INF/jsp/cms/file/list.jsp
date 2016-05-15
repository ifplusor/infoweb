<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ request.getContextPath()+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>list</title>
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
    当前位置: <a href="<%=basePath%>cms/file/list?pos=${pos}">${pos}</a>
    <br/>----------------------<br/>
    
    <c:forEach items="${list.folders}" var="folder">
        <c:choose>
            <c:when test="${pos == '/'}">
                <a href="<%=basePath%>cms/file/list?pos=/${folder}">${folder}/</a><br/>        
            </c:when>
            <c:otherwise>
                <a href="<%=basePath%>cms/file/list?pos=${pos}/${folder}">${folder}/</a><br/>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    
    <c:forEach items="${list.files}" var="file">
        <c:choose>
            <c:when test="${list.type == 0}">
                <a href="<%=basePath%>cms/file/op/download?path=${pos}">${file}</a><br/>
            </c:when>
            <c:when test="${pos == '/'}">
                <a href="<%=basePath%>cms/file/op/download?path=/${file}">${file}</a><br/>        
            </c:when>
            <c:otherwise>
                <a href="<%=basePath%>cms/file/op/download?path=${pos}/${file}">${file}</a><br/>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <br/><a href="<%=basePath%>cms/file/upload">上传文件</a>
  </body>
</html>