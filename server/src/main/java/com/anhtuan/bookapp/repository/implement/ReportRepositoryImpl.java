package com.anhtuan.bookapp.repository.implement;

import com.anhtuan.bookapp.config.Constant;
import com.anhtuan.bookapp.domain.Report;
import com.anhtuan.bookapp.repository.customize.ReportCustomizeRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ReportRepositoryImpl implements ReportCustomizeRepository {
    private MongoTemplate mongoTemplate;

    @Override
    public void updateStatusById(String id, int status) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Report.ID).is(new ObjectId(id)));
        Update update = new Update();
        update.set(Report.STATUS, status);
        if (status == Constant.REPORT_STATUS.CHECKED) {
            update.set(Report.CHECK_TIME, System.currentTimeMillis());
        }
        mongoTemplate.updateFirst(query, update, Report.class);
    }
}
