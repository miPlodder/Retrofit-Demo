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

#### Retrofit Annotations

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

**(9) @FormUrlEncoded** <br>
**(10) @Field** <br>
**(11) @Multipart** <br>
**(12) @Part** <br>
