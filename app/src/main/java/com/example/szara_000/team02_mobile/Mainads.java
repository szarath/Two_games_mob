package com.example.szara_000.team02_mobile;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.logging.Handler;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Mainads.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Mainads#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Mainads extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Json js;
    private static MainActivity ma;
    private JSONObject obj;
    public String[] name;
    public static int idnumber;
    private Handler handler;
    private JSONArray ja;

    private customgrid adapter;
    public static Drawable[] d;
    public static int[] adid;
    public static int[] price;
    public static String[] platform;
    public static String[] locaiton;
    private String mParam1;
    private ProgressBar mProgress;
    private int mProgressStatus = 0;
    private String[] Premium;
    public static boolean isOnmainad() {
        return onmainad;
    }

    public static void setOnmainad(boolean onmainad) {
        Mainads.onmainad = onmainad;
    }

    public static boolean onmainad;




    private String mParam2;
    public static int itemnumber;
    private GridView grid;
    private OnFragmentInteractionListener mListener;

    public Mainads() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Mainads.
     */
    // TODO: Rename and change types and number of parameters
    public static Mainads newInstance(String param1, String param2) {
        Mainads fragment = new Mainads();
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

        View view = inflater.inflate(R.layout.fragment_mainads, container, false);
        ma.mProgress.setVisibility(View.VISIBLE);
        ma.fab.setVisibility(View.GONE);


        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
               final String url = "AndroidAdCRUD.svc/AndroidgetRecentlyAdded";

                try {

                    mainback mTask = new mainback() {
                        @Override
                        protected String doInBackground(Void... params) {
                            try {

                                obj = js.getserverinfo(url);


                                ja = obj.getJSONArray("AndroidgetRecentlyAddedResult");

                                writeToFile(obj.toString());
                                JSONObject fromfile = new JSONObject(readFromFile());
                                return "Done";
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            return null;
                        }

                        protected void onPostExecute(String message) {
                            try{
                            if (ja != null) {

                                ma.mProgress.setVisibility(View.GONE);
                                ma.fab.setVisibility(View.VISIBLE);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        name = new String[ja.length()];
                                        d = new Drawable[ja.length()];
                                        adid = new int[ja.length()];
                                        price = new int[ja.length()];
                                        platform = new String[ja.length()];
                                        locaiton = new String[ja.length()];
                                        Premium = new String[ja.length()];
                                        try {

                                            for (int i = 0; i < ja.length(); i++) {
                                                List<String> items = Arrays.asList(ja.get(i).toString().split("[(\"\"*?)]"));   //.split("\\s*,\\s*"));

                                                for (int k = 0; k < items.size(); k++) {
                                                    Log.d("getsize", "value at" + k + items.get(k).toString());
                                                }


                                                adid[i] = Integer.parseInt(items.get(1).toString());

                                                name[i] = items.get(5).toString();
                                                platform[i] = items.get(7).toString();
                                                locaiton[i] = items.get(9).toString();
                                                Premium[i] = items.get(13).toString();

                                                if (items.get(3).toString().length() > 10) {

                                                    d[i] = new BitmapDrawable(getResources(), Base64toBitmap(items.get(3).toString()));
                                                } else if (items.get(3).contains("NOPIC") || items.get(3).contains("")) {
                                                    d[i] = getResources().getDrawable(R.drawable.noimage);

                                                }
                                                price[i] = Integer.parseInt(items.get(11).toString());
                                            }
                                            adapter = new customgrid(getView().getContext(), name, d, price,platform,locaiton,Premium);
                                            grid = (GridView) getView().findViewById(R.id.grid_view_image_text);
                                            grid.setAdapter(adapter);
                                            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view,
                                                                        int position, long id) {
                                                    Toast.makeText(view.getContext(), name[position], Toast.LENGTH_SHORT).show();
                                                    itemnumber = position;
                                                    idnumber = adid[position];

                                                    Class fragclass = null;
                                                    fragclass = Specificad.class;
                                                    ma.fragmentchange(fragclass, ma.fm);

                                                }
                                            });

                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                });

                            } else {


                                Class fragclass = null;
                                fragclass = Refresh.class;
                                ma.fragmentchange(fragclass, ma.fm);
                            }
                        }
                            catch (Exception ex)
                            {ex.printStackTrace();}
                    }
                    };
                    mTask.execute();
                }
                catch (Exception ex)
                {ex.printStackTrace();



                    Class fragclass = null;
                    fragclass = Refresh.class;
                    ma.fragmentchange(fragclass,ma.fm);

                }



            }
        });

        t.start();






        return view;
    }




    // TODO: Rename method, update argument and hook method into UI event
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public static Bitmap Base64toBitmap(String input)
    {
        byte[] decodedBytes = Base64.decode(input, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);


    }
    private abstract class mainback extends AsyncTask<Void, Void, String> {



    }

    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getContext().openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        catch (Exception ex)
        {ex.printStackTrace();}
    }


    private String readFromFile() {

        String ret = "";

        try {
            InputStream inputStream = getContext().openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


}
