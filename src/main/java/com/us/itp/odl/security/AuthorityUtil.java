package com.us.itp.odl.security;

import static com.us.itp.odl.util.CollectionUtil.immutableSetOf;
import static java.util.Arrays.stream;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toList;

import com.us.itp.odl.util.StaticUtil;
import java.util.Collection;
import java.util.Set;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class AuthorityUtil {

    private AuthorityUtil() {
        StaticUtil.preventInstantiation(this.getClass());
    }

    @NonNull public static Set<GrantedAuthority> authoritySet(
            @NonNull final String... authorities
    ) {
        return extendAuthoritySet(emptySet(), authorities);
    }

    @NonNull public static Set<GrantedAuthority> extendAuthoritySet(
            @NonNull final Set<GrantedAuthority> baseAuthorities,
            @NonNull final String... newAuthorities
    ) {
        return immutableSetOf(baseAuthorities, authoritiesFromStrings(newAuthorities));
    }

    @NonNull private static Collection<? extends GrantedAuthority> authoritiesFromStrings(
            @NonNull final String... authorityNames
    ) {
        return stream(authorityNames).map(SimpleGrantedAuthority::new).collect(toList());
    }
}
