package sample;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by SlUH on 21.09.2016.
 */
public class ComplexType implements Serializable{

    long num;
    String line;

    ComplexType(){
        this.num=new Random().nextLong();
        char[] mass=new char[5];
        boolean flag=true;
        int t=0;
        while(flag){
            int w = new Random().nextInt(127);
            if((w>=65 && w<=90) || (w>=97 && w<=122)) {
                if(t<5) {
                    mass[t] = (char) w;
                    t++;
                }
                else flag=false;
            }

        }
        this.line = new String(mass);
    }

    @Override
    public int hashCode() {
        String w = this.line + this.num;
        final int prime = 31;
        int result = 1;
        result = prime * result + w.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ComplexType other = (ComplexType) obj;
        if (num != other.num)
            return false;
        if (!line.equals(other.line))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return this.num + "" + this.line;
    }

    public static void main(String[] args) {
        HashMap<ComplexType, Double> hashMap= new HashMap<>();
        hashMap.put(new ComplexType(), new Random().nextDouble());
        for(Map.Entry<ComplexType,Double> e : hashMap.entrySet()){
            System.out.print(e.getKey());
        }
    }


}
