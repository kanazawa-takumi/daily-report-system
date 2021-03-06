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
        <h2>従業員一覧</h2>
        <table id="employee_list">
            <tbody>
                <tr>
                    <th>社員番号</th>
                    <th>氏名</th>
                    <th>操作</th>
                    <th>フォロー</th>
                </tr>
                <c:forEach var="employee" items="${employees}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td><c:out value="${employee.code}" /></td>
                        <td><c:out value="${employee.name}" /></td>
                        <td>
                            <c:choose>
                                <c:when test="${employee.delete_flag == 1}">
                                    (削除済み)
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/employees/show?id=${employee.id}' />">詳細を表示</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${login_employee.id != employee.id}">
                                <c:choose>
                                    <c:when test="${follow_flags[status.index] != 0}">
                                        <form method="POST" action="<c:url value='/follow/destroy' />">
                                            <c:import url="../follows/_follow.jsp" />
                                            <input type="hidden" name="id" value="${employee.id}" />
                                            <input type="hidden" name="url" value="/employees/index" />
                                            <button type="submit">フォロー解除</button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <form method="POST" action="<c:url value='/follow/create' />">
                                            <c:import url="../follows/_follow.jsp" />
                                            <input type="hidden" name="id" value="${employee.id}" />
                                            <input type="hidden" name="url" value="/employees/index" />
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
            (全 ${employees_count} 件)<br>
            <c:forEach var="i" begin="1" end="${((employees_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/employees/index?page=${i}' />"><c:out value="${i}"></c:out></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <c:if test="${login_employee.admin_flag == 1}">
            <p><a href="<c:url value='/employees/new' />">新規従業員の登録</a></p>
        </c:if>
    </c:param>
</c:import>