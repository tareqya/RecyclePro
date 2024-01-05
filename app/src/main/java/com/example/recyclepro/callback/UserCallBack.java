package com.example.recyclepro.callback;

import com.example.recyclepro.utils.User;
import com.google.android.gms.tasks.Task;

public interface UserCallBack {
    void onUserFetchDataComplete(User user);
    void onUpdateComplete(Task<Void> task);
}
