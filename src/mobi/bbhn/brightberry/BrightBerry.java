package mobi.bbhn.brightberry;

/*
Copyright (c) 2009, Chris Hallgren & Hallgren Networks
All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

	* Redistributions of source code must retain the above copyright notice,
	  this list of conditions and the following disclaimer.
	* Redistributions in binary form must reproduce the above copyright notice,
	  this list of conditions and the following disclaimer in the documentation
	  and/or other materials provided with the distribution.
	* Neither the name of Chris Hallgren or Hallgren Networks nor the names of
	  its contributors may be used to endorse or promote products derived from
	  this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
OF SUCH DAMAGE.
*/

import net.rim.device.api.applicationcontrol.ApplicationPermissions;
import net.rim.device.api.applicationcontrol.ApplicationPermissionsManager;
import net.rim.device.api.servicebook.ServiceBook;
import net.rim.device.api.servicebook.ServiceRecord;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.UiApplication;

public class BrightBerry extends UiApplication {
	static String version = "0.1.8-ALPHA";
	static String useragent = "BrightBerry " + version;
	static int itembgcolor = Color.WHITE;
	static int itemfontcolor = Color.BLACK;
	static int itemhlcolor = Color.LIGHTBLUE;
	static int buttonfontcolor = Color.BLACK;
	static int buttonbgcolor = Color.WHITE;
	static int buttonhlcolor = Color.LIGHTBLUE;
	
	public static void main(String[] args) {
		BrightBerry instance = new BrightBerry();
		instance.checkPermissions();
		instance.checkPermissions();
		instance.enterEventDispatcher();
	}

	public BrightBerry() {
		pushScreen(new BrightBerryMain());
	}
	
	public void checkPermissions() {
		ApplicationPermissionsManager permissionsManager = ApplicationPermissionsManager.getInstance();
		ApplicationPermissions newPermissions = new ApplicationPermissions();
		boolean permissionsRequest = false;
		
		int keyInject = permissionsManager.getPermission(ApplicationPermissions.PERMISSION_EVENT_INJECTOR);
        if (keyInject == ApplicationPermissions.VALUE_DENY){
            newPermissions.addPermission(ApplicationPermissions.PERMISSION_EVENT_INJECTOR);
            permissionsRequest = true;
        }
        
        int locationApi = permissionsManager.getPermission(ApplicationPermissions.PERMISSION_LOCATION_API);
        if (locationApi == ApplicationPermissions.VALUE_DENY) {
        	newPermissions.addPermission(ApplicationPermissions.PERMISSION_LOCATION_API);
        	permissionsRequest = true;
        }
        
        int browserFilter = permissionsManager.getPermission(ApplicationPermissions.PERMISSION_BROWSER_FILTER);
        if (browserFilter == ApplicationPermissions.VALUE_DENY) {
        	newPermissions.addPermission(ApplicationPermissions.PERMISSION_BROWSER_FILTER);
        	permissionsRequest = true;
        }
        
        int externalConnections = permissionsManager.getPermission(ApplicationPermissions.PERMISSION_EXTERNAL_CONNECTIONS);
        if (externalConnections == ApplicationPermissions.VALUE_DENY) {
        	newPermissions.addPermission(ApplicationPermissions.PERMISSION_EXTERNAL_CONNECTIONS);
        	permissionsRequest = true;
        }
        
        if (permissionsRequest){
            boolean allowed = permissionsManager.invokePermissionsRequest(newPermissions);
            if (!allowed){
                //We would show error code here
            	System.out.println("Unable to set permissions");
            }
        }
	}
	
	public static String connectionInfo() {
		ServiceRecord[] ippprecordArray = ServiceBook.getSB().findRecordsByCid("IPPP");
		if (ippprecordArray == null) {
			return "device (No Records)";
		}

		int numRecords = ippprecordArray.length;
		for (int i = 0; i < numRecords; ++i) {
			ServiceRecord ipppRecord = ippprecordArray[i];
			if ((ipppRecord.isValid()) && (ipppRecord.getName().equals("IPPP for BIBS"))) {
				return "BIS-B " + ipppRecord.getUid();
			}
		}
		return "device";
	}
	
	public static String replaceAll(String source, String pattern, String replacement) {
        if (source == null) {
            return "";
        }
       
        StringBuffer sb = new StringBuffer();
        int idx = -1;
        int patIdx = 0;

        while ((idx = source.indexOf(pattern, patIdx)) != -1) {
            sb.append(source.substring(patIdx, idx));
            sb.append(replacement);
            patIdx = idx + pattern.length();
        }
        sb.append(source.substring(patIdx));
        return sb.toString();

    }
}