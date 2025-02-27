 <%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
 <!DOCTYPE html>
 <html>
 <head>
 <meta charset="UTF-8">
 <title>Insert title here</title>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 </head>
 <body>
 <h1> 현재 게임 목록 </h1>
 <table>
 		<tr>
 		    <th>id</th>
 		    <th> | </th>
 			<th>제목</th>
 			<th> | </th>
 			<th>소제목</th>
 			<th> | </th>
 			<th>제작자</th>
 			<th> | </th>
 			<th>선택 1</th>
 			<th> | </th>
 			<th>개수</th>
 			<th> | </th>
 			<th>선택 2</th>
 			<th> | </th>
 			<th>개수</th>
 		</tr>

 		<!-- DB에 Mouse 데이터가 1개도 없을 경우, 등록된 Mouse 정보가 없습니다 출력 -->
 		<c:choose>
 			<c:when test="${empty requestScope.gameList || fn:length(gameList) == 0}">
 				<tr>
 					<td>등록된 게임이 없습니다.</td>
 				</tr>
 			</c:when>
 			<c:otherwise>
 				<c:forEach items="${requestScope.gameList}" var="game">
 					<tr>
 						<td>${game.id}</td>
 						<td> | </td>
 						<td>${game.title}</td>
 						<td> | </td>
 						<td>${game.subTitle}</td>
 						<td> | </td>
 						<td>${game.author}</td>
 						<td> | </td>
 						<td>${game.choices[0].getName()}</td>
 						<td> | </td>
 						<td>${game.choices[0].getCount()}</td>
 						<td> | </td>
 						<td>${game.choices[1].getName()}</td>
                        <td> | </td>
                        <td>${game.choices[1].getCount()}</td>
 					</tr>
 				</c:forEach>
 			</c:otherwise>
 		</c:choose>

 	</table>
 	<a href="index.html">HOME</a>
 </body>
 </html>