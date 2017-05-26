<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<input type="hidden" id="pageNo" name="page.pageNo" value="<s:property value='page.pageNo'/>">

<table width="100%" border=0  cellspacing="0" cellpadding="2" border="0">
    <tr>
<!-- ========== 总页数只有一页时 ============= -->
			<s:if test="(page.pageCount==0)||(page.pageCount==1)">
			<td class="pagebar" width="110" align="right">
				<span class="staticSpan">首页</span>
				<span class="staticSpan">上一页</span>
			</td>
			<td align="left" class="pagebar" style="word-break:break-all">
			    <!-- 数字页码 -->
					<SCRIPT LANGUAGE="JavaScript">
						  var pCount = parseInt(<s:property value='page.pageCount'/>);
						  var nowPage = parseInt(<s:property value='page.pageNo'/>);
						  for ( var i=0; i<pCount; i++){
							  if( i==(nowPage-1) )
								  document.write("<span>" + (i+1) + "</span>");
							  else
								  document.write( '<a href="javascript:goPageNumber(' + (i+1) + ')">' + (i+1) + '</a>' );
						  }
					</SCRIPT>
				<!-- / end 数字页码 -->
			</td>
			<td class="pagebar" width="110" align="right">
				<span class="staticSpan">下一页</span>
				<span class="staticSpan">末页</span>
			</td>
			</s:if>

<!-- ========== 总页数不止一页时 ============= -->
			<s:else>

                <!-- +++++++++++ 总页数不止一页时，且当前页为第一页时 ++++++++++++ -->
				<s:if test="page.pageNo==1">
				<td class="pagebar" width="110" align="right">
					<span class="staticSpan">首页</span>
					<span class="staticSpan">上一页</span>
			    </td>
				<td align="center" class="pagebar" style="word-break:break-all">
				    <!-- 数字页码 -->
						<SCRIPT LANGUAGE="JavaScript">
							  var pCount = parseInt(<s:property value='page.pageCount'/>);
							  var nowPage = parseInt(<s:property value='page.pageNo'/>);
							  for ( var i=0; i<pCount; i++){
								  if( i==(nowPage-1) )
									  document.write("<span>" + (i+1) + "</span>");
								  else
									  document.write( '<a href="javascript:goPageNumber(' + (i+1) + ')">' + (i+1) + '</a>' );
							  }
						</SCRIPT>
					<!-- / end 数字页码 -->
				</td>
				<td class="pagebar" width="110">
					<a href="javascript:NEXTPage();">下一页</a>
					<a href="javascript:LASTPage();">末页</a>
				</td>
				</s:if>

				<!-- +++++++++++ 总页数不止一页时，且当前页为尾页时 +++++++++++ -->
				<s:elseif test="page.pageNo==page.pageCount">
				<td class="pagebar" width="110">
					<a href="javascript:FIRSTPage();">首页</a>
					<a href="javascript:priorPage();">上一页</a>
				</td>
				<td align="center" class="pagebar" style="word-break:break-all">
				    <!-- 数字页码 -->
						<SCRIPT LANGUAGE="JavaScript">
							  var pCount = parseInt(<s:property value='page.pageCount'/>);
							  var nowPage = parseInt(<s:property value='page.pageNo'/>);
							  for ( var i=0; i<pCount; i++){
								  if( i==(nowPage-1) )
									  document.write("<span>" + (i+1) + "</span>");
								  else
									  document.write( '<a href="javascript:goPageNumber(' + (i+1) + ')">' + (i+1) + '</a>' );
							  }
						</SCRIPT>
					<!-- / end 数字页码 -->
				</td>
				<td class="pagebar" width="110" align="right">
					<span class="staticSpan">下一页</span>
					<span class="staticSpan">末页</span>
				</td>
				</s:elseif>

				<!-- +++++++++++ 总页数不止一页时，且当前页为非首、尾页时 +++++++++++ -->
				<s:else>
				<td class="pagebar" width="110">
					<a href="javascript:FIRSTPage();">首页</a>
					<a href="javascript:priorPage();">末页</a>
				</td>
				<td align="center" class="pagebar" style="word-break:break-all">
				    <!-- 数字页码 -->
						<SCRIPT LANGUAGE="JavaScript">
							  var pCount = parseInt(<s:property value='page.pageCount'/>);
							  var nowPage = parseInt(<s:property value='page.pageNo'/>);
							  for ( var i=0; i<pCount; i++){
								  if( i==(nowPage-1) )
									  document.write("<span>" + (i+1) + "</span>");
								  else
									  document.write( '<a href="javascript:goPageNumber(' + (i+1) + ')">' + (i+1) + '</a>' );
							  }
						</SCRIPT>
					<!-- / end 数字页码 -->
				</td>
				<td class="pagebar" width="110">
					<a href="javascript:NEXTPage();">下一页</a>
					<a href="javascript:LASTPage();">末页</a>
				</td>
				</s:else>
			</s:else>

		<td align="right" width="180" style="padding-top:20px; color:#B7B7B7">
			总共<font color='#008000'> <s:property value='page.recordCount'/> </font>条记录,
			<font color='#008000'> <s:property value='page.pageCount'/> </font>页
		</td>
	</tr>
</table>



<Script LANGUAGE="JavaScript">
function goPageNumber(s)
{
  document.getElementById('pageNo').value=parseInt(s);
  goPage();
}

function FIRSTPage()
{
    document.getElementById('pageNo').value=1;
    goPage();
}
function LASTPage()
{
    document.getElementById('pageNo').value=<s:property value='page.pageCount'/>;
    goPage();
}
function priorPage()
{
    document.getElementById('pageNo').value=parseInt(document.getElementById('pageNo').value)-1;
    goPage();
}
function NEXTPage()
{
    document.getElementById('pageNo').value=parseInt(document.getElementById('pageNo').value)+1;
    goPage();
}
function goPage()
{
	
  var re = /^[1-9]\d*$/i;
  var s = document.getElementById('pageNo').value;
    if (re.test(s))
    {
        document.forms[0].submit();
    }else{
    	alert("页码必须是正整数");
    }
}



</Script>