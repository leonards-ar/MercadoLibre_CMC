package com.ml.cmc.service

class LotGeneratorService {

    static transactional = true

    def getLotId() {
        
        def sql = "SELECT JAVA_LOT.nextval FROM dual"
        def query = sessionFactory.currentSession.createSQLQuery(sql);
        def result = query.list()
        return result[0]
    }
}
