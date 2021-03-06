package sample;

import javafx.animation.AnimationTimer;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Controller {

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    @FXML
    private Label timeLabel;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private TextField insertField;

    //определяем словарь
    Dictionary mp = new Dictionary();

    //назначаем исполнителя
    ExecutorService es = Executors.newCachedThreadPool();

    //устанавливаем доступ одного потока
    Semaphore sm = new Semaphore(1);

    //обновление текущего времени
    protected AnimationTimer at = new AnimationTimer() {
        @Override
        public void handle(long now) {
            longProcess2();
        }
    };
    @FXML
    public void initialize(){

        insertField.setTooltip(new Tooltip("Insert value"));

        //запускаем на исполнение показ текущего времени
        es.submit(new Runnable() {
            @Override
            public void run() {
                at.start();
            }
        });

    }

    //метод события кнопки addButton
    public void actionAddButton(ActionEvent actionEvent) {
        //Число с текстового поля
        int q = Integer.parseInt(insertField.getText());
        Dictionary dictionary = new Dictionary(mp);
        //Выполнение добавления записей в фоновом потоке
        final Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                dictionary.addRandom(q);
                mp = new Dictionary(dictionary);

                return null;
            }
        };
        //Выполнение сериализации записей в фоновом потоке
        final Task task1 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                //доступ для одного потока
                sm.acquire();


                int max = dictionary.size();

                //сортируем словарь
                dictionary.sort();


                FileOutputStream fos = new FileOutputStream("temp.out");
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                Thread.sleep(10000);


                for (int i = 0; i < max; i++) {
                    if (isCancelled()) {
                        break;
                    }
                    oos.writeObject(dictionary.getKey(i));
                    //oos.flush();
                    oos.writeObject(dictionary.getValue(i));
                    //oos.flush();
                    updateProgress(i+1, max);
                    Thread.sleep(100);

                }
                System.out.println("writed from add method " + max);

                oos.close();

                sm.release();

                return null;

            }

            @Override
            protected void updateProgress(long workDone, long max) {

                progressBar.setProgress((double) workDone / (double) max);
            }


        };

        //запуск добавления
        es.submit(task);

        //es.submit(task1);

        //запуск сериализации
        Service service=new Service<Void>() { @Override
        protected Task createTask() { return task1;
        }};
        service.start();

    }

    //метод события кнопки removeButton
    public void actionRemoveButton(ActionEvent actionEvent) {
        //Считываем число с текстового поля
        int q = Integer.parseInt(insertField.getText());

        //создаем копию словаря
        Dictionary dictionary = new Dictionary(mp);

        //процесс удаления записей
        final Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                dictionary.deleteRandom(q);
                mp = new Dictionary(dictionary);

                return null;
            }
        };

        //процесс сериализации
        final Task task1 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                sm.acquire();

                int max = dictionary.size();

                //сортируем словарь
                dictionary.sort();

                FileOutputStream fos = new FileOutputStream("temp.out");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                Thread.sleep(10000);

                for (int i = 0; i < max; i++) {
                    if (max == 0) break;
                    if (isCancelled()) {
                        break;
                    }
                    oos.writeObject(dictionary.getKey(i));
                    //oos.flush();
                    oos.writeObject(dictionary.getValue(i));
                    //oos.flush();
                    updateProgress(i+1, max);
                    Thread.sleep(100);

                }

                System.out.println("writed from remove method " + max);

                oos.close();
                sm.release();

                return null;

            }

            @Override
            protected void updateProgress(long workDone, long max) {

                progressBar.setProgress((double) workDone/ (double) max);
            }


        };

        //запуск удаления
        es.submit(task);

        //запуск сериализации
        Service service = new Service<Void>() {
            @Override
            protected Task createTask() {
                return task1;
            }
        };
        service.start();

    }

    //метод обновления текущего времени
    private void longProcess2() {

        Locale local = new Locale("ru", "RU");
        DateFormat df = df = DateFormat.getTimeInstance(DateFormat.DEFAULT, local);
        Date currentDate = new Date();
        df.format(currentDate);
        timeLabel.setText(df.format(currentDate));

    }
}
