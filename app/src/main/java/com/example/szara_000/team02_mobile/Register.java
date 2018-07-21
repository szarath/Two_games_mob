package com.example.szara_000.team02_mobile;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Register.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Register#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Register extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private Json js;
    private EditText regfname;
    private EditText reglname;
    private EditText regemail;
    private EditText regcemail;
    private EditText regdob;
    private EditText regphonenr;
    private EditText regusername;
    private EditText regpassword;
    private EditText regcpassword;
    private Button register;
    private static MainActivity ma;
    private Hash hash;
    private OnFragmentInteractionListener mListener;

    public Register() {
        ma.fab.setVisibility(View.GONE);
        ma.mProgress.setVisibility(View.GONE);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Register.
     */
    // TODO: Rename and change types and number of parameters
    public static Register newInstance(String param1, String param2) {
        Register fragment = new Register();
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        regfname = (EditText) view.findViewById(R.id.firstname);
        reglname = (EditText) view.findViewById(R.id.lastname);
        regemail = (EditText) view.findViewById(R.id.regemail);
        regcemail = (EditText) view.findViewById(R.id.regconfirmemail);
        regdob = (EditText) view.findViewById(R.id.Regdateofbirth);
        regphonenr = (EditText) view.findViewById(R.id.phonenumber);
        regusername  = (EditText) view.findViewById(R.id.username);
        regpassword  = (EditText) view.findViewById(R.id.regpassword);
        regcpassword  = (EditText) view.findViewById(R.id.regconfirmpassword);
        register = (Button) view.findViewById(R.id.register_button);

        regdob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth , int selectedday)

                    {   selectedmonth = selectedmonth + 1;
                        regdob.setText("" + selectedday + "/" + selectedmonth  + "/" + selectedyear);}},mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();  }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        attemptRegister();

                    }
                });

                t.start();
            }
        });
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a", Locale.ENGLISH);
            Date date = dateFormat.parse(regdob.toString());
            Calendar cal = dateFormat.getCalendar();
            cal.setTime(date);
            Log.d("cal", dateFormat.format(cal.getTime()).toString());
        }
        catch (Exception ex)
        {

        }
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

public void attemptRegister()
{

    js = new Json();
    try
    {

        if(regfname.getText().toString().length() == 0 || reglname.getText().toString().length() == 0 || regemail.getText().toString().length() == 0 ||regcemail.getText().toString().length() == 0 ||regdob.getText().toString().length() == 0 ||regphonenr.getText().toString().length() == 0 ||regusername.getText().toString().length() == 0 ||regpassword.getText().toString().length() == 0 ||regcpassword.getText().toString().length() == 0 )
        {
            Snackbar.make(getView(),"Please fill in all details", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else if( !regemail.getText().toString().contains(regcemail.getText().toString()) )
        {
            Snackbar.make(getView(),"Your emails don't match", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else if (!regpassword.getText().toString().contains(regcpassword.getText().toString()))
        {
            Snackbar.make(getView(),"Your Passwords don't match", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else if(regpassword.getText().length() < 8)
        {
            Snackbar.make(getView(),"Your Passwords has to be 8 or more characters long", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

            Date date = dateFormat.parse(regdob.getText().toString());
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);





            String url = "AndroidUserCRUD.svc/AndroidinsertUser/"+ regusername.getText().toString()+ "/"+  hash.Hash(regpassword.getText().toString())+"/"+regfname.getText().toString()+"/"+reglname.getText().toString()+"/"+regemail.getText().toString()+"/"+regphonenr.getText().toString()+"/"+ dateFormat2.format(date)+"/"+ dateFormat2.format(Calendar.getInstance().getTime()).toString();//+"/False/False/False";



            Class fragclass = null;
            fragclass = Mainads.class;
            ma.fragmentchange(fragclass,ma.fm);

           js.getserverinfo(url);
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            Snackbar.make(getView(),"You have registered successfully", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }


    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }

}



}
