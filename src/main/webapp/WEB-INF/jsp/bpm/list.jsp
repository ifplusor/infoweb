<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<body>

    <a href="<%=basePath%>bpm/create">创建新模型</a>

    <br/><br/>
    <p>模型：</p>
    <c:forEach items="${ActModels}" var="actModel">
        <p>
            <a href="<%=basePath%>modeler.html?modelId=${actModel.id}">编辑</a>
            user: ${actModel.id} | name: ${actModel.name} |  key: ${actModel.key}
        </p>
    </c:forEach>

    <br/>
    <p>用户：</p>
    <c:forEach items="${ActUsers}" var="actUser">
        <p>user: ${actUser.firstName} | ${actUser.lastName} | ${actUser.email}</p>
    </c:forEach>

</body>
</html>