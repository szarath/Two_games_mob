package com.example.szara_000.team02_mobile;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static android.R.attr.fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Account.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Account#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Account extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText  afn;
    private EditText  aln;
    private EditText  ae;
    private EditText  acell;
    private EditText adob;
    private static MainActivity ma;
    private Button changebtn;
    private Json js;
    private Login log;
    private int mYear;
    private  int mMonth;
    private int mDay;
    private String mParam1;
    private String mParam2;
    private Date date;
    private OnFragmentInteractionListener mListener;

    public Account() {
        ma.fab.setVisibility(View.GONE);
        ma.mProgress.setVisibility(View.GONE);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Account.
     */
    // TODO: Rename and change types and number of parameters
    public static Account newInstance(String param1, String param2) {
        Account fragment = new Account();
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

        View view =inflater.inflate(R.layout.fragment_account, container, false);

        afn = (EditText) view.findViewById(R.id.afirstname);
        aln = (EditText) view.findViewById(R.id.alastname);
        ae = (EditText ) view.findViewById(R.id.aregemail);
        acell = (EditText ) view.findViewById(R.id.acellnumber);
        adob = (EditText) view.findViewById(R.id.adateofbirth);
        changebtn = (Button) view.findViewById(R.id.saveacc_button);

        afn.setText(ma.getFirstname());
        aln.setText(ma.getLastname());
        ae.setText(ma.getEmail());
        acell.setText(ma.getCellnumber());

        Calendar cal = ma.getDob();
        mYear = cal.get(Calendar.YEAR);

        mMonth = cal.get(Calendar.MONTH) +1 ;
        mDay = cal.get(Calendar.DAY_OF_MONTH) ;
        Log.d("Date",ma.getDob().toString());
        adob.setText("" + mDay + "/" + mMonth  + "/" + mYear);
        adob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //To show current date in the datepicker
                Calendar cal = Calendar.getInstance();
                mYear = cal.get(Calendar.YEAR);

                mMonth = cal.get(Calendar.MONTH);
                mDay = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth , int selectedday)

                    {   selectedmonth = selectedmonth + 1;
                        adob.setText("" + selectedday + "/" + selectedmonth  + "/" + selectedyear);}},mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();  }
        });

        changebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                        try {
                             date = dateFormat.parse(adob.getText().toString());
                        }
                        catch (Exception ex)
                        {ex.printStackTrace();}

                        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

                      final  Calendar cal = dateFormat.getCalendar();

                       final String url = "AndroidUserCRUD.svc/AndroidupdateUserInfo/"+ma.getUserid()+"/"+afn.getText().toString()+"/"+aln.getText().toString()+"/"+acell.getText().toString()+"/"+dateFormat2.format(date)+"/"+ae.getText().toString();

                        accback mytask = new accback() {
                            @Override
                            protected String doInBackground(Void... params) {
                                try{ js.getserverinfo(url);}
                                catch (Exception ex)
                                {ex.printStackTrace();}



                                return null;
                            }

                            protected void onPostExecute(String message) {
                                NavigationView navigationView= (NavigationView) getActivity().findViewById (R.id.nav_view);
                                View header = navigationView.getHeaderView(0);
                                TextView navuser = (TextView)   header.findViewById(R.id.navfirstname);
                                TextView navemail = (TextView)   header.findViewById(R.id.navemail);



                                ma.setFirstname(afn.getText().toString());
                                ma.setLastname(aln.getText().toString());
                                ma.setCellnumber(acell.getText().toString());
                                ma.setEmail(ae.getText().toString());
                                ma.setDob(cal);
                                navuser.setText(ma.getFirstname().toString());
                                navemail.setText(ma.getEmail().toString());

                                Class fragclass = null;
                                fragclass = Mainads.class;
                              ma.fragmentchange(fragclass,ma.fm);

                                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                                Snackbar.make(getView(),"Account Details Changed", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();


                            }
                        };
                        mytask.execute();



                    }
                });

                t.start();

            }
        });
        return view ;
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
    }
*/
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


    private abstract class accback extends AsyncTask<Void, Void, String> {



    }
}
