import org.apache.log4j.DailyRollingFileAppender

// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// The default codec used to encode data with ${}
grails.views.default.codec = "html" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable for AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// set per-environment serverURL stem for creating absolute links
environments {
    production {
        grails.serverURL = "http://www.changeme.com"
    }
    development {
        grails.serverURL = "http://localhost:8080/${appName}"
    }
    test {
        grails.serverURL = "http://localhost:8080/${appName}"
    }
	mercadolibreDev {
		grails.serverURL = "http://localhost:8080/${appName}DEV"
		grails.app.context = "/cmcDev"
		application.name= "cmcDev"
		grails.config.locations = ["classpath:${application.name}-config.properties"]
	}
	mercadolibreUat {
		grails.serverURL = "http://localhost:8080/${appName}UAT"
		grails.app.context = "/cmcUat"
		application.name= "cmcUat"
		grails.config.locations = ["classpath:${application.name}-config.properties"]
	}
	mercadolibre {
		grails.serverURL = "http://localhost:8080/${appName}"
		application.name= "cmc"
		grails.config.locations = ["classpath:${application.name}-config.properties"]
	}

}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    appenders {
        console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
		environments {
			mercadolibreDev {
				appenders{
					 appender new DailyRollingFileAppender(
					name:'dailycmcAppender',
					datePattern: "'.'yyyy-MM-dd",
					file:'/usr/java/logs/cmcDev.log',
					layout:pattern(conversionPattern: '%d [%t] %-5p %c{2} %x - %m%n')
					 )
					appender new DailyRollingFileAppender(
					name:"sqlAppender",
					datePattern: "'.'yyyy-MM-dd",
					file:'/usr/java/logs/cmcDevSQL.log',
					layout:pattern(conversionPattern: '%d [%t] %-5p %c{2} %x - %m%n')
					)
				}
			}
			mercadolibreUat {
				appender new DailyRollingFileAppender(
					name:'dailycmcAppender',
					datePattern: "'.'yyyy-MM-dd",
					file:'/usr/java/logs/cmcUat.log',
					layout:pattern(conversionPattern: '%d [%t] %-5p %c{2} %x - %m%n')
				)
			}
			mercadolibre {
				appenders{
					appender new DailyRollingFileAppender(
						name:'dailycmcAppender',
						datePattern: "'.'yyyy-MM-dd",
						file:'/usr/java/logs/cmc.log',
						layout:pattern(conversionPattern: '%d [%t] %-5p %c{2} %x - %m%n')
					)
				}
			}
		}
    }

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    warn   'org.mortbay.log'
	
	info   'grails.app'
	debug 
	environments {
		mercadolibre {
			info dailycmcAppender: 'grails.app'
			 
		}
		mercadolibreUat {
			info dailycmcAppender: 'grails.app'
		}
		mercadolibreDev {
			trace sqlAppender: 'org.hibernate.type'
			debug sqlAppender: 'org.hibernate.SQL'
			info dailycmcAppender: 'grails.app'
		}
	}
}

// Added by the Spring Security Core plugin:
//grails.plugins.springsecurity.userLookup.userDomainClassName = 'com.ml.cmc.User'
//grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'com.ml.cmc.UserRole'
//grails.plugins.springsecurity.authority.className = 'com.ml.cmc.Role'
grails.plugins.springsecurity.useHttpSessionEventPublisher = true
grails.plugins.springsecurity.providerNames = ['ldapAuthProvider','anonymousAuthenticationProvider']
grails.plugins.springsecurity.logout.handlerNames = ['rememberMeServices','securityContextLogoutHandler', 'unlockLogoutHander']
grails.plugins.springsecurity.securityConfigType = "InterceptUrlMap"
grails.plugins.springsecurity.interceptUrlMap = [
    '/home/**':    ['ROLE_USER','ROLE_FOCUS_CONCILIACION','ROLE_FOCUS_CONCILIACION_ADMIN', 'ROLE_FOCUS_PRECONCILIACION', 'ROLE_FOCUS_PRECONCILIACION_ADMIN'],
    '/preconciliation/**':    ['ROLE_USER','ROLE_FOCUS_PRECONCILIACION', 'ROLE_FOCUS_PRECONCILIACION_ADMIN'],
	'/compensation/**':    ['ROLE_USER','ROLE_FOCUS_CONCILIACION', 'ROLE_FOCUS_CONCILIACION_ADMIN'],
	'/conciliation/**':    ['ROLE_USER','ROLE_FOCUS_CONCILIACION', 'ROLE_FOCUS_CONCILIACION_ADMIN'],
    '/desconciliation/**':    ['ROLE_USER','ROLE_FOCUS_CONCILIACION','ROLE_FOCUS_CONCILIACION_ADMIN'],
	'/despreconciliation/**':    ['ROLE_USER','ROLE_FOCUS_PRECONCILIACION','ROLE_FOCUS_PRECONCILIACION_ADMIN'],
	'/auditLog/**':    ['ROLE_USER','ROLE_FOCUS_CONCILIACION','ROLE_FOCUS_CONCILIACION_ADMIN','ROLE_FOCUS_PRECONCILIACION','ROLE_FOCUS_PRECONCILIACION_ADMIN'],
    '/index.gsp':  ['IS_AUTHENTICATED_ANONYMOUSLY'],
    '/login/**':     ['IS_AUTHENTICATED_ANONYMOUSLY'],
    '/logout/**':    ['IS_AUTHENTICATED_ANONYMOUSLY']
    
 ]

grails.plugins.springsecurity.ldap.context.managerDn = 'CN=Conciliador Focus,OU=FOCUS,OU=Usuarios Aplicativos,OU=Usuarios,OU=MELI,OU=MercadoLibre,DC=ml,DC=com'
grails.plugins.springsecurity.ldap.context.managerPassword = 'Xsw2.3edc'
grails.plugins.springsecurity.ldap.context.server = 'ldap://10.200.1.10:389'
//grails.plugins.springsecurity.ldap.context.server = 'ldap://localhost:10389'
grails.plugins.springsecurity.ldap.authorities.ignorePartialResultException = true // typically needed for Active Directory
grails.plugins.springsecurity.ldap.search.base = 'DC=ml,DC=com'
grails.plugins.springsecurity.ldap.search.filter="sAMAccountName={0}" // for Active Directory you need this
grails.plugins.springsecurity.ldap.search.searchSubtree = true
grails.plugins.springsecurity.ldap.auth.hideUserNotFoundExceptions = false
grails.plugins.springsecurity.ldap.search.attributesToReturn = ['name', 'mail','sn']

// role-specific LDAP config
grails.plugins.springsecurity.ldap.useRememberMe = false
grails.plugins.springsecurity.ldap.authorities.retrieveGroupRoles = true
grails.plugins.springsecurity.ldap.authorities.groupSearchBase ='OU=FOCUS,OU=Usuarios Aplicativos,OU=Usuarios,OU=MELI,OU=MercadoLibre,DC=ml,DC=com'
// If you don't want to support group membership recursion (groups in groups), then use the following setting
grails.plugins.springsecurity.ldap.authorities.groupSearchFilter = 'member={0}' // Active Directory specific

// jquery configuration
grails.views.javascript.library="jquery"
