<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style>
a {
	text-decoration: none;
}
</style>
<script>
function first(i){
	var pageNo = 1;
    document.getElementsByName("page.pageNo")[0].value = pageNo;
    if(checkAllTextValid(i)){
         i.submit()
     }
}
function prePage(i){
    var pageNo = Number(<s:property value="page.pageNo"/>) - 1;
    document.getElementsByName("page.pageNo")[0].value = pageNo;
    if(checkAllTextValid(i)){
         i.submit()
     }
}
function nextPage(i){
    var pageNo = Number(<s:property value="page.pageNo"/>) + 1;
    document.getElementsByName("page.pageNo")[0].value = pageNo;
   if(checkAllTextValid(i)){
      i.submit();
   }
}
function ends(i){
    var pageNo = Number(<s:property value="page.pageCount"/>) ;
    document.getElementsByName("page.pageNo")[0].value = pageNo;
     if(checkAllTextValid(i)){
         i.submit()
     }
}
function pageSizeSubmit(i) {
	document.getElementsByName("page.pageNo")[0].value=1;
	  if(checkAllTextValid(i)){
         i.submit()
     }
}
</script>
<s:hidden name="page.pageNo" id="pageNo" />
<table align="right" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td>
			合计
			<font color="#CC3300">
				<s:property default="0" value="page.pageCount" /> 
			</font>页
			 第
			<font color="#CC3300">
				<s:property default="0" value="page.pageNo" /> 
			</font> 页 
			共
			<font color="#CC3300">
				<s:property default="0" value="page.recordCount" /> 
			</font> 条 每页
		</td>
		<td>
			<select name="page.pageSize" onchange="pageSizeSubmit(<s:property value="#request.form_name" />)">
				<s:if test="page.pageSize==10">
					<option value="10" selected="true">
						10
					</option>
				</s:if>
				<s:else>
					<option value="10">
						10
					</option>
				</s:else>

				<s:if test="page.pageSize==20">
					<option value="20" selected="true">
						20
					</option>
				</s:if>
				<s:else>
					<option value="20">
						20
					</option>
				</s:else>

				<s:if test="page.pageSize==50">
					<option value="50" selected="true">
						50
					</option>
				</s:if>
				<s:else>
					<option value="50">
						50
					</option>
				</s:else>

				<s:if test="page.pageSize==100">
					<option value="100" selected="true">
						100
					</option>
				</s:if>
				<s:else>
					<option value="100">
						100
					</option>
				</s:else>

				<s:if test="page.pageSize==200">
					<option value="200" selected="true">
						200
					</option>
				</s:if>
				<s:else>
					<option value="200">
						200
					</option>
				</s:else>

				<s:if test="page.pageSize==500">
					<option value="500" selected="true">
						500
					</option>
				</s:if>
				<s:else>
					<option value="500">
						500
					</option>
				</s:else>
			</select>
		</td>
		<td>
			条
			<s:if test="page.pageNo!=1 && page.pageNo>0">
				<a href="javascript:first(<s:property value="#request.form_name" />)" title="首页"><img
						src="../../etc/images/first.gif" border="0" />
				</a>
			</s:if>
			<s:else>
				<img src="../../etc/images/noFirst.gif"
					border="0" />
			</s:else>

			<s:if test="page.pageNo >1">
				<a href="javascript:prePage(<s:property value="#request.form_name" />)" title="上一页"><img
						src="../../etc/images/prev.gif" border="0" />
				</a>
			</s:if>
			<s:else>
				<img src="../../etc/images/noPrev.gif"
					border="0" />
			</s:else>

			<s:if test="page.pageNo < page.pageCount">
				<a href="javascript:nextPage(<s:property value="#request.form_name" />)" title="下一页"><img
						src="../../etc/images/next.gif" border="0" />
				</a>
			</s:if>
			<s:else>
				<img src="../../etc/images/noNext.gif"
					border="0" />
			</s:else>

			<s:if test="page.pageNo != page.pageCount">
				<a href="javascript:ends(<s:property value="#request.form_name" />)" title="尾页"><img
						src="../../etc/images/last.gif" border="0" />
				</a>
			</s:if>
			<s:else>
				<img src="../../etc/images/noLast.gif"
					border="0" />
			</s:else>
			&nbsp;&nbsp;
		</td>
	</tr>
</table>
