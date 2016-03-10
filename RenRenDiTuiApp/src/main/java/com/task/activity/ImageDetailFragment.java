package com.task.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.renrentui.app.R;
import com.renrentui.util.ToastUtil;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 图片详情展示
 */
public class ImageDetailFragment extends Fragment {
    private String mImageUrl;
    private ImageView mImageView;
    private ProgressBar progressBar;
    private PhotoViewAttacher mAttacher;


    public static ImageDetailFragment newInstance(String url) {
        final ImageDetailFragment fragment = new ImageDetailFragment();
        final Bundle args = new Bundle();
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments()!=null?getArguments().getString("url"):null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final  View v= inflater.inflate(R.layout.fragment_image_detail, container, false);
        mImageView = (ImageView)v.findViewById(R.id.image);
        mAttacher = new PhotoViewAttacher(mImageView);
        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                    getActivity().finish();
            }
        });
        progressBar = (ProgressBar)v.findViewById(R.id.loading);
        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageLoader.getInstance().displayImage(mImageUrl,mImageView,new SimpleImageLoadingListener(){
            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                String message = null;
                switch (failReason.getType())
                {
                    case IO_ERROR:
                        message = "下载失败";
                        break;
                    case DECODING_ERROR:
                        message = "图片无法显示";
                        break;
                    case NETWORK_DENIED:
                        message = "网络问题，无法下载";
                        break;
                    case OUT_OF_MEMORY:
                        message = "图片太大无法显示";
                        break;
                    case UNKNOWN:
                        message = "未知错误";
                        break;
                }
                ToastUtil.show(getActivity(),message);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progressBar.setVisibility(View.VISIBLE);
                mAttacher.update();
            }

//            @Override
//            public void onLoadingCancelled(String imageUri, View view) {
//                super.onLoadingCancelled(imageUri, view);
//            }
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressBar.setVisibility(View.GONE);
                mAttacher.update();
            }

        });

    }



}
