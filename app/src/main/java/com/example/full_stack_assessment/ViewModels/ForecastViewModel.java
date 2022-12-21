package com.example.full_stack_assessment.ViewModels;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.full_stack_assessment.Data.Forecast.Period;
import com.example.full_stack_assessment.Data.Forecast.Weather;
import com.example.full_stack_assessment.Data.Grid.GridCall;
import com.example.full_stack_assessment.DataSource.WeatherApi;
import com.example.full_stack_assessment.ForecastActivity;
import com.example.full_stack_assessment.R;
import com.example.full_stack_assessment.Recycler.PeriodAdapter;
import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * The ForecastViewModel sets the UI state of the ForecastActivity
 * It asynchronously calls the National Weather API and returns a Json
 * A Gson builder converts the returning Json into objects found in the data directory
 */
public class ForecastViewModel extends ViewModel {

    private String BASE_URL = "https://api.weather.gov/";

    private final LatLng location = new LatLng(40.091135131249494, -88.24013532344047);

    //Variables used for the final weather GET request
    private String gridId = "";
    private int gridX;
    private int gridY;

    //Network status holder
    private MutableLiveData<APIStatus> _status = new MutableLiveData<>();
    public LiveData<APIStatus> status = _status;

    //Retrofit REST Network caller
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    //Initialize national Weather Api class
    private WeatherApi weatherApi = retrofit.create(WeatherApi.class);

    //using the activity context as a global variable for recycler view

    private ForecastActivity forecastActivity;
    //list of periods
    private List<Period> periodsList;

    //Api call on init of the viewModel
    public ForecastViewModel(ForecastActivity forecastActivity){
        this.forecastActivity = forecastActivity;
        makeGridApiCall();
    }

    public void makeGridApiCall(){
        _status.postValue(APIStatus.LOADING);

        try{
            getGridProperties();

        }catch(Exception e){
            Log.e("Forecast ViewModel Grid",e.toString());
            _status.postValue(APIStatus.ERROR);
        }
    }

    /**
     * Sends GET request to the weather api with a location point
     * Manipulates a GridCall type object
     */
    private void getGridProperties(){
        //The additional url
        String locationString = "points/"+ location.latitude + "," + location.longitude;

        //The GET call using Retrofit
        Call<GridCall> call = weatherApi.createGridProperties(locationString);

        //The asynchronous GET request
        call.enqueue(new Callback<GridCall>(){
            @Override
            public void onResponse(Call<GridCall> call, Response<GridCall> response) {

                //The first Api response will be put into a GridCall object
                GridCall gridCall = response.body();

                //Fill in the grid variables for the second weather api call
                if(gridCall != null) {
                    gridId = gridCall.getGridProperties().getGridID();
                    gridX = gridCall.getGridProperties().getGridX();
                    gridY = gridCall.getGridProperties().getGridY();
                }
                //Preform the final API call
                getWeatherProperties();
            }

            @Override
            public void onFailure(Call<GridCall> call, Throwable t) {
                Log.e("Forecast ViewModel Grid Call",t.toString());
                _status.postValue(APIStatus.ERROR);
                //display error on screen
                APIError();

            }
        });
    }

    /**
     * Sends GET request to the weather api with grid data
     * Manipulates a Weather type object
     */
    private void getWeatherProperties(){
        //ToDO: Write your own api call
        String forecastString = "gridpoints/LWX/" + gridX + "," + gridY + "/forecast";
        Call<Weather> call = weatherApi.createWeatherData(forecastString);

        //The asynchronous GET request
        call.enqueue(new Callback<Weather>(){
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                //API response for weather object
                Weather weather = response.body();

                //get the list of periods from weather object
                if(weather != null) {
                    periodsList = weather.getProperties().getPeriods();
                    //init for recyclerView
                    RecyclerView recyclerView = forecastActivity.findViewById(R.id.recycler_view);
                    PeriodAdapter pAdapter = new PeriodAdapter(periodsList, forecastActivity);
                    recyclerView.setAdapter(pAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(forecastActivity, LinearLayoutManager.VERTICAL, false));

                    recyclerView.addItemDecoration(new DividerItemDecoration(forecastActivity,
                            DividerItemDecoration.VERTICAL));

                    //set text view invisible
                    TextView error_text = (TextView) forecastActivity.findViewById(R.id.textView);
                    error_text.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e("Forecast ViewModel Grid Call",t.toString());
                _status.postValue(APIStatus.ERROR);
                APIError();
            }
        });

    }

    private void APIError(){
        //display error on screen
        RecyclerView recyclerView = forecastActivity.findViewById(R.id.recycler_view);
        recyclerView.setVisibility(View.GONE);
        TextView error_text = (TextView) forecastActivity.findViewById(R.id.textView);
        error_text.setText(R.string.error);
    }
}










