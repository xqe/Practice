package com.example.xieqe.test001.salary;

/**
 * Created by xqe on 2018/5/24.
 */

public class AddEmployeeTransaction implements Transaction {
    private String name;
    private String address;
    private PayClassification payClassification;
    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void execute() {
        //创建新的Employee对象，将payClassification对象拷贝到Employee中
    }

    public void addEmployee(String name,String address,PayClassification payClassification){
        this.address = address;
        this.name = name;
        this.payClassification = payClassification;
    }
}
