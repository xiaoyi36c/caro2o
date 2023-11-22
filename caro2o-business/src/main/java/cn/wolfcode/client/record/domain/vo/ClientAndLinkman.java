package cn.wolfcode.client.record.domain.vo;

import cn.wolfcode.client.contact.domain.CustomerLinkmane;
import cn.wolfcode.client.customer.domain.Customer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class ClientAndLinkman {
    private   List<CustomerLinkmane> clientLinkman = new ArrayList<CustomerLinkmane>();
    private   List<Customer> seeClient = new ArrayList<Customer>();
}
