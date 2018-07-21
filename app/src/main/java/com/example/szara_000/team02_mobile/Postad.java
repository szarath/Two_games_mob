package com.example.szara_000.team02_mobile;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Postad.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Postad#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Postad extends Fragment{


    private MainActivity ma;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Json js;
    private String userChoosenTask;
    private static final String TEMP_IMAGE_NAME ="tempImage" ;
    private ImageView pic1;
    private ImageView pic2;
    private ImageView pic3;
    private EditText title;
    private Spinner platform;
    private EditText description;
    private EditText price;
    private CheckBox negotiable;
    private CheckBox shownumber;
    private EditText location;
    private Button postad;
    private String post;
    private JSONObject job;
    private String mParam1;
    private String mParam2;
    private String imagepath;
    private Intent in;
    private OnFragmentInteractionListener mListener;
    private Drawable img;
    private String pic1string;
    private String pic2string;
    private String pic3string;
    private String currentpick;



    private static boolean pic1check = false;
    private static boolean pic2check = false;
    private static boolean pic3check= false;
    private static boolean pick = false;
    private static final int photo = 1;
    private static final int camera = 100;
    private static final int cameraexit = 110;
    private static ImageView currnetpic;
    private  ArrayList<String> list;
    private  String url;
    public Postad() {
       ma.fab.setVisibility(View.GONE);
        ma.mProgress.setVisibility(View.GONE);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Postad.
     */

    public static Postad newInstance(String param1, String param2) {
        Postad fragment = new Postad();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public boolean isPic1check() {
        return pic1check;
    }
    public static void setPic1check(boolean pic1check) {
        Postad.pic1check = pic1check;
    }

    public static boolean isPic2check() {
        return pic2check;
    }

    public static void setPic2check(boolean pic2check) {
        Postad.pic2check = pic2check;
    }

    public static boolean isPic3check() {
        return pic3check;
    }

    public static void setPic3check(boolean pic3check) {
        Postad.pic3check = pic3check;
    }

    public static boolean isPick() {
        return pick;
    }

    public static void setPick(boolean pick) {
        Postad.pick = pick;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_postad, container, false);

          pic1 = (ImageView) view.findViewById(R.id.imagepic1);
          pic2 = (ImageView) view.findViewById(R.id.imagepic2);
          pic3 = (ImageView) view.findViewById(R.id.imagepic3);
          title = (EditText) view.findViewById(R.id.postadtitle);
          platform = (Spinner) view.findViewById(R.id.postadspinner);
          description = (EditText) view.findViewById(R.id.postaddesc);
          price = (EditText) view.findViewById(R.id.postadprice);
          negotiable = (CheckBox) view.findViewById(R.id.postadneg);
          shownumber= (CheckBox) view.findViewById(R.id.postadneg);
          location = (EditText) view.findViewById(R.id.postadlocation);
          postad = (Button) view.findViewById(R.id.postadbtn);
        setPic1check(false);
        pic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPick(false);
                currnetpic = pic1;
                currentpick = "Pic1";
                selectImage();



            }
        });
        pic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPick(false);
                currnetpic = pic2;
                currentpick = "Pic2";
                selectImage();


            }
        });
        pic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPick(false);
                currnetpic = pic3;
                currentpick = "Pic3";
                selectImage();

            }
        });
        ma.fab.setVisibility(View.INVISIBLE);


        postad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(title.getText().toString().length() == 0 ||description.getText().toString().length() == 0 ||location.getText().toString().length() == 0||price.getText().toString().length() == 0 ||
                        list.get( platform.getSelectedItemPosition()).toString().contains("Choose a Platform") ) {
                    Snackbar.make(getView(), "Please fill in all details", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                else if(description.getText().length() < 10)
                {
                    Snackbar.make(getView(), "Description is too small", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else if(!isPic1check() && !isPic2check() && !isPic3check())
                {
                    Snackbar.make(getView(), "Post at least one picture", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
                else
                {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    ma.mProgress.setVisibility(View.VISIBLE);
                    BitmapDrawable drawablepic1 = (BitmapDrawable) pic1.getDrawable();
                    Bitmap bitmappic1 = drawablepic1.getBitmap();
                    BitmapDrawable drawablepic2 = (BitmapDrawable) pic2.getDrawable();
                    Bitmap bitmappic2 = drawablepic2.getBitmap();
                    BitmapDrawable drawablepic3 = (BitmapDrawable) pic3.getDrawable();
                    Bitmap bitmappic3 = drawablepic3.getBitmap();

                    if(isPic1check()){
                        pic1string = bitmapToBase64(bitmappic1);
                    }
                    else {
                        pic1string = "NOPIC";
                    }

                    if(isPic2check()){
                        pic2string = bitmapToBase64(bitmappic2);
                    }
                    else
                    {
                        pic2string = "NOPIC";
                    }

                    if(isPic3check()){
                        pic3string = bitmapToBase64(bitmappic3);
                    }
                    else {
                        pic3string = "NOPIC";
                    }

                    Log.d("pic1",""+isPic1check());
                    Log.d("pic2",""+isPic2check());
                    Log.d("pic3",""+isPic3check());

                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            String titl = title.getText().toString();
                            String desc = description.getText().toString();
                            String loc = location.getText().toString();
                            SimpleDateFormat createdate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                            try {
                             url = "AndroidAdCRUD.svc/AndroidinsertAd";

                                 job = new JSONObject();

                                job.put("Title",titl.toString());
                                job.put("Platform", list.get( platform.getSelectedItemPosition()).toString());
                                job.put("CreatedDate",  createdate.format(Calendar.getInstance().getTime()).toString());
                                job.put("Description",desc.toString());
                                job.put("Location",loc.toString());
                                job.put("Price",price.getText().toString());
                                job.put("Negotiable",String.valueOf( negotiable.isChecked()).toString());
                                job.put("ShowNumber",String.valueOf(shownumber.isChecked()).toString());
                                job.put("Pic1Path",pic1string.toString() );
                                job.put("Pic2Path",pic2string.toString() );
                                job.put("Pic3Path",pic3string.toString() );
                                job.put("PremiumAd",ma.getPremium().toString());
                                job.put("UserID",ma.getUserid().toString());

                            }
                            catch (Exception ex)
                            {ex.printStackTrace();}



                            postadback mTask = new postadback() {


                                @Override
                                protected String doInBackground(Void... params) {
                                    try {


                                        Log.d("Base64", pic1string);

                                        js.setserverinfo(url,job);
                                        return "Done";
                                    }
                                    catch (Exception ex)
                                    {ex.printStackTrace();}
                                    return null;
                                }
                                protected void onPostExecute(String message) {

                                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                                    Snackbar.make(getView(),"You have Posted a Ad", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();


                                    Class fragclass = null;
                                    fragclass = Mainads.class;
                                    ma.fragmentchange(fragclass,ma.fm);


                                }
                            };
                            mTask.execute();

                        }
                    });
                    t.start();




                }

            }
        });



         list = new ArrayList<String>();

        /** Declaring an ArrayAdapter to set items to ListView */
        ArrayAdapter<String> adapter;
        list.add("Choose a Platform");
        list.add("PSP");
        list.add("PS VITA");
        list.add("PS3");
        list.add("PS4");
        list.add("XBOX 360");
        list.add("XBOX ONE");
        list.add("WII U");
        list.add("3DS");
        list.add("PS2");
        list.add("DS");
        list.add("XBOX");
        list.add("GAMECUBE");
        list.add("WII");

        //"PS3","PS4","XBOX 360","XBOX ONE","WII U","3DS","PS2","DS","XBOX","GAMECUBE","WII","PSP","PS VITA"

        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, list);
        platform.setAdapter(adapter);

        return view;
    }
    private void selectImage() {
        final CharSequence[] items = { "Camera", "Gallery",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(getActivity());
                if (items[item].equals("Camera")) {
                    userChoosenTask="Camera";
                    if(result)

                    in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if(getActivity().checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                            in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(in, camera);

                        }else{
                            if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                                Toast.makeText(getActivity(),"Your Permission is needed to get access the camera",Toast.LENGTH_LONG).show();
                            }
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, 130);
                        }
                    }else{
                        in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(in, camera);

                    }

                } else if (items[item].equals("Gallery")) {
                    userChoosenTask="Gallery";
                    if(result)
                        in = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);



                    startActivityForResult(in, photo);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == photo) {
            if (resultCode == getActivity().RESULT_OK && data != null) {

                Uri pickedImage = data.getData();

                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(pickedImage, filePath, null, null, null);
                cursor.moveToFirst();
                imagepath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                img = Drawable.createFromPath(imagepath);

                setPick(true);
                click();

                currnetpic.setImageDrawable(img);




                cursor.close();
            }
        }

            if (requestCode == camera) {
                if (resultCode == getActivity().RESULT_OK) {
                    Bitmap bmp = (Bitmap) data.getExtras().get("data");

                  setPick(true);

                    click();

                    img = new BitmapDrawable(getResources(),bmp);

                    currnetpic.setImageDrawable(img);
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    // User cancelled the image capture
                } else {
                    // Image capture failed, advise user
                }
            }


    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }






    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private String bitmapToBase64(Bitmap bitmap) {



        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        byte[] byteArray = byteArrayOutputStream.toByteArray();

        String encoded =  Base64.encodeToString(byteArray, Base64.NO_WRAP);

        return encoded;


    }







    private abstract class postadback extends AsyncTask<Void, Void, String> {



    }
    private void click()
    {
        if(currentpick == "Pic1")
        {
            setPic1check(isPick());


        }

        if(currentpick == "Pic2")
        {
            setPic2check(isPick());


        }

        if(currentpick == "Pic3")
        {
            setPic3check(isPick());


        }


    }
}
