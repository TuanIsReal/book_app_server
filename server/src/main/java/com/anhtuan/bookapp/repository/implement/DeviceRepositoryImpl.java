package com.anhtuan.bookapp.repository.implement;

import com.anhtuan.bookapp.domain.Device;
import com.anhtuan.bookapp.repository.customize.DeviceCustomizeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class DeviceRepositoryImpl implements DeviceCustomizeRepository {

    private MongoTemplate mongoTemplate;

    @Override
    public void updateDeviceTokenByUserId(String userId, String deviceToken) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Device.USER_ID).is(userId));
        Update update = new Update();
        update.set(Device.DEVICE_TOKEN, deviceToken);
        mongoTemplate.updateFirst(query, update, Device.class);
    }
}
