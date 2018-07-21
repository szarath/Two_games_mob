package com.example.szara_000.team02_mobile;



import android.app.Fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;


import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;


import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static FloatingActionButton fab;

    public static boolean logcheck;

    public static void setLogcheck(boolean logcheck) {
        MainActivity.logcheck = logcheck;
    }

    public static boolean isLogcheck() {
        return logcheck;
    }

    public static String userid;
    public static String username;
    public static String firstname;
    public static String lastname;
    public static String email;
    public static String cellnumber;
    public static Boolean premium;
    public static Toolbar toolbar;
    private SearchView sv;
    public static String Searchitem;
    public static Boolean getPremium() {
        return premium;
    }

    public static void setPremium(Boolean premium) {
        MainActivity.premium = premium;
    }

    public static Calendar dob;
    public static  ProgressBar mProgress;
public static  FragmentManager fm;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        MainActivity.username = username;
    }

    public static String getFirstname() {
        return firstname;
    }

    public static void setFirstname(String firstname) {
        MainActivity.firstname = firstname;
    }

    public static String getLastname() {
        return lastname;
    }

    public static void setLastname(String lastname) {
        MainActivity.lastname = lastname;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        MainActivity.email = email;
    }

    public static String getCellnumber() {
        return cellnumber;
    }

    public static void setCellnumber(String cellnumber) {
        MainActivity.cellnumber = cellnumber;
    }

    public static Calendar getDob() {
        return dob;
    }

    public static void setDob(Calendar dob) {
        MainActivity.dob = dob;
    }

private boolean doubleBackToExitPressedOnce = false;

    public static String getUserid() {
        return userid;
    }

    public static void setUserid(String userid) {
        MainActivity.userid = userid;
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        setSupportActionBar(toolbar);
        this.setLogcheck(false);

         fm = getSupportFragmentManager();



        Class fragclass = null;
        fragclass = Mainads.class;
        fragmentchange(fragclass,fm);
        sv = (SearchView) findViewById(R.id.searchview);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                Searchitem = query;

                Class fragclass = null;
                fragclass = Searchad.class;
                fragmentchange(fragclass,fm);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });




        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Class fragclass = null;

                if( isLogcheck() == true){
                   fragclass = Postad.class;
                    fragmentchange(fragclass,fm);




                }
                else if (isLogcheck() == false) {
                    fragclass = Login.class;
                    fragmentchange(fragclass,fm);

                }

            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
          if (doubleBackToExitPressedOnce) {

              finish();
                return;
            }
            else{

              super.onBackPressed();
          }
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce=false;
                    }
                }, 2000);}



    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {




        int id = item.getItemId();
        android.support.v4.app.Fragment fragment = null;
        Class fragclass = null;
        if(id == R.id.nav_home)
        {
            fragclass = Mainads.class;
            fragmentchange(fragclass,fm);
            }
        else if (id == R.id.nav_login) {
           fragclass = Login.class;
            fragmentchange(fragclass,fm);



        } else if (id == R.id.my_ad) {
            if( isLogcheck() == true){
                fragclass = Myad.class;
                fragmentchange(fragclass,fm);

            }
            else if (isLogcheck() == false) {
                fragclass = Login.class;
                fragmentchange(fragclass,fm);



            }
            

        } else if (id == R.id.nav_account) {
            if( isLogcheck() == true) {
                fragclass = Account.class;
                fragmentchange(fragclass,fm);

            }
            else if(isLogcheck() == false)
            {
                fragclass = Login.class;
                fragmentchange(fragclass,fm);

            }
        } else if (id == R.id.nav_contact) {
            fragclass = Contact.class;
            fragmentchange(fragclass,fm);


        }
        else if (id == R.id.nav_register){

             fragclass = Register.class;
            fragmentchange(fragclass,fm);

         }
        else if(id == R.id.post_ad){

            if(isLogcheck() == true){
                fragclass = Postad.class;
                fragmentchange(fragclass,fm);

            }
            else if (isLogcheck() == false) {
                fragclass = Login.class;
                fragmentchange(fragclass,fm);

            }


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



   public static void fragmentchange(Class frag,FragmentManager fm)
   {
       android.support.v4.app.Fragment fragment = null;


       try{
           fragment = (android.support.v4.app.Fragment) frag.newInstance();
       }
       catch (Exception ex)
       {
           ex.printStackTrace();
       }

       fm.beginTransaction().replace(R.id.flContent,fragment).addToBackStack(fragment.getTag()).commit();


      
   }



}



