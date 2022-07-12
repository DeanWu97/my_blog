package com.dean.my_blog.threadLocals;

import com.dean.my_blog.entity.User;

import java.util.BitSet;
import java.util.Objects;

public class UserThreadLocal{
    private static final ThreadLocal<User> USER_THREADLOCAL = new InheritableThreadLocal<User>();

    public static void set(User user) {
        USER_THREADLOCAL.set(user);
    }

    public static User get() {
        return USER_THREADLOCAL.get();
    }

    public static void remove() {
        USER_THREADLOCAL.remove();
    }

    public static Boolean isGuest() {
        return Objects.nonNull(USER_THREADLOCAL.get()) && "guest".equals(USER_THREADLOCAL.get().getNickname());
    }

    public static Boolean notGuest() {return !isGuest();}
}
