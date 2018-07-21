package com.example.szara_000.team02_mobile;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Contact.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Contact#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Contact extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText name;
    private EditText email;
    private EditText description;
    private Spinner issue;
    private Button submit;
    private String mParam1;
    private String mParam2;
    private Json js;

    private ArrayList<String> list;
    private String url;
    private OnFragmentInteractionListener mListener;
    private static MainActivity ma;
    public Contact() {
        ma.fab.setVisibility(View.GONE);
        ma.mProgress.setVisibility(View.GONE);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Contact.
     */
    // TODO: Rename and change types and number of parameters
    public static Contact newInstance(String param1, String param2) {
        Contact fragment = new Contact();
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
        View view =inflater.inflate(R.layout.fragment_contact, container, false);
        name = (EditText) view.findViewById(R.id.contactanme);
        email = (EditText) view.findViewById(R.id.contactemail);
        description = (EditText) view.findViewById(R.id.contactdesc);
        issue = (Spinner) view.findViewById(R.id.contactspinner);
        submit = (Button) view.findViewById(R.id.contactsubmit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.getText().toString().length() == 0|| email.getText().toString().length() == 0 || description.getText().toString().length() == 0 || list.get(issue.getSelectedItemPosition()).toString().contains("Select an issue"))
                {
                    Snackbar.make(getView(), "Please fill in all details", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
                else
                {


              Thread t = new Thread(new Runnable() {
                  @Override
                  public void run() {
                     final String na = name.getText().toString();
                     final String em = email.getText().toString();
                      final String desc = description.getText().toString();
                    final  String is = list.get(issue.getSelectedItemPosition()).toString();
                    contactback mytask = new contactback() {
                        @Override
                        protected String doInBackground(Void... params) {
                            url = "AndroidAdCRUD.svc/Contact/" +em.toString()+"/" + na.toString()+"/"+is.toString()+"/"+desc.toString() ;
                            try {
                                js.getserverinfo(url);
                                return "Done";
                            }
                            catch (Exception ex)
                            {
                             ex.printStackTrace();

                            }


                            return null;
                        }

                        protected void onPostExecute(String message) {
                            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                            Snackbar.make(getView(),"Issue sent", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                    };

                    mytask.execute();

                  }
              });
                t.start();
                }
            }
        });

        list = new ArrayList<String>();

        ArrayAdapter<String> adapter;
        list.add("Select an issue");
        list.add("Ad Management");
        list.add("Premium Subscription");
        list.add("Account Management");
        list.add("Log in");
        list.add("Registration");
        list.add("Improvement Suggestions");
        list.add("Other");


        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, list);
        issue.setAdapter(adapter);




        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private abstract class contactback extends AsyncTask<Void, Void, String> {



    }




}
