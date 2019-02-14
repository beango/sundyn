<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="dialog">
	<div class="title">
		<div class="text">
			<s:text name='sudnyn.playList.editConfigFile' />
		</div>
		<div class="close">
			<img border="0" src="images/dialog_close.gif" class="hand" onclick="closeDialog()" />
		</div>
	</div>
	<div class="content">
		<div class="content_main">
			<div  style="margin-top: 20px;width: 100%">
			<input type="hidden" value="${config.playListId}" name="playListId" id="playListId"> 
			    <table width="90%" border="0" cellspacing="0" cellpadding="0">
				  <tr>
				    <td width="30%"><s:text name='sundyn.playList.screenDirection' /></td>
				    <td width="70%"><s:text name='sundyn.playList.h' />
				      <label>
				        <input type="radio" name="rotate"   value="H"   <c:if test="${config.rotate == 'H' }"> checked="checked" </c:if> />
				      </label>
				   <s:text name='sundyn.playList.v' />
				    <label>
				      <input type="radio" name="rotate"     value="V"   <c:if test="${config.rotate == 'V' }"> checked="checked" </c:if> />
				    </label></td>
				  </tr>
				  <tr>
				    <td><s:text name='sundyn.playList.updateUrl' /></td>
				    <td><input type="text" name="update" id="update" onblur="isUrl(this.value)" value="${config.update}" /></td>
				  </tr>
				  <tr>
				    <td><s:text name='sundyn.playList.CheckDelay' /></td>
				    <td><label>
				      <input type="text" name="CheckDelay" id="CheckDelay" onblur="isNumber(this.value)" value="${config.CheckDelay}"  /><s:text name="sundyn.playList.minute"/>
				    </label></td>
				  </tr>
				  <tr>
				    <td><s:text name='sundyn.playList.closeTime' /></td>
				    <td>
				      <select name="hh" id="hh"  >
				        <option value="1" <c:if test="${config.hh == 1}"> selected="selected" </c:if> >1</option>
				        <option value="2" <c:if test="${config.hh == 2}"> selected="selected" </c:if>>2</option>
				        <option value="3" <c:if test="${config.hh == 3}"> selected="selected" </c:if>>3</option>
				        <option value="4" <c:if test="${config.hh == 4}"> selected="selected" </c:if>>4</option>
				        <option value="5" <c:if test="${config.hh == 5}"> selected="selected" </c:if>>5</option>
				        <option value="6" <c:if test="${config.hh == 6}"> selected="selected" </c:if>>6</option>
				        <option value="7" <c:if test="${config.hh == 7}"> selected="selected" </c:if>>7</option>
				        <option value="8" <c:if test="${config.hh == 8}"> selected="selected" </c:if>>8</option>
				        <option value="9" <c:if test="${config.hh == 9}"> selected="selected" </c:if>>9</option>
				        <option value="10" <c:if test="${config.hh == 10}"> selected="selected" </c:if>>10</option>
				        <option value="11" <c:if test="${config.hh == 11}"> selected="selected" </c:if>>11</option>
				        <option value="12" <c:if test="${config.hh == 12}"> selected="selected" </c:if>>12</option>
				        <option value="13" <c:if test="${config.hh == 13}"> selected="selected" </c:if>>13</option>
				        <option value="14" <c:if test="${config.hh == 14}"> selected="selected" </c:if>>14</option>
				        <option value="15" <c:if test="${config.hh == 15}"> selected="selected" </c:if>>15</option>
				        <option value="16" <c:if test="${config.hh == 16}"> selected="selected" </c:if>>16</option>
				        <option value="17" <c:if test="${config.hh == 17}"> selected="selected" </c:if>>17</option>
				        <option value="18" <c:if test="${config.hh == 18}"> selected="selected" </c:if>>18</option>
				        <option value="19" <c:if test="${config.hh == 19}"> selected="selected" </c:if>>19</option>
				        <option value="20" <c:if test="${config.hh == 20}"> selected="selected" </c:if>>20</option>
				        <option value="21" <c:if test="${config.hh == 21}"> selected="selected" </c:if> > 21</option>
				        <option value="22" <c:if test="${config.hh == 22}"> selected="selected" </c:if> >22</option>
				        <option value="23" <c:if test="${config.hh == 23}"> selected="selected" </c:if>>23</option>
				        <option value="24" <c:if test="${config.hh == 24}"> selected="selected" </c:if>>24</option>
 				      </select><s:text name='sundyn.playList.hour' />
 				       <select name="mm" id="mm"  >
 				        <option value="0" <c:if test="${config.mm == 0}"> selected="selected" </c:if>>0</option>
				        <option value="1" <c:if test="${config.mm == 1}"> selected="selected" </c:if>>1</option>
				        <option value="2" <c:if test="${config.mm == 2}"> selected="selected" </c:if>>2</option>
				        <option value="3" <c:if test="${config.mm == 3}"> selected="selected" </c:if>>3</option>
				        <option value="4" <c:if test="${config.mm == 4 }"> selected="selected" </c:if>>4</option>
				        <option value="5" <c:if test="${config.mm == 5}"> selected="selected" </c:if>>5</option>
				        <option value="6" <c:if test="${config.mm == 6}"> selected="selected" </c:if>>6</option>
				        <option value="7" <c:if test="${config.mm == 7}"> selected="selected" </c:if>>7</option>
				        <option value="8" <c:if test="${config.mm == 8}"> selected="selected" </c:if>>8</option>
				        <option value="9" <c:if test="${config.mm == 9}"> selected="selected" </c:if>>9</option>
				        <option value="10" <c:if test="${config.mm == 10}"> selected="selected" </c:if>>10</option>
				        <option value="11" <c:if test="${config.mm == 11}"> selected="selected" </c:if>>11</option>
				        <option value="12" <c:if test="${config.mm == 12}"> selected="selected" </c:if>>12</option>
				        <option value="13" <c:if test="${config.mm == 13}"> selected="selected" </c:if>>13</option>
				        <option value="14" <c:if test="${config.mm == 14}"> selected="selected" </c:if>>14</option>
				        <option value="15" <c:if test="${config.mm == 15}"> selected="selected" </c:if>>15</option>
				        <option value="16" <c:if test="${config.mm == 16}"> selected="selected" </c:if>>16</option>
				        <option value="17" <c:if test="${config.mm == 17}"> selected="selected" </c:if>>17</option>
				        <option value="18" <c:if test="${config.mm == 18}"> selected="selected" </c:if>>18</option>
				        <option value="19" <c:if test="${config.mm == 19}"> selected="selected" </c:if>>19</option>
				        <option value="20" <c:if test="${config.mm == 20}"> selected="selected" </c:if>>20</option>
				        <option value="21" <c:if test="${config.mm == 21}"> selected="selected" </c:if>>21</option>
				        <option value="22" <c:if test="${config.mm == 22}"> selected="selected" </c:if>>22</option>
				        <option value="23" <c:if test="${config.mm == 23}"> selected="selected" </c:if>>23</option>
				        <option value="24" <c:if test="${config.mm == 24}"> selected="selected" </c:if>>24</option>
				        <option value="25" <c:if test="${config.mm == 25}"> selected="selected" </c:if>>25</option>
				        <option value="26" <c:if test="${config.mm == 26}"> selected="selected" </c:if>>26</option>
				        <option value="27" <c:if test="${config.mm == 27}"> selected="selected" </c:if>>27</option>
				        <option value="28" <c:if test="${config.mm == 28}"> selected="selected" </c:if>>28</option>
				        <option value="29" <c:if test="${config.mm == 29}"> selected="selected" </c:if>>29</option>
				        <option value="30" <c:if test="${config.mm == 30}"> selected="selected" </c:if>>30</option>
				        <option value="31" <c:if test="${config.mm == 31}"> selected="selected" </c:if>>31</option>
				        <option value="32" <c:if test="${config.mm == 32}"> selected="selected" </c:if>>32</option>
				        <option value="33" <c:if test="${config.mm == 33}"> selected="selected" </c:if>>33</option>
				        <option value="34" <c:if test="${config.mm == 34}"> selected="selected" </c:if>>34</option>
				        <option value="35" <c:if test="${config.mm == 35}"> selected="selected" </c:if>>35</option>
				        <option value="36" <c:if test="${config.mm == 36}"> selected="selected" </c:if>>36</option>
				        <option value="37" <c:if test="${config.mm == 37}"> selected="selected" </c:if>>37</option>
				        <option value="38" <c:if test="${config.mm == 38}"> selected="selected" </c:if>>38</option>
				        <option value="39" <c:if test="${config.mm == 39}"> selected="selected" </c:if>>39</option>
				        <option value="40" <c:if test="${config.mm == 40}"> selected="selected" </c:if>>40</option>
				        <option value="41" <c:if test="${config.mm == 41}"> selected="selected" </c:if>>41</option>
				        <option value="42" <c:if test="${config.mm == 42}"> selected="selected" </c:if>>42</option>
				        <option value="43" <c:if test="${config.mm == 43}"> selected="selected" </c:if>>43</option>
				        <option value="44" <c:if test="${config.mm == 44}"> selected="selected" </c:if>>44</option>
				        <option value="45" <c:if test="${config.mm == 45}"> selected="selected" </c:if>>45</option>
				        <option value="46" <c:if test="${config.mm == 46}"> selected="selected" </c:if>>46</option>
				        <option value="47" <c:if test="${config.mm == 47}"> selected="selected" </c:if>>47</option>
				        <option value="48" <c:if test="${config.mm == 48}"> selected="selected" </c:if>>48</option>
				        <option value="49" <c:if test="${config.mm == 49}"> selected="selected" </c:if>>49</option>
				        <option value="50" <c:if test="${config.mm == 50}"> selected="selected" </c:if>>50</option>
				        <option value="51" <c:if test="${config.mm == 51}"> selected="selected" </c:if>>51</option>
				        <option value="52" <c:if test="${config.mm == 52}"> selected="selected" </c:if>>52</option>
				        <option value="53" <c:if test="${config.mm == 53}"> selected="selected" </c:if>>53</option>
				        <option value="54" <c:if test="${config.mm == 54}"> selected="selected" </c:if>>54</option>
				        <option value="55" <c:if test="${config.mm == 55}"> selected="selected" </c:if>>55</option>
				        <option value="56" <c:if test="${config.mm == 56}"> selected="selected" </c:if>>56</option>
				        <option value="57" <c:if test="${config.mm == 57}"> selected="selected" </c:if>>57</option>
				        <option value="58" <c:if test="${config.mm == 58}"> selected="selected" </c:if>>58</option>
				        <option value="59" <c:if test="${config.mm == 59}"> selected="selected" </c:if>>59</option>
 				      </select><s:text name='sundyn.playList.minute' />
				    </td>
				  </tr>
				  <tr>
				    <td><s:text name='sundyn.playList.NetDelay' /></td>
				    <td><label>
				      <input type="text" name="NetDelay" id="NetDelay" onblur="isNumber(this.value)"  value="${config.NetDelay }"/><s:text name='sundyn.playList.minute' />
				    </label></td>
				  </tr>
				  <tr>
				    <td><s:text name='sundyn.playList.version' /></td>
				    <td><label>
				      <input type="text" name="version" id="version"  onblur="isNumber(this.value)" value="${config.version }"/>
				    </label></td>
				  </tr>
				  <tr>
				    <td><s:text name='sundyn.playList.serverIp' /></td>
				    <td><label>
				      <input type="text" name="ip" id="ip" onblur="isIP(this.value)"  value="${config.ip }"/>
				    </label></td>
				  </tr>
				  <tr>
				    <td><s:text name='sundyn.playList.serverPort' /></td>
				    <td><label>
				      <input type="text" name="port" id="port" onblur="isNumber(this.value)"  value="${config.port }" />
				    </label></td>
				  </tr>
				  <tr>
				    <td><s:text name='sundyn.playList.approvertime' /></td>
				    <td><label>
				      <input type="text" name="approvertime" id="approvertime" onblur="isNumber(this.value)" value="${config.approvertime }"  /><s:text name='sundyn.playList.second' />
				    </label></td>
				  </tr>
				  <tr>
				    <td><s:text name='sundyn.playList.xmlHttpOvertime' /></td>
				    <td><label>
				      <input type="text" name="xmlHttpOvertime" id="xmlHttpOvertime" onblur="isNumber(this.value)" value="${config.xmlHttpOvertime }" /><s:text name='sundyn.playList.second' />
				    </label></td>
				  </tr>
				  <tr>
				    <td><s:text name='sundyn.playList.showtimecontroltime' /></td>
				    <td><label>
				      <input type="text" name="showtimecontroltime" id="showtimecontroltime" onblur="isNumber(this.value)" value="${config.showtimecontroltime }"/><s:text name='sundyn.playList.second' />
				    </label></td>
				  </tr>
				  <tr>
				    <td><s:text name='sundyn.playList.showwelcometime' /></td>
				    <td><label>
				      <input type="text" name="showwelcometime" id="showwelcometime" onblur="isNumber(this.value)" value="${config.showwelcometime }" /><s:text name='sundyn.playList.second' />
				    </label></td>
				  </tr>
				  <tr>
				    <td><s:text name='sundyn.playList.readcardtime' /></td>
				    <td><label>
				      <input type="text" name="readcardtime" id="readcardtime" onblur="isNumber(this.value)" value="${config.readcardtime }"/><s:text name='sundyn.playList.second' />
				    </label></td>
				  </tr>
				  <tr>
				    <td><s:text name="sundyn.system.playlist.displayEmployee"></s:text></td>
				    <td><label>
				      <s:text name="sundyn.system.unit.Yes"></s:text><input type="radio" name="showEmployeePage"   value="1"   <c:if test="${config.showEmployeePage == '1' }"> checked="checked" </c:if> />
				      <s:text name="sundyn.system.unit.No"></s:text><input type="radio" name="showEmployeePage"   value="0"   <c:if test="${config.showEmployeePage == '0' }"> checked="checked" </c:if> />
				    </label></td>
				  </tr>
				</table>
				<br/>
 			</div>
		</div>
	</div>
	<div class="bottom">
 			<img src="<s:text name='sundyn.pic.save' />"  onclick="playListConfigSave()"
				alt="<s:text name='sundyn.system.playlist.modify'/>" class="hand" />
  			<img src="<s:text name='sundyn.pic.close' />"  onclick="closeDialog()"
				 class="hand">
 	</div>
</div>