//package by.hembar.idservice.entity;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//public enum Role {
//
//    ADMIN(Set.of(
//            Permission.NEWS_WRITE,
//            Permission.NEWS_READ,
//            Permission.COMMENTS_WRITE,
//            Permission.COMMENTS_READ,
//            Permission.ACTIVATE_USERS
//    )),
//    JOURNALIST(Set.of(
//            Permission.NEWS_WRITE,
//            Permission.NEWS_READ,
//            Permission.COMMENTS_READ
//    )),
//    SUBSCRIBER(Set.of(
//            Permission.NEWS_READ,
//            Permission.COMMENTS_READ,
//            Permission.COMMENTS_WRITE
//    ));
//
//    private final Set<Permission> permissions;
//
//    public Set<SimpleGrantedAuthority> getAuthorities() {
//        return permissions.stream().map(permission ->
//                new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toSet());
//    }
//}
