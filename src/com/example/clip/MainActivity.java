package com.example.clip;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditText et = (EditText)findViewById(R.id.editText1);
                setStringForClipBoad(et.getText());
            }
        });
    }
    
    @SuppressLint("ServiceCast")
    public void setStringForClipBoad(CharSequence s){
        Object cm = (Object)getSystemService(CLIPBOARD_SERVICE);
        try {
            setClip(cm, s);
        } catch (Throwable e1) {
            e1.printStackTrace();
            ((ClipboardManager)cm).setText(s.toString());
        }
    }
    
    public void setClip(Object cm, CharSequence s){
           String[] mimeType = new String[1];
        mimeType[0] = "text/plain";
        Object item = getClipDataItemInstance(s);
        Object clipDescription = getClipDescriptionInstance("text_data", mimeType);
        Object clipData;
        try {
            clipData = getClipDataInstance(clipDescription, item);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Method method = null;
        try {
            method = (Class.forName("android.content.ClipboardManager").cast(cm)).getClass().getMethod("setPrimaryClip", new Class[] { Class.forName("android.content.ClipData")});
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            method.invoke(cm, new Object[] { clipData });
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    public Object getClipDataItemInstance(CharSequence s) {
        Class<?> clazz;
        try {
            clazz = Class.forName("android.content.ClipData$Item");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Class<?>[] types = { CharSequence.class };
        Constructor<CharSequence> constructor;
        try {
            constructor = (Constructor<CharSequence>)clazz.getConstructor(types);
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        Object[] args = { s };
        Object item;
        try {
            item = constructor.newInstance(args);
            return item;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Object getClipDataInstance(Object c, Object i) throws ClassNotFoundException {
        Class<?> clazz;
        try {
            clazz = Class.forName("android.content.ClipData");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Class<?>[] types = { Class.forName("android.content.ClipDescription"), Class.forName("android.content.ClipData$Item") };
        Constructor<?> constructor;
        try {
          constructor = (Constructor<?>)clazz.getConstructor(types);
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        Object[] args = { c, i };
        Object clipData;
        try {
            clipData = constructor.newInstance(args);
            return clipData;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    public Object getClipDescriptionInstance(CharSequence s, String[] ss) {
        Class<?> clazz;
        try {
            clazz = Class.forName("android.content.ClipDescription");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Class<?>[] types = { CharSequence.class, String[].class };
        Constructor<?> constructor;
        try {
            constructor = (Constructor<?>)clazz.getConstructor(types);
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        Object[] args = { s, ss };
        Object description;
        try {
            description = constructor.newInstance(args);
            return description;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
