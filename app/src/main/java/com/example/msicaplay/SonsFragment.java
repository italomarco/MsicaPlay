package com.example.msicaplay;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.example.msicaplay.MainActivity.arquivosMusicas;


public class SonsFragment extends Fragment {


    RecyclerView recyclerView;
    MusicAdapter musicAdapter;
    public SonsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_sons, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        if(!(arquivosMusicas.size() < 1))
        {

            musicAdapter = new MusicAdapter(getContext(), arquivosMusicas);
            recyclerView.setAdapter(musicAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext() ,RecyclerView.VERTICAL,
                    false));
        }



        return view;
    }
}