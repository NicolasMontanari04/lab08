package it.unibo.deathnote.impl;

import it.unibo.deathnote.api.DeathNote;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DeathNoteImplementation implements DeathNote{
    private final long FOURTY_MILLISECOND = 40;
    private final long SIX_SECONDS = 6000 + FOURTY_MILLISECOND;
    private String lastName;
    
    private final List<Death> deaths;

    public DeathNoteImplementation(){
        deaths = new ArrayList<>();
    }

    @Override
    public String getDeathCause(String name) {
        if(isNameWritten(name) == false){
            throw new IllegalStateException("Name not written");
        }
        for(Death death : deaths){
            if(Objects.equals(death.getName(), name)){
                if(death.getCause() == null){
                    return "heart attack";
                }else {
                    return death.getCause();
                }
            }
        }
        return null;
    }

    @Override
    public String getDeathDetails(String name) {
        if(isNameWritten(name) == false){
            throw new IllegalStateException("Name not written");
        }
        for(Death death : deaths){
            if(Objects.equals(death.getName(), name)){
                if(death.getDetails() == null){
                    return "No details provided";
                } else {
                    return death.getDetails();
                }
            }
        }
        return null;
    }

    @Override
    public String getRule(int ruleNumber) {
        if( ruleNumber <= 0 || ruleNumber > RULES.size()){
            throw new IllegalArgumentException("Number zero or smaller than zero");
        }else{
            return RULES.get(ruleNumber - 1);
        }
    }

    @Override
    public boolean isNameWritten(String name) {
        for(Death death : deaths){
            if(death.getName().equals(name)){
              return true;  
            }
        }
        return false;
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if( cause == null || deaths.isEmpty() ){
            throw new IllegalStateException("cause is null");
        }
        for(Death death : deaths){
            if( death.getName().equals(lastName)){
                
                if((System.currentTimeMillis() - death.getTimeName()) < FOURTY_MILLISECOND){
                    death.setCause(cause);
                    return true;
                }else{
                    return false;
                }
            }
        }
        
        return false;
    }


    @Override
public boolean writeDetails(String details) {
    if (details == null || deaths.isEmpty()) {
        throw new IllegalStateException("Details is null");
    }
    Death lastDeath = deaths.get(deaths.size() - 1); 
    if (System.currentTimeMillis() - lastDeath.getTimeCause() < SIX_SECONDS) {
        lastDeath.setDetails(details);
        return true;
    } else {
        return false;
    }
}



    @Override
    public void writeName(String name) {
        if( name == null ){
            throw new NullPointerException("Name is null");
        } else{
            Death newN = new Death(name, "", "");
            deaths.add(newN);
            newN.setTimeName(System.currentTimeMillis());
            newN.setCause("");
            lastName = name;
        }
    }

    

    private final class Death {
        private String cause;
        private String details;
        private String name;
        private long timeCause;
        
        private long timeName;

        public Death(String name, String cause, String details) {
            this.name = name;
            this.cause = cause;
            this.details = details;
        }

        public String getCause() {
            return cause;
        }
        
        public String getDetails() {
            return details;
        }

        public Long getTimeName() {
            return timeName;
        }

        public Long getTimeCause(){
            return timeCause;
        }

        public void setTimeName(long timeN) {
            this.timeName = timeN;
        }

        public void setCause(String cause) {
            this.cause = cause;
            this.timeCause = System.currentTimeMillis();
            lastName = name;
        }
        
        public void setDetails(String details) {
            this.details = details;
        }

        public String getName(){
            return name;
        }

    }
    
}