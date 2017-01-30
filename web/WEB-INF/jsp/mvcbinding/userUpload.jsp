<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="mvc"%>
<html>
	<head>
		<title>spring mvc from handling</title>
	</head>
	<body>
		<h2>Here is the user form</h2>
		<%String actionPath = request.getContextPath()+ "/result";%>
		<%=actionPath%>
		<form action="<%=request.getContextPath()%>/result">
			<tr><td><%=request.getContextPath()+"/result"%></td></tr>
		</form>
		<mvc:form modelAttribute="user" action="<%=actionPath%>">
			<table>
				<tr>
					<td>
						<mvc:label path="userName">Name</mvc:label>
						<mvc:input path="userName"></mvc:input>
					</td></tr>
<tr>
	<td>
						<mvc:label path="birthDate">Birthday</mvc:label>
						<mvc:input path="birthDate"></mvc:input>
	</td></tr>
				<tr>
					<td>
						<mvc:label path="gender">Gender</mvc:label>
						<mvc:radiobuttons path="gender" items="${genders}"></mvc:radiobuttons>
					</td></tr>
				<tr>
					<td>
						<mvc:label path="Country">Country</mvc:label>
						<mvc:select path="Country" items="${countries}"></mvc:select>
					</td>
				</tr>
				<tr>
					<td>
					<input type="submit" />
					</td>
				</tr>
			</table>
		</mvc:form>
	</body>
</html>
