var arrCheckForm = new Array();
function iniCheckForm(formName){
	if(eval('document.'+formName)) var f = eval('document.'+formName);
	else return;
	arrCheckForm[formName] = new Array();
	var fLength = f.length,i,objFormEle,varFormEle,flag,eleName;
	for(i=0;i<fLength;i++){
		objFormEle = f.elements[i];
		eleName=objFormEle.name;
		flag=false;
		if(eleName) flag = eval('typeof '+eleName+'!="undefined"') && eval('typeof '+eleName+'.r!="undefined"');//we need this for firefox : eval('typeof '+eleName+'.r!="undefined"') 
		if(flag){
			varFormEle = eval(objFormEle.name);
			varFormEle.o = objFormEle;
			varFormEle.getInfobox();
			varFormEle.setInfobox();
			arrCheckForm[formName][arrCheckForm[formName].length] = varFormEle;
			
			//varFormEle.o.onblur = function(){window[this.name].fnValidate()};
			myAttachEvent(varFormEle.o,'blur',function(e){var ele=e.srcElement?e.srcElement:e.target;window[ele.name].fnValidate();});
		}
	}
	f.checkForm = function(){
		var flag = true;
		window.firstErrorFormEle = null;
		for(var i=0;i<arrCheckForm[formName].length;i++){
			arrCheckForm[formName][i].fnValidate();
			if(arrCheckForm[formName][i].s!=-1){
				flag=false;
				//if(window.firstErrorFormEle==null&&arrCheckForm[formName][i].o.getAttribute('type')!='hidden'&&arrCheckForm[formName][i].o.style.display!='none') window.firstErrorFormEle = arrCheckForm[formName][i].o;
				if(window.firstErrorFormEle==null)
					if(arrCheckForm[formName][i].p && arrCheckForm[formName][i].p.o4focus && eval(arrCheckForm[formName][i].p.o4focus)){
						try{
							setFocusFormEle(eval(arrCheckForm[formName][i].p.o4focus));
							document.getElementById("LErrmsgInfo").innerHTML=document.getElementById('L'+eval(arrCheckForm[formName][i].p.o4focus).id).innerHTML;
							
							window.firstErrorFormEle=arrCheckForm[formName][i].o;
						}
						catch(e){}
					}
					else{
						try{
							setFocusFormEle(arrCheckForm[formName][i].o);
							//alert(arrCheckForm[formName][i].o.name);
							document.getElementById("LErrmsgInfo").innerHTML=document.getElementById('L'+arrCheckForm[formName][i].o.name).innerHTML;
							
							window.firstErrorFormEle=arrCheckForm[formName][i].o;
						}
						catch(e){}
					}
			}
		}
		//if(window.firstErrorFormEle!=null) setFocusFormEle(window.firstErrorFormEle);
		return flag;
	}
	//f.onsubmit = f.checkForm;
}

function formEle(required,datatype,infobox,errormsg,status,parameter){
	this.r	= required;	
	this.d	= datatype;
	this.i = infobox;
	this.e	= errormsg;
	this.s = status;
	this.p = parameter;
	this.o = null;
	this.class_ok = 'classFormEle_ok';
	this.class_error = 'classFormEle_error';
	switch(this.d){
		case 'text' : this.checkData = checkText;break;
		case 'name' : this.checkData = checkName;break;
		case 'select' : this.checkData = checkSelect;break;
		case 'selectcondition' : this.checkData = checkSelectCondition;break;
		case 'checkbox' : 
		case 'radio' : this.checkData = checkCheckboxRadio;break;
		case 'email' : this.checkData = checkEmail;break;
		case 'id' : this.checkData = checkID;break;
		case 'startend' : this.checkData = checkStartEndDate;break;
		case 'text2' : this.checkData = checkText2;break;
		case 'text3' : this.checkData = checkText3;break;
		case 'selecttext' : this.checkData = checkSelecttext;break;
		case 'mirror' : this.checkData = checkMirror;break;
		case 'password' : this.checkData = checkPassword;break;
		case 'int' : this.checkData = checkInt;break;
		case 'float' : this.checkData = checkFloat;break;
		case 'date' : this.checkData = checkDate;break;
		case 'experience' : this.checkData = checkExperience;break;
		default : break;
	}
}

