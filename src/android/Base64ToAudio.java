package org.voiceapp.convAudio;

import java.io.*;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Environment;
import android.util.Base64;
import android.app.Activity;

public class Base64ToAudio extends CordovaPlugin{

    public Base64ToAudio(){

    }

    @Override
	public boolean execute (String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

		if("saveAudio".equals(action)){

            JSONObject params = args.getJSONObject(0);
        
            String b64String = params.getString("b64string");
            
            String filename = params.has("filename")
                    ? params.getString("filename")
                    : System.currentTimeMillis() + ".mp3";

            String folder = params.has("folder")
                    ? params.getString("folder")
                    //: Environment.getExternalStorageDirectory() + "/Pictures";
                    : Environment.getExternalStorageDirectory() + "/";

            Boolean overwrite = params.has("overwrite") 
                    ? params.getBoolean("overwrite") 
                    : false;

			try {
				saveAudio(b64String, filename, folder, overwrite);
                
                callbackContext.success();
	        } catch (InterruptedException e) {
	            System.err.println("Exception: " + e.getMessage());
                callbackContext.error(e.getMessage());
                return false;
	        }
		}else if("readAudio".equals(action)){
            try {
                JSONObject parameters = args.getJSONObject(0);
                if (parameters != null) {
                    String encodedfile = null;
                    try {
                        File file = new File(parameters.getString("filePath"));
                        FileInputStream fileInputStreamReader = new FileInputStream(file);
                        byte[] bytes = new byte[(int)file.length()];
                        fileInputStreamReader.read(bytes);
                        encodedfile = Base64.encodeToString(bytes, Base64.DEFAULT);
                        
                        callbackContext.success(encodedfile);
                    } catch (FileNotFoundException e) {
                       System.err.println("Exception: " + e.getMessage());
                        callbackContext.error(e.getMessage());
                        return false;
                    } catch (IOException e) {
                        System.err.println("Exception: " + e.getMessage());
                        callbackContext.error(e.getMessage());
                        return false;
                    }

                }
            } catch (JSONException e) {
                System.err.println("Exception: " + e.getMessage());
                callbackContext.error(e.getMessage());
                return false;
            }
        }
		
		return false;
	}
	
	private boolean saveAudio(String b64String, String fileName, String dirName, Boolean overwrite) throws InterruptedException, JSONException {

        try {

            //Directory and File
            File dir = new File(dirName);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(dirName, fileName);

            //Avoid overwriting a file
            if (!overwrite && file.exists()) {
                return true;
            }

            //Decode Base64 back to Binary format
            byte[] decodedBytes = Base64.decode(b64String, Base64.DEFAULT);

            //Save Binary file to phone
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            fOut.write(decodedBytes);
            fOut.close();
            
            return true;
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
