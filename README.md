====通知公告

生成路径：WEB-INF/source/notice.xml

====信息查询

生成路径： WEB-INF/source/mac.xml

评价信息推送接口：   appriesAddSpByPantryn.action
员工登录接口：     employeeLogin.action


==== 综合管理平台系统
系统架构：
Struts2 & Spring & MyBatis；
采用EhCache缓存数据；
Servlet实现接口
数据库采用SQL Server 2008R2以上版本

运行环境：
Windows 2008 R2或以上 / Linux各发行版 & jdk1.8 & tomcat7.0.85 & Sql Server 2008R2或以上

功能模块：
权限管理： 基于用户-角色-权限的管理体系；
系统管理：系统参数管理，组织机构及人员管理（支持多级划分如机构、大厅、窗口）；
评价器管理：配置满意率、星级等参数，信息查询、通知公告管理；资源管理及播放列表管理。
服务大厅、业务参数管理；
绩效管理及查询；
审计管理：基础数据及业务数据的安全性管理，如防篡改；
查询统计：从机构、大厅、业务、员工等维度对数据进行统计；
明细数据查询：如业务办理大厅，取号时间，叫号时间，开始、结束办理时间，办理人，服务评价结果等；
统计分析：使用图表方式对统计的数据进行直观的展现；
监控与预警：如差评预警，人流量预警，超时预警，等候人数预警，平均办理时间预警；

测试地址：
http://192.168.1.199:8080
账号 test3 / test123!@#

未实现：
1.多语言；
2。


LocalizedTextUtil.findDefaultText(msg.substring(1, msg.length()-1), locale);
