<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
    <script src="/js/jquery.form.js" type="text/javascript"></script>
    <script src="/js/ajaxfileupload.js" type="text/javascript"></script>
</head>
<div class="pageContent">
    <form action="/manage/wallet/icon/doAdd" method="post"
          onsubmit="return validateCallback(this , dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">

            <p>
                <label>归属类型:</label>
                <input name="belongingType" id="belongingType" value="" type="text" class="required" maxlength="32" style="width:150px"/>
            </p>

            <p>
                <label>icon编号:</label>
                <input name="iconNo" id="iconNo" value="" type="text" class="required" maxlength="32" style="width:150px"/>
            </p>

            <p>
                <label>icon标题:</label>
                <input name="iconTitle" id="iconTitle" value="" type="text" class="required" maxlength="32" style="width:150px"/>
            </p>

            <p>
                <label>埋点标识:</label>
                <input name="iconLabel" id="iconLabel" value="" type="text" maxlength="32" style="width:150px"/>
            </p>

            <p>
                <label>描述:</label>
                <input id="description" name="description" value="" type="text" class="" maxlength="32" style="width:150px"/>
            </p>

            <p>
                <label>客户端类型:</label>
                <select id="clientName" name="clientName"  style="width:180px">
                    <option value="android" >android</option>
                    <option value="iPhone" selected="selected">iPhone</option>
                    <option value="" selected="selected">ALL</option>
                </select>
            </p>

            <p>
                <label>支持客户端版本:</label>
                <input name="clientVersion" type="text" class="" maxlength="50" style="width:180px"
                       value=""/>
            </p>

            <p>
                <label>状态:</label>
                <select id="status"  name="status"
                        style="width:180px">
                    <option value="0">下线</option>
                    <option value="1">上线</option>
                </select>
            </p>

            <p>
                <label>状态切换定时</label>
                <input type="text" name="statusSwitchTimer" class="date" readonly="true"
                       datefmt="yyyy-MM-dd HH:mm:ss"/>
                <a class="inputDateButton" href="javascript:;">选择</a>
            </p>

            <p>
                <input type="file" id="file1" name="file"/> <input class="uploadimg" type="button" value="图片上传"
                                                                   onclick="ajaxFileUpload('file1', 'imgUrlId1', 'randimg1');"/>
            </p>

            <p class="nowrap">
                <img id="randimg1" src=""  width="320" height="95"/>
            </p>

            <p class="nowrap">
                <label>icon图标链接:</label>
                <textarea id="imgUrlId1" name="iconUrl" type="text" class=" textInput valid" maxlength="200" cols="60" rows="2"
                        ></textarea>
            </p>

            <p>
                <label>图标基色调:</label>
                <select id="selectBaseColor" onchange="baseColorSelect(this)" style="width:180px">
                    <option value="4"  selected="selected" >NotSetting</option>
                    <option value="0"><div style="color:red;">红</div></option>
                    <option value="1"><div style="color:yellow;">黄</div></option>
                    <option value="2"><div style="color:#0000ff;">蓝</div></option>
                    <option value="3"><div style="color:green;">绿</div></option>
                </select>
                <input type="hidden" id="solidColor" name="solidColor" />
                <input type="hidden" id="strokeColor" name="strokeColor" />
            </p>
            <p>
                <span style="color: red">[注: 控制背景色、边框色、推荐信息色与图标色的配对显示。]</span>
            </p>

            <p>
                <input type="file" id="file4" name="file"/> <input class="uploadimg" type="button" value="图片上传"
                                                                   onclick="ajaxFileUpload('file4', 'imgUrlId4', 'randimg4');"/>
            </p>

            <p class="nowrap">
                <img id="randimg4" src=""  width="320" height="95"/>
            </p>

            <p class="nowrap">
                <label>icon选中图标链接:</label>
                <textarea id="imgUrlId4" name="iconSelectUrl" type="text" class=" textInput valid" maxlength="200" cols="60" rows="2"
                        ></textarea>
            </p>

            <p>
                <label>字体RGB色:</label>
                <input id="fontColor" name="fontColor" type="text" maxlength="20" style="width:180px" class=" textInput valid"/>(注:带#的RGB值)
            </p>

            <p>
                <input type="file" id="file3" name="file"/> <input class="uploadimg" type="button" value="背景图上传"
                                                                   onclick="ajaxFileUpload('file3', 'imgUrlId3', 'randimg3');"/>
            </p>

            <p class="nowrap">
                <img id="randimg3" src=""  width="320" height="95"/>
            </p>

            <p class="nowrap">
                <label>背景图链接:</label>
                <textarea id="imgUrlId3" name="bgImageUrl" type="text" class=" textInput valid" maxlength="200" cols="60" rows="2"
                        ></textarea>
            </p>

            <p>
                <span style="color: red">!注意：默认屏幕高、宽、密度为1，其它按各自要求配置</span>
            </p>
            <p>
                <label>屏幕高度（像素）:</label>
                <input id="screenHeight" name="clientScreenConfigEntity.screenHeight" type="text" maxlength="5" style="width:50px" class="required digits"/>
            </p>

            <p>
                <label>屏幕宽度（像素）:</label>
                <input id="screenWidth" name="clientScreenConfigEntity.screenWidth" type="text" maxlength="5" style="width:50px" class="required digits"/>
            </p>

            <p>
                <label>屏幕密度（PPI）:</label>
                <input id="screenDensity" name="clientScreenConfigEntity.screenDensity" type="text" maxlength="5" style="width:50px" class="required digits"/>
            </p>

            <p>
                <label>是否显示角标:</label>
                <select id="showBadge" onchange="showAndHiddenBadge();" name="badgeConfigEntity.showBadge"
                        style="width:180px">
                    <option value="0">否</option>
                    <option value="1">是</option>
                </select>
            </p>
            <div id="badgeSetting" style="display: none;">
                <p>
                    <label>角标文字:</label>
                    <input name="badgeConfigEntity.badgeText" type="text" class="" maxlength="50" style="width:180px"
                           value=""/>
                </p>

                <!--<label>角标文字颜色:</label> -->
                <input name="badgeConfigEntity.badgeTextColor" type="hidden" class="" maxlength="50" style="width:180px"
                       value="ffffff"/>

                <p>
                    <label>角标背景颜色:</label>
                    <select name="badgeConfigEntity.badgeColor"
                            style="width:180px">
                        <option value="e04c34">红</option>
                    </select>
                </p>
                <p>
                    <input type="file" id="file2" name="file"/> <input class="uploadimg" type="button" value="角标图片上传"
                                                                       onclick="ajaxFileUpload('file2', 'imgUrlId2', 'randimg2');"/>
                </p>

                <p class="nowrap">
                    <img id="randimg2" src=""  width="320" height="95"/>
                </p>

                <p class="nowrap">
                    <label>角标图标链接:</label>
                    <textarea id="imgUrlId2" name="badgeConfigEntity.badgeUrl" type="text" class=" textInput valid" maxlength="200" cols="60" rows="2"
                            ></textarea>
                </p>
            </div>

            <div class="divider"/>
        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="submit">保存</button>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="button">
                        <div class="buttonContent">
                            <button type="button" class="close">取消</button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </form>
</div>
<script type="text/javascript">

    function baseColorSelect(selectElement){
        // 背景颜色RGB值
        var solidColor = ['#FFF1F2', '#FEF2E9', '#F1FAFF', '#E7FBFB', ""];

        //背景边框颜色RGB值
        var strokeColor = ['#FFE9E9', '#FFE8D7', '#E0F2FB', '#D1EBEE', ""];


        var selectedBaseColor = selectElement.value;

        var solidColorElement = document.getElementById("solidColor");
        solidColorElement.value = solidColor[selectedBaseColor];

        var strokeColorElement = document.getElementById("strokeColor");
        strokeColorElement.value = strokeColor[selectedBaseColor];

    }

    function showAndHiddenBadge(){
        var value = $("#showBadge").val();
        var $badgeSetting = $("#badgeSetting");
        if(value == '1'){
            $badgeSetting.show();
        }else{
            $badgeSetting.hide();
        }
    }

    function ajaxFileUpload(uploadFileId, resultFieldId, resultRandImgId) {
        $.ajaxFileUpload
        (
                {
                    url: '/manage/wallet/bannerconfig/uploadImage',
                    secureuri: false, //一般设置为false
                    fileElementId: uploadFileId,
                    dataType: 'text', //返回值类型 一般设置为json
                    success: function (data, status) {
                        if (data == "fail") {
                            alert("上传图片失败，请稍后尝试");
                        } else {
                            alert("上传图片成功");
                            $("#" + resultFieldId).val(data);
                            $("#" + resultRandImgId).attr("src", data);
                        }
                    },
                    error: function (data, status, e) {
                        alert(e);
                    }
                }
        )
        return false;
    }

</script>