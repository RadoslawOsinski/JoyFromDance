package dance.joyfrom.rest.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by Radosław Osiński
 */
public class JoyFromDanceAuthenticationToken extends org.springframework.security.authentication.UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = -8942611273704893882L;

    public JoyFromDanceAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public JoyFromDanceAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
