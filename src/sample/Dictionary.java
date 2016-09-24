package sample;

import java.io.Serializable;
import java.util.*;

public class Dictionary implements Serializable{

    //массив ключей
    public ArrayList<ComplexType> key = new ArrayList<>();
    //массив значений
    public ArrayList<Double> value = new ArrayList<>();


    public Dictionary(){

    }


    public Dictionary(Dictionary p){
        this.key= new ArrayList<ComplexType>(p.key);
        this.value= new ArrayList<Double>(p.value);
    }

    //новый словарь
    public Dictionary newDict(Dictionary o){
        return new Dictionary(o);
    }

    //количество записей словаря
    public int size(){
        return key.size();
    }

    //получаем ключ по индексу
    public ComplexType getKey(int q){
        return key.get(q);
    }

    //получаем значение по индексу
    public double getValue(int q){
        return value.get(q);
    }

    //сортировка словаря
    public void sort(){
        //промежуточный массив для ключей
        ArrayList<ComplexType> cp = new ArrayList<>(key);
        //сортируем ключи
        Collections.sort(key, new Comparator<ComplexType>() {
            @Override
            public int compare(ComplexType complexType, ComplexType t1) {
                return complexType.toString().compareTo(t1.toString());
            }
        });

        //сохраняем в старых позициях новые позиции ключей
        ArrayList<Integer> ee = new ArrayList<Integer>();
        for (int i=0;i<key.size();i++){
            for (int j=0;j<key.size();j++) {
                if (cp.get(i).toString().equals(key.get(j).toString())) {
                    ee.add(j);
                }
            }
        }

        //сортируем значения по новым позициям ключей
        ArrayList<Double> ww = new ArrayList<>();
        for (int i=0;i<value.size();i++){
            for (int j=0;j<value.size();j++){
                if(ee.get(j)==i){
                    ww.add(value.get(j));
            }


            }
        }
        //добавляем сортированные значения
        value=ww;

    }

    //Добавление рандомных записей
    public void addRandom(int n){

        for (int i=0;i<n;i++) {
            key.add(new ComplexType());
            value.add(new Random().nextDouble());
            System.out.println("add "+ i);
        }
        System.out.println("added " + n);
    }

    //Удаление рандомных записей
    public void deleteRandom(int n){
            for (int i = n; i > 0; i--) {
                int u = new Random().nextInt(i);
                key.remove(u);
                value.remove(u);
                System.out.println("remove "+ u);


            }
            System.out.println("removed " + n);
        }


}
