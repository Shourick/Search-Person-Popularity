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

    private IAdminRestAPI adminRestAPI;

    public RESTfulAdminRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( API_URL_BASE )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        adminRestAPI = retrofit.create( IAdminRestAPI.class );
    }

    @Override
    public int addAdmin( Admin admin ) throws IOException {
        Response<Integer> response = adminRestAPI.addAdmin(
                admin.getNickName(),
                admin.getLogin(),
                admin.getPassword() )
                .execute();
        if ( response.isSuccessful() ) {
            return response.body();
        }
        return EMPTY_ID;
    }

    @Override
    public Admin getAdmin( int id ) throws IOException {
        Response<Admin> response = adminRestAPI.getAdminById( id ).execute();
        if ( response.isSuccessful() ) {
            return response.body();
        }
        return new Admin( EMPTY_ID, EMPTY_NAME, EMPTY_NAME, EMPTY_NAME );
    }

    @Override
    public List<Admin> getAllAdmins() throws IOException {
        return adminRestAPI.getAllAdmins().execute().body();
    }

    @Override
    public int getAdminsCount() throws IOException {
        Response<List<Admin>> response = adminRestAPI.getAllAdmins().execute();
        if ( response.isSuccessful() ) {
            return response.body().size();
        }
        return EMPTY_ID;
    }

    @Override
    public int updateAdmin( Admin admin ) throws IOException {
        adminRestAPI.updateAdmin( admin.getId(), admin.getNickName() ).execute();
        adminRestAPI.updateAdminPassword( admin.getId(), admin.getPassword() ).execute();
        return UPDATE_OK;
    }

    @Override
    public void deleteAdmin( Admin admin ) throws IOException {
        adminRestAPI.deleteAdmin( admin.getId() ).execute();
    }

    @Override
    public void deleteAllAdmins() throws IOException {
        adminRestAPI.deleteAllAdmins().execute();
    }
}
