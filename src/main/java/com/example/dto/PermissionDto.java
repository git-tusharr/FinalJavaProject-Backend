package com.example.dto;

public record PermissionDto(
        Long id,
        String code,
        String module,
        boolean assigned,
        boolean disabled
) {}


//{
//	  "id": 7,
//	  "code": "ORDER_DELETE",
//	  "module": "ORDER",
//	  "assigned": true,
//	  "disabled": false
//	}
