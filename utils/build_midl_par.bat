@echo off
PUSHD classes
%JAVA_HOME%bin\jar -cvf %1\%2  su\org\coder\utils\TypeId.class su\org\coder\utils\SysCoderEx.class su\org\coder\utils\String0AHelper.class su\org\coder\utils\SkelRouter.class su\org\coder\utils\ISerializeHelper.class su\org\coder\utils\Integer0AHelper.class su\org\coder\utils\IMetaInfo.class su\org\coder\utils\IProxyParent.class su\org\coder\utils\ILgMessage.class su\org\coder\utils\IInvoker.class su\org\coder\utils\IInterceptorSeq.class su\org\coder\utils\IInterceptor.class su\org\coder\utils\FiFo.class su\org\coder\utils\ExceptionAddInfo0AHelper.class su\org\coder\utils\ExceptionAddInfo.class su\org\coder\utils\DebCounter.class su\org\coder\utils\Constants.class su\org\coder\utils\CallMessageImpl.class su\org\coder\utils\Boolean0AHelper.class su\org\coder\utils\Bukovki.class su\org\coder\utils\SerialUtils.class
POPD
