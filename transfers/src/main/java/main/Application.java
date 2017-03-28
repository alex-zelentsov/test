package main;

import com.google.inject.Module;
import endpoint.AccountEndpoint;
import io.bootique.Bootique;
import io.bootique.jersey.JerseyModule;
import mapper.JacksonMapper;
import mapper.JacksonMapperImpl;
import org.apache.cayenne.configuration.server.ServerRuntime;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.lock.LockService;
import service.lock.LockServiceImpl;
import storage.AccountStorage;
import storage.AccountStorageImpl;

/**
 * Created by alexander.zelentsov on 23.03.2017.
 */

public class Application {

    public static void main(String[] args) {
        Module module = getModule();

        Bootique.app(args).module(module).autoLoadModules().run();
    }

    private static Module getModule() {
        return binder -> {
                JerseyModule.contributeResources(binder).addBinding().to(AccountEndpoint.class);
                binder.bind(JacksonMapper.class).to(JacksonMapperImpl.class);
                binder.bind(AccountService.class).to(AccountServiceImpl.class);
                binder.bind(LockService.class).to(LockServiceImpl.class);
                binder.bind(AccountStorage.class).to(AccountStorageImpl.class);
                binder.bind(ServerRuntime.class).toInstance(createServerRuntime());
            };
    }

    private static ServerRuntime createServerRuntime() {
        return ServerRuntime.builder()
                .addConfig("cayenne-transfers.xml")
                .build();
    }
}
