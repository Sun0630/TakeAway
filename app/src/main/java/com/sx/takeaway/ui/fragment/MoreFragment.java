package com.sx.takeaway.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sx.takeaway.R;

/**
 * @Author sunxin
 * @Date 2017/5/22 16:58
 * @Description
 */

public class MoreFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView textView = (TextView) view.findViewById(R.id.tv);
        textView.setText("更多");
        super.onViewCreated(view, savedInstanceState);

    }
}
