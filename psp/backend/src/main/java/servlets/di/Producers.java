package servlets.di;

import jakarta.enterprise.inject.Produces;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Context;


public class Producers {







    @Produces
    public Jsonb producesJsonb()
    {
        return JsonbBuilder.create();
    }



}
