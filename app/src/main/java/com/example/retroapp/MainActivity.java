package com.example.retroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResults;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResults = findViewById(R.id.text_view_result);
        Gson gson = new GsonBuilder().serializeNulls().create();

        /*
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

         */

        /** Added Interceptor Header for all methods using loggingInterceptor **/

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient()
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                        Request originalRequest = chain.request();

                        Request newRequest = originalRequest.newBuilder()
                                .header("Interceptor-Header", "Superb")
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        //getPosts();
        //getComments();
        //createPost();
        //updatePost();
        //deletePost();
        getTodo();
    }

    private void deletePost() {
        Call<Void>  call = jsonPlaceHolderApi.deletePost(13);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textViewResults.setText("Code: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textViewResults.setText(t.getMessage());
            }
        });
    }

    /*
    private void updatePost() {
        Post post = new Post(13, "Midfielder", "Bruno Fernandes");
        Call<Post> call = jsonPlaceHolderApi.pathPost(5, post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    textViewResults.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID:  " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";
                textViewResults.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResults.setText(t.getMessage());
            }
        });
    }
     */

    /*
    private void updatePost() {
        Post post = new Post(13, "Midfielder", "Bruno Fernandes");
        Call<Post> call = jsonPlaceHolderApi.putPost("header",13, post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    textViewResults.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID:  " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";
                textViewResults.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResults.setText(t.getMessage());
            }
        });
    }
    */

    /** Added HeaderMap **/
    private void updatePost() {
        Post post = new Post(13, "Midfielder", "Bruno Fernandes");

        Map<String, String> headers = new HashMap<>();
        headers.put("Map-Header1", "def");
        headers.put("Map-Header2", "ghi");

        Call<Post> call = jsonPlaceHolderApi.pathPost(headers,13, post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    textViewResults.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID:  " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";
                textViewResults.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResults.setText(t.getMessage());
            }
        });
    }

    /** Method for this type of @POST call request
        Call<Post> createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text

            for @CREATEPOST METHOD
                                    **/

/*
    private void createPost() {
        Post post = new Post(07, "Football Idol", "Cristiano Ronaldo");

        Call<Post> call = jsonPlaceHolderApi.createPost(07, Football Idol, Cristiano Ronaldo);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    textViewResults.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID:  " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";
                textViewResults.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResults.setText(t.getMessage());

            }
        });
    }
   */


    private void createPost() {
        Post post = new Post(07, "Football Idol", "Cristiano Ronaldo");
        Map<String, String> fields = new HashMap<>();
        fields.put("userId", "07");
        fields.put("title", "Football Idol");
        fields.put("text", "Cristiano Ronaldo");

        Call<Post> call = jsonPlaceHolderApi.createPost(fields);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textViewResults.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID:  " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";
                textViewResults.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResults.setText(t.getMessage());

            }
        });
    }

    private void getComments() {
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments("posts/3/comments");
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    textViewResults.setText("Code: " + response.code());
                    return;
                }
                List<Comment> comments = response.body();

                for (Comment comment : comments) {
                    String content = "";
                    content += "ID:  " + comment.getId() + "\n";
                    content += "Post ID: " + comment.getPostId() + "\n";
                    content += "Name: " + comment.getName() + "\n";
                    content += "Email: " + comment.getEmail() + "\n";
                    content += "Text: " + comment.getText() + "\n\n";
                    textViewResults.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t){
                textViewResults.setText(t.getMessage());
            }

        });
    }

    private void getPosts() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");

        Call<List<Post>> call = jsonPlaceHolderApi.getPost(parameters);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textViewResults.setText("Code: " + response.code());
                    return;
                }

                List<Post> posts = response.body();

                for (Post post : posts) {
                    String content = "";
                    content += "ID " + post.getId() + "\n";
                    content += "User ID " + post.getId() + "\n";
                    content += "Title " + post.getId() + "\n";
                    content += "Body " + post.getId() + "\n\n";

                    textViewResults.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResults.setText(t.getMessage());
            }
        });
    }

    private void getTodo(){
        Call<List<Todo>> call = jsonPlaceHolderApi.getTodo("todos");
        call.enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                if (!response.isSuccessful()) {
                    textViewResults.setText("Code: " + response.code());
                    return;
                }

                List <Todo> todos = response.body();

                for (Todo todo : todos) {
                    String content = "";
                    content += "ID " + todo.getId() + "\n";
                    content += "User ID " + todo.getUserId() + "\n";
                    content += "Title " + todo.getTitle() + "\n";
                    content += "Status " + todo.getStatus()+ "\n\n";

                    textViewResults.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable t) {
                textViewResults.setText(t.getMessage());
            }
        });
    }
}