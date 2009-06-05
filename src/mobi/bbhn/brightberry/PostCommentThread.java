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

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import net.rim.blackberry.api.browser.URLEncodedPostData;
import net.rim.device.api.system.Alert;

public class PostCommentThread extends Thread {
	String url = "http://brightkite.com/objects/";
	HttpConnection httpConnection = null;
	InputStream httpInput = null;
	DataOutputStream httpOutput = null;
	String serverResponse = "";
	PostCommentScreen screen;
	private String comment;
	Settings settings = Settings.getInstance();
	
	public PostCommentThread(String id, String comment, PostCommentScreen screen) {
		PostCommentThread tmp56_55 = this;
		tmp56_55.url = tmp56_55.url + id + "/comments.json";
		URLEncodedPostData urlenc = new URLEncodedPostData(URLEncodedPostData.DEFAULT_CHARSET, true);
		urlenc.append("comment[comment]", comment);
		this.comment = urlenc.toString();
		this.screen = screen;
	}

	public void run() {
		try {
			this.url += NetworkConfig.getConnectionParameters(this.settings.getConnectionMode());
			this.httpConnection = ((HttpConnection)Connector.open(this.url));
			this.httpConnection.setRequestMethod("POST");
			this.httpConnection.setRequestProperty("User-Agent", BrightBerry.useragent);
			this.httpConnection.setRequestProperty("Content-Language", "en-US");
			this.httpConnection.setRequestProperty("Authorization", this.settings.getAuthHeader());
			this.httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			this.httpOutput = this.httpConnection.openDataOutputStream();
			this.httpOutput.write(this.comment.getBytes());
			this.httpInput = this.httpConnection.openInputStream();
			int rc = httpConnection.getResponseCode();
			if (rc == 201) {
				if (Alert.isVibrateSupported() && settings.getVibrateOnPost()) {
					Alert.startVibrate(2000);
				}
				this.screen.callPosted(true);
			} else {
				this.screen.callPosted(false);
			}
	    } catch (IOException ex) {
	    	ex.printStackTrace();
	    }
	}
}
