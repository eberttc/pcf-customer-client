package com.scotia.plato.training.customerapi.client;


import com.scotia.plato.training.customerapi.domain.Customer;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(name = "customerClient",url = "https://jsonplaceholder.typicode.com", fallback = CustomerClient.CustomerFallback.class)
public interface CustomerClient {

    @RequestMapping(method = GET, value="/users/{id}")
    Customer getCustomer(@PathVariable(name = "id") String id);

    @Component
    class CustomerFallback implements CustomerClient {

        @Override
        public Customer getCustomer(String id) {
            Customer c=new Customer();
            c.setName("Unavailable");
            return c;
        }
    }
}
