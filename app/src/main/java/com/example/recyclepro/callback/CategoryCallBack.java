package com.example.recyclepro.callback;

import com.example.recyclepro.utils.Category;

import java.util.ArrayList;

public interface CategoryCallBack {
    void onFetchCategoriesComplete(ArrayList<Category> categories);
}
