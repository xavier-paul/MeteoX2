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

public class CityDetails implements Serializable {

    private String m_key;
    private String m_value;

    public CityDetails(String p_key, String p_value) {
        super();
        this.m_key = p_key;
        this.m_value = p_value;

    }

    public  String get_key() {
        return m_key;
    }
    public void   set_key(String p_key) { this.m_key = p_key; }
    public String get_value() {
        return m_value;
    }
    public void   set_value(String p_value) {
        this.m_value = p_value;
    }

}
