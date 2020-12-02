package com.ryan.luckywheel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.divyanshu.colorseekbar.ColorSeekBar;

import java.io.IOException;

public class ExampleDialog extends AppCompatDialogFragment {
    String itemTitle="";
    public static ExampleDialog newInstance(String data,int color) {
        ExampleDialog dialog = new ExampleDialog();
        Bundle args = new Bundle();
        args.putString("data", data);
        args.putInt("color", color);
        dialog.setArguments(args);
        return dialog;
    }
    private ImageView imageView;
    EditText txtTitle;
    Button btn;
    Bitmap bm=null;
    Button btnAddImage,btnCamera;
    private int  SELECT_FILE = 1;
    ColorSeekBar seekBar,seekBarTextColor;
    private ExampleDialogListener listener;
    private static final int RESULT_LOAD_IMAGE = 100;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.item_create_dialog, null);

        imageView=(ImageView)view.findViewById(R.id.imageView2) ;
        txtTitle=(EditText)view.findViewById(R.id.txtTitle);

        btnCamera=(Button)view.findViewById(R.id.btnCamera);
        btn=(Button)view.findViewById(R.id.btn) ;
        btnAddImage=(Button)view.findViewById(R.id.btnAddImage);
        seekBar=(ColorSeekBar)view.findViewById(R.id.color_seek_bar);
        seekBarTextColor=(ColorSeekBar)view.findViewById(R.id.color_seek_bar2);

        btn.setBackgroundColor(seekBar.getColor());
        if (getArguments() != null) {
           // itemTitle = getArguments().getString("itemTitle","");
            //color = getArguments().getInt("color");
            //icon=getArguments().getByteArray("icon");
            //Bitmap bmp = BitmapFactory.decodeByteArray(icon, 0, icon.length);

            //imageView.setImageBitmap(bmp);
            //txtTitle.setText(itemTitle);
            //btn.setTextColor(color);
            btn.setText(getArguments().getString("data", ""));
            itemTitle=getArguments().getString("data", "");
            txtTitle.setText(getArguments().getString("data", ""));
            btn.setBackgroundColor(getArguments().getInt("color"));

        }


        builder.setView(view)
                .setTitle("New Option")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })

                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (txtTitle.getText().toString().isEmpty()){
                            final androidx.appcompat.app.AlertDialog ad = new androidx.appcompat.app.AlertDialog.Builder(getContext()).create();

                            String titleText = "WARNING";

                            // Initialize a new foreground color span instance
                            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.RED);

                            // Initialize a new spannable string builder instance
                            SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);

                            // Apply the text color span
                            ssBuilder.setSpan(
                                    foregroundColorSpan,
                                    0,
                                    titleText.length(),
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                            );

                            // Set the alert dialog title using spannable string builder
                            ad.setTitle(ssBuilder);
                            ad.setMessage("Item Title must be not empty");
                            ad.show();
                        }
                        else {
                            String title = txtTitle.getText().toString();
                            int color = ((ColorDrawable) btn.getBackground()).getColor();
                            listener.applyTexts(title, bm, color, itemTitle);
                        }
                    }
                });

        txtTitle.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                btn.setText(txtTitle.getText().toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        seekBar.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int i) {
                btn.setBackgroundColor(i);
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                //intent.setType("image/*");
                startActivityForResult(cameraIntent, 1888);
                /*startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                        SELECT_FILE);*/
            }
        });
        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                        SELECT_FILE);

            }
        });

        seekBarTextColor.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int i) {
                btn.setTextColor(i);
            }
        });

        return builder.create();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1888 && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }else
            if (requestCode == SELECT_FILE&& resultCode == Activity.RESULT_OK)
            {
            onSelectFromGalleryResult(data);
        }

    }
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {


        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        imageView.setImageBitmap(bm);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }
    public interface ExampleDialogListener {
        void applyTexts(String title,Bitmap icon,int color,String selectTitle);
    }
}