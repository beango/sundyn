var outlookbar=new outlook();
var t;
var t1;
t=outlookbar.addtitle('base function','help',1)
outlookbar.additem('unit manager',t,'baseHelp.action')
outlookbar.additem('staff manager',t,'baseHelp.action?help=2')
outlookbar.additem('administrator manger',t,'baseHelp.action?help=3')
outlookbar.additem('inquiry',t,'baseHelp.action?help=4')
outlookbar.additem('sum',t,'baseHelp.action?help=5')
outlookbar.additem('analyse',t,'baseHelp.action?help=6')
outlookbar.additem('change password',t,'baseHelp.action?help=7')
outlookbar.additem('get mac address',t,'baseHelp.action?help=14')

t=outlookbar.addtitle('height function','help',1)
outlookbar.additem('role',t,'baseHelp.action?help=8')
outlookbar.additem('staff on-line',t,'baseHelp.action?help=9')
outlookbar.additem('function set',t,'baseHelp.action?help=10')
outlookbar.additem('resouse manager',t,'baseHelp.action?help=11')
outlookbar.additem('play list manager',t,'baseHelp.action?help=12')
outlookbar.additem('key manager',t,'baseHelp.action?help=13')


t=outlookbar.addtitle('Qucik config','home',1)
outlookbar.additem('Change password',t,'managerChangePsw.action')
outlookbar.additem('Qucik inquiry',t,'quicklyQuery.action')
outlookbar.additem('FAQ',t,'lybQuery.action')
outlookbar.additem('Config guide',t,'guideLeft.action')
