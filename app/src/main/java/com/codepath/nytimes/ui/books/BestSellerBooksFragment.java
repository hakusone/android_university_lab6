package com.codepath.nytimes.ui.books;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.nytimes.networking.CallbackResponse;
import com.codepath.nytimes.networking.NYTimesApiClient;
import com.codepath.nytimes.R;
import com.codepath.nytimes.models.BestSellerBook;
import com.codepath.nytimes.ui.search.ArticleResultFragment;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class BestSellerBooksFragment extends Fragment implements OnListFragmentInteractionListener {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BestSellerBooksFragment() {
    }

    public static BestSellerBooksFragment newInstance() {
        BestSellerBooksFragment fragment = new BestSellerBooksFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_best_seller_books_list, container, false);
        ContentLoadingProgressBar progressBar = (ContentLoadingProgressBar) view.findViewById(R.id.progress);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);

        Context context = view.getContext();
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false));
        updateAdapter(progressBar, recyclerView);
        getActivity().setTitle(getString(R.string.action_bar_books));
        return view;
    }


    private void updateAdapter(final ContentLoadingProgressBar progressBar, final RecyclerView recyclerView) {
        progressBar.show();
        NYTimesApiClient nyTimesApiClient = new NYTimesApiClient();
        nyTimesApiClient.getBestSellersList(new CallbackResponse<List<BestSellerBook>>() {
            @Override
            public void onSuccess(List<BestSellerBook> models) {
                progressBar.hide();
                recyclerView.setAdapter(new BestSellerBooksRecyclerViewAdapter(models, BestSellerBooksFragment.this));
                Log.d("BestSellerBooksFragment", "response successful");
            }

            @Override
            public void onFailure(Throwable error) {
                progressBar.hide();
                Log.e("BestSellerBooksFragment", error.getMessage());
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("NYTimes Bestselling Books");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemClick(BestSellerBook item) {
    }
}
