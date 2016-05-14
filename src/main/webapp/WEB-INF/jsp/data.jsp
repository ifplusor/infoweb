<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<html>
<h2>test hibernate</h2>
<body>
    <c:forEach items="${data}" var="item">
        <p>${item.name}</p>
    </c:forEach>  
</body>
</html>