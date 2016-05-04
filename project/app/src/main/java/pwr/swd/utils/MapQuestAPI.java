package pwr.swd.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import pwr.swd.model.MapQuestResponse;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Patryk on 26.04.2016.
 */
public interface MapQuestAPI {

    @POST("/routematrix?key=" + Consts.MAP_QUEST_KEY)
    public void post(@Body String requestBody, Callback<MapQuestResponse> response) throws JSONException;


}