	
var index=1;

//添加商品
function addgoods(){
	if(index==4){
		alert("最多只有4个商品.");
		return;
	}
	var addnewGoods=$('#des1').clone().removeAttr("id").attr("name","newGoods");
	$('#btns').before(addnewGoods);
	addnewGoods.find("textarea").attr("name","img"+(index+1)+"_describe").val("");
	addnewGoods.find('#localImag1').attr("id","localImag"+(index+1)).css("background-image","url('img/upload.png')");
	addnewGoods.find("input").attr("id","selfPicture"+(index+1)).attr("onchange","setImagePreviewb('selfPicture"+(index+1)+"','localImag"+(index+1)+"')");
	index++;
}
//删除商品
function delgoods(){
	var newGoods=$("[name='newGoods']:last");
	if(newGoods.length==0){
		return;
	}
	newGoods.remove();
	index--;
}

function setImagePreview(selfPicture,preview,localImag) { 
		var docObj=document.getElementById(selfPicture);
		var imgObjPreview=document.getElementById(preview);
		if(docObj.files && docObj.files[0]){ 
			//火狐下，直接设img属性 
			imgObjPreview.style.display = 'block';
			imgObjPreview.style.width = '250px';
			imgObjPreview.style.height = '220px';
			//imgObjPreview.src = docObj.files[0].getAsDataURL(); 
			//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式 
			imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
		}else{ 
			//IE下，使用滤镜 
			docObj.select();
			var localImagId = document.getElementById(localImag);
			if(document.selection==undefined){
				imgObjPreview.removeAttribute("src");
				return true;
			}
			
			var imgSrc = document.selection.createRange().text;
			
			//必须设置初始大小 
			localImagId.style.width = "250px";
			localImagId.style.height = "200px";
			//图片异常的捕捉，防止用户修改后缀来伪造图片 
			try{ 
				localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
				localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
			}catch(e){ 
				alert("您上传的图片格式不正确，请重新选择!");
				return false;
			}
			imgObjPreview.style.display = 'none';
			document.selection.empty();
		}
		return true;
		} 
	
	
	function setImagePreviewb(selfPicture,localImag) { 
		var docObj=document.getElementById(selfPicture);
		var localImagId = document.getElementById(localImag);
		if(docObj.files && docObj.files[0]){ 
			localImagId.style.backgroundImage="url("+window.URL.createObjectURL(docObj.files[0])+")";
		}else{ 
			if(document.selection==undefined){
				localImagId.style.backgroundImage="url('img/upload.png')";
				return true;
			}
		} 
	}

	function submitForm(){
		var form = document.getElementById('modifyForm');//
		form.submit();
//		window.opener.location.reload();
//		window.close();
	}

