import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.web.authentication.session.ConcurrentSessionControlStrategy
import org.springframework.security.web.session.ConcurrentSessionFilter
import com.ml.cmc.handlers.UnlockLogoutHandler

beans = {

    sessionRegistry(SessionRegistryImpl)

    unlockLogoutHander(UnlockLogoutHandler)

    concurrencyFilter(ConcurrentSessionFilter) {
        sessionRegistry = sessionRegistry
        logoutHandlers = [
            ref("rememberMeServices"),
            ref("securityContextLogoutHandler"),
            ref("unlockLogoutHander")
        ]
        expiredUrl='/login/login.jsp'
    }
    concurrentSessionControlStrategy(ConcurrentSessionControlStrategy, sessionRegistry) {
        alwaysCreateSession = true
        exceptionIfMaximumExceeded = true
        maximumSessions = 1
    }
}
