package org.example.springjavafx.ui.listado;


import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;


import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ListadoController  {


    private final ListadoViewModel viewModel;





    public ListadoController(ListadoViewModel viewModel) {
        this.viewModel = viewModel;


    }

    @FXML
    private void ver(ActionEvent actionEvent) {
        asyncConCompletableFuture();
//        //getPrincipalController().sacarAlertError("ahora con single");
        //      asynConSingle();

        //getPrincipalController().root.setCursor(Cursor.WAIT);
//        viewModel.llamadaRetrofitAsyncEnViewModel();

    }

    private void asynConSingle() {
        Single.fromCallable(viewModel::llamadaRetrofitAsyncEnUi)
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
//                .doFinally(() -> setCursor(Cursor.DEFAULT))
                .subscribe(result ->
                                result.peek(cromos -> {

                                        })
                                        .peekLeft(error -> {
//                                            sacarAlertError(error);
                                        }),
                        throwable -> {
//                            sacarAlertError(throwable.getMessage());

                        });


//        getPrincipalController().root.setCursor(Cursor.WAIT);
    }


    private void asyncConTask() {

//        getPrincipalController().root.setCursor(Cursor.WAIT);
        var task = new Task<Either<String, List<String>>>() {
            @Override
            protected Either<String, List<String>> call() throws Exception {
                return viewModel.llamadaRetrofitAsyncEnUi();
            }
        };
        task.setOnSucceeded(workerStateEvent -> {
            //workerStateEvent.getSource().valueProperty().get()
//            getPrincipalController().root.setCursor(Cursor.DEFAULT);
//            var result = task.getValue();
//            result.peek(cromos -> {
//
//
//
//            }).peekLeft(error -> {
//                sacarAlertError(error);
//            });
        });
        task.setOnFailed(workerStateEvent -> {
            //workerStateEvent.getSource().getException().getMessage()
//            getPrincipalController().sacarAlertError(task.getException().getMessage());
//            getPrincipalController().root.setCursor(Cursor.DEFAULT);
        });

        new Thread(task).start();
    }


    private void asyncConCompletableFuture() {

//        getPrincipalController().root.setCursor(Cursor.WAIT);

        CompletableFuture.supplyAsync(viewModel::llamadaRetrofitAsyncEnUi)
                .exceptionally(throwable -> Either.left(throwable.getMessage()))
                .thenAcceptAsync(result ->
                                //Platform.runLater(() -> {
                        {
//                            getPrincipalController().root.setCursor(Cursor.DEFAULT);
//                            result.peek(cromos -> {
//                                tabla.getItems().clear();
//                                tabla.getItems().addAll(cromos);
//
//
//                            }).peekLeft(error -> {
//                                getPrincipalController().sacarAlertError(error);
//                            });
                        }
                        //})
                );

//        var task = new Task<Either<String, List<Cromo>>>() {
//            @Override
//            protected Either<String, List<Cromo>> call() throws Exception {
//                return viewModel.llamadaRetrofitAsyncEnUi();
//            }
//        };
//        task.setOnSucceeded(workerStateEvent -> {
//            //workerStateEvent.getSource().valueProperty().get()
//            getPrincipalController().root.setCursor(Cursor.DEFAULT);
//            var result = task.getValue();
//            result.peek(cromos -> {
//                tabla.getItems().clear();
//                tabla.getItems().addAll(cromos);
//
//
//            }).peekLeft(error -> {
//                getPrincipalController().sacarAlertError(error);
//            });
//        });
//        task.setOnFailed(workerStateEvent -> {
//            //workerStateEvent.getSource().getException().getMessage()
//            getPrincipalController().sacarAlertError(task.getException().getMessage());
//            getPrincipalController().root.setCursor(Cursor.DEFAULT);
//        });
//
//        new Thread(task).start();
    }


    public void initialize() {
// tabla materialFX
//        MFXTableColumn<Cromo> collecionColumn = new MFXTableColumn<>("collecion", true, Comparator.comparing(Cromo::getCollecion));
//        MFXTableColumn<Cromo> descriptionColumn = new MFXTableColumn<>("descripcion", true, Comparator.comparing(Cromo::getDescripcion));
//        MFXTableColumn<Cromo> numberColumn = new MFXTableColumn<>("numero", true, Comparator.comparing(Cromo::getNumero));
//        collecionColumn.setRowCellFactory(persona -> new MFXTableRowCell<>(Cromo::getCollecion));
//        descriptionColumn.setRowCellFactory(persona -> new MFXTableRowCell<>(Cromo::getDescripcion));
//        numberColumn.setRowCellFactory(persona -> new MFXTableRowCell<>(Cromo::getNumero));

//        colCol.setCellValueFactory(new PropertyValueFactory<>("collecion"));
//        colDes.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
//        colNum.setCellValueFactory(new PropertyValueFactory<>("numero"));


//        tablaNormal.getSelectionModel().getSelectedItems().addListener((ListChangeListener<? super Cromo>) change -> {
//            txtNombre.setText("" + change.getList().get(0).getNumero());
//        });
//        tabla.getSelectionModel().selectionProperty().addListener((observableValue, cromo, cromoNew) -> {
//            if (cromoNew != null) {
//                txtNombre.setText(cromoNew.values().stream().findFirst().get().getCollecion());
//            }
//        });
//
//        tabla.getTableColumns().addAll(collecionColumn, descriptionColumn, numberColumn);

        cambiosEstado();

        // tabla.setItems(viewModel.getState().get().getPersonas());
    }


//    @Override
    public void principalCargado() {
        viewModel.loadCromos();
    }

    private void cambiosEstado() {
        viewModel.getState().addListener((observableValue, listadoState, listadoStateNew) -> {
            Platform.runLater(() -> {
                if (listadoStateNew.getError() != null) {
//                    getPrincipalController().sacarAlertError(listadoStateNew.getError());
                }
                if (listadoStateNew.getCromos() != null) {
//
//                    tabla.getItems().clear();
//                    tabla.getItems().addAll(listadoStateNew.getCromos());
//                    tablaNormal.getItems().clear();
//                    tablaNormal.getItems().addAll(listadoStateNew.getCromos());
                }

//                if (listadoStateNew.isCargando())
////                    getPrincipalController().root.setCursor(Cursor.WAIT);
//                else
//                    getPrincipalController().root.setCursor(Cursor.DEFAULT);
            });

        });
    }

    public void verNormal(ActionEvent actionEvent) {

//        if (tablaNormal.getSelectionModel().getSelectedItem() != null)
//            getPrincipalController().onSeleccionCromo(tablaNormal.getSelectionModel().getSelectedItem());


    }
}
