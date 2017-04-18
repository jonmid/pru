package com.jonmid.tallerasynctask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public static List<Post> parse(String content) throws Exception {
        JSONArray jsonArray = new JSONArray(content);
        List<Post> mysPost = new ArrayList<>();

        for (int i=0; i<jsonArray.length(); i++){
            JSONObject jsonItem = jsonArray.getJSONObject(i);

            Post post = new Post();
            post.setId(jsonItem.getInt("id"));
            post.setTitle(jsonItem.getString("title"));
            post.setBody(jsonItem.getString("body"));

            mysPost.add(post);
        }

        return mysPost;
    }

}
