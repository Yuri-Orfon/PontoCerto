package br.com.devdiegopirutti.pontocertoapp.Login;

import androidx.lifecycle.MutableLiveData;

import br.com.devdiegopirutti.pontocertoapp.Model.Events;
import br.com.devdiegopirutti.pontocertoapp.Model.User;

public class LoginViewModel {

    public MutableLiveData<Events> event = new MutableLiveData();
    private LoginUseCase useCase = new LoginUseCase();

    public void doLogin(User user) {
        useCase.verifyLoginType(user)
                .addOnSuccessListener(authResult -> {
                    if ("adm@gmail.com".equals(authResult.getUser().getEmail()))
                        event.postValue(Events.SUCESSADMIN);
                    else event.postValue(Events.SUCESS);
                })
                .addOnFailureListener(e -> event.postValue(Events.FAILURE));
    }
}



