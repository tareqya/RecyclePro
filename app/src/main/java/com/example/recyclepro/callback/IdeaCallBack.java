package com.example.recyclepro.callback;

import com.example.recyclepro.utils.Idea;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public interface IdeaCallBack {
    void onAddIdeaComplete(Task<Void> task);
    void onFetchIdeasComplete(ArrayList<Idea> ideas);
}
