<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
+ request.getServerName() + ":" + request.getServerPort()
+ path + "/";
%>
<head>
<title>用户列表</title>
<script type="text/javascript" src="<%=basePath%>resources/jquery-1.12.0.js"></script>
<script type="text/javascript">
   function search(){
	   alert("search");  
   };
   function add(){
	   alert("add");
   };
   function modify(){
	   alert("modify");
   };
   function del(){
	   alert("delete");
   }
   
</script>
</head>
<body>
	<div style="margin: 0 auto; width: 500px;">
		<form>
			<input type="button" value="查询" onclick="search()"/> <input type="button" value="新增" onclick="add()"/>
			<input type="button" value="修改" onclick="modify()"/> <input type="button" value="删除" onclick="del()"/>
			<input type="text" value="文本框" name="textField"/>
		</form>
	</div>
</body>
</html>