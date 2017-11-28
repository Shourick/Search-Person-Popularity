package ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.PlayersRepositories;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.IRepository.Players.IAdminRepository;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.Players.Admin;
import ru.geekbrains.traineeship.group2.search_person_popularity_mai.Repository.RESTful.IRestAPI.IAdminRestAPI;

import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.API_URL_BASE;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_ID;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.EMPTY_NAME;
import static ru.geekbrains.traineeship.group2.search_person_popularity_mai.Utils.Constants.UPDATE_OK;

/**
 * Created by skubatko on 13/11/17
 */

public class RESTfulAdminRepository implements IAdminRepository {

    private IAdminRestAPI mAdminRestAPI;

    public RESTfulAdminRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( API_URL_BASE )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        mAdminRestAPI = retrofit.create( IAdminRestAPI.class );
    }

    @Override
    public int addAdmin( Admin admin ) throws IOException {
        Response<Integer> response = mAdminRestAPI.addAdmin(
                admin.getLogin(),
                admin.getNickName(),
                admin.getPassword() )
                .execute();
        if ( response.isSuccessful() ) {
            return response.body();
        }
        return EMPTY_ID;
    }

    @Override
    public Admin getAdmin( int id ) throws IOException {
        Response<Admin> response = mAdminRestAPI.getAdminById( id ).execute();
        if ( response.isSuccessful() ) {
            return response.body();
        }
        return new Admin( EMPTY_ID, EMPTY_NAME, EMPTY_NAME, EMPTY_NAME );
    }

    @Override
    public List<Admin> getAllAdmins() throws IOException {
        return mAdminRestAPI.getAllAdmins().execute().body();
    }

    @Override
    public int getAdminsCount() throws IOException {
        Response<List<Admin>> response = mAdminRestAPI.getAllAdmins().execute();
        if ( response.isSuccessful() ) {
            return response.body().size();
        }
        return EMPTY_ID;
    }

    @Override
    public int updateAdmin( Admin admin ) throws IOException {
        mAdminRestAPI.updateAdmin( admin.getId(), admin.getNickName() ).execute();
        mAdminRestAPI.updateAdminPassword( admin.getId(), admin.getPassword() ).execute();
        return UPDATE_OK;
    }

    @Override
    public void deleteAdmin( Admin admin ) throws IOException {
        mAdminRestAPI.deleteAdmin( admin.getId() ).execute();
    }

    @Override
    public void deleteAllAdmins() throws IOException {
        mAdminRestAPI.deleteAllAdmins().execute();
    }
}
