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
	mercadolibreDev {
		hibernate {
			show_sql=true
		}
		dataSource {
			//dbCreate = "create-drop" // one of 'create', 'create-drop','update'
			pooled = true
			dialect = "org.hibernate.dialect.OracleDialect"
			url = "jdbc:oracle:thin:@10.3.207.108:1521:ORCL"
			driverClassName = "oracle.jdbc.driver.OracleDriver"
			username = "ORCL_W01"
			password = "ora_desa1"
			properties {
				maxActive = 50
				maxIdle = 25
				minIdle = 5
				initialSize = 5
				minEvictableIdleTimeMillis = 60000
				timeBetweenEvictionRunsMillis = 60000
				maxWait = 10000
				validationQuery = "select 1 from dual"
			}
		}
	}
	mercadolibre {
		hibernate {
			show_sql=false
		}
		dataSource {
			//dbCreate = "create-drop" // one of 'create', 'create-drop','update'
			pooled = true
			dialect = "org.hibernate.dialect.OracleDialect"
			url = "jdbc:oracle:thin:@//172.16.0.90:1521/con_w01_access"
			driverClassName = "oracle.jdbc.driver.OracleDriver"
			username = "con_w01prod"
			password = "%D5Eh?hxl^84@"
			properties {
				maxActive = 50
				maxIdle = 25
				minIdle = 5
				initialSize = 5
				minEvictableIdleTimeMillis = 60000
				timeBetweenEvictionRunsMillis = 60000
				maxWait = 10000
				validationQuery = "select 1 from dual"
			}
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
