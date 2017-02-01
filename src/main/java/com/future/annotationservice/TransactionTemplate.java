package com.future.annotationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.TransactionManager;

/**
 * Created by Administrator on 2017/2/1.
 */
@Service
public class TransactionTemplate {
    @Autowired
    TransactionManager transactionManager;
}
