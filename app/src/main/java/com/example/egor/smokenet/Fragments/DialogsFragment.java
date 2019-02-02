package com.example.egor.smokenet.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.egor.smokenet.Adapters.DialogListAdapter;
import com.example.egor.smokenet.Models.Client;
import com.example.egor.smokenet.Models.NetworkService;
import com.example.egor.smokenet.R;
import com.example.egor.smokenet.Requests.DialogsUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DialogsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DialogsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DialogsFragment extends Fragment {
    RecyclerView recyclerViewDialogsView;
    Context context;
    DialogListAdapter dialogAdapter;
    List<Client> clientList;
    public static final String TAG = DialogsFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<Client> list;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DialogsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DialogsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DialogsFragment newInstance(String param1, String param2) {
        DialogsFragment fragment = new DialogsFragment();
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
        View v = inflater.inflate(R.layout.fragment_dialogs, container, false);
        recyclerViewDialogsView = v.findViewById(R.id.list_dialogs);
        clientList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerViewDialogsView.setLayoutManager(linearLayoutManager);



        DialogsUser dialogsUser = NetworkService.getInstance().getRetrofit().create(DialogsUser.class);
        Call<List<Client>> call = dialogsUser.getUsersLogin();

        call.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                clientList.addAll(response.body());
                dialogAdapter = new DialogListAdapter(10, getContext(),clientList);
                recyclerViewDialogsView.setAdapter(dialogAdapter);

                recyclerViewDialogsView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {

            }
        });



        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

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
}
