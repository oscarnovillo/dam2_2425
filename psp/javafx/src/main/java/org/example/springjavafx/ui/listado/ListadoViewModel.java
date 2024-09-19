package org.example.springjavafx.ui.listado;


import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
import java.util.List;

public class ListadoViewModel {


    private final ObjectProperty<ListadoState> _state;



    public ListadoViewModel() {


        ListadoState ls = null;
        List<String> cromos = new ArrayList<>();
        if (cromos == null)
            ls = new ListadoState(null, "no se han podido cargar cromos",false);
        else
            ls = new ListadoState(cromos, null,false);

        _state = new SimpleObjectProperty<>(new ListadoState(null, null,false));
    }

    public ReadOnlyObjectProperty<ListadoState> getState() {
        return _state;
    }

    public void loadCromos() {
        ListadoState ls = null;

    }


    public Either<String, List<String>> llamadaRetrofitAsyncEnUi() {
        return null;
    }

    public void llamadaRetrofitAsyncEnViewModel() {

        _state.setValue(new ListadoState(null, null, true));



    }


    public void llamadaRetrofit() {
        ListadoState ls = null;

    }

}
