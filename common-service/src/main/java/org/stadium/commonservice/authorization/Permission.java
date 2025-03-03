package org.stadium.commonservice.authorization;

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
    EVENT_VIEW("event:view"),
    EVENT_UPDATE("event:update"),
    EVENT_DELETE("event:delete"),
    PAYMENT_PROCESS("payment:process"),
    REPORT_GENERATE("report:generate");

    private String authority;

    Permission(String authority)
    {
        this.authority = authority;
    }

}

