package com.zyj.motion.sample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.zyj.motion.R;

/**
 * A fragment representing a list of Items.
 */
public class ItemFragment extends Fragment {

    private View mRootView;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 20;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
//        if (mRootView instanceof RecyclerView) {
//            Context context = mRootView.getContext();
//            RecyclerView recyclerView = (RecyclerView) mRootView;
//            recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS));
//        }
        WebView webView = mRootView.findViewById(R.id.id_event_dispatch_inner_scroll);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://www.baidu.com/");

        return mRootView;
    }

    public View getRootView() {
        return mRootView;
    }
}