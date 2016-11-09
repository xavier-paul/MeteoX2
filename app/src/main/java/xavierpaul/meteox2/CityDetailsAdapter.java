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

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CityDetailsAdapter extends ArrayAdapter<CityDetails> {

    private List<CityDetails> m_itemList;
    private Context m_context;

    public CityDetailsAdapter(List<CityDetails> itemList, Context ctx) {
        super(ctx, android.R.layout.activity_list_item, itemList);
        this.m_itemList = itemList;
        this.m_context = ctx;
    }

    public int getCount() {
        if (m_itemList != null)
            return m_itemList.size();
        return 0;
    }

    public CityDetails getItem(int position) {
        if (m_itemList != null)
            return m_itemList.get(position);
        return null;
    }

    public long getItemId(int position) {
        if (m_itemList != null)
            return m_itemList.get(position).hashCode();
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.city_details_list_item, null);
        }

        CityDetails c = m_itemList.get(position);
        TextView v_keyText = (TextView) v.findViewById(R.id.keyName);
        if (v_keyText != null)
            v_keyText.setText(c.get_key());

		TextView v_valueText = (TextView) v.findViewById(R.id.valueName);
        if (v_valueText != null)
            v_valueText.setText(c.get_value());

        try {
            ImageView v_imageIcon = (ImageView) v.findViewById(R.id.m_icon);
            if (v_imageIcon != null && c.get_value().contains(".png") && c.get_value().length() < 250) {
                v_imageIcon.setVisibility(View.VISIBLE);
                v_valueText.setVisibility(View.INVISIBLE);
                new BitmapHelper(v_imageIcon).execute(c.get_value());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return v;
    }

    public List<CityDetails> getItemList() {
        return m_itemList;
    }

    public void setItemList(List<CityDetails> itemList) {
        this.m_itemList = itemList;
    }

}
