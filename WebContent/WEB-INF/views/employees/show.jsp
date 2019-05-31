<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="../layout/app.jsp">
    <c:param name ="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}" />
            </div>
        </c:if>
        <c:choose>
            <c:when test="${employee != null}">
                <h2>id : ${employee.id} の従業員情報 詳細ページ</h2>

                <c:if test="${employee.id != login_employee.id}">
                    <c:choose>
                        <c:when test="${follow_flag != 0}">
                            <form method="POST" action="<c:url value='/follow/destroy' />">
                                <c:import url="../follows/_follow.jsp" />
                                <input type="hidden" name="id" value="${employee.id}" />
                                <input type="hidden" name="url" value="/employees/show?id=${employee.id}" />
                                <button type="submit">フォロー解除</button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form method="POST" action="<c:url value='/follow/create' />">
                                <c:import url="../follows/_follow.jsp" />
                                <input type="hidden" name="id" value="${employee.id}" />
                                <input type="hidden" name="url" value="/employees/show?id=${employee.id}" />
                                <button type="submit">フォロー</button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </c:if>
                <table>
                    <tbody>
                        <tr>
                            <th>社員情報</th>
                            <td><c:out value="${employee.code}" /></td>
                        </tr>
                        <tr>
                            <th>氏名</th>
                            <td><c:out value="${employee.name}" /></td>
                        </tr>
                        <tr>
                            <th>フォロー</th>
                            <td><a href="<c:url value='/employees/follow?id=${employee.id}' />"><c:out value="${follow_count}" /></a></td>
                        </tr>
                        <tr>
                            <th>権限</th>
                            <td>
                                <c:choose>
                                    <c:when test="${employee.admin_flag == 1}">管理者</c:when>
                                    <c:otherwise>一般</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <th>登録日時</th>
                            <td>
                                <fmt:formatDate value="${employee.created_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <th>更新日時</th>
                            <td>
                                <fmt:formatDate value="${employee.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                    </tbody>
                </table>
                <c:if test="${login_employee.admin_flag == 1}">
                    <p><a href="<c:url value='/employees/edit?id=${employee.id}' />">この従業員編集する</a></p>
                </c:if>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value='/employees/index' />">一覧に戻る</a></p>
    </c:param>
</c:import>