formEle.prototype.fnValidate = function(){
	if(this.p&&this.p.pre_condition&&eval(this.p.pre_condition)) return;
	if(this.o){
		if(this.d!='date'){
			var status = this.checkData(this.o,this.r,this.p);
			this.setStatus(status);
			this.setInfobox();
		}
		else this.checkData(this.o,this.r,this.p);
	}
	else return;
}

formEle.prototype.setInfobox = function(){
	if(this.o && this.infoC){
		var status = this.getStatus();
		if(typeof status=='undefined' || status == -1){
			this.infoC.style.display = 'none';
			this.infoT.innerHTML = '';
			if(this.d!='checkbox'&&this.d!='radio') this.o.className = this.setClassName(this.class_ok);
			if(this.d=='startend'&&this.p.sMonth&&eval(this.p.sMonth))  eval(this.p.sMonth).className = this.setClassName(this.class_ok);
			if(this.d=='text2'&&this.p.text&&eval(this.p.text))  eval(this.p.text).className = this.setClassName(this.class_ok);
		}
		else if(status>=0){
			if(this.getErrorMsg(status)!=null){
				this.infoC.style.display = '';
				this.infoT.innerHTML = this.getErrorMsg(status);
				if(this.d!='checkbox'&&this.d!='radio') this.o.className = this.setClassName(this.class_error);
				if(this.d=='startend'&&this.p.sMonth&&eval(this.p.sMonth))  eval(this.p.sMonth).className = this.setClassName(this.class_error);
				if(this.d=='text2'&&this.p.text&&eval(this.p.text))  eval(this.p.text).className = this.setClassName(this.class_error);
			}
		}
	}
	else return;
}

formEle.prototype.setClassName = function(c){
	if(this.o){
		var to = c;
		var from = c==this.class_ok?this.class_error:this.class_ok;
		if(this.o.className.indexOf(to)>-1) return this.o.className;
		if(this.o.className.indexOf(from)>-1) return this.o.className.replace(from,to);
		return this.o.className+' '+to;
	}
}

formEle.prototype.isRequired = function(){
	return this.r;
}

formEle.prototype.getDatatype = function(){
	return this.d;
}

formEle.prototype.getInfobox = function(){
	var cId,tId;
	if(typeof this.i == 'object'){
		cId = this.i[0];
		tId = this.i[1];
	}
	else cId = tId = this.i;
	this.infoC = document.getElementById('conError_'+cId)?document.getElementById('conError_'+cId):document.getElementById('txtError_'+cId)?document.getElementById('txtError_'+cId):null;
	this.infoT = document.getElementById('txtError_'+tId)?document.getElementById('txtError_'+tId):document.getElementById('conError_'+tId)?document.getElementById('conError_'+tId):null;
	if(this.infoT) this.infoT.className = 'org12 formError';
}

formEle.prototype.getErrorMsg = function(errorCode){
	if(this.e[errorCode]) return this.e[errorCode];
	else return null;
}

formEle.prototype.getStatus = function(){
	return this.s;
}

formEle.prototype.setStatus = function(v){
	this.s=v;
}

