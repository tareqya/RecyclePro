package com.example.recyclepro.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.recyclepro.R;
import com.example.recyclepro.adapter.IdeaAdapter;
import com.example.recyclepro.callback.IdeaCallBack;
import com.example.recyclepro.callback.IdeaClickListener;
import com.example.recyclepro.utils.Category;
import com.example.recyclepro.utils.Database;
import com.example.recyclepro.utils.Idea;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;


public class RecycleFragment extends Fragment {
    private AppCompatActivity activity;
    private Database db;
    private ArrayList<Category> categories;
    private Spinner fRecycle_SP_category;
    private Spinner fRecycle_SP_product;
    private Button fRecycle_BTN_search;
    private RecyclerView fRecycle_RV_ideas;

    public RecycleFragment(AppCompatActivity activity) {
        this.activity = activity;
        db = new Database();
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
        fRecycle_RV_ideas = view.findViewById(R.id.fRecycle_RV_ideas);
    }

    private void initVars() {
        db.setIdeaCallBack(new IdeaCallBack() {
            @Override
            public void onAddIdeaComplete(Task<Void> task) {

            }

            @Override
            public void onFetchIdeasComplete(ArrayList<Idea> ideas) {
                Log.d("test", ideas.size() + "");
                IdeaAdapter ideaAdapter = new IdeaAdapter(activity, ideas);
                ideaAdapter.setIdeaClickListener(new IdeaClickListener() {
                    @Override
                    public void onClick(Idea idea, int position) {

                    }
                });

                fRecycle_RV_ideas.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                fRecycle_RV_ideas.setHasFixedSize(true);
                fRecycle_RV_ideas.setItemAnimator(new DefaultItemAnimator());
                fRecycle_RV_ideas.setAdapter(ideaAdapter);
            }
        });
        fRecycle_SP_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<String> products = categories.get(i).getProducts();
                ArrayAdapter<String> productsAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, products);
                fRecycle_SP_product.setAdapter(productsAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fRecycle_BTN_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String product = (String) fRecycle_SP_product.getSelectedItem();
                String category = ((Category) fRecycle_SP_category.getSelectedItem()).getName();
                db.fetchIdeasByCategoryAndProduct(category, product);
            }
        });
    }
}