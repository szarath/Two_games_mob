package com.example.szara_000.team02_mobile;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Specificmyad.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Specificmyad#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Specificmyad extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String userChoosenTask;
    private MainActivity ma;
    private Json js;
    private Myad myad;
    private  ArrayList<String> list;
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
    private Button editad;
    private Button deletead;
    private JSONObject obj;
    private JSONArray ja;
    private  ArrayAdapter<String> adapter;
    private String imagepath;
    private Intent in;
    private Postad.OnFragmentInteractionListener mListener;
    private Drawable img;
    private String pic1string;
    private String pic2string;
    private String pic3string;
    private String currentpick;
    private String url;
    private static final int setimageedit = 200;

    private static boolean pic1checkedit = false;
    private static boolean pic2checkedit = false;
    private static boolean pic3checkedit= false;
    private static boolean pickedit = false;
    private static final int photoedit = 1;
    private static final int cameraedit = 100;
    private static final int cameraexitedit = 110;

    private static ImageView currnetpicedit;
    private JSONObject job;
    private String adid;

    public static boolean isPic1checkedit() {
        return pic1checkedit;
    }

    public static void setPic1checkedit(boolean pic1checkedit) {
        Specificmyad.pic1checkedit = pic1checkedit;
    }

    public static boolean isPic2checkedit() {
        return pic2checkedit;
    }

    public static void setPic2checkedit(boolean pic2checkedit) {
        Specificmyad.pic2checkedit = pic2checkedit;
    }

    public static boolean isPic3checkedit() {
        return pic3checkedit;
    }

    public static void setPic3checkedit(boolean pic3checkedit) {
        Specificmyad.pic3checkedit = pic3checkedit;
    }

    public static boolean isPickedit() {
        return pickedit;
    }

    public static void setPickedit(boolean pickedit) {
        Specificmyad.pickedit = pickedit;
    }

    public Specificmyad() {
        ma.fab.setVisibility(View.INVISIBLE);
        ma.mProgress.setVisibility(View.VISIBLE);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Specificmyad.
     */
    // TODO: Rename and change types and number of parameters
    public static Specificmyad newInstance(String param1, String param2) {
        Specificmyad fragment = new Specificmyad();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_specificmyad, container, false);



        pic1 = (ImageView) view.findViewById(R.id.myadimagepic1);
        pic2 = (ImageView) view.findViewById(R.id.myadimagepic2);
        pic3 = (ImageView) view.findViewById(R.id.myadimagepic3);
        title = (EditText) view.findViewById(R.id.adedittitle);
        platform = (Spinner) view.findViewById(R.id.myadeditspinner);
        description = (EditText) view.findViewById(R.id.adeditdesc);
        price = (EditText) view.findViewById(R.id.adeditprice);
        negotiable = (CheckBox) view.findViewById(R.id.adeditnegotiable);
        shownumber= (CheckBox) view.findViewById(R.id.adeditshownum);
        location = (EditText) view.findViewById(R.id.adeditlocation);
        editad = (Button) view.findViewById(R.id.myadeditbtn);
        deletead = (Button) view.findViewById(R.id.myaddeletebtn);
        pic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPickedit(false);
                currnetpicedit = pic1;
                currentpick = "Pic1";
                selectImage();



            }
        });
        pic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPickedit(false);
                currnetpicedit = pic2;
                currentpick = "Pic2";
                selectImage();


            }
        });
        pic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPickedit(false);
                currnetpicedit = pic3;
                currentpick = "Pic3";
                selectImage();

            }
        });
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final String urlc = "AndroidAdCRUD.svc/AndroidgetAdDetails/"+ myad.idnumber;
                specificmyback mytask = new specificmyback() {
                    @Override
                    protected String doInBackground(Void... params) {
                        try {

                            obj = js.getserverinfo(urlc);
                            ja = obj.getJSONArray("AndroidgetAdDetailsResult");
                            return "Done";
                        }
                        catch(JSONException je)
                        {
                            je.printStackTrace();
                        }
                        catch (IOException ie)
                        {
                            ie.printStackTrace();
                        }
                        return null;
                    }
                    protected void onPostExecute(String message) {
                        final List<String> items = Arrays.asList(ja.toString().split("[(\"\"*?)]"));

                        for(int i =0;i < items.size();i++)
                        {

                            Log.d("getsize","value at"+i+ items.get(i).toString());

                        }

                        if(items.get(15).toString().contains("True"))
                        {
                            negotiable.setChecked(true);
                        }
                        else
                        {
                            negotiable.setChecked(false);
                        }

                        if(items.get(17).toString().contains("True"))
                        {
                            shownumber.setChecked(true);
                        }
                        else
                        {
                            shownumber.setChecked(false);
                        }


                                adid = items.get(1).toString();
                                if(items.get(19).toString().length() > 10) {




                                    pic1.setImageDrawable(new BitmapDrawable(getResources(), Base64toBitmap(items.get(19).toString())));


                                   setPic1checkedit(true);
                                }
                                else if( items.get(19).contains("NOPIC") || items.get(19).contains("")) {

                                    pic1.setImageDrawable(getResources().getDrawable(R.drawable.noimage));

                                }
                                if(items.get(21).toString().length() > 10) {

                                    pic2.setImageDrawable(new BitmapDrawable(getResources(), Base64toBitmap(items.get(21).toString())));


                                    setPic2checkedit(true);
                                }
                                else if( items.get(21).contains("NOPIC") || items.get(21).contains("")) {

                                    pic2.setImageDrawable(getResources().getDrawable(R.drawable.noimage));
                                }
                                if(items.get(23).toString().length() > 10) {

                                    pic3.setImageDrawable(new BitmapDrawable(getResources(), Base64toBitmap(items.get(23).toString())));

                                    setPic3checkedit(true);

                                }
                                else if( items.get(23).contains("NOPIC") || items.get(23).contains("")) {

                                    pic3.setImageDrawable(getResources().getDrawable(R.drawable.noimage));
                                }

                                title.setText(items.get(3).toUpperCase());

                                int spinnerPosition = adapter.getPosition(items.get(5).toUpperCase());
                                platform.setSelection(spinnerPosition);




                                location.setText(items.get(11).toString());
                                price.setText(items.get(13).toString());
                        try {
                            description.setText(items.get(9).toString().replace("\\r","").replace("\\n",""));
                        }
                        catch (Exception ex)
                        {ex.printStackTrace();}






                        ma.mProgress.setVisibility(View.GONE);


                    }
                };
                mytask.execute();

            }

        });
        thread.start();

        ma.fab.setVisibility(View.INVISIBLE);

        editad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString().length() == 0 ||description.getText().toString().length() == 0 ||location.getText().toString().length() == 0||price.getText().toString().length() == 0 ) {
                    Snackbar.make(getView(), "Please fill in all details", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                else if(description.getText().length() < 20)
                {
                    Snackbar.make(getView(), "Description is too small", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else if (pic1.getDrawable().equals(R.drawable.numberone))
                {

                    Snackbar.make(getView(), "Please ad one picture", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                else {
                    BitmapDrawable drawablepic1 = (BitmapDrawable) pic1.getDrawable();
                    Bitmap bitmappic1 = drawablepic1.getBitmap();
                    BitmapDrawable drawablepic2 = (BitmapDrawable) pic2.getDrawable();
                    Bitmap bitmappic2 = drawablepic2.getBitmap();
                    BitmapDrawable drawablepic3 = (BitmapDrawable) pic3.getDrawable();
                    Bitmap bitmappic3 = drawablepic3.getBitmap();

                    if (isPic1checkedit()) {
                        pic1string = bitmapToBase64(bitmappic1);
                    } else {
                        pic1string = "NOPIC";
                    }

                    if (isPic2checkedit()) {
                        pic2string = bitmapToBase64(bitmappic2);
                    } else {
                        pic2string = "NOPIC";
                    }

                    if (isPic3checkedit()) {
                        pic3string = bitmapToBase64(bitmappic3);
                    } else {
                        pic3string = "NOPIC";
                    }
                    String titl = title.getText().toString();
                    String desc = description.getText().toString();
                    String loc = location.getText().toString();
                    SimpleDateFormat createdate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                    try { /*url = "AndroidAdCRUD.svc/AndroidupdateAd?AdID="+adid.toString()+"&Title="+URLEncoder.encode(titl, "UTF-8").toString() +"&Platform="+
                        URLEncoder.encode(platform.getItemAtPosition(platform.getSelectedItemPosition()).toString(), "UTF-8").toString()
                        +"&Description="+URLEncoder.encode(desc, "UTF-8").toString()+"&Location="+URLEncoder.encode(loc, "UTF-8").toString()+"&Price="+price.getText().toString()+"&Negotiable="+negotiable.isChecked()+"&ShowNumber="+shownumber.isChecked()+"&Pic1Path="+pic1string+"&Pic2Path="+pic2string+"&Pic3Path="+
                        pic3string+"&PremiumAd="+ma.getPremium().toString();*/

                        url = "AndroidAdCRUD.svc/AndroidupdateAd";
                        job = new JSONObject();
                        job.put("AdID", adid.toString());
                        job.put("Title", titl.toString());
                        job.put("Platform", list.get(platform.getSelectedItemPosition()).toString());
                        job.put("Description", desc.toString());
                        job.put("Location", loc.toString());
                        job.put("Price", price.getText().toString());
                        job.put("Negotiable", String.valueOf(negotiable.isChecked()).toString());
                        job.put("ShowNumber", String.valueOf(shownumber.isChecked()).toString());
                        job.put("Pic1Path", pic1string.toString());
                        job.put("Pic2Path", pic2string.toString());
                        job.put("Pic3Path", pic3string.toString());
                        job.put("PremiumAd", ma.getPremium().toString());


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    specificmyback mytask = new specificmyback() {
                        @Override
                        protected String doInBackground(Void... params) {
                            try {

                                js.setserverinfo(url, job);


                                return "Done";
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                            return null;
                        }

                        protected void onPostExecute(String message) {
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                            Snackbar.make(getView(), "Ad Edited", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();


                            Class fragclass = null;
                            fragclass = Mainads.class;
                            ma.fragmentchange(fragclass,ma.fm);


                        }
                    };
                    mytask.execute();
                }
            }
        });

        deletead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specificmyback mytask = new specificmyback() {
                    @Override

                    protected String doInBackground(Void... params) {
                        final String url2 = "AndroidAdCRUD.svc/AndroiddeleteAd/"+ adid.toString();
                        try{

                            js.getserverinfo(url2);
                            return "Done";
                        }
                        catch(Exception ex)
                        {
                            ex.printStackTrace();
                        }

                        return null;
                    }

                    protected void onPostExecute(String message) {
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                        Snackbar.make(getView(),"Ad Deleted", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();


                        Class fragclass = null;
                        fragclass = Mainads.class;
                        ma.fragmentchange(fragclass,ma.fm);


                    }
                };
                mytask.execute();



            }
        });

         list = new ArrayList<String>();



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
                            startActivityForResult(in, cameraedit);

                        }else{
                            if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                                Toast.makeText(getActivity(),"Your Permission is needed to get access the camera",Toast.LENGTH_LONG).show();
                            }
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, 130);
                        }
                    }else{
                        in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(in, cameraedit);

                    }

                } else if (items[item].equals("Gallery")) {
                    userChoosenTask="Gallery";
                    if(result)
                        in = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);



                    startActivityForResult(in, photoedit);
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


        if(requestCode == photoedit) {
            if (resultCode == getActivity().RESULT_OK && data != null) {

                Uri pickedImage = data.getData();
                // Let's read picked image path using content resolver
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(pickedImage, filePath, null, null, null);
                cursor.moveToFirst();
                imagepath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                img = Drawable.createFromPath(imagepath);

                setPickedit(true);
                click();
                currnetpicedit.setImageDrawable(img);




                cursor.close();
            }
        }

        if (requestCode == cameraedit) {
            if (resultCode == getActivity().RESULT_OK) {
                Bitmap bmp = (Bitmap) data.getExtras().get("data");

                setPickedit(true);

                click();

                img = new BitmapDrawable(getResources(),bmp);
                currnetpicedit.setImageDrawable(img);
            } else if (resultCode == Activity.RESULT_CANCELED) {

            } else {

            }
        }




    }
    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        byte[] byteArray = byteArrayOutputStream.toByteArray();

        String encoded =  Base64.encodeToString(byteArray, Base64.NO_WRAP);

        return encoded;
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

   /* @Override
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void click()
    {
        if(currentpick == "Pic1")
        {
            setPic1checkedit(isPickedit());
            Log.d("pic1",""+isPic1checkedit());

        }

        if(currentpick == "Pic2")
        {
            setPic2checkedit(isPickedit());
            Log.d("pic2",""+isPic2checkedit());

        }

        if(currentpick == "Pic3")
        {
            setPic3checkedit(isPickedit());
            Log.d("pic3",""+isPic3checkedit());

        }


    }


    public static Bitmap Base64toBitmap(String input)
    {
        byte[] decodedBytes = Base64.decode(input, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }


    private abstract class specificmyback extends AsyncTask<Void, Void, String> {



    }

}
