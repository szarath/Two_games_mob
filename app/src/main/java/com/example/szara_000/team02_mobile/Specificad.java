package com.example.szara_000.team02_mobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.EOFException;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Specificad.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Specificad#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Specificad extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MainActivity ma;
    private Mainads mainad;
    private Json js;
    private JSONObject obj;
    private  JSONArray ja;
    private JSONObject obj1;
    private  JSONArray ja1;
    private OnFragmentInteractionListener mListener;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private TextView title;

    private EditText platform;
    private EditText price;
    private EditText description;
    private TextView contactnumber;
    private EditText location;
    private TextView negiotable;
    private EditText message;
    private Button sendmesg;
    private String email;
    private String username;
    private Button report;
    private String adid;
    private String aduserid;
    private EditText msgname;
    private EditText msgemail;
    private TextView connumber;
    public static Drawable draw;
    public Specificad() {
        ma.fab.setVisibility(View.INVISIBLE);
        ma.mProgress.setVisibility(View.VISIBLE);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Specificad.
     */

    public static Specificad newInstance(String param1, String param2) {
        Specificad fragment = new Specificad();
        Bundle args = new Bundle();

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
        View view = inflater.inflate(R.layout.fragment_specificad, container, false);


        image1 = (ImageView) view.findViewById(R.id.speimagepic1);
        image2 = (ImageView) view.findViewById(R.id.speimagepic2);
        image3 = (ImageView) view.findViewById(R.id.speimagepic3);
        title = (TextView) view.findViewById(R.id.spetitle);

        platform = (EditText) view.findViewById(R.id.speplatform);
        price = (EditText) view.findViewById(R.id.speprice);
        description = (EditText) view.findViewById(R.id.spedescription);
        contactnumber = (TextView) view.findViewById(R.id.specontactnumber);
        location = (EditText) view.findViewById(R.id.spelocation);
        negiotable = (TextView) view.findViewById(R.id.spenegotiable);
        message = (EditText) view.findViewById(R.id.spemsgtext);
        msgname = (EditText) view.findViewById(R.id.spemsgname);
        msgemail = (EditText) view.findViewById(R.id.spemsgemail);
        sendmesg = (Button) view.findViewById(R.id.rspemsgbutton);
        report = (Button) view.findViewById(R.id.reportbtn);
        connumber = (TextView) view.findViewById(R.id.speconnum);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread re = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String url = "AndroidAdCRUD.svc/Androidreportad/" + adid.toString();
                            js.getserverinfo(url);
                            Snackbar.make(getView(), "Ad/User Reported", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                re.start();

                Thread reu = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String url = "AndroidUserCRUD.svc/AndroidreportUser/" + aduserid.toString();
                            js.getserverinfo(url);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                reu.start();


            }
        });

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                draw = image1.getDrawable();
                Class fragclass = null;
                fragclass = image.class;
                ma.fragmentchange(fragclass, ma.fm);
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                draw = image2.getDrawable();
                Class fragclass = null;
                fragclass = image.class;
                ma.fragmentchange(fragclass, ma.fm);
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                draw = image3.getDrawable();
                Class fragclass = null;
                fragclass = image.class;
                ma.fragmentchange(fragclass, ma.fm);
            }
        });
        sendmesg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (msgname.getText().toString().length() == 0 || msgemail.getText().toString().length() == 0 || message.getText().toString().length() == 0) {

                    Snackbar.make(getView(), "Fill in all details", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String url = "AndroidAdCRUD.svc/AndroidgetUserEmail/" + mainad.idnumber;
                            try {
                                js.getserverinfo(url);
                                obj1 = js.getserverinfo(url);
                                ja1 = obj1.getJSONArray("AndroidgetUserEmailResult");
                                final List<String> items1 = Arrays.asList(ja1.toString().split("[(\"\"*?)]"));
                                for (int k = 0; k < items1.size(); k++) {
                                    Log.d("items1", items1.get(k).toString());
                                }
                                email = items1.get(1).toString();
                                username = items1.get(3).toString();
                                String send = "AndroidAdCRUD.svc/Androidmessage/" + URLEncoder.encode(email.toString(), "UTF-8").toString() + "/" + URLEncoder.encode(username.toString(), "UTF-8").toString() + "/" + URLEncoder.encode(title.getText().toString(), "UTF-8").toString() + "/" + URLEncoder.encode(msgemail.getText().toString(), "UTF-8").toString() + "/" + URLEncoder.encode(msgname.getText().toString(), "UTF-8").toString() + "/" + URLEncoder.encode(message.getText().toString(), "UTF-8").toString();
                                Log.d("send", send);
                                js.getserverinfo(send);
                                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                                Snackbar.make(getView(), "Message sent", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                            //
                        }
                    });
                    t.start();
                }


            }

        });
        try{
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final String url = "AndroidAdCRUD.svc/AndroidgetAdDetails/" + mainad.idnumber;
                specback mytask = new specback() {
                    @Override
                    protected String doInBackground(Void... params) {
                        try {
                            //Bitmap noimg= BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
                            obj = js.getserverinfo(url);
                            ja = obj.getJSONArray("AndroidgetAdDetailsResult");
                            return "Done";
                        } catch (JSONException je) {
                            je.printStackTrace();
                        } catch (IOException ie) {
                            ie.printStackTrace();
                        }
                        return null;
                    }

                    protected void onPostExecute(String message) {
                        final List<String> items = Arrays.asList(ja.toString().split("[(\"\"*?)]"));
                        for (int i = 0; i < items.size(); i++) {
                            Log.d("getsize", "value at" + i + items.get(i).toString());
                        }
                        adid = items.get(1).toString();
                        aduserid = items.get(27).toString();
                        title.setText(items.get(3).toUpperCase());
                        platform.setText(items.get(5).toUpperCase());
                        try {
                            description.setText(items.get(9).toString().replace("\\r", "").replace("\\n", ""));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        try {
                            price.setText("R" + items.get(13).toString());
                            if (items.get(19).toString().length() > 10) {


                                image1.setImageDrawable(new BitmapDrawable(getResources(), Base64toBitmap(items.get(19).toString())));

                            } else if (items.get(19).contains("NOPIC") || items.get(19).contains("")) {

                                image1.setImageDrawable(getResources().getDrawable(R.drawable.noimage));

                            }
                            if (items.get(21).toString().length() > 10) {

                                image2.setImageDrawable(new BitmapDrawable(getResources(), Base64toBitmap(items.get(21).toString())));

                            } else if (items.get(21).contains("NOPIC") || items.get(21).contains("")) {

                                image2.setImageDrawable(getResources().getDrawable(R.drawable.noimage));
                            }
                            if (items.get(23).toString().length() > 10) {

                                image3.setImageDrawable(new BitmapDrawable(getResources(), Base64toBitmap(items.get(23).toString())));

                            } else if (items.get(23).contains("NOPIC") || items.get(23).contains("")) {

                                image3.setImageDrawable(getResources().getDrawable(R.drawable.noimage));
                            }


                            if (Boolean.parseBoolean(items.get(25).toString())) {
                                String star = "\u2605";
                                title.append(" " + star);
                            }


                            if (Boolean.parseBoolean(items.get(17).toString())) {
                                connumber.setText(items.get(33).toString());
                            } else {
                                contactnumber.setVisibility(View.INVISIBLE);
                                connumber.setVisibility(View.INVISIBLE);
                            }
                            if (Boolean.parseBoolean(items.get(15).toString())) {
                                negiotable.setVisibility(View.VISIBLE);
                            } else {
                                negiotable.setVisibility(View.INVISIBLE);
                            }

                            location.setText(items.get(11).toString());


                            ma.mProgress.setVisibility(View.GONE);
                        }
                        catch (Exception ex)
                        {ex.printStackTrace();}

                    }
                };
                mytask.execute();

            }
        });

        thread.start();

    }
    catch (Exception ex)
    {ex.printStackTrace();}

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

    public static Bitmap Base64toBitmap(String input)
    {
        byte[] decodedBytes = Base64.decode(input, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
    private abstract class specback extends AsyncTask<Void, Void, String> {



    }



}
