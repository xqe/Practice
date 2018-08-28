package com.example.xieqe.test001.autoButton;

import android.view.View;

public class ClickView {

    private View view;

    private String viewTree;

    public ClickView(View view, String viewTree) {
        this.view = view;
        this.viewTree = viewTree;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getViewTree() {
        return viewTree;
    }

    public void setViewTree(String viewTree) {
        this.viewTree = viewTree;
    }
}
