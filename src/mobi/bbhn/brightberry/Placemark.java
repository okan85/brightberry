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

import net.rim.device.api.util.Comparator;
import net.rim.device.api.util.Persistable;

public class Placemark implements Persistable, Comparator {
	private String name;
	private String display_location;
	private String placeid;
	private float latitude;
	private float longitude;
	private int placemarkid;

	public Placemark() {
	}

	public Placemark(int placemarkid, String placeid, String name, String display_location, float latitude, float longitude) {
		this.placemarkid = placemarkid;
		this.placeid = placeid;
		this.name = name;
		this.display_location = display_location;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public int getPlacemarkID() {
		return this.placemarkid;
	}
	
	public String getPlaceID() {
		return this.placeid;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDisplayLocation() {
		return this.display_location;
	}
	
	public float getLatitude() {
		return this.latitude;
	}
	
	public float getLongitude() {
		return this.longitude;
	}
	
	public int compare(Object Object1, Object Object2) {
		String name1 = ((Placemark) Object1).getName().toLowerCase();
		String name2 = ((Placemark) Object2).getName().toLowerCase();
		return name1.compareTo(name2);
	}
}