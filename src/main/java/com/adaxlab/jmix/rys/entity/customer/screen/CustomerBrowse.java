package com.adaxlab.jmix.rys.entity.customer.screen;

import io.jmix.ui.screen.*;
import com.adaxlab.jmix.rys.entity.Customer;

@UiController("rys_Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
public class CustomerBrowse extends StandardLookup<Customer> {
}