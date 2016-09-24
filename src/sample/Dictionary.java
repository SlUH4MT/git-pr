package sample;

import java.io.Serializable;
import java.util.*;

/**
 * Created by SlUH on 21.09.2016.
 */
public class Dictionary implements Serializable{

    public ArrayList<ComplexType> key = new ArrayList<>();
    public ArrayList<Double> value = new ArrayList<>();

    public Dictionary(){

    }
    public Dictionary(Dictionary p){
        this.key= new ArrayList<ComplexType>(p.key);
        this.value= new ArrayList<Double>(p.value);
    }
    public Dictionary newDict(Dictionary o){
        return new Dictionary(o);
    }

    public int size(){
        return key.size();
    }

    public ComplexType getKey(int q){
        return key.get(q);
    }
    public double getValue(int q){
        return value.get(q);
    }

    public void sort(){
        ArrayList<ComplexType> cp = new ArrayList<>(key);
        Collections.sort(key, new Comparator<ComplexType>() {
            @Override
            public int compare(ComplexType complexType, ComplexType t1) {
                return complexType.toString().compareTo(t1.toString());
            }
        });
        ArrayList<Integer> ee = new ArrayList<Integer>();
        for (int i=0;i<key.size();i++){
            for (int j=0;j<key.size();j++) {
                if (cp.get(i).toString().equals(key.get(j).toString())) {
                    ee.add(j);
                }
            }
        }
        ArrayList<Double> ww = new ArrayList<>();
        for (int i=0;i<value.size();i++){
            for (int j=0;j<value.size();j++){
                if(ee.get(j)==i){
                    ww.add(value.get(j));
            }


            }
        }
        value=ww;

    }

    public void addRandom(int n){

        for (int i=0;i<n;i++) {
            key.add(new ComplexType());
            value.add(new Random().nextDouble());
            System.out.println("add "+ i);
        }
        System.out.println("added " + n);
    }
    public void deleteRandom(int n){
            for (int i = n; i > 0; i--) {
                int u = new Random().nextInt(i);
                key.remove(u);
                value.remove(u);
                System.out.println("remove "+ u);


            }
        System.out.println("removed " + n);
        }
    public void access(int n, String f){
        if(f.equals("add")) addRandom(n);
        deleteRandom(n);
    }
    public static void main(String[] args){
        Dictionary e = new Dictionary();
        e.addRandom(10);
        //e.deleteRandom(5);
        for(int i=0;i<e.key.size();i++){
            System.out.println(""+ e.key.get(i) + "  " + e.value.get(i));
        }
        e.sort();
        System.out.println();
        for(int i=0;i<e.key.size();i++){
            System.out.println(""+ e.key.get(i) + "  " + e.value.get(i));
        }
    }


}
