package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.PlayersRepositories;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Players.IUserRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.User;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.IRestAPI.IUserRestAPI;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.API_URL_BASE;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.UPDATE_OK;

/**
 * Created by skubatko on 13/11/17
 */

public class RESTfulUserRepository implements IUserRepository {

    private IUserRestAPI userRestAPI;

    public RESTfulUserRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( API_URL_BASE )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        userRestAPI = retrofit.create( IUserRestAPI.class );
    }

    @Override
    public int addUser( User user ) throws IOException {
        Response<Integer> response = userRestAPI.addUser(user.getLogin(), user.getNickName(),user.getPassword() ).execute();
        if ( response.isSuccessful() ) {
            return response.body();
        }
        return EMPTY_ID;
    }

    @Override
    public User getUser( int id ) throws IOException {
        Response<User> response = userRestAPI.getUserById( id ).execute();
        if ( response.isSuccessful() ) {
            return response.body();
        }
        return new User( EMPTY_ID, EMPTY_NAME, EMPTY_NAME, EMPTY_NAME );
    }

    @Override
    public List<User> getAllUsers() throws IOException {
        return userRestAPI.getAllUsers().execute().body();
    }

    @Override
    public int getUsersCount() throws IOException {
        Response<List<User>> response = userRestAPI.getAllUsers().execute();
        if ( response.isSuccessful() ) {
            return response.body().size();
        }
        return EMPTY_ID;
    }

    @Override
    public int updateUser( User user ) throws IOException {
        userRestAPI.updateUser( user.getId(), user.getNickName() ).execute();
        userRestAPI.updateUserPassword( user.getId(), user.getPassword() ).execute();
        return UPDATE_OK;
    }

    @Override
    public void deleteUser( User user ) throws IOException {
        userRestAPI.deleteUser( user.getId() ).execute();
    }

    @Override
    public void deleteAllUsers() throws IOException {
        userRestAPI.deleteAllUsers().execute();
    }
}
