<%--
  Created by IntelliJ IDEA.
  User: zhangyoupeng
  Date: 15-3-6
  Time: 下午8:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="pageHeader">
    <form id="pagerForm" action="/manage/wallet/icon/list" method="post"
          onsubmit="return navTabSearch(this);">
        <div class="searchBar">
            <table class="searchContent">
                <tbody>
                <!--分页输入-->
                <input type="hidden" name="pageNum" value="1"/>
                <%--  <input type="hidden" name="pageSize" value="10"/>--%>
                <input type="hidden" name="numPerPage" value="${pageParam.pageSize eq null ? 20 : pageParam.pageSize}"/>
                <tr>
                    <td>
                        <label>创建时间段：</label>
                        <input class="date valid " datefmt="yyyy-MM-dd HH:mm:ss" name="createTimeMin" type="text"
                               maxlength="20"
                               value="${pageParam.createTimeMin}"/>至
                        <input class="date valid " datefmt="yyyy-MM-dd HH:mm:ss" name="createTimeMax" type="text"
                               maxlength="20"
                               value="${pageParam.createTimeMax}"/>
                    </td>
                    <td>
                        <label>状态：</label>
                        <select name="status" maxlength="50">
                            <option value=""
                                    <c:if test="${pageParam.status eq -1}">selected="selected"</c:if>>未选择
                            </option>
                            <option value="0"
                                    <c:if test="${pageParam.status eq '0'}">selected="selected"</c:if>>下线
                            </option>
                            <option value="1"
                                    <c:if test="${pageParam.status eq '1'}">selected="selected"</c:if>>上线
                            </option>
                        </select>
                    </td>
                    <td>
                        <label>客户端类型：</label>
                        <select name="clientName" maxlength="50">
                            <option value=""
                                    <c:if test="${pageParam.clientName eq ''}">selected="selected"</c:if>>未选择
                            </option>
                            <option value="android"
                                    <c:if test="${pageParam.clientName eq 'android'}">selected="selected"</c:if>>android
                            </option>
                            <option value="iPhone"
                                    <c:if test="${pageParam.clientName eq 'iPhone'}">selected="selected"</c:if>>iPhone
                            </option>
                        </select>
                    </td>
                    <td>
                        <label>所归属类型：</label>
                        <select name="belongingType" maxlength="50">
                            <option value="">未选择</option>
                            <c:forEach items="${belongingTypes}" var="belongingType" varStatus="status">
                                <option value="${belongingType}"
                                        <c:if test="${belongingType eq pageParam.belongingType}">selected="selected" </c:if> >
                                        ${belongingType}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="subBar">
                <ul>
                    <li>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button type="submit">搜索</button>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </form>
</div>

<div class="panelBar">
    <ul class="toolBar">
        <li>
            <a class="add" href="/manage/wallet/icon/beforeAdd" target="dialog" mask="true" rel="banner_add"
               height="450" width="500" title="添加icon配置">
                <span>添加icon配置</span>
            </a>
        </li>
    </ul>
