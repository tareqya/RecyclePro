package com.example.recyclepro.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.recyclepro.R;
import com.example.recyclepro.utils.Category;
import com.example.recyclepro.utils.Database;

import java.util.ArrayList;


public class RecycleFragment extends Fragment {
    private AppCompatActivity activity;
    private Database db;
    private ArrayList<Category> categories;
    private Spinner fRecycle_SP_category;
    private Spinner fRecycle_SP_product;
    private Button fRecycle_BTN_search;

    public RecycleFragment(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setCategories(ArrayList<Category> categories){
        this.categories = categories;
        ArrayAdapter<Category> categoriesAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, categories);
        fRecycle_SP_category.setAdapter(categoriesAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycle, container, false);
        findViews(view);
        initVars();
        return view;
    }

    private void findViews(View view) {
        fRecycle_BTN_search = view.findViewById(R.id.fRecycle_BTN_search);
        fRecycle_SP_product = view.findViewById(R.id.fRecycle_SP_product);
        fRecycle_SP_category = view.findViewById(R.id.fRecycle_SP_category);
    }

    private void initVars() {
    }
}