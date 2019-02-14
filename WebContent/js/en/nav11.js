
var outlookbar=new outlook();
var t;
var t1;
t=outlookbar.addtitle('Basic Functions','help',1)
outlookbar.additem('unit management',t,'baseHelp.action')
outlookbar.additem('staff management',t,'baseHelp.action?help=2')
outlookbar.additem('administrator management',t,'baseHelp.action?help=3')
outlookbar.additem('inquiry',t,'baseHelp.action?help=4')
outlookbar.additem('sum',t,'baseHelp.action?help=5')
outlookbar.additem('analysis',t,'baseHelp.action?help=6')
outlookbar.additem('change password',t,'baseHelp.action?help=7')
outlookbar.additem('get mac address',t,'baseHelp.action?help=14')

t=outlookbar.addtitle('height function','help',1)
outlookbar.additem('role',t,'baseHelp.action?help=8')
outlookbar.additem('staff on-line',t,'baseHelp.action?help=9')
outlookbar.additem('function set',t,'baseHelp.action?help=10')
//outlookbar.additem('resouse manager',t,'baseHelp.action?help=11')
//outlookbar.additem('play list manager',t,'baseHelp.action?help=12')
outlookbar.additem('key management',t,'baseHelp.action?help=13')



t=outlookbar.addtitle('System config','system',1)
outlookbar.additem('Unit management',t,'deptView.action')
outlookbar.additem('Staff mangement',t,'employeeView.action')
outlookbar.additem('terminal management',t,'apprieserQuery.action')
//outlookbar.additem('Administer mangement',t,'ManagerQuery.action')
outlookbar.additem('Administer mangement',t,'LowerManagerQuery.action')
//outlookbar.additem('Role mangement',t,'powerQuery.action')
outlookbar.additem('Role mangement',t,'lowerPowerQuery.action')
//outlookbar.additem('Attendance mangement',t,'attendanceQuery.action')
outlookbar.additem('Staff online',t,'baseOnLineEmployee.action')
//outlookbar.additem('FAQ mangement',t,'lybManage.action')
//outlookbar.additem('Data mangement',t,'appriesDel.action')
outlookbar.additem('Information',t,'weburlList.action')
outlookbar.additem('Notice',t,'noticeList.action')

t=outlookbar.addtitle('Basic config','system',1)
outlookbar.additem('Function config',t,'baseSundynSet.action')
outlookbar.additem('Resources Management',t,'playQuery.action')
outlookbar.additem('PlayList Management',t,'playListQueryAndroid.action')
//outlookbar.additem('PlayList manager',t,'playListQuery.action')
//outlookbar.additem('votes management',t,'voteQuery.action')
outlookbar.additem('Check device info',t,'m7InfoQuery.action')
outlookbar.additem('Check online device',t,'m7InfoOnline.action')


t=outlookbar.addtitle('Qucik config','system',1)
outlookbar.additem('Change password',t,'managerChangePsw.action')
//outlookbar.additem('Qucik inquiry',t,'quicklyQuery.action')
//outlookbar.additem('FAQ',t,'lybQuery.action')
//outlookbar.additem('Config guide',t,'guideLeft.action')


//
t=outlookbar.addtitle('evaluation analysis','analysis',1)
outlookbar.additem('Sum analysis',t,'analyseTotal.action')
outlookbar.additem('Satisfaction analysis',t,'analyseContent.action')
outlookbar.additem('Rate analysis',t,'analyseContentRate.action')
outlookbar.additem('Unit analysis',t,'analyseDept.action')
outlookbar.additem('Staff analysis',t,'analyseEmployee.action')

t=outlookbar.addtitle('Qucik config','analysis',1)
outlookbar.additem('Change password',t,'managerChangePsw.action')
//outlookbar.additem('Qucik inquiry',t,'quicklyQuery.action')
//outlookbar.additem('FAQ',t,'lybQuery.action')
//outlookbar.additem('Config guide',t,'guideLeft.action')
 

t=outlookbar.addtitle('Evaluation sum','inquiry',1)
outlookbar.additem('Unit sum',t,'totalDept.action')
outlookbar.additem('Hall sum',t,'totalDating.action')
outlookbar.additem('Window sum',t,'totalWindow.action')
outlookbar.additem('Staff sum',t,'totalPerson.action')


t=outlookbar.addtitle('Evaluation inquiry','inquiry',1)
outlookbar.additem('Unit inquiry',t,'queryDept.action')
outlookbar.additem('Staff inquiry',t,'queryPeopley.action')
outlookbar.additem('Result inquiry',t,'queryResult.action')
outlookbar.additem('complex inquiry',t,'queryZh.action')
outlookbar.additem('Error inquiry',t,'errorInfoQuery.action')

 
t=outlookbar.addtitle('Qucik config','inquiry',1)
outlookbar.additem('Change password',t,'managerChangePsw.action')
//outlookbar.additem('Qucik inquiry',t,'quicklyQuery.action')
//outlookbar.additem('FAQ',t,'lybQuery.action')
//outlookbar.additem('Config guide',t,'guideLeft.action')
//HomePage
t=outlookbar.addtitle('Qucik config','home',1)
outlookbar.additem('Change password',t,'managerChangePsw.action')
//outlookbar.additem('Qucik inquiry',t,'quicklyQuery.action')
//outlookbar.additem('FAQ',t,'lybQuery.action')
outlookbar.additem('Config guide',t,'guideLeft.action')
//outlookbar.additem('Regist2',t,'registerView.action')


//意见调查
t=outlookbar.addtitle('Setting','Opinion survey',1)
outlookbar.additem('Question setting',t,'adviceList.action')
t=outlookbar.addtitle('Results Statistics','Opinion survey',1)
outlookbar.additem('Results of Statistics',t,'adviceShowAnserTable.action')
outlookbar.additem('Detail of the result',t,'checkList.action')
////意见调查
//t=outlookbar.addtitle('Setting','Opinion survey',1)
//outlookbar.additem('Question of survey setting',t,'adviceList.action')
//t=outlookbar.addtitle('Result of survey','Opinion survey',1)
//outlookbar.additem('Results of Statistics',t,'adviceShowAnserTable.action')
//outlookbar.additem('Detail of the result',t,'checkList.action')
