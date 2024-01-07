package com.example.recyclepro.callback;

import com.google.android.gms.tasks.Task;

public interface IdeaCallBack {
    void onAddIdeaComplete(Task<Void> task);
}
