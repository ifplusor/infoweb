<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<body>
    <h2>test hibernate</h2>
    
    <div>login user's name: <security:authentication property="name"/></div>
    
    <c:forEach items="${data}" var="item">
        <p>test jpa for data: ${item.name}</p>
    </c:forEach>  
    
    <c:forEach items="${structTypes}" var="structType">
        <p>struct type name: ${structType.name} - with top entity: ${structType.topEntityType.name}</p>
        <c:forEach items="${structType.entityTypes}" var="entityType">
            <p>entity: ${entityType.name}</p>
            <c:forEach items="${entityType.parentEntitiyTypes}" var="parentEntityType">
                <p>parent: ${parentEntityType.name}</p>
            </c:forEach>
        </c:forEach>
    </c:forEach> 
</body>
</html>