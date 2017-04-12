// 导航栏配置文件
var outlookbar=new outlook();
var t;
var t1;
//帮助系统
t=outlookbar.addtitle('基础功能','帮助系统',1)
outlookbar.additem('机构管理',t,'baseHelp.action')
outlookbar.additem('员工管理',t,'baseHelp.action?help=2')
outlookbar.additem('用户管理',t,'baseHelp.action?help=3')
outlookbar.additem('信息查询',t,'baseHelp.action?help=4')
outlookbar.additem('信息统计',t,'baseHelp.action?help=5')
outlookbar.additem('决策分析',t,'baseHelp.action?help=6')
outlookbar.additem('修改密码',t,'baseHelp.action?help=7')
outlookbar.additem('得到mac地址',t,'baseHelp.action?help=14')

t=outlookbar.addtitle('高级功能','帮助系统',1)
outlookbar.additem('角色管理',t,'baseHelp.action?help=8')
//outlookbar.additem('在线员工',t,'baseHelp.action?help=9')
outlookbar.additem('软件设置',t,'baseHelp.action?help=10')
//outlookbar.additem('资源管理',t,'baseHelp.action?help=11')
//outlookbar.additem('播放列表',t,'baseHelp.action?help=12')
outlookbar.additem('用户自定义按键',t,'baseHelp.action?help=13')

//系统管理
t=outlookbar.addtitle('系统管理','系统管理',1)
outlookbar.additem('单位（机构）管理',t,'deptView.action')
outlookbar.additem('员工管理',t,'employeeView.action')
outlookbar.additem('终端管理',t,'apprieserQuery.action')
outlookbar.additem('用户管理',t,'LowerManagerQuery.action')
//outlookbar.additem('角色管理',t,'powerQuery.action')
outlookbar.additem('角色管理',t,'lowerPowerQuery.action')
//outlookbar.additem('考勤管理',t,'attendanceQuery.action')
outlookbar.additem('在线员工',t,'baseOnLineEmployee2.action')
//outlookbar.additem('FAQ管理',t,'lybManage.action')
//outlookbar.additem('数据维护',t,'appriesDel.action')
//outlookbar.additem('升级数据库',t,'updateDbUpdate.action')
// //outlookbar.additem('自动生成评价数据',t,'autoView.action')
//outlookbar.additem('自动生成评价数据',t,'autoView2.action')
//outlookbar.additem('意见调查',t,'autoView2.action')
outlookbar.additem('信息查询',t,'weburlList.action')
outlookbar.additem('通知公告',t,'noticeList.action')

//outlookbar.additem('业务类型',t,'businessView.action')


t=outlookbar.addtitle('基础设置','系统管理',1)
//outlookbar.additem('图片设置',t,'basePic.action') 
//outlookbar.additem('文字设置',t,'baseRead.action')
outlookbar.additem('软件设置',t,'baseSundynSet.action')
//outlookbar.additem('时间设置',t,'baseTime.action')
outlookbar.additem('资源管理',t,'playQuery.action')
//outlookbar.additem('原播放列表管理',t,'playListQuery.action')
outlookbar.additem('新播放列表管理',t,'playListQueryAndroid.action')
//outlookbar.additem('查看设备信息',t,'m7InfoQuery.action')
outlookbar.additem('查看在线设备',t,'m7InfoOnline.action')
//outlookbar.additem('投票管理',t,'voteQuery.action')
//outlookbar.additem('问卷调查管理',t,'businessView.action')
//outlookbar.additem('业务数据',t,'threeView.action')


t=outlookbar.addtitle('快速配置系统','系统管理',1)
outlookbar.additem('修改密码',t,'managerChangePsw.action')
//outlookbar.additem('管理快速查询',t,'quicklyQuery.action')
//outlookbar.additem('FAQ',t,'lybQuery.action')

//决策分析
t=outlookbar.addtitle('系统数据分析','决策分析',1)
outlookbar.additem('业务总量走势分析',t,'analyseTotal.action')
outlookbar.additem('满意量走势分析',t,'analyseContent.action')
outlookbar.additem('满意率走势分析',t,'analyseContentRate.action')
//outlookbar.additem('满意度走势分析',t,'analyseContentD.action')
outlookbar.additem('单位（机构）分析',t,'analyseDept.action')
//outlookbar.additem('部门分析',t,'analyseSection.action')
outlookbar.additem('员工绩效分析',t,'analyseEmployee.action')

t=outlookbar.addtitle('快速配置系统','决策分析',1)
outlookbar.additem('修改密码',t,'managerChangePsw.action')
//outlookbar.additem('管理快速查询',t,'quicklyQuery.action')
//outlookbar.additem('FAQ',t,'lybQuery.action')
 
//查询系统
t=outlookbar.addtitle('评价信息统计','查询统计',1)
outlookbar.additem('按照机构统计',t,'totalDept.action')
outlookbar.additem('按照大厅统计',t,'totalDating.action')
outlookbar.additem('按照窗口统计',t,'totalWindow.action')
outlookbar.additem('按照人员统计',t,'totalPerson.action')
//outlookbar.additem('按照工号统计',t,'totalJobNum.action')
//outlookbar.additem('按照部门统计',t,'totalSection.action')
//outlookbar.additem('按照问卷调查统计',t,'totalD.action')
//outlookbar.additem('按分店统计',t,'totalShop.action')


t=outlookbar.addtitle('评价信息查询','查询统计',1)
outlookbar.additem('按照机构查询',t,'queryDept.action')
outlookbar.additem('按照人员查询',t,'queryPeopley.action')
//outlookbar.additem('按照工号查询',t,'totalJobNum.action')
//outlookbar.additem('按照手机号查询',t,'queryTel.action')
//outlookbar.additem('按照身份证号查询',t,'queryIdCard.action')
outlookbar.additem('按照结果查询',t,'queryResult.action')
outlookbar.additem('综合查询',t,'queryZh.action')
outlookbar.additem('查看今天错误评价数据',t,'errorInfoQuery.action')

 
t=outlookbar.addtitle('快速配置系统','查询统计',1)
outlookbar.additem('修改密码',t,'managerChangePsw.action')
//outlookbar.additem('管理快速查询',t,'quicklyQuery.action')
//outlookbar.additem('FAQ',t,'lybQuery.action')
//首页
t=outlookbar.addtitle('快速配置系统','首页',1)
outlookbar.additem('修改密码',t,'managerChangePsw.action')
outlookbar.additem('清理视频文件',t,'deleteVideoFile.action')
//outlookbar.additem('人员调动查询',t,'staffMobility.action')
//outlookbar.additem('管理快速查询',t,'quicklyQuery.action')
//outlookbar.additem('FAQ',t,'lybQuery.action')
outlookbar.additem('配置向导',t,'guideLeft.action')
//outlookbar.additem('软件注册',t,'registerView.action')

//意见调查
t=outlookbar.addtitle('设置','意见调查',1)
outlookbar.additem('调查问题设置',t,'adviceList.action')
t=outlookbar.addtitle('调查结果','意见调查',1)
outlookbar.additem('调查结果统计',t,'adviceShowAnserTable.action')
outlookbar.additem('详细调查结果',t,'checkList.action')
