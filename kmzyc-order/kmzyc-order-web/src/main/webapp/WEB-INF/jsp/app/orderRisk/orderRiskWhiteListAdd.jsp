<%@page contentType="text/html;charset=UTF-8"%>
	<script type="text/javascript">
	$(function(){
	    $('#back').bind('click', $.unblockUI);
	    $('#btnsub').bind('click', function(){
		    var name=$('#riskContent').val();
		    var type=$('#showType').val();
		    /* if(type==2 && !/^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\d{8}$/.test(name)){
				alert('请输入正确的手机号码');
			}else  */
		    if(name==""||$.trim(name).length==0){
				alert('白名单内容不能为空');
			}else if(confirm('确认将'+name+'添加到风控白名单？')){
				$.ajax({
		            async: false,
		            url: '/app/addWhiteList.action',
		            cache:false,
		            type:'post',
		            data:{'type':type,'content':name},
		            success: function (data) {
		            	if(null!=data&&'null'!=data&&data.length>0&&data=='0'){
							alert(name+'黑名单已存在！');
				        }else if(null!=data&&'null'!=data&&data.length>0&&data=='2'){
							alert(name+'白名单已存在！');
				        }else if(null!=data&&'null'!=data&&data.length>0&&data=='3'){
							alert(name+'下单账号不存在！');
				        } else if(null!=data&&'null'!=data&&data.length>0&&data=='1'){
							alert('将'+name+'添加到风控白名单成功！');
							window.location.reload();
				        }
		            },
		            error:function(){
		            	window.location.reload();
			        }
		        });
			}
		});
	 });
	</script>
   <table width="90%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse;margin-left:10px;margin-top:10px;">
		<tbody><tr>
			<th align="left" class="edit_title">添加风控白名单条目</th>
		</tr>
        <tr>
        <td>请选择白名单条目类型：</td>
        </tr>
		<tr>
			<td>
			<select id="showType">
				<option value="1">下单账号</option>
		   		<!--<option value="2">收货手机号码</option>
		 		<option value="3">入驻商家</option>-->
			</select>
			</td>
		</tr>
        <tr>
        <td>请输入白名单内容：</td>
        </tr>
		<tr>
		<td>
			<input type="text" id="riskContent" name="riskContent" value="下单人用户名、手机号码或者商家用户名" maxlength="30" style="color:#ccc; width:275px;" onclick="if(value==defaultValue){value='';this.style.color='#333'}" onblur="if(!value){value=defaultValue;this.style.color='#ccc'}"/>
			</td>
		</tr>
				<tr>
			<td align="center">
                   <input type="button" id="btnsub" class="btn-custom" name="btnsub" value="提交" ><input type="button" class="btn-custom" id="back" name="back" value="返回">

            			</td>
		</tr>
	</table>