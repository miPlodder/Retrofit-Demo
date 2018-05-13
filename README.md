# About 
This repo contains demo of how to use Retrofit with RxJava and OkHttp and is highly documented for better user understanding.

## Retrofit Guide

Retrofit turns HTTP API requests in Java Interfaces. Retrofit works basically in 3 steps,

**Step 1) Create an Service INTERFACE**
```
public interface DemoService {
  @GET("users/{user}/repos")
  Call<List<ReposModel>> getRepos(@Path("user") String user);
}
```

**Step 2) Initialising Retrofit**
```
Retrofit retrofit = new Retrofit.Builder().baseUser("http://api.github.com").build();
```

**Step 3) Making API Request**
```
//Retrofit makes an implementation of the `DemoService Class`
DemoService demoService = retrofit.create(DemoService.class);
Call<List<RepoModel>> repos = demoService.getRepos("miPlodder");
```

### Retrofit Annotations

An annotation describes how a request will be handled. <br><br>
**(1) @GET** - Used for a Get request.

**(2) @POST** - Used for a Post request.

**(3) @PUT** - Used for a Put request.

**(4) @DELETE** - Used for a Delete request.

**(5) @Path** - Used for method argument and this value will be replace the value inside {} in URL.
```
@GET("users/{id}")
Call<UserModel> getUser(@Path("id") int userId);
```
Result - If id = 1, then URL = www.api.sitename/users/1

**(6) @Query** - Used to add query to the URL
```
@GET("users/{id}")
Call<UserModel> getUser(@Path("id") int userId, @Query("authToken") String authToken);
```
Result - If id=1, authToken=xyz, then URL = www.api.sitename/users/1?authToken=xyz

**(7) @QueryMap** - Used when we have complex Query
```
@GET("users/{id}")
Call<UserModel> getUser(@Path("id") int userId, @QueryMap Map<String, String> options);
```

**(8) @Body** - An object can be specified for use as HTTP request body with the help of @Body Annotation.<br>

**(9) @FormUrlEncoded** - This annotation is used to send data to an API and can not be used with GET. Data is send in the request body, not in the URL Parameters. (like in @Query)<br> 
**(10) @Field** - This adds an individual data fields to the request body and used in conjunction with @FormUrlEncoded Annotation. <br>
```
@FormUrlEncoded
@POST("login")
Call<ResponseBody> login(@Field("first_name") String name, @Field("last_name") String lname)
```
**(11) @FieldMap** - This adds a dictionary/map to the request body and is used in conjunction with @FormUrlEncoded Annotation. <br>
```
@FormUrlEncoded
@POST("login")
Call<ResponseBody> login(@FieldMap Map<String, String> map)
```
**(12) @Multipart** - Used to upload files, i.e. Audio, Video, Photo, etc to the server. It's every parameter should be annotated with @Part Annotation. 
```
@Multipart
@PUT("user/profile")
Call<ResponseBody> updateUserProfile(@Part("photo") MultipartBody.Part photo, @Part("description") RequestBody description);
```
**(13) @Part** - This is used with @Multipart Annotated methods parameter;

## RxJava 2 Guide

It basically consists of 3 things, Observable (emits data), Observer (consumes data), and Subscriber (connects the Observer and Observable). 

### Data Emission 

**(1) Observable** - It emits 0 to n items and terminates with complete or error. It has no backpressure. <br>
**(2) Flowable** - Similar to Observable, but has backpressure. <br>
**(3) Single** - It emits only 1 item and terminates with complete or error. <br>

### Consuming Emitted Data 

**(1) DisposableObserver** - This is similar to Observer (in Rx Java 1) that's it consumes the data emitted by the Observable. <br>
**(2) Observer** - It's similar to Observer (in Rx Java 1), but has 1 newer method, `onSubscribe()`.<br>

### Connecting Observer and Observable

**(1) Disposable** - This is similar to Subscription (in Rx Java 1) that's it connects Observable and Observer together. <br>
**(2) CompositeDisposable** - This is similar to CompositeSubscription (in Rx Java 1) that's it holds multiple Subscription/Disposable together in single object. <br>

### Rx Additional Features

**(1) Subject** - It's special object that acts as both Observable and an Observer.<br>
**(2) map()** - It's function of Observable that takes one value and outputs another value.<br>
**(3) debounce()** - It's function that triggers after some amount of time.<br>

### Example
```
compositeDisposable.add(observable
                            //.io() or .newThread() any can be used
                            .subscribeOn(Schedulers.newThread())
                            //observing on the main thread
                            .observeOn(AndroidSchedulers.mainThread())
                            //newer method added
                            .subscribeWith(new DisposableObserver<MyPojo>() {
                                @Override
                                public void onNext(MyPojo o) {}
                                @Override
                                public void onError(Throwable e) {}
                                @Override
                                public void onComplete() {}
                            }));
```

## OkHttp Guide

It offers a request/response API with client to make Network Operation easier. We have two ways to add request to the OkHttpClient, firstly by using enqueue (which runs on Worker Thread) and execute (which works on the current Thread).

### Example 
```
OkHttpClient client = new OkHttpClient();
Request request = new Request.Builder().url("www.google.com").build();

client.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, final IOException e) {
          runOnUiThread(new Runnable() {
          @Override
          public void run() {}
            });
          }

      @Override
      public void onResponse(Call call, final Response response) throws IOException {
             runOnUiThread(new Runnable() {
             @Override
             public void run() {}
                });
            }
        });
```

## Using RxJava, Retrofit, OkHttp in conjunction

We can enhance Retrofit's functionality by using RxJava and OkHttp. Below is a well-documented example for the same.
```
retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    /*
                        adding ConverterFactory helps in serialising and de-serialising the json
                        for example there are many libraries for this purpose like Gson, Moshi
                     */
                    .addConverterFactory(GsonConverterFactory.create())
                    /*  adding AdapterFactory helps us in adding support for rx
                        otherwise, we would have to use Call return type to work, instead of Observable
                    */
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    /*  adding client helps us to customize HTTP message
                        For example, adding query to every URL request like Auth Token
                        We can do this using OkHttpClient, instead of adding argument to every retrofit request
                    */
                    .client(new OkHttpClient())
                    .build();

```

## Contributor

[Saksham Handu](https://github.com/miPlodder)
