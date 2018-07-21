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
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Searchad.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Searchad#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Searchad extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Json js;
    private String[] premium;
    private static MainActivity ma;
    private JSONObject obj;
    public String[] name;
    public static int idnumber;
    private Handler handler;
    private JSONArray ja;
    private Mainads mainad;
    private customgrid adapter;
    public static Drawable[] d;
    public static int[] adid;
    public static int[] price;
    private OnFragmentInteractionListener mListener;
    private GridView grid;
    private int itemnumber;
    public static String[] platform;
    public static String[] locaiton;
    public Searchad() {
        ma.mProgress.setVisibility(View.VISIBLE);
        ma.fab.setVisibility(View.GONE);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Searchad.
     */
    // TODO: Rename and change types and number of parameters
    public static Searchad newInstance(String param1, String param2) {
        Searchad fragment = new Searchad();
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
        View view = inflater.inflate(R.layout.fragment_searchad, container, false);



        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                final String url = "AndroidAdCRUD.svc/AndroidsearchAds/" + ma.Searchitem.toString();

                try {

                    searchback mTask = new searchback() {
                        @Override
                        protected String doInBackground(Void... params) {
                            try {

                                obj = js.getserverinfo(url);
                                ja = obj.getJSONArray("AndroidsearchAdsResult");


                                return "Done";
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            return null;
                        }

                        protected void onPostExecute(String message) {
                            ma.mProgress.setVisibility(View.GONE);
                            ma.fab.setVisibility(View.VISIBLE);



                        if (ja != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        name = new String[ja.length()];
                                        d = new Drawable[ja.length()];
                                        adid = new int[ja.length()];
                                        price = new int[ja.length()];
                                        platform = new String[ja.length()];
                                        locaiton = new String[ja.length()];
                                        premium = new String[ja.length()];
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
                                                premium[i] = items.get(13).toString();
                                                if (items.get(3).toString().length() > 10) {
                                                    byte[] bpic = Base64.decode(items.get(3).getBytes(), Base64.DEFAULT);

                                                    Bitmap decocde = BitmapFactory.decodeByteArray(bpic, 0, bpic.length);
                                                    d[i] = new BitmapDrawable(getResources(), decocde);
                                                } else if (items.get(3).contains("NOPIC") || items.get(3).contains("")) {
                                                    d[i] = getResources().getDrawable(R.drawable.noimage);

                                                }
                                                price[i] = Integer.parseInt(items.get(11).toString());
                                            }
                                            adapter = new customgrid(getView().getContext(), name, d, price,platform,locaiton,premium);
                                            grid = (GridView) getView().findViewById(R.id.grid_search);
                                            grid.setAdapter(adapter);
                                            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view,
                                                                        int position, long id) {
                                                    Toast.makeText(view.getContext(), "You Clicked at " + name[position], Toast.LENGTH_SHORT).show();
                                                    itemnumber = position;
                                                    mainad.idnumber = adid[position];

                                                    Class fragclass = null;
                                                    fragclass = Specificad.class;
                                                    ma.fragmentchange(fragclass,ma.fm);

                                                }
                                            });

                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                });
                            }
                            else
                        {
                            Snackbar.make(getView(),"No Ads to show", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                        }
                    };
                    mTask.execute();
                }
                catch (Exception ex)
                {ex.printStackTrace();}



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
    private abstract class searchback extends AsyncTask<Void, Void, String> {



    }
}
