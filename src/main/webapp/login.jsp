<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>"></base>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK href="style/login.css" type=text/css rel=stylesheet>
<title>Web OA系统首页</title>
</head>
<body>
<BODY class=PageBody leftMargin=0 topMargin=0 onload=onLoad(); MARGINHEIGHT="0" 
MARGINWIDTH="0">
<CENTER>
<form action="user/login.action" method="post">
<TABLE height="100%" cellSpacing=0 cellPadding=0 border=0>
  <TBODY>
  <TR>
    <TD></TD></TR>
  <TR>
    <TD>
      <TABLE cellSpacing=0 cellPadding=0 border=0>
        <TBODY>
        <TR class=UpTr height=20>
          <TD></TD>
          <TD></TD>
          <TD class=VersionTitle align=right>版本:1.0
          </TD></TR>
        <TR class=UpTr height=65>
          <TD width=20></TD>
          <TD colSpan=2><IMG src="images/login/logo.gif" 
            border=0></TD></TR>
        <TR height=3>
          <TD background=images/login/logo_under_line.gif 
          colSpan=3></TD></TR>
        <TR class=DownTr>
          <TD></TD>
          <TD>
            <TABLE height=204 cellSpacing=0 cellPadding=0 border=0>
              <TBODY>
              <TR height=50>
                <TD colSpan=3></TD></TR>
              <TR height=18>
                <TD width=290 
                background=images/login/userLogin.gif 
                colSpan=3></TD></TR>
              <TR>
                <TD class=LoginLine width=2></TD>
                <TD width=286>
                  <TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" 
                  border=0>
                    <TBODY>
                    <TR height=10>
                      <TD colSpan=6></TD></TR>
                    <TR>
                      <TD class=ItemTitleFont align=right width=80 
                        height=25>用户名：</TD>
                      <TD width=100><INPUT class=inputFrm name=userName></TD>
                      <TD align=middle rowSpan=2><input type="image" src="images/login/userLogin_button.gif" 
                        border=0></TD></TR>
                    <TR>
                      <TD class=ItemTitleFont align=right height=25>密码：</TD>
                      <TD><INPUT class=inputFrm type=password 
                    name=password></TD></TR>
                    
                    <TR>
                      <TD></TD>
                      <TD></TD>
                      <TD></TD></TR></TBODY></TABLE></TD>
                <TD class=LoginLine width=2></TD></TR>
              <TR height=11>
                <TD width=290 
                background=images/login/userLogin_down.gif 
                colSpan=3></TD></TR></TBODY></TABLE></TD>
          <TD width=228><IMG src="images/login/logo_bg.gif" 
            border=0></TD></TR>
        <TR class=DownTr height=24>
          <TD></TD>
          <TD class=VersionTitle vAlign=bottom align=right 
          background=images/login/logo_copyright_bg.gif 
          colSpan=2><A class=close href="http://www.xunpoit.com/" 
            target=_blank>迅博科技</A></TD></TR></TBODY></TABLE></TD></TR>
  <TR height="3%">
    <TD></TD></TR></TBODY></TABLE></form></CENTER></BODY>
</html>