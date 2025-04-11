package org.stadium.commonservice.authorization;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

@Getter
public enum Permission
{
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    TICKET_VIEW("ticket:view"),
    TICKET_BOOK("ticket:book"),
    TICKET_CANCEL("ticket:cancel"),
    TICKET_MANAGE("ticket:manage"),
    EVENT_CREATE("event:create"),
    EVENT_READ("event:read"),
    EVENT_UPDATE("event:update"),
    EVENT_DELETE("event:delete"),
    PAYMENT_PROCESS("payment:process"),
    REPORT_GENERATE("report:generate");

    private String authority;

    Permission(String authority)
    {
        this.authority = authority;
    }

    public static final Set<Permission> ALL_PERMISSIONS;


    static{
        Set<Permission> all = new HashSet<>();
        Collections.addAll(all, values());
        ALL_PERMISSIONS =  Collections.unmodifiableSet(all);
        
    }

}

