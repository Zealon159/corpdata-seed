package com.corpdata.app.test.service.impl;

import com.corpdata.app.test.entity.TbCar;
import com.corpdata.app.test.exception.NotSufficientFundsException;
import com.corpdata.app.test.service.TbCarService;
import com.corpdata.core.datasource.DataSourceEnum;
import com.corpdata.core.datasource.aop.DynamicSwitchDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.corpdata.core.base.AbstractBaseService;
import com.corpdata.app.test.dao.TbAccountMapper;
import com.corpdata.app.test.entity.TbAccount;
import com.corpdata.app.test.service.TbAccountService;

/**
 * 
 * 
 * @author zealon
 * @email zealon@126.com
 * @date 2018-07-23 11:28:57
 */
@Service
public class TbAccountServiceImpl extends AbstractBaseService<TbAccount> implements TbAccountService {

    @Autowired
    private TbCarService tbCarService;

    @Autowired
    private TbAccountService tbAccountService;

    @Transactional
    @Override
    @DynamicSwitchDataSource(dataSource = DataSourceEnum.MASTER)
    public void buyCar(String uid,String carName,int number) {
        TbCar car = tbCarService.findById(carName);
        TbAccount account = tbAccountService.findById(uid);

        int carPrice = getCarPrice(car,carName,number);
        int AccountBalance = getUserAccount(account);

        car.setCarNumber(car.getCarNumber()-number); //数量减少
        tbCarService.update(car);

        if(AccountBalance<carPrice){
            throw new NotSufficientFundsException("余额不足！");
        }

        account.setAccount(account.getAccount()-carPrice);//余额减少
        tbAccountService.update(account);
    }

    /**
     * 获取要购买的汽车价格
     * @param carName
     * @param number
     * @return
     */
    public int getCarPrice(TbCar car,String carName,int number){
        return car.getCarPrice()*number;
    }

    /**
     * 获取个人总金额
     * @param account
     * @return
     */
    public int getUserAccount(TbAccount account){
        return account.getAccount();
    }
}
