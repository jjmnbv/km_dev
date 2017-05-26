//查看时代会员信息
function queryEraInfoDetail(eraInfoId,loginId){
	location.href="/userInfo/eraInfo_queryEraInfoDetail.action?eraInfo.eraInfoId="+eraInfoId+"&&eraInfo.loginId="+loginId;
}

//查看康美会员信息
function queryUserDetail(personalId){
	if(personalId==null){
		location.href="/userInfo/eraInfo_pageList.action";
	}else {
		location.href="/userInfo/personalBasicInfo_preDetail.action?personalId="+personalId;
	}
	
}