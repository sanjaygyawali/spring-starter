package com.rasello.auth.seeders;

import com.rasello.auth.core.enums.Environment;
import com.rasello.auth.repository.SeederRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public abstract class BaseSeeder extends MetaSeeder  {

    public BaseSeeder(SeederRepository repository) {
        super(repository);
    }

    abstract String getEntityName();

     public List<? extends BaseSeeder> getDependencies(){
        List<? extends BaseSeeder> list = new ArrayList<>();
        return list;
    }

    public void run() {
//        TODO getenvironment from application properties here.
        Environment currentEnv = Environment.Development;
        this.runDependencies();
        String entityName = this.getEntityName();
        if(currentEnv == Environment.Development){
            if(this.isSeedNeeded(Environment.Production, entityName)) {
                this.production();
                this.updateSeedEnvironment(Environment.Production, entityName);
            }
            if(this.isSeedNeeded(Environment.Development, entityName)) {
                this.development();
                this.updateSeedEnvironment(Environment.Development, entityName);
            }
        }else if(currentEnv == Environment.Staging){
            if(this.isSeedNeeded(Environment.Production, entityName)) {
                this.production();
                this.updateSeedEnvironment(Environment.Production, entityName);
            }
            if(this.isSeedNeeded(Environment.Staging, entityName)) {
                this.staging();
                this.updateSeedEnvironment(Environment.Staging, entityName);
            }
        }else if(currentEnv == Environment.Production){
            if(this.isSeedNeeded(Environment.Production, entityName)){
                this.production();
                this.updateSeedEnvironment(Environment.Production, entityName);
            }
        }
    }

    private void  runDependencies(){
        for( BaseSeeder dependencies: this.getDependencies()){
            dependencies.run();
        }
    }

    abstract void development();
    abstract void staging();
    abstract void production();
}
