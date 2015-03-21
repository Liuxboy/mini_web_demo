//客户端验证文件类型和大小
var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
function fileChange(target) {
    var fileSize = 0;
    var filetypes =[".jpg",".png",".gif",".rar",".txt",".zip",".doc",".ppt",".xls",".pdf",".docx",
        ".xlsx",".avi",".mkv",".rmvb",".7z",".vsd",".html",".xml",".csv"];
    var filepath = target.value;
    var filemaxsize = 1024*1024;//1000*M 
    if(filepath){
        var isnext = false;
        var fileend = filepath.substring(filepath.lastIndexOf("."));
        if(filetypes && filetypes.length>0){
            for(var i =0; i<filetypes.length;i++){
                if(filetypes[i].toUpperCase()==fileend.toUpperCase()){
                    isnext = true;
                    break;
                }
            }
        }
        if(!isnext){
            alert("不接受此文件类型！");
            target.value ="";
            if(event.stopPropagation) {
                event.stopPropagation();
            } else {
                event.returnValue = false;
            }
            return false;
        }
    }else{
        return false;
    }
   // c=new Double(Math.round(a/b)/1000.0);//这样为保持3位
    var sizeString = changeTwoDecimal_f(fileSize / 1024);

    if(sizeString>filemaxsize){
        alert("附件大小不能大于"+filemaxsize/1024+"M！");
        target.value ="";
        return false;
    }
}
function DisplayFileSize(kbytecount) {
    var str = kbytecount+' KB';
    if (Number(kbytecount) > 1024) {  return str = (kbytecount/1024).toFixed(2)+' MB'; }
    if (Number(kbytecount) > 1048576) { return str = (kbytecount/1048576).toFixed(2)+' GB'; }
    if (Number(kbytecount) > 1073741824) {return str = (kbytecount/1073741824).toFixed(2)+' TB'; }
    return str;
}
changeTwoDecimal_f= function (floatvar)
{
    var f_x = parseFloat(floatvar);
    if (isNaN(f_x))
    {
        alert('function:changeTwoDecimal->parameter error');
        return false;
    }
    f_x = Math.round(f_x*100)/100;
    var s_x = f_x.toString();
    var pos_decimal = s_x.indexOf('.');
    if (pos_decimal < 0)
    {
        pos_decimal = s_x.length;
        s_x += '.';
    }
    while (s_x.length <= pos_decimal + 2)
    {
        s_x += '0';
    }
    return s_x;
}