<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layer/layer.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
</head>
<body>
<div class="layui-form">
    <input type="hidden" value="${config.playListId}" name="playListId" id="playListId">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" style="border-color: #e9f5fd;">
        <tr>
            <td><s:text name='sundyn.playList.version' /></td>
            <td>
                <input type="text" name="Version" id="Version"  onblur="isNumber(this.value)" value="${config.Version }" class="input_comm"/>
            </td>
        </tr>
        <tr>
            <td><s:text name='sundyn.playList.showwelcometime' /></td>
            <td>
                <input type="text" name="Welcometime" id="Welcometime" onblur="isNumber(this.value)" value="${config.Welcometime }" class="input_comm" /><s:text name='sundyn.playList.second' />
            </td>
        </tr>
        <tr>
            <td><s:text name='sundyn.playList.approvertime' /></td>
            <td>
                <input type="text" name="Approvertime" id="Approvertime" onblur="isNumber(this.value)" value="${config.Approvertime }" class="input_comm" /><s:text name='sundyn.playList.second' />
            </td>
        </tr>
        <tr>
            <td><s:text name='sundyn.playList.closeTime' /></td>
            <td class="layui-form-item">
                <div class="layui-input-inline" style="width:100px;">
                <select name="Shutdownhh" id="Shutdownhh" >
                    <option value="1" <c:if test="${config.Shutdownhh == 1}"> selected="selected" </c:if> >1</option>
                    <option value="2" <c:if test="${config.Shutdownhh == 2}"> selected="selected" </c:if>>2</option>
                    <option value="3" <c:if test="${config.Shutdownhh == 3}"> selected="selected" </c:if>>3</option>
                    <option value="4" <c:if test="${config.Shutdownhh == 4}"> selected="selected" </c:if>>4</option>
                    <option value="5" <c:if test="${config.Shutdownhh == 5}"> selected="selected" </c:if>>5</option>
                    <option value="6" <c:if test="${config.Shutdownhh == 6}"> selected="selected" </c:if>>6</option>
                    <option value="7" <c:if test="${config.Shutdownhh == 7}"> selected="selected" </c:if>>7</option>
                    <option value="8" <c:if test="${config.Shutdownhh == 8}"> selected="selected" </c:if>>8</option>
                    <option value="9" <c:if test="${config.Shutdownhh == 9}"> selected="selected" </c:if>>9</option>
                    <option value="10" <c:if test="${config.Shutdownhh == 10}"> selected="selected" </c:if>>10</option>
                    <option value="11" <c:if test="${config.Shutdownhh == 11}"> selected="selected" </c:if>>11</option>
                    <option value="12" <c:if test="${config.Shutdownhh == 12}"> selected="selected" </c:if>>12</option>
                    <option value="13" <c:if test="${config.Shutdownhh == 13}"> selected="selected" </c:if>>13</option>
                    <option value="14" <c:if test="${config.Shutdownhh == 14}"> selected="selected" </c:if>>14</option>
                    <option value="15" <c:if test="${config.Shutdownhh == 15}"> selected="selected" </c:if>>15</option>
                    <option value="16" <c:if test="${config.Shutdownhh == 16}"> selected="selected" </c:if>>16</option>
                    <option value="17" <c:if test="${config.Shutdownhh == 17}"> selected="selected" </c:if>>17</option>
                    <option value="18" <c:if test="${config.Shutdownhh == 18}"> selected="selected" </c:if>>18</option>
                    <option value="19" <c:if test="${config.Shutdownhh == 19}"> selected="selected" </c:if>>19</option>
                    <option value="20" <c:if test="${config.Shutdownhh == 20}"> selected="selected" </c:if>>20</option>
                    <option value="21" <c:if test="${config.Shutdownhh == 21}"> selected="selected" </c:if> > 21</option>
                    <option value="22" <c:if test="${config.Shutdownhh == 22}"> selected="selected" </c:if> >22</option>
                    <option value="23" <c:if test="${config.Shutdownhh == 23}"> selected="selected" </c:if>>23</option>
                    <option value="24" <c:if test="${config.Shutdownhh == 24}"> selected="selected" </c:if>>24</option>
                </select>
                </div>
                <div class="layui-form-mid layui-word-aux"><s:text name='sundyn.playList.hour' /></div>
                <div class="layui-input-inline" style="width:100px;">
                <select name="Shutdownmm" id="Shutdownmm"  >
                    <option value="0" <c:if test="${config.Shutdownmm == 0}"> selected="selected" </c:if>>0</option>
                    <option value="1" <c:if test="${config.Shutdownmm == 1}"> selected="selected" </c:if>>1</option>
                    <option value="2" <c:if test="${config.Shutdownmm == 2}"> selected="selected" </c:if>>2</option>
                    <option value="3" <c:if test="${config.Shutdownmm == 3}"> selected="selected" </c:if>>3</option>
                    <option value="4" <c:if test="${config.Shutdownmm == 4 }"> selected="selected" </c:if>>4</option>
                    <option value="5" <c:if test="${config.Shutdownmm == 5}"> selected="selected" </c:if>>5</option>
                    <option value="6" <c:if test="${config.Shutdownmm == 6}"> selected="selected" </c:if>>6</option>
                    <option value="7" <c:if test="${config.Shutdownmm == 7}"> selected="selected" </c:if>>7</option>
                    <option value="8" <c:if test="${config.Shutdownmm == 8}"> selected="selected" </c:if>>8</option>
                    <option value="9" <c:if test="${config.Shutdownmm == 9}"> selected="selected" </c:if>>9</option>
                    <option value="10" <c:if test="${config.Shutdownmm == 10}"> selected="selected" </c:if>>10</option>
                    <option value="11" <c:if test="${config.Shutdownmm == 11}"> selected="selected" </c:if>>11</option>
                    <option value="12" <c:if test="${config.Shutdownmm == 12}"> selected="selected" </c:if>>12</option>
                    <option value="13" <c:if test="${config.Shutdownmm == 13}"> selected="selected" </c:if>>13</option>
                    <option value="14" <c:if test="${config.Shutdownmm == 14}"> selected="selected" </c:if>>14</option>
                    <option value="15" <c:if test="${config.Shutdownmm == 15}"> selected="selected" </c:if>>15</option>
                    <option value="16" <c:if test="${config.Shutdownmm == 16}"> selected="selected" </c:if>>16</option>
                    <option value="17" <c:if test="${config.Shutdownmm == 17}"> selected="selected" </c:if>>17</option>
                    <option value="18" <c:if test="${config.Shutdownmm == 18}"> selected="selected" </c:if>>18</option>
                    <option value="19" <c:if test="${config.Shutdownmm == 19}"> selected="selected" </c:if>>19</option>
                    <option value="20" <c:if test="${config.Shutdownmm == 20}"> selected="selected" </c:if>>20</option>
                    <option value="21" <c:if test="${config.Shutdownmm == 21}"> selected="selected" </c:if>>21</option>
                    <option value="22" <c:if test="${config.Shutdownmm == 22}"> selected="selected" </c:if>>22</option>
                    <option value="23" <c:if test="${config.Shutdownmm == 23}"> selected="selected" </c:if>>23</option>
                    <option value="24" <c:if test="${config.Shutdownmm == 24}"> selected="selected" </c:if>>24</option>
                    <option value="25" <c:if test="${config.Shutdownmm == 25}"> selected="selected" </c:if>>25</option>
                    <option value="26" <c:if test="${config.Shutdownmm == 26}"> selected="selected" </c:if>>26</option>
                    <option value="27" <c:if test="${config.Shutdownmm == 27}"> selected="selected" </c:if>>27</option>
                    <option value="28" <c:if test="${config.Shutdownmm == 28}"> selected="selected" </c:if>>28</option>
                    <option value="29" <c:if test="${config.Shutdownmm == 29}"> selected="selected" </c:if>>29</option>
                    <option value="30" <c:if test="${config.Shutdownmm == 30}"> selected="selected" </c:if>>30</option>
                    <option value="31" <c:if test="${config.Shutdownmm == 31}"> selected="selected" </c:if>>31</option>
                    <option value="32" <c:if test="${config.Shutdownmm == 32}"> selected="selected" </c:if>>32</option>
                    <option value="33" <c:if test="${config.Shutdownmm == 33}"> selected="selected" </c:if>>33</option>
                    <option value="34" <c:if test="${config.Shutdownmm == 34}"> selected="selected" </c:if>>34</option>
                    <option value="35" <c:if test="${config.Shutdownmm == 35}"> selected="selected" </c:if>>35</option>
                    <option value="36" <c:if test="${config.Shutdownmm == 36}"> selected="selected" </c:if>>36</option>
                    <option value="37" <c:if test="${config.Shutdownmm == 37}"> selected="selected" </c:if>>37</option>
                    <option value="38" <c:if test="${config.Shutdownmm == 38}"> selected="selected" </c:if>>38</option>
                    <option value="39" <c:if test="${config.Shutdownmm == 39}"> selected="selected" </c:if>>39</option>
                    <option value="40" <c:if test="${config.Shutdownmm == 40}"> selected="selected" </c:if>>40</option>
                    <option value="41" <c:if test="${config.Shutdownmm == 41}"> selected="selected" </c:if>>41</option>
                    <option value="42" <c:if test="${config.Shutdownmm == 42}"> selected="selected" </c:if>>42</option>
                    <option value="43" <c:if test="${config.Shutdownmm == 43}"> selected="selected" </c:if>>43</option>
                    <option value="44" <c:if test="${config.Shutdownmm == 44}"> selected="selected" </c:if>>44</option>
                    <option value="45" <c:if test="${config.Shutdownmm == 45}"> selected="selected" </c:if>>45</option>
                    <option value="46" <c:if test="${config.Shutdownmm == 46}"> selected="selected" </c:if>>46</option>
                    <option value="47" <c:if test="${config.Shutdownmm == 47}"> selected="selected" </c:if>>47</option>
                    <option value="48" <c:if test="${config.Shutdownmm == 48}"> selected="selected" </c:if>>48</option>
                    <option value="49" <c:if test="${config.Shutdownmm == 49}"> selected="selected" </c:if>>49</option>
                    <option value="50" <c:if test="${config.Shutdownmm == 50}"> selected="selected" </c:if>>50</option>
                    <option value="51" <c:if test="${config.Shutdownmm == 51}"> selected="selected" </c:if>>51</option>
                    <option value="52" <c:if test="${config.Shutdownmm == 52}"> selected="selected" </c:if>>52</option>
                    <option value="53" <c:if test="${config.Shutdownmm == 53}"> selected="selected" </c:if>>53</option>
                    <option value="54" <c:if test="${config.Shutdownmm == 54}"> selected="selected" </c:if>>54</option>
                    <option value="55" <c:if test="${config.Shutdownmm == 55}"> selected="selected" </c:if>>55</option>
                    <option value="56" <c:if test="${config.Shutdownmm == 56}"> selected="selected" </c:if>>56</option>
                    <option value="57" <c:if test="${config.Shutdownmm == 57}"> selected="selected" </c:if>>57</option>
                    <option value="58" <c:if test="${config.Shutdownmm == 58}"> selected="selected" </c:if>>58</option>
                    <option value="59" <c:if test="${config.Shutdownmm == 59}"> selected="selected" </c:if>>59</option>
                </select>
                </div>
                <div class="layui-form-mid layui-word-aux"><s:text name='sundyn.playList.minute' /></div>
            </td>
        </tr>
        <tr>
            <td><s:text name="sundyn.system.playlist.autoStart"></s:text></td>
            <td class="layui-form-item">
                <div class="layui-input-inline" style="width:100px;">
                <select name="Boothh" id="Boothh">
                    <option value="1" <c:if test="${config.Boothh == 1}"> selected="selected" </c:if> >1</option>
                    <option value="2" <c:if test="${config.Boothh == 2}"> selected="selected" </c:if>>2</option>
                    <option value="3" <c:if test="${config.Boothh == 3}"> selected="selected" </c:if>>3</option>
                    <option value="4" <c:if test="${config.Boothh == 4}"> selected="selected" </c:if>>4</option>
                    <option value="5" <c:if test="${config.Boothh == 5}"> selected="selected" </c:if>>5</option>
                    <option value="6" <c:if test="${config.Boothh == 6}"> selected="selected" </c:if>>6</option>
                    <option value="7" <c:if test="${config.Boothh == 7}"> selected="selected" </c:if>>7</option>
                    <option value="8" <c:if test="${config.Boothh == 8}"> selected="selected" </c:if>>8</option>
                    <option value="9" <c:if test="${config.Boothh == 9}"> selected="selected" </c:if>>9</option>
                    <option value="10" <c:if test="${config.Boothh == 10}"> selected="selected" </c:if>>10</option>
                    <option value="11" <c:if test="${config.Boothh == 11}"> selected="selected" </c:if>>11</option>
                    <option value="12" <c:if test="${config.Boothh == 12}"> selected="selected" </c:if>>12</option>
                    <option value="13" <c:if test="${config.Boothh == 13}"> selected="selected" </c:if>>13</option>
                    <option value="14" <c:if test="${config.Boothh == 14}"> selected="selected" </c:if>>14</option>
                    <option value="15" <c:if test="${config.Boothh == 15}"> selected="selected" </c:if>>15</option>
                    <option value="16" <c:if test="${config.Boothh == 16}"> selected="selected" </c:if>>16</option>
                    <option value="17" <c:if test="${config.Boothh == 17}"> selected="selected" </c:if>>17</option>
                    <option value="18" <c:if test="${config.Boothh == 18}"> selected="selected" </c:if>>18</option>
                    <option value="19" <c:if test="${config.Boothh == 19}"> selected="selected" </c:if>>19</option>
                    <option value="20" <c:if test="${config.Boothh == 20}"> selected="selected" </c:if>>20</option>
                    <option value="21" <c:if test="${config.Boothh == 21}"> selected="selected" </c:if> > 21</option>
                    <option value="22" <c:if test="${config.Boothh == 22}"> selected="selected" </c:if> >22</option>
                    <option value="23" <c:if test="${config.Boothh == 23}"> selected="selected" </c:if>>23</option>
                    <option value="24" <c:if test="${config.Boothh == 24}"> selected="selected" </c:if>>24</option>
                </select>
                </div>
                <div class="layui-form-mid layui-word-aux"><s:text name='sundyn.playList.hour' /></div>
                <div class="layui-input-inline" style="width:100px;">
                <select name="Bootmm" id="Bootmm">
                    <option value="0" <c:if test="${config.Bootmm == 0}"> selected="selected" </c:if>>0</option>
                    <option value="1" <c:if test="${config.Bootmm == 1}"> selected="selected" </c:if>>1</option>
                    <option value="2" <c:if test="${config.Bootmm == 2}"> selected="selected" </c:if>>2</option>
                    <option value="3" <c:if test="${config.Bootmm == 3}"> selected="selected" </c:if>>3</option>
                    <option value="4" <c:if test="${config.Bootmm == 4 }"> selected="selected" </c:if>>4</option>
                    <option value="5" <c:if test="${config.Bootmm == 5}"> selected="selected" </c:if>>5</option>
                    <option value="6" <c:if test="${config.Bootmm == 6}"> selected="selected" </c:if>>6</option>
                    <option value="7" <c:if test="${config.Bootmm == 7}"> selected="selected" </c:if>>7</option>
                    <option value="8" <c:if test="${config.Bootmm == 8}"> selected="selected" </c:if>>8</option>
                    <option value="9" <c:if test="${config.Bootmm == 9}"> selected="selected" </c:if>>9</option>
                    <option value="10" <c:if test="${config.Bootmm == 10}"> selected="selected" </c:if>>10</option>
                    <option value="11" <c:if test="${config.Bootmm == 11}"> selected="selected" </c:if>>11</option>
                    <option value="12" <c:if test="${config.Bootmm == 12}"> selected="selected" </c:if>>12</option>
                    <option value="13" <c:if test="${config.Bootmm == 13}"> selected="selected" </c:if>>13</option>
                    <option value="14" <c:if test="${config.Bootmm == 14}"> selected="selected" </c:if>>14</option>
                    <option value="15" <c:if test="${config.Bootmm == 15}"> selected="selected" </c:if>>15</option>
                    <option value="16" <c:if test="${config.Bootmm == 16}"> selected="selected" </c:if>>16</option>
                    <option value="17" <c:if test="${config.Bootmm == 17}"> selected="selected" </c:if>>17</option>
                    <option value="18" <c:if test="${config.Bootmm == 18}"> selected="selected" </c:if>>18</option>
                    <option value="19" <c:if test="${config.Bootmm == 19}"> selected="selected" </c:if>>19</option>
                    <option value="20" <c:if test="${config.Bootmm == 20}"> selected="selected" </c:if>>20</option>
                    <option value="21" <c:if test="${config.Bootmm == 21}"> selected="selected" </c:if>>21</option>
                    <option value="22" <c:if test="${config.Bootmm == 22}"> selected="selected" </c:if>>22</option>
                    <option value="23" <c:if test="${config.Bootmm == 23}"> selected="selected" </c:if>>23</option>
                    <option value="24" <c:if test="${config.Bootmm == 24}"> selected="selected" </c:if>>24</option>
                    <option value="25" <c:if test="${config.Bootmm == 25}"> selected="selected" </c:if>>25</option>
                    <option value="26" <c:if test="${config.Bootmm == 26}"> selected="selected" </c:if>>26</option>
                    <option value="27" <c:if test="${config.Bootmm == 27}"> selected="selected" </c:if>>27</option>
                    <option value="28" <c:if test="${config.Bootmm == 28}"> selected="selected" </c:if>>28</option>
                    <option value="29" <c:if test="${config.Bootmm == 29}"> selected="selected" </c:if>>29</option>
                    <option value="30" <c:if test="${config.Bootmm == 30}"> selected="selected" </c:if>>30</option>
                    <option value="31" <c:if test="${config.Bootmm == 31}"> selected="selected" </c:if>>31</option>
                    <option value="32" <c:if test="${config.Bootmm == 32}"> selected="selected" </c:if>>32</option>
                    <option value="33" <c:if test="${config.Bootmm == 33}"> selected="selected" </c:if>>33</option>
                    <option value="34" <c:if test="${config.Bootmm == 34}"> selected="selected" </c:if>>34</option>
                    <option value="35" <c:if test="${config.Bootmm == 35}"> selected="selected" </c:if>>35</option>
                    <option value="36" <c:if test="${config.Bootmm == 36}"> selected="selected" </c:if>>36</option>
                    <option value="37" <c:if test="${config.Bootmm == 37}"> selected="selected" </c:if>>37</option>
                    <option value="38" <c:if test="${config.Bootmm == 38}"> selected="selected" </c:if>>38</option>
                    <option value="39" <c:if test="${config.Bootmm == 39}"> selected="selected" </c:if>>39</option>
                    <option value="40" <c:if test="${config.Bootmm == 40}"> selected="selected" </c:if>>40</option>
                    <option value="41" <c:if test="${config.Bootmm == 41}"> selected="selected" </c:if>>41</option>
                    <option value="42" <c:if test="${config.Bootmm == 42}"> selected="selected" </c:if>>42</option>
                    <option value="43" <c:if test="${config.Bootmm == 43}"> selected="selected" </c:if>>43</option>
                    <option value="44" <c:if test="${config.Bootmm == 44}"> selected="selected" </c:if>>44</option>
                    <option value="45" <c:if test="${config.Bootmm == 45}"> selected="selected" </c:if>>45</option>
                    <option value="46" <c:if test="${config.Bootmm == 46}"> selected="selected" </c:if>>46</option>
                    <option value="47" <c:if test="${config.Bootmm == 47}"> selected="selected" </c:if>>47</option>
                    <option value="48" <c:if test="${config.Bootmm == 48}"> selected="selected" </c:if>>48</option>
                    <option value="49" <c:if test="${config.Bootmm == 49}"> selected="selected" </c:if>>49</option>
                    <option value="50" <c:if test="${config.Bootmm == 50}"> selected="selected" </c:if>>50</option>
                    <option value="51" <c:if test="${config.Bootmm == 51}"> selected="selected" </c:if>>51</option>
                    <option value="52" <c:if test="${config.Bootmm == 52}"> selected="selected" </c:if>>52</option>
                    <option value="53" <c:if test="${config.Bootmm == 53}"> selected="selected" </c:if>>53</option>
                    <option value="54" <c:if test="${config.Bootmm == 54}"> selected="selected" </c:if>>54</option>
                    <option value="55" <c:if test="${config.Bootmm == 55}"> selected="selected" </c:if>>55</option>
                    <option value="56" <c:if test="${config.Bootmm == 56}"> selected="selected" </c:if>>56</option>
                    <option value="57" <c:if test="${config.Bootmm == 57}"> selected="selected" </c:if>>57</option>
                    <option value="58" <c:if test="${config.Bootmm == 58}"> selected="selected" </c:if>>58</option>
                    <option value="59" <c:if test="${config.Bootmm == 59}"> selected="selected" </c:if>>59</option>
                </select>
                </div>
                <div class="layui-form-mid layui-word-aux"><s:text name='sundyn.playList.minute' /></div>
            </td>
        </tr>
        <tr>
            <td><s:text name="sundyn.system.playlist.displayEmployee"></s:text></td>
            <td>
                <input type="radio" name="ShowEmployeePage"  title="<s:text name="sundyn.system.unit.Yes"></s:text>" value="1"   <c:if test="${config.ShowEmployeePage == '1' }"> checked="checked" </c:if> />
                <input type="radio" name="ShowEmployeePage" title="<s:text name="sundyn.system.unit.No"></s:text>"  value="0"   <c:if test="${config.ShowEmployeePage == '0' }"> checked="checked" </c:if> />
            </td>
        </tr>
        <tr>
            <td>评价后填写联系方式</td>
            <td>
                <input type="radio" name="customerInfo"  title="<s:text name="sundyn.system.unit.Yes"></s:text>" value="1" <c:if test="${config.customerInfo == '1' }"> checked="checked" </c:if> />
                <input type="radio" name="customerInfo" title="<s:text name="sundyn.system.unit.No"></s:text>"  value="0" <c:if test="${config.customerInfo == '0' }"> checked="checked" </c:if> />
            </td>
        </tr>
        <tr>
            <td><s:text name='sundyn.playList.serverIp' /></td>
            <td>
                <input type="text" name="IP" id="IP" onblur="isIP(this.value)" class="input_comm" value="${config.IP }"/>
            </td>
        </tr>
        <tr>
            <td><s:text name='sundyn.playList.serverPort' /></td>
            <td>
                <input type="text" name="Port" id="Port" onblur="isNumber(this.value)" class="input_comm" value="${config.Port }" />
            </td>
        </tr>
        <tr>
            <td><s:text name="sundyn.system.playlist.useWireless"></s:text></td>
            <td>
                <input type="radio" name="Type" value="1" title="<s:text name="sundyn.conncet.yes"></s:text>"  <c:if test="${config.Type == '1' }"> checked="checked" </c:if> />
                <input type="radio" name="Type" value="0" title="<s:text name="sundyn.connect.no"></s:text>" <c:if test="${config.Type == '0' }"> checked="checked" </c:if> />
            </td>
        </tr>
        <tr>
            <td>

            </td>
            <td>
                <img src="<s:text name='sundyn.pic.save' />"  onclick="playListConfigSaveAndroid()"
                     alt="<s:text name='sundyn.system.playlist.modify'/>" class="hand" />
                <img src="<s:text name='sundyn.pic.close' />"  onclick="closeDialog()"
                     class="hand">
            </td>
        </tr>
    </table>
</div>
</body>
<script>
    //Demo
    layui.use('form', function(){
        var form = layui.form;
    });
</script>
</html>