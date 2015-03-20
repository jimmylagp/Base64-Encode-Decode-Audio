package org.voiceapp.convAudio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Environment;
import android.util.Base64;

public class Base64ToAudio extends CordovaPlugin{

    public Base64ToAudio(){

    }

	public boolean execute (String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		
		if("saveAudio".equals(action)){
			try {
				return open(args, callbackContext);
	        } catch (JSONException e) {
	            e.printStackTrace();
	            return false;
	        }
		}else if("readAudio".equals(action)){
            try {
                JSONObject parameters = args.getJSONObject(0);
                if (parameters != null) {
                    String encodedfile = null;
                    try {
                        File file = parameters.getString("filePath");
                        FileInputStream fileInputStreamReader = new FileInputStream(file);
                        byte[] bytes = new byte[(int)file.length()];
                        fileInputStreamReader.read(bytes);
                        String encodedfile = Base64.encodeToString(data, Base64.DEFAULT);
                        
                        callbackContext.success(encodedfile);
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }
		
		return false;
	}
	
    private boolean open (JSONArray args, CallbackContext ctx) throws JSONException {
    	JSONObject params = args.getJSONObject(1);
    	
        String b64String = "";
        if (b64String.startsWith("data:audio")) {
            b64String = args.getString(0).substring(21);
        } else {
            b64String = args.getString(0);
        }
    	//Optional parameter
        if (params.has("b64string"))
        	b64String = params.getString("b64string");
        
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
			return saveAudio(b64String, filename, folder, overwrite);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return overwrite;
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
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}
