package com.tarena.tlbs.parser;

import org.json.JSONException;
import org.json.JSONObject;

import com.tarena.tlbs.entity.UpdateEntity;

public class UpdateParser {

	public UpdateEntity parser(String jsonString) {
		UpdateEntity updateEntity=null;
		try {
			JSONObject jsonObject=new JSONObject(jsonString);
			String status=jsonObject.getString("status");
			if(status.equals("0")){
				String version=jsonObject.getString("version");
				String changLog=jsonObject.getString("changeLog");
				String apkUrl=jsonObject.getString("apkUrl");
				updateEntity=new UpdateEntity(status, version, changLog, apkUrl);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updateEntity;
	}

}
