<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <title>Read From XML</title>
    </head>

    <body>
        <form:form action="/" method="post" modelAttribute="resources" >
            <c:if test="${not empty resources}">
                <c:if test="${not empty resources.stringOrStringArray}">
                    <h1>Result list</h1>
                    <table>
                        <c:forEach var="entry" items="${resources.stringOrStringArray}" varStatus="i">
                            <tr>
                                <c:if test="${entry.class.name == 'ru.stasyan.interview_cl.entity.StringArray'}">

                                    <td> <form:input type="hidden" path="stringOrStringArray[${i.index}].name"/>${entry.name}</td>
                                    <td> <c:forEach var="itm" items="${entry.item}" varStatus="z">
                                             <form:input path="stringOrStringArray[${i.index}].item[${z.index}]"/>
                                         </c:forEach>
                                    </td>
                                </c:if>

                                <c:if test="${entry.class.name == 'ru.stasyan.interview_cl.entity.StringElement'}">
                                    <td> <form:input type="hidden" path="stringOrStringArray[${i.index}].name"/>${entry.name} </td>
                                    <td> <form:input path="stringOrStringArray[${i.index}].content"/> </td>
                                </c:if>

                                <c:if test="${entry.class.name == 'ru.stasyan.interview_cl.entity.EntryComment'}">
                                    <form:input type="hidden" path="stringOrStringArray[${i.index}].name"/>
                                </c:if>

                            </tr>
                        </c:forEach>
                    </table>
                    <hr>
                </c:if>
                <button>save</button>
            </c:if>
        </form:form>
        <c:if test="${empty resources}">
            <h1>Result list is EMPTY</h1>
        </c:if>
    </body>
</html>
