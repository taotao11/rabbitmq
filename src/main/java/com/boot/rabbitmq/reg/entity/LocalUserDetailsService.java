package com.boot.rabbitmq.reg.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户验证判断
 * 实现UserDetailsService 接口
 *
 *  boolean enabled = true; // 可用性 :true:可用 false:不可用
 *  boolean accountNonExpired = true; // 过期性 :true:没过期 false:过期boolean
 *  credentialsNonExpired = true; // 有效性 :true:凭证有效 false:凭证无效
 *  boolean accountNonLocked = true; // 锁定性 :true:未锁定 false:已锁定
 */
public class LocalUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //用户验证
        if (!"admin".equals(s)){
            System.out.println("用户不存在!!!");
           throw new UsernameNotFoundException("用户不存在!!!!");
        }
        //拿到密码
        String password = "$2a$10$5QRz2BTtOa63cbvNrWGXNOtzWLw64CWTKgmI/TNbad/SIYy4z00gu";
        //拿到角色
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new User(s,password,list);
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //加密
        String pwd = encoder.encode("123456");
        System.out.println(pwd);
    }
}
