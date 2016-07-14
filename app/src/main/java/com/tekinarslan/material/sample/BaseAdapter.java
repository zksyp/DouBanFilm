package com.tekinarslan.material.sample;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kaishen on 16/6/14.
 */
public class BaseAdapter<T extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<T> {
    private int mLastPosition = -1;

    protected OnItemClickListener mCallback;

//    protected ImageLoader imageLoader = ImageLoader.getInstance();
//    protected DisplayImageOptions options = MyApplication.getLoaderOptions();

    public void setOnItemClickListener(OnItemClickListener listener) {
        mCallback = listener;
    }

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(T holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void showItemAnim(final View view, int pos) {
//        final Context mContext = view.getContext();
//        if (pos > mLastPosition) {
//            view.setAlpha(0.0f);
//            view.post(new Runnable() {
//                @Override
//                public void run() {
//                    Animator animator = AnimatorInflater.loadAnimator(
//                            mContext, R.animator.slide_from_right);
//                    animator.addListener(new AnimatorListenerAdapter() {
//
//                        @Override
//                        public void onAnimationEnd() {
//                            view.setAlpha(1.0f);
//                        }
//
//                    });
//                    animator.setTarget(view);
//                    animator.start();
//                }
//            });
//            mLastPosition = pos;
//        }
    }

    public interface OnItemClickListener {
        void onItemClick(String id, String imageUrl);
    }
}
