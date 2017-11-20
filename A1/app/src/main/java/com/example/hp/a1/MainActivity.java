package com.example.hp.a1;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements List_Fragment.ListSelectionListener {

    public static String[] mListArray;
    public static String[] mWebpageArray;
    public static final int MY_PERMISSIONS_REQUEST=1;
    private static final String MY_PERMISSION = "com.example.hp.ACCESS_IMAGES";

    private final webViewFragment mwebViewFragment = new webViewFragment();
    private FragmentManager mFragmentManager;
    private FrameLayout mListFrameLayout, mWebPageFrameLayout;

    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);


        mListArray = getResources().getStringArray(R.array.LANDMARKS_LIST); // Reference to array of landmarks defined in string.xml
        mWebpageArray = getResources().getStringArray(R.array.WEB_URLS); // Reference to array of URLS defined in string.xml
        setContentView(R.layout.activity_main);

        //References to frame layout
        mListFrameLayout = (FrameLayout) findViewById(R.id.listfragment);
        mWebPageFrameLayout = (FrameLayout) findViewById(R.id.webpagefragment);

        // Get a reference to the FragmentManager
        mFragmentManager = getFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();

        fragmentTransaction.add(R.id.listfragment,
                new List_Fragment());
        fragmentTransaction.commit();

        // Add a OnBackStackChangedListener to reset the layout when the back stack changes
        mFragmentManager
                .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        setLayout();
                    }
                });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Exit: finishAndRemoveTask();
                            return true;
            case R.id.Launch:
                    //Check if application has the required permission
                if (ContextCompat.checkSelfPermission(MainActivity.this,MY_PERMISSION) !=PackageManager.PERMISSION_GRANTED)
                {
                    //Application does not have the permission so it requests user at run time for permission
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{MY_PERMISSION}, MY_PERMISSIONS_REQUEST);
                }
            else
                {
                    //Application already has the permission so broadcasts the intent
                    Intent intent = new Intent();
                    intent.setAction("View_Gallery");
                    sendBroadcast(intent);
                }


            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission granted by user at run time, launch A2
                    Intent intent = new Intent();
                    intent.setAction("View_Gallery");
                    sendBroadcast(intent);
                } else {

                    // permission denied by user at run time
                    break;
                }
                return;
            }
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        setLayout();

    }
    private void setLayout() {
        // Determine whether the WebViewFragment has been added
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            if (!mwebViewFragment.isAdded()) {

                // Make the List_Fragment occupy the entire layout
                mListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
                mWebPageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
            } else {

                // Make the webViewFragment occupy the entire layout
                mListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
                mWebPageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                        MATCH_PARENT));
            }

        } else {
            if (!mwebViewFragment.isAdded()) {

                // Make the List_Fragment occupy the entire layout
                mListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
                mWebPageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
            } else {

                // Make the List Layout take 1/3 of the layout's width
                mListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));

                // Make the  show webpage Layout take 2/3's of the layout's width
                mWebPageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 2f));
            }
        }
    }

        public void onListSelection(int index) {

        // If the webViewFragment has not been added, add it now
        if (!mwebViewFragment.isAdded()) {

            // Start a new FragmentTransaction
            FragmentTransaction fragmentTransaction = mFragmentManager
                    .beginTransaction();

            // Add the webViewFragment to the layout
            fragmentTransaction.add(R.id.webpagefragment,
                    mwebViewFragment);

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            mFragmentManager.executePendingTransactions();
        }

        if (mwebViewFragment.getShownIndex() != index) {

            // Tell the webView Fragment to load the URL at position index
            mwebViewFragment.showWebPageAtIndex(index);

        }
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onPause()");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onRestart()");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onResume()");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStop()");
        super.onStop();
    }

}


