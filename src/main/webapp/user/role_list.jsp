<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>

<head>
<base href="<%=basePath%>"></base>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="style/oa.css" rel="stylesheet" type="text/css">
<script language="javascript" src="script/public.js"></script>
<title>分配用户分配角色</title>
</head>
<body>
<center>
<TABLE class="tableEdit" border="0" cellspacing="1" cellpadding="0" style="width:778px;">
	<TBODY>
		<TR>
			<!-- 这里是添加、编辑界面的标题 -->
			<td align="center" class="tdEditTitle">分配用户【${user.person.name}】分配角色</TD>
		</TR>
				<TR>
					
					<TD height=14 align="right" vAlign=center noWrap>
						<%
							/**
							* 在这里定义“添加”，“查询”等按钮
							* <input type="image" name="find" value="find" src="images/cz.gif">
							* &nbsp;&nbsp;&nbsp;&nbsp; 
							* <a href="#" onClick="BeginOut('document.do?method=addInput','470')">
							* <img src="images/addpic.gif" border=0 align=absMiddle style="CURSOR: hand"></a>
							*/
						%> <!-- <a onclick="openWin('${pageContext.request.contextPath}/org/addUI.action?pid=${pid}','addperson',600,200);">添加机构信息</a>&nbsp;&nbsp;&nbsp; -->
						<span style="cursor: hand; color: #0000ff"
						onmouseover="this.style.coler='#ff0000'"
						onmouseout="this.style.coler='#0000ff'"
						onclick="openWin('${pageContext.request.contextPath}/user/addRoleToUser.action?userId=${user.id }','selectRole',600,200);">
							给用户分配角色</span>&nbsp;&nbsp;&nbsp; 
					</TD>
				</TR>
			  <TR>
					<TD height=28 colspan="2" align=right vAlign=center noWrap
						background=images/list_middle.jpg>&nbsp;&nbsp; <!-- 可以在这里插入分页导航条 -->
					</TD>
				</TR>
			<!-- 列表标题栏 -->
			<table width="778" border="0" cellPadding="0" cellSpacing="1"
			bgcolor="#6386d6">
			<tr bgcolor="#EFF3F7" class="TableBody1">
				<td width="30%" height="37" align="center"><b>角色名称</b></td>
				<td width="30%" height="37" align="center"><B>优先级</B></td>
				<td width="30%" height="37" align="center"><b>操作</b></td>
			</tr>
			<!-- 列表数据栏 -->
			<c:if test="${!empty roleList}">
				<c:forEach items="${roleList }" var="userRole">
					<tr bgcolor="#EFF3F7" class="TableBody1"
						onmouseover="this.bgColor = '#DEE7FF';"
						onmouseout="this.bgColor='#EFF3F7';">
						<td align="center" vAlign="center">${userRole.role.name }</td>
						<td align="center" vAlign="center">${userRole.level }</td>
						<td align="center" vAlign="center"><a
							onclick="del('${pageContext.request.contextPath}/user/delUserRole.action?ursId=${userRole.id}');">删除</a>
						&nbsp;&nbsp;
						</td>
					</tr>
				</c:forEach>
			</c:if>

			<!-- 在列表数据为空的时候，要显示的提示信息 -->
			<c:if test="${empty roleList}">
				<tr>
					<td colspan="7" align="center" bgcolor="#EFF3F7" class="TableBody1"
						onmouseover="this.bgColor = '#DEE7FF';"
						onmouseout="this.bgColor='#EFF3F7';">没有找到相应的记录</td>
				</tr>
			</c:if>
			</table>
	</TBODY>
    <TABLE>
		<TR align="center">
			<TD colspan="3" bgcolor="#EFF3F7">
			<input type="button" class="MyButton"
				value="关闭窗口" onclick="window.close()">
			</TD>
		</TR>
   </TABLE>
</TABLE>

</center>
</body>
</html>