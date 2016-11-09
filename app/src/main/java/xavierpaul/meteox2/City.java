/*
 * Copyright (C) 2013 Surviving with Android (http://www.survivingwithandroid.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xavierpaul.meteox2;

import java.io.Serializable;

public class City implements Serializable {

	//name":"Bagimont","npa":"5550","region":"","country":"BEL","url":"bagimont"}

	private String m_name;
	private String m_npa;
	private String m_region;
	private String m_country;
	private String m_url;
	private int m_position;
	
	public City(String p_name, String p_npa, String p_region, String p_country, String p_url, int p_position) {
		super();
		this.m_name = p_name;
		this.m_npa = p_npa;
		this.m_region = p_region;
		this.m_country = p_country;
		this.m_url = p_url;
		this.m_position = p_position;
	}


	public  String get_name() {
		return m_name;
	}
	public void   set_name(String m_name) {
		this.m_name = m_name;
	}
	public String get_npa() {
		return m_npa;
	}
	public void   set_npa(String m_npa) {
		this.m_npa = m_npa;
	}
	public String get_region() {
		return m_region;
	}
	public void   set_region(String m_region) {
		this.m_region = m_region;
	}
	public String get_country() {
		return m_country;
	}
	public void   set_country(String m_country) {
		this.m_country = m_country;
	}
	public String get_url() {
		return m_url;
	}
	public void   set_url(String m_url) {
		this.m_url = m_url;
	}
	public int    get_position() {
		return m_position;
	}
	public void   set_position(int m_position) {
		this.m_position = m_position;
	}
}
