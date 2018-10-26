package cn.kuwo.teampay.presenter;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by cdm on 2018/10/25.
 */

public class CouponPresenter {
    public static void saveCouponList(Context context, ArrayList<AbstractCouponComputer> coupons) {
        try {
            File file = new File(context.getFilesDir(), "obj.tem");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
            objectOutputStream.writeObject(coupons);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<AbstractCouponComputer> getCouponList(Context context) {
        try {
            File file = new File(context.getFilesDir(), "obj.tem");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream couponsInputStream = new ObjectInputStream(fis);
            return (ArrayList<AbstractCouponComputer>) couponsInputStream.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
