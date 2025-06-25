package dev.jaidson.geestacar.util;

public class Calculate {
    public static double percentage(int spotUnoccupied, int totalSpot) {
        return (float) (spotUnoccupied * 100 / totalSpot);
    }
    public static double priceReduction(double baseprice, double percentage) {
        return baseprice-(baseprice * percentage);
    }
    public static double priceIncrease(double baseprice, double percentage) {
        return baseprice+(percentage * baseprice);
    }
    public static double priceExit(double percentagen, double baseprice) {
        if(percentagen <=25){
            return priceReduction(baseprice, 0.1);
        }
        if(percentagen <=50){
            return baseprice;
        }
        if(percentagen <=75){
            return priceIncrease(baseprice, 0.1);
        }
        if (percentagen <=100){
            return priceIncrease(baseprice, 0.25);
        }
        return baseprice;
    }
}
