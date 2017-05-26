function toProductSku(skuId){
	var url= window.location.href;
	var prductUrl = "";
	if(url.indexOf('km1818')>=0){
		prductUrl = "http://www.km1818.com/products/skuId.html";
	}else{
		prductUrl = "http://10.1.0.213/products/skuId.html";
	}
	prductUrl = prductUrl.replace("skuId",skuId);
	window.open(prductUrl);
}