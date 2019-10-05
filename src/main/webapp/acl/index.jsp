<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/common/common.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>"></base>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="style/oa.css" rel="stylesheet" type="text/css">
<script language="javascript" src="script/public.js"></script>
<c:if test="${!empty role }">
<title>请给角色【 ${role.name}】授权</title>
</c:if>
<c:if test="${!empty user }">
<title>请给用户【 ${user.person.name} 】授权</title>
</c:if>
</head>
<BODY bgColor=#dee7ff leftMargin=0 background="" topMargin=0
	marginheight="0" marginwidth="0">
	<center>
		
		<TABLE width="778" border=0 align=center cellPadding=0 cellSpacing=0
			borderColor=#ffffff style="FONT-SIZE: 10pt">
			<TBODY>
				<TR>
					<TD height=28 colspan="2" align="center" vAlign=center noWrap
						background=images/list_middle.jpg>&nbsp;&nbsp; <!-- 可以在这里插入分页导航条 -->
						<c:if test="${!empty role }">
						请给角色【 ${role.name}】授权
						</c:if>
						<c:if test="${!empty user }">
						请给用户【 ${user.person.name} 】授权
						</c:if>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<table width="778" border="0" cellPadding="0" cellSpacing="1"
			bgcolor="#6386d6">
			<!-- 列表标题栏 -->
			<tr bgcolor="#EFF3F7" class="TableBody1">
				<td width="15%" height="37" align="center"><b>顶级模块</b></td>
				<td width="25%" height="37" align="center"><B>二级模块</B></td>
				<td width="30%" height="37" align="center"><b>权限</b></td>
				<c:if test="${!empty user}">
				<td width="10%" height="37" align="center"><b>不继承</b></td>
				</c:if>
				<td width="10%" height="37" align="center"><b>启用</b></td>
			</tr>
			<!-- 列表数据栏 -->
			<c:if test="${!empty pm.dataList}">
				<c:forEach items="${pm.dataList }" var="module">
					<tr bgcolor="#EFF3F7" class="TableBody1"
						onmouseover="this.bgColor = '#DEE7FF';"
						onmouseout="this.bgColor='#EFF3F7';">
						<td align="center" vAlign="center">${module.name }</td>
						<td align="center" vAlign="center">&nbsp;</td>
						<td align="center" vAlign="center">
						<input type="checkbox" name="c">C
						<input type="checkbox" name="r">R
						<input type="checkbox" name="u">U
						<input type="checkbox" name="d">D
						</td>
						<c:if test="${!empty user}">
						<td align="center" vAlign="center"><input type="checkbox" name=""></td>
						</c:if>
						<td align="center" vAlign="center"><input type="checkbox" name=""></td>
					</tr>
					<c:forEach items="${module.childList }" var="child">
						<tr bgcolor="#EFF3F7" class="TableBody1"
							onmouseover="this.bgColor = '#DEE7FF';"
							onmouseout="this.bgColor='#EFF3F7';">
							<td align="center" vAlign="center">&nbsp;</td>
							<td align="center" vAlign="center">${child.name }</td>
							<td align="center" vAlign="center">
							<input type="checkbox" name="c">C
							<input type="checkbox" name="r">R
							<input type="checkbox" name="u">U
							<input type="checkbox" name="d">D
							</td>
							<c:if test="${!empty user}">
							<td align="center" vAlign="center"><input type="checkbox" name=""></td>
							</c:if>
							<td align="center" vAlign="center"><input type="checkbox" name=""></td>
						</tr>
					</c:forEach>
				</c:forEach>
			</c:if>

			<!-- 在列表数据为空的时候，要显示的提示信息 -->
			<c:if test="${empty pm.dataList}">
				<tr>
					<td colspan="7" align="center" bgcolor="#EFF3F7" class="TableBody1"
						onmouseover="this.bgColor = '#DEE7FF';"
						onmouseout="this.bgColor='#EFF3F7';">没有找到相应的记录</td>
				</tr>
			</c:if>
		</table>
		
	</center>

</body>

</html>
