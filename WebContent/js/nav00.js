// �����������ļ�
var outlookbar=new outlook();
var t;
var t1;
 

t=outlookbar.addtitle('��������ϵͳ','ϵͳ����',1)
outlookbar.additem('�޸�����',t,'managerChangePsw.action')
outlookbar.additem('������ٲ�ѯ',t,'quicklyQuery.action')
outlookbar.additem('FAQ',t,'lybQuery.action')

//���߷���
t=outlookbar.addtitle('ϵͳ���ݷ���','���߷���',1)
outlookbar.additem('ҵ���������Ʒ���',t,'analyseTotal.action')
outlookbar.additem('���������Ʒ���',t,'analyseContent.action')
outlookbar.additem('���������Ʒ���',t,'analyseContentRate.action')
outlookbar.additem('��λ������������',t,'analyseDept.action')
outlookbar.additem('Ա����Ч����',t,'analyseEmployee.action')

t=outlookbar.addtitle('��������ϵͳ','���߷���',1)
outlookbar.additem('�޸�����',t,'managerChangePsw.action')
outlookbar.additem('������ٲ�ѯ',t,'quicklyQuery.action')
outlookbar.additem('FAQ',t,'lybQuery.action')
 
//��ѯϵͳ
t=outlookbar.addtitle('������Ϣͳ��','��ѯͳ��',1)
outlookbar.additem('���ջ���ͳ��',t,'totalDept.action')
outlookbar.additem('���մ���ͳ��',t,'totalDating.action')
outlookbar.additem('���մ���ͳ��',t,'totalWindow.action')
outlookbar.additem('������Աͳ��',t,'totalPerson.action')
outlookbar.additem('����ҵ��ͳ��',t,'totalBusiness.action')

t=outlookbar.addtitle('������Ϣ��ѯ','��ѯͳ��',1)
outlookbar.additem('���ջ�����ѯ',t,'queryDept.action')
outlookbar.additem('������Ա��ѯ',t,'queryPeopley.action')
outlookbar.additem('���ս����ѯ',t,'queryResult.action')
outlookbar.additem('�ۺϲ�ѯ',t,'queryZh.action')
outlookbar.additem('�鿴���������������',t,'errorInfoQuery.action')

 
t=outlookbar.addtitle('��������ϵͳ','��ѯͳ��',1)
outlookbar.additem('�޸�����',t,'managerChangePsw.action')
outlookbar.additem('������ٲ�ѯ',t,'quicklyQuery.action')
outlookbar.additem('FAQ',t,'lybQuery.action')
//��ҳ
t=outlookbar.addtitle('��������ϵͳ','��ҳ',1)
outlookbar.additem('�޸�����',t,'managerChangePsw.action')
outlookbar.additem('������ٲ�ѯ',t,'quicklyQuery.action')
outlookbar.additem('FAQ',t,'lybQuery.action')