dataSource {
    pooled = true
    driverClassName =   "org.hsqldb.jdbcDriver"//"oracle.jdbc.driver.OracleDriver"
    username = "sa"//"ORCL_W01"
    password = ""//"ORCL_W01"
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop','update'
            //url = "jdbc:oracle:thin:@localhost:1521:"
            url = "jdbc:hsqldb:mem:devDB"
        }
    }
	mercadolibre {
		hibernate {
			show_sql=true
		}
		dataSource {
			//dbCreate = "create-drop" // one of 'create', 'create-drop','update'
			dialect = "org.hibernate.dialect.OracleDialect"
			url = "jdbc:oracle:thin:@10.3.207.108:1521:ORCL "
			driverClassName = "oracle.jdbc.driver.OracleDriver"
			username = "ORCL_W01"
			password = "ORCL_W01"
		}
	}
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:hsqldb:mem:testDb"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:hsqldb:file:prodDb;shutdown=true"
        }
    }
}