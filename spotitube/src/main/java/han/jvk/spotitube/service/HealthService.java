package han.jvk.spotitube.service;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HealthService implements IHealthService{

    @Override
    public String getHealth(){return "Healthy";}

}
