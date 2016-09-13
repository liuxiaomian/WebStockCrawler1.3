<%--
  Created by IntelliJ IDEA.
  User: liumian
  Date: 16/8/11
  Time: 下午2:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>实时数据</title>
    <meta http-equiv="refresh" content=${rate}>
</head>
<body>

<p>
<h2 align="center">刷新时间: ${time}</h2> <br>
<p align="center">耗时(毫秒):${consume}</p>
<%--data: ${stock.data}--%>
<table border="0" align="center">

    <tr align="center" bgcolor="#CEFFCE">
        <td>序号<br></td>
        <td>&nbsp;&nbsp;代&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码&nbsp;&nbsp;<br></td>
        <td>当前价格<br></td>
        <td>上涨之和<br></td>
        <td>下跌之和<br></td>
        <td>上涨之和/下跌之和<br></td>
        <td>上/下最大值<br></td>
        <td>大于1.5次数<br></td>
        <td>上证指数比例<br></td>
        <td>大于1.5次数<br></td>
    </tr>
    <c:forEach items="${stockArray}" var="data" varStatus="index">

        <tr
                <c:if test="${index.index%2==0}">bgcolor="#DODODO"</c:if> align="center">
            <td>${index.index+1}</td>
            <td><c:out value="${data.code}"/></td>
            <td><c:out value="${data.current}"/></td>
            <td><c:out value="${data.riseSum}"/></td>
            <td><c:out value="${data.declineSum}"/></td>
            <td><c:out value="${data.ratio}"/></td>
            <td><c:out value="${data.maxRatio}"/></td>
            <td><c:out value="${data.surpassTimes}"/></td>
            <td><c:out value="${data.shMaxRatio}"/></td>
            <td><c:out value="${data.shTimes}"/></td>

        </tr>
    </c:forEach>
    </p>
</table>


</body>
</html>
