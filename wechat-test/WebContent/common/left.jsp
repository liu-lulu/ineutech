<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<aside>
          <div id="sidebar"  class="nav-collapse ">
              <!-- sidebar menu start-->
              <ul class="sidebar-menu">             
                  <li class="sub-menu <c:if test='${fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"user/myinfo.do")>0}'>active</c:if>">
                      <a href="../user/myinfo.do" class="">
                          <i class="icon-book"></i>
                          <span>我的主页</span>
                          <span class="arrow"></span>
                      </a>
                  </li>
                  <li class="sub-menu <c:if test='${fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"finance/myAccount.do")>0||fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"finance/prizeRecord.do")>0||fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"finance/goTransfer.do")>0
                  ||fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"finance/transferRecord.do")>0||fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"finance/goCoinConvert.do")>0||fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"finance/coinConvert.do")>0||fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"finance/convertRecord.do")>0}'>active</c:if>">
                      <a href="javascript:;" class="">
                          <i class="icon-book"></i>
                          <span>财务管理</span>
                          <span class="arrow"></span>
                      </a>
                     <ul class="sub">
                          <li <c:if test='${fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"finance/myAccount.do")>0}'>class="active"</c:if>><a class="" href="../finance/myAccount.do">我的账户</a></li>
                          <li <c:if test='${fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"finance/prizeRecord.do")>0}'>class="active"</c:if>><a class="" href="../finance/prizeRecord.do">奖金记录</a></li>
                          <li <c:if test='${fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"finance/goTransfer.do")>0}'>class="active"</c:if>><a class="" href="../finance/goTransfer.do">会员转账</a></li>
                          <li <c:if test='${fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"finance/transferRecord.do")>0}'>class="active"</c:if>><a class="" href="../finance/transferRecord.do">转账记录</a></li>
                          <li <c:if test='${fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"finance/goCoinConvert.do")>0}'>class="active"</c:if>><a class="" href="../finance/goCoinConvert.do">币种转换</a></li>
                          <li <c:if test='${fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"finance/convertRecord.do")>0}'>class="active"</c:if>><a class="" href="../finance/convertRecord.do">转换记录</a></li>
                      </ul>
                  </li>
                  <li class="sub-menu <c:if test='${fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"member/goRegister.do")>0||fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"member/activate.do")>0||fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"member/activatelist.do")>0
                  ||fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"member/myRecommendation.do")>0||fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"member/diagram.do")>0}'>active</c:if>">
                      <a href="javascript:;" class="">
                          <i class="icon-book"></i>
                          <span>会员管理</span>
                          <span class="arrow"></span>
                      </a>
                     <ul class="sub">
                          <li <c:if test='${fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"member/goRegister.do")>0}'>class="active"</c:if>><a class="" href="../member/goRegister.do">会员注册</a></li>
                          <li <c:if test='${fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"member/activate.do")>0}'>class="active"</c:if>><a class="" href="../member/activate.do">激活会员</a></li>
                          <li <c:if test='${fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"member/activatelist.do")>0}'>class="active"</c:if>><a class="" href="../member/activatelist.do">激活列表</a></li>
                          <li <c:if test='${fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"member/myRecommendation.do")>0}'>class="active"</c:if>><a class="" href="../member/myRecommendation.do">我的推荐</a></li>
                          <li <c:if test='${fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"member/diagram.do")>0}'>class="active"</c:if>><a class="" href="../member/diagram.do">关系图</a></li>
                      </ul>
                  </li>
                  <li class="sub-menu <c:if test='${fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"user/updInfo.do")>0||fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"user/goUpdInfo.do")>0||fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"user/goUpdPassword.do")>0||fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"user/updPassword.do")>0}'>active</c:if>">
                      <a href="javascript:;" class="">
                          <i class="icon-book"></i>
                          <span>信息管理</span>
                          <span class="arrow"></span>
                      </a>
                     <ul class="sub">
                          <li <c:if test='${fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"user/goUpdInfo.do")>0}'>class="active"</c:if>><a class="" href="../user/goUpdInfo.do">用户信息</a></li>
                          <li <c:if test='${fn:indexOf(requestScope["javax.servlet.forward.request_uri"],"user/goUpdPassword.do")>0}'>class="active"</c:if>><a class="" href="../user/goUpdPassword.do">修改密码</a></li>
                      </ul>
                  </li>
              </ul>
              <!-- sidebar menu end-->
          </div>
      </aside>