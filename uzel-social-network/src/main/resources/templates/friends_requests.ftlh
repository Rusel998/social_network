<!DOCTYPE html>
<#import "/spring.ftl" as spring1 />
<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <title><@spring1.message code="requests.title" /></title>

    <#if _csrf??>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <meta name="_csrf" content="${_csrf.token}"/>
    </#if>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100">

<#include "fragments/header.ftlh">

<div class="container py-4">
    <h2><@spring.message code="requests.title" /></h2>

    <#if requests?size == 0>
        <p class="text-muted"><@spring.message code="requests.empty" /></p>
    <#else>
        <div class="list-group">
            <#list requests as friendship>
                <div class="list-group-item d-flex align-items-center justify-content-between">
                    <div class="d-flex align-items-center">
                        <img src="/files/${friendship.user.avatar.storageFileName}" alt="<@spring.message code='requests.avatar'/>" width="48" height="48" class="rounded-circle me-3">
                        <div>
                            <strong>${friendship.user.firstName} ${friendship.user.lastName}</strong><br>
                            <a href="/profile/${friendship.user.id}" class="text-decoration-none">
                                <@spring.message code="requests.viewProfile" />
                            </a>
                        </div>
                    </div>
                    <div class="d-flex gap-2">
                        <form method="post" action="/friendships/${friendship.id}/accept">
                            <#if _csrf??>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            </#if>
                            <button class="btn btn-sm btn-success">
                                <@spring.message code="requests.accept" />
                            </button>
                        </form>

                        <form method="post" action="/friendships/${friendship.id}/decline">
                            <#if _csrf??>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            </#if>
                            <button class="btn btn-sm btn-outline-danger">
                                <@spring.message code="requests.decline" />
                            </button>
                        </form>
                    </div>
                </div>
            </#list>
        </div>
    </#if>
</div>

<#include "fragments/footer.ftlh">

</body>
</html>
