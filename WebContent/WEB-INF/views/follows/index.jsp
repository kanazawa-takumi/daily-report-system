<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}" />
            </div>
        </c:if>
        <h2>フォロー一覧</h2>
        <table id="follow_list">
            <tbody>
                <tr>
                    <th>社員番号</th>
                    <th>氏名</th>
                    <th>詳細</th>
                    <th>フォロー</th>
                </tr>
                <c:forEach var="follow" items="${follows}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td><c:out value="${follow.followee.code}" /></td>
                        <td><c:out value="${follow.followee.name}" /></td>
                        <td><a href="<c:url value='/employees/show?id=${follow.id}' />">詳細</a></td>
                        <td>
                            <c:if test="${login_employee.id != follow.followee.id}">
                                <c:choose>
                                    <c:when test="${follow_flags[status.index] != 0}">
                                        <form method="POST" action="<c:url value='/follow/destroy' />">
                                            <c:import url="../follows/_follow.jsp" />
                                            <input type="hidden" name="id" value="${follow.followee.id}" />
                                            <input type="hidden" name="url" value="/employees/follow?id=${employee.id}" />
                                            <button type="submit">フォロー解除</button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <form method="POST" action="<c:url value='/follow/create' />">
                                            <c:import url="../follows/_follow.jsp" />
                                            <input type="hidden" name="id" value="${follow.followee.id}" />
                                            <input type="hidden" name="url" value="/employees/follow?id=${employee.id}" />
                                            <button type="submit">フォロー</button>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pageination">
            (全 ${followsCount} 件)<br>
            <c:forEach var="i" begin="1" end="${((followsCount - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/employees/follow?id=${employee.id}&page=${i}' />"><c:out value="${i}"></c:out></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/employees/show?id=${employee.id}' />">従業員詳細に戻る</a></p>
    </c:param>
</c:import>