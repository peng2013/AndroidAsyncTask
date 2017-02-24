package com.example.peng.asynctask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by peng on 2017/2/24.
 */

public class NewsAdapter extends BaseAdapter {
    private List<NewsBean>mList;
    private LayoutInflater mInflater;
    public NewsAdapter(Context context,List<NewsBean>data){
        mList=data;
        mInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            view=mInflater.inflate(R.layout.item_layout,null);
            viewHolder.ivIcon= (ImageView) view.findViewById(R.id.iv_Icon);
            viewHolder.tvContent= (TextView) view.findViewById(R.id.iv_Content);
            viewHolder.tvTitle= (TextView) view.findViewById(R.id.iv_Title);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.ivIcon.setImageResource(R.mipmap.ic_launcher);
        //处理图片显示错位
        String url=mList.get(i).newsIconUrl;
        viewHolder.ivIcon.setTag(url);
        new ImageLoder().showImageByThread
                (viewHolder.ivIcon,mList.get(i).newsIconUrl);
        viewHolder.tvTitle.setText(mList.get(i).newsTitle);
        viewHolder.tvContent.setText(mList.get(i).newsContent);
        return view;
    }
    class ViewHolder{
        public TextView tvTitle,tvContent;
        public ImageView ivIcon;
    }

}