</div>
<table class="table" layoutH="162">
    <thead>
    <tr>
        <th style="width:3%">归属类型</th>
        <th style="width:80px">icon编号</th>
        <th style="width:100px">icon标题</th>
        <th style="width:100px">埋点标识</th>
        <th style="width:100px">描述</th>

        <th style="width:50px">ICON图标预览</th>
        <th style="width:50px">ICON选中图标预览</th>
        <th style="width:50px">字体RGB色</th>
        <th style="width:3%">图标基色调</th>
        <th style="width:50px">背景图</th>

        <th style="width:3%">是否显示角标</th>
        <th style="width:3%">角标文字</th>
        <th style="width:3%">角标文字颜色</th>
        <th style="width:3%">角标背景颜色</th>

        <th style="width:100px">屏幕高度</th>
        <th style="width:100px">屏幕宽度</th>
        <th style="width:100px">屏幕密度</th>

        <th style="width:2%">客户端类型</th>
        <th style="width:3%">支持客户端版本</th>
        <th style="width:3%">状态</th>
        <th style="width:5%">状态切换定时</th>
        <th style="width:5%">创建时间</th>
        <th style="width:5%">修改时间</th>
        <th style="width:2%">修改者</th>
        <th style="width:8%">操作</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${entities}" var="entity" varStatus="status">
        <tr>
            <td>
                <div>${entity.belongingType}</div>
            </td>

            <td>${entity.iconNo}</td>
            <td>${entity.iconTitle}</td>
            <td>${entity.iconLabel}</td>
            <td>${entity.description}</td>
            <td style="background-color: #d3d3d3"><img src="${entity.iconUrl}" width="25px" height="25px"/></td>
            <td style="background-color: #d3d3d3"><img src="${entity.iconSelectUrl}" width="25px" height="25px"/></td>
            <td> ${entity.fontColor} </td>
            <td>
                <c:if test="${empty entity.solidColor}">
                    <div>NotSetting</div>
                </c:if>
                <c:if test="${plugin.solidColor eq '#FFF1F2'}">
                    <div style="color:red;"> 红</div>
                </c:if>
                <c:if test="${plugin.solidColor eq '#FEF2E9'}">
                    <div style="color:yellow;"> 黄</div>
                </c:if>
                <c:if test="${plugin.solidColor eq '#F1FAFF'}">
                    <div style="color:#0000ff;"> 蓝</div>
                </c:if>
                <c:if test="${plugin.solidColor eq '#E7FBFB'}">
                    <div style="color:green;"> 绿</div>
                </c:if>
            </td>
            <td ><img src="${entity.bgImageUrl}" width="25px" height="25px"/></td>

            <td>${entity.badgeConfigEntity.showBadge}</td>
            <td>${entity.badgeConfigEntity.badgeText}</td>
            <td>${entity.badgeConfigEntity.badgeTextColor}</td>
            <td>${entity.badgeConfigEntity.badgeColor}</td>

            <td>${entity.clientScreenConfigEntity.screenHeight}</td>
            <td>${entity.clientScreenConfigEntity.screenWidth}</td>
            <td>${entity.clientScreenConfigEntity.screenDensity}</td>

            <td>${entity.clientName}</td>
            <td>${entity.clientVersion}</td>
            <td>
                <c:if test="${entity.status == 0 }">
                    <div style="color:red;"> 下线</div>
                </c:if>
                <c:if test="${entity.status== 1 }">
                    <div style="color:green;"> 上线</div>
                </c:if>
            </td>

            <td><fmt:formatDate value="${entity.statusSwitchTimer}" type="both"
                                pattern="yyyy-MM-dd HH:mm:ss"/></td>

            <td>
                <fmt:formatDate value="${entity.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>

            <td>
                <fmt:formatDate value="${entity.modifyTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>

            <td>
                <div style="color:orange;" title="${entity.modifier}"> ${entity.modifier} </div>
            </td>
            <td>
                <c:if test="${entity.status == 1 }">
                    <a href="/manage/wallet/icon/updateStatus/${entity.id}/0?iconNo=${entity.iconNo}"
                       target="ajaxTodo"
                       title="确定要更改吗?">下线</a>
                </c:if>
                <c:if test="${entity.status == 0 }">
                    <a href="/manage/wallet/icon/updateStatus/${entity.id}/1?iconNo=${entity.iconNo}"
                       target="ajaxTodo"
                       title="确定要更改吗?">上线</a>
                </c:if>
                <a href="/manage/wallet/icon/query/${entity.id}" target="dialog" mask="true"
                   rel="wallet_plugin_edit_view"
                   resizable="false" maxable="false" minable="false"
                   title="插件" height="450" width="500" class="btnEdit">编辑</a>
                <a href="/manage/wallet/icon/delete/${entity.id}?iconNo=${entity.iconNo}"
                   target="ajaxTodo" class="btnDel"
                   title="确定要删除吗?">删除</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</div>

<!--分页页面组件 -->
<div class="panelBar">
    <div class="pages">
        <span>每页</span>
        <select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
            <option value="20" ${pageParam.pageSize eq 20 ? 'selected':''}>20</option>
            <option value="50" ${pageParam.pageSize eq 50 ? 'selected':''}>50</option>
            <option value="100" ${pageParam.pageSize eq 100 ? 'selected':''}>100</option>
            <option value="200" ${pageParam.pageSize eq 200 ? 'selected':''}>200</option>
        </select>
        <span>记录总数: ${pageParam.totalCount}</span>
    </div>
    <div class="pagination" targetType="navTab" totalCount="${pageParam.totalCount}" numPerPage="${pageParam.pageSize}"
         pageNumShown="10" currentPage="${pageParam.pageNum}"></div>
</div>
