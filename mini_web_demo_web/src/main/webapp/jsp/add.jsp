<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uploadUrl" value="/minidemo/attendance/fileupload"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <script type="text/javascript" src="${ctx}/js/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jqupload/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="${ctx}/js/clientValidFile.js"></script>
    <script type="text/javascript" src="${ctx}/js/jqupload/jquery.iframe-transport.js"></script>
    <script type="text/javascript" src="${ctx}/js/jqupload/jquery.fileupload.js"></script>
    <script type="text/javascript" src="${ctx}/js/jqupload/jquery.fileupload-process.js"></script>
    <script type="text/javascript" src="${ctx}/js/jqupload/jquery.fileupload-validate.js"></script>
    <script>
        function init() {
            $("#upload").fileupload({
                acceptFileTypes: /(\.|\/)(xls|xlsx)$/i,
                maxFileSize: 1073741824,//1024MB
                dataType: "json",
                messages:{
                    maxNumberOfFiles: '添加的附件数量太多',
                    acceptFileTypes: '不支持该文件类型',
                    maxFileSize: '上传文件太大,请上传小于1GB的文件',
                    minFileSize: '上传文件太小'
                },
                done: function (e, data) {
                    $.each(data.result, function (index, file) {
                        if($.inArray(file.fileName,$("body").data('filelist')) ==-1){
                            $("body").data('filelist').push(file.fileName);
                            $('#filename').append(formatFileDisplay(file));
                        }
                    });
                }
            }).on('fileuploadprocessalways', function (e, data) {
                if(data.files.error){
                    alert(data.files[0].error);
                }
            });
        }

        function fileDelete(e) {
            var target;
            if (!e) var e = window.event;
            if (e.target) target = e.target;
            else if (e.srcElement) target = e.srcElement;
            if (target.nodeType == 3)
                target = target.parentNode ;
            if (target) {
                $(target).parent().remove();
            }
            var src = target;
            if (src) {
                var fileName = src.getAttribute("fileName");
                $.post("/minidemo/attendance/deleteUploaded",{
                    "fileName":fileName
                },function(data){
                    var data = eval("("+data+")");

                    if(data.success){
                        $(src).parent().next().remove();
                        $(src).parent().remove();
                        var index = $.inArray(fileName,$("body").data('filelist'));
                        if(index>=0){
                            $("body").data('filelist').splice(index,1);
                        }
                    }else{
                        alert(data.message);
                    }
                });

            }
            if(event.stopPropagation) {
                event.stopPropagation();
            } else {
                event.returnValue = false;
            }
        }

        function formatFileDisplay(file) {
            var size = '<span style="font-style:italic">' + DisplayFileSize(file.fileSize)+' </span>';
            return '<span>' + file.fileName + ' (' + size + ') <a href="#" fileName=\"' + file.fileName + '\" onclick="fileDelete(event)">删除</a> </span><br/>';
        }

        $(document).ready(function () {
            $('body').data('filelist', new Array());
            init();
        });

        //下载文件
        function downLoadFile(filename){
            window.location.href="/minidemo/attendance/filedownload?filename="+filename;
        }
</script>
</head>
<body>
    <form name="mainForm" method="post">
        <div class="pageContent">
            <form action="/minidemo/attendance/excute" method="post">
                <div class="pageFormContent" layoutH="56">
                    <p class="nowrap">
                        <label>文件名:</label>
                        <div id="filename"></div>
                    </p>
                    <div class="divider"/>
                    <p class="nowrap">
                        <input id="upload" type="file" name="files[]" data-url="${uploadUrl}" multiple>
                    </p>
                </div>
            </form>
            <br>
            <form action="/minidemo/attendance/excute" method="post">
                <div class="buttonContent">
                    <button type="submit">执行</button>
                </div>
            </form>
        </div>
    </form>
    <div class="divider"/>
    <div class="pageContent">
        <table width="100%" class="table" id="table1" border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
            <c:forEach items="${files}" var="item">
                <tr bgcolor="#ffffff" align="center" class="STYLE19">
                    <td> <a href="${item.fileUrl}">${item.fileName}</a> </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
