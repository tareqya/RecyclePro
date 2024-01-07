package com.example.recyclepro.utils;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.recyclepro.callback.AuthCallBack;
import com.example.recyclepro.callback.CategoryCallBack;
import com.example.recyclepro.callback.IdeaCallBack;
import com.example.recyclepro.callback.UserCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class Database {
    public static final String USERS_TABLE = "Users";
    public static final String USERS_PROFILE_IMAGES = "Profile";
    public static final String CATEGORIES_TABLE = "Categories";
    public static final String IDEAS_TABLE = "Ideas";
    private FirebaseAuth mAuth;
    private AuthCallBack authCallBack;
    private UserCallBack userCallBack;
    private IdeaCallBack ideaCallBack;
    private CategoryCallBack categoryCallBack;
    private FirebaseFirestore db;
    FirebaseStorage storage;

    public Database(){
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public void setAuthCallBack(AuthCallBack authCallBack){
        this.authCallBack = authCallBack;
    }

    public void setUserCallBack(UserCallBack userCallBack){
        this.userCallBack = userCallBack;
    }

    public void setCategoryCallBack(CategoryCallBack categoryCallBack){
        this.categoryCallBack = categoryCallBack;
    }
    public void setIdeaCallBack(IdeaCallBack ideaCallBack){
        this.ideaCallBack = ideaCallBack;
    }
    public void loginUser(String email, String password){
        this.mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                authCallBack.onLoginComplete(task);
            }
        });
    }

    public void createAccount(String email, String password, User userData) {
        this.mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(getCurrentUser() != null){
                    userData.setKey(getCurrentUser().getUid());
                    saveUserData(userData);
                }else {
                    authCallBack.onCreateAccountComplete(false, task.getException().getMessage().toString());
                }
            }
        });
    }

    public void saveUserData(User user){
        this.db.collection(USERS_TABLE).document(user.getKey()).set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                    authCallBack.onCreateAccountComplete(true, "");
                else
                    authCallBack.onCreateAccountComplete(false, task.getException().getMessage().toString());
            }
        });
    }

    public void updateUser(User user){
        this.db.collection(USERS_TABLE).document(user.getKey()).set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                      userCallBack.onUpdateComplete(task);
                    }
                });
    }
    public FirebaseUser getCurrentUser(){
        return mAuth.getCurrentUser();
    }

    public void logout() {
        this.mAuth.signOut();
    }

    public void fetchUserData(String uid){
        db.collection(USERS_TABLE).document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                User user = value.toObject(User.class);
                if(user.getImagePath() != null){
                    // download image url
                    String imageUrl = downloadImageUrl(user.getImagePath());
                    user.setImageUrl(imageUrl);
                }
                userCallBack.onUserFetchDataComplete(user);
            }
        });
    }

    public void fetchCategories(){
        this.db.collection(CATEGORIES_TABLE).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                ArrayList<Category> categories = new ArrayList<>();
                for(DocumentSnapshot item: value.getDocuments()){
                    Category category = item.toObject(Category.class);
                    category.setKey(item.getId());
                    categories.add(category);
                }

                categoryCallBack.onFetchCategoriesComplete(categories);
            }
        });
    }

    public void uploadIdea(Idea idea){
        this.db.collection(IDEAS_TABLE).document().set(idea)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                ideaCallBack.onAddIdeaComplete(task);
            }
        });
    }

    public String downloadImageUrl(String imagePath){
        Task<Uri> downloadImageTask = storage.getReference().child(imagePath).getDownloadUrl();
        while (!downloadImageTask.isComplete() && !downloadImageTask.isCanceled());
        return downloadImageTask.getResult().toString();
    }

    public boolean uploadImage(Uri imageUri, String imagePath){
        try{
            UploadTask uploadTask = storage.getReference(imagePath).putFile(imageUri);
            while (!uploadTask.isComplete() && !uploadTask.isCanceled());
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage().toString());
            return false;
        }

    }
}
