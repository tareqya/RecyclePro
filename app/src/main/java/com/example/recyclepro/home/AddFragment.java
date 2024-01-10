package com.example.recyclepro.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.recyclepro.R;
import com.example.recyclepro.callback.IdeaCallBack;
import com.example.recyclepro.utils.Category;
import com.example.recyclepro.utils.Database;
import com.example.recyclepro.utils.Generic;
import com.example.recyclepro.utils.Idea;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;


public class AddFragment extends Fragment {
    private AppCompatActivity activity;
    private Spinner fAdd_SP_category;
    private Spinner fAdd_SP_Product;
    private TextInputLayout fAdd_TF_title;
    private TextInputLayout fAdd_TF_description;
    private ImageView fAdd_IV_uploadImage;
    private Button fAdd_BTN_AddIdea;
    private Database db;
    private ArrayList<Category> categories;
    private Uri selectedImageUri;

    public AddFragment(AppCompatActivity activity) {
        // Required empty public constructor
        this.activity = activity;
        db = new Database();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        if(!Generic.checkPermissions(activity)) {
            Generic.requestPermissions(activity);
        }
        findViews(view);
        initVars();
        return view;
    }
    public void setCategories(ArrayList<Category> categories){
        this.categories = categories;
        ArrayAdapter<Category> categoriesAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, categories);
        fAdd_SP_category.setAdapter(categoriesAdapter);
    }
    private void initVars() {
        db.setIdeaCallBack(new IdeaCallBack() {
            @Override
            public void onAddIdeaComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(activity, "Idea upload successful", Toast.LENGTH_SHORT).show();
                    selectedImageUri = null;
                    fAdd_TF_title.getEditText().setText("");
                    fAdd_TF_description.getEditText().setText("");
                    fAdd_IV_uploadImage.setImageResource(R.drawable.upload);
                }
            }

            @Override
            public void onFetchIdeasComplete(ArrayList<Idea> ideas) {

            }
        });

        fAdd_BTN_AddIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkInputs()) return;
                uploadIdea();
            }
        });

        fAdd_SP_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<String> products = categories.get(i).getProducts();
                ArrayAdapter<String> productsAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, products);
                fAdd_SP_Product.setAdapter(productsAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fAdd_IV_uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageSourceDialog();
            }
        });
    }

    private void uploadIdea() {
        String title = fAdd_TF_title.getEditText().getText().toString();
        String description = fAdd_TF_description.getEditText().getText().toString();
        String uid = db.getCurrentUser().getUid();
        Random random = new Random();
        int randomNumber = 1_000_000 + random.nextInt(9_999_999);
        String ext = Generic.getFileExtension(activity, selectedImageUri);
        String imagePath = "Ideas/"+uid+"_"+randomNumber+"."+ext;
        if(db.uploadImage(selectedImageUri, imagePath)){
            String category = ((Category)fAdd_SP_category.getSelectedItem()).getName();
            String product = ((String)fAdd_SP_Product.getSelectedItem());

            Idea idea = new Idea()
                    .setCategory(category)
                    .setProduct(product)
                    .setDescription(description)
                    .setTitle(title)
                    .setImagePath(imagePath)
                    .setUid(uid);

            db.uploadIdea(idea);
        }
    }

    private boolean checkInputs() {
        String title = fAdd_TF_title.getEditText().getText().toString();
        String description = fAdd_TF_description.getEditText().getText().toString();

        if(title.isEmpty()){
            Toast.makeText(activity, "title is required!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(description.isEmpty()){
            Toast.makeText(activity, "description is required!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(selectedImageUri == null){
            Toast.makeText(activity, "image is required!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void findViews(View view) {
        fAdd_SP_category = view.findViewById(R.id.fAdd_SP_category);
        fAdd_SP_Product = view.findViewById(R.id.fAdd_SP_Product);
        fAdd_TF_title = view.findViewById(R.id.fAdd_TF_title);
        fAdd_TF_description = view.findViewById(R.id.fAdd_TF_description);
        fAdd_IV_uploadImage = view.findViewById(R.id.fAdd_IV_uploadImage);
        fAdd_BTN_AddIdea = view.findViewById(R.id.fAdd_BTN_AddIdea);

    }

    private void showImageSourceDialog() {
        CharSequence[] items = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Choose Image Source");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (which) {
                    case 0:
                        openCamera();
                        break;
                    case 1:
                        openGallery();
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraResults.launch(intent);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        gallery_results.launch(intent);
    }

    private final ActivityResultLauncher<Intent> gallery_results = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            try {
                                Intent intent = result.getData();
                                selectedImageUri = intent.getData();
                                final InputStream imageStream = activity.getContentResolver().openInputStream(selectedImageUri);
                                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                                fAdd_IV_uploadImage.setImageBitmap(selectedImage);
                            }
                            catch (FileNotFoundException e) {
                                e.printStackTrace();
                                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_LONG).show();
                            }
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(activity, "Gallery canceled", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });

    private final ActivityResultLauncher<Intent> cameraResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            Intent intent = result.getData();
                            Bitmap bitmap = (Bitmap)  intent.getExtras().get("data");
                            fAdd_IV_uploadImage.setImageBitmap(bitmap);
                            selectedImageUri = Generic.getImageUri(activity, bitmap);
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(activity, "Camera canceled", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
}