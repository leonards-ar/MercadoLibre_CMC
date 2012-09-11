package com.ml.cmc.service

class LotGeneratorService {

    static transactional = true
    
    def sessionFactory

    def getLotId() {
        
        def sql = "SELECT JAVA_LOT.nextval FROM dual"
        def session = sessionFactory.getCurrentSession();
        def query = session.createSQLQuery(sql);
        def result = query.list()
        return result[0]
    }
    
    def getGroupId() {
        
        def sql = "SELECT COMP_GROUP.nextval FROM dual"
        def session = sessionFactory.getCurrentSession();
        def query = session.createSQLQuery(sql);
        def result = query.list()
        return result[0]
        
    }
}
