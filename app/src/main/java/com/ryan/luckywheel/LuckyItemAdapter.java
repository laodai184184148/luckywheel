package com.ryan.luckywheel;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rubikstudio.library.LuckyWheelView;
import rubikstudio.library.model.LuckyItem;

class LuckyItemAdapter extends BaseAdapter {

    //Dữ liệu liên kết bởi Adapter là một mảng các sản phẩm
    final ArrayList<LuckyItem> itemList;

    LuckyItemAdapter(ArrayList<LuckyItem> itemList) {
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        //Trả về tổng số phần tử, nó được gọi bởi ListView
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        //Trả về dữ liệu ở vị trí position của Adapter, tương ứng là phần tử
        //có chỉ số position trong listProduct
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //convertView là View của phần tử ListView, nếu convertView != null nghĩa là
        //View này được sử dụng lại, chỉ việc cập nhật nội dung mới
        //Nếu null cần tạo mới

        View viewProduct;
        if (convertView == null) {
            viewProduct = View.inflate(parent.getContext(), R.layout.list_item_layout, null);
        } else viewProduct = convertView;

        //Bind sữ liệu phần tử vào View
        LuckyItem item = (LuckyItem) getItem(position);
        ((TextView) viewProduct.findViewById(R.id.txtWheeltTitle)).setBackgroundColor(item.getColor());
        ((TextView) viewProduct.findViewById(R.id.txtWheeltTitle)).setText(String.format(item.getTopText()));
        ((ConstraintLayout) viewProduct.findViewById(R.id.layout)).setBackgroundColor(item.getColor());

        return viewProduct;
    }

}