package com.example.bicofacil.navbar.home;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bicofacil.R;

public class FragmentAvaliacao extends Fragment {

    private FragmentAvaliacaoViewModel mViewModel;

    public static FragmentAvaliacao newInstance() {
        return new FragmentAvaliacao();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_avaliacao, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FragmentAvaliacaoViewModel.class);
        // TODO: Use the ViewModel
    }

}