// ************************
// *** utility routines ***
// ************************
function checkText(obj,r,p){
	if((p && p.condition && eval(p.condition)) || !p || !p.condition){
			if(r && obj.value.toString().trim()=='') return 0;
			if(r && p && p.arrInvaTxt){
				var v = obj.value.toString().trim();
				for(var i=0;i<p.arrInvaTxt.length;i++) if(v==p.arrInvaTxt[i]) return 0;
			}
			if(p && p.length && obj.value.length>p.length) return 1;
		}
	return -1;
}
function checkName(obj,r,p){
	if((p && p.condition && eval(p.condition)) || !p || !p.condition){
			if(r && obj.value.toString().trim()=='') return 0;
			if(r && p && p.arrInvaTxt){
				var v = obj.value.toString().trim();
				for(var i=0;i<p.arrInvaTxt.length;i++) if(v==p.arrInvaTxt[i]) return 0;
			}
			if(p && p.min && obj.value.length<p.min) return 1;
			var regName=/[~!@#$%^&*\(\)+～1234567890１２３４５６７８９０！＠＃￥％……＆（）＝＋×÷※∏℃￥『』〗〖☆○△◇□■◆▲●★【】‰？?〓↓↑←→◎§┌┍┎┏┐┑┒┓┄┈├┝┞┟┠┡┢┣│┬┭┮┯┰┱┲┳╃╂╁╀┿┾┽┼└┕┗┘┙┚┛━┃┇┋┫┪┩┨┧┦┥┤┴［］｛｝┵┶┷┸┺┻╋╊╉╈╇┶╅╄≈≡≠＝≤≥＜＞≮≯∷∞∝∮∫∥∠⌒⊙≌∽√⊥∴∵∈∩∏∑∨￠¤￡∧<>\{\}\[\]℃]/;
			if(regName.test(obj.value)) return 2;
		}
	return -1;
}
function checkText2(obj,r,p){
	if((p && p.condition && eval(p.condition)) || !p || !p.condition){
			var textObj1=obj,textObj2=eval(p.text);
			var flag1=-1,flag2=-1;
			flag1=checkText(textObj1,r,p);
			if(textObj2) flag2=checkText(textObj2,r,p);
			if(flag1==-1 || flag2==-1) return -1;
			return 0;
		}
	return -1;
}
function checkText3(obj,r,p){
	if((p && p.condition && eval(p.condition)) || !p || !p.condition){
			if(r && obj.value.toString().trim()=='') return 0;
			if(r && p && p.arrInvaTxt){
				var v = obj.value.toString().trim();
				for(var i=0;i<p.arrInvaTxt.length;i++) if(v==p.arrInvaTxt[i]) return 0;
			}
			if(p && p.length && obj.value.trimAll().fixLength()>p.length) return 1;
		}
	return -1;
}

function checkSelect(obj,r,p){
	if((p && p.condition && eval(p.condition)) || !p || !p.condition){
			if(r && obj.options[obj.selectedIndex].value=='') return 0;
		}
	return -1;
}

function checkSelectCondition(obj,r,p){
	if(p.condition && eval(p.condition) && checkText(eval(p.text),r)==0) return 0;
	if(r && obj.options[obj.selectedIndex].value=='') return 0;
	return -1;
}

function checkSelecttext(obj,r,p){
	if((p && p.condition && eval(p.condition)) || !p || !p.condition){
		var selObj=obj,textObj=eval(p.text);
		var flag1=-1,flag2=-1;
		if(textObj) flag1=checkText(textObj,r,p);
		flag2=checkSelect(selObj,r,p);
		if(flag1==-1 || flag2==-1) return -1;
		return 0;
	}
	return -1;
}
function checkSelectText(obj,r,p){
	var flag;
	if((p && p.condition && eval(p.condition)) || !p || !p.condition){
		if(p && p.con4txt && eval(p.con4txt) && p.text){
			flag = checkText(eval(p.text),r,p);
			if(flag>-1) return flag;
		}
		if(r && obj.options[obj.selectedIndex].value=='') return 0;
	}
	return -1;
}

function checkCheckboxRadio(obj,r,p){
	if((p && p.condition && eval(p.condition)) || !p || !p.condition){
			if(r){
				var arrO = new Array(),flag=false;
				obj = eval('document.'+obj.form.name+'.'+obj.name);
				if(obj.length) arrO = obj;
				else arrO[0] = obj;
				for(var i=0;i<arrO.length;i++) if(arrO[i].checked) {flag=true;break;}
				if(flag) return -1;
				return 0;
			}
			else return -1;
		}
	return -1;
}

function checkEmail(obj,r,p){
	if((p && p.condition && eval(p.condition)) || !p || !p.condition){
		var str = obj.value;
		var patn = /^[_a-zA-Z0-9\-]+(\.[_a-zA-Z0-9\-]*)*@[a-zA-Z0-9\-]+([\.][a-zA-Z0-9\-]+)+$/;
		if(r && str.trim()=='') return 0;
		if(str.trim()!='' && !patn.test(str)) return 1;
	}
	return -1; 
}

function checkID(obj,r,p){
	if((p && p.condition && eval(p.condition)) || !p || !p.condition){
			var str = obj.value.toLowerCase();
			if(r && str.trim()=='') return 0;
			if(eval(p.flag) && str.trim()!=''){
				var patnL15 = /^\d{15}$/,patnL17 = /^\d{17}$/,patnL18 = /^\d{18}$/,patnL18_x = /^\d{17}x$/;
				if(!patnL15.test(str) && !patnL17.test(str) && !patnL18.test(str) && !patnL18_x.test(str)) return 1;
				var year='',month='',date='',strYMD=null,strYMD_id=null;
				if(p.year && eval(p.year)) year = eval(p.year).value;
				if(p.month && eval(p.month)) month = eval(p.month).value;
				if(p.date && eval(p.date)) date = eval(p.date).value;
				if(month*1==0) strYMD = year*1;
				else if(date*1==0) strYMD = year*100+month*1;
				else strYMD = year*10000+month*100+date*1;
				if(strYMD!=0){
				switch(str.length){
					case 17 :
					case 18 : strYMD_id=str.substr(6,8)*1;break;
					case 15 : strYMD_id=19*1000000+str.substr(6,6)*1;break;
					default : break;
				}
				if(strYMD!=null && strYMD_id!=null && strYMD.toString()!=strYMD_id.toString().substr(0,strYMD.toString().length)) return 2;}
			}
		}
	return -1;
}

function checkStartEndDate(obj,r,p){
	if((p && p.condition && eval(p.condition)) || !p || !p.condition){
			var sYear,sMonth,eYear,eMonth,sYMD,eYMD;
			sYear=obj.value*100;
			if(p.sMonth&&eval(p.sMonth)) sMonth=eval(p.sMonth).value*1;
			if(p.eYear&&eval(p.eYear)) eYear=eval(p.eYear).value*100;
			if(p.eMonth&&eval(p.eMonth)) eMonth=eval(p.eMonth).value*1;
			if(r && (sYear==0 || sMonth==0)) return 0;
			var myDate = new Date();
			if(eYear==0) eYear=myDate.getFullYear()*100;
			if(eMonth==0) eMonth=myDate.getMonth()+1;
			if(sYear+sMonth>eYear+eMonth) return 1;
		}
	return -1;
}

function checkMirror(obj,r,p){
	if((p && p.condition && eval(p.condition)) || !p || !p.condition){
			if(r && obj.value.toString().trim()=='') return 0;
			if(p && p.sameas && eval(p.sameas) && obj.value!=eval(p.sameas).value) return 1;
		}
	return -1;
}

function checkPassword(obj,r,p){
	if((p && p.condition && eval(p.condition)) || !p || !p.condition){
			if(r && obj.value.toString().trim()=='') return 0;
			if(obj.value.length<6 || obj.value.length>25) return 1;
		}
	return -1;
}

function checkInt(obj,r,p){
	if((p && p.condition && eval(p.condition)) || !p || !p.condition){
			var str = obj.value;
			var patn = /^\d+$/;
			if(r && str.trim()=='') return 0;
			if(str.trim()!='' && !patn.test(str)) return 1;
		}
	return -1;
}

function checkFloat(obj,r,p){
	if((p && p.condition && eval(p.condition)) || !p || !p.condition){
			var str = obj.value;
			var patn = /^\d+(\.\d+)?$/;
			if(r && str.trim()=='') return 0;
			if(str.trim()!='' && !patn.test(str)) return 1;
		}
	return -1;
}

function checkDate(obj,r,p){
	if(p&&p.ymym){
		var s_y = eval(p.ymym[0]);
		var s_m = eval(p.ymym[1]);
		var e_y = eval(p.ymym[2]);
		var e_m = eval(p.ymym[3]);
		var status,status1;
		status = checkText(s_y.o,s_y.r,s_y.p);
		if(status>-1){//s_y.o.value==''
			s_y.setStatus(status);s_y.setInfobox();
			status1 = checkText(s_m.o,s_m.r,s_m.p);
			s_m.setStatus(status1);s_m.setInfobox();
			status1 = checkText(e_y.o,e_y.r,e_y.p);
			e_y.setStatus(status1);e_y.setInfobox();
			status1 = checkText(e_m.o,e_m.r,e_m.p);
			e_m.setStatus(status1);e_m.setInfobox();
		}
		else{//s_y.o.value!=''
			if(e_y.o.value==''){
				s_y.setStatus(status);s_y.setInfobox();
				status1 = checkText(s_m.o,s_m.r,s_m.p);
				s_m.setStatus(status1);s_m.setInfobox();
				status1 = checkText(e_y.o,e_y.r,e_y.p);
				e_y.setStatus(status1);e_y.setInfobox();
				status1 = checkText(e_m.o,e_m.r,e_m.p);
				e_m.setStatus(status1);e_m.setInfobox();
			}
			else{
				if(parseInt(s_y.o.value)>parseInt(e_y.o.value)){
					s_y.setStatus(1);s_y.setInfobox();
					e_y.setStatus(-1);e_y.setInfobox();e_y.setStatus(1);e_y.o.className=e_y.setClassName(e_y.class_error);
					status1 = checkText(s_m.o,s_m.r,s_m.p);
					s_m.setStatus(status1);s_m.setInfobox();
					status1 = checkText(e_m.o,e_m.r,e_m.p);
					e_m.setStatus(status1);e_m.setInfobox();
				}
				else{
					s_y.setStatus(-1);s_y.setInfobox();
					e_y.setStatus(-1);e_y.setInfobox();
					if(s_m.o.value==''||e_m.o.value==''){
						status1 = checkText(s_m.o,s_m.r,s_m.p);
						s_m.setStatus(status1);s_m.setInfobox();
						status1 = checkText(e_m.o,e_m.r,e_m.p);
						e_m.setStatus(status1);e_m.setInfobox();
					}
					else{
						if(parseInt(s_y.o.value)==parseInt(e_y.o.value)&&parseInt(s_m.o.value)>parseInt(e_m.o.value)){
							s_m.setStatus(1);s_m.setInfobox();
							e_m.setStatus(-1);e_m.setInfobox();e_m.setStatus(1);e_m.o.className=e_m.setClassName(e_m.class_error);
						}
						else{
							status1 = checkText(s_m.o,s_m.r,s_m.p);
							s_m.setStatus(status1);s_m.setInfobox();
							status1 = checkText(e_m.o,e_m.r,e_m.p);
							e_m.setStatus(status1);e_m.setInfobox();
						}
					}
				}
			}
		}
	}
}

function checkExperience(obj,r,p){
	if((p && p.condition && eval(p.condition)) || !p || !p.condition){
		var year = p.year&&eval(p.year);
		var month = p.month&&eval(p.month);
		if(year&&month){
			if(r){
				if(year.value=='') return 0;
				if(year.value!=''&&month.value==''){
					var myYear = new Date().getFullYear();
					var miny = p.miny||12;
					var y = parseInt(myYear) - miny;
					if(year.value>y) return 1;
				}
			}
		}
	}
	return -1;
}


String.prototype.trim = function(){
	var x=this;
	x=x.replace(/^\s*(.*)/, "$1");
    //x=x.replace(/(.*?)\s*$/, "$1");
    return x;
}

function setFocusFormEle(obj){
	if(obj) obj.focus();
}


function myAttachEvent(d,e,f){
	try {
		if(d.attachEvent) d.attachEvent("on"+e,f);
		else if(d.addEventListener) d.addEventListener(e,f,false);
		else{
			var oldF = eval('d.on'+e);
			if(typeof oldF!='function') eval('d.on'+e+'=f');
			else eval('d.on'+e)=function(){oldF();f();}
		}
	}
	catch (error){}
}

String.prototype.trimAll=function(){return this.replace(/(^\s*)|(\s*$)/g,"");}
String.prototype.fixLength=function(){return this.replace(/[^\x00-\xff]/g,"aa").length;}

