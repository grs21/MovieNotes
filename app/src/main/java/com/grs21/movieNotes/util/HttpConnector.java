package com.grs21.movieNotes.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public final  class HttpConnector {
    private static final String TAG = "HttpConnector";

     private static HttpConnector instance;
     private RequestQueue requestQueue;
     private Context context;

     private HttpConnector (Context context){
         this.context=context;
         this.requestQueue=getRequestQueue();
     }

     public RequestQueue getRequestQueue(){
         //Todo:Imperative to applicationContext
         if (requestQueue==null){
             requestQueue= Volley.newRequestQueue(this.context.getApplicationContext());
         }
         return requestQueue;
     }

     public static HttpConnector getInstance(Context context){
         if (instance==null){
             instance=new HttpConnector(context);
         }
         return  instance;
     }

     //Todo:Generics addRequest
     public <T> void addRequestQue(Request<T> request){
         getRequestQueue().add(request);
     }

}
