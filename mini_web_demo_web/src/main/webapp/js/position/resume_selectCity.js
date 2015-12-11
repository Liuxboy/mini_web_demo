(function(){
    $.fn.selectCity = function(options){
	    var s = this;
		//控件初始化参数
	    var opts = {
		   outInput:"",
		   hiddenInput:"",
		   limit:10,
		   posy:"center",
//		   hotCity:arrHotcity,
		   province:arrProvince,
		   oversea:[],
		   title:"现居住地",
      	   district:arrDistrict,
		   type:"",
		   buttonMethod:function(){
		           
		   }
		} ;
		$.extend(opts, options);
		//绑定元素的控件初始化城市选择
		var hiddenVal = $('#'+opts.hiddenInput).val();
		var arrVal = hiddenVal.split(",");
		var arrText = [];
		if(arrVal[0]){
			for(var i=0;i<arrVal.length;i++){
				var val = arrVal[i].trim();
				var text = "";
				var disAttrVal = val.split(":")[1];
				if(disAttrVal &&disAttrVal.length ==4){
//				    val = disAttrVal;
//				    text = getDistrictNameById(val);
//					text = getDistrictParentName(val)+"-"+text;
				}else{
				    text  = getprovincetNameById(val);
					if(!text){
					    text = getCityNameById(val);
					}
				}
				if(text.indexOf("省")>0){
					text=text.substring(0,text.indexOf("省"));
				}
				arrText.push(text);
			}
			var defaultText = arrText.join("+");
			$('#'+opts.outInput).attr("title",defaultText);
//			if(defaultText.length>15){
//				defaultText = defaultText.substring(0,15)+"...";
//			}
			$('#'+opts.outInput).text(defaultText);
		}
		//获取绑定元素ID
		var id = s.attr("id") || Math.round(Math.random() * 10000);
		var wrapHtml = $("#selectCity-for-" + id);
		//创建页面主题结构
		if(!wrapHtml.length){
		    //页面主体结构
//			var hotTitleName = "";
//			var  otherProvince ="";
//			var overseas = "";
//			if(opts.type == "en"){
//			    hotTitleName = "Major city";
//				otherProvince = "Other province";
//				overseas = "overseas";
//			}else{
//			hotTitleName = "主要城市";
//			otherProvince="省市";
//				overseas = "国外";
//			}
		    wrapHtml = createTag('<div class="selectCityWrap" id="selectCity-for-'+id+'"></div>');
			var header = createTag('<div class="header"></div>');
			var content = createTag('<div class="content"></div>');
			var headerCity = createTag('<div class="headerCity"></div>');
			var footer = createTag('<div class="footer"></div>');
			//添加头部
//			if(opts.type == "en"){
//                 var headerTitle = createTag('<h3 class="title"><strong>'+opts.title+'：</strong><span>Up to<em class="count">'+opts.limit+'</em>options</span></h3>',header);
//			}else{
			
			var headerTitle = createTag('<h3 class="title"><strong>'+opts.title+'：</strong><span>最多添加<em class="count">'+opts.limit+'</em>个</span></h3>',header);
//			}
			//内容部分
			//热门城市
//			var hotCity = createTag('<div class="hotCity"></div>',content);		
//			var hotTitle = createTag('<div class="hotTitle"><a href="javascript:void(0)" class="more imgOpen"></a><h5>'+hotTitleName+'</h5></div>',hotCity);
//			var hotContent = createTag('<div class="hotContent"></div>',hotCity);
//			createHotCity(opts.hotCity,hotContent);
//			var selectCityClearfix = createTag('<div class="selectCity-clearfix"></div>',hotContent);
			//省市
			var province = createTag('<div class="province"></div>',content);
//			var provinceTitle = createTag('<div class="provinceTitle"><a href="javascript:void(0)" class="more imgOpen"></a><h5>'+otherProvince+'</h5></div>',province);
			var provinceContent = createTag('<div class="provinceContent"></div>',province);
			createProvince(opts.province,provinceContent);
			var selectCityClearfix = createTag('<div class="selectCity-clearfix"></div>',provinceContent);
			//海外
//			if((opts.oversea).length>0){
//			    var oversea = createTag('<div class="oversea"></div>',content);
//				var overseaTitle = createTag('<div class="overseaTitle"><a href="javascript:void(0)" class="more imgClose"></a><h5>'+overseas+'</h5></div>',oversea);
//				var overseaContent = createTag('<div class="overseaContent"></div>',oversea);
//				createOversea(opts.oversea,overseaContent);
//				var selectCityClearfix = createTag('<div class="selectCity-clearfix"></div>',overseaContent);
//			}else{
//			    var oversea = createTag('<div class="oversea"></div>',content);
//				var spanCity =  createTag('<span class="checkable"></span>',oversea);
//				var selectCityClearfix = createTag('<div class="selectCity-clearfix"></div>',oversea);
//				var subCBox = createTag('<input type="checkbox" name="hotCity" id="'+id+'-selectCitySpan-480" value="480" ips ="'+id+'"/>',spanCity);
//				subCBox.click(function(){
//				    var n = $(".citys", wrapHtml).length;
//					var _el = $(this);
//					var _val = _el.val();
//					var _text = _el.parent().text();
//					if (_el[0].checked) {
//					    if(n>=opts.limit){
//							alert("您最多只能添加"+opts.limit+"个城市！");
//							return false;
//					   }
//					    //$(".headerCity").slideDown('slow');
//						$(".headerCity").show();
//						var citys = preTocreateTag('<div class="citys" iname="'+_text+'" val="'+_val+'"><span>'+_text+'</span><a href="javascript:void(0)" class="closeCity"></a></div>',selectedCount);
//					}else{
//					    removeSItem(_val);
//						n = $(".citys", wrapHtml).length;
//					   if(n == 0){
//					        //$(".headerCity").slideUp('slow');
//							$(".headerCity").hide();
//					   }
//					}
//				});
//				var subLabel = createTag('<label for="'+id+'-selectCitySpan-480" class="labelCity weight" >国外</label>',spanCity); 
//			}
			//添加已选择状态
			var headerCitySelect = createTag('<div class="selectedCity"></div>',headerCity);
			var headerCitySelectH4 = createTag('<h4>已选城市：</h4>',headerCitySelect);
			var selectedCount = createTag('<div class="selectedCount"></div>',headerCitySelect);
			//var selectedCountContent = createTag('<div class="selectedCountContent"></div>',selectedCount)
			var clearAllCity = createTag('<a href="javascript:void(0)" class="clearAllCity">[清空]</a>',selectedCount);
			clearAllCity.bind("click",function(){
			    var sItems = $(".citys", wrapHtml);
			    sItems.remove();
				$("input:checked",wrapHtml).attr({"checked" : false});
				$("input",wrapHtml).removeAttr("disabled");
				//$(".headerCity").slideUp('slow');
				$(".headerCity").hide();
				$(".cityDistrict").hide();
				$(".cityProvince").hide();
				$(".checkable").removeClass('proviceCurrent');
			});
			var selectCityClearfix = createTag('<div class="selectCity-clearfix"></div>',headerCitySelect);
			//添加底部按钮
			var confirm = createTag('<button type="submit">确定</button>',footer);
			//确认按钮操作
			confirm.bind("click",function(){
			    var _seledItems = $(".citys", wrapHtml);
				var _arrTxt = [];
				var _arrVal = [];
				clearSeled();
				var _seledItemsLen = _seledItems.length;
				for ( var j = 0; j < _seledItemsLen; j++) {
				     var _seledItemsVal = _seledItems.eq(j).attr("val");
					if (_seledItemsVal) {
						_arrTxt.push(_seledItems.eq(j).text());
//						if(_seledItemsVal.length == 4){
//						    _seledItemsVal = getDistrictProvById(_seledItemsVal) +":"+_seledItemsVal;
//						}
						_arrVal.push(_seledItemsVal);
					}
				}
				var val = _arrVal.join(",");
				var text = _arrTxt.join("+");
				$("#" + opts.outInput).attr("title", text);
//				if(text.length>15){
//				    text = text.substring(0,15)+"...";
//				}
				$("#" + opts.outInput).text(text);
				$("#" + opts.hiddenInput).val(val);
				$.popupBase.close(wrapHtml);
			});
			//取消按钮操作
			var cancle = createTag('<button type="button">取消</button>',footer);
			cancle.bind("click",function(){
			    $.popupBase.close(wrapHtml);
			});
			//将元素添加到页面
			wrapHtml.append(header);
			wrapHtml.append(content);
			wrapHtml.append(headerCity);
			wrapHtml.append(footer);
			wrapHtml.css({
				"z-index" : 9999
			});
			$("body").append(wrapHtml);
			//城市申拉展开按钮点击
			$(".more",wrapHtml).click(function(){
				if($(this).hasClass('imgOpen')){
					$(this).removeClass('imgOpen').addClass('imgClose');
					$(this).parent().next().hide();
				}else{
					$(this).removeClass('imgClose').addClass('imgOpen');
					$(this).parent().next().show();
				}
			});   
		}
		//点击控件绑定元素初始化事件
		s.bind("click",function(){
		    //初始化弹窗的数据显示
 		    var sItems = $(".citys", wrapHtml);
			sItems.remove();
			$("input:checked",wrapHtml).attr({"checked" : false});
			var hiddenVal = $('#'+opts.hiddenInput).val();
			var arrVal = hiddenVal.split(",");
			if(arrVal[0]){
			    $(".headerCity").show();
				for(var i=0;i<arrVal.length;i++){
					var val = arrVal[i].trim();
					var text = "";
					var disAttrVal = val.split(":")[1];
					if(disAttrVal && disAttrVal.length ==4){
//					   val = disAttrVal;
//					   text = getDistrictNameById(val);
//					   text = getDistrictParentName(val)+"-"+text;
					}else{
					   text  = getprovincetNameById(val);
						if(!text){
							text = getCityNameById(val);
						}
					}
					if(text.indexOf("省")>0){
					    text=text.substring(0,text.indexOf("省"));
					}
					var citys = preTocreateTag('<div class="citys" iname="'+text+'" val="'+val+'"><span>'+text+'</span><a href="javascript:void(0)" class="closeCity"></a></div>',selectedCount);
				    var checkable = $("input:checkbox");
					var  checkableLen = checkable.length;
					for(var j = 0;j<checkableLen;j++){
					    if(checkable.eq(j).val() === val){
						    checkable.eq(j).attr({"checked" : true});
						}
					}
					//初始化判断城市关系置灰
//					var inputAll = $("input:checkbox",hotContent);
//					var  inputAllLen = inputAll.length;
//					for(var j =0;j<inputAllLen;j++){
//					    var  inputAllVal = inputAll.eq(j).val();
//					    var provId = getProvId(inputAllVal);
//						if (provId == val){
//							inputAll.eq(j).attr("checked",false);
//							inputAll.eq(j).attr("disabled",true);
//						}
//					}
					
				}
			}
		    var n = $(".citys", wrapHtml).length;
			if(n == 0){
				 $(".headerCity").hide();
	        }
		   $.popupBase.init({
				div : wrapHtml,
				maskable:false,
				posx : s.offset().left - $(document).scrollLeft(),
				posy : s.offset().top - $(document).scrollTop() + s.height()+2
		    });
			$(".cityDistrict").hide();
			$(".cityProvince").hide();
			$(".checkable").removeClass('proviceCurrent');
			$(document).click(function(e) {
				if ($(e.target).closest(wrapHtml).length || $(e.target).closest(s).length ||$(e.target).closest(".closeCity").length ) {
						return;
					}
				$.popupBase.close(wrapHtml);
			});
		});
		//已选择城市的关闭按钮
		$(".closeCity").live("click",function(){
		    $(this).parent().remove();
			var inputAll = $("input:checkbox",wrapHtml);
			var selectInput = {};
			var _val = $(this).parent().attr("val");
			removeSItem(_val);
			var n = $(".citys", wrapHtml).length;
			if(n == 0){
				// $(".headerCity",wrapHtml).slideUp('slow');
				$(".headerCity",wrapHtml).hide();
	        }
			var  inputAllLen = inputAll.length;
			for(var j =0;j<inputAllLen;j++){
				    var  inputAllVal = inputAll.eq(j).val();
				   if(inputAllVal.length==4){
//						var provId = getDistrictProvById(inputAllVal);
					}else{
						var provId = getProvId(inputAllVal);
					}
					if (provId == _val){
					    if($(".city-"+inputAllVal).length>0){
							selectInput = $("input",$(".city-"+inputAllVal));
						}
						if($(".district-"+inputAllVal).length>0){
							selectInput = $("input",$(".district-"+inputAllVal));
						}
						if(selectInput.length>0){
						    selectInput.removeAttr("disabled");
						}
						inputAll.eq(j).removeAttr("disabled");
					}
				}
		});
		//点击热门城市展开
//		$("label",hotContent).click(function(){
//		    var inputId = $(this).prev().val();
//			var district = opts.district;
//			if(!district.length){
//			    return;
//			}
//			if(parseInt(inputId) === 654 || parseInt(inputId) === 831){
//				 return ;
//			}
//			$(".cityProvince",wrapHtml).hide();
//			$(".proviceCurrent",wrapHtml).removeClass("proviceCurrent");
//				var element = $(this).parent().parent();
//				var div = $(".district-"+inputId,hotContent);
//				div.siblings(".cityDistrict").hide();
//					if(div.css("display") =="none"){
//					   div.show();
//					}else{
//					   div.hide(); 
//					}
//					$(this).removeClass('ico');
//				var posX = $(this).offset().left - $(document).scrollLeft();
//				var wrapX = wrapHtml.offset().left - $(document).scrollLeft();
//				var posLeft = posX - wrapX ;
//				if(!div.length){
//				    div = createTag('<div class="cityDistrict district-'+inputId+'"></div>',element);
//					for(var i =0;i<district.length;i++){
//						if(inputId === district[i][1]){
//								$(".cityDistrict",element).hide();
//								var value = district[i][0];
//								var text = district[i][2];
//								var spanCity =  createTag('<span class="checkable-district"></span>',div);
//								var input = createTag('<input type="checkbox" name="districtCity" id="selectCitySpan-'+value+'" value="'+value+'"/>',spanCity);
//								input.click(function(){
//									var n = $(".citys", wrapHtml).length;
//									var _el = $(this);
//									var _val = _el.val();
//									var _text = _el.parent().text();
//									_text = getDistrictParentName(_val)+"-"+_text;
//									if (_el[0].checked) {
//									    if(n>=opts.limit){
//											alert("您最多只能添加"+opts.limit+"个城市！");
//											return false;
//									   }
//										//$(".headerCity").slideDown('slow');
//										$(".headerCity").show();
//										var citys = preTocreateTag('<div class="citys" iname="'+_text+'" val="'+_val+'"><span>'+_text+'</span><a href="javascript:void(0)" class="closeCity"></a></div>',selectedCount);
//									}else{
//										removeSItem(_val);
//										n = $(".citys", wrapHtml).length;
//									   if(n == 0){
//											//$(".headerCity").slideUp('slow');
//											$(".headerCity").hide();
//									   }
//									}
//								});
//								if(opts.type == "en"){
//								    if(text.length>8){
//									   var littleText = text.substring(0,8);
//									}else{
//									   var littleText = text;
//									}
//								}else{
//								    if(text.length>4){
//									   var littleText = text.substring(0,4);
//									}else{
//									   var littleText = text;
//									}
//								}
//								
//								var subLabel = createTag('<label for="selectCitySpan-'+value+'" class="labelCity" title="'+text+'">'+littleText+'</label>',spanCity);
//						}
//					}
//					 var arrIco = createTag('<em class="arrIco"></em>',div);
//					arrIco.css({
//						left:posLeft
//					});
//					var spanLen = $(this).parent().prevAll('span').length+1;
//					if(opts.type =="en"){
//					spanLen = Math.ceil(spanLen/6)*6-1;
//					}else{
//					spanLen = Math.ceil(spanLen/7)*7-1;
//					}
//					if(spanLen ==34){
//					    spanLen =32;
//					}
//					$(".checkable",element).eq(spanLen).after(div);
//					div.show();				
//				}
//				if($(this).prev().is(":checked") ||$(this).prev().attr("disabled")){
//				    $("input",$(".district-"+inputId)).attr("disabled",true);
//				}
//				var citys = $(".citys",wrapHtml);
//				var citysLen = citys.length;
//				for(var  k = 0;k<citysLen;k++){
//					 $("input[value='"+citys.eq(k).attr("val")+"']").attr("checked","checked");
//				}
//				return false;
//			
//		});
		//点击省份展开城市
		$(".labelCity",provinceContent).click(function(){
		        var inputId = $(this).prev().val();
				var div = $(".city-"+inputId,provinceContent);
				if(parseInt(inputId) === 561 || parseInt(inputId) === 562 || parseInt(inputId) === 563){
					return ;
				}
				$(".cityDistrict",wrapHtml).hide();
				div.siblings(".cityProvince").hide();
				var element = $(this).parent().parent();
				if(div.css("display") =="none"){
			       div.show();
				   $(".checkable",provinceContent).removeClass('proviceCurrent');
				   $(this).parent().addClass('proviceCurrent');
				}else{
				   div.hide(); 
				   $(this).parent().removeClass('proviceCurrent');
				}
				$(this).removeClass('ico');
			    var arrDatas = window.arrCity;
				if(!div.length){
					$(".cityProvince",wrapHtml).hide();
					$(".checkable",provinceContent).removeClass('proviceCurrent');
				    $(this).parent().addClass('proviceCurrent');
					 div = createTag('<div class="cityProvince city-'+inputId+'"></div>',provinceContent);
					for(var j=0;j<arrDatas.length;j++){
						if(inputId ===arrDatas[j][1] ){
							var value = arrDatas[j][0];
							var text = arrDatas[j][2];
							var spanCity =  createTag('<span class="checkable-province"></span>',div);
							var input = createTag('<input type="checkbox" name="provinceCity" id="selectProvinceCitySpan-'+value+'" value="'+value+'"/>',spanCity);
							var district = opts.district;
							for(var k =0;k<district.length;k++){
								if(value == district[k][1]){
									spanCity.addClass("selectedIco");
								}
							}
							input.click(function(){
								var n = $(".citys", wrapHtml).length;
								var _el = $(this);
								var _val = _el.val();
								var _text = _el.next().attr("title");
								checkboxChecked(_el);
								if (_el[0].checked) {
								    if(n>=opts.limit){
											alert("您最多只能添加"+opts.limit+"个城市！");
											return false;
								    }
									//$(".headerCity").slideDown('slow');
										$(".headerCity").show();
									var citys = preTocreateTag('<div class="citys" iname="'+_text+'" val="'+_val+'"><span>'+_text+'</span><a href="javascript:void(0)" class="closeCity"></a></div>',selectedCount);
								}else{
									removeSItem(_val);
									n = $(".citys", wrapHtml).length;
								   if(n == 0){
										//$(".headerCity").slideUp('slow');
										$(".headerCity").hide();
								   }
								}
							});
							if(opts.type == "en"){
								    if(text.length>8){
									   var littleText = text.substring(0,8);
									}else{
									   var littleText = text;
									}
								}else{
								    if(text.length>4){
									   var littleText = text.substring(0,4);
									}else{
									   var littleText = text;
									}
								}
							var subLabel = createTag('<label for="selectProvinceCitySpan-'+value+'" class="labelCity" title="'+text+'">'+littleText+'</label>',spanCity);
						     //点击省份下面的城市展开城区
//							subLabel.click(function(event){
//								var inputId = $(this).prev().val();
//								var district = opts.district;
//								if(!district.length){
//									return;
//								}
//								for(var i =0;i<district.length;i++){
//								   if(inputId === district[i][1]){
//										var element = $(this).parent().parent();
//										var div = $(".district-"+inputId,$(".cityProvince",wrapHtml));
//										div.siblings(".cityDistrict").hide();
//											if(div.css("display") =="none"){
//											   div.show();
//											}else{
//											   div.hide(); 
//											}
//											$(this).removeClass('ico');
//										var posX = $(this).offset().left - $(document).scrollLeft();
//										var wrapX = wrapHtml.offset().left - $(document).scrollLeft();
//										var posLeft= posX - wrapX ;
//										if(!div.length){
//											div = createTag('<div class="cityDistrict district-'+inputId+'"></div>',element);
//											for(var i =0;i<district.length;i++){
//												if(inputId === district[i][1]){
//														$(".cityDistrict",element).hide();
//														var value = district[i][0];
//														var text = district[i][2];
//														var spanCity =  createTag('<span class="checkable-district"></span>',div);
//														var input = createTag('<input type="checkbox" name="districtCity" id="selectCitySpan-'+value+'" value="'+value+'"/>',spanCity);
//														input.click(function(){
//															var n = $(".citys", wrapHtml).length;
//															var _el = $(this);
//															var _val = _el.val();
//															var _text = _el.parent().text();
//															_text = getDistrictParentName(_val)+"-"+_text;
//															if(n>=opts.limit){
//																alert("您最多只能添加"+opts.limit+"个城市！");
//																return false;
//															}
//															if (_el[0].checked) {
//																if(n>=opts.limit){
//																		alert("您最多只能添加"+opts.limit+"个城市！");
//																		return false;
//																}
//																//$(".headerCity").slideDown('slow');
//										                        $(".headerCity").show();
//																var citys = preTocreateTag('<div class="citys" iname="'+_text+'" val="'+_val+'"><span>'+_text+'</span><a href="javascript:void(0)" class="closeCity"></a></div>',selectedCount);
//															}else{
//																removeSItem(_val);
//																n = $(".citys", wrapHtml).length;
//															   if(n == 0){
//																	//$(".headerCity").slideUp('slow');
//																	$(".headerCity").hide();
//															   }
//															}
//														});
//														if(opts.type == "en"){
//															if(text.length>8){
//															   var littleText = text.substring(0,8);
//															}else{
//															   var littleText = text;
//															}
//														}else{
//															if(text.length>4){
//															   var littleText = text.substring(0,4);
//															}else{
//															   var littleText = text;
//															}
//														}
//														var subLabel = createTag('<label for="selectCitySpan-'+value+'" class="labelCity" title="'+text+'">'+littleText+'</label>',spanCity);
//												}
//											}
//											 var arrIco = createTag('<em class="arrIco"></em>',div);
//											arrIco.css({
//												left:posLeft
//											});
//											var spanLen = $(this).parent().prevAll('span').length+1;
//											if(opts.type =="en"){
//											    spanLen = Math.ceil(spanLen/6)*6-1;
//											}else{
//											    spanLen = Math.ceil(spanLen/7)*7-1;
//											}
//											$(".checkable-province",element).eq(spanLen).after(div);
//											div.show();
//											//event.stopPropagation();						
//										}
//										if($(this).prev().is(":checked") || $(this).prev().attr("disabled")){
//											$("input",$(".district-"+inputId)).attr("disabled",true);
//										}
//										var citys = $(".citys",wrapHtml);
//										var citysLen = citys.length;
//										for(var  k = 0;k<citysLen;k++){
//											 $("input[value='"+citys.eq(k).attr("val")+"']").attr("checked","checked");
//										}
//										return false;
//									}
//								}
//							});
						}
					}
					var spanLen = $(this).parent().prevAll('span').length+1;
					if(opts.type =="en"){
						spanLen = Math.ceil(spanLen/6)*6-1;
					}else{
						spanLen = Math.ceil(spanLen/7)*7-1;
					}
					var checkableSpan = $(".checkable",provinceContent);
					if(opts.type=="en"){
						checkableSpan.eq(5).css("width","86px");
						checkableSpan.eq(11).css("width","86px");
						checkableSpan.eq(17).css("width","86px");
						checkableSpan.eq(23).css("width","86px");
					}else{
						checkableSpan.eq(6).css("width","86px");
						checkableSpan.eq(13).css("width","86px");
						checkableSpan.eq(20).css("width","86px");
						checkableSpan.eq(27).css("width","86px");
					}	
					$(".checkable",element).eq(spanLen).after(div);
					div.show();
				}
				if($(this).prev().is(":checked") ||$(this).prev().attr("disabled")){
				    $("input",$(".city-"+inputId)).attr("disabled",true);
				}
				var citys = $(".citys",wrapHtml);
				var citysLen = citys.length;
				for(var  k = 0;k<citysLen;k++){
					 $("input[value='"+citys.eq(k).attr("val")+"']").attr("checked","checked");
				}
				var scrollHeight = div.offset().top-$(".content",wrapHtml).offset().top +div.height(); 
				if(scrollHeight>$(".content",wrapHtml).height()){
					$(".content",wrapHtml).animate({scrollTop:div.height()},700);
				}
				return false;
		});
		
		//鼠标移入效果
		$(".checkable").mouseenter(function(){
		    if($(this).hasClass("selectedIco")){
			    $(this).children('label').addClass('ico');	
			}
		}).mouseleave(function(){
		    $(this).children('label').removeClass('ico');
		});
		$(".checkable-province").live("mouseenter",function(){
		    if($(this).hasClass("selectedIco")){
			    $(this).children('label').addClass('ico');	
			}
		}).live("mouseleave",function(){
		    $(this).children('label').removeClass('ico');
		});
		//创建热门城市
//		function createHotCity(data,element){
//		   var len = data.length;
//		   var district = opts.district;
//		    createSpan(data,element);
//			if(!district.length){
//		        return;
//		    }
//			for(var i=0;i<len;i++){
//			     for(var j =0;j<district.length;j++){
//				    if(data[i][0] == district[j][1]){
//					    $(".checkable",element).eq(i).addClass("selectedIco");
//					}
//				 }
//			}
//		}
		//创建省份
		function createProvince(data,element){
		    var len = data.length;
		    createSpan(data,element);
			for(var i=0;i<len;i++){
				    if(data[i][0] == 561 || data[i][0] == 562 || data[i][0] == 563){
					    return ;
					}
					 $(".checkable",element).eq(i).addClass("selectedIco");
			}
		}
//		//创建海外城市
//		function createOversea(data,element){
//		    createSpan(data,element);
//			$(".checkable",element).addClass("width88");
//			$(element).hide();
//		}
		/**
		* @function createSpan 创建初始化的input元素 城市内容
		* @param data 初始化部分数据，为数组
		* @param element 元素 确定创建的范围
		*/
		function createSpan(data,element){
		    var len = data.length;
			for(var i=0;i<len;i++){
			    var text = data[i][2];
				var value = data[i][0];
			    var spanCity =  createTag('<span class="checkable"></span>',element);
				var subCBox = createTag('<input type="checkbox" name="hotCity" id="'+id+'-selectCitySpan-'+value+'" value="'+value+'"/>',spanCity);
				subCBox.click(function(){
				    var n = $(".citys", wrapHtml).length;
					var _el = $(this);
					var _val = _el.val();
					var _text = _el.parent().text();
					checkboxChecked(_el);
					if (_el[0].checked) {
					    if(n>=opts.limit){
							alert("您最多只能添加"+opts.limit+"个城市！");
							return false;
					    }
					    //$(".headerCity").slideDown('slow');
						$(".headerCity").show();
						var citys = preTocreateTag('<div class="citys" iname="'+_text+'" val="'+_val+'"><span>'+_text+'</span><a href="javascript:void(0)" class="closeCity"></a></div>',selectedCount);
					}else{
					    removeSItem(_val);
						n = $(".citys", wrapHtml).length;
					   if(n == 0){
					        //$(".headerCity").slideUp('slow');
							$(".headerCity").hide();
					   }
					}
				});
				if(opts.type =="en"){
				    var subLabel = createTag('<label for="'+id+'-selectCitySpan-'+value+'" class="labelCity" title="'+text+'">'+text.substring(0,8)+'</label>',spanCity);
				}else{
				var subLabel = createTag('<label for="'+id+'-selectCitySpan-'+value+'" class="labelCity">'+text+'</label>',spanCity);
                }				
			}
			
		}
		/**
		* @function checkboxChecked 
		* @param val 
		*/
		function checkboxChecked(ele){
		    var _val = ele.val();
			var selectInput = {};
			var selectInputCheck = {};
			var selectDiv = {}; 
			var inputAll = $("input:checkbox",wrapHtml);
			var citys = $(".citys", wrapHtml);
			if($(".city-"+_val).length>0){
				selectInput = $("input",$(".city-"+_val));
				selectInputCheck = $("input:checked",$(".city-"+_val));
				selectDiv = $(".city-"+_val);
			}
			if($(".district-"+_val).length>0){
			    selectInput = $("input",$(".district-"+_val));
				selectInputCheck = $("input:checked",$(".district-"+_val));
				selectDiv = $(".district-"+_val);
			}
			if(ele[0].checked){
			    var citysLens = citys.length;
				for(var j =0;j<citysLens;j++){
				   var  inputAllVal = citys.eq(j).attr("val");
				   if(inputAllVal.length==4){
//						var provId = getDistrictProvById(inputAllVal);
//						var provPid  = getProvId(provId);
//						if(provPid == _val){
//							var sItems = $('.citys[val='+citys.eq(j).attr("val")+']', wrapHtml);
//						    sItems.remove();
//						}
					}else{
						var provId = getProvId(inputAllVal);
					}
					if (provId == _val){
						var sItems = $('.citys[val='+citys.eq(j).attr("val")+']', wrapHtml);
						sItems.remove();
					}
				}
				var inputAllLens = inputAll.length;
				for(var j =0;j<inputAllLens;j++){
				   var  inputAllVal = inputAll.eq(j).val();
				   if(inputAllVal.length==4){
//						var provId = getDistrictProvById(inputAllVal);
//						var provPid  = getProvId(provId);
//						if(provPid == _val){
//						    inputAll.eq(j).attr("checked",false);
//						}
					}else{
						var provId = getProvId(inputAllVal);
					}
					if (provId == _val){
					    if($(".city-"+inputAllVal).length>0){
							selectInput = $("input",$(".city-"+inputAllVal));
						}
						if($(".district-"+inputAllVal).length>0){
							selectInput = $("input",$(".district-"+inputAllVal));
						}
						if(selectInput.length>0){
						    selectInput.attr("disabled",true);
						}
					    inputAll.eq(j).attr("checked",false);
						inputAll.eq(j).attr("disabled",true);
					}
				   if(inputAll.eq(j).val() == _val){
				        inputAll.eq(j).attr("checked","checked");
				    }	
				}
			}else{
			    if(selectDiv.length>0){
			        selectInput.removeAttr("disabled");
				}
				var inputAllLens = inputAll.length;
				for(var j =0;j<inputAllLens;j++){
				    var  inputAllVal = inputAll.eq(j).val();
				   if(inputAllVal.length==4){
//						var provId = getDistrictProvById(inputAllVal);
					}else{
						var provId = getProvId(inputAllVal);
					}
					if (provId == _val){
					    if($(".city-"+inputAllVal).length>0){
							selectInput = $("input",$(".city-"+inputAllVal));
						}
						if($(".district-"+inputAllVal).length>0){
							selectInput = $("input",$(".district-"+inputAllVal));
						}
						if(selectInput.length>0){
						    selectInput.removeAttr("disabled");
						}
						inputAll.eq(j).removeAttr("disabled");
					}
				}
				for(var i = 0; i<inputAllLens;i++){
				    if(inputAll.eq(i).val() == _val){
				        inputAll.eq(i).attr("checked",false);
				    }
				}
			}
		}
		/**
		* @function removeSItem 
		* @param val 
		*/
		function removeSItem(val) {
			var items = $(".citys", wrapHtml);
			var  itemsLen = items.length;
			for ( var i = 0; i < itemsLen; i++) {
				if (items.eq(i).attr("val") === val) {
					items.eq(i).remove();
					break;
				}
			}
			var items2 = $("input:checked",wrapHtml);
			var  items2Len = items2.length;
			for ( var i = 0; i < items2Len; i++) {
				if (items2.eq(i).val() === val) {
					items2.eq(i).attr({
						"checked" : false
					});
				}
		    }
			
		}
		// 获得某市所在的省份
		function getProvId(cityId) {
            var sData = window.dCity;
			if (!sData) {
				throw new Error("相关数据没有载入!");
				return "";
			}
			if (new RegExp("@" + cityId + "\\|[^@]+\\|(\\d+)@")
					.test(sData)) {
				return RegExp["$1"];
			}
			return "";
		}
		/**
		* @function clearSeled 
		* @param val 
		*/
		function clearSeled() {
			if (opts.outInput) {
				$("#" + opts.outInput).text("");
			}
			if (opts.hiddenInput) {
				$("#" + opts.hiddenInput).val("");
			}
		}
		/**
		* @function getCityNameById 获得某市的名称
		* @param val 
		*/
		function getCityNameById(cityId) {
		   var sData = window.arrCity;
			if (!sData) {
				throw new Error("相关数据没有载入!");
				return "";
			}
			for (var i =0; i<sData.length;i++){
			    if(sData[i][0] == cityId ){
				  return sData[i][2];
				}
			}
			
		}
		
		/**
		* @function getDistrictNameById
		*/
		function getDistrictNameById(districtId){
		    var districtData = opts.district;
			for (var i =0; i<districtData.length;i++){
			    if(districtData[i][0] == districtId ){
				  return districtData[i][2];
				}
			}
		}
		function getDistrictProvById(districtId){
		    var districtData = opts.district;
			for (var i =0; i<districtData.length;i++){
			    if(districtData[i][0] == districtId ){
				  return districtData[i][1];
				}
			}
		}
		/**
		* @function getprovincetNameById
		*/
		function getprovincetNameById(provinceId){
		    var provinceData = opts.province;
			for (var i =0; i<provinceData.length;i++){
			    if(provinceData[i][0] == provinceId ){
				  return provinceData[i][2];
				}
			}
			return false;
		}
		function getDistrictParentName(districtId){
		    if(!districtId){
			   return "";
			}
			var parentId = getDistrictProvById(districtId);
			return getCityNameById(parentId);
		}
		/**
		* @function createTag  生成Tag函数
		* @param val 
		*/
		function createTag(str, parent) {
			var temp = $(str);
			if (parent) {
				temp.appendTo(parent);
			}
			return temp;
		}
		/**
		* @function createTag  生成Tag函数
		* @param val 
		*/
		function preTocreateTag(str, parent) {
			var temp = $(str);
			if (parent) {
				temp.insertBefore(parent.find($(".clearAllCity")));
			}
			return temp;
		}
	}
})(jQuery);