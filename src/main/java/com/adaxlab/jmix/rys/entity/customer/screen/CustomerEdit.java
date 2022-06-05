package com.adaxlab.jmix.rys.entity.customer.screen;

import io.jmix.ui.screen.*;
import com.adaxlab.jmix.rys.entity.Customer;

@UiController("rys_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
public class CustomerEdit extends StandardEditor<Customer> {
}