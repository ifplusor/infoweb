package psn.ifplusor.core.security;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AlternativeAuthenticationEntryPoint implements AuthenticationEntryPoint,
        InitializingBean {

    private CasAuthenticationEntryPoint casEntryPoint = null;
    private LoginUrlAuthenticationEntryPoint formEntryPoint = null;
    private DigestAuthenticationEntryPoint digestEntryPoint = null;

    private boolean useHeader = true;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.isTrue((casEntryPoint!=null) || (formEntryPoint!=null) || (digestEntryPoint!=null), "must have one AuthenticationEntry Point");
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        if (casEntryPoint != null) {
            casEntryPoint.commence(request, response, authException);
            return;
        }

        if (formEntryPoint == null || digestEntryPoint == null) {
            if (formEntryPoint == null) {
                digestEntryPoint.commence(request, response, authException);
            } else {
                formEntryPoint.commence(request, response, authException);
            }
        } else {
            // 决策使用表单认证还是摘要认证
            String authMethod = null;
            if (useHeader) {
                authMethod = request.getHeader("Auth-Method");
            } else {
                authMethod = (String) request.getParameter("Auth-Method");
            }

            if (authMethod != null && authMethod.equals("Digest")) {
                digestEntryPoint.commence(request, response, authException);
            } else {
                formEntryPoint.commence(request, response, authException);
            }
        }
    }

    public void setCasEntryPoint(CasAuthenticationEntryPoint casEntryPoint) {
        this.casEntryPoint = casEntryPoint;
    }

    public void setFormEntryPoint(LoginUrlAuthenticationEntryPoint formEntryPoint) {
        this.formEntryPoint = formEntryPoint;
    }

    public void setDigestEntryPoint(DigestAuthenticationEntryPoint digestEntryPoint) {
        this.digestEntryPoint = digestEntryPoint;
    }

    public void setUseHeader(boolean useHeader) {
        this.useHeader = useHeader;
    }
}
