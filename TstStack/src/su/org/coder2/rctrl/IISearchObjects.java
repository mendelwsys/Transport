package su.org.coder2.rctrl;

import su.org.coder.utils.SysCoderEx;

import java.io.IOException;

public interface IISearchObjects
{ 

	GisObjectPair[] getObjectsByMousePnt(String cliname,MouseCrd crd) throws IOException, SysCoderEx; 

	RetSelRastr getPictureById(String cliname,String id_lr,String obj_id) throws IOException, SysCoderEx; 

	ChangePos toObjectByName(String cliname,String objname) throws IOException, SysCoderEx; 

	RetSelRastr selectOrGetPictureById(String cliname,String id_lr,String obj_id) throws IOException, SysCoderEx; 

	int selectObjectById(String cliname,String id_lr,String obj_id) throws IOException, SysCoderEx; 

	int deselectObjectById(String cliname,String id_lr,String obj_id) throws IOException, SysCoderEx; 

	int selectObjectsByIds(String cliname,boolean deselectPrev,byte[] ids) throws IOException, SysCoderEx; 

	int selectLargeObjectsByIds(String cliname,boolean deselectPrev,byte[] ids) throws IOException, SysCoderEx; 

	int deselectAllObjects(String cliname) throws IOException, SysCoderEx; 

}
