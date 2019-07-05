package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.adapter;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.R;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.YoutubePlayerActivity;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.ConnectionDetector;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.ShortFilmData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private List<ShortFilmData> moviesList;
    private StorageReference mStorage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://ktu-mentor.appspot.com/short_films/");
    Context mContext;
    private boolean isInternetPresent=false;
    ConnectionDetector connectionDetector;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, duration, collegeName;
        public ImageView imageViewlogo;
        public ProgressBar progressBar;
        public Button buttonShare;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.textViewName);
            duration = (TextView) view.findViewById(R.id.textViewDuration);
            collegeName = (TextView) view.findViewById(R.id.textViewCollageName);
            imageViewlogo= (ImageView) view.findViewById(R.id.imageViewLogo);
            progressBar= (ProgressBar) view.findViewById(R.id.progress);
            buttonShare= (Button) view.findViewById(R.id.button_share);
        }
    }

    public MoviesAdapter(List<ShortFilmData> moviesList, Context context) {
        this.moviesList = moviesList;
        mContext=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.short_film_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {


        final ShortFilmData movie = moviesList.get(position);
        connectionDetector=new ConnectionDetector(mContext);
        holder.name.setText(movie.getName());
        holder.duration.setText(movie.getDuration());
        holder.collegeName.setText(movie.getCollege());


        holder.imageViewlogo.setVisibility(View.INVISIBLE);
        holder.progressBar.setVisibility(View.VISIBLE);


        mStorage.child(movie.getName()+".jpg").getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.progressBar.setVisibility(View.GONE);
                holder.imageViewlogo.setVisibility(View.VISIBLE);

                holder.imageViewlogo.setImageBitmap(bmp);
                // Use the bytes to display the image

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                holder.progressBar.setVisibility(View.GONE);
                holder.imageViewlogo.setVisibility(View.VISIBLE);
                holder.imageViewlogo.setImageResource(R.drawable.short_default);


                // Handle any errors
            }
        });


        holder.buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                isInternetPresent=connectionDetector.isConnectingToInternet();
                if (isInternetPresent) {

                    if (android.os.Build.VERSION.SDK_INT == android.os.Build.VERSION_CODES.M) {
                        int hasWriteMemoryPermission = ContextCompat.checkSelfPermission(mContext,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        if (hasWriteMemoryPermission != PackageManager.PERMISSION_GRANTED) {
                            if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                showMessageOKCancel("You need to allow access to External storage",
                                        new DialogInterface.OnClickListener() {
                                            @TargetApi(Build.VERSION_CODES.M)
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ActivityCompat.requestPermissions((Activity) mContext,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                        REQUEST_CODE_ASK_PERMISSIONS);
                                            }
                                        });
                                return;
                            }
                            ActivityCompat.requestPermissions((Activity) mContext,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    REQUEST_CODE_ASK_PERMISSIONS);
                            return;
                        } else {
                            Uri img = getLocalBitmapUri(holder.imageViewlogo);
                            if (img != null) {

                                String body = movie.getName() + "\nYoutube link: https://www.youtube.com/watch?v=" + movie.getYoutube() + "\nShared via KTU Mentor";
                                Intent shareIntent = new Intent();
                                shareIntent.setAction(Intent.ACTION_SEND);
                                shareIntent.putExtra(Intent.EXTRA_TEXT, body);
                                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Shared:\t" + movie.getName());
                                shareIntent.putExtra(Intent.EXTRA_STREAM, img);
                                shareIntent.setType("image/*");
                                mContext.startActivity(Intent.createChooser(shareIntent, "Share product"));
                            } else {
                                Toast.makeText(mContext, "No uri ", Toast.LENGTH_SHORT).show();
                            }

                        }

                    } else {
                        Uri img = getLocalBitmapUri(holder.imageViewlogo);
                        if (img != null) {

                            String body = movie.getName() + "\nYoutube link: https://www.youtube.com/watch?v=" + movie.getYoutube() + "\nShared via KTU Mentor";
                            Intent shareIntent = new Intent();
                            shareIntent.setAction(Intent.ACTION_SEND);
                            shareIntent.putExtra(Intent.EXTRA_TEXT, body);
                            shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Shared:\t" + movie.getName());
                            shareIntent.putExtra(Intent.EXTRA_STREAM, img);
                            shareIntent.setType("image/*");
                            mContext.startActivity(Intent.createChooser(shareIntent, "Share product"));
                        } else {
                            Toast.makeText(mContext, "No uri ", Toast.LENGTH_SHORT).show();
                        }

                    }
                }



                        else {
                    Toast.makeText(mContext, "No network connection ", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.imageViewlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isInternetPresent = connectionDetector.isConnectingToInternet();
                if (isInternetPresent) {
                    Intent intentYoutube = new Intent(mContext, YoutubePlayerActivity.class);
                    intentYoutube.putExtra("link", movie.getYoutube());
                    intentYoutube.putExtra("duration",movie.getDuration());
                    mContext.startActivity(intentYoutube);
                } else {
                    Toast.makeText(mContext, "No network connection", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public Uri getLocalBitmapUri(ImageView imageView) {

        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){

            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {


            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            // See https://youtu.be/5xVh-7ywKpE?t=25m25s
            File file =  new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_images_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
        return bmpUri;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(mContext)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
