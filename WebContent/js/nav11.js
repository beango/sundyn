// 导航栏配置文件
var outlookbar=new outlook();
var t;
var t1;
//系统管理
t=outlookbar.addtitle('系统管理','系统管理',1)
outlookbar.additem('单位（机构）管理',t,'deptView.action')
outlookbar.additem('员工管理',t,'employeeView.action')
outlookbar.additem('评价器管理',t,'apprieserQuery.action')
outlookbar.additem('用户管理',t,'ManagerQuery.action')
outlookbar.additem('角色管理',t,'powerQuery.action')
outlookbar.additem('考勤管理',t,'attendanceQuery.action')
outlookbar.additem('在线员工',t,'baseOnLineEmployee.action')
outlookbar.additem('FAQ管理',t,'lybManage.action')
outlookbar.additem('数据维护',t,'appriesDel.action')


t=outlookbar.addtitle('基础设置','系统管理',1)
//outlookbar.additem('图片设置',t,'basePic.action') 
//outlookbar.additem('文字设置',t,'baseRead.action')
outlookbar.additem('软件设置',t,'baseSundynSet.action')
//outlookbar.additem('时间设置',t,'baseTime.action')
outlookbar.additem('资源管理',t,'playQuery.action')
outlookbar.additem('播放列表管理',t,'playListQuery.action')
outlookbar.additem('投票管理',t,'voteQuery.action')
outlookbar.additem('业务类型管理',t,'businessQuery.action')

t=outlookbar.addtitle('快速配置系统','系统管理',1)
outlookbar.additem('修改密码',t,'managerChangePsw.action')
outlookbar.additem('管理快速查询',t,'quicklyQuery.action')
outlookbar.additem('FAQ',t,'lybQuery.action')

//决策分析
t=outlookbar.addtitle('系统数据分析','决策分析',1)
outlookbar.additem('业务总量走势分析',t,'analyseTotal.action')
outlookbar.additem('满意量走势分析',t,'analyseContent.action')
outlookbar.additem('满意率走势分析',t,'analyseContentRate.action')
outlookbar.additem('单位（机构）分析',t,'analyseDept.action')
outlookbar.additem('员工绩效分析',t,'analyseEmployee.action')

t=outlookbar.addtitle('快速配置系统','决策分析',1)
outlookbar.additem('修改密码',t,'managerChangePsw.action')
outlookbar.additem('管理快速查询',t,'quicklyQuery.action')
outlookbar.additem('FAQ',t,'lybQuery.action')
 
//查询系统
t=outlookbar.addtitle('评价信息统计xx','查询统计',1)
outlookbar.additem('按照机构统计',t,'totalDept.action')
outlookbar.additem('按照大厅统计',t,'totalDating.action')
outlookbar.additem('按照窗口统计',t,'totalWindow.action')
outlookbar.additem('按照人员统计',t,'totalPerson.action')
outlookbar.additem('按照业务统计',t,'totalBusiness.action')

t=outlookbar.addtitle('评价信息查询','查询统计',1)
outlookbar.additem('按照机构查询',t,'queryDept.action')
outlookbar.additem('按照人员查询',t,'queryPeopley.action')
outlookbar.additem('按照结果查询',t,'queryResult.action')
outlookbar.additem('综合查询',t,'queryZh.action')
outlookbar.additem('查看今天错误评价数据',t,'errorInfoQuery.action')

 
t=outlookbar.addtitle('快速配置系统','查询统计',1)
outlookbar.additem('修改密码',t,'managerChangePsw.action')
outlookbar.additem('管理快速查询',t,'quicklyQuery.action')
outlookbar.additem('FAQ',t,'lybQuery.action')
//首页
t=outlookbar.addtitle('快速配置系统','首页',1)
outlookbar.additem('修改密码',t,'managerChangePsw.action')
outlookbar.additem('管理快速查询',t,'quicklyQuery.action')
outlookbar.additem('FAQ',t,'lybQuery.action')