package com.example.szara_000.team02_mobile;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.TimeUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.Security;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

import static android.Manifest.permission.READ_CONTACTS;


public class Login extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button signbtn;
    public boolean logcheck = false;
    private Json js;
    private EditText passowrd;
    private EditText username;
    private EditText navue;
    private TextView ne;
    private TextView nue;
    private static MainActivity ma;
    private Hash ha;
    private String mParam1;
    private String mParam2;
    private  JSONObject obj;
    private OnFragmentInteractionListener mListener;
    private Calendar cal;


    public Login() {
        ma.fab.setVisibility(View.GONE);
        ma.mProgress.setVisibility(View.GONE);
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
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
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_login, container, false);

        username = (EditText) view.findViewById(R.id.logemail);
        passowrd = (EditText) view.findViewById(R.id.logpassword);

        signbtn = (Button) view.findViewById(R.id.email_sign_in_button);

        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( username.getText().length() ==0 || passowrd.getText().length() == 0) {
                    Snackbar.make(getView(),"Enter Details", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }

                else {
                    Thread t = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            attemptLogin();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    try {


                                        if (!obj.toString().contains("null")) {
                                            JSONArray ja = obj.getJSONArray("AndroidAuthenticateResult");
                                            ma.setLogcheck(true);
                                            ma.setUserid(ja.get(0).toString());

                                            ma.setFirstname(ja.get(1).toString());
                                            ma.setLastname(ja.get(2).toString());
                                            ma.setEmail(ja.get(3).toString());
                                            ma.setCellnumber(ja.get(4).toString());
                                            ma.setPremium(Boolean.parseBoolean(ja.get(8).toString()));


                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
                                            Date date = sdf.parse(ja.get(5).toString());
                                            Calendar cal = sdf.getCalendar();
                                            cal.setTime(date);
                                            ma.setDob(cal);

                                            Log.d("oriDate", ja.get(5).toString());
                                            Log.d("Date", date.toString());
                                            changeinfo(ma.getFirstname(), ma.getEmail());



                                            ma.setLogcheck(true);


                                            Class fragclass = null;
                                            fragclass = Mainads.class;
                                            ma.fragmentchange(fragclass,ma.fm);

                                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                            Snackbar.make(getView(), "Logged in", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();

                                        } else {
                                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                            Snackbar.make(getView(), "Invalid username or password", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        }


                                        Log.d("info", obj.toString());
                                    } catch (JSONException je) {
                                        je.printStackTrace();
                                    } catch (ParseException pe) {
                                        pe.printStackTrace();
                                    }
                                    catch (Exception ex)
                                    {ex.printStackTrace();}
                                }

                            });
                        }
                    });

                    t.start();
                }
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




    public void attemptLogin(){






        try {


           // String url = "AndroidUserCRUD.svc/AndroidAuthenticate/sza/ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f";


            String url = "AndroidUserCRUD.svc/AndroidAuthenticate/" + username.getText().toString() +"/" + ha.Hash(passowrd.getText().toString());
            obj =  js.getserverinfo(url);



        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }


    }


    public void changeinfo(String username,String email)
    {
        NavigationView navigationView= (NavigationView) getActivity().findViewById (R.id.nav_view);
        View header = navigationView.getHeaderView(0);

        TextView navuser = (TextView)   header.findViewById(R.id.navfirstname);
        TextView navemail = (TextView)   header.findViewById(R.id.navemail);

        navuser.setText(username);
        navemail.setText(email);



    }




}
