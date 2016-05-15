<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<body>

    <c:forEach items="${userList}" var="user">
        <p>user: ${user.username}</p>
    </c:forEach>
</body>
</html>