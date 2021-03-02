package com.example.svasuassociate.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.example.svasuassociate.R;
import com.example.svasuassociate.entity.Banner;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BannerAdapter extends PagerAdapter {
    Context context;
    List<Banner> bannerList;
    public BannerAdapter(Context context, List<Banner> bannerList) {
        this.context = context;
        this.bannerList = bannerList;
    }
    @Override
    public int getCount() {
        return bannerList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == ((ImageView) o);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if(bannerList.get(position).getImage()!=null&&!bannerList.get(position).getImage().equalsIgnoreCase("")) {
            Picasso.with(context).load(bannerList.get(position).getImage()).error(R.drawable.logo).placeholder(R.drawable.logo).fit().into(imageView);
        }
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}
