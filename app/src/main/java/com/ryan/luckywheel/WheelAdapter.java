package com.ryan.luckywheel;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rubikstudio.library.LuckyWheelView;
import rubikstudio.library.model.LuckyItem;
import rubikstudio.library.model.Wheel;

class WheelAdapter extends BaseAdapter {

    //Dữ liệu liên kết bởi Adapter là một mảng các sản phẩm
    final ArrayList<Wheel> wheelList;

    WheelAdapter(ArrayList<Wheel> wheelList) {
        this.wheelList = wheelList;
    }

    @Override
    public int getCount() {
        //Trả về tổng số phần tử, nó được gọi bởi ListView
        return wheelList.size();
    }

    @Override
    public Object getItem(int position) {
        //Trả về dữ liệu ở vị trí position của Adapter, tương ứng là phần tử
        //có chỉ số position trong listProduct
        return wheelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //convertView là View của phần tử ListView, nếu convertView != null nghĩa là
        //View này được sử dụng lại, chỉ việc cập nhật nội dung mới
        //Nếu null cần tạo mới

        View viewProduct;
        if (convertView == null) {
            viewProduct = View.inflate(parent.getContext(), R.layout.wheel_list_item, null);
        } else viewProduct = convertView;

        //Bind sữ liệu phần tử vào View
        Wheel item = (Wheel) getItem(position);
        ((TextView) viewProduct.findViewById(R.id.txtWheeltTitle)).setText(item.getTitle());




        return viewProduct;
    }
}

