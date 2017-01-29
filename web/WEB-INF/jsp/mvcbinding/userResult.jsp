<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="mvc"%>
<html>
	<head>
		<title>spring mvc from handling</title>
	</head>
	<body>
		<h2>Here is the user form</h2>
		<mvc:form modelAttribute="user" action="result">
			<table>
				<tr>
					<td>
						<mvc:label path="userName">Name</mvc:label>
						<mvc:label path="userName">${u.userName}</mvc:label>
					</td></tr>
<tr>
	</tr>
				<tr>
					<td>
						<mvc:label path="gender">Gender</mvc:label>
						<mvc:label path="gender">${u.gender}</mvc:label>
					</td></tr>
				<tr>
					<td>
						<mvc:label path="country">Country</mvc:label>
						<mvc:label path="country">${u.country}</mvc:label>
					</td>
				</tr>
				<tr>
					<td>
						<mvc:label path="birthDate">BirthDate</mvc:label>
						<mvc:label path="birthDate">${u.birthDate}</mvc:label>
					</td>
				</tr>
				<tr>
					<input type="submit" />
					</td>
				</tr>
			</table>
		</mvc:form>
	</body>
</html>
