##将Activiti 分解成五个步骤：
- ### 创建流程
- ### 部署流程
- ### 启动流程
- ### 查看流程
- ### 完成流程

## Activiti各表作用:

### 一般通用数据表
 ACT_GE_BYTEARRAY      通用的流程定义和流程资源
 **ACT_GE_PROPERTY**   系统相关属性
 
### 流程定义表
 **ACT_RE_DEPLOYMENT** 部署单元信息
 ACT_RE_MODEL          模型信息
 ACT_RE_PROCDEF        已部署的流程定义
                   
### 运行实例表
 ACT_RU_EVENT_SUBSCR   运行时事件
 ACT_RU_EXECUTION      运行时流程执行实例
 ACT_RU_IDENTITYLINK   运行时用户关系信息
 ACT_RU_JOB            运行时作业
 ACT_RU_TASK           运行时任务
 ACT_RU_VARIABLE       运行时变量表
                                                                                                                                                                                                                         
### 历史流程记录表
 ACT_HI_ACTINST         历史的流程实例
 ACT_HI_ATTACHMENT      历史的流程附件
 ACT_HI_COMMENT         历史的说明性信息
 ACT_HI_DETAIL          历史的流程运行中的细节信息
 ACT_HI_IDENTITYLINK    历史的流程运行过程中用户关系
 ACT_HI_PROCINST        历史的流程实例
 ACT_HI_TASKINST        历史的任务实例
 ACT_HI_VARINST         历史的流程运行中的变量信息                  

### 用户用户组表
 ACT_ID_GROUP           身份信息-组信息                     
 ACT_ID_INFO            身份信息-组信息                        
 ACT_ID_MEMBERSHIP      身份信息-用户和组关系的中间表                       
 ACT_ID_USER            身份信息-用户信息
                        
